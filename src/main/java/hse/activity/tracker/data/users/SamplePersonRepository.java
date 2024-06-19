package hse.activity.tracker.data.users;

import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SamplePersonRepository
    extends
    JpaRepository<SamplePerson, Long>,
    JpaSpecificationExecutor<SamplePerson> {
    SamplePerson findByEmail(@Email String email);
}
