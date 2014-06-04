package models;


import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Line_routes")
public class LineRoutes extends Model {

    @Id
    @Column(name="line_id")
    public Long lineId;

    @Column(name="line_code")
    public Long lineCode;

    @Column(name="line_topic")
    public String lineTopic;

    @Column(name="line_name")
    public String lineName;

    @Column(name="line_type")
    public String lineType;

    @Column (name="route_from")
    public String routeFrom;

    @Column (name="route_to")
    public String routeTo;

    //@OneToMany()
   // List<LineCheckpoints> lineCheckpoints;


    public static Finder<Long, LineRoutes> find = new Finder(
            Long.class, LineRoutes.class
    );

    public static LineRoutes findLine(Long lineId){
        return  find.byId(lineId);
    }

    public static List<LineRoutes> findAllBuses(){
        List<LineRoutes> lineList = find.where()
                .eq("line_type", "Buss")
                .findList();
        return lineList;
    }

    public static List<LineRoutes> getAllLines(){
        return find.all();
    }

}


