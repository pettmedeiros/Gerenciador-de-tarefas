//Função para carregar 
const API_BASE = '/api';
let token = localStorage.getItem('token');

// Verifica se esta logado
async function verificarlogin() {
    if(token){
        document.getElementById('telaBoasVindas').classList.add('hidden');
        document.getElementById('telaPrincipal').classList.add('hidden');

        try{ //// Extrai o email do token JWT e exibe no header
            const payload = JSON.parse(atob(token.split('.')[1]));
            document.getElementById('usuarioLogado').innerText = payload.sub;
        } catch{
            document.getElementById('usuarioLogado').innerText = "Usuário";
        }

        carregarTarefas();
    }
}

//Abre modal para efetuar login
async function abrirLogin(){
    Swal.fire({
        title: 'login',
        html:`
            <input type="email" id="loginEmail" class="swal2-input" placeholder="Email" required>
            <input type="senha" id="loginSenha" class="swal2-input" placeholder="Senha" required>
        `,
        showCancelButton: true,
        confirmButtonText: 'Entrar',
        cancelButtonText: 'Cancelar',
        preConfirm: () => {            
            const email = document.getElementById('loginEmail').value;
            const senha = document.getElementById('loginSenha').value;
            if(!email || !senha){
                Swal.showValidationMessage('Preencha email e senha');
                return false;
            }
            return { email, senha};
        }
        
    }).then(async (result) => {
        if(result.isConfirmed){
            const {email, senha} = result.value;
            try{
                const res = await fetch(`${API_BASE}/usuarios/login`, {
                    method: 'POST',
                    headers: { 'Content-Type' : 'application/json'},
                    body: JSON.stringify({email, senha})
                });
                if(res.ok){
                    const data = await res.json();
                    token = data.token;
                    localStorage.setItem('token', token);
                    verificarlogin();
                    Swal.fire('Sucesso!', 'Login realizado com sucesso!', 'sucess');
                }else{
                    Swal.fire('Erro', 'Email ou senha incorretos', 'error');
                }
            } catch (err){
                Swal.fire('Erro', 'Não foi possível conectar ao servidor', 'error');
            }
        }
    });
}


//Abre modal para efetuar cadastro 
async function abrirCadastro() {
    Swal.fire({
        title: 'Fazer Cadastro', 
        html: `
            <input type="text" id="cadNome" class="swal2-input" placeholder="Nome Completo" required>
            <input type="text" id="cadCpf" class="swal2-input" placeholder="CPF (apenas números)" required>
            <input type="email" id="cadEmail" class="swal2-input" placeholder="Email" required>
            <input type="senha" id="cadSenha" class="swal2-input" placeholder="Senha" required>
        `,
        showCancelButton: true,
        confirmButtonText: 'Entrar',
        cancelButtonText: 'Cancelar',
        preConfirm: () => {
            const nome = document.getElementById('cadNome').value;
            const cpf = document.getElementById('cadCpf').value;
            const email = document.getElementById('cadEmail').value;
            const senha = document.getElementById('cadSenha').value;
            
            if(!nome || !cpf || !email || !senha){
                Swal.showValidationMessage('Preencha todos os campos');
                return false;
            };
            return {nome, cpf, email, senha};
        }

    }).then(async (result) =>{
        if (result.isConfirmed){
            const dados = result.value;
            try {
                const res = await fetch(`${API_BASE}/usuarios/cadastrar`, {
                    method: 'POST', 
                    headers: { 'Content-Type': 'application/json'},
                    body: JSON.stringify(dados)
                })
                if (res.ok){
                    Swal.fire('Sucesso!', 'Cadastro realizado com sucesso!', 'sucess');
                } else{
                    Swal.fire('Erro', 'Não foi possível conectar ao servidor', 'error');
                }
            } catch (error) {
                Swal.fire('Erro', 'Não foi possível conectar ao servidor', 'error');
            }
        }
    })
 
}

// Logout
function logout() {
    localStorage.removeItem('token');
    token = null;
    document.getElementById('telaPrincipal').classList.add('hidden');
    document.getElementById('telaBoasVindas').classList.remove('hidden');
}

// Função genérica pra chamadas com token
async function apiFetch(url, options = {}) {
    const headers = {
        'Content-Type': 'application/json',
        ...(token && { 'Authorization': `Bearer ${token}` })
    };
    const res = await fetch(url, { ...options, headers });
    if (res.status === 401) {
        logout();
        Swal.fire('Sessão expirada', 'Faça login novamente', 'warning');
    }
    return res;
}


// Função principal para carregar as tarefas da API e renderizar na tela
async function carregarTarefas() {
    const response = await apiFetch(`${API_BASE}/tarefas`);

    if (!response || !response.ok) {
        return;
    }
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
                Prazo: ${tarefa.dataPrevistaDate 
                    ? new Date(tarefa.dataPrevistaDate).toLocaleDateString('pt-BR') : "Sem prazo" }
            </small>
        </div>
        <div class="actions">
            <button onclick="editarTarefa(${tarefa.idTarefa})">✏️</button>
            <button onclick="removerTarefa(${tarefa.idTarefa})">🗑️</button>
            <button onclick="concluirTarefa(${tarefa.idTarefa})">✅</button>
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
    const result = await Swal.fire({ //swal.fire usei para mostrar pop-ups interativos
    title: "Você tem certeza",
    text: "Tem certeza que deseja excluir esta tarefa?",
    icon: "warning",
    draggable: true,
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Sim, remover!",
    cancelButtonText: "Cancelar"
    });

    if(result.isConfirmed){
        try{
            const response = await fetch(`/api/tarefas/${id}`, { method: 'DELETE' })
            
            if(response.ok){
                await Swal.fire("Tarefa removida com sucesso!", "", "success");
                carregarTarefas();
            } else if (response.status === 404){
                Swal.fire("tarefa não encontrada!", "", "error");
            } else{
                Swal.fire("Erro ao remover a tarefa!", "", "error");
            }
        } catch(error){
            console.error("Erro:", error);
            Swal.fire("Falha na comunicação com o servidor!", "", "error")
        }
    }
}

// Abrir modal (criar ou editar)
function abrirModal(tarefa = null){
    const modal = document.getElementById('modalNovaTarefa');
    const form = document.getElementById('formTarefa');
    modal.classList.remove('hidden'); //hiden é para deixar invisivel na tela 

    if (tarefa){ // Prenche os campos para edição 
        document.getElementById('modalTitulo').innerText = 'Editar Tarefa';
        document.getElementById('titulo').value = tarefa.titulo;
        document.getElementById('descricao').value = tarefa.descricao;
        document.getElementById('prioridade').value = tarefa.prioridade;
        document.getElementById('status').value = tarefa.status;
        document.getElementById('dataPrazo').value = tarefa.dataPrevistaDate; 
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

async function concluirTarefa(id) {
    const result = await Swal.fire({ //
    title: "Você tem certeza?",
    text: "Tem certeza que deseja concluir esta tarefa?",
    icon: "question",
    draggable: true,
    showCancelButton: true,
    confirmButtonColor: "#4cd761ff",
    cancelButtonColor: "#d33",
    confirmButtonText: "Sim, concluir!",
    cancelButtonText: "Cancelar"
    });

    if (result.isConfirmed){ // confirma o result 
        try {
            const response = await fetch(`/api/tarefas/${id}/status`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify("CONCLUIDA")
            });
            
            if(response.ok){
                await Swal.fire({
                title: "Tarefa Concluída!",
                text: "Parabéns, você finalizou essa tarefa!",
                icon: "success",
                timer: 3000,
                showConfirmButton: false
                });
                carregarTarefas();
            } else{
                Swal.fire("Erro!", "Não foi possível concluir a tarefa.", "error")
            }
        } catch(error) {
            console.error("Erro:", error);
            Swal.fire("Falha na comunicação com o servidor!", "", "error");
        }
    }
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
        dataPrevistaDate: document.getElementById('dataPrazo').value
    };

    try{
        if (editandoId) {
            // Atualiza tarefa existente
            await fetch(`/api/tarefas/${editandoId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(novaTarefa)
            });

            Swal.fire({
                title: "Tarefa Atualizada!",
                icon: "success",
                timer: 3000,
                showConfirmButton: false
            });

        } else {
            // Cria nova tarefa
            await fetch('/api/tarefas', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(novaTarefa)
            });

            Swal.fire({
                title: "Tarefa adicionada com sucesso!",
                icon: "success",
                timer: 3000,
                showConfirmButton: false
            });
        }

        fecharModal();
        carregarTarefas();
    } catch (error){
        console.error("Erro ao salvar tarefa:", error);
        Swal.fire("Erro!", "Não foi possível salvar a tarefa.", "error");
    }
});

// Carrega as tarefas ao abrir a página
document.addEventListener('DOMContentLoaded', carregarTarefas);

// Inicia a aplicação
document.addEventListener('DOMContentLoaded', () => {
    verificarLogin();
});
