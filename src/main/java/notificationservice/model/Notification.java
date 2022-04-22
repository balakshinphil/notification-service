package notificationservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "NOTIFICATIONS")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @JsonProperty("start_time")
    LocalDateTime startSendingTime;

    @NotBlank
    String message;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "mobileOperator", column = @Column(name = "filter_operator")),
            @AttributeOverride(name = "tag", column = @Column(name = "filter_tag"))
    })
    ClientFilter filter;

    LocalDateTime endSendingTime;

    public Notification() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getStartSendingTime() {
        return startSendingTime;
    }

    public void setStartSendingTime(LocalDateTime startSendingTime) {
        this.startSendingTime = startSendingTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ClientFilter getFilter() {
        return filter;
    }

    public void setFilter(ClientFilter filter) {
        this.filter = filter;
    }

    public LocalDateTime getEndSendingTime() {
        return endSendingTime;
    }

    public void setEndSendingTime(LocalDateTime endSendingTime) {
        this.endSendingTime = endSendingTime;
    }
}
