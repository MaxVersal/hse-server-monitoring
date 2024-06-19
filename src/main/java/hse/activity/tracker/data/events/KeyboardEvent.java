package hse.activity.tracker.data.events;

import java.util.Map;

public class KeyboardEvent {
    private Map<String, Long> keyPressed;

    private Map<String, Long> keyReleased;
    private Map<String, Long> keyTyped;
    private Long totalPresses;

    public Map<String, Long> getKeyPressed() {
        return keyPressed;
    }

    public void setKeyPressed(Map<String, Long> keyPressed) {
        this.keyPressed = keyPressed;
    }

    public Map<String, Long> getKeyReleased() {
        return keyReleased;
    }

    public void setKeyReleased(Map<String, Long> keyReleased) {
        this.keyReleased = keyReleased;
    }

    public Map<String, Long> getKeyTyped() {
        return keyTyped;
    }

    public void setKeyTyped(Map<String, Long> keyTyped) {
        this.keyTyped = keyTyped;
    }

    public Long getTotalPresses() {
        return totalPresses;
    }

    public void setTotalPresses(Long totalPresses) {
        this.totalPresses = totalPresses;
    }

    @Override
    public String toString() {
        return "KeyboardEvent{" +
            "keyPressed=" + keyPressed +
            ", keyReleased=" + keyReleased +
            ", keyTyped=" + keyTyped +
            '}';
    }
}
