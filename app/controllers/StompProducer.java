package controllers;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.transport.stomp.Stomp;
import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;

import javax.jms.*;
import java.io.IOException;

public class StompProducer {

    private String topic;

    private static StompConnection connection;

    //TODO close connection on application stop

    public StompProducer(String url, int port, String topic){
        connection = new StompConnection();
        try {
            connection.open(url, port);
            connection.connect("Test", "Test");
            StompFrame connect = connection.receive();
            if (!connect.getAction().equals(Stomp.Responses.CONNECTED)){
                throw new Exception("Not connected");
            }
        }
        catch (Exception e){

        }
        this.topic = topic;
    }

    public void produce(String textMessage) {
        try {
            connection.send(topic, textMessage);
        }
        catch(Exception exp) {
            //TODO ERROR HANDLING
        }
    }

    public void close(){
        try {
            connection.close();
        }
        catch(IOException exp) {

            //TODO ERROR HANDLING
        }
    }
}
