package com.example.tejaspatel.log;

/**
 * Created by Tejas Patel on 26-Jul-17.
 */

public class PostInfo  {
    private String uname;
    private int id;
    private  int teamid;
    private  int touserid;
    private  int fromuserid;
    private  String status;

    public PostInfo(String uname, int id, int teamid, int touserid, int fromuserid, String status) {
        this.uname = uname;
        this.id = id;
        this.teamid = teamid;
        this.touserid = touserid;
        this.fromuserid = fromuserid;
        this.status = status;
    }

    public String getUname() {
        return uname;
    }

    public int getId() {
        return id;
    }

    public int getTeamid() {
        return teamid;
    }

    public int getTouserid() {
        return touserid;
    }

    public int getFromuserid() {
        return fromuserid;
    }

    public String getStatus() {
        return status;
    }
}
