package com.example.doaapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private DatabaseHelper dbHelper;
    private ArrayList<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapter(itemList, this::abrirDetalhes);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);

        findViewById(R.id.btnAdicionar).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
            startActivity(intent);
        });

        carregarItens();
    }

    private void carregarItens() {
        itemList.clear();
        Cursor cursor = dbHelper.obterItens("Disponível");
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
                String descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"));
                String categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                itemList.add(new Item(id, nome, descricao, categoria, status));
            } while (cursor.moveToNext());
        }
        cursor.close();
        itemAdapter.notifyDataSetChanged();
    }

    private void abrirDetalhes(Item item) {
        Intent intent = new Intent(MainActivity.this, DetalhesActivity.class);
        intent.putExtra("ITEM_ID", item.getId());
        startActivity(intent);
    }
}
