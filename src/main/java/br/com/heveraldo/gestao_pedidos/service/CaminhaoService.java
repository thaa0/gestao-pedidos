package br.com.heveraldo.gestao_pedidos.service;

import br.com.heveraldo.gestao_pedidos.dto.CaminhaoRequestDTO;
import br.com.heveraldo.gestao_pedidos.model.Caminhao;
import br.com.heveraldo.gestao_pedidos.model.CentroDistribuicao;
import br.com.heveraldo.gestao_pedidos.repository.CaminhaoRepository;
import br.com.heveraldo.gestao_pedidos.repository.CentroDistribuicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaminhaoService {

    @Autowired
    private CaminhaoRepository caminhaoRepository;
    @Autowired
    private CentroDistribuicaoRepository cdRepository;

    public Caminhao criarCaminhao(CaminhaoRequestDTO dto) {
        CentroDistribuicao cd = cdRepository.findById(dto.getCentroDistribuicaoId())
                .orElseThrow(() -> new RuntimeException("Centro de Distribuição não encontrado"));

        Caminhao novoCaminhao = new Caminhao();
        novoCaminhao.setPlaca(dto.getPlaca());
        novoCaminhao.setCapacidadePesoKg(dto.getCapacidadePesoKg());
        novoCaminhao.setCapacidadeVolumeM3(dto.getCapacidadeVolumeM3());
        novoCaminhao.setCentroDistribuicao(cd);
        novoCaminhao.setDisponivel(true);

        return caminhaoRepository.save(novoCaminhao);
    }
}