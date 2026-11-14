package br.edu.concessionaria.estoque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modelos")
public class Modelo extends EntidadeBase {

    @Column(nullable = false, length = 80)
    private String nome;

    // Muitos modelos pertencem a uma marca (N:1)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    // Um modelo possui varios veiculos em estoque (1:N)
    @OneToMany(mappedBy = "modelo")
    private List<Veiculo> veiculos = new ArrayList<>();

    public Modelo() {
    }

    public Modelo(String nome, Marca marca) {
        this.nome = nome;
        this.marca = marca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    @Override
    public String toString() {
        return "Modelo{id=" + getId() + ", nome='" + nome + "'}";
    }
}
