package br.com.heveraldo.gestao_pedidos.model;

import br.com.heveraldo.gestao_pedidos.dto.ViaCepClientRespose;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Embeddable
@Data
@AllArgsConstructor
public class Endereco {
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public Endereco() {

    }

    public Endereco(ViaCepClientRespose enderecoResponse) {
        this.estado = enderecoResponse.getState();
        this.cidade = enderecoResponse.getCity();
        this.bairro = enderecoResponse.getNeighborhood();
        this.logradouro = enderecoResponse.getStreet();
        this.cep = enderecoResponse.getCep();
    }
}
