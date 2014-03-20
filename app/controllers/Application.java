package controllers;

import models.BusPosition;
import play.*;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

import java.util.List;

public class Application extends Controller {

    public static JMSProducer jmsProducer;

    public static Result index() {
        BusPosition busPosition = new BusPosition();
        busPosition = busPosition.findBusPosition(1L);
        return ok(index.render("Busslinje: " + busPosition.lineId));
    }

    public static Result getAllLines(){
        List<BusPosition> allBuses = BusPosition.getAllBuses();
        return ok(Json.toJson(allBuses));
    }

    public static Result jmsTest(){
        return ok(jmstest.render());
    }

}
