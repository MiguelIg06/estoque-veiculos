package br.edu.concessionaria.estoque.dto;

import br.edu.concessionaria.estoque.model.Marca;

public record MarcaResponse(Long id, String nome, String paisOrigem) {

    public static MarcaResponse de(Marca marca) {
        return new MarcaResponse(marca.getId(), marca.getNome(), marca.getPaisOrigem());
    }
}
