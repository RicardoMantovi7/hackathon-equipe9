# Projeto Java Swing - Gerenciamento de Eventos e Palestrantes

## Descrição

Este projeto é uma aplicação desktop em **Java Swing** para gerenciar palestrantes, eventos e inscrições. Ele segue uma arquitetura em camadas com DAO, Service, Model e GUI para manter o código organizado e de fácil manutenção.

## Pré-requisitos

- Java JDK 11 ou superior
- Maven 3.6+
- MySQL 5.7+

## Configuração do Banco de Dados

1. Abra o MySQL e execute os seguintes comandos para criar o banco e as tabelas:
   ```sql
   CREATE DATABASE IF NOT EXISTS javapoo;
   USE javapoo;

   -- Tabela de alunos (não utilizada nesta versão)
   CREATE TABLE IF NOT EXISTS aluno (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       nome VARCHAR(255) NOT NULL,
       cpf VARCHAR(20) NOT NULL UNIQUE,
       endereco VARCHAR(255)
   );

   -- Tabela de palestrantes
   CREATE TABLE IF NOT EXISTS palestrante (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       nome VARCHAR(255) NOT NULL,
       minicurriculo TEXT,
       temas_abordados VARCHAR(255)
   );

   -- Tabela de eventos
   CREATE TABLE IF NOT EXISTS evento (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       nome_evento VARCHAR(255) NOT NULL,
       data_evento VARCHAR(255) NOT NULL,
       id_palestrante BIGINT,
       FOREIGN KEY (id_palestrante) REFERENCES palestrante(id)
   );

   -- Tabela para registrar a inscrição de um aluno em um evento
CREATE TABLE IF NOT EXISTS inscricao (
    id_aluno BIGINT NOT NULL,
    id_evento BIGINT NOT NULL,

    PRIMARY KEY (id_aluno, id_evento),

    -- ON DELETE CASCADE: Se um aluno ou evento for deletado, a inscrição some junto.
    FOREIGN KEY (id_aluno) REFERENCES aluno(id) ON DELETE CASCADE,
    FOREIGN KEY (id_evento) REFERENCES evento(id) ON DELETE CASCADE
);

   ```

## Configuração de Conexão

Edite o arquivo `src/main/resources/db.properties` (ou equivalente) com as credenciais do seu MySQL:

```
jdbc.url=jdbc:mysql://localhost:3306/javapoo
jdbc.user=seu_usuario
jdbc.password=sua_senha
```

## Build e Execução

1. No diretório do projeto, rode:
   ```bash
   mvn clean package
   ```
2. Execute o JAR gerado:
   ```bash
   java -jar target/gestao-eventos-1.0.jar
   ```

Ou, se preferir, abra a IDE (Eclipse/IntelliJ), importe como projeto Maven e execute a classe que contém o método `main`.

## Estrutura de Pastas

```
src/
├─ main/
│  ├─ java/
│  │  └─ felipe/nascimento/
│  │     ├─ model/       # Classes de entidade (POJOs)
│  │     ├─ dao/         # Acesso ao banco (CRUD)
│  │     ├─ service/     # Lógica de negócio e validações
│  │     └─ gui/         # Telas em Swing
│  └─ resources/        # Configurações (db.properties)
└─ test/                # Testes unitários (se houver)
```

## Estrutura de Dados

- **aluno**: id, nome, cpf, endereco
- **palestrante**: id, nome, minicurriculo, temas\_abordados
- **evento**: id, nome\_evento, data\_evento, id\_palestrante
