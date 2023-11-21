# Teste Agendamento de Transferências

## Visão Geral

Este é o repositório do teste de agendamento de transferências, uma aplicação web construída com Angular no frontend e Spring Boot no backend. A aplicação lida com transferências de valores, calculando taxas com base em determinados intervalos de dias.

## Decisões Arquiteturais

O projeto é estruturado usando uma arquitetura de cliente-servidor, com o frontend construído usando Angular e o backend utilizando Spring Boot. As transferências são armazenadas em um banco de dados H2 embutido. A comunicação entre o frontend e o backend é realizada através de chamadas HTTP RESTful.

## Ferramentas Utilizadas

- **Anguar v15:** Framework JavaScript para construção de interfaces de usuário.
- **Bootstrap 5** Bootstrap é um framework front-end que fornece estruturas de CSS para a criação de sites e aplicações responsivas de forma rápida e simples
- **Spring Boot (v3.1.5):** Framework Java para desenvolvimento de aplicativos Java rapidamente.
- **H2 Database :** Banco de dados SQL embutido para armazenar transferências.
- **Maven (v4.0.0):** Ferramenta de gerenciamento de projetos utilizada para construir e gerenciar dependências no backend.

## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas em sua máquina:

- Java Development Kit (JDK)
- NodeJs
- Angular CLI

## Instruções para Subir o Projeto local

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

        ```bash
        npm install -g @angular/cli
        ```

        ```bash
        ng serve
        ```

4. Acesse a aplicação no navegador em [http://localhost:4200](http://localhost:4200).

5. **Observações**

- O projeto no Front-end foi refeito do zero, ultilizando Angular V15
- No backend criei uma classe para validação dos campos, no frontend todos os campos são validados e tratados corretamente
- Funcionalidades que antes não foram implementadas, agora foram como: Notificação de erros e sucesso para o usário, formatação de datas usando o date-fns, validação de formulários com o ReactiveForms
- Os testes unitários foram escritos no backend!
- Acredito atender todos os requisitos da vaga, pois tenho conhecimentos com: Java; Spring MVC; Spring Data;Spring Boot; Hibernate/JPA; Oracle; Angular V9+; Bootstrap;
- Oque procuro no momento é um ambiente onde possa aprender novas tecnologias e melhorar meus conhecimentos atuais!
- Obrigada pela oportunidade e espero ter uma entrevista em breve!
