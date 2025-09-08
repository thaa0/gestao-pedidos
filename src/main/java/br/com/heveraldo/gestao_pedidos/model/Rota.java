package br.com.heveraldo.gestao_pedidos.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Rota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "caminhao_id", nullable = false)
    private Caminhao caminhao;

    @OneToMany(mappedBy = "rota")
    private List<Pedido> pedidos = new ArrayList<>();

    private LocalDate dataRota;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Caminhao getCaminhao() { return caminhao; }
    public void setCaminhao(Caminhao caminhao) { this.caminhao = caminhao; }
    public List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }
    public LocalDate getDataRota() { return dataRota; }
    public void setDataRota(LocalDate dataRota) { this.dataRota = dataRota; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}