package in.co.dineout.xoppin.dineoutcollection.model;

import java.io.Serializable;

/**
 * Created by suraj on 04/02/16.
 */
public class TimingModel implements Serializable {
    private static final long serialVersionUID = -7141378558786661652L;

    private String state;
    private String[] st_time;

//    public String getDay() {
//        return day;
//    }

//    public void setDay(String day) {
//        this.day = day;
//    }

    private String[] en_time;
//    private String day;

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String[] getSt_time() {
        return this.st_time;
    }

    public void setSt_time(String[] st_time) {
        this.st_time = st_time;
    }

    public String[] getEn_time() {
        return this.en_time;
    }

    public void setEn_time(String[] en_time) {
        this.en_time = en_time;
    }
}
