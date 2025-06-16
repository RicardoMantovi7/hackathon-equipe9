import { Request, Response } from 'express'; // Importa tipos do Express
import { EventoService } from '../services/EventoService'; // Importa o service de evento

export class EventoController { // Define a classe do controller
  private eventoService: EventoService; // Propriedade para o service

  constructor() {
    this.eventoService = new EventoService(); // Instancia o service
  }

  createEvento = async (req: Request, res: Response) => { // Cria um evento
    try {
      const { nome_evento, data_evento, id_palestrante } = req.body; // Pega dados do corpo
      const evento = await this.eventoService.createEvento({ nome_evento, data_evento, id_palestrante }); // Cria evento
      return res.status(201).json(evento); // Retorna evento criado
    } catch (error) {
      return res.status(500).json({ error: 'Erro ao criar evento' }); // Retorna erro genérico
    }
  };

  getEventos = async (req: Request, res: Response) => { // Busca eventos por critério
    try {
      const criteria = req.query; // Pega critérios da query string
      const eventos = await this.eventoService.findEventosByCriteria(criteria); // Busca eventos no banco
      return res.json(eventos); // Retorna lista de eventos
    } catch (error) {
      return res.status(500).json({ error: 'Erro ao buscar eventos' }); // Retorna erro genérico
    }
  };

  getEventoById = async (req: Request, res: Response) => { // Busca evento por ID
    try {
      const { id } = req.params; // Pega o id da URL
      const evento = await this.eventoService.findEventoById(Number(id)); // Busca evento no banco
      if (!evento) {
        return res.status(404).json({ error: 'Evento não encontrado' }); // Retorna erro se não achar
      }
      return res.json(evento); // Retorna evento encontrado
    } catch (error) {
      return res.status(500).json({ error: 'Erro ao buscar evento' }); // Retorna erro genérico
    }
  };

  updateEvento = async (req: Request, res: Response) => { // Atualiza evento
    try {
      const { id } = req.params; // Pega o id da URL
      const { nome_evento, data_evento, id_palestrante } = req.body; // Pega dados do corpo
      const evento = await this.eventoService.updateEvento(Number(id), { nome_evento, data_evento, id_palestrante }); // Atualiza no banco
      if (!evento) {
        return res.status(404).json({ error: 'Evento não encontrado' }); // Retorna erro se não achar
      }
      return res.json(evento); // Retorna evento atualizado
    } catch (error) {
      return res.status(500).json({ error: 'Erro ao atualizar evento' }); // Retorna erro genérico
    }
  };

  deleteEvento = async (req: Request, res: Response) => { // Deleta evento
    try {
      const { id } = req.params; // Pega o id da URL
      const deleted = await this.eventoService.deleteEvento(Number(id)); // Deleta no banco
      if (!deleted) {
        return res.status(404).json({ error: 'Evento não encontrado' }); // Retorna erro se não achar
      }
      return res.status(204).send(); // Retorna sucesso sem conteúdo
    } catch (error) {
      return res.status(500).json({ error: 'Erro ao deletar evento' }); // Retorna erro genérico
    }
  };
}
