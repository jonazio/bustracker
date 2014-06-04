package models;

import javax.persistence.*;
import play.db.ebean.Model;

import java.util.List;

/**
 * Created by firkav on 2014-05-15.
 */

@Entity
@Table(name="vehicles")
public class Vehicles extends Model {
    @Id
    @Column(name="id")
    public Long id;

    @Column(name="registration_number")
    public Long registrationNumber;

    public static Finder<Long, Vehicles> find =new Finder(
            Long.class, Vehicles.class
    );

    public static Vehicles findVehicle(Long vehicleId){
        return find.byId(vehicleId);
    }

    public static List<Vehicles> findAllVehicles(){
       List<Vehicles>  vehicleList = find.all();
        return vehicleList;
    }
}
