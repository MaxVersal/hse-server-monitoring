package hse.activity.tracker.data.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface SessionRepository
    extends JpaRepository<ActiveSession, Long>,
    JpaSpecificationExecutor<ActiveSession> {

    @Query("select s.time from ActiveSession s where s.person.id = ?1")
    List<Timestamp> findAllSessionById(Long id);

    @Query("select s from ActiveSession s where s.person.id = ?1")
    List<ActiveSession> findByUserId(Long id);

    @Query(nativeQuery = true, value =
        """
            SELECT SUM(EXTRACT(SECOND FROM time)) AS total_time
            FROM active_session
            WHERE user_id =?1
              """)
    Object getOverallTime(Long userId);

    @Query("SELECT COUNT(*)" +
        "FROM ActiveSession s WHERE s.person.id=?1")
    Object sessionCount(Long userId);

    @Query("SELECT SUM(EXTRACT(minute FROM s.time)) FROM SamplePerson p" +
        " JOIN ActiveSession s ON p.id = s.person.id" +
        " where p.id=?1")
    Object middleActivity(Long userId);

}
