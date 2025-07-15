// src/app/auth/jwt-utils.ts

export function decodeToken<T>(token: string): T | null {
  try {
    const payload = token.split('.')[1]; // Pega a parte do meio do JWT
    const decodedPayload = atob(payload); // Decodifica de base64 para string
    return JSON.parse(decodedPayload); // Converte string JSON para objeto
  } catch (e) {
    console.error('Erro ao decodificar token JWT:', e);
    return null;
  }
}