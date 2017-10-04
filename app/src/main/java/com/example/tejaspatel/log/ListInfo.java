package com.example.tejaspatel.log;

/**
 * Created by Tejas Patel on 24-Jul-17.
 */

public class ListInfo {
    //private String imgURL;
    private int id;
    private int userid;
    private String locationname;
    private  int need;
    private String userName;
    private String gameName;
    private String date;

    public ListInfo(int id, int userid,String userName,String gameName,String locationname,int need, String date){
        //this.imgURL=imgURL;
        this.id=id;
        this.userid=userid;
        this.locationname=locationname;
        this.need=need;
        this.userName=userName;
        this.gameName=gameName;
        this.date=date;
    }
//    public String getImgURL(){
//        return imgURL;
//    }


    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public String getLocationname() {
        return locationname;
    }

    public String getNeed() {
        return ""+need;
    }

    public String getUserName(){
        return userName;
    }
    public String getGameName(){
        return gameName;
    }
}
