# Star Wars API

Este projeto é uma API REST desenvolvida em Java com Spring Boot e MongoDB para cadastro de planetas dos filmes da franquia Star Wars, 
após o cadastro será possível buscar o planeta e saber em quantos filmes da franquia Star Wars o planeta apareceu.

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
    - A aplicação estará inicializada quando o log exibir:
    ```
    : Tomcat started on port(s): 8080 (http) with context path ''
    : Started StarwarsApiApplication in 7.09 seconds (JVM running for 8.069)
    ```
- Com  a aplicação inicializada, acesse ```http://localhost:8080/api/v1/planetas``` ou ```http://localhost:8080/swapi/planetas``` para ter acesso aos recursos.

## Documentação API

A API disponibiliza recursos para inserção e busca de planetas para serem consumidos.

- [GET]
  - /api/v1/planetas - Retorna todos os planetas
  ```
  [
    {
        "id": "5cfa4d890274390026f1f635",
        "nome": "Coruscant",
        "terreno": "cityscape, mountains",
        "clima": "temperate",
        "aparicoesEmFilmes": 4
    },
    {
        "id": "5cfa4db50274390026f1f636",
        "nome": "Tatooine",
        "terreno": "dessert",
        "clima": "arid",
        "aparicoesEmFilmes": 5
    }
  ]
  ```
  - /api/v1/planetas/{planetaId} - Retorna o planeta pelo ID informado
  ```
    /api/v1/planetas/5cfa4db50274390026f1f636
    
    {
        "id": "5cfa4db50274390026f1f636",
        "nome": "Tatooine",
        "terreno": "dessert",
        "clima": "arid",
        "aparicoesEmFilmes": 5
    }
  ```
  - /api/v1/planetas/planeta/{nome} - Retorna o planeta pelo nome informado
  ```
    /api/v1/planetas/planeta/Tatooine
    
    {
        "id": "5cfa4db50274390026f1f636",
        "nome": "Tatooine",
        "terreno": "dessert",
        "clima": "arid",
        "aparicoesEmFilmes": 5
    }
  ```
  - /swapi/planetas?pagina= - Retorna a página com os planetas da API pública do Star Wars
  ```
    /swapi/planetas?pagina=2

    {
        "count": 61, 
        "next": "https://swapi.co/api/planets/?page=2", 
        "previous": null, 
        "results": [
            {
                "name": "Alderaan", 
                "rotation_period": "24", 
                "orbital_period": "364", 
                "diameter": "12500", 
                "climate": "temperate", 
                "gravity": "1 standard", 
                "terrain": "grasslands, mountains", 
                "surface_water": "40", 
                "population": "2000000000", 
                "residents": [
                    "https://swapi.co/api/people/5/", 
                    "https://swapi.co/api/people/68/", 
                    "https://swapi.co/api/people/81/"
                ], 
                "films": [
                    "https://swapi.co/api/films/6/", 
                    "https://swapi.co/api/films/1/"
                ], 
                "created": "2014-12-10T11:35:48.479000Z", 
                "edited": "2014-12-20T20:58:18.420000Z", 
                "url": "https://swapi.co/api/planets/2/"
            }, 
            ...
            {
                "name": "Kamino", 
                "rotation_period": "27", 
                "orbital_period": "463", 
                "diameter": "19720", 
                "climate": "temperate", 
                "gravity": "1 standard", 
                "terrain": "ocean", 
                "surface_water": "100", 
                "population": "1000000000", 
                "residents": [
                    "https://swapi.co/api/people/22/", 
                    "https://swapi.co/api/people/72/", 
                    "https://swapi.co/api/people/73/"
                ], 
                "films": [
                    "https://swapi.co/api/films/5/"
                ], 
                "created": "2014-12-10T12:45:06.577000Z", 
                "edited": "2014-12-20T20:58:18.434000Z", 
                "url": "https://swapi.co/api/planets/10/"
            }, 
            {
                "name": "Geonosis", 
                "rotation_period": "30", 
                "orbital_period": "256", 
                "diameter": "11370", 
                "climate": "temperate, arid", 
                "gravity": "0.9 standard", 
                "terrain": "rock, desert, mountain, barren", 
                "surface_water": "5", 
                "population": "100000000000", 
                "residents": [
                    "https://swapi.co/api/people/63/"
                ], 
                "films": [
                    "https://swapi.co/api/films/5/"
                ], 
                "created": "2014-12-10T12:47:22.350000Z", 
                "edited": "2014-12-20T20:58:18.437000Z", 
                "url": "https://swapi.co/api/planets/11/"
            }
        ]
    }
  ```
  - /swapi/planetas/{planetaId} - Retorna o planeta da API pública do Star Wars pelo Id informado.
  ```
    /swapi/planetas/5

    {
        "name": "Dagobah",
        "rotation_period": "23",
        "orbital_period": "341",
        "diameter": "8900",
        "climate": "murky",
        "gravity": "N/A",
        "terrain": "swamp, jungles",
        "surface_water": "8",
        "population": "unknown",
        "residents": [],
        "films": [
            "https://swapi.co/api/films/2/",
            "https://swapi.co/api/films/6/",
            "https://swapi.co/api/films/3/"
        ],
        "created": "2014-12-10T11:42:22.590000Z",
        "edited": "2014-12-20T20:58:18.425000Z",
        "url": "https://swapi.co/api/planets/5/"
    }
  ```
- [POST]
  - /api/v1/planetas - Cadastra no banco um novo planeta
  ```
  { 
        "nome": "Tatooine", 
        "terreno": "dessert", 
        "clima": "arid" 
  }
  ```
- [DELETE]
  - /api/v1/planetas/{planetaId} - Remove um planeta pelo Id
  ```
    /api/v1/planetas/5cfa4db50274390026f1f636
  ```

Para mais detalhes sobre a API, acesse `http://localhost:8080/swagger-ui.html` quando a aplicação estiver inicialidada.