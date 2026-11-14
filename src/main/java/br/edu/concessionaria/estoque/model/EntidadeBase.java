package br.edu.concessionaria.estoque.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Classe base abstrata (HERANCA): concentra o identificador e o
 * equals/hashCode comuns a todas as entidades do sistema.
 */
@MappedSuperclass
public abstract class EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // POLIMORFISMO por sobrescrita de metodos herdados de Object
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntidadeBase outra)) return false;
        return id != null && id.equals(outra.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
