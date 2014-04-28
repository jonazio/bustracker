package controllers;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSProducer {

    private Topic topic;

   // private static Connection connection;
   // private static Session session;
    private ConnectionFactory connectionFactory;
 //   private static MessageProducer producer;

    private Connection connection;
    private Session session;
    private MessageProducer producer;

    //TODO close connection on application stop

    public JMSProducer(String url, String topic){
        connectionFactory = new ActiveMQConnectionFactory(url);
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            this.topic = session.createTopic(topic);
            producer = session.createProducer(this.topic);
        }
        catch(JMSException exp) {
            System.out.println(exp.getMessage());
        }
    }

    public void produce(String textMessage) {

        try {
            TextMessage msg = session.createTextMessage();
            msg.setText(textMessage);
            producer.send(msg);
        }
        catch(JMSException exp) {
        }
    }

    // doesn't seem to work ...
    public void close(){
        try {
            session.close();
            connection.close();
        }
        catch(JMSException exp) {
        }
    }
}