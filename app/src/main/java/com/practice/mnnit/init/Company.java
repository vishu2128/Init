package com.practice.mnnit.init;

/**
 * Created by Shivani gupta on 10/7/2017.
 */
public class Company {

    private String name;
    private String link;
    private String course;
    private String ctc;
    private String location;
    private String designation;
    private String eligibility;
    private String dateArrival;
    private String dateTest;
    private String dateRegistration;
    private String placed;
    private String tmp;

    public Company(String name) {
        this.name = name;
    }

    public Company(String name, String dateRegistration) {
        this.dateRegistration = dateRegistration;
        this.name = name;
    }

    public Company(String name, String placed, String tmp) {
        this.name = name;
        this.placed = placed;
        this.tmp = tmp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCtc() {
        return ctc;
    }

    public void setCtc(String ctc) {
        this.ctc = ctc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getDateArrival() {
        return dateArrival;
    }

    public void setDateArrival(String dateArrival) {
        this.dateArrival = dateArrival;
    }

    public String getDateTest() {
        return dateTest;
    }

    public void setDateTest(String dateTest) {
        this.dateTest = dateTest;
    }

    public String getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(String dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public String getPlaced() {
        return placed;
    }

    public void setPlaced(String placed) {
        this.placed = placed;
    }
}
