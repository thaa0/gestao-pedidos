package br.com.heveraldo.gestao_pedidos.controller;

import br.com.heveraldo.gestao_pedidos.model.Rota;
import br.com.heveraldo.gestao_pedidos.service.RotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rotas")
public class RotaController {

    @Autowired
    private RotaService rotaService;

    @GetMapping
    public List<Rota> listarRotas() {
        return rotaService.listarTodas();
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<String> finalizarRota(@PathVariable Long id) {
        try {
            rotaService.finalizarRota(id);
            return ResponseEntity.ok("Rota " + id + " finalizada com sucesso. Caminhão e motorista estão disponíveis novamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}