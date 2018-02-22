package dev.aot.markkevindalbios.quarantinemonitoring.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mark.kevin.d.albios on 2/1/2018.
 */

public class Person implements Serializable {

    private String id;
    private String fName;
    private String mName;
    private String lName;
    private String dateOfBirth;
    private String gender;
    private String imageUrl;
    private String address;
    private String contactNo;
    private ArrayList<Logs> pLogs;

    public Person() {
        this.pLogs = new ArrayList<Logs>();
    }

    public Person(String id, String fName, String mName, String lName,
                  String dateOfBirth, String gender, String imageUrl, String address, String contactNo) {
        this.id = id;
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.address = address;
        this.contactNo = contactNo;
        this.pLogs = new ArrayList<Logs>();
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public ArrayList<Logs> getpLogs() {
        return pLogs;
    }

    public void setpLogs(ArrayList<Logs> pLogs) {
        this.pLogs = pLogs;
    }
}
