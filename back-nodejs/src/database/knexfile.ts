import dotenv from 'dotenv'; // Importa dotenv para ler variáveis de ambiente

dotenv.config(); // Carrega variáveis do .env

export default {
  development: { // Configuração para ambiente de desenvolvimento
    client: 'mysql2', // Cliente do banco de dados
    connection: { // Dados de conexão
      host: process.env.DB_HOST || 'localhost', // Host do banco
      port: Number(process.env.DB_PORT) || 3306, // Porta do banco
      database: process.env.DB_NAME || 'javapoo', // Nome do banco
      user: process.env.DB_USER || 'root', // Usuário do banco
      password: process.env.DB_PASSWORD || '' // Senha do banco
    },
    migrations: { // Configuração das migrations
      directory: 'migrations', // Pasta das migrations
      tableName: 'knex_migrations' // Tabela de controle das migrations
    },
    seeds: { // Configuração dos seeds
      directory: './src/database/seeds' // Pasta dos seeds
    },
  }
};