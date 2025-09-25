package com.fiap.bank_api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PixRequest {

    @NotNull private Long idOrigem;
    @NotNull private Long idDestino;

    @NotNull
    @DecimalMin(value = "0.01", message = "Valor deve ser positivo")
    private BigDecimal valor;

    public Long getIdOrigem() { return idOrigem; }
    public void setIdOrigem(Long idOrigem) { this.idOrigem = idOrigem; }
    public Long getIdDestino() { return idDestino; }
    public void setIdDestino(Long idDestino) { this.idDestino = idDestino; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    

}
