package com.example.doaapp;

public class Item {
    private int id;
    private String nome;
    private String descricao;
    private String categoria;
    private String status;

    public Item(int id, String nome, String descricao, String categoria, String status) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.status = status;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getCategoria() { return categoria; }
    public String getStatus() { return status; }
}
