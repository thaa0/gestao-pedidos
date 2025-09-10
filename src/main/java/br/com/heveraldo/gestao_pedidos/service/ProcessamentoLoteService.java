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
import java.util.ArrayList;
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
        log.info(">>> {} pedidos pendentes encontrados no total.", pedidosPendentes.size());

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
        log.info("Caminhão {} (Capacidade: {}kg) e Motorista {} selecionados.", 
                 caminhao.getPlaca(), caminhao.getCapacidadePesoKg(), motorista.getNome());

        List<Pedido> pedidosParaEstaRota = new ArrayList<>();
        double pesoAtual = 0.0;
        double volumeAtual = 0.0;

        for (Pedido pedido : pedidosPendentes) {
            double pesoDoPedido = 0.0;
            double volumeDoPedido = 0.0;

            for (ItemPedido item : pedido.getItens()) {
                Produto produto = item.getProduto();
                int quantidade = item.getQuantidade();
                
                int paletesCheios = 0;
                int unidadesSoltas = quantidade;

                if (produto.getUnidadesPorPalete() > 0 && quantidade >= produto.getUnidadesPorPalete()) {
                    paletesCheios = quantidade / produto.getUnidadesPorPalete();
                    unidadesSoltas = quantidade % produto.getUnidadesPorPalete();
                }

                pesoDoPedido += (paletesCheios * produto.getPesoPorPaleteKg()) + (unidadesSoltas * produto.getPesoKg());
                volumeDoPedido += (paletesCheios * produto.getVolumePorPaleteM3()) + (unidadesSoltas * produto.getVolumeM3());
            }

            if ((pesoAtual + pesoDoPedido <= caminhao.getCapacidadePesoKg()) &&
                (volumeAtual + volumeDoPedido <= caminhao.getCapacidadeVolumeM3())) {
                
                pedidosParaEstaRota.add(pedido);
                pesoAtual += pesoDoPedido;
                volumeAtual += volumeDoPedido;
                log.info("Pedido ID {} (Peso Total: {}kg) adicionado à rota.", pedido.getId(), String.format("%.2f", pesoDoPedido));
            } else {
                log.warn("Pedido ID {} (Peso Total: {}kg) não coube no caminhão e ficará para a próxima rota.", pedido.getId(), String.format("%.2f", pesoDoPedido));
            }
        }

        if (!pedidosParaEstaRota.isEmpty()) {
            Rota novaRota = new Rota();
            novaRota.setCaminhao(caminhao);
            novaRota.setMotorista(motorista);
            novaRota.setDataRota(LocalDate.now());
            novaRota.setStatus("PLANEJADA");
            rotaRepository.save(novaRota);

            for (Pedido pedidoDaRota : pedidosParaEstaRota) {
                pedidoDaRota.setStatus(StatusPedido.EM_ROTA_DE_ENTREGA);
                pedidoDaRota.setDataProcessamento(LocalDateTime.now());
                pedidoDaRota.setRota(novaRota);
                pedidoRepository.save(pedidoDaRota);
            }

            caminhao.setDisponivel(false);
            motorista.setDisponivel(false);
            caminhaoRepository.save(caminhao);
            motoristaRepository.save(motorista);

            log.info("--- DISTRIBUIÇÃO CONCLUÍDA. Rota ID {} criada com {} pedidos. ---", novaRota.getId(), pedidosParaEstaRota.size());
        } else {
            log.info("--- NENHUM PEDIDO COUBE NO CAMINHÃO DISPONÍVEL. NENHUMA ROTA CRIADA. ---");
        }
    }
}