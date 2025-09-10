package br.com.heveraldo.gestao_pedidos.controller;

import br.com.heveraldo.gestao_pedidos.model.CentroDistribuicao;
import br.com.heveraldo.gestao_pedidos.repository.CentroDistribuicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cds")
public class CentroDistribuicaoController {

    @Autowired
    private CentroDistribuicaoRepository cdRepository;

    @PostMapping
    public CentroDistribuicao criarCD(@RequestBody CentroDistribuicao centroDistribuicao) {
        return cdRepository.save(centroDistribuicao);
    }

    @GetMapping
    public List<CentroDistribuicao> listarCDs() {
        return cdRepository.findAll();
    }
}