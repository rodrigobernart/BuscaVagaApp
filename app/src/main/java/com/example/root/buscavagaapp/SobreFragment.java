package com.example.root.buscavagaapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SobreFragment extends Fragment {


    private TextView tvVersaoApp;
    private Button btFacebook;
    private Button btInstagram;
    private Button btPlayStore;
    private static final String FACEBOOK_URL = "https://www.facebook.com/buscavagaapp/";
    private static final String INSTAGRAM_URL = "https://www.instagram.com/buscavagaapp/";

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sobre, container, false);

        tvVersaoApp = view.findViewById(R.id.tvVersaoApp);
        String versao = BuildConfig.VERSION_NAME;
        tvVersaoApp.setText("Versão: " + versao);

        btFacebook = view.findViewById(R.id.btFacebook);
        btFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(getContext());
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
            }
        });

        btInstagram = view.findViewById(R.id.btInstagram);
        btInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instaIntent = new Intent(Intent.ACTION_VIEW);
                String instaUrl = getInstagramPageURL(getContext());
                instaIntent.setData(Uri.parse(instaUrl));
                startActivity(instaIntent);
            }
        });

        return view;
    }


    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;

            if (versionCode >= 3002850) {
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else {
                return "fb://page/buscavagaapp";
            }

        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL;
        }
    }

    public String getInstagramPageURL(Context context){
        Uri uri = Uri.parse("http://instagram.com/_u/buscavagaapp");

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.instagram.android");

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(INSTAGRAM_URL)));
        }

        return INSTAGRAM_URL;
    }

}
