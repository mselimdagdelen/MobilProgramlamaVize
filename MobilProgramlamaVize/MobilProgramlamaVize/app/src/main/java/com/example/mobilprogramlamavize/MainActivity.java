package com.example.mobilprogramlamavize;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_READ_CONTACTS = 1;
    int resultValue;
    ListView rehber_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rehber_list=findViewById(R.id.rehber_list);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            ArrayList<Kisiler> kisiler = new ArrayList<Kisiler>();

            Cursor tel_rehber = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,  null, null);
            while (tel_rehber.moveToNext())
            {
                String isim = tel_rehber.getString(tel_rehber.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                String numara = tel_rehber.getString(tel_rehber.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

                String contactID=tel_rehber.getString(tel_rehber.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));



                Kisiler nesnecik = new Kisiler();

                nesnecik.set_isim(isim);
                nesnecik.set_numara(numara);
                nesnecik.setResim(ContactPhoto(contactID));
                kisiler.add(nesnecik);
            }

            tel_rehber.close();
            KisilerAdapter kisilerAdapter = new KisilerAdapter(this, kisiler);
            if (rehber_list != null) {
                rehber_list.setAdapter(kisilerAdapter);
            }
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }



    }
    public Bitmap ContactPhoto(String contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(contactId));
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = getContentResolver().query(photoUri,
                new String[]{ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToNext();
            byte[] data = cursor.getBlob(0);
            if (data != null)
                return BitmapFactory.decodeStream(new ByteArrayInputStream(data));
            else
                return null;
        }
        cursor.close();
        return null;
    }
    public void goToActivity (View view){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}
