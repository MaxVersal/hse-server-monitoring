package hse.activity.tracker.services;

import hse.activity.tracker.data.projects.Project;
import hse.activity.tracker.data.projects.ProjectRepository;
import hse.activity.tracker.data.users.SamplePerson;
import hse.activity.tracker.data.users.SamplePersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final SamplePersonRepository samplePersonRepository;

    public ProjectService(ProjectRepository projectRepository, SamplePersonRepository samplePersonRepository) {
        this.projectRepository = projectRepository;
        this.samplePersonRepository = samplePersonRepository;
    }
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Transactional
    public void addPersonToProject(Long projectId, Long personId) {
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        Optional<SamplePerson> personOpt = samplePersonRepository.findById(personId);

        if (projectOpt.isPresent() && personOpt.isPresent()) {
            Project project = projectOpt.get();
            SamplePerson person = personOpt.get();

            project.getUsers().add(person);
            person.getProjectList().add(project);

            projectRepository.save(project);
            samplePersonRepository.save(person);
        } else {
            throw new RuntimeException("Project or Person not found");
        }
    }

    public Project findByName(String name) {
        return projectRepository.findByProjectName(name);
    }

    public Page<Project> list(Pageable pageable, Specification<Project> filter) {
        return projectRepository.findAll(filter, pageable);
    }
}
