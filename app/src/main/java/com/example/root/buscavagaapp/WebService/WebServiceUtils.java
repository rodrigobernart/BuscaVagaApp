package com.example.root.buscavagaapp.WebService;

import com.example.root.buscavagaapp.modelo.DadosEmpresas;
import com.example.root.buscavagaapp.modelo.ParametrosEmpresa;

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
                empresas.setLatitude(objArray.getDouble("latitude"));
                empresas.setLongitude(objArray.getDouble("longitude"));
                listaEmpresas.add(empresas);
            }
            return listaEmpresas;

        } catch(JSONException ex){
            ex.printStackTrace();
            return null;
        }
    }

    private ArrayList<ParametrosEmpresa> converte(String json){
        try {
            ArrayList<ParametrosEmpresa> listaParametros = new ArrayList<>();

            JSONObject jsonObj = new JSONObject(json);
            JSONObject empresa = jsonObj.getJSONObject("empresa");
            JSONArray array = empresa.getJSONArray("parametros_empresa");

            for (int i = 0; i < array.length(); i++){
                JSONObject objArray = array.getJSONObject(i);
                ParametrosEmpresa parametros = new ParametrosEmpresa();

                parametros.setId_empresa(objArray.getInt("id_empresa"));
                parametros.setTipo_veiculo(objArray.getString("tipo_veiculo").charAt(0));
                parametros.setValor_meiahora(objArray.getDouble("valor_meiahora"));
                parametros.setValor_umahora(objArray.getDouble("valor_umahora"));
                parametros.setValor_diaria(objArray.getDouble("valor_diaria"));
                parametros.setValor_semana(objArray.getDouble("valor_semana"));
                parametros.setValor_mes(objArray.getDouble("valor_mes"));
                listaParametros.add(parametros);
            }
            return listaParametros;

        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
