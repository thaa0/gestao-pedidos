package br.com.heveraldo.gestao_pedidos.service;

import br.com.heveraldo.gestao_pedidos.model.Pedido;
import br.com.heveraldo.gestao_pedidos.model.StatusPedido;
import br.com.heveraldo.gestao_pedidos.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProcessamentoLoteService {

    private static final Logger log = LoggerFactory.getLogger(ProcessamentoLoteService.class);

    @Autowired
    private PedidoRepository pedidoRepository;

    /**
     * * EXEMPLO PARA TESTES (roda a cada minuto):
     * cron = "0 * * * * *"
     * * EXEMPLO PARA PRODUÇÃO (roda todo dia às 18h):
     * cron = "0 0 18 * * *"
     */
    @Scheduled(cron = "0 * * * * *") // <-- Configurado para rodar a cada minuto para facilitar os testes
    public void processarPedidosPendentes() {
        log.info("--- INICIANDO ROTINA DE PROCESSAMENTO DE PEDIDOS PENDENTES ---");

        List<Pedido> pedidosPendentes = pedidoRepository.findByStatus(StatusPedido.AGUARDANDO_PROCESSAMENTO);

        if (pedidosPendentes.isEmpty()) {
            log.info("Nenhum pedido pendente encontrado.");
            return;
        }

        log.info(">>> {} pedidos encontrados para processamento.", pedidosPendentes.size());

        for (Pedido pedido : pedidosPendentes) {
            pedido.setStatus(StatusPedido.PROCESSADO);
            pedido.setDataProcessamento(LocalDateTime.now());
            
            pedidoRepository.save(pedido);
            log.info("Pedido ID {} processado com sucesso.", pedido.getId());
        }

        log.info("--- PROCESSAMENTO DE PEDIDOS CONCLUÍDO ---");
    }
}