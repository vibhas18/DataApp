package in.co.dineout.xoppin.dineoutcollection.model;

import java.io.Serializable;

/**
 * Created by suraj on 06/02/16.
 */
public class AssignedTaskModel implements Serializable {
    private static final long serialVersionUID = -3622467715983260121L;

    private String priority;
    private String status;
    private String profile_name;
    private int r_id = -1;
    private String locality_name;
    private int task_status;
    private String task_details;

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfile_name() {
        return this.profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }

    public int getR_id() {
        return this.r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public String getLocality_name() {
        return this.locality_name;
    }

    public void setLocality_name(String locality_name) {
        this.locality_name = locality_name;
    }

    public int getTask_status() {
        return this.task_status;
    }

    public void setTask_status(int task_status) {
        this.task_status = task_status;
    }

    public String getTask_details() {
        return this.task_details;
    }

    public void setTask_details(String task_details) {
        this.task_details = task_details;
    }
}
