# Sistema de Eventos - Front-end PHP

Sistema ultra básico em PHP que consome APIs Node.js/Java para gerenciar eventos.

## Estrutura
```
├── index.php          # Página principal com navegação
├── pages/
│   ├── alunos.php     # CRUD de alunos
│   ├── palestrantes.php # CRUD de palestrantes
│   ├── eventos.php    # CRUD de eventos
│   └── inscricoes.php # CRUD de inscrições
└── README.md
```

## Configuração

1. **Configure a URL da API** no arquivo `index.php`:
   ```php
   $api_base = 'http://localhost:3000/api'; // Sua API Node/Java
   ```

2. **APIs esperadas:**
   - `GET/POST /api/alunos`
   - `GET/POST /api/palestrantes`
   - `GET/POST /api/eventos`
   - `GET/POST /api/inscricoes`

## Uso

1. Inicie seu servidor PHP:
   ```bash
   php -S localhost:8000
   ```

2. Acesse: `http://localhost:8000`

## Características

- **Ultra resumido**: Apenas o essencial
- **SPA simples**: Navegação por query params
- **Fetch API**: Consome APIs REST
- **Responsivo**: CSS básico funcional
- **CRUD completo**: Create e Read para todas entidades

## Banco de Dados

O sistema espera que sua API Node/Java implemente as tabelas:
- `aluno` (id, nome, cpf, endereco)
- `palestrante` (id, nome, minicurriculo, temas_abordados)
- `evento` (id, nome_evento, data_evento, id_palestrante)
- `inscricao` (id_aluno, id_evento)
