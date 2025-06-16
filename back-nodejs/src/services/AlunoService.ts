import { knexConnection as knex } from '../database/connection'; // Importa a conexão com o banco

export class AlunoService { // Classe de serviço para alunos
  async createAluno(data: { nome: string; cpf: string; endereco?: string }): Promise<any> { // Cria um aluno
    const [aluno] = await knex('aluno').insert(data).returning('*'); // Insere no banco e retorna o aluno criado
    return aluno;
  }

  async findAlunoByCpf(cpf: string): Promise<any | undefined> { // Busca aluno por CPF
    return knex('aluno').where({ cpf }).first(); // Retorna o primeiro aluno com o CPF
  }

  async findAlunosByCriteria(criteria: any): Promise<any[]> { // Busca alunos por critérios
    return knex('aluno').where(criteria); // Retorna lista de alunos filtrados
  }

  async findAlunoById(id: number): Promise<any | undefined> { // Busca aluno por ID
    return knex('aluno').where({ id }).first(); // Retorna o aluno com o ID
  }

  async updateAluno(id: number, data: { nome?: string; cpf?: string; endereco?: string }): Promise<any | undefined> { // Atualiza aluno
    const [aluno] = await knex('aluno').where({ id }).update(data).returning('*'); // Atualiza e retorna o aluno
    return aluno;
  }

  async deleteAluno(id: number): Promise<boolean> { // Deleta aluno
    const deleted = await knex('aluno').where({ id }).del(); // Deleta do banco
    return !!deleted; // Retorna true se deletou
  }
}
