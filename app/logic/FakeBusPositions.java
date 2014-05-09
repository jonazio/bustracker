package logic;

import controllers.JMSProducer;
import controllers.StompProducer;
import models.Checkpoint;
import models.LineCheckpoint;
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
    public HashMap<Long, JMSProducer> jmsTopicHashMap;

    private StompProducer stompProducer;
    private static List<Position> busPositions = null;
    private static int listIndex = 0;
    private static boolean backwards = false;


    private static final int initLine1_1 = 0;
    private static final int initLine1_2 = 1600;
    private static final int initLine1_3 = 3000;
    private static final int initLine1_4 = 3800;
    private static final int initLine1_5 = 4400;
    private static final int initLine1Reverse_1 = 0;
    private static final int initLine1Reverse_2 = 2400;
    private static final int initLine1Reverse_3 = 3400;
    private static final int initLine2_1 = 0;
    private static final int initLine2_2 = 1600;
    private static final int initLine2Reverse_1 = 0;
    private static final int initLine2Reverse_2 = 1400;

    private static final String warningType="warning";
    private static final String okType="ok";
    private static final String errorType="error";
    private static final String offType="off";

    private static final String warningText="Bussen är försenad.";
    private static final String okText="Bussen är på väg.";
    private static final String errorText="Bussen har tekniska problem.";
    private static final String offText="Bus has completed the route.";

    private static final String warningDescription="Köer i trafiken.";
    private static final String okDescription="";
    private static final String errorDescription="Bussen kan tas ur trafik.";
    private static final String offDescription="A text for bus off status";









    /*
    private int counter1 = 0;
    private int counter2 = 0;
    private int counter3 = 0;
    private int counter4 = 0;
    private int counter5 = 50;
    private int counter6 =100;
    */

    //Line routes list
    private static List<LineRoutes> lineRoutes = null;

    private static final String hostValue = Play.application().configuration().getString("activemq.host");
    private static final int jmsPortValue = Play.application().configuration().getInt("activemq.jmsPort");
    private static final int stompPortValue = Play.application().configuration().getInt("activemq.stompPort");


    private StatusJson status;


    public FakeBusPositions() {

        jmsTopicHashMap=createAllTopics();

        stompProducer = new StompProducer(hostValue, stompPortValue, "/topic/StompBusTopic");

        Akka.system().scheduler().schedule(Duration.create(1, TimeUnit.SECONDS), Duration.create(1, TimeUnit.SECONDS), new Runnable() {
            @Override
            public void run() {
                try {

                    Position busPosition = getNextPosition();


                    // LineId:1 coordinates
                    Coordinates getPosLine1 = new Coordinates("line-1-stops.gpx");
                    // LineId:1 reverse coordinates
                    Coordinates getPosLine1Reverse = new Coordinates("line-1-stops-reverse.gpx");
                    // LineId:2 coordinates
                    Coordinates getPosLine2 = new Coordinates("line-2-no-stops.gpx");
                    // LineId:2 revese coordinates
                    Coordinates getPosLine2Reverse = new Coordinates("line-2-no-stops-reverse.gpx");





                    int checkStatus = 0;
                    //System.out.println(LineJson.getAllCheckpoints());


                    int ctrLine1_1  = initLine1_1;
                    int ctrLine1_2  = initLine1_2;
                    int ctrLine1_3 = initLine1_3;
                    int ctrLine1_4 = initLine1_4 ;
                    int ctrLine1_5 = initLine1_5;
                    int ctrLine1Reverse_1 = initLine1Reverse_1;
                    int ctrLine1Reverse_2 = initLine1Reverse_2;
                    int ctrLine1Reverse_3 = initLine1Reverse_3;
                    int ctrLine2_1 = initLine2_1;
                    int ctrLine2_2 = initLine2_2;
                    int ctrLine2Reverse_1 = initLine2Reverse_1;
                    int ctrLine2Reverse_2 = initLine2Reverse_2;


                    while (true){

                       //busId: 1  lineId:1
                        ctrLine1_1 =
                                createVehicleOnMap(1L,1L,getPosLine1,ctrLine1_1);

                        //busId:2, lineId:1
                        ctrLine1_2 =
                                createVehicleOnMap(1L,2L,getPosLine1,ctrLine1_2);

                        //busId:3, lineId:1
                        ctrLine1_3 =
                                createVehicleOnMap(1L,3L,getPosLine1,ctrLine1_3);

                        //busId:4, lineId:1
                        ctrLine1_4 =
                                createVehicleOnMap(1L,4L,getPosLine1,ctrLine1_4);

                        //busId:5, lineId:1
                        ctrLine1_5 =
                                createVehicleOnMap(1L, 5L,getPosLine1,ctrLine1_5);

                        //busId:6, lineId:2
                        ctrLine1Reverse_1 =
                                createVehicleOnMap(2L,6L,getPosLine1Reverse,ctrLine1Reverse_1);

                        //busId:7, lineId:2
                        ctrLine1Reverse_2 =
                                createVehicleOnMap(2L,7L,getPosLine1Reverse,ctrLine1Reverse_2);


                        //busId:8, lineId:2
                        ctrLine1Reverse_3 =
                                createVehicleOnMap(2L,8L,getPosLine1Reverse,ctrLine1Reverse_3);

                        //busId:9, lineId:3
                        ctrLine2_1 =
                                createVehicleOnMap(3L,9L,getPosLine2,ctrLine2_1);

                        //busId:10, lineId:3
                        ctrLine2_2 =
                                createVehicleOnMap(3L,10L,getPosLine2,ctrLine2_2);

                        //busId:11, lineId:4
                        ctrLine2Reverse_1 =
                                createVehicleOnMap(4L,11L,getPosLine2Reverse,ctrLine2Reverse_1);

                        //busId:12, lineId:4
                        ctrLine2Reverse_2 =
                                createVehicleOnMap(4L,12L,getPosLine2Reverse,ctrLine2Reverse_2);


                      Thread.sleep(500);
                     /*   counter6 =
                                createVehicleOnMap(4L,
                                        1L,
                                        getPosLine1.get(counter6),
                                        getPosLine1.get(counter6 + 1),
                                        counter6,
                                        ctr6,
                                        getPosLine1.size());*/

                    }




                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

        }, Akka.system().dispatcher());
    }

    private void createBusStatusMessage(Long lineId, Long vehicleId, int counter){

        if ((counter % 300 == 0 && (vehicleId == 1L )|| (counter % 200 == 0 && vehicleId == 7L))){
            createStatusMessage(lineId, vehicleId, errorType, errorText, errorDescription);

        }
        else if ((counter % 200 == 0 && vehicleId == 1L ) || (counter % 300 == 0 && vehicleId == 7L) ){
            createStatusMessage(lineId, vehicleId, warningType, warningText, warningDescription);

        }
        else if (counter % 100 == 0 && (vehicleId == 1L || vehicleId == 7L)){
            createStatusMessage(lineId, vehicleId, okType, okText, okDescription);

        }

    }



    private int createVehicleOnMap(Long lineId,
                                   Long vehicleId,
                                   Coordinates posList,
                                   int counter) {


        if (counter == posList.getList().size()){
            createStatusMessage(lineId, vehicleId, offType, offText, offDescription);
            //counter = initCounter;
            counter =0;

        }
        else
        {
            BigDecimal vehicleLat = posList.getIndex(counter);
            BigDecimal vehicleLon = posList.getIndex(counter + 1);
            createVehicleCoordinates(lineId,vehicleId, vehicleLat, vehicleLon);
            createBusStatusMessage(lineId, vehicleId, counter);
            counter=counter+2;
        }

        return counter;

    }

    private void createVehicleCoordinates (Long lineId, Long vehicleId, BigDecimal vehicleLat, BigDecimal vehicleLon){

        BusJson bus = new BusJson("position",
                lineId,
                vehicleId,
                vehicleLat,
                vehicleLon
        );

        String posJson = bus.createBusJSON();
        jmsTopicHashMap.get(LineRoutes.findLine(lineId).lineCode).produce(posJson);
        System.out.println(posJson);

    }

    private void createStatusMessage(Long lineId, Long vehicleId, String messagetType, String messageText, String messageDesc){
        StatusJson status = new StatusJson("status",
                lineId,
                vehicleId,
                messagetType,
                messageText,
                messageDesc);
        String statusJson = status.createStatusJSON();
        jmsTopicHashMap.get(LineRoutes.findLine(lineId).lineCode).produce(statusJson);
        System.out.println(statusJson);

    }

    private HashMap createAllTopics(){

        HashMap<Long, JMSProducer> lineTopicHashMap = new LinkedHashMap<Long, JMSProducer>();
        int lineSize = getAllBusLines().size();

        for (int i =0; i<lineSize;i++){
            if (!lineTopicHashMap.containsKey(getAllBusLines().get(i).lineCode)) {
                System.out.println("Get:   " + getAllBusLines().get(i).lineTopic);
                lineTopicHashMap.put(getAllBusLines().get(i).lineCode,
                                      new JMSProducer("tcp://" + hostValue + ":" + jmsPortValue, getAllBusLines().get(i).lineTopic));
            }

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
