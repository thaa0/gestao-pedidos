package br.com.heveraldo.gestao_pedidos.repository;

import br.com.seunome.gestaopedidos.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}