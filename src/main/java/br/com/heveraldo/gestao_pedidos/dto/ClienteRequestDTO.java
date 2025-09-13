package br.com.heveraldo.gestao_pedidos.dto;

import lombok.Getter;

@Getter
public class ClienteRequestDTO {
    private String razaoSocial;
    private String cnpj;
    private String logradouro;
    private String Bairro;
    private String numero;
    private String cidade;
    private String cep;
    private Long centroDistribuicaoId;

}