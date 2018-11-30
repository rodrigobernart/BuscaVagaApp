package com.example.root.buscavagaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //cria tabela de preferencias em cada celular
        db.execSQL("CREATE TABLE PREFERENCIAS_USUARIO(CARRO VARCHAR(3) NOT NULL DEFAULT 'SIM', " +
                "MOTO VARCHAR(3) NOT NULL DEFAULT 'SIM', " +
                "VALOR_MEIAHORA VARCHAR(3) NOT NULL DEFAULT 'SIM', " +
                "VALOR_UMAHORA VARCHAR(3) NOT NULL DEFAULT 'SIM', " +
                "VALOR_DIARIA VARCHAR(3) NOT NULL DEFAULT 'SIM', " +
                "VALOR_SEMANAL VARCHAR(3) NOT NULL DEFAULT 'SIM', " +
                "VALOR_MENSAL VARCHAR(3) NOT NULL DEFAULT 'SIM'," +
                "COBERTAS VARCHAR(3) NOT NULL DEFAULT 'SIM'," +
                "DESCOBERTAS VARCHAR(3) NOT NULL DEFAULT 'SIM')");

        //insere default no momento da instalação
        db.execSQL("INSERT INTO PREFERENCIAS_USUARIO(CARRO, MOTO, VALOR_MEIAHORA, VALOR_UMAHORA, VALOR_DIARIA, VALOR_SEMANAL, VALOR_MENSAL, COBERTAS, DESCOBERTAS) VALUES " +
                "('SIM','SIM','SIM','SIM','SIM','SIM','SIM','SIM', 'SIM')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
