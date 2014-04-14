package logic;

import controllers.JMSProducer;
import controllers.StompProducer;
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
    public JMSProducer jmsProducer;
    private StompProducer stompProducer;
    private static List<Position> busPositions = null;
    private static int listIndex = 0;
    private static boolean backwards = false;


    private StatusJson status;


    public FakeBusPositions() {

        final String hostValue = Play.application().configuration().getString("activemq.host");
        final int jmsPortValue = Play.application().configuration().getInt("activemq.jmsPort");
        final int stompPortValue = Play.application().configuration().getInt("activemq.stompPort");

        //jmsProducer = new JMSProducer("tcp://localhost:61616", "BusTopic");
        jmsProducer = new JMSProducer("tcp://" + hostValue + ":" + jmsPortValue, "BusTopic");

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



                           status = new StatusJson("status",
                                                    busPosition.lineId,
                                                    busPosition.vehicleId,
                                                    "ok",
                                                    "Bus is running as expected",
                                                     "A text for bus status");
                           String statJson = status.createStatusJSON();



                            // Put JSON messages to activeMq
                            jmsProducer.produce(posJson);
                            jmsProducer.produce(statJson);
                            Thread.sleep(100);
                            jmsProducer.produce(posJson2);

                            //jmsProducer.produce("BusId: " + busPosition.vehicleId + " pos X: " + busPosition.gpsX + " pos Y: " + busPosition.gpsY);

                            stompProducer.produce("StompTest");
                            // System.out.println("SeqId " + busPosition.seqId + " BusId: " + busPosition.vehicleId + " pos X: " + busPosition.gpsX + " pos Y: " + busPosition.gpsY);
                            System.out.println(posJson);
                            System.out.println(statJson);
                            System.out.println(posJson2);
                            Thread.sleep(100);
                 }

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
