package com.fotg.keepingcool.models;

import com.google.firebase.database.PropertyName;

public class Profile {

    private String firstName;
    private String lastName;
    private String aboutMe;

    @PropertyName("First Name")
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @PropertyName("Last Name")
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @PropertyName("About Me")
    public String getAboutMe() {
        return aboutMe;
    }
    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public Profile() {}

    public Profile(String f, String l, String a) {
        firstName = f;
        lastName = l;
        aboutMe = a;
    }
}
