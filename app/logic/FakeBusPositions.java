package logic;

import com.avaje.ebean.Ebean;
import controllers.JMSProducer;
import controllers.StompProducer;
import models.*;
import play.Play;
import play.libs.Akka;
import scala.concurrent.duration.Duration;



import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;


public class FakeBusPositions {

    public HashMap<Long, JMSProducer> jmsTopicHashMap;
    private CoordinateInitializer coordinateInit;

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

    private static List<LineRoutes> lineRoutes = null;


    public FakeBusPositions() {

        Akka.system().scheduler().schedule(Duration.create(1, TimeUnit.SECONDS), Duration.create(1, TimeUnit.SECONDS), new Runnable() {
            @Override
            public void run() {
                try {
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

                    coordinateInit = new CoordinateInitializer();
                    PositionPublish positionPublish = new PositionPublish(coordinateInit);
                    CheckpointPublish checkpointPublish = new CheckpointPublish(coordinateInit);
                    StatusPublish statusPublish = new StatusPublish(coordinateInit);

                    while (true){

                       //busId: 1  lineId:1
                        //Status type, message, text.

                        // vehicleId, lineId, statusId, lat, lon

                        ctrLine1_1 =
                                createVehicleOnMap(1L,1L,getPosLine1,ctrLine1_1);

                        //busId:2, lineId:1
                        ctrLine1_2 =
                                createVehicleOnMap(2L,1L,getPosLine1,ctrLine1_2);

                        //busId:3, lineId:1
                        ctrLine1_3 =
                                createVehicleOnMap(3L,1L,getPosLine1,ctrLine1_3);

                        //busId:4, lineId:1
                        ctrLine1_4 =
                                createVehicleOnMap(4L,1L,getPosLine1,ctrLine1_4);

                        //busId:5, lineId:1
                        ctrLine1_5 =
                                createVehicleOnMap(5L,1L,getPosLine1,ctrLine1_5);

                        //busId:6, lineId:2
                        ctrLine1Reverse_1 =
                                createVehicleOnMap(6L,2L,getPosLine1Reverse,ctrLine1Reverse_1);

                        //busId:7, lineId:2
                        ctrLine1Reverse_2 =
                                createVehicleOnMap(7L,2L,getPosLine1Reverse,ctrLine1Reverse_2);


                        //busId:8, lineId:2
                        ctrLine1Reverse_3 =
                                createVehicleOnMap(8L,2L,getPosLine1Reverse,ctrLine1Reverse_3);

                        //busId:9, lineId:3
                        ctrLine2_1 =
                                createVehicleOnMap(9L,3L,getPosLine2,ctrLine2_1);

                        //busId:10, lineId:3
                        ctrLine2_2 =
                                createVehicleOnMap(10L,3L,getPosLine2,ctrLine2_2);

                        //busId:11, lineId:4
                        ctrLine2Reverse_1 =
                                createVehicleOnMap(11L,4L,getPosLine2Reverse,ctrLine2Reverse_1);

                        //busId:12, lineId:4
                        ctrLine2Reverse_2 =
                                createVehicleOnMap(12L,4L,getPosLine2Reverse,ctrLine2Reverse_2);


                    Thread.sleep(500);


                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

        }, Akka.system().dispatcher());
    }

    private int createVehicleOnMap(Long lineId,
                                   Long vehicleId,
                                   Coordinates posList,
                                   int counter) {


        if (counter == posList.getList().size()){
            coordinateInit.setPositionParameters(lineId, vehicleId, 4L, null,null);
            //counter = initCounter;
            counter =0;

        }
        else
        {
            BigDecimal vehicleLat = posList.getIndex(counter);
            BigDecimal vehicleLon = posList.getIndex(counter + 1);
            fakeStatus(lineId, vehicleId, counter );
            coordinateInit.setPositionParameters(lineId, vehicleId, null, vehicleLat, vehicleLon);
            counter=counter+2;
        }

        return counter;

    }

    private void fakeStatus(Long lineId, Long vehicleId, int counter){
        if ((counter % 300 == 0 && (vehicleId == 1L )|| (counter % 200 == 0 && vehicleId == 7L))){
            coordinateInit.setPositionParameters(lineId,vehicleId,3L,null,null);

            // createStatusMessage(lineId, vehicleId, errorType, errorText, errorDescription);
            // vehicle.setLastStatus(errorType);

        }
        else if ((counter % 200 == 0 && vehicleId == 1L ) || (counter % 300 == 0 && vehicleId == 7L) ){
            coordinateInit.setPositionParameters(lineId,vehicleId,1L,null,null);
            // vehicle.setLastStatus(warningType);
        }
        else if (counter % 100 == 0 && (vehicleId == 1L || vehicleId == 7L)){
            coordinateInit.setPositionParameters(lineId,vehicleId,2L,null,null);
            // vehicle.setLastStatus(okType);

        }
    }
}
