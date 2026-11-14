package br.edu.concessionaria.estoque.service;

import br.edu.concessionaria.estoque.dto.VeiculoRequest;
import br.edu.concessionaria.estoque.dto.VeiculoResponse;
import br.edu.concessionaria.estoque.exception.RecursoNaoEncontradoException;
import br.edu.concessionaria.estoque.exception.RegraNegocioException;
import br.edu.concessionaria.estoque.model.Modelo;
import br.edu.concessionaria.estoque.model.StatusVeiculo;
import br.edu.concessionaria.estoque.model.Veiculo;
import br.edu.concessionaria.estoque.repository.ModeloRepository;
import br.edu.concessionaria.estoque.repository.VeiculoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Camada de servico: concentra as regras de negocio do estoque.
 * Depende apenas das INTERFACES dos repositorios (abstracao/polimorfismo).
 */
@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ModeloRepository modeloRepository;

    public VeiculoService(VeiculoRepository veiculoRepository, ModeloRepository modeloRepository) {
        this.veiculoRepository = veiculoRepository;
        this.modeloRepository = modeloRepository;
    }

    @Transactional
    public VeiculoResponse salvar(VeiculoRequest req) {
        Veiculo veiculo = new Veiculo();
        preencher(veiculo, req);
        return VeiculoResponse.de(veiculoRepository.save(veiculo));
    }

    @Transactional
    public VeiculoResponse atualizar(Long id, VeiculoRequest req) {
        Veiculo veiculo = obter(id);
        preencher(veiculo, req);
        return VeiculoResponse.de(veiculoRepository.save(veiculo));
    }

    @Transactional
    public VeiculoResponse atualizarStatus(Long id, StatusVeiculo novoStatus) {
        Veiculo veiculo = obter(id);
        if (veiculo.getStatus() == StatusVeiculo.VENDIDO && novoStatus != StatusVeiculo.VENDIDO) {
            throw new RegraNegocioException("Um veiculo ja vendido nao pode voltar ao estoque");
        }
        veiculo.setStatus(novoStatus);
        return VeiculoResponse.de(veiculoRepository.save(veiculo));
    }

    @Transactional(readOnly = true)
    public VeiculoResponse buscarPorId(Long id) {
        return VeiculoResponse.de(obter(id));
    }

    @Transactional(readOnly = true)
    public Page<VeiculoResponse> filtrar(String marca, String modelo,
                                         BigDecimal precoMin, BigDecimal precoMax,
                                         Integer anoMin, Integer anoMax,
                                         StatusVeiculo status, Pageable pageable) {
        if (precoMin != null && precoMax != null && precoMin.compareTo(precoMax) > 0) {
            throw new RegraNegocioException("O preco minimo nao pode ser maior que o preco maximo");
        }
        if (anoMin != null && anoMax != null && anoMin > anoMax) {
            throw new RegraNegocioException("O ano minimo nao pode ser maior que o ano maximo");
        }
        return veiculoRepository
                .filtrar(vazioParaNulo(marca), vazioParaNulo(modelo),
                        precoMin, precoMax, anoMin, anoMax, status, pageable)
                .map(VeiculoResponse::de);
    }

    @Transactional
    public void remover(Long id) {
        veiculoRepository.delete(obter(id));
    }

    // ----------------- metodos auxiliares -----------------
    private void preencher(Veiculo veiculo, VeiculoRequest req) {
        Modelo modelo = modeloRepository.findById(req.modeloId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Modelo", req.modeloId()));
        veiculo.setModelo(modelo);
        veiculo.setAno(req.ano());
        veiculo.setCor(req.cor().trim());
        veiculo.setPreco(req.preco());
        veiculo.setQuilometragem(req.quilometragem());
        veiculo.setStatus(req.status());
    }

    private Veiculo obter(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Veiculo", id));
    }

    private String vazioParaNulo(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor.trim();
    }
}
