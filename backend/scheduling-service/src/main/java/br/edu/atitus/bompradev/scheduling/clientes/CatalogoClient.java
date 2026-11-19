package br.edu.atitus.bompradev.scheduling.clientes;

import br.edu.atitus.bompradev.scheduling.dtos.ServicoCatalogo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import java.time.Duration;
import java.util.UUID;

@Component
public class CatalogoClient {
    private final WebClient cliente;

    public CatalogoClient(WebClient.Builder builder, @Value("${CATALOG_SERVICE_URL:http://catalog-service}") String url) {
        this.cliente = builder.baseUrl(url).build();
    }

    public ServicoCatalogo consultar(UUID servicoId) {
        try {
            var servico = cliente.get().uri("/catalogo/servicos/{id}", servicoId).retrieve()
                    .bodyToMono(ServicoCatalogo.class).block(Duration.ofSeconds(5));
            if (servico == null || !servicoId.equals(servico.id()) || servico.providerId() == null
                    || servico.duracaoMinutos() == null || servico.duracaoMinutos() <= 0) {
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resposta inválida do catálogo");
            }
            return servico;
        } catch (WebClientResponseException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Catálogo indisponível", e);
        }
    }
}
