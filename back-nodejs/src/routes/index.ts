import { Router } from 'express'; // Importa o Router do Express
import { authMiddleware } from '../middlewares/authMiddleware'; // Importa o middleware de autenticação
const { alunoRoutes } = require('./alunoRoutes'); // Importa as rotas de aluno
const { eventoRoutes } = require('./eventoRoutes'); // Importa as rotas de evento
export const router = Router(); // Cria o router principal

// Rotas públicas
router.use('/auth', alunoRoutes); // Rotas públicas para autenticação/aluno

// Rotas protegidas
router.use('/events', authMiddleware, eventoRoutes); // Rotas de eventos protegidas por autenticação
router.use('/alunos', authMiddleware, alunoRoutes); // Rotas de alunos protegidas por autenticação