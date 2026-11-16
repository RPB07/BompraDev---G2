const DEFAULT_API_URL = 'http://localhost:8080';

function getApiUrl() {
  return process.env.EXPO_PUBLIC_API_URL ?? DEFAULT_API_URL;
}

export async function apiRequest(path, options = {}) {
  const response = await fetch(`${getApiUrl()}${path}`, {
    ...options,
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json',
      ...options.headers,
    },
  });

  if (!response.ok) {
    throw new Error(`Falha na requisição: HTTP ${response.status}`);
  }

  if (response.status === 204) {
    return undefined;
  }

  return response.json();
}

