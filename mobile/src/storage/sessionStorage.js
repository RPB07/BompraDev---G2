import * as SecureStore from 'expo-secure-store';

const SESSION_KEY = 'bompradev.session';

export function salvarSessao(token) {
  return SecureStore.setItemAsync(SESSION_KEY, token);
}

export function obterSessao() {
  return SecureStore.getItemAsync(SESSION_KEY);
}

export function removerSessao() {
  return SecureStore.deleteItemAsync(SESSION_KEY);
}

