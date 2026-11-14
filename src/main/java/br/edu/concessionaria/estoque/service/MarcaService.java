package br.edu.concessionaria.estoque.service;

import br.edu.concessionaria.estoque.dto.MarcaRequest;
import br.edu.concessionaria.estoque.dto.MarcaResponse;
import br.edu.concessionaria.estoque.exception.RecursoNaoEncontradoException;
import br.edu.concessionaria.estoque.exception.RegraNegocioException;
import br.edu.concessionaria.estoque.model.Marca;
import br.edu.concessionaria.estoque.repository.MarcaRepository;
import br.edu.concessionaria.estoque.repository.ModeloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MarcaService {

    private final MarcaRepository marcaRepository;
    private final ModeloRepository modeloRepository;

    public MarcaService(MarcaRepository marcaRepository, ModeloRepository modeloRepository) {
        this.marcaRepository = marcaRepository;
        this.modeloRepository = modeloRepository;
    }

    @Transactional
    public MarcaResponse criar(MarcaRequest req) {
        if (marcaRepository.existsByNomeIgnoreCase(req.nome().trim())) {
            throw new RegraNegocioException("Ja existe uma marca chamada '" + req.nome() + "'");
        }
        Marca marca = new Marca(req.nome().trim(), req.paisOrigem());
        return MarcaResponse.de(marcaRepository.save(marca));
    }

    @Transactional(readOnly = true)
    public List<MarcaResponse> listar(String nome) {
        List<Marca> marcas = (nome == null || nome.isBlank())
                ? marcaRepository.findAll()
                : marcaRepository.findByNomeContainingIgnoreCaseOrderByNomeAsc(nome);
        return marcas.stream().map(MarcaResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public MarcaResponse buscarPorId(Long id) {
        return MarcaResponse.de(obter(id));
    }

    @Transactional
    public MarcaResponse atualizar(Long id, MarcaRequest req) {
        Marca marca = obter(id);
        if (marcaRepository.existsByNomeIgnoreCaseAndIdNot(req.nome().trim(), id)) {
            throw new RegraNegocioException("Ja existe uma marca chamada '" + req.nome() + "'");
        }
        marca.setNome(req.nome().trim());
        marca.setPaisOrigem(req.paisOrigem());
        return MarcaResponse.de(marcaRepository.save(marca));
    }

    @Transactional
    public void remover(Long id) {
        Marca marca = obter(id);
        if (modeloRepository.existsByMarcaId(id)) {
            throw new RegraNegocioException("Nao e possivel remover a marca: existem modelos vinculados a ela");
        }
        marcaRepository.delete(marca);
    }

    private Marca obter(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Marca", id));
    }
}
