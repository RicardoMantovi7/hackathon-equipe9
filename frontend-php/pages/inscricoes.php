<div class="card">
    <h2>Gerenciar Inscrições</h2>
    
    <form id="inscricaoForm">
        <div class="form-group">
            <select id="id_aluno" required>
                <option value="">Carregando alunos...</option>
            </select>
        </div>
        <div class="form-group">
            <select id="id_evento" required>
                <option value="">Carregando eventos...</option>
            </select>
        </div>
        <button type="submit">Fazer Inscrição</button>
    </form>
</div>

<div class="card">
    <h3>Lista de Inscrições</h3>
    <div id="inscricoesLista" class="loading">Carregando...</div>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    carregarInscricoes();
    carregarAlunosSelect();
    carregarEventosSelect();
    
    document.getElementById('inscricaoForm').addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const inscricao = {
            id_aluno: document.getElementById('id_aluno').value,
            id_evento: document.getElementById('id_evento').value
        };
        
        const result = await apiCall('/inscricoes', 'POST', inscricao);
        
        if (!result.error) {
            alert('Inscrição realizada com sucesso!');
            document.getElementById('inscricaoForm').reset();
            carregarInscricoes();
            carregarAlunosSelect();
            carregarEventosSelect();
        } else {
            alert('Erro ao fazer inscrição: ' + result.error);
        }
    });
});

async function carregarAlunosSelect() {
    const alunos = await apiCall('/alunos');
    const select = document.getElementById('id_aluno');
    
    if (alunos.error) {
        select.innerHTML = '<option value="">Erro ao carregar</option>';
        return;
    }
    
    select.innerHTML = `
        <option value="">Selecione o aluno</option>
        ${alunos.map(a => `<option value="${a.id}">${a.nome}</option>`).join('')}
    `;
}

async function carregarEventosSelect() {
    const eventos = await apiCall('/eventos');
    const select = document.getElementById('id_evento');
    
    if (eventos.error) {
        select.innerHTML = '<option value="">Erro ao carregar</option>';
        return;
    }
    
    select.innerHTML = `
        <option value="">Selecione o evento</option>
        ${eventos.map(e => `<option value="${e.id}">${e.nome_evento}</option>`).join('')}
    `;
}

async function carregarInscricoes() {
    const inscricoes = await apiCall('/inscricoes');
    const lista = document.getElementById('inscricoesLista');
    
    if (inscricoes.error) {
        lista.innerHTML = '<p>Erro ao carregar inscrições</p>';
        return;
    }
    
    if (inscricoes.length === 0) {
        lista.innerHTML = '<p>Nenhuma inscrição encontrada</p>';
        return;
    }
    
    lista.innerHTML = `
        <table>
            <tr>
                <th>Aluno</th>
                <th>Evento</th>
                <th>Data do Evento</th>
            </tr>
            ${inscricoes.map(i => `
                <tr>
                    <td>${i.aluno_nome}</td>
                    <td>${i.nome_evento}</td>
                    <td>${i.data_evento}</td>
                </tr>
            `).join('')}
        </table>
    `;
}
</script>
