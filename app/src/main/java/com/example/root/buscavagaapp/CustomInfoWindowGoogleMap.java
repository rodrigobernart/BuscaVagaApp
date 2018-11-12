package com.example.root.buscavagaapp;

import android.app.Activity;
import android.content.Context;
import android.view.View;
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

        TextView tvEstacionamento = (TextView)v.findViewById(R.id.tvEstacionamento);
        TextView tvPrecoMeiaHora = (TextView)v.findViewById(R.id.tvPrecoMeiaHora);
        TextView tvPrecoUmaHora = (TextView)v.findViewById(R.id.tvPrecoUmaHora);
        TextView tvPrecoDiaria = (TextView)v.findViewById(R.id.tvPrecoDiaria);
        TextView tvPrecoSemanal = (TextView)v.findViewById(R.id.tvPrecoSemanal);
        TextView tvPrecoMensal = (TextView)v.findViewById(R.id.tvPrecoMensal);
        TextView tvTipoVeiculo = (TextView)v.findViewById(R.id.tvTipoVeiculo);

        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();

        tvEstacionamento.setText(infoWindowData.getNome());
        tvPrecoMeiaHora.setText(infoWindowData.getPrecoMeiaHora());
        tvPrecoUmaHora.setText(infoWindowData.getPrecoUmaHora());
        tvPrecoDiaria.setText(infoWindowData.getPrecoDiaria());
        tvPrecoSemanal.setText(infoWindowData.getPrecoSemanal());
        tvPrecoMensal.setText(infoWindowData.getPrecoMensal());
        tvTipoVeiculo.setText(infoWindowData.getTipo_veiculo());

        return v;
    }
}
