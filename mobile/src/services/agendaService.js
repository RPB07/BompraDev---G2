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
