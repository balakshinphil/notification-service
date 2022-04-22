package notificationservice.service;

import notificationservice.dto.NotificationStatisticsDTO;
import notificationservice.dto.StatisticsDTO;
import notificationservice.model.Message;
import notificationservice.model.MessageStatus;
import notificationservice.model.Notification;
import notificationservice.repository.MessageRepository;
import notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private final NotificationRepository notificationRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public StatisticsService(NotificationRepository notificationRepository,
                             MessageRepository messageRepository) {
        this.notificationRepository = notificationRepository;
        this.messageRepository = messageRepository;
    }



    public List<StatisticsDTO> getGeneralStatistics() {
        List<Notification> notificationList = notificationRepository.findAll();
        return notificationList.stream()
                .map(notification -> getGeneralStatisticsForNotification(notification.getId()))
                .collect(Collectors.toList());
    }


    public NotificationStatisticsDTO getDetailStatisticsForNotification(long id) {
        List<Message> messageList = messageRepository.findAllByNotificationId(id);
        return new NotificationStatisticsDTO(id, messageList);
    }



    private StatisticsDTO getGeneralStatisticsForNotification(long id) {
        List<Message> messageList = messageRepository.findAllByNotificationId(id);
        long deliveredMessages = messageList.stream()
                .filter(message -> message.getStatus() == MessageStatus.DELIVERED).count();

        long declinedMessages = messageList.stream()
                .filter(message -> message.getStatus() == MessageStatus.DECLINED).count();

        return new StatisticsDTO(id, deliveredMessages, declinedMessages);
    }
}
