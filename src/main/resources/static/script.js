
// Função principal para carregar as tarefas da API e renderizar na tela
async function carregarTarefas() {
    const response = await fetch('/api/tarefas');
    const tarefas = await response.json();
    
    const lista = document.getElementById('lista-tarefas');
    lista.innerHTML = '';

    let total = 0, pendentes= 0, concluidas = 0;

    tarefas.forEach(tarefa => {
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
            <small>Criada: ${tarefa.dataCriacao} | Prazo: ${tarefa.dataPrazo}</small>
        </div>
        <div class="actions">
            <button onclick="editarTarefa(${tarefa.id})">✏️</button>
            <button onclick="removerTarefa(${tarefa.id})">🗑️</button>
        </div>
        `;
        lista.appendChild(card);
    });

    document.getElementById('total').innerText = total;
    document.getElementById('pendentes').innerText = pendentes;
    document.getElementById('concluidas').innerText = concluidas;

    async function removerTarefa(id) {

        await fetch (`/api/tarefas/${id}`, {method: 'DELETE'});
        carregarTarefas();
        
    }

    document.addEventListener('DOMContentLoaded', carregarTarefas);

    const modal = document.getElementById('modalNovaTarefa');
    const form = document.getElementById('formTarefa');
    let editandoId = null;

    document.getElementById('btnNovaTarefa').addEventListener('click', () => {
        abrirModal();
    })

    function abrirModal(tarefa = null){
        modal.classList.remove('hidden');
        if (tarefa){
            document.getElementById('modalTitulo').innerText = 'Editar Tarefa';
            document.getElementById('titulo').value = tarefa.titulo;
            document.getElementById('descricao').value = tarefa.descricao;
            document.getElementById('prioridade').value = tarefa.prioridade;
            document.getElementById('status').value = tarefa.status;
            document.getElementById('dataPrazo').value = tarefa.dataPrazo;
            editandoId = tarefa.id;
        } else{
            document.getElementById('modalTitulo').innerText = 'Nova Tarefa';
            form.reset();
            editandoId = null;
        }
    }
    
    function fecharModal(){
        modal.classList.add('hidden');
    }

    form.addEventListener('submit', async(e) =>{
        e.preventDefault();

        const novaTarefa = {
            titulo: document.getElementById('titulo').value,
            descricao: document.getElementById('descricao').value,
            prioridade: document.getElementById('prioridade').value,
            status: document.getElementById('status').value,
            dataPrazo: document.getElementById('dataPrazo').value
        };

        if(editandoId) {
            await fetch(`/api/tarefas/${editandoId}`, {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(novaTarefa)
            });
        }
        fecharModal();
        carregarTarefas();
    });
    function editarTarefa(id) {
        fetch(`/api/tarefas/${id}`)
        .then(res => res.json())
        .then(tarefa => abrirModal(tarefa));
    }
}