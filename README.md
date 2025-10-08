# Gerenciamento de Tarefas
Um sistema completo para criação, edição, listagem e exclusão de tarefas, desenvolvido como prática de integração entre backend (Java + Spring Boot) e frontend (HTML, CSS, JavaScript).

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

⚙️ Funcionalidades

- ✅ Criar novas tarefas
- ✏️ Editar tarefas existentes
- 🗑️ Remover tarefas com confirmação
- ✅ Marcar tarefas como concluídas
- 📅 Definir prazos e prioridades
- 📊 Contadores automáticos de tarefas (Total, Pendentes e Concluídas)
- 💬 Feedback visual com Swal.fire (mensagens de sucesso, erro e confirmação)

🧠 Conceitos Praticados

- Arquitetura MVC
- Integração entre frontend e backend via **API REST**
- Consumo de endpoints com **Fetch API**
- Persistência com **Spring Data JPA e PostgreSQL**
- Manipulação do DOM com JavaScript
- Uso de **SweetAlert** para melhorar a experiência do usuário


⚡ Como Executar o Projeto

Backend: 

1- Clone o repositório: 
  git clone git@github.com:pettmedeiros/Gerenciador-de-tarefas.git
  
2- Configure o banco no arquivo application.properties:
  spring.datasource.url=jdbc:postgresql://localhost:5432/gerenciador_tarefas
  spring.datasource.username=seu_usuario
  spring.datasource.password=sua_senha
  spring.jpa.hibernate.ddl-auto=update

3-Execute a aplicação (pela IDE ou terminal):
  mvn spring-boot:run

Frontend:

O front está dentro de src/main/resources/static/
Basta rodar o backend e abrir o navegador em: http://localhost:8080

 # Autor
   
   Peterson Lisboa Medeiros
   
  🎯 Estudante de Análise e Desenvolvimento de Sistemas
  
   https://www.linkedin.com/in/peterson-medeiros-b54307318/
