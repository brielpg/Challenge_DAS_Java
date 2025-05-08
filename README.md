
# Dental Analytics Safe

## Integrantes
- RM554012 Gabriel Pescarolli Galiza  
- RM554258 Guilherme Gambarão Baptista
- RM553640 Marcelo Vieira Junior


### Descrição Geral sobre o Projeto
Nosso projeto consiste em duas API desenvolvidas em Java utilizando o framework Spring Boot.  
As API estão divididas em dois diretórios: "DAS-Monolito" e "DAS-Microsservice", em cada um deles você encontrará um README detalhando a arquitetura, endpoints, testes e outras informações sobre a respectiva api.


### Sumário
- [Integrantes](#integrantes)
- [Descrição Geral sobre o Projeto](#descrição-geral-sobre-o-projeto)
- [Passos para rodar a aplicação](#1-passos-para-rodar-a-aplicação)
- [Video](#2-vídeo-apresentando-o-projeto)
- [Endpoints Disponíveis](#3-endpoints-disponíveis)
---

## 1. Passos para rodar a aplicação:
> Antes de rodar a aplicação é necessário que você tenha o Docker instalado na sua máquina.

1. Clone o repositório:  
   ```bash
   git clone https://github.com/brielpg/Challenge_Java_01.git

2. Abra o diretório do projeto clonado:  
    ```bash
   cd Challenge_DAS_JAVA
   
3. Abra o Docker Desktop


4. Execute o comando para subir os serviços:
    ```bash
   docker-compose up --build -d

> Pronto, agora está tudo certo para você testar nossa aplicação.

---
## 2. Vídeo apresentando o projeto
Link do vídeo: https://www.youtube.com/watch?v=6F3w8023OTM

---
## 3. Endpoints Disponíveis
> Abaixo você pode verificar alguns dos endpoints disponíveis do projeto. 
> 
> Você também pode verificar as portas que estão sendo utilizadas, basta acessar a área de "Containers" dentro do Docker Desktop.
>
> É importante ressaltar que caso queira se aprofundar mais nos endpoints acesse a documentação de cada uma das API.
> 
> Caso queira testar o serviço de monitoramente é necessário realizar algumas configurações, mas não se preocupe, está tudo bem detalhado na documentação do 📂 DAS-Monolito/


```http
  localhost:8080/
```

| Método  | Endpoint     | Descrição                                      | Autenticação |
|:--------|:-------------|:-----------------------------------------------|:-------------|
| `GET`   | `/`          | Acessa a página home                           | Deslogado    |
| `GET`   | `/login`     | Acessa a página para realizar o login          | Deslogado    |
| `GET`   | `/clinica`   | Acessa a página com a listagem das clínicas    | ADMIN        |
| `GET`   | `/paciente`  | Acessa a página com a listagem dos pacientes   | USER         |
| `GET`   | `/consulta`  | Acessa a página com a listagem das consultas   | USER         |
| `GET`   | `/relatorio` | Acessa a página com a listagem dos relatórios  | USER         |

---