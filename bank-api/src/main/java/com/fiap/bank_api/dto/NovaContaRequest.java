package com.fiap.bank_api.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fiap.bank_api.domain.TipoConta;

public class NovaContaRequest {

    @NotBlank private String numero;
    @NotBlank private String agencia;

    @NotBlank private String nomeTitular;

    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    private String cpfTitular;

    @NotNull
    @PastOrPresent(message = "Data de abertura não pode ser no futuro")
    private LocalDate dataAbertura;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true, message = "Saldo inicial não pode ser negativo")
    private BigDecimal saldoInicial;

    @NotNull private Boolean ativa;

    @NotNull private TipoConta tipo;

    // getters/setters
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getAgencia() { return agencia; }
    public void setAgencia(String agencia) { this.agencia = agencia; }
    public String getNomeTitular() { return nomeTitular; }
    public void setNomeTitular(String nomeTitular) { this.nomeTitular = nomeTitular; }
    public String getCpfTitular() { return cpfTitular; }
    public void setCpfTitular(String cpfTitular) { this.cpfTitular = cpfTitular; }
    public LocalDate getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDate dataAbertura) { this.dataAbertura = dataAbertura; }
    public BigDecimal getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(BigDecimal saldoInicial) { this.saldoInicial = saldoInicial; }
    public Boolean getAtiva() { return ativa; }
    public void setAtiva(Boolean ativa) { this.ativa = ativa; }
    public TipoConta getTipo() { return tipo; }
    public void setTipo(TipoConta tipo) { this.tipo = tipo; }
}
