package com.example.contactslistdemo;

public class ContactsModel {
    String DisplayName,PhoneNumber,PhoneID;

    public ContactsModel(String displayName, String phoneNumber, String phoneID) {
        DisplayName = displayName;
        PhoneNumber = phoneNumber;
        PhoneID = phoneID;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPhoneID() {
        return PhoneID;
    }

    public void setPhoneID(String phoneID) {
        PhoneID = phoneID;
    }
}
