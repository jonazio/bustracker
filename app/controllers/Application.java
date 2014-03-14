package controllers;

import models.BusPosition;
import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static JMSProducer jmsProducer;

    public static Result index() {
        BusPosition busPosition = new BusPosition();
        busPosition = busPosition.findBusPosition(1L);
        return ok(index.render("Busslinje: " + busPosition.lineId));
    }

}
