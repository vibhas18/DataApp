package in.co.dineout.xoppin.dineoutcollection.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suraj on 04/02/16.
 */
public class TimingModel implements Serializable {
    private static final long serialVersionUID = -7141378558786661652L;

    private String state;

    public JSONArray getTimings() {

        try {

            JSONArray array = new JSONArray();
            for(Slots slots : timings){

                JSONObject value = new JSONObject();
                value.put("st_time",slots.getSt_time());
                value.put("en_time",slots.getEn_time());
                array.put(value);

            }
            return array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    public void setTimings(List<Slots> timings) {
        this.timings = timings;
    }

    private List<Slots> timings = new ArrayList<>();
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


    public class Slots{

        public String getSt_time() {
            return st_time;
        }

        public void setSt_time(String st_time) {
            this.st_time = st_time;
        }

        private String st_time;

        public String getEn_time() {
            return en_time;
        }

        public void setEn_time(String en_time) {
            this.en_time = en_time;
        }

        private String en_time;
    }
}
