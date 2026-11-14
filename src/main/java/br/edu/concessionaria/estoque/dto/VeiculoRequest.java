package br.edu.concessionaria.estoque.dto;

import br.edu.concessionaria.estoque.model.StatusVeiculo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record VeiculoRequest(
        @NotNull(message = "O modelo e obrigatorio")
        Long modeloId,

        @NotNull(message = "O ano e obrigatorio")
        @Min(value = 1900, message = "Ano invalido")
        @Max(value = 2100, message = "Ano invalido")
        Integer ano,

        @NotBlank(message = "A cor e obrigatoria")
        @Size(max = 30, message = "A cor deve ter no maximo 30 caracteres")
        String cor,

        @NotNull(message = "O preco e obrigatorio")
        @DecimalMin(value = "0.01", message = "O preco deve ser maior que zero")
        BigDecimal preco,

        @NotNull(message = "A quilometragem e obrigatoria")
        @PositiveOrZero(message = "A quilometragem nao pode ser negativa")
        Integer quilometragem,

        @NotNull(message = "O status e obrigatorio (DISPONIVEL, RESERVADO ou VENDIDO)")
        StatusVeiculo status) {
}
