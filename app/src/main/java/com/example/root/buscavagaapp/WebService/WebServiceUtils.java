package com.example.root.buscavagaapp.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WebServiceUtils{
    public ArrayList<DadosEmpresas> retornaInformacoes(String url){
        String json;
        ArrayList<DadosEmpresas> retorno;
        json = ConexaoUtils.retornaJSON(url);

        retorno = converteJSON(json);
        return retorno;
    }
    private ArrayList<DadosEmpresas> converteJSON(String json){
        try{
            ArrayList<DadosEmpresas> listaEmpresas = new ArrayList<>();

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("empresa");

            for(int i = 0; i < array.length(); i++){

                JSONObject objArray = array.getJSONObject(i);
                DadosEmpresas empresas = new DadosEmpresas();

                empresas.setNome_empresa(objArray.getString("nome_empresa"));
                empresas.setTelefone_fixo(objArray.getString("telefone_fixo"));
                empresas.setTelefone_cel(objArray.getString("telefone_cel"));
                empresas.setLatitude(objArray.getString("latitude"));
                empresas.setLongitude(objArray.getString("longitude"));
                listaEmpresas.add(empresas);
            }
            return listaEmpresas;

        } catch(JSONException ex){
            ex.printStackTrace();
            return null;
        }
    }
}
