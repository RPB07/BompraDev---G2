import * as SecureStore from 'expo-secure-store';

const SESSION_KEY = 'bompradev.session';

export function saveSession(token) {
  return SecureStore.setItemAsync(SESSION_KEY, token);
}

export function getSession() {
  return SecureStore.getItemAsync(SESSION_KEY);
}

export function removeSession() {
  return SecureStore.deleteItemAsync(SESSION_KEY);
}

