# Star Wars API

Este projeto é uma API REST desenvolvida em Java com Spring Boot e MongoDB para cadastro de planetas dos filmes da franquia Star Wars, 
após o cadastro será possível buscar o planeta e saber em quantos filmes do Star Wars o planeta apareceu.

Para saber a quantidade de aparições dos planetas nos filmes, esta API faz uma requisição para uma [API pública](https://swapi.co/) que disponibiliza 
informações sobre os filmes da franquia Star Wars.

## Pré-requisitos

- Java 8
- Maven 3.5
- Docker
- Docker Compose

## Executando a aplicação

- Clone o projeto ```git clone https://github.com/gabrielmq/starwars-api.git```
- Acesse o diretório do projeto pelo prompt de comando.
- Execute o comando ```docker-compose up -d``` para o docker compose subir os serviços.
    - A API demora alguns segundos para inicializar, para acompanhar a inicialização da aplicação 
    execute o comando ```docker logs -f starwars-api-app``` 
    para ver o log de inicialização da API.
- Com o container e a aplicação inicializados, acesse ```http://localhost:8080/api/v1/planetas``` para testar a API.