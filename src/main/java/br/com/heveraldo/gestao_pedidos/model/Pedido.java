package br.com.heveraldo.gestao_pedidos.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    private LocalDateTime dataCriacao;
    private LocalDateTime dataProcessamento;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    private double valorTotal;

    private String tipoPagamento;
    private LocalDate dataVencimento;

    @ManyToOne
    @JoinColumn(name = "rota_id")
    private Rota rota;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<ItemPedido> getItens() { return itens; }

    public void setItens(List<ItemPedido> itens) { this.itens = itens; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }

    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataProcessamento() { return dataProcessamento; }
    
    public void setDataProcessamento(LocalDateTime dataProcessamento) { this.dataProcessamento = dataProcessamento; }

    public StatusPedido getStatus() { return status; }

    public void setStatus(StatusPedido status) { this.status = status; }

    public double getValorTotal() { return valorTotal; }

    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public Rota getRota() { return rota; }

    public void setRota(Rota rota) { this.rota = rota; }

    public String getTipoPagamento() { return tipoPagamento; }

    public void setTipoPagamento(String tipoPagamento) { this.tipoPagamento = tipoPagamento; }

    public LocalDate getDataVencimento() { return dataVencimento; }

    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
}