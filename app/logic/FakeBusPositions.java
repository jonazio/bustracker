package logic;

import controllers.JMSProducer;
import controllers.StompProducer;
import models.Position;
import play.libs.Akka;
import scala.concurrent.duration.Duration;
import play.api.libs.json.Json;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
//import org.codehaus.jackson.*;
//import org.codehaus.jackson.node.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import  com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class FakeBusPositions {

    public int currentPosition = 1;
    public JMSProducer jmsProducer;
    private StompProducer stompProducer;
    private static List<Position> busPositions = null;
    private static int listIndex = 0;
    private static boolean backwards = false;


    public FakeBusPositions() {

        jmsProducer = new JMSProducer("tcp://localhost:61616", "BusTopic");
        stompProducer = new StompProducer("localhost", 61613, "/topic/StompBusTopic");


        Akka.system().scheduler().schedule(Duration.create(1, TimeUnit.SECONDS), Duration.create(1, TimeUnit.SECONDS), new Runnable() {
            @Override
            public void run() {
                try {
                    Position busPosition = getNextPosition();
                     
                    // Set up ObjectMapper for bus positions to create JSON messages
                    ObjectMapper posMapper = new ObjectMapper();
                    Map<String,LinkedHashMap> posMap  = new LinkedHashMap<String, LinkedHashMap>();
                    LinkedHashMap<String, Long> posHashMap = new LinkedHashMap<String, Long>();
                    posHashMap.put("LineId", busPosition.lineId);
                    posHashMap.put("BusId", busPosition.vehicleId);
                    posHashMap.put("posX", busPosition.gpsX);
                    posHashMap.put("posY", busPosition.gpsY);

                    posMap.put("position",posHashMap);

                    String posJson = posMapper.writeValueAsString(posMap);


                    // Set up ObjectMapper for statuses to create JSON messages
                    ObjectMapper statMapper = new ObjectMapper();
                    Map<String,LinkedHashMap> statMap  = new LinkedHashMap<String, LinkedHashMap>();
                    LinkedHashMap<String, Object> statHashMap = new LinkedHashMap<String, Object>();
                    statHashMap.put("LineId", busPosition.lineId);
                    statHashMap.put("BusId", busPosition.vehicleId);
                    statHashMap.put("statusType", "statusType1");
                    statHashMap.put("message", "message1");
                    statHashMap.put("text","text1");

                    statMap.put("status",statHashMap);

                    String statJson = posMapper.writeValueAsString(statMap);

                    // Put JSON messages to activeMq
                    jmsProducer.produce(posJson);
                    jmsProducer.produce(statJson);

                    //jmsProducer.produce("BusId: " + busPosition.vehicleId + " pos X: " + busPosition.gpsX + " pos Y: " + busPosition.gpsY);

                    stompProducer.produce("StompTest");
                   // System.out.println("SeqId " + busPosition.seqId + " BusId: " + busPosition.vehicleId + " pos X: " + busPosition.gpsX + " pos Y: " + busPosition.gpsY);
                    System.out.println(posJson);
                    System.out.println(statJson);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

        }, Akka.system().dispatcher());
    }

    private static Position getNextPosition() {
        if (busPositions == null) {
            return initList();
        }

        return busPositions.get(nextIndex());
    }

    private static int nextIndex() {
        if (listIndex == busPositions.size() - 1) {
            backwards = true;
            listIndex = busPositions.size() - 2;
            return listIndex;
        }

        if (listIndex == 0 && backwards) {
            backwards = false;
            listIndex++;
            return listIndex;
        }

        if (backwards) {
            listIndex--;
        } else {
            listIndex++;
        }

        return listIndex;
    }

    private static Position initList() {
        busPositions = Position.getAllBuses();
        listIndex = 0;
        backwards = false;
        return busPositions.get(listIndex);
    }


}
