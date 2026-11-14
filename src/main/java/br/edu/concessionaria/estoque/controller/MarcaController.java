package br.edu.concessionaria.estoque.controller;

import br.edu.concessionaria.estoque.dto.MarcaRequest;
import br.edu.concessionaria.estoque.dto.MarcaResponse;
import br.edu.concessionaria.estoque.service.MarcaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    private final MarcaService service;

    public MarcaController(MarcaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MarcaResponse> criar(@Valid @RequestBody MarcaRequest req) {
        MarcaResponse criada = service.criar(req);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criada.id()).toUri();
        return ResponseEntity.created(uri).body(criada);
    }

    @GetMapping
    public List<MarcaResponse> listar(@RequestParam(required = false) String nome) {
        return service.listar(nome);
    }

    @GetMapping("/{id}")
    public MarcaResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public MarcaResponse atualizar(@PathVariable Long id, @Valid @RequestBody MarcaRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
