import { requisicaoApi } from './apiClient';

export function listarCategorias() {
  return requisicaoApi('/catalogo/categorias');
}

export function listarServicos(categoriaId) {
  const filtro = categoriaId ? `?categoriaId=${encodeURIComponent(categoriaId)}` : '';
  return requisicaoApi(`/catalogo/servicos${filtro}`);
}

export function consultarServico(id) {
  return requisicaoApi(`/catalogo/servicos/${encodeURIComponent(id)}`);
}

export function salvarServico(token, dados, id) {
  const caminho = id ? `/catalogo/servicos/${encodeURIComponent(id)}` : '/catalogo/servicos';
  return requisicaoApi(caminho, {
    method: id ? 'PUT' : 'POST',
    headers: { Authorization: `Bearer ${token}` },
    body: JSON.stringify(dados),
  });
}
