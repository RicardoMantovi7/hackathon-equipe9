import { Router } from 'express'; // Importa o Router do Express
import { EventoController } from '../controllers/EventoController'; // Importa o controller de evento

const eventoRoutes = Router(); // Cria o router para eventos
const eventoController = new EventoController(); // Instancia o controller

eventoRoutes.post('/', eventoController.createEvento); // Rota para criar evento
eventoRoutes.get('/', eventoController.getEventos); // Rota para listar eventos
eventoRoutes.get('/:id', eventoController.getEventoById); // Rota para buscar evento por id
eventoRoutes.put('/:id', eventoController.updateEvento); // Rota para atualizar evento
eventoRoutes.delete('/:id', eventoController.deleteEvento); // Rota para deletar evento

export { eventoRoutes }; // Exporta as rotas de evento
