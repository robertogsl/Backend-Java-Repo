package com.project.app.controller;

import com.project.app.model.Cliente;
import com.project.app.model.Empresa;
import com.project.app.repository.ClienteRepository;
import com.project.app.repository.EmpresaRepository;
import com.project.app.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    EmpresaRepository empresaRepository;
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    SmsService smsService;

    @CrossOrigin
    @PostMapping("/registrar")
    public ResponseEntity registrarEmpresa(@RequestBody Empresa newEmpresa) {

        Empresa empresa = empresaRepository.findByCnpj(newEmpresa.getCnpj());

        if (empresa == null) {
            if (validarCnpj(newEmpresa.getCnpj())) {
                empresaRepository.save(newEmpresa);

                return ResponseEntity.status(201).build();
            }
            return ResponseEntity.status(400).body("Por favor informe um CNPJ válido");
        }

        return ResponseEntity.status(400).body("Empresa já cadastrado");
    }

    public Boolean validarCnpj(String cnpj) {

        if (cnpj.length() != 14) {
            return false;
        }

        Integer result = 0;
        Integer contador = 5;

        for (int i = 0; i < 12; i++) {
            Integer soma = Character.getNumericValue(cnpj.charAt(i)) * contador;

            result += soma;
            if (contador == 2) {
                contador = 9;
            }
            else {
                contador--;
            }
        }

        result = result % 11;
        Integer caracter = Character.getNumericValue(cnpj.charAt(12));

        if (result < 2) {
            if (caracter != 0) {
                return false;
            }
        }
        else if(caracter != (11 - result)) {
            System.out.println(11 - result);
            System.out.println(caracter);
            System.out.println(caracter != (11 - result));
            return false;
        }

        result = 0;
        contador = 6;

        for (int i = 0; i < 13; i++) {
            Integer soma = Character.getNumericValue(cnpj.charAt(i)) * contador;

            result += soma;
            if (contador == 2) {
                contador = 9;
            }
            else {
                contador--;
            }
        }

        result = result % 11;
        caracter = Character.getNumericValue(cnpj.charAt(13));

        if (result < 2) {
            if (caracter != 0) {
                return false;
            }
        }
        else if(caracter != (11 - result)) {
            return false;
        }

        return true;
    }

    @CrossOrigin
    @GetMapping("/{cnpj}")
    public ResponseEntity getEmpresa(@PathVariable("cnpj") String cnpj) {

        List<Empresa> empresas = empresaRepository.findAll();

        if (empresas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        for (Empresa e : empresas) {
            if (e.getCnpj().equals(cnpj)) {
                return ResponseEntity.status(200).body(e);
            }
        }

        return ResponseEntity.status(204).build();
    }

    @CrossOrigin
    @PostMapping("/{cnpj}/depositar/{valor}/{cpf}")
    public ResponseEntity postDeposito(@PathVariable String cnpj, @PathVariable Double valor, @PathVariable String cpf) {

        Empresa empresa = empresaRepository.findByCnpj(cnpj);
        Cliente cliente = clienteRepository.findByCpf(cpf);

        if (empresa == null || cliente == null) {
            return ResponseEntity.status(404).build();
        }

        try {
            String mensagem = "Transação do tipo Depósito realizada com sucesso no valor de R$"+valor+" para a empresa "+empresa.getNome();
            smsService.enviarSms(cliente.getTelefone(), mensagem);

            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            System.out.println("Erro na transação!" + e);

            String mensagem = "Erro ao realizar transação do tipo Depósito no valor de R$"+valor+" para a empresa "+empresa.getNome();
            smsService.enviarSms(cliente.getTelefone(), mensagem);
            return ResponseEntity.status(500).build();
        }
    }

    @CrossOrigin
    @PostMapping("/{cnpj}/sacar/{valor}/{cpf}")
    public ResponseEntity postSacar(@PathVariable String cnpj, @PathVariable Double valor, @PathVariable String cpf) {

        Empresa empresa = empresaRepository.findByCnpj(cnpj);
        Cliente cliente = clienteRepository.findByCpf(cpf);

        if (empresa == null || cliente == null) {
            return ResponseEntity.status(404).build();
        }

        try {
            if (empresa.sacar(empresa, valor)) {
                String mensagem = "Transação do tipo Saque realizada com sucesso no valor de R$"+valor+" para a empresa "+empresa.getNome();
                smsService.enviarSms(cliente.getTelefone(), mensagem);

                return ResponseEntity.status(200).build();
            }

            String mensagem = "Erro ao realizar transação do tipo Saque no valor de R$"+valor+" para a empresa "+empresa.getNome()+" pois não há saldo suficiente!";
            smsService.enviarSms(cliente.getTelefone(), mensagem);

            return ResponseEntity.status(400).build();

        } catch (Exception e) {
            System.out.println("Erro na transação!" + e);

            String mensagem = "Erro ao realizar transação do tipo Saque no valor de R$"+valor+" para a empresa "+empresa.getNome();
            smsService.enviarSms(cliente.getTelefone(), mensagem);
            return ResponseEntity.status(500).build();
        }
    }
}
