package com.fiap.bank_api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class MovimentoRequest {

    @NotNull
    private Long idConta;

    @NotNull
    @DecimalMin(value = "0.01", message = "Valor deve ser positivo")
    private BigDecimal valor;

    public Long getIdConta() { return idConta; }
    public void setIdConta(Long idConta) { this.idConta = idConta; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    
}
