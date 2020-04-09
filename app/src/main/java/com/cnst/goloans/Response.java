package com.cnst.goloans;

import android.widget.Button;

public class Response {
private String firstname;
private  String lastname;
    private  String phone;
private  String email;
private Button approve;
private Button reject;
private  String uid;
private  String fcmid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFcmid() {
        return fcmid;
    }

    public void setFcmid(String fcmid) {
        this.fcmid = fcmid;
    }

    public Response(String uid, String fcmid) {
        this.uid = uid;
        this.fcmid = fcmid;
    }

    public Button getApprove() {
        return approve;
    }

    public void setApprove(Button approve) {
        this.approve = approve;
    }

    public Button getReject() {
        return reject;
    }

    public void setReject(Button reject) {
        this.reject = reject;
    }

    public Response(Button approve, Button reject) {
        this.approve = approve;
        this.reject = reject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Response(String email) {
        this.email = email;
    }

    public Response() {
    }

    public Response(String firstname, String lastname, String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
