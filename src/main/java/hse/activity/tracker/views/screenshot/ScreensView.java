package hse.activity.tracker.views.screenshot;

import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;
import hse.activity.tracker.data.screenshots.ScreenShot;
import hse.activity.tracker.data.users.SamplePerson;
import hse.activity.tracker.services.SamplePersonService;
import hse.activity.tracker.services.ScreenshotService;
import hse.activity.tracker.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;

import java.io.ByteArrayInputStream;
import java.util.List;

@RolesAllowed("ADMIN")
@PageTitle("ScreenShots")
@Route(value = "screens", layout = MainLayout.class)
@RouteAlias(value = "screens", layout = MainLayout.class)
@Uses(Icon.class)
public class ScreensView extends Main {
    private final ScreenshotService screenshotService;
    private final SamplePersonService samplePersonService;
    private Select<String> personSelect;
    private VerticalLayout layout = new VerticalLayout();

    public ScreensView(ScreenshotService screenshotService, SamplePersonService samplePersonService) {
        this.screenshotService = screenshotService;
        this.samplePersonService = samplePersonService;
        initSelect();
        initScreens();
        add(layout);
    }

    private void initScreens() {
        layout.removeAll();
        List<ScreenShot> screenShots = screenshotService.getLastScreenshots(samplePersonService.getByEmail(personSelect.getValue()).getId());
        screenShots.forEach(screenShot -> {
            final byte[] bytes = screenShot.getScreenshot();
            StreamResource streamResource = getImageStreamResource(bytes);
            Image image = new Image(streamResource, "screen");
            layout.add(image);
        });
    }

    private void initSelect() {
        personSelect = new Select<>();
        List<String> names = samplePersonService.list()
            .stream()
            .map(SamplePerson::getEmail)
            .toList();
        personSelect.setItems(names);
        personSelect.setValue(names.get(0));
        personSelect.setWidth("215px");
        personSelect.addValueChangeListener(e -> initScreens());

        this.add(personSelect);
    }

    private StreamResource getImageStreamResource(byte[] imageBytes) {
        return new StreamResource("screenshot.png", () -> new ByteArrayInputStream(imageBytes));
    }
}
