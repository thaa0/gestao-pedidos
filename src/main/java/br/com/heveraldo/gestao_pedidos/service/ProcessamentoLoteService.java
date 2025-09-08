package br.com.heveraldo.gestao_pedidos.service;

import br.com.heveraldo.gestao_pedidos.model.Caminhao;
import br.com.heveraldo.gestao_pedidos.model.Pedido;
import br.com.heveraldo.gestao_pedidos.model.Rota;
import br.com.heveraldo.gestao_pedidos.model.StatusPedido;
import br.com.heveraldo.gestao_pedidos.repository.CaminhaoRepository;
import br.com.heveraldo.gestao_pedidos.repository.PedidoRepository;
import br.com.heveraldo.gestao_pedidos.repository.RotaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProcessamentoLoteService {

    private static final Logger log = LoggerFactory.getLogger(ProcessamentoLoteService.class);

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CaminhaoRepository caminhaoRepository; 

    @Autowired
    private RotaRepository rotaRepository; 

    @Scheduled(cron = "0 * * * * *") // Roda a cada minuto para testes
    @Transactional 
    public void processarPedidosPendentes() {
        log.info("--- INICIANDO ROTINA DE PROCESSAMENTO E DISTRIBUIÇÃO ---");

        List<Pedido> pedidosPendentes = pedidoRepository.findByStatus(StatusPedido.AGUARDANDO_PROCESSAMENTO);

        if (pedidosPendentes.isEmpty()) {
            log.info("Nenhum pedido pendente encontrado.");
            return;
        }

        log.info(">>> {} pedidos encontrados para processamento.", pedidosPendentes.size());

        Optional<Caminhao> caminhaoDisponivelOpt = caminhaoRepository.findFirstByDisponivelTrue();

        if (caminhaoDisponivelOpt.isEmpty()) {
            log.warn("Nenhum caminhão disponível para criar a rota. Pedidos permanecerão pendentes.");
            return;
        }

        Caminhao caminhao = caminhaoDisponivelOpt.get();
        log.info("Caminhão {} (placa: {}) selecionado para a rota.", caminhao.getMotorista(), caminhao.getPlaca());

        Rota novaRota = new Rota();
        novaRota.setCaminhao(caminhao);
        novaRota.setDataRota(LocalDate.now());
        novaRota.setStatus("PLANEJADA");
        rotaRepository.save(novaRota);

        for (Pedido pedido : pedidosPendentes) {
            pedido.setStatus(StatusPedido.EM_ROTA_DE_ENTREGA); 
            pedido.setDataProcessamento(LocalDateTime.now());
            pedido.setRota(novaRota); 
            pedidoRepository.save(pedido);
            log.info("Pedido ID {} alocado para a Rota ID {}.", pedido.getId(), novaRota.getId());
        }

        
        caminhao.setDisponivel(false);
        caminhaoRepository.save(caminhao);

        log.info("--- DISTRIBUIÇÃO CONCLUÍDA. Rota ID {} criada. ---", novaRota.getId());
    }
}