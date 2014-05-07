package models;


import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Positions")
public class Position extends Model{

    @Id
    @Column(name="seq_id")
    public Long seqId;

    @Column(name="vehicle_id")
    public Long vehicleId;

    @Column(name="line_id")
    public Long lineId;

    @Column(name="gps_lat")
    public Long gpsLat;

    @Column(name="gps_lon")
    public Long gpsLon;

    @Column(name="timestamp")
    public Date timestamp;

    public static Finder<Long, Position> find = new Finder(
            Long.class, Position.class
    );

    public static Position findBusPosition(Long seqId){
        return  find.byId(seqId);
    }

    public static List<Position> getAllBuses(){
        return find.all();
    }

}

