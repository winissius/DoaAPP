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
        if (itemId != -1) {
            carregarDetalhes(itemId);
        } else {
            Toast.makeText(this, "Erro ao carregar detalhes do item.", Toast.LENGTH_SHORT).show();
            finish();
        }

        findViewById(R.id.btnMarcarDoado).setOnClickListener(v -> marcarComoDoado());
    }

    private void carregarDetalhes(int id) {
        Cursor cursor = dbHelper.obterItemPorId(id);
        if (cursor != null && cursor.moveToFirst()) {
            txtNome.setText(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
            txtDescricao.setText(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
            txtCategoria.setText(cursor.getString(cursor.getColumnIndexOrThrow("categoria")));
            cursor.close();
        } else {
            Toast.makeText(this, "Item não encontrado.", Toast.LENGTH_SHORT).show();
            finish();
        }
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
