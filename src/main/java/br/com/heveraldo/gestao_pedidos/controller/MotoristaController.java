package br.com.heveraldo.gestao_pedidos.controller;

import br.com.heveraldo.gestao_pedidos.model.Motorista;
import br.com.heveraldo.gestao_pedidos.repository.MotoristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motoristas")
public class MotoristaController {

    @Autowired
    private MotoristaRepository motoristaRepository;

    @PostMapping
    public Motorista criarMotorista(@RequestBody Motorista motorista) {
        return motoristaRepository.save(motorista);
    }

    @GetMapping
    public List<Motorista> listarMotoristas() {
        return motoristaRepository.findAll();
    }
}