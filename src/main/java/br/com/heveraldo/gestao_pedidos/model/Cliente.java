package br.com.heveraldo.gestao_pedidos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String razaoSocial;
    private String cnpj;
    
    private String logradouro;
    private String numero;
    private String cidade;
    private String cep;

    @ManyToOne
    @JoinColumn(name = "cd_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CentroDistribuicao centroDistribuicao;

    public Long getId() { 
        return id; }
        
    public void setId(Long id) { 
        this.id = id; }

    public String getRazaoSocial() { 
        return razaoSocial; }

    public void setRazaoSocial(String razaoSocial) { 
        this.razaoSocial = razaoSocial; }

    public String getCnpj() { 
        return cnpj; }

    public void setCnpj(String cnpj) { 
        this.cnpj = cnpj; }

    public String getLogradouro() { 
        return logradouro; }

    public void setLogradouro(String logradouro) { 
        this.logradouro = logradouro; }

    public String getNumero() { 
        return numero; }

    public void setNumero(String numero) { 
        this.numero = numero; }

    public String getCidade() { 
        return cidade; }

    public void setCidade(String cidade) { 
        this.cidade = cidade; }

    public String getCep() { 
        return cep; }

    public void setCep(String cep) { 
        this.cep = cep; }
}