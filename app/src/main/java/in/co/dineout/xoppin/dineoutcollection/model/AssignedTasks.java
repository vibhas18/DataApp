package in.co.dineout.xoppin.dineoutcollection.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by suraj on 06/02/16.
 */
public class AssignedTasks implements Serializable {
    private static final long serialVersionUID = -3663665419525875366L;

    ArrayList<AssignedTaskModel> task_list;

    public ArrayList<AssignedTaskModel> getTask_list() {
        return this.task_list;
    }

    public void setTask_list(ArrayList<AssignedTaskModel> task_list) {
        this.task_list = task_list;
    }
}
