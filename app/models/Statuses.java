package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by firkav on 2014-05-15.
 */

@Entity
@Table(name="status")
public class Statuses extends Model {

    @Id
    @Column(name="id")
    public Long id;

    @Column(name="status_type")
    public String statusType;

    @Column(name="status_text")
    public String statusText;

    @Column(name="description")
    public String description;

    public static Finder<Long, Statuses> find = new Finder(Long.class, Statuses.class);

    public static Statuses getStatusById(Long statusId){
        return find.byId(statusId);
    }

    public static List<Statuses> getStatusByType(String type) {
        return find.where().eq("status_type",type).findList();
    }

}
