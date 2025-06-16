import { knexConnection as knex } from '../database/connection'; // Importa a conexão com o banco

export class EventoService { // Classe de serviço para eventos
  async createEvento(data: { nome_evento: string; data_evento: string; id_palestrante?: number }): Promise<any> { // Cria um evento
    const [evento] = await knex('evento').insert(data).returning('*'); // Insere no banco e retorna o evento criado
    return evento;
  }

  async findEventoById(id: number): Promise<any | undefined> { // Busca evento por ID
    return knex('evento').where({ id }).first(); // Retorna o evento com o ID
  }

  async findEventosByCriteria(criteria: any): Promise<any[]> { // Busca eventos por critérios
    return knex('evento').where(criteria); // Retorna lista de eventos filtrados
  }

  async updateEvento(id: number, data: { nome_evento?: string; data_evento?: string; id_palestrante?: number }): Promise<any | undefined> { // Atualiza evento
    const [evento] = await knex('evento').where({ id }).update(data).returning('*'); // Atualiza e retorna o evento
    return evento;
  }

  async deleteEvento(id: number): Promise<boolean> { // Deleta evento
    const deleted = await knex('evento').where({ id }).del(); // Deleta do banco
    return !!deleted; // Retorna true se deletou
  }
}
