package logic;

import controllers.JMSProducer;
import controllers.StompProducer;
import models.LineRoutes;
import models.Position;
import play.Play;
import play.libs.Akka;
import scala.concurrent.duration.Duration;



import java.math.BigDecimal;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.ArrayList;



public class FakeBusPositions {

    public int currentPosition = 1;
    private StompProducer stompProducer;
    private static List<Position> busPositions = null;
    private static int listIndex = 0;
    private static boolean backwards = false;
    public HashMap<Long, JMSProducer> jmsTopicHashMap;

    //Line routes list
    private static List<LineRoutes> lineRoutes = null;

    private static final String hostValue = Play.application().configuration().getString("activemq.host");
    private static final int jmsPortValue = Play.application().configuration().getInt("activemq.jmsPort");
    private static final int stompPortValue = Play.application().configuration().getInt("activemq.stompPort");


    private StatusJson status;


    public FakeBusPositions() {

        //final String hostValue = Play.application().configuration().getString("activemq.host");
        //final int jmsPortValue = Play.application().configuration().getInt("activemq.jmsPort");
        //final int stompPortValue = Play.application().configuration().getInt("activemq.stompPort");

      /*  int lineSize = getAllBusLines().size();
        lineTopicHashMap = new LinkedHashMap<Long, JMSProducer>();

        for (int i =0; i<lineSize;i++){
            System.out.println("Get:   "+getAllBusLines().get(i).lineTopic);
            lineTopicHashMap.put(getAllBusLines().get(i).lineId,
                    new JMSProducer("tcp://" + hostValue + ":" + jmsPortValue, getAllBusLines().get(i).lineTopic));

        }*/
        jmsTopicHashMap=createAllTopics();


        //stompProducer = new StompProducer("localhost", 61613, "/topic/StompBusTopic");
        stompProducer = new StompProducer(hostValue, stompPortValue, "/topic/StompBusTopic");




        Akka.system().scheduler().schedule(Duration.create(1, TimeUnit.SECONDS), Duration.create(1, TimeUnit.SECONDS), new Runnable() {
            @Override
            public void run() {
                try {

                    Position busPosition = getNextPosition();

                    //Create GpsXmlReader object to be able to read gpx file
                    GpsXmlReader gpxLine1 = new GpsXmlReader("fake_line1.gpx");
                    GpsXmlReader gpxLine2 = new GpsXmlReader("fake_line2.gpx");

                    //gpx positions are stored in a arraylist. Loop through the list and craete JSON messages.
                    ArrayList<BigDecimal> getPosLine1 = gpxLine1.getPositions();
                    ArrayList<BigDecimal> getPosLine2 = gpxLine2.getPositions();

                    int checkStatus = 0;
                    for (int i=0,j=getPosLine1.size()-1; i<getPosLine1.size();i=i+2, j=j-2){



                         BusJson  bus = new BusJson("position",
                                               busPosition.lineId,
                                               busPosition.vehicleId,
                                               getPosLine1.get(i),
                                               getPosLine1.get(i+1));
                           String posJson = bus.createBusJSON();

                        BusJson bus2 = new BusJson("position",
                                busPosition.lineId,
                                Long.valueOf(2),
                                getPosLine1.get(j-1),
                                getPosLine1.get(j));
                        String posJson2 = bus2.createBusJSON();


                        BusJson bus3 = new BusJson("position",
                                                    Long.valueOf(2),
                                                    Long.valueOf(3),
                                                    getPosLine1.get(i+1500),
                                                    getPosLine1.get(i+1501));
                        String posJson3 = bus3.createBusJSON();


                         String posJson4 = null;
                        String posJson5 = null;
                        BusJson bus4 = null;
                        BusJson bus5 = null;

                        if (i <= getPosLine2.size() ) {
                               bus4 = new BusJson("position",
                                    Long.valueOf(3),
                                    Long.valueOf(4),
                                    getPosLine2.get(i),
                                    getPosLine2.get(i + 1));
                            posJson4 = bus4.createBusJSON();
                        }



                        if (i+400 <= getPosLine2.size()) {
                           bus5 = new BusJson("position",
                                    Long.valueOf(3),
                                    Long.valueOf(5),
                                    getPosLine2.get(i + 400),
                                    getPosLine2.get(i + 401));
                            posJson5 = bus5.createBusJSON();
                        }


                        if ( i % 100 == 0 && checkStatus ==0 ){
                            status = new StatusJson("status",
                                    busPosition.lineId,
                                    busPosition.vehicleId,
                                    "ok",
                                    "Bus is running as expected",
                                    "A text for bus status");

                            String statJson = status.createStatusJSON();
                            jmsTopicHashMap.get(status.getLineId()).produce(statJson);
                            System.out.println(statJson);
                            checkStatus=1;
                        } else if ( i % 100 == 0 && checkStatus ==1 ){
                            status = new StatusJson("status",
                                    busPosition.lineId,
                                    busPosition.vehicleId,
                                    "warning",
                                    "Bus has problems.",
                                    "A text for bus warning");

                            String statJson = status.createStatusJSON();
                            jmsTopicHashMap.get(status.getLineId()).produce(statJson);
                            System.out.println(statJson);
                            checkStatus=2;
                        }
                        else if ( i % 100 == 0 && checkStatus ==2 ){
                            status = new StatusJson("status",
                                    busPosition.lineId,
                                    busPosition.vehicleId,
                                    "error",
                                    "Bus is not running.",
                                    "A text for bus error");

                            String statJson = status.createStatusJSON();
                            jmsTopicHashMap.get(status.getLineId()).produce(statJson);
                            System.out.println(statJson);
                            checkStatus=0;

                        }

                        System.out.println((bus.getLineId()));
                        System.out.println((bus2.getLineId()));
                        System.out.println((bus3.getLineId()));



                        System.out.println(jmsTopicHashMap.keySet().toString());

                        jmsTopicHashMap.get(bus.getLineId()).produce(posJson);
                        jmsTopicHashMap.get(bus2.getLineId()).produce(posJson2);
                        jmsTopicHashMap.get(bus3.getLineId()).produce(posJson3);
                        jmsTopicHashMap.get(bus4.getLineId()).produce(posJson4);
                        jmsTopicHashMap.get(bus5.getLineId()).produce(posJson5);
                       // lineTopicHashMap.get(status.getLineId()).produce(statJson);
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
                            System.out.println(posJson4);
                            System.out.println(posJson5);

                            Thread.sleep(700);
                 }

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

        }, Akka.system().dispatcher());
    }

    private HashMap createAllTopics(){

        HashMap<Long, JMSProducer> lineTopicHashMap;
        int lineSize = getAllBusLines().size();
        lineTopicHashMap = new LinkedHashMap<Long, JMSProducer>();

        for (int i =0; i<lineSize;i++){
            System.out.println("Get:   "+getAllBusLines().get(i).lineTopic);
            lineTopicHashMap.put(getAllBusLines().get(i).lineId,
                    new JMSProducer("tcp://" + hostValue + ":" + jmsPortValue, getAllBusLines().get(i).lineTopic));

        }
        return lineTopicHashMap;

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
