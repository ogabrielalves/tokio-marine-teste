# Transfer App

Aplicação de **gestão de transferências** construída com **Java 11 (Spring Boot)** no back-end e **Vue 3 (Vite)** no front-end. O projeto é modularizado com separação entre `BACKEND/` e `FRONTEND/`.

---

## 📁 Estrutura do Projeto

```
/BACKEND        → API REST em Java 11 + Spring Boot 2
  └── com.example.demo
      ├── config               → Configurações de CORS, Swagger, etc.
      ├── controller           → Endpoints REST
      ├── dto                  → Objetos de transferência de dados
      ├── enums                → Enumerações úteis (ex: tipo de taxa)
      ├── mapper               → Conversão entre entidades e DTOs
      ├── model                → Entidades do domínio
      ├── repository           → Acesso ao banco de dados (JPA)
      ├── service              → Regras de negócio
      │   └── strategy         → Padrão Strategy aplicado ao cálculo de taxas
      └── util                 → Utilitários genéricos

/FRONTEND       → Aplicação Vue 3
```

---

## 🚀 Tecnologias Utilizadas

| Camada        | Tecnologia               |
|---------------|--------------------------|
| Backend       | Java 11, Spring Boot 2   |
| Frontend      | Vue 3, Vite              |
| Node.js       | v20.x                    |
| Documentação  | Swagger/OpenAPI          |

---

## 🧠 Sobre o Backend

A API oferece endpoints para realizar e listar **transferências bancárias**, com cálculo automático de taxas baseado em regras dinâmicas.

### 🧩 Uso do Strategy Pattern

A lógica de cálculo de taxas varia conforme o tipo ou data da transferência. Para manter o código extensível e desacoplado, aplicamos o padrão **Strategy**:

- `TaxCalculatorStrategy` é a interface comum.
- Cada tipo de regra (ex: "A", "B", "C") possui sua própria implementação.
- `GenericFeeCalculatorStrategy` seleciona dinamicamente a regra correta conforme o contexto.

Isso permite adicionar novas regras **sem alterar o código existente** — apenas criando uma nova classe e registrando-a.

---

## 🔗 Endpoints da API

### `POST /transfers`

Cria uma nova transferência.

#### 📤 Exemplo de Request

```json
POST /transfers
Content-Type: application/json

{
  "originAccount": "12345-6",
  "destinationAccount": "65432-1",
  "amount": 1200.00,
  "transferDate": "2025-05-15"
}
```

#### 📥 Exemplo de Response

```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "originAccount": "12345-6",
    "destinationAccount": "65432-1",
    "amount": 1200.00,
    "fee": 36.00,
    "schedulingDate": "2025-05-15",
    "createdAt": "2025-05-14T10:23:00"
  }
}
```

---

### `GET /transfers`

Lista todas as transferências já realizadas.

#### 📥 Exemplo de Response

```json
{
  "status": 200,
  "message": "OK",
  "data": [
    {
      "id": 1,
      "originAccount": "12345-6",
      "destinationAccount": "65432-1",
      "amount": 1200.00,
      "fee": 36.00,
      "schedulingDate": "2025-05-15"
    }
  ]
}
```

---

## 📘 Swagger UI

A documentação interativa está disponível automaticamente via Swagger:

> 🔗 Acesse em: `http://localhost:8080/swagger-ui.html`

Permite testar todos os endpoints com interface amigável.

---

## ▶️ Como Executar o Backend

### Pré-requisitos
- Java 11
- Maven

### Passos

1. Acesse a pasta do back:
   ```bash
   cd BACKEND
   ```

2. Compile e execute:
   ```bash
   ./mvnw spring-boot:run
   ```

3. Acesse a API em:
   ```
   http://localhost:8080
   ```

---

## 🖥️ Como Executar o Frontend

### Pré-requisitos
- Node.js 20+
- NPM

### Passos

1. Acesse a pasta do front:
   ```bash
   cd FRONTEND
   ```

2. Instale as dependências:
   ```bash
   npm install
   ```

3. Inicie o servidor:
   ```bash
   npm run dev
   ```

4. Acesse:
   ```
   http://localhost:5173
   ```

---