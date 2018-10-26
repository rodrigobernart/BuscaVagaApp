package com.example.root.buscavagaapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Switch;

public class PreferenciasFragment extends Fragment {

    private Activity context;

    private Switch motoSwitch, carroSwitch, meiaHoraSwitch, umaHoraSwitch, diariaSwitch, semanalSwitch, mensalSwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        motoSwitch = (Switch) getActivity().findViewById(R.id.motoSwitch);
        carroSwitch = (Switch) getActivity().findViewById(R.id.carroSwitch);
        meiaHoraSwitch = (Switch) getActivity().findViewById(R.id.meiaHoraSwitch);
        umaHoraSwitch = (Switch) getActivity().findViewById(R.id.umaHoraSwitch);
        diariaSwitch = (Switch) getActivity().findViewById(R.id.diariaSwitch);
        semanalSwitch = (Switch) getActivity().findViewById(R.id.semanalSwitch);
        mensalSwitch = (Switch) getActivity().findViewById(R.id.mensalSwitch);

        return inflater.inflate(R.layout.fragment_preferencias, container, false);

    }


    public void salvarPreferencias(View view) {

    }

    public void limparPreferencias(View view) {
        motoSwitch.setChecked(false);
        carroSwitch.setChecked(false);
        meiaHoraSwitch.setChecked(false);
        umaHoraSwitch.setChecked(false);
        diariaSwitch.setChecked(false);
        semanalSwitch.setChecked(false);
        mensalSwitch.setChecked(false);
    }
}
