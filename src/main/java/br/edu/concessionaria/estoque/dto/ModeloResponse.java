package br.edu.concessionaria.estoque.dto;

import br.edu.concessionaria.estoque.model.Modelo;

public record ModeloResponse(Long id, String nome, Long marcaId, String marca) {

    public static ModeloResponse de(Modelo modelo) {
        return new ModeloResponse(modelo.getId(), modelo.getNome(),
                modelo.getMarca().getId(), modelo.getMarca().getNome());
    }
}
