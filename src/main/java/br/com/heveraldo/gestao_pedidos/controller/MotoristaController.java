package br.com.heveraldo.gestao_pedidos.controller;

import br.com.heveraldo.gestao_pedidos.dto.MotoristaRequestDTO;
import br.com.heveraldo.gestao_pedidos.model.Motorista;
import br.com.heveraldo.gestao_pedidos.repository.MotoristaRepository;
import br.com.heveraldo.gestao_pedidos.service.MotoristaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motoristas")
public class MotoristaController {

    @Autowired
    private MotoristaService motoristaService;

    @Autowired
    private MotoristaRepository motoristaRepository;

    @PostMapping
    public Motorista criarMotorista(@RequestBody MotoristaRequestDTO motoristaRequestDTO) {
        return motoristaService.criarMotorista(motoristaRequestDTO);
    }

    @GetMapping
    public List<Motorista> listarMotoristas() {
        return motoristaRepository.findAll();
    }
}