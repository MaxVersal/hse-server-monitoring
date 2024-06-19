package hse.activity.tracker.data.projects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectRepository extends
    JpaRepository<Project, Long>,
    JpaSpecificationExecutor<Project> {

    Project findByProjectName(String projectName);
}
