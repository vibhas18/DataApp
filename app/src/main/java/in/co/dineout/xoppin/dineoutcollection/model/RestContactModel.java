package in.co.dineout.xoppin.dineoutcollection.model;

import java.io.Serializable;

/**
 * Created by suraj on 04/02/16.
 */
public class RestContactModel implements Serializable {
    private static final long serialVersionUID = 6939084069144798461L;

    private String phone_no = "";
    private String gcrr_type = "";
    private String au_email = "";
    private String first_name  ="";
    private String last_name = "";

    public String getPhone_no() {
        return this.phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getGcrr_type() {
        return this.gcrr_type;
    }

    public void setGcrr_type(String gcrr_type) {
        this.gcrr_type = gcrr_type;
    }

    public String getAu_email() {
        return this.au_email;
    }

    public void setAu_email(String au_email) {
        this.au_email = au_email;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
