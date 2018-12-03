package com.example.root.buscavagaapp;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AjudaFragment extends Fragment {

    private TextView tvLinkManual;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ajuda, container, false);

        tvLinkManual = (TextView) view.findViewById(R.id.tvLinkManual);

        tvLinkManual.setMovementMethod(LinkMovementMethod.getInstance());

        return view;
    }

}
