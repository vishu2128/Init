package com.practice.mnnit.init;

import android.graphics.Bitmap;

/**
 * Created by Shivani gupta on 8/27/2017.
 */
public class Student {
    private String name;
    private String reg_no;
    private String gender;
    private String dob;

    private String course;
    private String branch;
    private String cpi;
    private String aisse;
    private String aissce;

    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String hostel;

    private String TpoCredits;
    private String password;
    private String resume;
    private String photo;

    private String updated;
    private String updatable;

    private String placed;
    private String company;
    private String ctc;

    private Bitmap pic;
    private int tpoImage;

    public Student(String name, String reg_no, String course, String branch, String email, String phone, String tpoCredits, String placed, String company, String ctc, Bitmap pic) {
        this.name = name;
        this.reg_no = reg_no;
        this.course = course;
        this.branch = branch;
        this.email = email;
        this.phone = phone;
        this.TpoCredits = tpoCredits;
        this.placed = placed;
        this.company = company;
        this.ctc = ctc;
        this.pic = pic;
    }

    public Student(String name, String reg_no, String course, String branch,  String phone, String email, int tpoImage) {
        this.name = name;
        this.reg_no = reg_no;
        this.course = course;
        this.branch = branch;
        this.email = email;
        this.phone = phone;
        this.tpoImage = tpoImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCpi() {
        return cpi;
    }

    public void setCpi(String cpi) {
        this.cpi = cpi;
    }

    public String getAisse() {
        return aisse;
    }

    public void setAisse(String aisse) {
        this.aisse = aisse;
    }

    public String getAissce() {
        return aissce;
    }

    public void setAissce(String aissce) {
        this.aissce = aissce;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTpoCredits() {
        return TpoCredits;
    }

    public void setTpoCredits(String tpoCredits) {
        TpoCredits = tpoCredits;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUpdatable() {
        return updatable;
    }

    public void setUpdatable(String updatable) {
        this.updatable = updatable;
    }

    public String getPlaced() {
        return placed;
    }

    public void setPlaced(String placed) {
        this.placed = placed;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCtc() {
        return ctc;
    }

    public void setCtc(String ctc) {
        this.ctc = ctc;
    }

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    public int getTpoImage() {
        return tpoImage;
    }

    public void setTpoImage(int tpoImage) {
        this.tpoImage = tpoImage;
    }
}

