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
        db.execSQL("CREATE TABLE PREFERENCIAS_USUARIO(CARRO BOOLEAN NOT NULL DEFAULT 1, MOTO BOOLEAN NOT NULL DEFAULT 1, VALOR_MEIAHORA BOOLEAN NOT NULL DEFAULT 1, VALOR_UMAHORA BOOLEAN NOT NULL DEFAULT 1, VALOR_DIARIA BOOLEAN NOT NULL DEFAULT 1, VALOR_SEMANAL BOOLEAN NOT NULL DEFAULT 1, VALOR_MENSAL BOOLEAN NOT NULL DEFAULT 1)");
        db.execSQL("INSERT INTO PREFERENCIAS_USUARIO(CARRO, MOTO, VALOR_MEIAHORA, VALOR_UMAHORA, VALOR_DIARIA, VALOR_SEMANAL, VALOR_MENSAL) VALUES (1,1,1,1,1,1,1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
