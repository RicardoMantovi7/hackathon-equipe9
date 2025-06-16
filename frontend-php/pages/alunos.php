<div class="card">
    <h2>Gerenciar Alunos</h2>

    <form id="alunoForm">
        <div class="form-group">
            <input type="text" id="nome" placeholder="Nome" required>
        </div>
        <div class="form-group">
            <input type="text" id="cpf" placeholder="CPF" required>
        </div>
        <div class="form-group">
            <input type="text" id="endereco" placeholder="Endereço">
        </div>
        <button type="submit">Adicionar Aluno</button>
    </form>
</div>

<div class="card">
    <h3>Lista de Alunos</h3>
    <div id="alunosLista" class="loading">Carregando...</div>
</div>

<script>
const API_BASE = 'http://localhost:3000/alunos'; // Altere se necessário

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
        return json;
    } catch (err) {
        console.error(err);
        return { error: 'Erro de conexão com a API' };
    }
}

document.addEventListener('DOMContentLoaded', function() {
    carregarAlunos();

    document.getElementById('alunoForm').addEventListener('submit', async function(e) {
        e.preventDefault();

        const aluno = {
            nome: document.getElementById('nome').value,
            cpf: document.getElementById('cpf').value,
            endereco: document.getElementById('endereco').value
        };

        const result = await apiCall('', 'POST', aluno);

        if (!result.error) {
            alert('Aluno adicionado com sucesso!');
            document.getElementById('alunoForm').reset();
            carregarAlunos();
        } else {
            alert('Erro ao adicionar aluno: ' + result.error);
        }
    });
});

async function carregarAlunos() {
    const alunos = await apiCall();
    const lista = document.getElementById('alunosLista');

    if (alunos.error) {
        lista.innerHTML = '<p>Erro ao carregar alunos</p>';
        return;
    }

    if (alunos.length === 0) {
        lista.innerHTML = '<p>Nenhum aluno encontrado</p>';
        return;
    }

    lista.innerHTML = `
        <table>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>CPF</th>
                <th>Endereço</th>
            </tr>
            ${alunos.map(aluno => `
                <tr>
                    <td>${aluno.id}</td>
                    <td>${aluno.nome}</td>
                    <td>${aluno.cpf}</td>
                    <td>${aluno.endereco || '-'}</td>
                </tr>
            `).join('')}
        </table>
    `;
}
</script>
