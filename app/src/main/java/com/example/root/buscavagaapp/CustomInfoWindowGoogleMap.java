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
        TextView tvPrecoMeiaHora = v.findViewById(R.id.tvPrecoMeiaHora);
        TextView tvPrecoUmaHora = v.findViewById(R.id.tvPrecoUmaHora);
        TextView tvPrecoDiaria = v.findViewById(R.id.tvPrecoDiaria);
        TextView tvPrecoSemanal = v.findViewById(R.id.tvPrecoSemanal);
        TextView tvPrecoMensal = v.findViewById(R.id.tvPrecoMensal);
        ImageView ivCarro = v.findViewById(R.id.ivCarro);
        ImageView ivMoto = v.findViewById(R.id.ivMoto);
        TextView tvPrecosMoto = v.findViewById(R.id.tvPrecosMoto);

        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();
        ivCarro.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_car));
        tvEstacionamento.setText(infoWindowData.getNome());
        tvPrecoMeiaHora.setText(infoWindowData.getPrecoMeiaHora());
        tvPrecoUmaHora.setText(infoWindowData.getPrecoUmaHora());
        tvPrecoDiaria.setText(infoWindowData.getPrecoDiaria());
        tvPrecoSemanal.setText(infoWindowData.getPrecoSemanal());
        tvPrecoMensal.setText(infoWindowData.getPrecoMensal());

        ivMoto.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_moto));
        tvPrecosMoto.setText(infoWindowData.getPrecosMoto());

        return v;
    }
}
