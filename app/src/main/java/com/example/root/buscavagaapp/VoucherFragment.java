package com.example.root.buscavagaapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.buscavagaapp.WebService.WebServiceUtils;
import com.example.root.buscavagaapp.modelo.Voucher;

import java.util.ArrayList;


public class VoucherFragment extends Fragment {

    private TextView tvCodigoCupom;
    private TextView tvDescricaoCupom;
    private TextView tvValidadeCupom;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voucher, container, false);

        tvCodigoCupom = view.findViewById(R.id.tvCodigoCupom);
        tvDescricaoCupom = view.findViewById(R.id.tvDescricaoCupom);
        tvValidadeCupom = view.findViewById(R.id.tvValidadeCupom);

        ExecutaConexao executaConexao = new ExecutaConexao();
        executaConexao.execute();

        return view;
    }

    private class ExecutaConexao extends AsyncTask<Void, Void, ArrayList<Voucher>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList doInBackground(Void... params) {
            WebServiceUtils utils = new WebServiceUtils();

            ArrayList<Voucher> dados = utils.retornaVoucher("http://buscavagaapp.com.br:8000/cupom.php");

            return dados;
        }

        @Override
        protected void onPostExecute(ArrayList<Voucher> vouchers) {
            super.onPostExecute(vouchers);

            if(vouchers != null) {
                for (Voucher voucher : vouchers) {

                    tvCodigoCupom.setText(voucher.getCodigo().toUpperCase());
                    tvDescricaoCupom.setText(voucher.getDescricao());
                    tvValidadeCupom.setText("Validade: " + voucher.getValidade());

                }
            } else {
                tvCodigoCupom.setText("Opa.. não temos nenhum cupom ativo no momento!");
            }

        }
    }
}
