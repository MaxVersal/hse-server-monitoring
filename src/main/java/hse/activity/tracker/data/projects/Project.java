package hse.activity.tracker.data.projects;

import hse.activity.tracker.data.AbstractEntity;
import hse.activity.tracker.data.users.SamplePerson;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

import java.util.List;

/**
 * todo Document type Project
 */
@Entity
public class Project extends AbstractEntity {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "project_person",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private List<SamplePerson> users;

    private String projectName;

    public List<SamplePerson> getUsers() {
        return users;
    }

    public void setUsers(List<SamplePerson> users) {
        this.users = users;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
