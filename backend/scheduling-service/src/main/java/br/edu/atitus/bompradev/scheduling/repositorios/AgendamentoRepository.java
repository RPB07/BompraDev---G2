package br.edu.atitus.bompradev.scheduling.repositorios;

import br.edu.atitus.bompradev.scheduling.entidades.Agendamento;
import br.edu.atitus.bompradev.scheduling.entidades.SituacaoAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {
    List<Agendamento> findByProviderIdAndSituacaoInAndInicioLessThanAndFimGreaterThan(
            UUID providerId, Collection<SituacaoAgendamento> situacoes, Instant fim, Instant inicio);
    List<Agendamento> findByUserIdOrderByInicioDesc(UUID userId);
}
