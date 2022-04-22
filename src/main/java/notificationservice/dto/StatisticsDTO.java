package notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatisticsDTO {

    @JsonProperty("notification_id")
    private final long notificationId;

    @JsonProperty("delivered_messages")
    private final long deliveredMessages;

    @JsonProperty("declined_messages")

    private final long declinedMessages;

    public StatisticsDTO(long notificationId, long deliveredMessages, long declinedMessages) {
        this.notificationId = notificationId;
        this.deliveredMessages = deliveredMessages;
        this.declinedMessages = declinedMessages;
    }

    public long getNotificationId() {
        return notificationId;
    }

    public long getDeliveredMessages() {
        return deliveredMessages;
    }

    public long getDeclinedMessages() {
        return declinedMessages;
    }
}
