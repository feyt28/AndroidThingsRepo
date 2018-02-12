package dev.aot.markkevindalbios.quarantinemonitoring.model;

import java.io.Serializable;

/**
 * Created by mark.kevin.d.albios on 2/1/2018.
 */

public class Person implements Serializable {

    private String id;
    private String fName;
    private String mName;
    private String lName;
    private int age;
    private String gender;
    private String imageUrl;

    public Person() {
    }

    public Person(String id, String fName, String mName, String lName, int age, String gender, String imageUrl) {
        this.id = id;
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.age = age;
        this.gender = gender;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
