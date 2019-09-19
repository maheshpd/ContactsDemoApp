package com.example.contactslistdemo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_CONTACT =1121 ;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 2212;
    Button contactsBtn;
    TextView phoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusCheck();


        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_CONTACTS).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

            }
        }).check();


        contactsBtn = findViewById(R.id.go_to_phone_contacts_btn);
        phoneNo = findViewById(R.id.phone_no_txt);

        contactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);

            }
        });


        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                statusCheck();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

            }
        }).check();


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { 
//            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) { 
//                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Location Permission");
//                builder.setMessage("The app needs location permissions. Please grant this permission to continue using the features of the app.");
//                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION); 
//                    }
//                });
//                builder.setNegativeButton(android.R.string.no, null);
//                builder.show();
//            }
//        } else {
//            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            boolean isGpsProviderEnabled, isNetworkProviderEnabled;
//            isGpsProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//            isNetworkProviderEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//            if(!isGpsProviderEnabled && !isNetworkProviderEnabled) {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Location Permission");
//                builder.setMessage("The app needs location permissions. Please grant this permission to continue using the features of the app.");
//                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivity(intent);
//                    }
//                });
//                builder.setNegativeButton(android.R.string.no, null);
//                builder.show();
//            }
//        }

    }


    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        try {
                            if (hasPhone.equalsIgnoreCase("1")) {
                                Cursor phones = getContentResolver().query(
                                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                        null, null);
                                phones.moveToFirst();
                                String cNumber = phones.getString(phones.getColumnIndex("data1"));
                                System.out.println("number is:" + cNumber);
                                Log.d( "phone no: ",cNumber);
                                phoneNo.setText("Phone Number is: "+cNumber);
                            }
                            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                            txtname.setText("Name is: "+name);
                        }
                        catch (Exception ex)
                        {
                            ex.getMessage();
                        }
                    }
                }
                break;

                //Comment
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case PERMISSION_REQUEST_COARSE_LOCATION: {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Log.d(TAG, "coarse location permission granted");
//                } else {
//                    Intent intent = new Intent();
//                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                    Uri uri = Uri.fromParts("package", getPackageName(), null);
//                    intent.setData(uri);
//                    startActivity(intent);
//                }
//            }
//        }
//    }
}
