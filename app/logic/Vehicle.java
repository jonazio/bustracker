package logic;

import models.LineRoutes;
import models.VehicleStatuses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by firkav on 2014-06-04.
 */
public class Vehicle {

    public static  Long getLineId(Long vehicleId){
        return VehicleStatuses.getLastStatusForVehicle(vehicleId).lineId;
    }

    public static ArrayList<Long> getVehiclesForLine(Long lineId){

        ArrayList<Long> vehicleIdList = new ArrayList<Long>();
        List<VehicleStatuses> statusList = VehicleStatuses.getStatusByLineId(lineId);
        for (int i = 0; i< statusList.size(); i++ ) {
               vehicleIdList.add(statusList.get(i).vehicleId);
        }
        return vehicleIdList;
    }

    public static ArrayList<Long> getVehiclesForLineCode(Long lineCode){

        ArrayList<Long> vehicleIdList = new ArrayList<Long>();
        List<LineRoutes> lineList = LineRoutes.getLinesForLineCode(lineCode);
        ArrayList<Long> lineIds = new ArrayList<Long>();

        for (int i = 0; i< lineList.size(); i++){
            lineIds.add(lineList.get(i).lineId);
        }

        for (int i=0; i<lineIds.size();i++){
          if (vehicleIdList.isEmpty()) {
              vehicleIdList = getVehiclesForLine(lineIds.get(i));
          }
          else {
              vehicleIdList.addAll(getVehiclesForLine(lineIds.get(i)));
          }

      }

        return vehicleIdList;


    }
}
