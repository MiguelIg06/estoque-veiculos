package br.edu.concessionaria.estoque.controller;

import br.edu.concessionaria.estoque.dto.VeiculoRequest;
import br.edu.concessionaria.estoque.dto.VeiculoResponse;
import br.edu.concessionaria.estoque.model.StatusVeiculo;
import br.edu.concessionaria.estoque.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private final VeiculoService service;

    public VeiculoController(VeiculoService service) {
        this.service = service;
    }

    /** CREATE - POST /api/veiculos */
    @PostMapping
    public ResponseEntity<VeiculoResponse> criar(@Valid @RequestBody VeiculoRequest req) {
        VeiculoResponse criado = service.salvar(req);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criado.id()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    /**
     * READ (lista + filtros) - GET /api/veiculos
     * Exemplo: /api/veiculos?marca=toyota&precoMax=150000&anoMin=2020&status=DISPONIVEL&page=0&size=10
     */
    @GetMapping
    public Page<VeiculoResponse> listar(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax,
            @RequestParam(required = false) Integer anoMin,
            @RequestParam(required = false) Integer anoMax,
            @RequestParam(required = false) StatusVeiculo status,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return service.filtrar(marca, modelo, precoMin, precoMax, anoMin, anoMax, status, pageable);
    }

    /** READ (por id) - GET /api/veiculos/{id} */
    @GetMapping("/{id}")
    public VeiculoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    /** UPDATE (completo) - PUT /api/veiculos/{id} */
    @PutMapping("/{id}")
    public VeiculoResponse atualizar(@PathVariable Long id, @Valid @RequestBody VeiculoRequest req) {
        return service.atualizar(id, req);
    }

    /** UPDATE (parcial) - PATCH /api/veiculos/{id}/status?valor=VENDIDO */
    @PatchMapping("/{id}/status")
    public VeiculoResponse atualizarStatus(@PathVariable Long id, @RequestParam("valor") StatusVeiculo valor) {
        return service.atualizarStatus(id, valor);
    }

    /** DELETE - DELETE /api/veiculos/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
