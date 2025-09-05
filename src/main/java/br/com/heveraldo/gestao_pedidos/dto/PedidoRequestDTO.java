package br.com.heveraldo.gestao_pedidos.dto;

public class PedidoRequestDTO {
    private Long clienteId;
    private ItemPedidoRequestDTO[] itens;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public ItemPedidoRequestDTO[] getItens() {
        return itens;
    }

    public void setItens(ItemPedidoRequestDTO[] itens) {
        this.itens = itens;
    }
}
