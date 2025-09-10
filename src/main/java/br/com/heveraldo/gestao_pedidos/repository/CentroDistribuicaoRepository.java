package br.com.heveraldo.gestao_pedidos.repository;

import br.com.heveraldo.gestao_pedidos.model.CentroDistribuicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentroDistribuicaoRepository extends JpaRepository<CentroDistribuicao, Long> {
}