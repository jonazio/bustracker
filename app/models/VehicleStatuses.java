package models;

import javax.persistence.*;
import play.db.ebean.Model;

import java.util.Date;
import java.util.List;


/**
 * Created by firkav on 2014-05-15.
 */
@Entity
@Table(name="vehicle_status")
public class VehicleStatuses extends Model {

    @Id
    @Column(name="id")
    public Long id;

    @Column(name="vehicle_id")
    public Long vehicleId;

    @Column(name="status_id")
    public Long statusId;

    @Column(name="line_id")
    public Long lineId;

    @Column(name="timestamp")
    public Date timestamp;

    public static Finder<Long, VehicleStatuses> find = new Finder(
            Long.class, VehicleStatuses.class);


    public static List<VehicleStatuses> getStatusByVehicleId(Long vehicleId){

        List<VehicleStatuses> listVehicleStatus = find.where()
                .eq("vehicle_id",vehicleId)
                .orderBy("timestamp desc")
                .findList();
        return listVehicleStatus;

    }

    public static VehicleStatuses getLastStatusForVehicle(Long vehicleId){
        return find.where().eq("vehicle_id", vehicleId).orderBy("timestamp desc").findUnique();
    }






}
