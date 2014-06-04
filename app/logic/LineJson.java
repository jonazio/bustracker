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
    }


