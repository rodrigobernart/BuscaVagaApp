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
    private String[] colunas = {"CARRO", "MOTO", "VALOR_MEIAHORA", "VALOR_UMAHORA", "VALOR_DIARIA", "VALOR_SEMANAL", "VALOR_MENSAL"};

    public PreferenciasUsuarioDAO(Context context){
        dbHelper = new SQLiteHelper(context,"PREFERENCIAS_USUARIO", null, 1);
        db = dbHelper.getWritableDatabase();
    }

    public long atualizar(PreferenciasUsuario preferenciasUsuario){
        ContentValues valores = new ContentValues();
        try {
            valores.put("MOTO", preferenciasUsuario.isMoto());
            valores.put("CARRO", preferenciasUsuario.isCarro());
            valores.put("VALOR_MEIAHORA", preferenciasUsuario.isValor_meiahora());
            valores.put("VALOR_UMAHORA", preferenciasUsuario.isValor_umahora());
            valores.put("VALOR_DIARIA", preferenciasUsuario.isValor_diaria());
            valores.put("VALOR_SEMANAL", preferenciasUsuario.isValor_semanal());
            valores.put("VALOR_MENSAL", preferenciasUsuario.isValor_mensal());

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
                    preferenciasUsuario.setMoto(Boolean.parseBoolean(cursor.getString(0)));
                    preferenciasUsuario.setCarro(Boolean.parseBoolean(cursor.getString(1)));
                    preferenciasUsuario.setValor_meiahora(Boolean.parseBoolean(cursor.getString(2)));
                    preferenciasUsuario.setValor_umahora(Boolean.parseBoolean(cursor.getString(3)));
                    preferenciasUsuario.setValor_diaria(Boolean.parseBoolean(cursor.getString(4)));
                    preferenciasUsuario.setValor_semanal(Boolean.parseBoolean(cursor.getString(5)));
                    preferenciasUsuario.setValor_mensal(Boolean.parseBoolean(cursor.getString(6)));

                    listaPreferencias.add(preferenciasUsuario);
                } while (cursor.moveToNext());
            }

            return listaPreferencias;
        } catch (Exception e){
            return listaPreferencias;
        }

    }

}
