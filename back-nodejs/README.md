# API RESTful de Eventos

Esta é uma API RESTful completa para gerenciamento de eventos, com autenticação de usuários e operações CRUD.

## Funcionalidades

- Autenticação de usuários (registro e login)
- CRUD completo de eventos
- Proteção de rotas com JWT
- Migrations para controle de versão do banco de dados
- Arquitetura em camadas (Controllers, Services, Models)

## Tecnologias Utilizadas

- Node.js
- TypeScript
- Express
- PostgreSQL
- Knex.js
- JWT para autenticação
- Bcrypt para hash de senhas

## Pré-requisitos

- Node.js (versão 14 ou superior)
- PostgreSQL
- npm ou yarn

## Configuração

1. Clone o repositório
2. Instale as dependências:
```bash
npm install
```

3. Configure as variáveis de ambiente:
Crie um arquivo `.env` na raiz do projeto com as seguintes variáveis:
```
DB_HOST=localhost
DB_PORT=5432
DB_NAME=hackathon_db
DB_USER=seu_usuario
DB_PASSWORD=sua_senha
JWT_SECRET=seu_jwt_secret
PORT=3000
```

4. Execute as migrations:
```bash
npm run migrate
```

## Executando o Projeto

Para desenvolvimento:
```bash
npm run dev
```

Para produção:
```bash
npm run build
npm start
```

## Endpoints da API

### Autenticação

- POST /auth/register - Registro de usuário
- POST /auth/login - Login de usuário

### Eventos

- POST /events - Criar evento
- GET /events - Listar eventos
- GET /events/:id - Buscar evento específico
- PUT /events/:id - Atualizar evento
- DELETE /events/:id - Deletar evento

## Estrutura do Projeto

```
src/
  ├── controllers/    # Controladores da aplicação
  ├── models/        # Interfaces e tipos
  ├── routes/        # Rotas da API
  ├── services/      # Lógica de negócio
  ├── middlewares/   # Middlewares
  ├── database/      # Configurações e migrations
  ├── types/         # Tipos personalizados
  └── server.ts      # Arquivo principal
```

//aluno: Murilo Marchiori Rodrigues RA:14510