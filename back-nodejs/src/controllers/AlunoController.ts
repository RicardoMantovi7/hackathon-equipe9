import { Request, Response } from 'express'; // Importa tipos do Express
import { AlunoService } from '../services/AlunoService'; // Importa o service de aluno

export class AlunoController { // Define a classe do controller
  private alunoService: AlunoService; // Propriedade para o service

  constructor() {
    this.alunoService = new AlunoService(); // Instancia o service
  }

  createAluno = async (req: Request, res: Response) => { // Cria um aluno
    try {
      const { nome, cpf, endereco } = req.body; // Pega dados do corpo da requisição
      const alunoExists = await this.alunoService.findAlunoByCpf(cpf); // Verifica se já existe aluno com esse CPF
      if (alunoExists) {
        return res.status(400).json({ error: 'Aluno já existe' }); // Retorna erro se já existe
      }
      const aluno = await this.alunoService.createAluno({ nome, cpf, endereco }); // Cria o aluno
      return res.status(201).json(aluno); // Retorna o aluno criado
    } catch (error) {
      return res.status(500).json({ error: 'Erro ao criar aluno' }); // Retorna erro genérico
    }
  };

  getAlunos = async (req: Request, res: Response) => { // Busca alunos por critério
    try {
      const criteria = req.query; // Pega critérios da query string
      const alunos = await this.alunoService.findAlunosByCriteria(criteria); // Busca alunos no banco
      return res.json(alunos); // Retorna lista de alunos
    } catch (error) {
      return res.status(500).json({ error: 'Erro ao buscar alunos' }); // Retorna erro genérico
    }
  };

  getAlunoById = async (req: Request, res: Response) => { // Busca aluno por ID
    try {
      const { id } = req.params; // Pega o id da URL
      const aluno = await this.alunoService.findAlunoById(Number(id)); // Busca aluno no banco
      if (!aluno) {
        return res.status(404).json({ error: 'Aluno não encontrado' }); // Retorna erro se não achar
      }
      return res.json(aluno); // Retorna o aluno encontrado
    } catch (error) {
      return res.status(500).json({ error: 'Erro ao buscar aluno' }); // Retorna erro genérico
    }
  };

  updateAluno = async (req: Request, res: Response) => { // Atualiza aluno
    try {
      const { id } = req.params; // Pega o id da URL
      const { nome, cpf, endereco } = req.body; // Pega dados do corpo
      const aluno = await this.alunoService.updateAluno(Number(id), { nome, cpf, endereco }); // Atualiza no banco
      if (!aluno) {
        return res.status(404).json({ error: 'Aluno não encontrado' }); // Retorna erro se não achar
      }
      return res.json(aluno); // Retorna aluno atualizado
    } catch (error) {
      return res.status(500).json({ error: 'Erro ao atualizar aluno' }); // Retorna erro genérico
    }
  };

  deleteAluno = async (req: Request, res: Response) => { // Deleta aluno
    try {
      const { id } = req.params; // Pega o id da URL
      const deleted = await this.alunoService.deleteAluno(Number(id)); // Deleta no banco
      if (!deleted) {
        return res.status(404).json({ error: 'Aluno não encontrado' }); // Retorna erro se não achar
      }
      return res.status(204).send(); // Retorna sucesso sem conteúdo
    } catch (error) {
      return res.status(500).json({ error: 'Erro ao deletar aluno' }); // Retorna erro genérico
    }
  };
}
