export interface Aluno {
  id: number; // ID do aluno (bigint no banco)
  nome: string; // Nome do aluno
  cpf: string; // CPF do aluno
  endereco?: string | null; // Endereço do aluno (opcional)
}
