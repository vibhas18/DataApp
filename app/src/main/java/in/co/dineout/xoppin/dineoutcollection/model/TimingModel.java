package in.co.dineout.xoppin.dineoutcollection.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by suraj on 04/02/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimingModel implements Serializable {
    private static final long serialVersionUID = -7141378558786661652L;

    private String state;
    private String st_time;
    private String en_time;

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSt_time() {
        return this.st_time;
    }

    public void setSt_time(String st_time) {
        this.st_time = st_time;
    }

    public String getEn_time() {
        return this.en_time;
    }

    public void setEn_time(String en_time) {
        this.en_time = en_time;
    }
}
