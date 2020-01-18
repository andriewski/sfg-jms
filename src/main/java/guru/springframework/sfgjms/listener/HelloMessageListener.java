package guru.springframework.sfgjms.listener;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
@RequiredArgsConstructor
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers, Message message) {
        System.out.println("I got Message!" + helloWorldMessage);

//        throw new RuntimeException("foo");
    }

    @JmsListener(destination = JmsConfig.MY_SEND_AND_RCV_QUEUE)
    public void listenForHello(@Payload HelloWorldMessage helloWorldMessage, Message message) throws JMSException {
        HelloWorldMessage replyMessage = HelloWorldMessage.getMessage(helloWorldMessage.getMessage() + " <<< Nu Dorou!!!");
        System.out.println(">>> Received message!");

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), replyMessage);
    }
}
