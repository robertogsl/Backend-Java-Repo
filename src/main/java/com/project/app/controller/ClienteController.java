package com.project.app.controller;

import com.project.app.model.Cliente;
import com.project.app.repository.ClienteRepository;
import com.project.app.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    SmsService smsService;

    @CrossOrigin
    @PostMapping("/registrar")
    public ResponseEntity registrarCliente(@RequestBody Cliente newCliente) {

        Cliente cliente = clienteRepository.findByCpf(newCliente.getCpf());

        if (cliente == null) {
            if (validarCpf(newCliente.getCpf())) {
                clienteRepository.save(newCliente);

                return ResponseEntity.status(201).build();
            }
            return ResponseEntity.status(400).body("Por favor informe um CPF válido");
        }

        return ResponseEntity.status(400).body("Usuário já cadastrado");
    }

    public Boolean validarCpf(String cpf) {

        if (cpf.length() != 11) {
            return false;
        }

        Integer result = 0;

        for (int i = 1; i <= 9; i++) {
            Integer soma = Character.getNumericValue(cpf.charAt(i-1)) * i;

            result += soma;
        }

        result = result % 11;

        if (result >= 10) {
            if (result.toString().charAt(1) != cpf.charAt(9)) {
                return false;
            }
        }
        else if(result.toString().charAt(0) != cpf.charAt(9)) {
            return false;
        }

        result = 0;

        for (int i = 0; i < 10; i++) {
            Integer soma = Character.getNumericValue(cpf.charAt(i)) * i;

            result += soma;
        }

        result = result % 11;

        if (result >= 10) {
            if (result.toString().charAt(1) != cpf.charAt(10)) {
                return false;
            }
        }
        else if(result.toString().charAt(0) != cpf.charAt(10)) {
            return false;
        }

        return true;
    }

    @CrossOrigin
    @GetMapping("/{cpf}")
    public ResponseEntity getCliente(@PathVariable String cpf) {

        Cliente cliente = clienteRepository.findByCpf(cpf);

        if (cliente != null) {
            return ResponseEntity.status(200).body(cliente);
        }

        return ResponseEntity.status(204).build();
    }
}
