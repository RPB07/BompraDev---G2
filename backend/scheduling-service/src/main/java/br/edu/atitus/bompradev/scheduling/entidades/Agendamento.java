package br.edu.atitus.bompradev.scheduling.entidades;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "agendamentos")
public class Agendamento {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "servico_id", nullable = false)
    private UUID servicoId;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(name = "provider_id", nullable = false)
    private UUID providerId;
    @Column(nullable = false)
    private Instant inicio;
    @Column(nullable = false)
    private Instant fim;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private SituacaoAgendamento situacao = SituacaoAgendamento.PENDENTE;

    protected Agendamento() {}
    public Agendamento(UUID servicoId, UUID userId, UUID providerId, Instant inicio, Instant fim) {
        this.servicoId = servicoId;
        this.userId = userId;
        this.providerId = providerId;
        this.inicio = inicio;
        this.fim = fim;
    }

    public UUID getId() { return id; }
    public UUID getServicoId() { return servicoId; }
    public UUID getUserId() { return userId; }
    public UUID getProviderId() { return providerId; }
    public Instant getInicio() { return inicio; }
    public Instant getFim() { return fim; }
    public SituacaoAgendamento getSituacao() { return situacao; }
}
