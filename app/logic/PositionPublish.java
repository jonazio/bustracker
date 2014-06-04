package logic;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

/**
 * Created by firkav on 2014-06-02.
 */
public class PositionPublish implements Observer, PublishElement {
    private CoordinateInitializer coorInit;
    private Long vehicleId;
    private Long lineId;
    private BigDecimal lat;
    private BigDecimal lon;
    private LinkedHashMap<String, Object> posHashMap;
    private String posJson;

    private static final String messageType = "position";

    public PositionPublish(CoordinateInitializer coorInit){
       this.coorInit = coorInit;
       coorInit.registerObserver(this);
    }



    public void update(Long vehicleId, Long lineId, Long statusId, BigDecimal lat, BigDecimal lon){
        if (lat != null || lon!=null) {
            this.vehicleId = vehicleId;
            this.lineId = lineId;
            this.lat = lat;
            this.lon = lon;
            publish();
        }



    }

    public void publish(){
        try{
                ObjectMapper posMapper = new ObjectMapper();
                posHashMap = new LinkedHashMap<String, Object>();
                posHashMap.put("messageType", messageType);
                posHashMap.put("lineId", this.lineId);
                posHashMap.put("busId", this.vehicleId);
                posHashMap.put("lat", this.lat);
                posHashMap.put("lon", this.lon);
                posJson = posMapper.writeValueAsString(posHashMap);

                System.out.println(posJson);
                TopicHandler topicHandler = TopicHandler.getInstance();
                topicHandler.createTopicsForCheckpoints();
                topicHandler.produceMessage(lineId,posJson);

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }


}
