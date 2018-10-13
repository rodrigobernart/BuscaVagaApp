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
            JSONArray array = jsonObj.getJSONArray("");

            for(int i = 0; i < array.length(); i++){

                JSONObject objArray = array.getJSONObject(i);
                DadosEmpresas empresas = new DadosEmpresas();
/* Ajustar primeiro a classe dos dados de empresa para ter os getters e setters
                empresas.setData(objArray.getString("date"));
                empresas.setDia(objArray.getString("day"));
                empresas.setMaxima(objArray.getString("high"));
                empresas.setMinima(objArray.getString("low"));
                empresas.setTexto(objArray.getString("text"));
                listaEmpresas.add(empresas);
*/
            }
            return listaEmpresas;

        } catch(JSONException ex){
            ex.printStackTrace();
            return null;
        }
    }
}
