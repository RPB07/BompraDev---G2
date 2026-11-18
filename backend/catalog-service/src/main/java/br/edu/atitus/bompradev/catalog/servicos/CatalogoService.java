package br.edu.atitus.bompradev.catalog.servicos;

import br.edu.atitus.bompradev.catalog.dtos.CategoriaResponse;
import br.edu.atitus.bompradev.catalog.dtos.ServicoRequest;
import br.edu.atitus.bompradev.catalog.dtos.ServicoResponse;
import br.edu.atitus.bompradev.catalog.entidades.Categoria;
import br.edu.atitus.bompradev.catalog.entidades.Servico;
import br.edu.atitus.bompradev.catalog.entidades.SituacaoServico;
import br.edu.atitus.bompradev.catalog.repositorios.CategoriaRepository;
import br.edu.atitus.bompradev.catalog.repositorios.ServicoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class CatalogoService {
    private final CategoriaRepository categorias;
    private final ServicoRepository servicos;

    public CatalogoService(CategoriaRepository categorias, ServicoRepository servicos) {
        this.categorias = categorias;
        this.servicos = servicos;
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponse> listarCategorias() {
        return categorias.findByAtivaTrueOrderByNomeAsc().stream().map(CategoriaResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public List<ServicoResponse> listar(UUID categoriaId) {
        var encontrados = categoriaId == null
                ? servicos.findBySituacaoOrderByTituloAsc(SituacaoServico.APROVADO)
                : servicos.findBySituacaoAndCategoriaIdOrderByTituloAsc(SituacaoServico.APROVADO, categoriaId);
        return encontrados.stream().map(ServicoResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public ServicoResponse consultar(UUID id) {
        return servicos.findById(id).filter(servico -> servico.getSituacao() == SituacaoServico.APROVADO)
                .map(ServicoResponse::de)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<ServicoResponse> listarDoProvider(UUID providerId) {
        return servicos.findByProviderIdOrderByTituloAsc(providerId).stream().map(ServicoResponse::de).toList();
    }

    @Transactional
    public ServicoResponse cadastrar(UUID providerId, ServicoRequest dados) {
        var servico = new Servico(providerId, buscarCategoria(dados.categoriaId()), dados.titulo().strip(),
                dados.descricao().strip(), dados.valor(), dados.duracaoMinutos());
        return ServicoResponse.de(servicos.save(servico));
    }

    @Transactional
    public ServicoResponse atualizar(UUID id, UUID providerId, ServicoRequest dados) {
        var servico = servicos.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));
        if (!servico.getProviderId().equals(providerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Serviço pertence a outro prestador");
        }
        if (servico.getSituacao() != SituacaoServico.APROVADO) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Serviço indisponível para edição");
        }
        servico.atualizar(buscarCategoria(dados.categoriaId()), dados.titulo().strip(),
                dados.descricao().strip(), dados.valor(), dados.duracaoMinutos());
        return ServicoResponse.de(servico);
    }

    private Categoria buscarCategoria(UUID id) {
        return categorias.findById(id).filter(Categoria::isAtiva)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria indisponível"));
    }
}
