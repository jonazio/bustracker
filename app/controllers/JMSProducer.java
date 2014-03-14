package controllers;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSProducer {
    public void produce(String textMessage) {
        String url = "tcp://localhost:61616";
        ConnectionFactory factory = new ActiveMQConnectionFactory(url);
        try {
            Connection connection = factory.createConnection();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("BusTopic");
            MessageProducer producer = session.createProducer(topic);
            TextMessage msg = session.createTextMessage();
            msg.setText(textMessage);
            producer.send(msg);
        }
        catch(JMSException exp) {
        }
    }
}