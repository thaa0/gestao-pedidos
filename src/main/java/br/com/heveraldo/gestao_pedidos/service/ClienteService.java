package br.com.heveraldo.gestao_pedidos.service;

import br.com.heveraldo.gestao_pedidos.dto.ViaCepClientRespose;
import br.com.heveraldo.gestao_pedidos.comunicacao.service.ViaCepClient;
import br.com.heveraldo.gestao_pedidos.dto.ClienteRequestDTO;
import br.com.heveraldo.gestao_pedidos.model.CentroDistribuicao;
import br.com.heveraldo.gestao_pedidos.model.Cliente;
import br.com.heveraldo.gestao_pedidos.model.Endereco;
import br.com.heveraldo.gestao_pedidos.repository.CentroDistribuicaoRepository;
import br.com.heveraldo.gestao_pedidos.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CentroDistribuicaoRepository cdRepository;
    @Autowired
    private ViaCepClient viaCepClient;

    public Cliente criarCliente(ClienteRequestDTO dto) {
        CentroDistribuicao centroDistribuicao = getCentroDistribuicao(dto);
        Endereco endereco = buscaEnderecoPeloCep(dto.getCep());
        Cliente novoCliente = new Cliente(dto, endereco, centroDistribuicao);
        return clienteRepository.save(novoCliente);
    }

    private CentroDistribuicao getCentroDistribuicao(ClienteRequestDTO dto) {
        return cdRepository.findById(dto.getCentroDistribuicaoId())
                .orElseThrow(() -> new RuntimeException("Centro de Distribuição não encontrado"));
    }

    private Endereco buscaEnderecoPeloCep(String cep) {
        ViaCepClientRespose enderecoResponse = viaCepClient.consultaCep(cep);
        return new Endereco(enderecoResponse);
    }
}