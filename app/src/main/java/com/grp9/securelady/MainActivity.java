package com.grp9.securelady;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.BuildConfig;

public class MainActivity extends AppCompatActivity {
    CardView siren, Settings, currentlocation, nearbyPolice, news, aboutUs, shareBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Intent backgroundService = new Intent( getApplicationContext(), ScreenOnOffBackgroundService.class );
        this.startService( backgroundService );
        Log.d( ScreenOnOffReceiver.SCREEN_TOGGLE_TAG, "Activity onCreate" );
        int permissionCheck = ContextCompat.checkSelfPermission (MainActivity.this, Manifest.permission.SEND_SMS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission (MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission (MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {


            final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.custom_dialog_mainactivity,null);

            Button btn_okay = (Button)mView.findViewById(R.id.btn_okay);
            TextView heading=mView.findViewById (R.id.heading);
            heading.setText("SecureLady needs access to");
            TextView sms=mView.findViewById (R.id.sms);
            sms.setText("Sending SMS:-");
            TextView textView=mView.findViewById (R.id.textFormodal);
            textView.setText ("Emergency messaging needs SEND SMS permission");
            TextView location=mView.findViewById (R.id.location);
            location.setText("Location:-");
            TextView locationText=mView.findViewById (R.id.textLocation);
            locationText.setText("Messaging embedded with live location needs Location permission");
            TextView call=mView.findViewById (R.id.call);
            call.setText("Phone Call:-");
            TextView callText=mView.findViewById (R.id.textCall);
            callText.setText("Emergency Calling needs CALL PHONE permission");
            TextView declaration=mView.findViewById (R.id.declaration);
            declaration.setText("Declaration");
            TextView declaratioText=mView.findViewById (R.id.textDeclaration);
            declaratioText.setText("The app is solely developed by INDIAN Developers and all data related to this app is stored locally in your phone.");

            alert.setView(mView);
            final AlertDialog alertDialog = alert.create();
            alertDialog.setCanceledOnTouchOutside(false);
            btn_okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss ();
                    ActivityCompat.requestPermissions (MainActivity.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE}, 0);
                }
            });
            alertDialog.show();

        }

        siren = findViewById( R.id.Siren );
        Settings = findViewById( R.id.Settings );
        currentlocation = findViewById( R.id.Currentlocation );
        news = findViewById( R.id.News );
        aboutUs = findViewById( R.id.about_us );
        nearbyPolice=findViewById (R.id.nearbyPolice);
        siren.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), Flashing.class ) );
            }
        } );
        Settings.setOnClickListener( v -> startActivity( new Intent( getApplicationContext(), SmsActivity.class ) ) );
        currentlocation.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), ChoosenActivity.class ) );
            }
        } );
        news.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), NewsActivity.class ) );
            }
        } );


        aboutUs.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), AboutUs.class ) );
            }

        } );

        nearbyPolice.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String url = "http://maps.google.co.in/maps?q=nearby+police+station";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse(url));
                startActivity(intent);
            }
        });

        shareBtn = findViewById(R.id.ShareBtn);
        shareBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



               try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "SecureLady");
                    String shareMessage= "Hey Ladies,I am gifting a token of safety to all the females in my society as \n\n*Secure Lady* solves a very heart wrenching problem of our civilisation, *Women's Safety*. \n\nJust *download*,start using, and spread the app \n\nSo that any *female* related to you can feel safer and empowered in this world. \n\nDownload SecureLady   at:-\n";

                    shareMessage = shareMessage + "https://drive.google.com/file/d/1OXacToXLj_RQUrxVWGFVs9ewaAbJuHVX/view?usp=sharing";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Share"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

    }




}