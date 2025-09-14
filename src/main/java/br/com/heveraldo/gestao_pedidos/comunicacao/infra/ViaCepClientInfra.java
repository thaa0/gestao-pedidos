package br.com.heveraldo.gestao_pedidos.comunicacao.infra;

import br.com.heveraldo.gestao_pedidos.dto.ViaCepClientRespose;
import br.com.heveraldo.gestao_pedidos.comunicacao.service.ViaCepClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Log4j2
@Component
public class ViaCepClientInfra implements ViaCepClient {

    private final WebClient webClient;

    public ViaCepClientInfra(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public ViaCepClientRespose consultaCep(String cep) {
        String url = geraUrlRequisicao(cep);
        ViaCepClientRespose response = null;
        try {
            response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(ViaCepClientRespose.class)
                    .doOnSuccess(request -> log.info("[sucess] Operação realizada com sucesso ao grupo profissão programador."))
                    .block();
            log.info("Busca feita com sucesso!");
        } catch (WebClientResponseException e) {
            String errorMessage = e.getResponseBodyAsString();
            log.error("[error] Erro ao enviar mensagem: {}", errorMessage);
        } catch (Exception e) {
            log.error("[error] Erro inesperado ao enviar mensagem: {}", e.getMessage());
        }
        return response;
    }

    private String geraUrlRequisicao(String cep) {
        return "https://brasilapi.com.br/api/cep/v1/"+cep;
    }
}
