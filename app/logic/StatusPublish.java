package logic;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

/**
 * Created by firkav on 2014-06-03.
 */
public class StatusPublish implements Observer, PublishElement{
    private CoordinateInitializer coorInit;
    private Long vehicleId;
    private Long lineId;
    private Long statusId;
    private String statusType;
    private String statusText;
    private String description;
    private Status status;

    private LinkedHashMap<String, Object> statusHashMap;
    private String statusJson;

    private static final String messageType = "status";
    private static final Long offStatusId = 4L;

    public StatusPublish(CoordinateInitializer coorInit){
        this.coorInit = coorInit;
        coorInit.registerObserver(this);
        status = new Status();

    }

    public void update(Long vehicleId,Long lineId, Long statusId, BigDecimal lat, BigDecimal lon){

        if (statusId != null){
            this.vehicleId = vehicleId;
            this.lineId = lineId;
            this.statusId = statusId;
            this.statusType = status.getStatusType(this.statusId);
            this.statusText = status.getStatusText(this.statusId);
            this.description = status.getDescription(this.statusId);

            // If the vehicle has off status then empty passedCheckpoints set.
            if (statusId == offStatusId){
                Checkpoint checkpoint = Checkpoint.getInstance();
                checkpoint.emptyPassedCheckpoints(vehicleId);
                publish();
            }
           // No need to send status for now.
           // publish();
        }

    }

    public void publish(){
        try{
            ObjectMapper statusMapper = new ObjectMapper();
            statusHashMap = new LinkedHashMap<String, Object>();
            statusHashMap.put("messageType", messageType);
            statusHashMap.put("lineId", this.lineId);
            statusHashMap.put("busId", this.vehicleId);
            statusHashMap.put("statusType", this.statusType);
            statusHashMap.put("message", this.statusText);
            statusHashMap.put("text",this.description);
            statusJson = statusMapper.writeValueAsString(statusHashMap);

            System.out.println(statusJson);
            TopicHandler topicHandler = TopicHandler.getInstance();
            topicHandler.createTopicsForCheckpoints();
            topicHandler.produceMessage(lineId,statusJson);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
