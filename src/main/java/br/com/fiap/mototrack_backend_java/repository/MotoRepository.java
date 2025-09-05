package br.com.fiap.mototrack_backend_java.repository;

import br.com.fiap.mototrack_backend_java.model.Moto;
import br.com.fiap.mototrack_backend_java.model.enums.ModeloMoto;
import br.com.fiap.mototrack_backend_java.model.enums.Status;
import br.com.fiap.mototrack_backend_java.model.enums.TipoDepartamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {
    @Query("""
    SELECT m FROM Moto m
    WHERE (:placa IS NULL OR LOWER(m.placa) LIKE LOWER(CONCAT('%', :placa, '%')))
      AND (:chassi IS NULL OR LOWER(m.chassi) LIKE LOWER(CONCAT('%', :chassi, '%')))
      AND (:modelo IS NULL OR m.modelo = :modelo)
      AND (:status IS NULL OR m.status = :status)
    ORDER BY m.id ASC
    """)
    List<Moto> findByFiltros(
            @Param("placa") String placa,
            @Param("chassi") String chassi,
            @Param("modelo") ModeloMoto modelo,
            @Param("status") Status status
    );

    @Query("""
    SELECT COUNT(m)
    FROM Moto m
    JOIN m.movimentacoes mov
    WHERE mov.dataMovimentacao = (
        SELECT MAX(m2.dataMovimentacao)
        FROM Movimentacao m2
        WHERE m2.moto = m
    )
    AND mov.departamento.tipo = :tipo
    """)
    long countByDepartamentoAtual(@Param("tipo") TipoDepartamento tipo);

    long count();

    boolean existsByPlacaIgnoreCase(String placa);

    boolean existsByChassiIgnoreCase(String chassi);
}
