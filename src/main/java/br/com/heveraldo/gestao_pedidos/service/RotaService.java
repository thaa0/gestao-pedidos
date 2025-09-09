package br.com.heveraldo.gestao_pedidos.service;

import br.com.heveraldo.gestao_pedidos.model.Caminhao;
import br.com.heveraldo.gestao_pedidos.model.Motorista;
import br.com.heveraldo.gestao_pedidos.model.Rota;
import br.com.heveraldo.gestao_pedidos.repository.CaminhaoRepository;
import br.com.heveraldo.gestao_pedidos.repository.MotoristaRepository;
import br.com.heveraldo.gestao_pedidos.repository.RotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RotaService {

    @Autowired
    private RotaRepository rotaRepository;
    @Autowired
    private CaminhaoRepository caminhaoRepository;
    @Autowired
    private MotoristaRepository motoristaRepository;

    public List<Rota> listarTodas() {
        return rotaRepository.findAll();
    }

    @Transactional
    public void finalizarRota(Long rotaId) {
        Rota rota = rotaRepository.findById(rotaId)
                .orElseThrow(() -> new RuntimeException("Rota não encontrada com ID: " + rotaId));

        Caminhao caminhao = rota.getCaminhao();
        caminhao.setDisponivel(true);
        caminhaoRepository.save(caminhao);

        Motorista motorista = rota.getMotorista();
        motorista.setDisponivel(true);
        motoristaRepository.save(motorista);

        rota.setStatus("CONCLUIDA");
        rotaRepository.save(rota);
    }
}