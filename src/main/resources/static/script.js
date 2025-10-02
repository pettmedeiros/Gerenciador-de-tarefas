
// Função principal para carregar as tarefas da API e renderizar na tela
async function carregarTarefas() {
    const response = await fetch('/api/tarefas');
    const tarefas = await response.json(); //converte a resposta Json em JS
    
    const lista = document.getElementById('lista-tarefas');
    lista.innerHTML = '';

    let total = 0, pendentes= 0, concluidas = 0;

    tarefas.forEach(tarefa => { //procura todas as tarefas 
        total++; 
        if(tarefa.status === "PENDENTE") pendentes++;
        if(tarefa.status === "CONCLUIDA") concluidas++;

        const card = document.createElement('div');
        card.classList.add('task-card');
        
        card.innerHTML = `
        <div class="task-info">
            <h3>${tarefa.titulo} 
                <span class="badge ${tarefa.status.toLowerCase()}">${tarefa.status}</span>
                <span class="badge ${tarefa.prioridade.toLowerCase()}">${tarefa.prioridade}</span>
            </h3>
            <p>${tarefa.descricao}</p>
            <small>
                Criada: ${new Date(tarefa.dataCriacao).toLocaleDateString('pt-BR')} |
                Prazo: ${new Date(tarefa.dataPrazo).toLocaleDateString('pt-BR')}
            </small>
        </div>
        <div class="actions">
            <button onclick="editarTarefa(${tarefa.idTarefa})">✏️</button>
            <button onclick="removerTarefa(${tarefa.idTarefa})">🗑️</button>
        </div>
        `;
        lista.appendChild(card);
    });

    // Atualiza os contadores
    document.getElementById('total').innerText = total;
    document.getElementById('pendentes').innerText = pendentes;
    document.getElementById('concluidas').innerText = concluidas;
}

// Remover Tarefa
async function removerTarefa(id) {
    if (confirm("Tem certeza que deseja exluir esta tarefa?")){
        fetch(`/api/tarefas/${id}`, { method: 'DELETE' })
        .then(response => {
            if(response.ok){
                alert("Tarefa removida com sucesso!");
                carregarTarefas();
            } else if (response.status === 404){
                alert("tarefa não encontrada!");
            } else{
                alert("Erro ao remover a tarefa!");
            }
        })
        .catch(Error => console.error("Erro:", error));
    }
}

// Abrir modal (criar ou editar)
function abrirModal(tarefa = null){
    const modal = document.getElementById('modalNovaTarefa');
    const form = document.getElementById('formTarefa');
    modal.classList.remove('hidden');

    if (tarefa){ // Prenche os campos para edição 
        document.getElementById('modalTitulo').innerText = 'Editar Tarefa';
        document.getElementById('titulo').value = tarefa.titulo;
        document.getElementById('descricao').value = tarefa.descricao;
        document.getElementById('prioridade').value = tarefa.prioridade;
        document.getElementById('status').value = tarefa.status;
        document.getElementById('dataPrazo').value = tarefa.dataPrazo;
        form.dataset.editandoId = tarefa.idTarefa;
    } else{
        document.getElementById('modalTitulo').innerText = 'Nova Tarefa';
        form.reset();
        form.dataset.editandoId = ""; // nenhum id em edição
    }
}

function fecharModal(){
    document.getElementById('modalNovaTarefa').classList.add('hidden');
}

// Editar Tarefa
async function editarTarefa(id) {
    const response = await fetch(`/api/tarefas/${id}`);
    const tarefa = await response.json();
    abrirModal(tarefa);
}

// --- Eventos da página ---

// Abre o modal para criar nova tarefa
document.getElementById('btnNovaTarefa').addEventListener('click', () => {
    abrirModal();
});

// Listener do formulário (criar ou editar)
document.getElementById('formTarefa').addEventListener('submit', async (e) => {
    e.preventDefault();

    const form = e.target;
    const editandoId = form.dataset.editandoId;

    const novaTarefa = {
        titulo: document.getElementById('titulo').value,
        descricao: document.getElementById('descricao').value,
        prioridade: document.getElementById('prioridade').value,
        status: document.getElementById('status').value,
        dataPrazo: document.getElementById('dataPrazo').value
    };

    if (editandoId) {
        // Atualiza tarefa existente
        await fetch(`/api/tarefas/${editandoId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(novaTarefa)
        });
    } else {
        // Cria nova tarefa
        await fetch('/api/tarefas', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(novaTarefa)
        });
    }

    fecharModal();
    carregarTarefas();
});

// Carrega as tarefas ao abrir a página
document.addEventListener('DOMContentLoaded', carregarTarefas);
