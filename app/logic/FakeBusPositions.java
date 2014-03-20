package logic;

import akka.actor.Props;
import controllers.JMSProducer;
import models.BusPosition;
import play.*;
import play.libs.Akka;
import scala.concurrent.duration.Duration;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class FakeBusPositions {

    public int currentPosition = 1;
    public JMSProducer jmsProducer;
    private static List<BusPosition> busPositions = null;
    private static int listIndex = 0;
    private static boolean backwards = false;


    public FakeBusPositions() {

        jmsProducer = new JMSProducer("tcp://localhost:61616", "BusTopic");

        Akka.system().scheduler().schedule(Duration.create(1, TimeUnit.SECONDS), Duration.create(1, TimeUnit.SECONDS), new Runnable() {
            @Override
            public void run() {
                BusPosition busPosition = getNextPosition();
                jmsProducer.produce("BusId: " + busPosition.busId + " pos X: " + busPosition.gpsX + " pos Y: " + busPosition.gpsY);
                System.out.println("SeqId " + busPosition.seqId + " BusId: " + busPosition.busId + " pos X: " + busPosition.gpsX + " pos Y: " + busPosition.gpsY);

            }
        }, Akka.system().dispatcher());
    }

    private static BusPosition getNextPosition() {
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

    private static BusPosition initList() {
        busPositions = BusPosition.getAllBuses();
        listIndex = 0;
        backwards = false;
        return busPositions.get(listIndex);
    }


}
