package models;


import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="BusPosition")
public class BusPosition extends Model{

    @Id
    @Column(name="seq_id")
    public Long seqId;

    @Column(name="bus_id")
    public Long busId;

    @Column(name="line_id")
    public Long lineId;

    @Column(name="gps_x")
    public Long gpsX;

    @Column(name="gps_y")
    public Long gpsY;

    @Column(name="timestamp")
    public Date timestamp;

    public static Finder<Long, BusPosition> find = new Finder(
            Long.class, BusPosition.class
    );

    public static BusPosition findBusPosition(Long seqId){
        return  find.byId(seqId);
        }

    public static List<BusPosition> getAllBuses(){
        return find.all();
    }

}
