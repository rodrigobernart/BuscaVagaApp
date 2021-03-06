package com.example.root.buscavagaapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.root.buscavagaapp.SQLiteHelper;
import com.example.root.buscavagaapp.modelo.PreferenciasUsuario;

import java.util.ArrayList;
import java.util.List;

public class PreferenciasUsuarioDAO {

    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;
    private String[] colunas = {"CARRO", "MOTO", "VALOR_MEIAHORA", "VALOR_UMAHORA", "VALOR_DIARIA", "VALOR_SEMANAL", "VALOR_MENSAL", "COBERTAS", "DESCOBERTAS"};

    public PreferenciasUsuarioDAO(Context context){
        dbHelper = new SQLiteHelper(context,"PREFERENCIAS_USUARIO", null, 1);
        db = dbHelper.getWritableDatabase();
    }

    public long atualizar(PreferenciasUsuario preferenciasUsuario){
        ContentValues valores = new ContentValues();
        try {
            valores.put("CARRO", preferenciasUsuario.getCarro());
            valores.put("MOTO", preferenciasUsuario.getMoto());
            valores.put("VALOR_MEIAHORA", preferenciasUsuario.getValor_meiahora());
            valores.put("VALOR_UMAHORA", preferenciasUsuario.getValor_umahora());
            valores.put("VALOR_DIARIA", preferenciasUsuario.getValor_diaria());
            valores.put("VALOR_SEMANAL", preferenciasUsuario.getValor_semanal());
            valores.put("VALOR_MENSAL", preferenciasUsuario.getValor_mensal());
            valores.put("COBERTAS", preferenciasUsuario.getCobertas());
            valores.put("DESCOBERTAS", preferenciasUsuario.getDescobertas());

            return db.update("PREFERENCIAS_USUARIO", valores, null, null);
        } catch (SQLException ex){
            ex.printStackTrace();
            return -1;
        }
    }

    public List<PreferenciasUsuario> retornaPreferencias(){
        List<PreferenciasUsuario> listaPreferencias = new ArrayList<>();

        try {
            Cursor cursor = db.query("PREFERENCIAS_USUARIO", colunas, null, null, null, null, null);
            if(cursor.moveToFirst()) {
                do {
                    PreferenciasUsuario preferenciasUsuario = new PreferenciasUsuario();
                    preferenciasUsuario.setCarro(cursor.getString(0));
                    preferenciasUsuario.setMoto(cursor.getString(1));
                    preferenciasUsuario.setValor_meiahora(cursor.getString(2));
                    preferenciasUsuario.setValor_umahora(cursor.getString(3));
                    preferenciasUsuario.setValor_diaria(cursor.getString(4));
                    preferenciasUsuario.setValor_semanal(cursor.getString(5));
                    preferenciasUsuario.setValor_mensal(cursor.getString(6));
                    preferenciasUsuario.setCobertas(cursor.getString(7));
                    preferenciasUsuario.setDescobertas(cursor.getString(8));

                    listaPreferencias.add(preferenciasUsuario);
                } while (cursor.moveToNext());
            }

            return listaPreferencias;
        } catch (Exception e){
            return listaPreferencias;
        }

    }

}
