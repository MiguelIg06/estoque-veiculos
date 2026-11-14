package br.edu.concessionaria.estoque.dto;

import br.edu.concessionaria.estoque.model.StatusVeiculo;
import br.edu.concessionaria.estoque.model.Veiculo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VeiculoResponse(
        Long id,
        Long modeloId,
        String modelo,
        String marca,
        Integer ano,
        String cor,
        BigDecimal preco,
        Integer quilometragem,
        StatusVeiculo status,
        LocalDateTime dataCadastro) {

    public static VeiculoResponse de(Veiculo v) {
        return new VeiculoResponse(
                v.getId(),
                v.getModelo().getId(),
                v.getModelo().getNome(),
                v.getModelo().getMarca().getNome(),
                v.getAno(),
                v.getCor(),
                v.getPreco(),
                v.getQuilometragem(),
                v.getStatus(),
                v.getDataCadastro());
    }
}
