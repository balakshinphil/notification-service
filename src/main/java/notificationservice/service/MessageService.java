package notificationservice.service;

import notificationservice.exception.HttpRequestException;
import notificationservice.model.*;
import notificationservice.repository.ClientRepository;
import notificationservice.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final ClientRepository clientRepository;
    private final MessageRepository messageRepository;
    private final String TOKEN;

    @Autowired
    public MessageService(ClientRepository clientRepository, MessageRepository messageRepository, String TOKEN) {
        this.clientRepository = clientRepository;
        this.messageRepository = messageRepository;
        this.TOKEN = TOKEN;
    }


    public void createMessageSendingEvent(Notification notification) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        TaskScheduler scheduler = new ConcurrentTaskScheduler(executor);

        scheduler.schedule(() -> startNotificationSending(notification),
                Date.from(notification.getStartSendingTime()
                        .atZone(ZoneId.systemDefault()).toInstant()));
    }



    private void startNotificationSending(Notification notification) {
        List<Client> clientList = clientRepository.findAll();
        ClientFilter filter = notification.getFilter();
        if (filter != null) {
            String filterMobileOperator = filter.getMobileOperator();
            if (filterMobileOperator != null) {
                clientList = clientList.stream()
                        .filter(client -> Objects.equals(client.getMobileOperator(), filterMobileOperator))
                        .collect(Collectors.toList());
            }

            String filterTag = filter.getTag();
            if (filterTag != null) {
                clientList = clientList.stream()
                        .filter(client -> Objects.equals(client.getTag(), filterTag))
                        .collect(Collectors.toList());
            }
        }

        for (Client client : clientList) {
            sendMessage(client, notification);
        }
    }

    private void sendMessage(Client client, Notification notification) {
        Message message = new Message(LocalDateTime.now(), MessageStatus.IN_PROCESS,
                notification.getId(), client.getId());

        messageRepository.save(message);

        HttpResponse<String> response;
        try {
          response = postRequest(message.getId(), client.getPhoneNumber(), notification.getMessage());
            if (response.statusCode() == 200) {
                message.setStatus(MessageStatus.DELIVERED);
            } else {
                message.setStatus(MessageStatus.DECLINED);
            }
        } catch (HttpRequestException e) {
            message.setStatus(MessageStatus.DECLINED);
        }

        messageRepository.save(message);
    }

    private HttpResponse<String> postRequest(long messageId, String phoneNumber, String message) {
        try {
            String json = "{" +
                    String.format("\"id\": %d, ", messageId) +
                    String.format("\"phone\": %d, ", Long.parseLong(phoneNumber)) +
                    String.format("\"text\": \"%s\"", message) +
                    "}";


            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();


            URI uri = URI.create(String.format("https://probe.fbrq.cloud/v1/send/%d", messageId));

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .uri(uri)
                    .setHeader("User-Agent", "Notification service")
                    .setHeader("Authorization", "Bearer " + TOKEN)
                    .header("Content-Type", "application/json")
                    .build();

            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new HttpRequestException("Http request error!", e);
        }
    }

}
