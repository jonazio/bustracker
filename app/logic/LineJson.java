package logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Checkpoints;
import models.LineCheckpoints;
import models.LineRoutes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by firkav on 2014-05-06.
 */
public class LineJson {

    private Long lineId;
    private String lineCode;
    private String lineTopic;
    private String lineName;
    private String lineType;
    private String routeFrom;
    private String routeTo;

    public static String getAllCheckpoints() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = "";
            String jsonFinal;
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < LineRoutes.getAllLines().size(); i++) {

                LinkedHashMap<String, Object> lineHashMap = new LinkedHashMap<String, Object>();
                List<Object> checkpoints = new ArrayList();

                lineHashMap.put("id", LineRoutes.getAllLines().get(i).lineId);
                lineHashMap.put("lineCode", LineRoutes.getAllLines().get(i).lineCode);
                lineHashMap.put("lineTopic", LineRoutes.getAllLines().get(i).lineTopic);
                lineHashMap.put("lineName", LineRoutes.getAllLines().get(i).lineName);
                lineHashMap.put("routeFrom", LineRoutes.getAllLines().get(i).routeFrom);
                lineHashMap.put("routeTo", LineRoutes.getAllLines().get(i).routeTo);

                // for (LineCheckpoints listCheckpoint : LineCheckpoints.getCheckpointByLineId(LineRoutes.getAllLines().get(i).lineId))
                for (int j = 0; j < LineCheckpoints.getCheckpointByLineId(LineRoutes.getAllLines().get(i).lineId).size(); j++) {
                    LinkedHashMap<String, Object> checkpointHashMap = new LinkedHashMap<String, Object>();
                    Long checkPoint = LineCheckpoints
                            .getCheckpointByLineId(LineRoutes
                                    .getAllLines()
                                    .get(i).lineId)
                            .get(j).checkpointId;
                    checkpointHashMap.put("id", Checkpoints.findCheckpoint(checkPoint).checkpointId);
                    checkpointHashMap.put("name", Checkpoints.findCheckpoint(checkPoint).checkpointName);
                    checkpointHashMap.put("lat", Checkpoints.findCheckpoint(checkPoint).checkpointLat);
                    checkpointHashMap.put("lon", Checkpoints.findCheckpoint(checkPoint).checkpointLon);
                    checkpoints.add(j, checkpointHashMap);
                    //lineHashMap.put("lineStops", checkpointHashMap);


                }

                lineHashMap.put("lineStops", checkpoints);
               // jsonMessage.append(objectMapper.writeValueAsString(lineHashMap));
                jsonMessage = objectMapper.writeValueAsString(lineHashMap);
                strBuilder.append(jsonMessage);
                if (i!=LineRoutes.getAllLines().size() - 1){
                    strBuilder.append(",");
                }

                //return objectMapper.writeValueAsString(lineHashMap);
                // System.out.println(json);
            }

            jsonFinal = "[" + strBuilder.toString() + "]";
            return jsonFinal;

        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }


    }

    public static String initiateVehicleWithLineCode(Long lineCode){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LinkedHashMap<String, Object> initHashMap = new LinkedHashMap<String, Object>();
            LinkedHashMap<String, Object> statusHashMap = new LinkedHashMap<String, Object>();
            String jsonMessage = "";
            String jsonFinal;
            StringBuilder strBuilder = new StringBuilder();

            Checkpoint checkpoint = Checkpoint.getInstance();
                ArrayList vehicleList = Vehicle.getVehiclesForLineCode(lineCode);

                for (int i = 0; i < vehicleList.size(); i++) {
                    Long vehicleId = (Long)vehicleList.get(i);
                   // initHashMap.put("id", vehicleList.get(i));
                    initHashMap.put("id", vehicleId);
                    initHashMap.put("lineId", Vehicle.getLineId(vehicleId));
                    statusHashMap.put("type","ok");
                    statusHashMap.put("message","Bussen är på väg");
                    statusHashMap.put("text","");
                    initHashMap.put("status",statusHashMap);
                    initHashMap.put("checkpointsPassed", checkpoint.getPassedCheckpoints().get(vehicleId));

                    jsonMessage = objectMapper.writeValueAsString(initHashMap);
                    strBuilder.append(jsonMessage);
                    if (i!=vehicleList.size() - 1){
                        strBuilder.append(",");
                    }
                }


            jsonFinal = "[" + strBuilder.toString() + "]";

            return jsonFinal;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return e.toString();
        }

    }

    public static String initiateVehicleWithLineId(Long lineId){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LinkedHashMap<String, Object> initHashMap = new LinkedHashMap<String, Object>();
            LinkedHashMap<String, Object> statusHashMap = new LinkedHashMap<String, Object>();
            String jsonMessage = "";
            String jsonFinal;
            StringBuilder strBuilder = new StringBuilder();

            Checkpoint checkpoint = Checkpoint.getInstance();

            ArrayList vehicleList = Vehicle.getVehiclesForLine(lineId);

            for (int i = 0; i < vehicleList.size(); i++) {
                initHashMap.put("id", vehicleList.get(i));
                initHashMap.put("lineId", lineId);
                statusHashMap.put("type","ok");
                statusHashMap.put("message","Bussen är på väg");
                statusHashMap.put("text","");
                initHashMap.put("status",statusHashMap);
                initHashMap.put("checkpointsPassed", checkpoint.getPassedCheckpoints().get(vehicleList.get(i)));

                jsonMessage = objectMapper.writeValueAsString(initHashMap);
                strBuilder.append(jsonMessage);
                if (i!=vehicleList.size() - 1){
                    strBuilder.append(",");
                }
            }


            jsonFinal = "[" + strBuilder.toString() + "]";

            return jsonFinal;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return e.toString();
        }
    }

  }


