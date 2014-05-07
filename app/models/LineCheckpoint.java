package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;



/**
 * Created by firkav on 2014-04-29.
 */


@Entity
@Table(name="Line_Checkpoint")
public class LineCheckpoint extends Model{
    @Id
    @Column(name="line_checkpoint_id")
    public Long lineCheckpointId;

    @Column(name="checkpoint_seq")
    public Long checkpointSeq;


    @Column(name="checkpoint_id")
    public Long checkpointId;

    @Column(name="line_id")
    public Long lineId ;

 /*   @ManyToOne
    //@JoinColumn(name="checkpoint_id",nullable = false)
   //@JoinColumn(name="checkpoint_id", referencedColumnName = "checkpoint_id")
    public Checkpoint checkpoint;*/

    //@ManyToOne
    //public LineRoutes lineRoute;

    public static Model.Finder<Long, LineCheckpoint> find = new Model.Finder(
            Long.class, LineCheckpoint.class
    );

    public static LineCheckpoint findLineCheckpoint(Long lineCheckpointId){
        return  find.byId(lineCheckpointId);
    }

    public static List<LineCheckpoint> getCheckpointByLineId(Long lineId){
         List<LineCheckpoint> listLineCheckpoint = find.where()
                 .eq("line_id",lineId)
                 .orderBy("checkpoint_seq")
                 .findList();
        return listLineCheckpoint;
    }

    public static List<LineCheckpoint> findAll(){
        return find.all();
    }
}
