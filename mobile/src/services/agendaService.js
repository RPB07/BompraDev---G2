import { requisicaoApi } from './apiClient';

export function listarDisponibilidades(token) {
  return requisicaoApi('/agenda/disponibilidades', { headers: { Authorization: `Bearer ${token}` } });
}

export function cadastrarDisponibilidade(token, dados) {
  return requisicaoApi('/agenda/disponibilidades', {
    method: 'POST', headers: { Authorization: `Bearer ${token}` }, body: JSON.stringify(dados),
  });
}

export function removerDisponibilidade(token, id) {
  return requisicaoApi(`/agenda/disponibilidades/${encodeURIComponent(id)}`, {
    method: 'DELETE', headers: { Authorization: `Bearer ${token}` },
  });
}

export function listarHorarios(token, servicoId, data) {
  return requisicaoApi(`/agenda/horarios?servicoId=${encodeURIComponent(servicoId)}&data=${encodeURIComponent(data)}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
}

export function solicitarAgendamento(token, dados) {
  return requisicaoApi('/agenda/agendamentos', {
    method: 'POST', headers: { Authorization: `Bearer ${token}` }, body: JSON.stringify(dados),
  });
}

export function listarMeusAgendamentos(token) {
  return requisicaoApi('/agenda/agendamentos/meus', { headers: { Authorization: `Bearer ${token}` } });
}
