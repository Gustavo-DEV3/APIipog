[README.md](https://github.com/user-attachments/files/28687433/README.md)
# BackendIPOG — API REST de Gerenciamento de Usuários

API RESTful desenvolvida com **Spring Boot 4** e **PostgreSQL** para gerenciamento de usuários. Expõe operações CRUD completas sob o recurso `/cuser/user`.

---

## Tecnologias

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 17 |
| Framework | Spring Boot 4.0.6 |
| Persistência | Spring Data JPA + Hibernate |
| Banco de dados | PostgreSQL |
| Build | Maven (Maven Wrapper incluído) |
| Frontend auxiliar | Python 3 (scripts em `/frontendepythonn`) |

---

## Pré-requisitos

- Java 17+
- Maven 3.8+ (ou usar o `mvnw` incluído)
- PostgreSQL rodando localmente na porta `5432`
- Banco de dados `ipogapis` criado previamente

---

## Configuração do banco de dados

Crie o banco antes de subir a aplicação:

```sql
CREATE DATABASE ipogapis;
```

As credenciais padrão estão em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ipogapis
spring.datasource.username=postgres
spring.datasource.password=12345678
```

> ⚠️ Altere `username` e `password` conforme o seu ambiente. Não suba credenciais reais para o repositório.

O Hibernate está configurado com `ddl-auto=update`, ou seja, a tabela `tb_user` é criada/atualizada automaticamente na primeira execução.

---

## Como executar

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/APIipog.git
cd APIipog

# Execute com o Maven Wrapper
./mvnw spring-boot:run
```

A API sobe em: `http://localhost:8080`

---

## Endpoints

Base URL: `http://localhost:8080/cuser`

> CORS configurado para aceitar requisições de `http://localhost:4200` (Angular).

### Listar todos os usuários

```
GET /cuser/user
```

**Resposta 200:**
```json
[
  {
    "id": 1,
    "name": "João Silva",
    "email": "joao@email.com",
    "password": "senha123",
    "tarefa": "Revisão de código"
  }
]
```

---

### Buscar usuário por ID

```
GET /cuser/user/{id}
```

| Parâmetro | Tipo | Descrição |
|---|---|---|
| `id` | `Long` | ID do usuário |

**Resposta 200:**
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "senha123",
  "tarefa": "Revisão de código"
}
```

**Resposta 404:** usuário não encontrado.

---

### Criar usuário

```
POST /cuser/user
Content-Type: application/json
```

**Body:**
```json
{
  "name": "Maria Souza",
  "email": "maria@email.com",
  "password": "senha456",
  "tarefa": "Testes unitários"
}
```

**Resposta 200:** objeto do usuário criado com `id` gerado.

---

### Atualizar usuário

```
PUT /cuser/user/{id}
Content-Type: application/json
```

| Parâmetro | Tipo | Descrição |
|---|---|---|
| `id` | `Long` | ID do usuário a ser atualizado |

**Body:**
```json
{
  "name": "Maria Souza Atualizada",
  "email": "maria.novo@email.com",
  "password": "novasenha",
  "tarefa": "Deploy"
}
```

**Resposta 200:** objeto do usuário atualizado.

**Resposta 404:** usuário não encontrado.

---

### Excluir usuário

```
DELETE /cuser/user/{id}
```

| Parâmetro | Tipo | Descrição |
|---|---|---|
| `id` | `Long` | ID do usuário a ser excluído |

**Resposta 200:**
```json
{
  "excluido": true
}
```

**Resposta 404:** usuário não encontrado.

---

### Atribuir tarefa a um usuário

```
PUT /cuser/user/{id}/tarefa?tarefa={descricao}
```

| Parâmetro | Tipo | Descrição |
|---|---|---|
| `id` | `Long` | ID do usuário |
| `tarefa` | `String` (query param) | Descrição da tarefa |

> ⚠️ Este endpoint está **incompleto** — retorna a string `"tarefa"` sem persistir nenhuma alteração no banco.

---

## Estrutura do projeto

```
APIipog-main/
├── frontendepythonn/          # Scripts Python auxiliares (WIP)
│   ├── Frontend.py
│   └── user.py
├── src/
│   └── main/
│       ├── java/com/br/
│       │   ├── BackendIPOG/
│       │   │   └── BackendIpogApplication.java   # Ponto de entrada
│       │   ├── controller/
│       │   │   └── UserController.java           # Endpoints REST
│       │   ├── exception/
│       │   │   └── ResourceNotFoundException.java
│       │   ├── model/
│       │   │   └── User.java                     # Entidade JPA
│       │   └── repository/
│       │       └── UserRepository.java           # Interface JPA
│       └── resources/
│           └── application.properties
└── pom.xml
```

---

## Modelo de dados

Tabela: `tb_user`

| Campo | Tipo Java | Coluna DB | Descrição |
|---|---|---|---|
| `id` | `Long` | `id` | Chave primária, gerada automaticamente |
| `name` | `String` | `name` | Nome do usuário |
| `email` | `String` | `email` | E-mail do usuário |
| `password` | `String` | `password` | Senha (sem criptografia) |
| `tarefa` | `String` | `tarefa` | Tarefa atribuída ao usuário |

---

## Pontos de atenção

- **Senha em texto plano:** o campo `password` é armazenado sem hash. Recomenda-se implementar criptografia com BCrypt antes de qualquer uso em produção.
- **Endpoint `/tarefa` não funcional:** o `PUT /cuser/user/{id}/tarefa` não persiste a tarefa recebida.
- **Frontend Python incompleto:** `Frontend.py` contém erros de sintaxe (`resposta.request.get` e uso de `-` no lugar de `=`) e aponta para a porta `8000` em vez de `8080`.
- **Sem autenticação:** a API não possui nenhuma camada de segurança (Spring Security ou similar).
