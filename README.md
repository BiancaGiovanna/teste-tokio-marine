# Teste Agendamento de Transferências

## Visão Geral

Este é o repositório do teste de agendamento de transferências, uma aplicação web construída com Vue.js no frontend e Spring Boot no backend. A aplicação lida com transferências de valores, calculando taxas com base em determinados intervalos de dias.

## Decisões Arquiteturais

O projeto é estruturado usando uma arquitetura de cliente-servidor, com o frontend construído usando Vue.js e o backend utilizando Spring Boot. As transferências são armazenadas em um banco de dados H2 embutido. A comunicação entre o frontend e o backend é realizada através de chamadas HTTP RESTful.

## Ferramentas Utilizadas

- **Vue.js (v3.0.0):** Framework JavaScript progressivo para construção de interfaces de usuário.
- **Bootstrap 5** Bootstrap é um framework front-end que fornece estruturas de CSS para a criação de sites e aplicações responsivas de forma rápida e simples
- **Spring Boot (v3.1.5):** Framework Java para desenvolvimento de aplicativos Java rapidamente.
- **H2 Database :** Banco de dados SQL embutido para armazenar transferências.
- **Maven (v4.0.0):** Ferramenta de gerenciamento de projetos utilizada para construir e gerenciar dependências no backend.
- **Docker:** Plataforma de conteinerização para facilitar a implantação.

## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas em sua máquina:

- Java Development Kit (JDK)
- Docker

## Instruções para Subir o Projeto local e com Docker

1. **Clone o repositório:**

    ```bash
    git clone https://github.com/BiancaGiovanna/teste-tokio-marine.git
    ```

2. **Para rodar o Backend com Maven:**

    - Navegue até o diretório `backend`

        ```bash
        cd backend
        ```

    - Execute o aplicativo Spring Boot usando Maven Wrapper

        ```bash
        ./mvnw spring-boot:run
        ```

3. **Construir a Imagem Docker para o Frontend:**

    - Navegue até o diretório `frontend`:

        ```bash
        cd frontend
        ```

    - Construa a imagem Docker:

        ```bash
        docker build -t teste-bianca-gomes-frontend:tag .
        ```

    - Execute o contêiner Docker do frontend:

        ```bash
        docker run -p 8070:8080 teste-bianca-gomes-frontend:tag
        ```

4. Acesse a aplicação no navegador em [http://localhost:8070](http://localhost:8070).

5. **Observações**

- O projeto no Front-end não teve todas as implemetações como ultilização de componentes para envio de notifação para o usuário, validação de alguns campos ou ultilização de bibliotecas para a validação
- Obtive certa dificuldade em trabalhar com o VueJS 3 por conta dos problemas com TypeScritp (não ultilizei o Angular poís quando percebi esses erros diria que o front-end estava a mais de 50% completo então não faria mais sentido começar um projeto do zero)
- Tomei a decisão de ultilizar o TS mas acabou complicando pois muitos módulos simplesmente não suportam o mesmo, então ocorreu alguns travamentos e possiveis repetições de código
- Busquei uma abordagem super simples para forcar no que realmente importava, acredito que o backend estaja em uma qualidade aceitavel para um teste simples
- Os testes foram escritos apenas no backend pois não obtive experiencia com testes em front-end (por mais que sim tetaria implementar nesse projeto porem por conta do problema do TS+VueJS acabei perdendo muito tempo em coisas que em outro framework como ReactJS não aconteceria poís é oque mais domino na questão de front-end)
- Acredito atender 80% dos requisitos da vaga, pois tenho conhecimentos como: Java; Spring MVC; Spring Data;Spring Boot; Hibernate/JPA; Oracle; Angular V9+; Bootstrap;
- Oque procuro no momento é um ambiente onde possa aprender novas tecnologias e melhorar meus conhecimentos atuais!
- Obrigada pela oportunidade e espero ter uma entrevista em breve!
