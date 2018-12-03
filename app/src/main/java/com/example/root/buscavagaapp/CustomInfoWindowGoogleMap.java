package com.example.root.buscavagaapp;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Activity context;

    public CustomInfoWindowGoogleMap(Activity context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View v = context.getLayoutInflater().inflate(R.layout.info_window, null);

        TextView tvEstacionamento = v.findViewById(R.id.tvEstacionamento);
        TextView tvEndereco = v.findViewById(R.id.tvEndereco);
        TextView tvTelefones = v.findViewById(R.id.tvTelefones);

        final InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();
        tvEstacionamento.setText(infoWindowData.getNome());
        tvEndereco.setText(infoWindowData.getEndereco());
        tvTelefones.setText(infoWindowData.getTelefones());

        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
