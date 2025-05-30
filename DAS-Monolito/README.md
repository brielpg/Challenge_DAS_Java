
# Dental Analytics Safe

## 1. Integrantes
- RM554012 Gabriel Pescarolli Galiza  
- RM554258 Guilherme Gambarão Baptista
- RM553640 Marcelo Vieira Junior

### Descrição do Projeto
Nosso projeto consiste em uma API desenvolvida em Java utilizando o framework Spring Boot para gerenciar clientes e clínicas odontológicas, incluindo a criação e consulta de relatórios de consulta. A API segue a arquitetura monolítica permitindo a criação, atualização e listagem de clientes e clínicas, além do gerenciamento de relatórios clínicos, incluindo um front-end gerenciado pelo Thymeleaf.

### Tecnologias Utilizadas
- **Java 17**
- **Spring Boot**
- **JPA / Hibernate**
- **Prometheus / Grafana**
- **Thymeleaf**
- **Docker**
- **Banco de Dados Oracle**
- **Maven**

### Sumário
- [Integrantes](#1-integrantes)
- [Passos para rodar a Aplicação](#2-passos-para-rodar-a-aplicação-sem-docker)
- [Diagramas](#31-arquitetura)
- [Telas](#32-telas)
- [Relacionamento e Constraints](#33-relacionamentos-e-constraints)
- [Endpoints Disponíveis](#4-endpoints-disponíveis)
- [Testes](#5-testes)
- [Prometheus e Grafana](#6-integração-spring-boot-actuator-com-prometheus-e-grafana)

---
## 2. Passos para rodar a aplicação (Sem Docker):
> É necessário que o RabbitMq esteja rodando para garantir o funcionamento da aplicação.
>
> Recomendamos que você suba a aplicação utilizando o Docker, dessa forma, todos os serviços necessários para o funcionamento também serão iniciados.
> 
> Deixamos um passo a passo para você rodar a aplicação com o Docker na documentação raíz do projeto.

1. Clone o repositório:  
   ```bash
   git clone https://github.com/brielpg/Challenge_DAS_Java.git

2. Abra o diretório do projeto clonado:  
    ```bash
   cd Challenge_DAS_JAVA/DAS-Monolito
   
3. Execute o comando para gerar um .jar da aplicação  
    ```bash
   mvn clean package

4. Acesse o diretório target para acessar o .jar  
    ```bash
    cd target

5. Rode a aplicação
    ```bash
   java -jar Challenge01-0.0.1-SNAPSHOT.jar  

---
## 3. Imagens:

### 3.1 Arquitetura:

![Diagrama de Arquitetura Geral](./imagens/diagramaArquiteturaGeral.png)

![Diagrama Relacional](./imagens/diagramaRelacional.png)

![Diagrama Logico](./imagens/diagramaLogico.png)

![Diagrama de Classe](./imagens/diagramaDeClasses.PNG)

### 3.2 Telas:

![Imagem_Home](./imagens/home_deslogado.jpg)

![Imagem Listagem de Clinicas](./imagens/listagem_clinica_admin.jpg)

![Imagem Editar Clinica](./imagens/editarClinica.jpg)

![Imagem Detalhamento da Clinica](./imagens/detalhamentoClinica.jpg)

![Imagem Listagem de Pacientes](./imagens/listagemPacientes.jpg)

![Imagem Listagem de Consultas](./imagens/listagemConsultas.jpg)

![Imagem Listagem de Relatorios](./imagens/listagemRelatorios.jpg)

### 3.3 Relacionamentos e Constraints:

#### Relacionamentos:  
> - Um cliente pode estar relacionado a vários relatórios, mas cada relatório pertence a apenas um cliente.  
> - Uma clínica pode estar relacionado a vários relatórios, mas cada relatório pertence a apenas uma clínica.  
> - Um cliente pode estar relacionado a várias clínicas e uma clínica pode estar relacionada a vários clientes.  

#### Chaves Estrangeiras:  
> cliente_id e clinica_id no relatório referenciam as tabelas Cliente e Clínica, garantindo que um relatório sempre esteja associado a um cliente e a uma clínica.  

> Clinica e Cliente possuem uma tabela de junçao com os IDs cliente_id e clinica_id que são chaves estrangeiras que referenciam Cliente e Clínica, respectivamente.  

---
## 4. Endpoints Disponíveis
> Ao lado da descrição de cada endpoint está o requisito de autenticação para utiliza-lo.
>
> A aplicação é inicializada com um login `ADMIN` por padrão, email: **admin@email.com**, senha: **admin**

### 4.1 Endpoints de Clínica

```http
  /clinica
```

| Método     | Endpoint       | Descrição                                                    | Autenticação |
|:-----------|:---------------|:-------------------------------------------------------------|:-------------|
| `GET`      | `/`            | Acessa a página que lista todas as clínicas                  | ADMIN        |
| `GET`      | `/create`      | Acessa a página para cadastro de clínicas                    | ADMIN        |
| `GET`      | `/update/{id}` | Acessa a página para atualizar as informações de uma clínica | ADMIN        |
| `GET`      | `/{cnpj}`      | Acessa a página que lista as informações de uma clínica      | ADMIN        |
| `GET`      | `/delete/{id}` | Endpoint para deletar uma clínica                            | ADMIN        |
| `POST`     | `/`            | Endpoint para criar uma nova clínica                         | ADMIN        |
| `POST`     | `/update/{id}` | Endpoint para atualizar informações da clínica               | ADMIN        |
| `POST`     | `/changeRole`  | Endpoint para atualizar a role/cargo da clínica              | ADMIN        |

### 4.2 Endpoints de Paciente

```http
  /paciente
```

| Método     | Endpoint        | Descrição                                                    | Autenticação |
|:-----------|:----------------|:-------------------------------------------------------------|:-------------|
| `GET`      | `/`             | Acessa a página que lista todos os pacientes                 | USER         |
| `GET`      | `/create`       | Acessa a página para cadastro de paciente                    | USER         |
| `GET`      | `/update/{cpf}` | Acessa a página para atualizar as informações de um paciente | USER         |
| `GET`      | `/{cpf}`        | Acessa a página que lista as informações de um paciente      | USER         |
| `GET`      | `/delete/{id}`  | Endpoint para deletar um paciente                            | ADMIN        |
| `POST`     | `/`             | Endpoint para criar um novo paciente                         | USER         |
| `POST`     | `/update/{cpf}` | Endpoint para atualizar informações de um paciente           | USER         |

### 4.3 Endpoints de Consulta

```http
  /consulta
```

| Método     | Endpoint       | Descrição                                                     | Autenticação |
|:-----------|:---------------|:--------------------------------------------------------------|:-------------|
| `GET`      | `/`            | Acessa a página que lista todas as consultas                  | USER         |
| `GET`      | `/create`      | Acessa a página para cadastro de consulta                     | USER         |
| `GET`      | `/update/{id}` | Acessa a página para atualizar as informações de uma consulta | USER         |
| `GET`      | `/{id}`        | Acessa a página que lista as informações de uma consulta      | USER         |
| `POST`     | `/`            | Endpoint para criar uma nova consulta                         | USER         |
| `POST`     | `/update/{id}` | Endpoint para atualizar informações de uma consulta           | USER         |


### 4.4 Endpoints de Relatorio

```http
  /relatorio
```

| Método       | Endpoint        | Descrição                                                        | Autenticação |
|:-------------|:----------------|:-----------------------------------------------------------------|:-------------|
| `GET`        | `/`             | Acessa a página que lista todos os relatórios                    | USER         |
| `GET`        | `/{id}`         | Acessa a página que lista as informações de um relatório         | USER         |
| `GET`        | `/negar/{id}`   | Endpoint para negar um pedido de um relatório                    | ADMIN        |
| `GET`        | `/aprovar/{id}` | Endpoint para aprovar um pedido de um relatório                  | ADMIN        |
| `GET`        | `/update/{id}`  | Acessa a página para atualizar as informações de um relatório    | USER         |
| `POST`       | `/update/{id}`  | Endpoint para atualizar informações de um relatório              | USER         |

### 4.5 Outros Endpoints

| Método       | Endpoint        | Descrição                  | Autenticação |
|:-------------|:----------------|:---------------------------|:-------------|
| `GET`        | `/`             | Acessa a home da aplicação | N/A          |
| `GET`        | `/login`        | Acessa a página de login   | N/A          |
| `GET`        | `/login?logout` | Faz o logout da conta      | USER / ADMIN |

---
## 5. Testes
> Os testes podem ser feitos via `Postman` através do arquivo collection postman disponibilizado, `"Challenge Odontoprev.postman_collection.json"`, ou nas próprias páginas thymeleaf com os exemplos abaixo.  
>
> Temos um perfil `ADMIN` cadastrado por padrão, email: **admin@email.com**, senha: **admin**

### Login
```
Email:   admin@email.com --padrão
Senha:   admin           --padrão
```

### Endereço
```
Logradouro:     Rua das Flores
Bairro:         Centro
CEP:            00000-123
Cidade:         São Paulo
UF:             SP
Complemento:    Apto 101
Número:         12
```

### Cadastro de Clínica  
```
Nome:           Clinica Sorriso
CNPJ:           12.323.123-0000-11
Telefone:       11 12196779
Email:          sorriso@clinica.com
Razão Social:   Clinica Sorriso Ltda
Senha:          123!@#Senha
```

### Cadastro de Paciente
```
Nome:                      Gabriel Galiza
CPF:                       12953568923
Telefone:                  11 55126778
Data de Nascimento:        2000-02-10
Número da Carteira Odonto: 123456789
```

### Cadastro de Consulta
```
Procedimento:       Limpeza Dental
Dentista:           Dr. Gabriel
Data da Consulta:   2025-03-19
Valor da Consulta:  123456789
Clínica ID:         1
Paciente CPF:       12953568923
```

### Relatório da Consulta
```
Título:         Consulta de Rotina
Descrição:      O paciente realizou uma avaliação completa da saúde bucal. O dentista identificou uma pequena cárie em um dos molares e realizou a aplicação de flúor para fortalecimento do esmalte. Uma próxima consulta foi agendada para acompanhamento e possível tratamento restaurador.
Imagem:         www.imagem.com
```

---
## 6. Integração Spring Boot Actuator com Prometheus e Grafana

### - Endpoints

> Alguns dos Endpoints relacionados ao actuator disponíveis.

| Endpoint                   | Descrição                                                     |
|:---------------------------|:--------------------------------------------------------------|
| `/actuator`                | Lista todos os endpoints disponíveis.                         |
| `/actuator/health`         | Retorna o estado da aplicação (UP/DOWN).                      |
| `/actuator/info`           | Exibe informações da aplicação.                               |
| `/actuator/metrics`        | Lista todas as métricas disponíveis.                          |
| `/actuator/metrics/{nome}` | Exibe dados de uma métrica específica, como 'jvm.memory.used' |
| `/actuator/prometheus`     | Exibe métricas formatadas para o Prometheus.                  |

### - Configuração do Grafana

* **Faça login** (usuário padrão: admin, senha: admin)  


* **Adicione uma nova fonte de dados:**  
   - Tipo: Prometheus  
   - URL: http://prometheus:9090 (endereço padrão do Prometheus)


* **Importe um dashboard para Spring Boot Actuator:**
   - Vá em "Create" > "Import"  
   - Use o ID do dashboard oficial: 4701 (Spring Boot Actuator Metrics)  
   - Configure a fonte de dados Prometheus criada  

### - Testando a Integração Localmente

**Prometheus** - Acesse `http://localhost:8080/actuator/prometheus` para verificar as métricas expostas.

**Grafana** - Acesse `http://localhost:3000` para abrir o Grafana e visualizar o dashboard.

---