package notificationservice.service;

import notificationservice.exception.NotificationNotFoundException;
import notificationservice.model.Client;
import notificationservice.model.Notification;
import notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MessageService messageService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository,
                               MessageService messageService) {
        this.notificationRepository = notificationRepository;
        this.messageService = messageService;
    }



    public Map<String, Long> addNotification(Notification notification) {
        notificationRepository.save(notification);

        messageService.createMessageSendingEvent(notification);

        return Map.of("id", notification.getId());
    }


    public void updateNotification(Notification newNotification, long oldNotificationId) {
        if (!notificationRepository.existsById(oldNotificationId)) {
            throw new NotificationNotFoundException(String.format("Notification with id %d not found!", oldNotificationId));
        }

        newNotification.setId(oldNotificationId);
    }


    public void deleteNotification(long notificationId) {
        if (!notificationRepository.existsById(notificationId)) {
            throw new NotificationNotFoundException(String.format("Notification with id %d not found!", notificationId));
        }

        notificationRepository.deleteById(notificationId);
    }

}
