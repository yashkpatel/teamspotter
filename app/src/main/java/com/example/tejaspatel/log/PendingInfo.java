package com.example.tejaspatel.log;

/**
 * Created by Tejas Patel on 26-Jul-17.
 */

public class PendingInfo {

    private String username;
    private String sportname;
    private String locationname;
    private String status;
    private int need;
    private int userid;
    private String date;

    public PendingInfo(int userid,String username, String sportname, String locationname, String status, int need, String date) {
        this.username = username;
        this.sportname = sportname;
        this.locationname = locationname;
        this.status = status;
        this.need = need;
        this.userid = userid;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    public String getSportname() {
        return sportname;
    }

    public String getLocationname() {
        return locationname;
    }

    public String getStatus() {
        return status;
    }

    public int getNeed() {
        return need;
    }

    public int getUserid(){return userid;}
}
