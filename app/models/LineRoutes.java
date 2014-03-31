package models;


import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="Line_routes")
public class LineRoutes extends Model {

    @Id
    @Column(name="line_id")
    public Long lineId;

    //Jag har lagt till den för att testa /Firat
    @Column(name="line_topic")
    public String lineTopic;

    @Column(name="line_type")
    public String lineType;

    @Column(name="description")
    public String description;

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

}


