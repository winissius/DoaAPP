package com.example.doaapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetalhesActivity extends AppCompatActivity {
    private TextView txtNome, txtDescricao, txtCategoria;
    private DatabaseHelper dbHelper;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        txtNome = findViewById(R.id.txtDetalheNome);
        txtDescricao = findViewById(R.id.txtDetalheDescricao);
        txtCategoria = findViewById(R.id.txtDetalheCategoria);
        dbHelper = new DatabaseHelper(this);

        itemId = getIntent().getIntExtra("ITEM_ID", -1);
        carregarDetalhes(itemId);

        findViewById(R.id.btnMarcarDoado).setOnClickListener(v -> marcarComoDoado());
    }

    private void carregarDetalhes(int id) {
        Cursor cursor = dbHelper.obterItens("Disponível");
        if (cursor.moveToFirst()) {
            txtNome.setText(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
            txtDescricao.setText(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
            txtCategoria.setText(cursor.getString(cursor.getColumnIndexOrThrow("categoria")));
        }
        cursor.close();
    }

    private void marcarComoDoado() {
        boolean resultado = dbHelper.marcarComoDoado(itemId);
        if (resultado) {
            Toast.makeText(this, "Item marcado como Doado!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao marcar item.", Toast.LENGTH_SHORT).show();
        }
    }
}
