package br.com.heveraldo.gestao_pedidos.dto;

public class ClienteRequestDTO {
    private String razaoSocial;
    private String cnpj;
    private String logradouro;
    private String numero;
    private String cidade;
    private String cep;
    private Long centroDistribuicaoId;

    
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

    public Long getCentroDistribuicaoId() { 
        return centroDistribuicaoId; }

    public void setCentroDistribuicaoId(Long centroDistribuicaoId) { 
        this.centroDistribuicaoId = centroDistribuicaoId; }
}