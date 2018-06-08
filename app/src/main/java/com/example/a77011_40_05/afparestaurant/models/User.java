package com.example.a77011_40_05.afparestaurant.models;

public class User {


    private int idStaff;
    private String name;
    private String firstname;
    private String path;
    private int idJob;

    public User(){

    }


    public int getIdStaff() {
        return idStaff;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFullName(){
        return name +" "+ firstname;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setIdStaff(int idStaff) {
        this.idStaff = idStaff;
    }

    public int getIdJob() {
        return idJob;
    }

    public void setIdJob(int idJob) {
        this.idJob = idJob;
    }
}
