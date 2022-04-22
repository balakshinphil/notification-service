package notificationservice.controller;

import notificationservice.model.Notification;
import notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@RestController
public class NotificationController {

    private final NotificationService notificationService;



    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @PostMapping("/api/notification/new")
    public Map<String, Long> addNotification(@RequestBody @Valid Notification notification) {
        return notificationService.addNotification(notification);
    }


    @PutMapping("/api/notification/{id}")
    public void updateNotification(@RequestBody @Valid Notification notification,
                                   @PathVariable long id) {
        notificationService.updateNotification(notification, id);
    }


    @DeleteMapping("/api/notification/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotification(@PathVariable long id) {
        notificationService.deleteNotification(id);
    }
}
