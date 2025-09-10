package br.com.heveraldo.gestao_pedidos.service;

import br.com.heveraldo.gestao_pedidos.model.*;
import br.com.heveraldo.gestao_pedidos.repository.*;
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

@Service
public class ProcessamentoLoteService {

    private static final Logger log = LoggerFactory.getLogger(ProcessamentoLoteService.class);

    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private CaminhaoRepository caminhaoRepository;
    @Autowired private RotaRepository rotaRepository;
    @Autowired private MotoristaRepository motoristaRepository;
    
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void processarPedidosPorCD() {
        log.info("--- INICIANDO ROTINA GERAL DE PROCESSAMENTO POR CD ---");
        
        List<CentroDistribuicao> cdsComPedidos = pedidoRepository.findDistinctCDsComPedidosPendentes(StatusPedido.AGUARDANDO_PROCESSAMENTO);

        if (cdsComPedidos.isEmpty()) {
            log.info("Nenhum CD com pedidos pendentes encontrado.");
            return;
        }

        for (CentroDistribuicao cd : cdsComPedidos) {
            log.info("================ Processando CD: {} ================", cd.getNome());
            processarPedidosDeUmCD(cd);
        }
        
        log.info("--- ROTINA GERAL FINALIZADA ---");
    }

    private void processarPedidosDeUmCD(CentroDistribuicao cd) {
        List<Pedido> pedidosPendentesDoCD = pedidoRepository.findByCliente_CentroDistribuicaoAndStatus(cd, StatusPedido.AGUARDANDO_PROCESSAMENTO);
        
        while (!pedidosPendentesDoCD.isEmpty()) {
            Caminhao caminhao = caminhaoRepository.findFirstByCentroDistribuicaoAndDisponivelTrue(cd).orElse(null);
            Motorista motorista = motoristaRepository.findFirstByCentroDistribuicaoAndDisponivelTrue(cd).orElse(null);

            if (caminhao == null || motorista == null) {
                log.warn("Recursos insuficientes no CD {}. Caminhão ou motorista não disponível. {} pedidos restantes.", cd.getNome(), pedidosPendentesDoCD.size());
                break; 
            }

            log.info("CD {}: Caminhão {} e Motorista {} selecionados.", cd.getNome(), caminhao.getPlaca(), motorista.getNome());

            List<Pedido> pedidosParaEstaRota = new ArrayList<>();
            double pesoAtual = 0.0;
            double volumeAtual = 0.0;
            
            for (Pedido pedido : new ArrayList<>(pedidosPendentesDoCD)) {
                double pesoDoPedido = 0.0;
                double volumeDoPedido = 0.0;

                for (ItemPedido item : pedido.getItens()) {
                    pesoDoPedido += item.getQuantidade() * item.getProduto().getPesoKg();
                    volumeDoPedido += item.getQuantidade() * item.getProduto().getVolumeM3();
                }

                if ((pesoAtual + pesoDoPedido <= caminhao.getCapacidadePesoKg()) && (volumeAtual + volumeDoPedido <= caminhao.getCapacidadeVolumeM3())) {
                    pedidosParaEstaRota.add(pedido);
                    pedidosPendentesDoCD.remove(pedido); 
                    pesoAtual += pesoDoPedido;
                    volumeAtual += volumeDoPedido;
                }
            }

            if (!pedidosParaEstaRota.isEmpty()) {
                Rota novaRota = new Rota();
                novaRota.setCaminhao(caminhao);
                novaRota.setMotorista(motorista);
                novaRota.setDataRota(LocalDate.now());
                novaRota.setStatus("PLANEJADA");
                rotaRepository.save(novaRota);

                for (Pedido p : pedidosParaEstaRota) {
                    p.setStatus(StatusPedido.EM_ROTA_DE_ENTREGA);
                    p.setRota(novaRota);
                    pedidoRepository.save(p);
                }
                caminhao.setDisponivel(false);
                motorista.setDisponivel(false);
                caminhaoRepository.save(caminhao);
                motoristaRepository.save(motorista);

                log.info("CD {}: Rota {} criada com {} pedidos.", cd.getNome(), novaRota.getId(), pedidosParaEstaRota.size());
            } else {
                log.warn("CD {}: Nenhum pedido coube no caminhão {}. Loop interrompido.", cd.getNome(), caminhao.getPlaca());
                break;
            }
        }
    }
}