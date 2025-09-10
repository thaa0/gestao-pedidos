package br.com.heveraldo.gestao_pedidos.repository;

import br.com.heveraldo.gestao_pedidos.model.Motorista;
import br.com.heveraldo.gestao_pedidos.model.CentroDistribuicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista, Long> {

    Optional<Motorista> findFirstByCentroDistribuicaoAndDisponivelTrue(CentroDistribuicao centroDistribuicao);
}