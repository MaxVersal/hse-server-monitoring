package hse.activity.tracker.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import hse.activity.tracker.data.RabbitMessage;
import hse.activity.tracker.data.session.ActiveSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class RabbitListenerService {
    private final static Logger LOGGER = LoggerFactory.getLogger(RabbitListenerService.class);
    private final SessionService sessionService;
    private final ObjectReader objectReader;

    public RabbitListenerService(SessionService sessionRepository) {
        this.sessionService = sessionRepository;
        this.objectReader = new ObjectMapper().readerFor(RabbitMessage.class);
    }

    @RabbitListener(queues = "employer_data")
    public void handleMessage(Message message) {
        System.out.println("Received message: " + new String(message.getBody(), StandardCharsets.UTF_8));
        try {
            RabbitMessage rabbitMessage = objectReader.readValue(new String(message.getBody(), StandardCharsets.UTF_8));
            ActiveSession activeSession = new ActiveSession(rabbitMessage);
            sessionService.save(activeSession);
        } catch (JsonProcessingException e) {
            LOGGER.error("error on parsing : {}", e.getMessage());
        }
    }

    @RabbitListener(queues = "screenshot_data")
    public void handleScreen(Message message) {

    }
}
