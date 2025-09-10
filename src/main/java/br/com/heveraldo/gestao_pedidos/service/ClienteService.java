package br.com.heveraldo.gestao_pedidos.service;

import br.com.heveraldo.gestao_pedidos.dto.ClienteRequestDTO;
import br.com.heveraldo.gestao_pedidos.model.CentroDistribuicao;
import br.com.heveraldo.gestao_pedidos.model.Cliente;
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

    public Cliente criarCliente(ClienteRequestDTO dto) {
        CentroDistribuicao cd = cdRepository.findById(dto.getCentroDistribuicaoId())
                .orElseThrow(() -> new RuntimeException("Centro de Distribuição não encontrado"));

        Cliente novoCliente = new Cliente();
        novoCliente.setRazaoSocial(dto.getRazaoSocial());
        novoCliente.setCnpj(dto.getCnpj());
        novoCliente.setLogradouro(dto.getLogradouro());
        novoCliente.setNumero(dto.getNumero());
        novoCliente.setCidade(dto.getCidade());
        novoCliente.setCep(dto.getCep());
        novoCliente.setCentroDistribuicao(cd);

        return clienteRepository.save(novoCliente);
    }
}