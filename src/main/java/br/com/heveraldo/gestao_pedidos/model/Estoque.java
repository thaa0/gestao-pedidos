package br.com.heveraldo.gestao_pedidos.model;

import jakarta.persistence.*;

@Entity
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "cd_id", nullable = false)
    private CentroDistribuicao centroDistribuicao;

    private int quantidade;

    public Long getId() { 
        return id; }
        
    public void setId(Long id) { 
        this.id = id; }

    public Produto getProduto() { 
        return produto; }

    public void setProduto(Produto produto) { 
        this.produto = produto; }

    public CentroDistribuicao getCentroDistribuicao() { 
        return centroDistribuicao; }

    public void setCentroDistribuicao(CentroDistribuicao centroDistribuicao) { 
        this.centroDistribuicao = centroDistribuicao; }

    public int getQuantidade() { 
        return quantidade; }

    public void setQuantidade(int quantidade) { 
        this.quantidade = quantidade; }
}