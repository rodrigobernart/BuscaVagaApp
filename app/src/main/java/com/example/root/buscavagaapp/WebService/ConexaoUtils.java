package com.example.root.buscavagaapp.WebService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConexaoUtils {
    //responsavel por conectar ao webservice e carregar um objeto JSON
    public static String retornaJSON(String url){
        String retorno = "";
        try{
            URL urlApi = new URL(url);
            int codigoResposta;
            HttpURLConnection conexao;
            InputStream is;

            conexao = (HttpURLConnection) urlApi.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setReadTimeout(15000);
            conexao.setConnectTimeout(15000);
            conexao.connect();

            codigoResposta = conexao.getResponseCode();
            if(codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST){
                is = conexao.getInputStream();
            } else {
                is = conexao.getErrorStream();
            }

            //pega o inputstream e converte para String
            retorno = converteInputStream(is);
            is.close();
            conexao.disconnect();

        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    //converte o inputstream em string
    private static String converteInputStream(InputStream is){
        StringBuffer buffer = new StringBuffer();

        try{
            BufferedReader br;
            String linha;

            br = new BufferedReader(new InputStreamReader(is));
            while((linha = br.readLine())!= null){
                buffer.append(linha); //adiciona o dado no buffer
            }
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        return buffer.toString();
    }
}