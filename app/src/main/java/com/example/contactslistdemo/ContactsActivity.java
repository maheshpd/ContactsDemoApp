package com.example.contactslistdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

public class ContactsActivity extends AppCompatActivity {

    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ",
                null, null);
        cursor.moveToNext();

        String cNumber = cursor.getString(cursor.getColumnIndex("data1"));
        System.out.println(cNumber);




    }
}
