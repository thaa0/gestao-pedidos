package br.com.heveraldo.gestao_pedidos.dto;

import lombok.Getter;

@Getter
public class ClienteRequestDTO {
    private String razaoSocial;
    private String cnpj;
    private String cep;
    private String numero;
    private Long centroDistribuicaoId;

}