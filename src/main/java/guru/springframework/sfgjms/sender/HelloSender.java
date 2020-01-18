package guru.springframework.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        HelloWorldMessage message = HelloWorldMessage.getMessage("Hello world!");

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {
        Message receivedMessage = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_AND_RCV_QUEUE, session -> {
            try {
                HelloWorldMessage helloMessage = HelloWorldMessage.getMessage("Dorou :)");
                Message message = session.createTextMessage(objectMapper.writeValueAsString(helloMessage));
                message.setStringProperty("_type", "guru.springframework.sfgjms.model.HelloWorldMessage");

                System.out.println(">>> Sending hello");

                return message;
            } catch (JsonProcessingException e) {
                throw new JMSException("boom");
            }
        });

        System.out.println(Objects.requireNonNull(receivedMessage).getBody(String.class));
    }
}
