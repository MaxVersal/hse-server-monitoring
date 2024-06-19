package hse.activity.tracker.data.screenshots;

import hse.activity.tracker.data.AbstractEntity;
import hse.activity.tracker.data.projects.Project;
import hse.activity.tracker.data.users.SamplePerson;
import jakarta.persistence.*;

@Entity
public class ScreenShot extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private SamplePerson person;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @Lob
    private byte[] screenshot;

    public SamplePerson getPerson() {
        return person;
    }

    public void setPerson(SamplePerson person) {
        this.person = person;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public byte[] getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(byte[] screenshot) {
        this.screenshot = screenshot;
    }
}
