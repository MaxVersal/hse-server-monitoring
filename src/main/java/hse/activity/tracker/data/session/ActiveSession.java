package hse.activity.tracker.data.session;

import hse.activity.tracker.data.AbstractEntity;
import hse.activity.tracker.data.RabbitMessage;
import hse.activity.tracker.data.users.SamplePerson;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.sql.Timestamp;

@Entity
public class ActiveSession extends AbstractEntity {
    private Timestamp time;
    private Long mouseClicks;
    private Double mouseDistance;
    private Integer keyPressed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private SamplePerson person;

    public ActiveSession() {}

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Long getMouseClicks() {
        return mouseClicks;
    }

    public void setMouseClicks(Long mouseClicks) {
        this.mouseClicks = mouseClicks;
    }

    public Double getMouseDistance() {
        return mouseDistance;
    }

    public void setMouseDistance(Double mouseDistance) {
        this.mouseDistance = mouseDistance;
    }

    public Integer getKeyPressed() {
        return keyPressed;
    }

    public void setKeyPressed(Integer keyPressed) {
        this.keyPressed = keyPressed;
    }

    public ActiveSession(RabbitMessage rabbitMessage) {
        this.mouseClicks = rabbitMessage.getMouseEvent().getMouseClicks();
        this.mouseDistance = rabbitMessage.getMouseEvent().getMouseDistance();
        this.time = Timestamp.valueOf(rabbitMessage.getCreateTime());
    }
}
