package br.com.heveraldo.gestao_pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ViaCepClientRespose {
    private String cep;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String service;
}