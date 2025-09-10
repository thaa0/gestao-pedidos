package br.com.heveraldo.gestao_pedidos.controller;

import br.com.heveraldo.gestao_pedidos.dto.EstoqueRequestDTO;
import br.com.heveraldo.gestao_pedidos.model.Estoque;
import br.com.heveraldo.gestao_pedidos.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @PostMapping("/adicionar")
    public Estoque adicionarEstoque(@RequestBody EstoqueRequestDTO estoqueRequestDTO) {
        return estoqueService.atualizarEstoque(estoqueRequestDTO);
    }
}