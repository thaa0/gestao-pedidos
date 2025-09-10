package br.com.heveraldo.gestao_pedidos.service;

import br.com.heveraldo.gestao_pedidos.dto.ItemPedidoRequestDTO;
import br.com.heveraldo.gestao_pedidos.dto.PedidoRequestDTO;
import br.com.heveraldo.gestao_pedidos.model.*;
import br.com.heveraldo.gestao_pedidos.repository.ClienteRepository;
import br.com.heveraldo.gestao_pedidos.repository.EstoqueRepository; 
import br.com.heveraldo.gestao_pedidos.repository.PedidoRepository;
import br.com.heveraldo.gestao_pedidos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private EstoqueRepository estoqueRepository;

    @Transactional
    public Pedido criarPedido(PedidoRequestDTO pedidoRequestDTO) {
        Cliente cliente = clienteRepository.findById(pedidoRequestDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + pedidoRequestDTO.getClienteId()));
        CentroDistribuicao cd = cliente.getCentroDistribuicao();

        Pedido novoPedido = new Pedido();
        novoPedido.setCliente(cliente);
        novoPedido.setDataCriacao(LocalDateTime.now());
        novoPedido.setStatus(StatusPedido.AGUARDANDO_PROCESSAMENTO);
        novoPedido.setTipoPagamento("BOLETO_15_DIAS");
        novoPedido.setDataVencimento(LocalDate.now().plusDays(15));

        double valorTotalCalculado = 0.0;
        List<ItemPedido> itensDoPedido = new ArrayList<>();

        for (ItemPedidoRequestDTO itemDTO : pedidoRequestDTO.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + itemDTO.getProdutoId()));

            Estoque estoqueDoProdutoNoCD = estoqueRepository.findByProdutoAndCentroDistribuicao(produto, cd)
                    .orElseThrow(() -> new RuntimeException("Estoque não encontrado para o produto " + produto.getNome() + " no CD " + cd.getNome()));

            if (estoqueDoProdutoNoCD.getQuantidade() < itemDTO.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome() + ". Disponível: " + estoqueDoProdutoNoCD.getQuantidade());
            }

            estoqueDoProdutoNoCD.setQuantidade(estoqueDoProdutoNoCD.getQuantidade() - itemDTO.getQuantidade());
            estoqueRepository.save(estoqueDoProdutoNoCD);

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(novoPedido);
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemDTO.getQuantidade());
            itemPedido.setPrecoUnitario(produto.getPreco());
            
            itensDoPedido.add(itemPedido);
            valorTotalCalculado += (itemDTO.getQuantidade() * produto.getPreco());
        }

        novoPedido.setItens(itensDoPedido);
        novoPedido.setValorTotal(valorTotalCalculado);

        return pedidoRepository.save(novoPedido);
    }
}