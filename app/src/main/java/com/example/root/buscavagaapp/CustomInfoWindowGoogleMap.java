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
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View v = context.getLayoutInflater().inflate(R.layout.info_window, null);

        TextView tvEstacionamento = v.findViewById(R.id.tvEstacionamento);
        TextView tvEndereco = v.findViewById(R.id.tvEndereco);
        TextView tvTelefones = v.findViewById(R.id.tvTelefones);
        ImageView ivCarro = v.findViewById(R.id.ivCarro);
        TextView tvPrecosCarro = v.findViewById(R.id.tvPrecosCarro);
        ImageView ivMoto = v.findViewById(R.id.ivMoto);
        TextView tvPrecosMoto = v.findViewById(R.id.tvPrecosMoto);

        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();
        tvEstacionamento.setText(infoWindowData.getNome());
        tvEndereco.setText(infoWindowData.getEndereco());
        tvTelefones.setText(infoWindowData.getTelefones());
        //carro
        ivCarro.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_car));
        tvPrecosCarro.setText(infoWindowData.getPrecosCarro());
        //moto
        ivMoto.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_moto));
        tvPrecosMoto.setText(infoWindowData.getPrecosMoto());

        return v;
    }
}
