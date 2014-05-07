package controllers;

import logic.LineJson;
import models.LineRoutes;
import models.Position;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

import java.util.List;

public class Application extends Controller {

    public static JMSProducer jmsProducer;

    public static Result index() {
        Position busPosition = new Position();
        busPosition = busPosition.findBusPosition(1L);
        return ok(index.render("Busslinje: " + busPosition.lineId));
    }

    public static Result getAllLines(){
        List<LineRoutes> allBuses = LineRoutes.findAllBuses();
        return ok(Json.toJson(allBuses));
    }

    public static Result jmsTest(){
        return ok(jmstest.render());
    }

    public static Result getAllPositions() {
        Position busPosition = new Position();
        busPosition = busPosition.findBusPosition(1L);
        //return ok(index.render("Busslinje: " + busPosition.lineId));
        List<Position> getAllPositionAttributes = Position.getAllBuses();
        return ok(Json.toJson(getAllPositionAttributes));
    }

    public static Result getAllCheckpoints(){
        return ok(LineJson.getAllCheckpoints());
    }

}
