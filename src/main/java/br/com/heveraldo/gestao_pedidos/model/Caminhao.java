package br.com.heveraldo.gestao_pedidos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Caminhao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placa;
    private boolean disponivel = true;
    private CentroDistribuicao centroDistribuicao;
    private double capacidadePesoKg;
    private double capacidadeVolumeM3;

    public Long getId() { 
        return id; }

    public void setId(Long id) { 
        this.id = id; }

    public String getPlaca() { 
        return placa; }

    public void setPlaca(String placa) { 
        this.placa = placa; }

    public boolean isDisponivel() { 
        return disponivel; }

    public void setDisponivel(boolean disponivel) { 
        this.disponivel = disponivel; }

    public double getCapacidadePesoKg() { 
        return capacidadePesoKg; }

    public void setCapacidadePesoKg(double capacidadePesoKg) { 
        this.capacidadePesoKg = capacidadePesoKg; }

    public double getCapacidadeVolumeM3() { 
        return capacidadeVolumeM3; }

    public void setCapacidadeVolumeM3(double capacidadeVolumeM3) { 
        this.capacidadeVolumeM3 = capacidadeVolumeM3; }

    public CentroDistribuicao getCentroDistribuicao() { 
        return centroDistribuicao; }

    public void setCentroDistribuicao(CentroDistribuicao centroDistribuicao) { 
        this.centroDistribuicao = centroDistribuicao; }

}