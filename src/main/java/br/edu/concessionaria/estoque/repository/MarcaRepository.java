package br.edu.concessionaria.estoque.repository;

import br.edu.concessionaria.estoque.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

    boolean existsByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);

    List<Marca> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);
}
