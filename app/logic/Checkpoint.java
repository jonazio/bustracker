package logic;

import models.Checkpoints;
import models.LineCheckpoints;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by firkav on 2014-06-02.
 */
public class Checkpoint {

    private static Checkpoint checkpointInstance;
    private Long checkpointId;
    private String checkpointName;
    private Long checkpointLat;
    private Long checkpointLon;
    private LinkedHashMap<Long,LinkedHashSet<Long>> passedCheckpoints;

    private Checkpoint(){
        passedCheckpoints = new LinkedHashMap<Long, LinkedHashSet<Long>>();
    }

    public static synchronized Checkpoint getInstance(){
        if (checkpointInstance == null){
            checkpointInstance = new Checkpoint();
        }
        return checkpointInstance;
    }

    public  boolean vehicleOnCheckpoint(Long lineId, Long vehicleId, BigDecimal vehicleLat, BigDecimal vehicleLon ){
        boolean result = false;
        for (int i =0;i< LineCheckpoints.getCheckpointByLineId(lineId).size();i++){
            Long checkpointId = LineCheckpoints.getCheckpointByLineId(lineId).get(i).checkpointId;
            if ((vehicleLat.equals( BigDecimal.valueOf(Checkpoints.findCheckpoint(checkpointId).checkpointLat)))
                    && (vehicleLon.equals(BigDecimal.valueOf(Checkpoints.findCheckpoint(checkpointId).checkpointLon)))){

                    if (passedCheckpoints.get(vehicleId) == null){
                        passedCheckpoints.put(vehicleId, new LinkedHashSet<Long>());
                    }
                passedCheckpoints.get(vehicleId).add(checkpointId);
                //System.out.println(passedCheckpoints.get(vehicleId));
                setCheckpointId(checkpointId);
                result = true;
            }

        }
        return result;
    }

    public void emptyPassedCheckpoints(Long vehicleId){
       if (passedCheckpoints.containsKey(vehicleId))
        passedCheckpoints.get(vehicleId).clear();
    }

    public Long getCheckpointId() {
        return checkpointId;
    }

    public void setCheckpointId(Long checkpointId) {
        this.checkpointId = checkpointId;
    }

    public String getCheckpointName() {
        return checkpointName;
    }

    public void setCheckpointName(String checkpointName) {
        this.checkpointName = checkpointName;
    }

    public Long getCheckpointLat() {
        return checkpointLat;
    }

    public void setCheckpointLat(Long checkpointLat) {
        this.checkpointLat = checkpointLat;
    }

    public Long getCheckpointLon() {
        return checkpointLon;
    }

    public void setCheckpointLon(Long checkpointLon) {
        this.checkpointLon = checkpointLon;
    }
}
