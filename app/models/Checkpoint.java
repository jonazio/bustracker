package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;



/**
 * Created by firkav on 2014-04-29.
 */

@Entity
@Table(name="Checkpoint")
public class Checkpoint extends Model{
    @Id
    @Column(name="checkpoint_id")
    public Long checkpointId;

    @Column(name="checkpoint_name")
    public String checkpointName;

    @Column(name="checkpoint_lat")
    public Double checkpointLat;

    @Column(name="checkpoint_lon")
    public Double checkpointLon;

  /*  @OneToMany(cascade=CascadeType.PERSIST)
   // @JoinColumn(name="checkpoint_id")
    public List<LineCheckpoint> lineCheckpoints;*/

    public static Finder<Long, Checkpoint> find = new Finder(
            Long.class, Checkpoint.class
    );

    public static Checkpoint findCheckpoint(Long checkpointId){
        return  find.byId(checkpointId );
    }





}
