package br.edu.concessionaria.estoque.controller;

import br.edu.concessionaria.estoque.dto.ModeloRequest;
import br.edu.concessionaria.estoque.dto.ModeloResponse;
import br.edu.concessionaria.estoque.service.ModeloService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/modelos")
public class ModeloController {

    private final ModeloService service;

    public ModeloController(ModeloService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ModeloResponse> criar(@Valid @RequestBody ModeloRequest req) {
        ModeloResponse criado = service.criar(req);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criado.id()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    @GetMapping
    public List<ModeloResponse> listar(@RequestParam(required = false) Long marcaId) {
        return service.listar(marcaId);
    }

    @GetMapping("/{id}")
    public ModeloResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ModeloResponse atualizar(@PathVariable Long id, @Valid @RequestBody ModeloRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
