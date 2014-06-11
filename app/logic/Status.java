package logic;

import models.Statuses;

/**
 * Created by firkav on 2014-06-03.
 *
 * This class is used to maintain status related data.
 */
public class Status {
    private Long statusId;
    private String statusType;
    private String statusText;
    private String description;

    public String getStatusType(Long statusId) {
        this.statusType = Statuses.getStatusById(statusId).statusType;
        return statusType;
    }

    public String getStatusText(Long statusId) {
        this.statusText = Statuses.getStatusById(statusId).statusText;
        return statusText;
    }

    public String getDescription(Long statusId) {
        this.description = Statuses.getStatusById(statusId).description;
        return description;
    }




    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
}
