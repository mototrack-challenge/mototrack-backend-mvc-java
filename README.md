# 🛵 MotoTrack - Backend (Java)

## 📝 Descrição do Projeto

O **MotoTrack** é um sistema desenvolvido para ajudar no controle e monitoramento das motos utilizadas por uma empresa aluguel de motos. A solução foi pensada para resolver problemas comuns no gerenciamento físico dessas motos, como desorganização nos pátios, dificuldade para localizar veículos disponíveis ou em manutenção, e ausência de histórico rastreável de movimentações.

Com a API desenvolvida em Java, é possível:

- Realizar login e controlar o acesso ao sistema.
- Cadastrar, editar e listar motos.
- Acompanhar em tempo real a movimentação de cada moto entre diferentes departamentos (como avaliação, manutenção, pronto para uso).
- Associar um **status atual** à moto (ex: Em análise, Em manutenção, Disponível).
- Emitir **alertas** relacionados a situações específicas (ex: Moto parada por muito tempo em um departamento, manutenção pendente).
- Consultar e gerenciar os diferentes **departamentos** que fazem parte do fluxo operacional da empresa.

## 👥 Integrantes

- **Felipe Ulson Sora** – RM555462 – [@felipesora](https://github.com/felipesora)
- **Augusto Lope Lyra** – RM558209 – [@lopeslyra10](https://github.com/lopeslyra10)
- **Vinicius Ribeiro Nery Costa** – RM559165 – [@ViniciusRibeiroNery](https://github.com/ViniciusRibeiroNery)

## 📌 Relação entre os Endpoints

- **Usuário**: O ponto de entrada no sistema. Para interagir com os recursos, é necessário estar autenticado por meio de um login com credenciais válidas.

- **Moto**: Cada moto cadastrada representa uma unidade que pode ser rastreada e gerenciada. O cadastro da moto é essencial para registrar movimentações e associar status e alertas.

- **Movimentação**: Toda vez que uma moto troca de local ou de situação, uma movimentação é registrada. Ela indica em qual **departamento** a moto está, em que momento chegou, e permite rastrear todo o seu histórico.

- **Departamento**: Representa locais físicos ou setores (como "Avaliação", "Manutenção", "Pronta para Uso"). Cada movimentação aponta para um departamento.

- **Status**: Cada moto possui um status atual (por exemplo, "Em análise", "Em manutenção", "Disponível"). Isso facilita filtros e relatórios operacionais.

- **Alerta**: Utilizado para sinalizar condições críticas ou especiais associadas à moto (como necessidade urgente de manutenção, tempo excessivo parado em um departamento, etc.).

Essa estrutura modular permite que o sistema seja escalável, auditável e pronto para futuras integrações com aplicações móveis ou dashboards de visualização.

## 📡 Endpoints da API

Abaixo estão listados os principais endpoints da API do MotoTrack, divididos por entidade. Nos endpoints que requerem envio de dados (POST/PUT), são fornecidos exemplos de JSON.

---

### 🔐 Usuário

- `POST - /usuarios/salvar`  
  Cadastra um novo usuário.

```jsonc
{
  "nome": "João Silva",
  "email": "joao@example.com",
  "senha": "123456"
}
```

- `GET - /usuarios/listar/todos`  
  Lista todas os usuários cadastrados.

![GET usuarios](images/usuarios-get.png)

- `GET BY ID - /usuarios/listar/{id}`  
  Lista o usuário cadastrado com este id.

![GET ID usuarios](images/usuarios-get-id.png)

- `PUT - /usuarios/atualizar/{id}`  
  Atualiza os dados do usuário com este id.

```jsonc
{
  "nome": "João da Silva", // alterando o nome
  "email": "joao@example.com",
  "senha": "senha123456" // alterando a senha
}
```

- `DELETE - /usuarios/deletar/{id}`  
  Remove o usuário com este id.

---

### 🛵 Moto

- `POST - /motos/salvar`  
  Cadastra uma nova moto.

```jsonc
{
  "placa": "ABC1234",
  "modelo": "Mottu-E",
  "status": {
    "id_status": 1
  }
}
```

- `GET - /motos/listar/todos`  
  Lista todas as motos cadastradas.

![GET motos](images/motos-get.png)

- `GET BY ID - /motos/listar/{id}`  
  Lista a moto cadastrada com este id.

![GET ID motos](images/motos-get-id.png)

- `PUT - /motos/atualizar/{id}`  
  Atualiza os dados da moto com este id.

```jsonc
{
    "placa": "ABC1245", // alterando placa
    "modelo": "Mottu-E",
    "status": {
        "id_status": 2 // alterando status
    }
}
```

- `DELETE - /motos/deletar/{id}`  
  Remove a moto com este id.

---

### 🔁 Movimentação

- `POST - /movimentacoes/salvar`  
  Cadastra uma nova movimentação.

```jsonc
{
  "moto": {
    "id_moto": 1
  },
  "departamento": {
    "id_departamento": 1
  }
}
```

- `GET - /movimentacoes/listar/todos`  
  Lista todas as movimentações cadastradas.

![GET movimentacoes](images/movimentacoes-get.png)

- `GET BY ID - /movimentacoes/listar/{id}`  
  Lista a movimentação cadastrada com este id.

![GET ID movimentacoes](images/movimentacoes-get-id.png)

- `PUT - /movimentacoes/atualizar/{id}`  
  Atualiza os dados da movimentação com este id.

```jsonc
{
    "timestamp": "2025-05-04T17:49:02.237146",
    "departamento": {
        "id_departamento": 3 // alterando o departamento
    },
    "moto": {
        "id_moto":1
    }
}
```

- `DELETE - /movimentacoes/deletar/{id}`  
  Remove a movimentação com este id.

---

### 🏷️ Status

- `POST - /status/salvar`  
  Cadastra um novo status.

```jsonc
{
  "descricao": "Este status é que a moto está em avaliação",
  "tipo": "AVALIACAO"
}
```

- `GET - /status/listar/todos`  
  Lista todos os status cadastrados.

![GET status](images/status-get.png)

- `GET BY ID - /status/listar/{id}`  
  Lista o status cadastrado com este id.

![GET ID status](images/status-get-id.png)

- `PUT - /status/atualizar/{id}`  
  Atualiza os dados do status com este id.

```jsonc
{
    "tipo": "EM_MANUTENCAO", // alterando o tipo
    "descricao": "Este status é que a moto está em manutenção" // alterando a descricao
}
```

- `DELETE - /status/deletar/{id}`  
  Remove o status com este id.

---

### 🏬 Departamento

- `POST - /departamentos/salvar`  
  Cadastra um novo departamento.

```jsonc
{
  "nome": "Departamento de Entrada",
  "tipo": "ENTRADA"
}
```

- `GET - /departamentos/listar/todos`  
  Lista todos os departamentos cadastrados.

![GET departamentos](images/departamentos-get.png)

- `GET BY ID - /departamentos/listar/{id}`  
  Lista o departamento com este id.

![GET ID departamentos](images/departamentos-get-id.png)

- `PUT - /departamentos/atualizar/{id}`  
  Atualiza os dados do departamento com este id.

```jsonc
{
    "nome": "Departamento de Avaliação", // alterando o nome do departamento
    "tipo": "AVALIACAO" // alterando o tipo do departamento
}
```

- `DELETE - /departamentos/deletar/{id}`  
  Remove o departamentos com este id.

---

### 🚨 Alerta

- `POST - /alertas/salvar`  
  Cadastra um novo alerta.

```jsonc
{
  "mensagem": "Moto com o motor gravemente danificado.",
  "moto": {
    "id_moto": 1
  }
}
```

- `GET - /alertas/listar/todos`  
  Lista todos os alertas cadastrados.

![GET alertas](images/alertas-get.png)

- `GET BY ID - /alertas/listar/{id}`  
  Lista o alerta com este id.

![GET ID alertas](images/alertas-get-id.png)

- `PUT - /alertas/atualizar/{id}`  
  Atualiza os dados do alerta com este id.

```jsonc
{
    "mensagem": "Moto com os pneus furados.", // alterando a mensagem
    "dataAlerta": "2025-05-04T17:52:06.871938",
    "moto": {
        "id_moto": 1
    }
}
```

- `DELETE - /alertas/deletar/{id}`  
  Remove o alerta com este id.

## 🚀 Como Executar o Projeto

Siga os passos abaixo para rodar o backend do MotoTrack localmente na sua máquina:

### 🔧 Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas:

- **Java 21** ou superior  
- **Maven**  
- **IDE** (como IntelliJ IDEA ou Eclipse)  
- **Oracle Database** (ou um banco já configurado e acessível)  
- **Postman** (para testar os endpoints)

---

### 📥 1. Clonar o repositório

Abra o terminal e clone o projeto:

```bash
git clone https://github.com/mototrack-challenge/mototrack-backend-java.git
```

### 📦 2. Configurar o Banco de Dados

No arquivo `application.properties` (em `src/main/resources`), configure os dados do banco Oracle:

```bash
spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/XEPDB1
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```

### ⚙️ 3. Compilar e rodar o projeto

Abra o projeto na sua IDE (como IntelliJ ou Eclipse) e clique no **botão verde de "play"** na classe principal: (em `src/main/java/com/mototrack/MotoTrackBackendJavaApplication.java`):

O servidor será iniciado por padrão em:

```bash
http://localhost:8080/
```

### 📬 4. Testar com Postman

Abra o Postman e use os endpoints listados na seção anterior.

---

### ✅ Pronto!
Agora você pode testar todos os recursos do MotoTrack diretamente via API REST, utilizando o Postman ou outra ferramenta de sua preferência.
