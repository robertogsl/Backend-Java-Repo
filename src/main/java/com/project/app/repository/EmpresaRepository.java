package com.project.app.repository;

import com.project.app.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    Empresa findByCnpj(String cnpj);
}
