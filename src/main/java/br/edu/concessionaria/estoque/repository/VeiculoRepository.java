package br.edu.concessionaria.estoque.repository;

import br.edu.concessionaria.estoque.model.StatusVeiculo;
import br.edu.concessionaria.estoque.model.Veiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    // ---- Consultas derivadas (Spring Data cria o SQL a partir do nome do metodo) ----
    List<Veiculo> findByStatus(StatusVeiculo status);

    List<Veiculo> findByModeloId(Long modeloId);

    List<Veiculo> findByModeloMarcaId(Long marcaId);

    List<Veiculo> findByAnoGreaterThanEqual(Integer ano);

    List<Veiculo> findByPrecoBetween(BigDecimal minimo, BigDecimal maximo);

    boolean existsByModeloId(Long modeloId);

    // ---- Filtro combinado: todos os parametros sao opcionais (null = ignora o filtro) ----
    @Query("""
            SELECT v FROM Veiculo v
            JOIN v.modelo m
            JOIN m.marca ma
            WHERE (:marca IS NULL OR LOWER(ma.nome) LIKE LOWER(CONCAT('%', :marca, '%')))
              AND (:modelo IS NULL OR LOWER(m.nome) LIKE LOWER(CONCAT('%', :modelo, '%')))
              AND (:precoMin IS NULL OR v.preco >= :precoMin)
              AND (:precoMax IS NULL OR v.preco <= :precoMax)
              AND (:anoMin IS NULL OR v.ano >= :anoMin)
              AND (:anoMax IS NULL OR v.ano <= :anoMax)
              AND (:status IS NULL OR v.status = :status)
            """)
    Page<Veiculo> filtrar(@Param("marca") String marca,
                          @Param("modelo") String modelo,
                          @Param("precoMin") BigDecimal precoMin,
                          @Param("precoMax") BigDecimal precoMax,
                          @Param("anoMin") Integer anoMin,
                          @Param("anoMax") Integer anoMax,
                          @Param("status") StatusVeiculo status,
                          Pageable pageable);
}
