import { Router } from 'express'; // Importa o Router do Express
import { AlunoController } from '../controllers/AlunoController'; // Importa o controller de aluno

const alunoRoutes = Router(); // Cria o router para alunos
const alunoController = new AlunoController(); // Instancia o controller

alunoRoutes.post('/', alunoController.createAluno); // Rota para criar aluno
alunoRoutes.get('/', alunoController.getAlunos); // Rota para listar alunos
alunoRoutes.get('/:id', alunoController.getAlunoById); // Rota para buscar aluno por id
alunoRoutes.put('/:id', alunoController.updateAluno); // Rota para atualizar aluno
alunoRoutes.delete('/:id', alunoController.deleteAluno); // Rota para deletar aluno

export { alunoRoutes }; // Exporta as rotas de aluno
