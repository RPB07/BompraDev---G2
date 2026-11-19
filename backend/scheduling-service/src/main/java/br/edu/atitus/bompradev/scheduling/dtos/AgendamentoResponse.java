package br.edu.atitus.bompradev.scheduling.dtos;

import br.edu.atitus.bompradev.scheduling.entidades.Agendamento;
import br.edu.atitus.bompradev.scheduling.entidades.SituacaoAgendamento;
import java.time.Instant;
import java.util.UUID;

public record AgendamentoResponse(UUID id, UUID servicoId, UUID providerId, Instant inicio, Instant fim,
                                  SituacaoAgendamento situacao) {
    public static AgendamentoResponse de(Agendamento agendamento) {
        return new AgendamentoResponse(agendamento.getId(), agendamento.getServicoId(), agendamento.getProviderId(),
                agendamento.getInicio(), agendamento.getFim(), agendamento.getSituacao());
    }
}
