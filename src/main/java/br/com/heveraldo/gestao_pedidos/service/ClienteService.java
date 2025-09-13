package br.com.heveraldo.gestao_pedidos.service;

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

    public Cliente criarCliente(ClienteRequestDTO dto) {
        CentroDistribuicao cd = cdRepository.findById(dto.getCentroDistribuicaoId())
                .orElseThrow(() -> new RuntimeException("Centro de Distribuição não encontrado"));

        Endereco endereco = new Endereco(dto.getLogradouro(), dto.getNumero(), dto.getBairro(), dto.getCidade(),"", dto.getCep());
        Cliente novoCliente = new Cliente();
        novoCliente.setRazaoSocial(dto.getRazaoSocial());
        novoCliente.setCnpj(dto.getCnpj());
        novoCliente.setEndereco(endereco);
        novoCliente.setCentroDistribuicao(cd);

        return clienteRepository.save(novoCliente);
    }
}