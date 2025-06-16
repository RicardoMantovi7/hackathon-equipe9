<div class="card">
    <h2>Gerenciar Palestrantes</h2>
    
    <form id="palestranteForm">
        <div class="form-group">
            <input type="text" id="nome" placeholder="Nome" required>
        </div>
        <div class="form-group">
            <textarea id="minicurriculo" placeholder="Minicurrículo" rows="3"></textarea>
        </div>
        <div class="form-group">
            <input type="text" id="temas" placeholder="Temas Abordados">
        </div>
        <button type="submit">Adicionar Palestrante</button>
    </form>
</div>

<div class="card">
    <h3>Lista de Palestrantes</h3>
    <div id="palestrantesLista" class="loading">Carregando...</div>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    carregarPalestrantes();
    
    document.getElementById('palestranteForm').addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const palestrante = {
            nome: document.getElementById('nome').value,
            minicurriculo: document.getElementById('minicurriculo').value,
            temas_abordados: document.getElementById('temas').value
        };
        
        const result = await apiCall('/palestrantes', 'POST', palestrante);
        
        if (!result.error) {
            alert('Palestrante adicionado com sucesso!');
            document.getElementById('palestranteForm').reset();
            carregarPalestrantes();
        } else {
            alert('Erro ao adicionar palestrante: ' + result.error);
        }
    });
});

async function carregarPalestrantes() {
    const palestrantes = await apiCall('/palestrantes');
    const lista = document.getElementById('palestrantesLista');
    
    if (palestrantes.error) {
        lista.innerHTML = '<p>Erro ao carregar palestrantes</p>';
        return;
    }
    
    if (palestrantes.length === 0) {
        lista.innerHTML = '<p>Nenhum palestrante encontrado</p>';
        return;
    }
    
    lista.innerHTML = `
        <table>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Minicurrículo</th>
                <th>Temas</th>
            </tr>
            ${palestrantes.map(p => `
                <tr>
                    <td>${p.id}</td>
                    <td>${p.nome}</td>
                    <td>${p.minicurriculo || '-'}</td>
                    <td>${p.temas_abordados || '-'}</td>
                </tr>
            `).join('')}
        </table>
    `;
}
</script>
