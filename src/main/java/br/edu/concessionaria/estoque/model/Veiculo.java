package br.edu.concessionaria.estoque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "veiculos")
public class Veiculo extends EntidadeBase {

    // Muitos veiculos pertencem a um modelo (N:1)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "modelo_id", nullable = false)
    private Modelo modelo;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false, length = 30)
    private String cor;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer quilometragem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private StatusVeiculo status = StatusVeiculo.DISPONIVEL;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    public Veiculo() {
    }

    @PrePersist
    private void aoCriar() {
        if (dataCadastro == null) {
            dataCadastro = LocalDateTime.now();
        }
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(Integer quilometragem) {
        this.quilometragem = quilometragem;
    }

    public StatusVeiculo getStatus() {
        return status;
    }

    public void setStatus(StatusVeiculo status) {
        this.status = status;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    @Override
    public String toString() {
        return "Veiculo{id=" + getId() + ", ano=" + ano + ", cor='" + cor + "', preco=" + preco + ", status=" + status + "}";
    }
}
