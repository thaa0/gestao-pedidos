package br.com.heveraldo.gestao_pedidos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CentroDistribuicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String logradouro;
    private String numero;     
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public Long getId() { 
        return id; }
        
    public void setId(Long id) { 
        this.id = id; }

    public String getNome() { 
        return nome; }

    public void setNome(String nome) { 
        this.nome = nome; }

    public String getCidade() { 
        return cidade; }

    public void setCidade(String cidade) { 
        this.cidade = cidade; }

    public String getEstado() { 
        return estado; }

    public void setEstado(String estado) { 
        this.estado = estado; }

    public String getCep() { 
        return cep; }

    public void setCep(String cep) { 
        this.cep = cep; }
}