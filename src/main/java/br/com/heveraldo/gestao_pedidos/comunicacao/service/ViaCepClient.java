package br.com.heveraldo.gestao_pedidos.comunicacao.service;

import br.com.heveraldo.gestao_pedidos.dto.ViaCepClientRespose;

public interface ViaCepClient {
    ViaCepClientRespose consultaCep(String cep);
}
