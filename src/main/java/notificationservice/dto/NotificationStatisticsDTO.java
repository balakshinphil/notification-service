package notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import notificationservice.model.Message;

import java.util.List;

public class NotificationStatisticsDTO {

    @JsonProperty("notification_id")
    private long notificationId;

    @JsonProperty("sent_messages")
    private List<Message> messageList;

    public NotificationStatisticsDTO(long notificationId, List<Message> messageList) {
        this.notificationId = notificationId;
        this.messageList = messageList;
    }

    public long getNotificationId() {
        return notificationId;
    }

    public List<Message> getMessageList() {
        return messageList;
    }
}
