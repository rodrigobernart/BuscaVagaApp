package com.example.root.buscavagaapp.WebService;

import com.example.root.buscavagaapp.modelo.DadosEmpresas;

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

                empresas.setId_empresa(objArray.getInt("id_empresa"));
                empresas.setNome_empresa(objArray.getString("nome_empresa"));
                empresas.setEndereco(objArray.getString("endereco"));
                empresas.setTelefone_fixo(objArray.getString("telefone_fixo"));
                empresas.setTelefone_cel(objArray.getString("telefone_cel"));
                empresas.setLatitude(objArray.getDouble("latitude"));
                empresas.setLongitude(objArray.getDouble("longitude"));

                if(objArray.has("parametros_empresa")){
                    JSONArray ja = objArray.getJSONArray("parametros_empresa");
                    int tamanhoArray2 = ja.length();
                    for(int j = 0; j < tamanhoArray2; j++){
                        JSONObject objeto = ja.getJSONObject(j);
                        if(objeto.getString("tipo_veiculo").charAt(0) == 'C') {
                            empresas.setCarro(true);
                            empresas.setValor_meiahora_c(objeto.getDouble("valor_meiahora"));
                            empresas.setValor_umahora_c(objeto.getDouble("valor_umahora"));
                            empresas.setValor_diaria_c(objeto.getDouble("valor_diaria"));
                            empresas.setValor_semana_c(objeto.getDouble("valor_semana"));
                            empresas.setValor_mes_c(objeto.getDouble("valor_mes"));
                        }
                        else if(objeto.getString("tipo_veiculo").charAt(0) == 'M'){
                            empresas.setMoto(true);
                            empresas.setValor_meiahora_m(objeto.getDouble("valor_meiahora"));
                            empresas.setValor_umahora_m(objeto.getDouble("valor_umahora"));
                            empresas.setValor_diaria_m(objeto.getDouble("valor_diaria"));
                            empresas.setValor_semana_m(objeto.getDouble("valor_semana"));
                            empresas.setValor_mes_m(objeto.getDouble("valor_mes"));
                        }
                    }
                }
                listaEmpresas.add(empresas);
            }
            return listaEmpresas;

        } catch(JSONException ex){
            ex.printStackTrace();
            return null;
        }
    }
}
