package models;

import play.db.ebean.Model;

import javax.persistence.*;


/**
 * Created by firkav on 2014-04-29.
 */

@Entity
@Table(name="Checkpoints")
public class Checkpoints extends Model{
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
    public List<LineCheckpoints> lineCheckpoints;*/

    public static Finder<Long, Checkpoints> find = new Finder(
            Long.class, Checkpoints.class
    );

    public static Checkpoints findCheckpoint(Long checkpointId){
        return  find.byId(checkpointId );
    }





}
