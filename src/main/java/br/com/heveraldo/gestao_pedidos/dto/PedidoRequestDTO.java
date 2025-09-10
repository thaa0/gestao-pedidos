package br.com.heveraldo.gestao_pedidos.dto;

import java.util.List;

public class PedidoRequestDTO {
    private Long clienteId;
    private List<ItemPedidoRequestDTO> itens;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemPedidoRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoRequestDTO> itens) {
        this.itens = itens;
    }
}