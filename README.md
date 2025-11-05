# SIGEV-MPF API

## Descrição

O **SIGEV-MPF** (Sistema de Gestão de Visitas do Ministério Público Federal) é uma aplicação desenvolvida em **Java 17** com **Spring Boot**, que permite o controle de visitantes, usuários e registros de visitas.  
O sistema oferece autenticação via JWT, persistência com PostgreSQL e documentação automática das rotas utilizando **Swagger / OpenAPI 3.0**.

---

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.5.7
- Spring Web
- Spring Security
- Spring Data JPA
- PostgreSQL
- Lombok
- Jakarta Validation
- Swagger (Springdoc OpenAPI 3)
- JWT (Auth0 Java JWT)

---

## Configuração do Ambiente

### Pré-requisitos

Certifique-se de ter instalado:

- **Java 17+**
- **Maven 3.8+**
- **PostgreSQL** (com um banco configurado)
- **IDE** (IntelliJ, VS Code, Eclipse, etc.)

---

## Como Rodar o Projeto

1. **Clonar o repositório**
   ```bash
   git clone https://github.com/seu-usuario/SIGEV-MPF.git
   cd SIGEV-MPF
   ```

2. **Configurar o banco de dados**

   Edite o arquivo `application.properties` ou `application.yml`:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/sigev_mpf
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

3. **Compilar e rodar o projeto**

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Acessar a aplicação**

   A API estará disponível em:  
   [http://localhost:8080](http://localhost:8080)

---

## Documentação da API (Swagger)

Após iniciar o projeto, acesse:

- **Swagger UI:**  
  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

- **Especificação OpenAPI (JSON):**  
  [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)


---

## Rotas Principais

### 1. Autenticação

| Método | Rota              | Descrição        |
|---------|------------------|------------------|
| POST | `/auth/register` | Registrar novo usuário |
| POST | `/auth/login` | Autenticar e gerar token JWT |

**Exemplo de login:**

```json
POST /auth/login
{
  "email": "usuario@mpf.gov.br",
  "password": "senha123"
}
```

---

### 2. Visitantes (`visitante-controller`)

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/visitante` | Lista todos os visitantes |
| GET | `/visitante/{id}` | Retorna um visitante pelo ID |
| POST | `/visitante` | Cadastra um novo visitante |
| PUT | `/visitante/{id}` | Atualiza todas as informações de um visitante |
| PATCH | `/visitante/{id}` | Atualiza parcialmente um visitante |
| DELETE | `/visitante/{id}` | Exclui um visitante |

**Exemplo de criação:**

```json
POST /visitante
{
  "nomeCompleto": "João da Silva",
  "cpf": "12345678900",
  "telefone": "(61) 99999-9999",
  "sexo": "M",
  "documentoIdentidade": "MG123456",
  "dataNascimento": "1990-05-12",
  "foto": "base64string..."
}
```

---

### 3. Visitas (`visita-controller`)

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/visitas` | Lista todas as visitas |
| GET | `/visitas/{id}` | Retorna uma visita específica |
| POST | `/visitas` | Cadastra uma nova visita |
| PATCH | `/visitas/{id}/entrada` | Registra entrada do visitante |
| PATCH | `/visitas/{id}/saida` | Registra saída do visitante |
| DELETE | `/visitas/{id}` | Remove uma visita |

**Exemplo de criação:**

```json
POST /visitas
{
  "idVisitante": "uuid-do-visitante",
  "registradoPor": "admin",
  "localDestino": "Gabinete 101",
  "tipoVisita": "Institucional",
  "motivo": "Reunião",
  "status": "PENDENTE"
}
```

---

### 4. Usuários (`user-controller`)

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/user` | Lista todos os usuários |
| GET | `/user/{email}` | Busca usuário pelo e-mail |
| POST | `/user` | Cria um novo usuário |
| PUT | `/user/{id}` | Atualiza um usuário |
| DELETE | `/user/{id}` | Remove um usuário |

**Exemplo de criação:**

```json
POST /user
{
  "name": "Maria Souza",
  "email": "maria@mpf.gov.br",
  "password": "senha123",
  "role": "USER"
}
```

---

## Estrutura do Projeto

```
SIGEV-MPF/
 ├── src/
 │   ├── main/java/com/sergio/SIGEV_MPF/
 │   │   ├── config/        -> Configurações (Swagger, segurança)
 │   │   ├── controller/    -> Controladores REST
 │   │   ├── dto/           -> Objetos de transferência (Request/Response)
 │   │   ├── model/         -> Entidades JPA
 │   │   ├── repository/    -> Repositórios (Spring Data)
 │   │   ├── service/       -> Lógica de negócio
 │   │   └── SIGEVMPFApplication.java -> Classe principal
 │   └── resources/
 │       ├── application.properties
 │       └── openapi.json
 └── pom.xml
```

---

## Autor

**Sérgio Souza**  
E-mail: [sergio.tbl0123@gmail.com](mailto:sergio.tbl0123@gmail.com)

---


