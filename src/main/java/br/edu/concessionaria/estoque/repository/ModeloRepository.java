package br.edu.concessionaria.estoque.repository;

import br.edu.concessionaria.estoque.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    List<Modelo> findByMarcaIdOrderByNomeAsc(Long marcaId);

    boolean existsByMarcaIdAndNomeIgnoreCase(Long marcaId, String nome);

    boolean existsByMarcaIdAndNomeIgnoreCaseAndIdNot(Long marcaId, String nome, Long id);

    boolean existsByMarcaId(Long marcaId);
}
