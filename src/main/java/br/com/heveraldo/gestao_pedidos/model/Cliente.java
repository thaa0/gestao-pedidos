package br.com.heveraldo.gestao_pedidos.model;

import br.com.heveraldo.gestao_pedidos.dto.ClienteRequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String razaoSocial;
    private String cnpj;
    @Embedded
    private Endereco endereco;
    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "cd_id") 
    private CentroDistribuicao centroDistribuicao;

    public Cliente(ClienteRequestDTO dto, Endereco endereco, CentroDistribuicao centroDistribuicao) {
        this.razaoSocial = dto.getRazaoSocial();
        this.cnpj = dto.getCnpj();
        this.endereco = endereco;
        this.centroDistribuicao = centroDistribuicao;
    }

    public Cliente() {

    }
}