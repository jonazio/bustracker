package logic;

import akka.actor.Props;
import controllers.JMSProducer;
import play.*;
import play.libs.Akka;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeUnit.*;


public class FakeBusPositions {

    public int currentPosition = 1;
    JMSProducer jmsProducer;

    public FakeBusPositions(){

        jmsProducer = new JMSProducer("tcp://localhost:61616", "BusTopic");

        Akka.system().scheduler().schedule(Duration.create(1, TimeUnit.SECONDS), Duration.create(1, TimeUnit.SECONDS), new Runnable() {
            @Override
            public void run() {
                jmsProducer.produce("Hej svejs");
            }
        }, Akka.system().dispatcher());
    }

}
