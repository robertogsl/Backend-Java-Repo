package com.project.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Empresa {

    @Id
    private String cnpj;

    private String nome;

    private Double saldo = 0.0;

    private Double taxa;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNomesa(String nome) {
        this.nome = nome;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo += saldo;
    }

    public Double getTaxa() {
        return taxa;
    }

    public void setTaxa(Double taxa) {
        this.taxa = taxa;
    }

    public Boolean depositar(Empresa empresa, Double valor) {
        Double porcentagem = 100.00 - empresa.getTaxa();
        Double valorReal = valor * porcentagem;
        this.saldo += valorReal;

        return true;
    }

    public Boolean sacar(Empresa empresa, Double valor) {
        Double porcentagem = 100.00 - empresa.getTaxa();
        Double valorReal = valor * porcentagem;

        if (valor > saldo) {
            return false;
        }

        this.saldo -= valorReal;

        return true;
    }
}
