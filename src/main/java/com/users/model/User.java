package com.users.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
    //Test comment added
    String username;
    String displayName;
    String department;

    public User(){
    }

    public User(String username, String displayName, String department){
        this.username = username;
        this.displayName = displayName;
        this.department = department;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


}