package br.com.heveraldo.gestao_pedidos.dto;

public class CaminhaoRequestDTO {
    private String placa;
    private double capacidadePesoKg;
    private double capacidadeVolumeM3;
    private Long centroDistribuicaoId;

    public String getPlaca() { 
        return placa; }
        
    public void setPlaca(String placa) { 
        this.placa = placa; }

    public double getCapacidadePesoKg() { 
        return capacidadePesoKg; }

    public void setCapacidadePesoKg(double capacidadePesoKg) { 
        this.capacidadePesoKg = capacidadePesoKg; }

    public double getCapacidadeVolumeM3() { 
        return capacidadeVolumeM3; }

    public void setCapacidadeVolumeM3(double capacidadeVolumeM3) { 
        this.capacidadeVolumeM3 = capacidadeVolumeM3; }

    public Long getCentroDistribuicaoId() { 
        return centroDistribuicaoId; }

    public void setCentroDistribuicaoId(Long centroDistribuicaoId) { 
        this.centroDistribuicaoId = centroDistribuicaoId; }
}