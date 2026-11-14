package br.edu.concessionaria.estoque.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "marcas")
public class Marca extends EntidadeBase {

    @Column(nullable = false, length = 60, unique = true)
    private String nome;

    @Column(name = "pais_origem", length = 60)
    private String paisOrigem;

    // Uma marca possui varios modelos (1:N)
    @OneToMany(mappedBy = "marca", cascade = CascadeType.PERSIST)
    private List<Modelo> modelos = new ArrayList<>();

    public Marca() {
    }

    public Marca(String nome, String paisOrigem) {
        this.nome = nome;
        this.paisOrigem = paisOrigem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }

    public List<Modelo> getModelos() {
        return modelos;
    }

    @Override
    public String toString() {
        return "Marca{id=" + getId() + ", nome='" + nome + "'}";
    }
}
