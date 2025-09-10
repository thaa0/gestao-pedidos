package br.com.heveraldo.gestao_pedidos.repository;

import br.com.heveraldo.gestao_pedidos.model.CentroDistribuicao;
import br.com.heveraldo.gestao_pedidos.model.Pedido;
import br.com.heveraldo.gestao_pedidos.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByStatus(StatusPedido status);

    // Encontra todos os pedidos pendentes de um CD específico
    List<Pedido> findByCliente_CentroDistribuicaoAndStatus(CentroDistribuicao centroDistribuicao, StatusPedido status);

    // Encontra quais CDs têm pedidos pendentes
    @Query("SELECT DISTINCT p.cliente.centroDistribuicao FROM Pedido p WHERE p.status = :status")
    List<CentroDistribuicao> findDistinctCDsComPedidosPendentes(StatusPedido status);
}