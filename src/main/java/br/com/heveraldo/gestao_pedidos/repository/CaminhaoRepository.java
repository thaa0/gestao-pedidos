package br.com.heveraldo.gestao_pedidos.repository;

import br.com.heveraldo.gestao_pedidos.model.Caminhao;
import br.com.heveraldo.gestao_pedidos.model.CentroDistribuicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaminhaoRepository extends JpaRepository<Caminhao, Long> {

    Optional<Caminhao> findFirstByCentroDistribuicaoAndDisponivelTrue(CentroDistribuicao centroDistribuicao);
}