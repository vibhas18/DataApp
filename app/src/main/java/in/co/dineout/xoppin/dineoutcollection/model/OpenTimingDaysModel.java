package in.co.dineout.xoppin.dineoutcollection.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by suraj on 04/02/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenTimingDaysModel implements Serializable {
    private static final long serialVersionUID = 8747738709276510849L;


    private TimingModel monday;


    private TimingModel tuesday;


    private TimingModel wednesday;


    private TimingModel thursday;


    private TimingModel friday;


    private TimingModel saturday;


    private TimingModel sunday;



    @JsonProperty("Monday")
    public TimingModel getMonday() {
        return this.monday;
    }

    public void setMonday(TimingModel monday) {
        this.monday = monday;
    }

    @JsonProperty("Tuesday")
    public TimingModel getTuesday() {
        return this.tuesday;
    }

    public void setTuesday(TimingModel tuesday) {
        this.tuesday = tuesday;
    }

    @JsonProperty("Wednesday")
    public TimingModel getWednesday() {
        return this.wednesday;
    }

    public void setWednesday(TimingModel wednesday) {
        this.wednesday = wednesday;
    }

    @JsonProperty("Thursday")
    public TimingModel getThursday() {
        return this.thursday;
    }

    public void setThursday(TimingModel thursday) {
        this.thursday = thursday;
    }

    @JsonProperty("Friday")
    public TimingModel getFriday() {
        return this.friday;
    }

    public void setFriday(TimingModel friday) {
        this.friday = friday;
    }

    @JsonProperty("Saturday")
    public TimingModel getSaturday() {
        return this.saturday;
    }

    public void setSaturday(TimingModel saturday) {
        this.saturday = saturday;
    }

    @JsonProperty("Sunday")
    public TimingModel getSunday() {
        return this.sunday;
    }

    public void setSunday(TimingModel sunday) {
        this.sunday = sunday;
    }
}
