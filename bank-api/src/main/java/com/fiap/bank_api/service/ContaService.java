package com.fiap.bank_api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.bank_api.domain.Conta;
import com.fiap.bank_api.domain.TipoConta;
import com.fiap.bank_api.dto.MovimentoRequest;
import com.fiap.bank_api.dto.NovaContaRequest;
import com.fiap.bank_api.dto.PixRequest;
import com.fiap.bank_api.exception.NegocioException;
import com.fiap.bank_api.repository.ContaRepository;

import java.math.BigDecimal;

@Service
public class ContaService {

    private final ContaRepository repo;

    public ContaService(ContaRepository repo) {
        this.repo = repo;
    }

    public Conta criar(NovaContaRequest req) {
        // regra simples pra evitar duplicidade de número
        repo.findByNumero(req.getNumero()).ifPresent(c -> {
            throw new NegocioException("Já existe conta com este número");
        });

        Conta c = new Conta();
        c.setNumero(req.getNumero());
        c.setAgencia(req.getAgencia());
        c.setNomeTitular(req.getNomeTitular());
        c.setCpfTitular(req.getCpfTitular());
        c.setDataAbertura(req.getDataAbertura());
        c.setSaldo(req.getSaldoInicial());
        c.setAtiva(req.getAtiva());
        c.setTipo(req.getTipo()); // já validado pelo enum

        return repo.save(c);
    }

    public Conta buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NegocioException("Conta não encontrada"));
    }

    public java.util.List<Conta> listar() {
        return repo.findAll();
    }

    @Transactional
    public Conta encerrar(Long id) {
        Conta c = buscarPorId(id);
        if (!c.isAtiva()) {
            throw new NegocioException("Conta já está inativa");
        }
        c.setAtiva(false);
        return c; // JPA dirty checking salva
    }

    @Transactional
    public Conta depositar(MovimentoRequest req) {
        Conta c = buscarPorId(req.getIdConta());
        if (!c.isAtiva()) throw new NegocioException("Conta inativa");
        c.setSaldo(c.getSaldo().add(req.getValor()));
        return c;
    }

    @Transactional
    public Conta sacar(MovimentoRequest req) {
        Conta c = buscarPorId(req.getIdConta());
        if (!c.isAtiva()) throw new NegocioException("Conta inativa");
        BigDecimal novo = c.getSaldo().subtract(req.getValor());
        if (novo.compareTo(BigDecimal.ZERO) < 0)
            throw new NegocioException("Saldo insuficiente");
        c.setSaldo(novo);
        return c;
    }

    @Transactional
    public Conta pix(PixRequest req) {
        if (req.getIdOrigem().equals(req.getIdDestino()))
            throw new NegocioException("Origem e destino não podem ser a mesma conta");

        Conta origem = buscarPorId(req.getIdOrigem());
        Conta destino = buscarPorId(req.getIdDestino());

        if (!origem.isAtiva()) throw new NegocioException("Conta de origem inativa");
        if (!destino.isAtiva()) throw new NegocioException("Conta de destino inativa");

        BigDecimal novo = origem.getSaldo().subtract(req.getValor());
        if (novo.compareTo(BigDecimal.ZERO) < 0)
            throw new NegocioException("Saldo insuficiente na conta de origem");

        origem.setSaldo(novo);
        destino.setSaldo(destino.getSaldo().add(req.getValor()));
        // salvar ambos (transação garante)
        repo.save(destino);
        return origem;
    }
}
