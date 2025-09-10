package br.com.heveraldo.gestao_pedidos.service;

import br.com.heveraldo.gestao_pedidos.dto.EstoqueRequestDTO;
import br.com.heveraldo.gestao_pedidos.model.CentroDistribuicao;
import br.com.heveraldo.gestao_pedidos.model.Estoque;
import br.com.heveraldo.gestao_pedidos.model.Produto;
import br.com.heveraldo.gestao_pedidos.repository.CentroDistribuicaoRepository;
import br.com.heveraldo.gestao_pedidos.repository.EstoqueRepository;
import br.com.heveraldo.gestao_pedidos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CentroDistribuicaoRepository cdRepository;

    public Estoque atualizarEstoque(EstoqueRequestDTO dto) {
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        CentroDistribuicao cd = cdRepository.findById(dto.getCdId())
                .orElseThrow(() -> new RuntimeException("CD não encontrado"));

        Optional<Estoque> estoqueExistenteOpt = estoqueRepository.findByProdutoAndCentroDistribuicao(produto, cd);

        Estoque estoque;
        if (estoqueExistenteOpt.isPresent()) {
            estoque = estoqueExistenteOpt.get();
            estoque.setQuantidade(estoque.getQuantidade() + dto.getQuantidade()); 
        } else {
            
            estoque = new Estoque();
            estoque.setProduto(produto);
            estoque.setCentroDistribuicao(cd);
            estoque.setQuantidade(dto.getQuantidade());
        }
        
        return estoqueRepository.save(estoque);
    }
}