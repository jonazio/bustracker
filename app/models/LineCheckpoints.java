package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;



/**
 * Created by firkav on 2014-04-29.
 */


@Entity
@Table(name="Line_Checkpoint")
public class LineCheckpoints extends Model{
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
    public Checkpoints checkpoint;*/

    //@ManyToOne
    //public LineRoutes lineRoute;

    public static Model.Finder<Long, LineCheckpoints> find = new Model.Finder(
            Long.class, LineCheckpoints.class
    );

    public static LineCheckpoints findLineCheckpoint(Long lineCheckpointId){
        return  find.byId(lineCheckpointId);
    }

    public static List<LineCheckpoints> getCheckpointByLineId(Long lineId){
         List<LineCheckpoints> listLineCheckpoint = find.where()
                 .eq("line_id",lineId)
                 .orderBy("checkpoint_seq")
                 .findList();
        return listLineCheckpoint;
    }

    public static List<LineCheckpoints> findAll(){
        return find.all();
    }
}
