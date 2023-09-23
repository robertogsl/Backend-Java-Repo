package com.project.app.repository;

import com.project.app.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Cliente findByCpf(String cpf);
}
