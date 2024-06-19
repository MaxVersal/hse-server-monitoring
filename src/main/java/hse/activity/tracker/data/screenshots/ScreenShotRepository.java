package hse.activity.tracker.data.screenshots;

import hse.activity.tracker.data.session.ActiveSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScreenShotRepository extends JpaRepository<ScreenShot, Long>,
    JpaSpecificationExecutor<ScreenShot> {

    @Query("select s from ScreenShot s where s.person.id = ?1")
    List<ScreenShot> getLastScreenshots(Long userId);
}
