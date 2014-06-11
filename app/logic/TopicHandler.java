package logic;

import controllers.JMSProducer;
import models.LineRoutes;
import play.Play;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by firkav on 2014-05-20.
 *
 * TopicHandler supplies functions to produce a JMS message and to create JMS topics for checkpoints.
 */
public class TopicHandler {

    private static TopicHandler topicHandlerInstance;
    private static HashMap<Long, JMSProducer> lineHashMap;
    private JMSProducer singleTopic;
    private HashMap<Long, JMSProducer> lineTopicHashMap ;


    private static final String hostValue = Play.application().configuration().getString("activemq.host");
    private static final int jmsPortValue = Play.application().configuration().getInt("activemq.jmsPort");
    private static final int stompPortValue = Play.application().configuration().getInt("activemq.stompPort");

    private TopicHandler(){
        lineTopicHashMap = new LinkedHashMap<Long, JMSProducer>();
    }

    public static synchronized TopicHandler getInstance(){
        if (topicHandlerInstance == null){
            topicHandlerInstance = new TopicHandler();
        }
        return topicHandlerInstance;
    }

    public void createTopicsForCheckpoints(){

        List<LineRoutes> listLines = LineRoutes.getAllLines();
        int lineSize = listLines.size();

        for (int i =0; i<lineSize;i++){
            if (!lineTopicHashMap.containsKey(listLines.get(i).lineCode)) {
                System.out.println("Get:" + listLines.get(i).lineTopic);
                lineTopicHashMap.put(listLines.get(i).lineCode,
                        new JMSProducer("tcp://" + hostValue + ":" + jmsPortValue, listLines.get(i).lineTopic));
            }

           lineHashMap = lineTopicHashMap;

        }

    }

   public void produceMessage(Long lineId, String msg){
        lineHashMap.get(LineRoutes.findLine(lineId).lineCode).produce(msg);
   }

}

