package logic;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

/**
 * Created by firkav on 2014-06-02.
 */
public class CheckpointPublish implements Observer, PublishElement{
    private CoordinateInitializer coorInit;
    private Long vehicleId;
    private Long lineId;
    private BigDecimal lat;
    private BigDecimal lon;
    private Long checkpointId;
    private LinkedHashMap<String, Object> checkpointHashMap;
    private Checkpoint checkpoint;
    private String checkpointJson;

    private static final String messageType = "status";
    private static final String statusType = "checkpoint";

    public CheckpointPublish(CoordinateInitializer coorInit){
        this.coorInit = coorInit;
        coorInit.registerObserver(this);


        checkpoint = Checkpoint.getInstance();

    }

    public void update(Long vehicleId, Long lineId, Long statusId, BigDecimal lat, BigDecimal lon){
        if (lat != null || lon!=null) {
            this.vehicleId = vehicleId;
            this.lineId = lineId;
            this.lat = lat;
            this.lon = lon;
            if (checkpoint.vehicleOnCheckpoint(lineId, vehicleId,lat,lon)){
                setCheckpointId(checkpoint.getCheckpointId());
                publish();
            }
        }


    }

    public void publish(){
        try{
            ObjectMapper checkpointMapper = new ObjectMapper();
            checkpointHashMap = new LinkedHashMap<String, Object>();
            checkpointHashMap.put("messageType", messageType);
            checkpointHashMap.put("lineId", this.lineId);
            checkpointHashMap.put("busId", this.vehicleId);
            checkpointHashMap.put("statusType", statusType);
            checkpointHashMap.put("message", this.checkpointId.toString());
            checkpointHashMap.put("text", "");
            checkpointJson = checkpointMapper.writeValueAsString(checkpointHashMap);

            System.out.println(checkpointJson);
            TopicHandler topicHandler = TopicHandler.getInstance();
            topicHandler.createTopicsForCheckpoints();
            topicHandler.produceMessage(lineId,checkpointJson);
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public Long getCheckpointId() {
        return checkpointId;
    }

    public void setCheckpointId(Long checkpointId) {
        this.checkpointId = checkpointId;
    }
}
