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

    @JsonProperty("Monday")
    private TimingModel Monday;

    @JsonProperty("Tuesday")
    private TimingModel Tuesday;

    @JsonProperty("Wednesday")
    private TimingModel Wednesday;

    @JsonProperty("Thursday")
    private TimingModel Thursday;

    @JsonProperty("Friday")
    private TimingModel Friday;

    @JsonProperty("Saturday")
    private TimingModel Saturday;

    @JsonProperty("Sunday")
    private TimingModel Sunday;

    public TimingModel getMonday() {
        return this.Monday;
    }

    public void setMonday(TimingModel monday) {
        this.Monday = monday;
    }

    public TimingModel getTuesday() {
        return this.Tuesday;
    }

    public void setTuesday(TimingModel tuesday) {
        this.Tuesday = tuesday;
    }

    public TimingModel getWednesday() {
        return this.Wednesday;
    }

    public void setWednesday(TimingModel wednesday) {
        this.Wednesday = wednesday;
    }

    public TimingModel getThursday() {
        return this.Thursday;
    }

    public void setThursday(TimingModel thursday) {
        this.Thursday = thursday;
    }

    public TimingModel getFriday() {
        return this.Friday;
    }

    public void setFriday(TimingModel friday) {
        this.Friday = friday;
    }

    public TimingModel getSaturday() {
        return this.Saturday;
    }

    public void setSaturday(TimingModel saturday) {
        this.Saturday = saturday;
    }

    public TimingModel getSunday() {
        return this.Sunday;
    }

    public void setSunday(TimingModel sunday) {
        this.Sunday = sunday;
    }
}
