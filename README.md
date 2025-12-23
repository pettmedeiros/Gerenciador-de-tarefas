# Gerenciador de Tarefas – Full-Stack com Autenticação
Projeto de estudo desenvolvido com o objetivo de praticar e consolidar conceitos de desenvolvimento Full-Stack, integrando frontend, backend e banco de dados, além de implementar autenticação e controle de acesso por usuário.

O projeto começou como um CRUD simples e evoluiu para uma aplicação mais robusta, com login, segurança e regras de negócio.

## Tecnologias Utilizadas

🖥️ Backend
- **Java** 17
- **Spring Boot** 3.3.5 = Framework para desenvolvimento de aplicações Java.
- **Spring Data JPA**.
- **Maven**: Gerenciador de dependências e construção.**
- **H2 Database e PostgreSQL**
- **Postman** (para testar a API)**7

💻 Frontend
- **HTML5**
- **CSS3**
-  **JavaScript**

💻 Banco de Dados
- **PostgreSQL**

⚙️ Funcionalidades

🔐 Autenticação e Usuários
- Cadastro de usuários
- Login com autenticação
- Controle de acesso via token (JWT)
- Cada usuário visualiza e gerencia apenas suas próprias tarefas

✅ Gerenciamento de Tarefas
- Criar tarefas
- Editar tarefas
- Concluir tarefas
- Excluir tarefas
- Definir prioridade e prazo
- Atualização dinâmica das informações no frontend

📊 Dashboard
- Exibição de tarefas em tempo real
- Contadores automáticos:
- Total de tarefas
- Tarefas pendentes
- Tarefas concluídas

🧠 Conceitos Praticados

- Arquitetura MVC
- Integração entre frontend e backend via **API REST**
- Consumo de endpoints com **Fetch API**
- Persistência com **Spring Data JPA e PostgreSQL**
- Manipulação do DOM com JavaScript
- Uso de **SweetAlert** para melhorar a experiência do usuário


⚡ Como Executar o Projeto

**Backend:**

1- Clone o repositório: 
  git clone git@github.com:pettmedeiros/Gerenciador-de-tarefas.git
  
2- Configure o banco no arquivo application.properties:
- spring.datasource.url=jdbc:postgresql://localhost:5432/gerenciador_tarefas
- spring.datasource.username=seu_usuario
- spring.datasource.password=sua_senha
- spring.jpa.hibernate.ddl-auto=update
  

3-Execute a aplicação (pela IDE ou terminal):
  mvn spring-boot:run

**Frontend:**

O front está dentro de src/main/resources/static/
Basta rodar o backend e abrir o navegador em: http://localhost:8080

🧾 **Exemplo de Uso**
- Crie uma nova tarefa
- Edite o título ou a prioridade
- Marque como concluída
- Delete com confirmação
- 
## 📸 Screenshots

<p align="center">
  <img src="src/main/resources/static/images/dashboard.png" width="45%" />
  <img src="src/main/resources/static/images/nova-tarefa.png" width="45%" />
</p>

<p align="center">
  <img src="src/main/resources/static/images/concluir.png" width="45%" />
  <img src="src/main/resources/static/images/excluir.png" width="45%" />
</p>
 # Autor
   
   Peterson Lisboa Medeiros
   
  🎯 Estudante de Análise e Desenvolvimento de Sistemas 
  
   https://www.linkedin.com/in/peterson-medeiros-b54307318/
