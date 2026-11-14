package br.edu.concessionaria.estoque.service;

import br.edu.concessionaria.estoque.dto.ModeloRequest;
import br.edu.concessionaria.estoque.dto.ModeloResponse;
import br.edu.concessionaria.estoque.exception.RecursoNaoEncontradoException;
import br.edu.concessionaria.estoque.exception.RegraNegocioException;
import br.edu.concessionaria.estoque.model.Marca;
import br.edu.concessionaria.estoque.model.Modelo;
import br.edu.concessionaria.estoque.repository.MarcaRepository;
import br.edu.concessionaria.estoque.repository.ModeloRepository;
import br.edu.concessionaria.estoque.repository.VeiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModeloService {

    private final ModeloRepository modeloRepository;
    private final MarcaRepository marcaRepository;
    private final VeiculoRepository veiculoRepository;

    public ModeloService(ModeloRepository modeloRepository,
                         MarcaRepository marcaRepository,
                         VeiculoRepository veiculoRepository) {
        this.modeloRepository = modeloRepository;
        this.marcaRepository = marcaRepository;
        this.veiculoRepository = veiculoRepository;
    }

    @Transactional
    public ModeloResponse criar(ModeloRequest req) {
        Marca marca = obterMarca(req.marcaId());
        if (modeloRepository.existsByMarcaIdAndNomeIgnoreCase(marca.getId(), req.nome().trim())) {
            throw new RegraNegocioException("A marca " + marca.getNome()
                    + " ja possui o modelo '" + req.nome() + "'");
        }
        Modelo modelo = new Modelo(req.nome().trim(), marca);
        return ModeloResponse.de(modeloRepository.save(modelo));
    }

    @Transactional(readOnly = true)
    public List<ModeloResponse> listar(Long marcaId) {
        List<Modelo> modelos = (marcaId == null)
                ? modeloRepository.findAll()
                : modeloRepository.findByMarcaIdOrderByNomeAsc(marcaId);
        return modelos.stream().map(ModeloResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public ModeloResponse buscarPorId(Long id) {
        return ModeloResponse.de(obter(id));
    }

    @Transactional
    public ModeloResponse atualizar(Long id, ModeloRequest req) {
        Modelo modelo = obter(id);
        Marca marca = obterMarca(req.marcaId());
        if (modeloRepository.existsByMarcaIdAndNomeIgnoreCaseAndIdNot(marca.getId(), req.nome().trim(), id)) {
            throw new RegraNegocioException("A marca " + marca.getNome()
                    + " ja possui o modelo '" + req.nome() + "'");
        }
        modelo.setNome(req.nome().trim());
        modelo.setMarca(marca);
        return ModeloResponse.de(modeloRepository.save(modelo));
    }

    @Transactional
    public void remover(Long id) {
        Modelo modelo = obter(id);
        if (veiculoRepository.existsByModeloId(id)) {
            throw new RegraNegocioException("Nao e possivel remover o modelo: existem veiculos vinculados a ele");
        }
        modeloRepository.delete(modelo);
    }

    private Modelo obter(Long id) {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Modelo", id));
    }

    private Marca obterMarca(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Marca", id));
    }
}
