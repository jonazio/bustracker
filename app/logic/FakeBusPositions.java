package logic;

import controllers.JMSProducer;
import controllers.StompProducer;
import models.LineRoutes;
import models.Position;
import play.Play;
import play.libs.Akka;
import scala.concurrent.duration.Duration;

import play.api.libs.json.Json;

import java.math.BigDecimal;
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
import java.util.HashMap;
import java.util.ArrayList;


public class FakeBusPositions {

    public int currentPosition = 1;
    public  List<JMSProducer>  jmsProducer ;
    private StompProducer stompProducer;
    private static List<Position> busPositions = null;
    private static int listIndex = 0;
    private static boolean backwards = false;
    private HashMap<Long, JMSProducer> lineTopicHashMap;

    //Line routes list
    private static List<LineRoutes> lineRoutes = null;


    private StatusJson status;


    public FakeBusPositions() {

        final String hostValue = Play.application().configuration().getString("activemq.host");
        final int jmsPortValue = Play.application().configuration().getInt("activemq.jmsPort");
        final int stompPortValue = Play.application().configuration().getInt("activemq.stompPort");


        //jmsProducer = new JMSProducer("tcp://localhost:61616", "BusTopic");
       // jmsProducer = new JMSProducer("tcp://" + hostValue + ":" + jmsPortValue, "BusTopic");
        int lineSize = getAllBusLines().size();
        jmsProducer = new ArrayList<JMSProducer>();
        lineTopicHashMap = new HashMap<Long, JMSProducer>();

        for (int i =0; i<lineSize;i++){
            System.out.println("Get:   "+getAllBusLines().get(i).lineTopic);
            jmsProducer.add(i, new JMSProducer("tcp://" + hostValue + ":" + jmsPortValue, getAllBusLines().get(i).lineTopic));
            lineTopicHashMap.put(getAllBusLines().get(i).lineId,jmsProducer.get(i));
        }

        //stompProducer = new StompProducer("localhost", 61613, "/topic/StompBusTopic");
        stompProducer = new StompProducer(hostValue, stompPortValue, "/topic/StompBusTopic");




        Akka.system().scheduler().schedule(Duration.create(1, TimeUnit.SECONDS), Duration.create(1, TimeUnit.SECONDS), new Runnable() {
            @Override
            public void run() {
                try {


                    Position busPosition = getNextPosition();


                    //Create GpsXmlReader object to be able to read gpx file
                    GpsXmlReader gpx = new GpsXmlReader();

                    //gpx positions are stored in a arraylist. Loop through the list and craete JSON messages.
                    ArrayList<BigDecimal> getPos = gpx.getPositions();






                    int checkStatus = 0;
                    for (int i=0,j=getPos.size()-1; i<getPos.size();i=i+2, j=j-2){



                         BusJson  bus = new BusJson("position",
                                               busPosition.lineId,
                                               busPosition.vehicleId,
                                               getPos.get(i),
                                               getPos.get(i+1));
                           String posJson = bus.createBusJSON();

                        BusJson bus2 = new BusJson("position",
                                busPosition.lineId,
                                Long.valueOf(2),
                                getPos.get(j-1),
                                getPos.get(j));
                        String posJson2 = bus2.createBusJSON();

                        BusJson bus3 = new BusJson("position",
                                                    Long.valueOf(2),
                                                    Long.valueOf(3),
                                                    getPos.get(i+50),
                                                    getPos.get(i+51));
                        String posJson3 = bus3.createBusJSON();


                        if ( i % 100 == 0 && checkStatus ==0 ){
                            status = new StatusJson("status",
                                    busPosition.lineId,
                                    busPosition.vehicleId,
                                    "ok",
                                    "Bus is running as expected",
                                    "A text for bus status");
                            String statJson = status.createStatusJSON();
                            checkStatus=1;
                        } else if ( i % 100 == 0 && checkStatus ==1 ){
                            status = new StatusJson("status",
                                    busPosition.lineId,
                                    busPosition.vehicleId,
                                    "warning",
                                    "Bus has problems.",
                                    "A text for bus warning");
                            checkStatus=2;
                        }
                        else if ( i % 100 == 0 && checkStatus ==2 ){
                            status = new StatusJson("status",
                                    busPosition.lineId,
                                    busPosition.vehicleId,
                                    "error",
                                    "Bus is not running.",
                                    "A text for bus error");

                            checkStatus=0;
                        }
                        String statJson = status.createStatusJSON();

                        lineTopicHashMap.get(bus.getLineId()).produce(posJson);
                        lineTopicHashMap.get(bus2.getLineId()).produce(posJson2);
                        lineTopicHashMap.get(bus3.getLineId()).produce(posJson3);
                        lineTopicHashMap.get(status.getLineId()).produce(statJson);






                          /*
                           status = new StatusJson("status",
                                                    busPosition.lineId,
                                                    busPosition.vehicleId,
                                                    "ok",
                                                    "Bus is running as expected",
                                                     "A text for bus status");
                           String statJson = status.createStatusJSON();*/


                            // Put JSON messages to activeMq
                           // jmsProducer.produce(posJson);
                            //jmsProducer.produce(statJson);
                            //Thread.sleep(100);
                            //jmsProducer.produce(posJson2);

                            //jmsProducer.produce("BusId: " + busPosition.vehicleId + " pos X: " + busPosition.gpsX + " pos Y: " + busPosition.gpsY);

                            stompProducer.produce("StompTest");
                            // System.out.println("SeqId " + busPosition.seqId + " BusId: " + busPosition.vehicleId + " pos X: " + busPosition.gpsX + " pos Y: " + busPosition.gpsY);
                            System.out.println(posJson);
                            System.out.println(posJson2);
                            System.out.println(posJson3);
                            System.out.println(statJson);

                            Thread.sleep(100);
                 }

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

        }, Akka.system().dispatcher());
    }

    private static List<LineRoutes> getAllBusLines(){
        lineRoutes = LineRoutes.findAllBuses();
        return lineRoutes;
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
