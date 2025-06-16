<!-- <div class="card">
  <h2>Gerenciar Eventos</h2>
  <form id="eventoForm">
    <div class="form-group">
      <input type="text" id="nome_evento" placeholder="Nome do Evento" required>
    </div>
    <div class="form-group">
      <input type="date" id="data_evento" required>
    </div>
    <div class="form-group">
      <select id="id_palestrante">
        <option value="">Selecione um palestrante</option>
        </select>
    </div>
    <button type="submit">Adicionar Evento</button>
  </form>
</div> -->

<div class="card">
  <h3>Lista de Eventos</h3>
  <div id="eventosLista" class="loading">Carregando...</div>
</div>

<script>
// 1. Definir a URL base da API de Eventos
const API_BASE = 'http://localhost:3000/eventos';

// Função genérica para chamar a API (exatamente como em alunos.php)
async function apiCall(endpoint = '', method = 'GET', data = null) {
  const options = {
    method,
    headers: {
      'Content-Type': 'application/json'
    }
  };

  if (data) options.body = JSON.stringify(data);

  try {
    const response = await fetch(`${API_BASE}${endpoint}`, options);
    const json = await response.json();
    
    // Adicionado para tratar erros vindos da API (ex: validação)
    if (!response.ok) {
        throw new Error(json.error || 'A requisição à API falhou');
    }

    return json;
  } catch (err) {
    console.error(err);
    // Retornamos um objeto de erro para ser tratado no 'catch' de quem chamou
    return { error: err.message || 'Erro de conexão com a API' };
  }
}

// Função para carregar e renderizar a lista de eventos
async function carregarEventos() {
  const lista = document.getElementById('eventosLista');
  const eventos = await apiCall();

  if (eventos.error) {
    lista.innerHTML = `<p>Erro ao carregar eventos: ${eventos.error}</p>`;
    return;
  }

  if (eventos.length === 0) {
    lista.innerHTML = '<p>Nenhum evento encontrado</p>';
    return;
  }

  lista.innerHTML = `
    <table>
      <tr>
        <th>ID</th>
        <th>Nome do Evento</th>
        <th>Data</th>
        <th>Palestrante</th>
      </tr>
      ${eventos.map(evento => `
        <tr>
          <td>${evento.id}</td>
          <td>${evento.nome_evento}</td>
          <td>${evento.data_evento}</td>
          <td>${evento.palestrante_nome || 'A definir'}</td>
        </tr>
      `).join('')}
    </table>
  `;
}

// O código principal é executado quando o DOM está pronto
document.addEventListener('DOMContentLoaded', function() {
  // carregarPalestrantesSelect(); // Você pode implementar esta função para popular o <select>
  carregarEventos();

  document.getElementById('eventoForm').addEventListener('submit', async function(e) {
    e.preventDefault();

    // 2. Obter os dados do formulário de evento
    const evento = {
      nome_evento: document.getElementById('nome_evento').value,
      data_evento: document.getElementById('data_evento').value,
      id_palestrante: document.getElementById('id_palestrante').value || null
    };

    // Usamos um endpoint vazio ('') para fazer POST na URL base
    const result = await apiCall('', 'POST', evento);

    // 3. Tratar a resposta
    if (!result.error) {
      alert('Evento adicionado com sucesso!');
      document.getElementById('eventoForm').reset();
      carregarEventos(); // Atualiza a lista
    } else {
      alert('Erro ao adicionar evento: ' + result.error);
    }
  });
});

</script>