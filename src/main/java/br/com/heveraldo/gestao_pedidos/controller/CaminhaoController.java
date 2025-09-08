package br.com.heveraldo.gestao_pedidos.controller;

import br.com.heveraldo.gestao_pedidos.model.Caminhao;
import br.com.heveraldo.gestao_pedidos.repository.CaminhaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caminhoes")
public class CaminhaoController {

    @Autowired
    private CaminhaoRepository caminhaoRepository;

    @PostMapping
    public Caminhao criarCaminhao(@RequestBody Caminhao caminhao) {
        return caminhaoRepository.save(caminhao);
    }

    @GetMapping
    public List<Caminhao> listarCaminhoes() {
        return caminhaoRepository.findAll();
    }
}