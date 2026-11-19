package br.edu.atitus.bompradev.scheduling.servicos;

import br.edu.atitus.bompradev.scheduling.clientes.CatalogoClient;
import br.edu.atitus.bompradev.scheduling.dtos.AgendamentoRequest;
import br.edu.atitus.bompradev.scheduling.dtos.AgendamentoResponse;
import br.edu.atitus.bompradev.scheduling.dtos.ServicoCatalogo;
import br.edu.atitus.bompradev.scheduling.entidades.Agendamento;
import br.edu.atitus.bompradev.scheduling.entidades.SituacaoAgendamento;
import br.edu.atitus.bompradev.scheduling.repositorios.AgendamentoRepository;
import br.edu.atitus.bompradev.scheduling.repositorios.DisponibilidadeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AgendamentoService {
    private static final ZoneId FUSO = ZoneId.of("America/Sao_Paulo");
    private static final List<SituacaoAgendamento> OCUPADOS = List.of(SituacaoAgendamento.PENDENTE, SituacaoAgendamento.APROVADO);
    private final CatalogoClient catalogo;
    private final DisponibilidadeRepository disponibilidades;
    private final AgendamentoRepository agendamentos;
    private final JdbcTemplate jdbc;

    public AgendamentoService(CatalogoClient catalogo, DisponibilidadeRepository disponibilidades,
                              AgendamentoRepository agendamentos, JdbcTemplate jdbc) {
        this.catalogo = catalogo;
        this.disponibilidades = disponibilidades;
        this.agendamentos = agendamentos;
        this.jdbc = jdbc;
    }

    @Transactional(readOnly = true)
    public List<LocalTime> horarios(UUID servicoId, LocalDate data) {
        return horariosDisponiveis(catalogo.consultar(servicoId), data);
    }

    @Transactional
    public AgendamentoResponse solicitar(UUID userId, AgendamentoRequest dados) {
        var servico = catalogo.consultar(dados.servicoId());
        jdbc.query("SELECT pg_advisory_xact_lock(hashtext(?))", rs -> null, servico.providerId().toString());
        if (!horariosDisponiveis(servico, dados.data()).contains(dados.horario())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Horário indisponível");
        }
        Instant inicio = dados.data().atTime(dados.horario()).atZone(FUSO).toInstant();
        Instant fim = inicio.plus(Duration.ofMinutes(servico.duracaoMinutos()));
        return AgendamentoResponse.de(agendamentos.save(new Agendamento(servico.id(), userId, servico.providerId(), inicio, fim)));
    }

    @Transactional(readOnly = true)
    public List<AgendamentoResponse> meus(UUID userId) {
        return agendamentos.findByUserIdOrderByInicioDesc(userId).stream().map(AgendamentoResponse::de).toList();
    }

    private List<LocalTime> horariosDisponiveis(ServicoCatalogo servico, LocalDate data) {
        if (data.isBefore(LocalDate.now(FUSO))) return List.of();
        var periodos = disponibilidades.findByProviderIdAndDiaSemanaAndAtivaTrueOrderByHoraInicioAsc(
                servico.providerId(), (short) (data.getDayOfWeek().getValue() % 7));
        Instant inicioDia = data.atStartOfDay(FUSO).toInstant();
        Instant fimDia = data.plusDays(1).atStartOfDay(FUSO).toInstant();
        var ocupados = agendamentos.findByProviderIdAndSituacaoInAndInicioLessThanAndFimGreaterThan(
                servico.providerId(), OCUPADOS, fimDia, inicioDia);
        Instant agora = Instant.now();
        List<LocalTime> resultado = new ArrayList<>();
        for (var periodo : periodos) {
            long minutos = Duration.between(periodo.getHoraInicio(), periodo.getHoraFim()).toMinutes();
            int duracao = servico.duracaoMinutos();
            for (long deslocamento = 0; deslocamento + duracao <= minutos; deslocamento += duracao) {
                var horario = periodo.getHoraInicio().plusMinutes(deslocamento);
                Instant inicio = data.atTime(horario).atZone(FUSO).toInstant();
                Instant fim = inicio.plus(Duration.ofMinutes(duracao));
                boolean livre = ocupados.stream().noneMatch(a -> a.getInicio().isBefore(fim) && a.getFim().isAfter(inicio));
                if (inicio.isAfter(agora) && livre) resultado.add(horario);
            }
        }
        return resultado;
    }
}
