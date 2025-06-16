import { knex } from 'knex'; // Importa o knex
import config from './knexfile'; // Importa as configurações do knexfile

type Environment = 'development'; // Define o tipo de ambiente
const environment = (process.env.NODE_ENV as Environment) || 'development'; // Pega o ambiente atual ou usa 'development'
const connectionConfig = config[environment]; // Seleciona a config do ambiente

export const knexConnection = knex(connectionConfig); // Cria e exporta a conexão com o banco