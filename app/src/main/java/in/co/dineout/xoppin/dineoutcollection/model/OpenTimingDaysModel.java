package in.co.dineout.xoppin.dineoutcollection.model;


import java.io.Serializable;

/**
 * Created by suraj on 04/02/16.
 */
public class OpenTimingDaysModel implements Serializable {
    private static final long serialVersionUID = 8747738709276510849L;

    private TimingModel Monday;

    private TimingModel Tuesday;

    private TimingModel Wednesday;

    private TimingModel Thursday;

    private TimingModel Friday;

    private TimingModel Saturday;

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
