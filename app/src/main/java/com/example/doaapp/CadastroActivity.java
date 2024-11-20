package com.example.doaapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CadastroActivity extends AppCompatActivity {
    private EditText edtNome, edtDescricao, edtCategoria;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNome = findViewById(R.id.edtNome);
        edtDescricao = findViewById(R.id.edtDescricao);
        edtCategoria = findViewById(R.id.edtCategoria);
        dbHelper = new DatabaseHelper(this);

        findViewById(R.id.btnSalvar).setOnClickListener(v -> salvarItem());
    }

    private void salvarItem() {
        String nome = edtNome.getText().toString();
        String descricao = edtDescricao.getText().toString();
        String categoria = edtCategoria.getText().toString();

        if (nome.isEmpty() || descricao.isEmpty() || categoria.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        long resultado = dbHelper.adicionarItem(nome, descricao, categoria);
        if (resultado > 0) {
            Toast.makeText(this, "Item salvo com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao salvar item.", Toast.LENGTH_SHORT).show();
        }
    }
}
