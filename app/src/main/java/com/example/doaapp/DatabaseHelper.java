package com.example.doaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DoacoesDB";
    private static final int DATABASE_VERSION = 1;

    // Nome da tabela e colunas
    private static final String TABLE_ITENS = "itens";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOME = "nome";
    private static final String COLUMN_DESCRICAO = "descricao";
    private static final String COLUMN_CATEGORIA = "categoria";
    private static final String COLUMN_STATUS = "status";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_ITENS = "CREATE TABLE " + TABLE_ITENS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOME + " TEXT NOT NULL, " +
                COLUMN_DESCRICAO + " TEXT NOT NULL, " +
                COLUMN_CATEGORIA + " TEXT NOT NULL, " +
                COLUMN_STATUS + " TEXT DEFAULT 'Disponível')";
        db.execSQL(CREATE_TABLE_ITENS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITENS);
        onCreate(db);
    }

    // Adicionar um item
    public long adicionarItem(String nome, String descricao, String categoria) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, nome);
        values.put(COLUMN_DESCRICAO, descricao);
        values.put(COLUMN_CATEGORIA, categoria);

        long id = db.insert(TABLE_ITENS, null, values);
        db.close();
        return id;
    }

    // Obter todos os itens com um status específico
    public Cursor obterItens(String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_ITENS,
                null,
                COLUMN_STATUS + " = ?",
                new String[]{status},
                null,
                null,
                COLUMN_NOME + " ASC"
        );
    }

    // Obter um item pelo ID
    public Cursor obterItemPorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_ITENS,
                null,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );
    }

    // Marcar um item como "Doado"
    public boolean marcarComoDoado(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, "Doado");

        int rowsAffected = db.update(TABLE_ITENS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected > 0;
    }

    // Excluir um item pelo ID
    public boolean excluirItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_ITENS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted > 0;
    }
}
