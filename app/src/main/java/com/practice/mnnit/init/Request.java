package com.practice.mnnit.init;

/**
 * Created by Shivani gupta on 10/5/2017.
 */
public class Request {
    String message;
    String UID;
    String Response;
    String reg;

    public Request()
    {

    }

    public Request(String message, String UID, String response) {
        this.message = message;
        this.UID = UID;
        Response = response;
    }

    public Request(String UID, String message, String reg, String response ) {
        this.message = message;
        this.UID = UID;
        this.Response = response;
        this.reg = reg;
    }

    public Request(String message, String UID) {
        this.message = message;
        this.UID = UID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }
}
