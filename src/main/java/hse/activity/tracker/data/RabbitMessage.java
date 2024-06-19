package hse.activity.tracker.data;

import hse.activity.tracker.data.events.KeyboardEvent;
import hse.activity.tracker.data.events.MouseEvent;

import java.time.LocalDateTime;

public class RabbitMessage {
    private MouseEvent mouseEvent;
    private KeyboardEvent keyboardEvent;
    private LocalDateTime createTime;
    private String projectName;

    public MouseEvent getMouseEvent() {
        return mouseEvent;
    }

    public void setMouseEvent(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
    }

    public KeyboardEvent getKeyboardEvent() {
        return keyboardEvent;
    }

    public void setKeyboardEvent(KeyboardEvent keyboardEvent) {
        this.keyboardEvent = keyboardEvent;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
