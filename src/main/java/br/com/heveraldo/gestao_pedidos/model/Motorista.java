package br.com.heveraldo.gestao_pedidos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Motorista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cnh; 
    private boolean disponivel = true;

    @ManyToOne
    @JoinColumn(name = "cd_id", nullable = false) 
    private CentroDistribuicao centroDistribuicao;
    
    public Long getId() { 
        return id; }

    public void setId(Long id) { 
        this.id = id; }

    public String getNome() { 
        return nome; }

    public void setNome(String nome) { 
        this.nome = nome; }

    public String getCnh() { 
        return cnh; }

    public void setCnh(String cnh) { 
        this.cnh = cnh; }

    public boolean isDisponivel() { 
        return disponivel; }
        
    public void setDisponivel(boolean disponivel) { 
        this.disponivel = disponivel; }

    public CentroDistribuicao getCentroDistribuicao() { 
        return centroDistribuicao; }

    public void setCentroDistribuicao(CentroDistribuicao centroDistribuicao) { 
        this.centroDistribuicao = centroDistribuicao; }

}