package br.com.heveraldo.gestao_pedidos.dto;

public class EstoqueRequestDTO {
    private Long produtoId;
    private Long cdId;
    private int quantidade;

    public Long getProdutoId() { 
        return produtoId; }
        
    public void setProdutoId(Long produtoId) { 
        this.produtoId = produtoId; }

    public Long getCdId() { 
        return cdId; }

    public void setCdId(Long cdId) { 
        this.cdId = cdId; }

    public int getQuantidade() { 
        return quantidade; }

    public void setQuantidade(int quantidade) { 
        this.quantidade = quantidade; }
}