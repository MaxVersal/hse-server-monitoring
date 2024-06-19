package hse.activity.tracker.services;

import hse.activity.tracker.data.screenshots.ScreenShot;
import hse.activity.tracker.data.screenshots.ScreenShotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenshotService {
    private final ScreenShotRepository screenShotRepository;

    public ScreenshotService(ScreenShotRepository screenShotRepository) {
        this.screenShotRepository = screenShotRepository;
    }

    public void saveScreenshot(ScreenShot s) {
        screenShotRepository.save(s);
    }

    public List<ScreenShot> getLastScreenshots(Long userId) {
        return screenShotRepository.getLastScreenshots(userId);
    }
}
