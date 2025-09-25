package com.fiap.bank_api.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.fiap.bank_api.domain.Conta;
import com.fiap.bank_api.dto.MovimentoRequest;
import com.fiap.bank_api.dto.NovaContaRequest;
import com.fiap.bank_api.dto.PixRequest;
import com.fiap.bank_api.service.ContaService;

import java.util.List;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    private final ContaService service;

    public ContaController(ContaService service) {
        this.service = service;
    }

    // cadastrar conta (H2) + validações -> 20% + 20%
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Conta criar(@Valid @RequestBody NovaContaRequest req) {
        return service.criar(req);
    }

    // listar todas -> 10%
    @GetMapping
    public List<Conta> listar() {
        return service.listar();
    }

    // buscar por id -> 10%
    @GetMapping("/{id}")
    public Conta porId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // encerrar conta (marcar inativa) -> 10%
    @PostMapping("/{id}/encerrar")
    public Conta encerrar(@PathVariable Long id) {
        return service.encerrar(id);
    }

    // depósito -> 10%
    @PostMapping("/deposito")
    public Conta depositar(@Valid @RequestBody MovimentoRequest req) {
        return service.depositar(req);
    }

    // saque -> 10%
    @PostMapping("/saque")
    public Conta sacar(@Valid @RequestBody MovimentoRequest req) {
        return service.sacar(req);
    }

    // pix -> 20%
    @PostMapping("/pix")
    public Conta pix(@Valid @RequestBody PixRequest req) {
        return service.pix(req);
    }
    
}
