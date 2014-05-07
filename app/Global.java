import com.avaje.ebean.Ebean;
import controllers.JMSProducer;
import logic.FakeBusPositions;
import models.Position;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Yaml;

import java.util.List;
import java.util.Map;

public class Global extends GlobalSettings{

    private FakeBusPositions fakeBusPositions;

    public void onStart(Application app){
        Logger.info("Application has started");
        InitialData.insert(app);

        fakeBusPositions = new FakeBusPositions();
    }

    public void onStop(Application app){
        Logger.info("Application has stopped");
       // fakeBusPositions.jmsProducer.close();
        for (JMSProducer jmsProd :fakeBusPositions.jmsTopicHashMap.values()) {
            jmsProd.close();
        }
    }


    static class InitialData {

        public static void insert(Application application){
            if (Ebean.find(Position.class).findRowCount() == 0){

                Map<String, List<Object>> all = (Map<String,List<Object>>) Yaml.load("initial-data.yml");

                Ebean.save(all.get("lineroutes"));

                // insert dummy bus positions
                Ebean.save(all.get("position"));

                Ebean.save(all.get("checkpoint"));

                Ebean.save(all.get("linecheckpoint"));

            }
        }

    }

}
