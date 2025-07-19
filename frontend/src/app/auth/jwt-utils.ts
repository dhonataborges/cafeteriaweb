// src/app/auth/jwt-utils.ts

/**
 * Decodifica o payload de um token JWT.
 * @param token Token JWT no formato "header.payload.signature"
 * @returns O payload decodificado como objeto do tipo T ou null em caso de erro
 */
export function decodeToken<T>(token: string): T | null {
  try {
    // Separa o token em partes (header, payload, signature) e pega a parte do meio (payload)
    const payload = token.split('.')[1];
    // Decodifica o payload de base64 para string JSON
    const decodedPayload = atob(payload);
    // Converte a string JSON para objeto do tipo T
    return JSON.parse(decodedPayload);
  } catch (e) {
    // Em caso de erro, exibe no console e retorna null
    console.error('Erro ao decodificar token JWT:', e);
    return null;
  }
}