package hse.activity.tracker.services;

import hse.activity.tracker.data.session.ActiveSession;
import hse.activity.tracker.data.session.SessionRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * todo Document type SessionService
 */
@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void save(ActiveSession activeSession) {
        sessionRepository.save(activeSession);
    }

    public List<Timestamp> findAllTimestampsByUserId(Long id) {
        return sessionRepository.findAllSessionById(id);
    }

    public List<ActiveSession> findAll() {
        return sessionRepository.findAll();
    }

    public List<ActiveSession> findAllByUserId(Long id) {
        return sessionRepository.findByUserId(id);
    }

    public Object getOverallTime(Long userId) {
        return sessionRepository.getOverallTime(userId);
    }

    public Object middleTime(Long userId) {
        return sessionRepository.middleActivity(userId);
    }

    public Object sessionCount(Long userId) {
        return sessionRepository.sessionCount(userId);
    }
}
