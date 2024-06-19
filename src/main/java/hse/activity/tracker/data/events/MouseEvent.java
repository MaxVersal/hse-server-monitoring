package hse.activity.tracker.data.events;

public class MouseEvent {
    private Long mouseClicks;
    private Double mouseDistance;
    private Long mousePresses;

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

    public Long getMousePresses() {
        return mousePresses;
    }

    public void setMousePresses(Long mousePresses) {
        this.mousePresses = mousePresses;
    }
}
