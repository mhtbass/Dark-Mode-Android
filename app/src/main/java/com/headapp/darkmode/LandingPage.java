package com.headapp.darkmode;

import android.app.UiModeManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class LandingPage extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    RadioButton day, night, auto;

    UiModeManager uiModeManager;

    LinearLayout adRemove;
    LinearLayout rateUp;
    RelativeLayout main;

    BillingProcessor bp;

    private AdView mAdView;

    public static SharedPreferences pref;
    public static SharedPreferences prefMarka;
    SharedPreferences.Editor editor;
    int checkSatınAlma;

    private String BILLING="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bp=new BillingProcessor(this,BILLING,this);
        bp.initialize();

        adRemove=findViewById(R.id.removeADS);
        rateUp=findViewById(R.id.rateUp);
        main=findViewById(R.id.main);

        prefMarka = getApplicationContext().getSharedPreferences("Check", getApplicationContext().MODE_PRIVATE);
        editor = prefMarka.edit();

        if(prefMarka.getInt("firstSettings", 0) == 0) {

            main.setBackgroundResource(R.drawable.bg_day);
            editor.putInt("firstSettings", 1);
            editor.putInt("checkSatınAlma", 0);
            editor.apply();

            Log.d("firstcount",String.valueOf(prefMarka.getInt("checkSatınAlma",0)));

        }else{

            checkSatınAlma = prefMarka.getInt("checkSatınAlma",0);
            Log.d("firstcount",String.valueOf(checkSatınAlma));

        }


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();

        String checkSatinalString = String.valueOf(checkSatınAlma);
        if(checkSatinalString.equals("1")){
            adRemove.setVisibility(View.INVISIBLE);

        }else{
            adRemove.setVisibility(View.VISIBLE);

            mAdView.loadAd(adRequest);
        }


        AdManager adManager = AdManager.getInstance();
        adManager.createAd(getApplicationContext());

        InterstitialAd ad =  adManager.getAd();


        day = findViewById(R.id.radioDay);
        night = findViewById(R.id.radioNight);
        auto = findViewById(R.id.radioAuto);

        uiModeManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);

        switch (uiModeManager.getNightMode()) {
            case UiModeManager.MODE_NIGHT_NO:
                day.setChecked(true);
                main.setBackgroundResource(R.drawable.bg_day);
                break;
            case UiModeManager.MODE_NIGHT_YES:
                night.setChecked(true);
                main.setBackgroundResource(R.drawable.bg_night);
                break;
            case UiModeManager.MODE_NIGHT_AUTO:
                auto.setChecked(true);
                main.setBackgroundResource(R.drawable.bg_day);
                break;
        }

        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(checkSatinalString.equals("1")){

                }else{
                    if (ad.isLoaded()) {
                        ad.show();
                        adManager.createAgain();
                    }else{
                        adManager.createAgain();
                    }
                }

                day.setChecked(true);
                night.setChecked(false);
                auto.setChecked(false);
                uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
                main.setBackgroundResource(R.drawable.bg_day);


            }
        });

        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkSatinalString.equals("1")){

                }else{
                    if (ad.isLoaded()) {
                        ad.show();
                        adManager.createAgain();
                    }else{
                        adManager.createAgain();
                    }
                }

                day.setChecked(false);
                night.setChecked(false);
                auto.setChecked(true);
                uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_AUTO);
                main.setBackgroundResource(R.drawable.bg_day);

            }
        });


        night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkSatinalString.equals("1")){

                }else{
                    if (ad.isLoaded()) {
                        ad.show();
                        adManager.createAgain();
                    }else{
                        adManager.createAgain();
                    }
                }

                day.setChecked(false);
                night.setChecked(true);
                auto.setChecked(false);
                uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
                main.setBackgroundResource(R.drawable.bg_night);
            }
        });


        adRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp.purchase(LandingPage.this,"buy");
            }
        });

        ad.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                adManager.createAgain();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                adManager.createAgain();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                adManager.createAgain();
            }
        });


       rateUp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
               Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
               // To count with Play market backstack, After pressing back button,
               // to taken back to our application, we need to add following flags to intent.
               goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                       Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                       Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
               try {
                   startActivity(goToMarket);
               } catch (ActivityNotFoundException e) {
                   startActivity(new Intent(Intent.ACTION_VIEW,
                           Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
               }
           }
       });
    }


    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {

        Toast.makeText(this, R.string.succespruc, Toast.LENGTH_SHORT).show();
        editor.putInt("checkSatınAlma",1);
        editor.apply();
        Toast.makeText(this, R.string.restartapp, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {

        Toast.makeText(this, R.string.failpurc, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
}
