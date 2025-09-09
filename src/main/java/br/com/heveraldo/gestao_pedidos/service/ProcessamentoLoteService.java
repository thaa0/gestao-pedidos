package br.com.heveraldo.gestao_pedidos.service;

import br.com.heveraldo.gestao_pedidos.model.*; 
import br.com.heveraldo.gestao_pedidos.repository.CaminhaoRepository;
import br.com.heveraldo.gestao_pedidos.repository.MotoristaRepository;
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
    @Autowired
    private MotoristaRepository motoristaRepository; 

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void processarPedidosPendentes() {
        log.info("--- INICIANDO ROTINA DE PROCESSAMENTO E DISTRIBUIÇÃO ---");

        List<Pedido> pedidosPendentes = pedidoRepository.findByStatus(StatusPedido.AGUARDANDO_PROCESSAMENTO);

        if (pedidosPendentes.isEmpty()) {
            log.info("Nenhum pedido pendente encontrado.");
            return;
        }
        log.info(">>> {} pedidos encontrados para processamento.", pedidosPendentes.size());

        Optional<Caminhao> caminhaoOpt = caminhaoRepository.findFirstByDisponivelTrue();
        if (caminhaoOpt.isEmpty()) {
            log.warn("Nenhum caminhão disponível. Pedidos permanecerão pendentes.");
            return;
        }

        Optional<Motorista> motoristaOpt = motoristaRepository.findFirstByDisponivelTrue();
        if (motoristaOpt.isEmpty()) {
            log.warn("Nenhum motorista disponível. Pedidos permanecerão pendentes.");
            return;
        }

        Caminhao caminhao = caminhaoOpt.get();
        Motorista motorista = motoristaOpt.get();
        log.info("Caminhão {} e Motorista {} selecionados para a rota.", caminhao.getPlaca(), motorista.getNome());

        Rota novaRota = new Rota();
        novaRota.setCaminhao(caminhao);
        novaRota.setMotorista(motorista); 
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
        motorista.setDisponivel(false);
        caminhaoRepository.save(caminhao);
        motoristaRepository.save(motorista);

        log.info("--- DISTRIBUIÇÃO CONCLUÍDA. Rota ID {} criada. ---", novaRota.getId());
    }
}