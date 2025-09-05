package br.com.heveraldo.gestao_pedidos.controller;

import br.com.heveraldo.gestao_pedidos.dto.PedidoRequestDTO;
import br.com.heveraldo.gestao_pedidos.model.Pedido;
import br.com.heveraldo.gestao_pedidos.repository.PedidoRepository;
import br.com.heveraldo.gestao_pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    
    @Autowired
    private PedidoRepository pedidoRepository;

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        try {
            Pedido novoPedido = pedidoService.criarPedido(pedidoRequestDTO);
            return ResponseEntity.ok(novoPedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}