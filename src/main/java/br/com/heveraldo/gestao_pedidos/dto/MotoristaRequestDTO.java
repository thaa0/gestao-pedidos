package br.com.heveraldo.gestao_pedidos.dto;

public class MotoristaRequestDTO {
    private String nome;
    private String cnh;
    private Long centroDistribuicaoId;

    public String getNome() { 
        return nome; }
        
    public void setNome(String nome) { 
        this.nome = nome; }

    public String getCnh() { 
        return cnh; }

    public void setCnh(String cnh) { 
        this.cnh = cnh; }

    public Long getCentroDistribuicaoId() { 
        return centroDistribuicaoId; }

    public void setCentroDistribuicaoId(Long centroDistribuicaoId) { 
        this.centroDistribuicaoId = centroDistribuicaoId; }
}