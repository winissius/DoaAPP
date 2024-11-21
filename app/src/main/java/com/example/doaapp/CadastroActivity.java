package com.example.doaapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;

public class CadastroActivity extends AppCompatActivity {
    private EditText edtNome, edtDescricao;
    private Spinner spinnerCategoria;
    private DatabaseHelper dbHelper;
    private String categoriaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNome = findViewById(R.id.edtNome);
        edtDescricao = findViewById(R.id.edtDescricao);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        dbHelper = new DatabaseHelper(this);

        configurarSpinner();

        findViewById(R.id.btnSalvar).setOnClickListener(v -> salvarItem());
    }

    private void configurarSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.categorias_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoriaSelecionada = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoriaSelecionada = null;
            }
        });
    }

    private void salvarItem() {
        String nome = edtNome.getText().toString();
        String descricao = edtDescricao.getText().toString();

        if (nome.isEmpty() || descricao.isEmpty() || categoriaSelecionada == null) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        long resultado = dbHelper.adicionarItem(nome, descricao, categoriaSelecionada);
        if (resultado > 0) {
            Toast.makeText(this, "Item salvo com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao salvar item.", Toast.LENGTH_SHORT).show();
        }
    }
}
