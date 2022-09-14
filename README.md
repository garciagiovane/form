# form
cadastro usuario

### [Swagger](localhost:8080/swagger-ui/index.html)

### Requisitos para rodar o projeto
- Java 17
- Gradle
- Docker e docker compose (Se for usar o docker-compose do projeto)
- PostgresSQL (se não usar o docker-compose do projeto)

### Como rodar o projeto
#### Usando o docker-compose
- Coloque nas variáveis abaixo (no arquivo `docker-compose.yml`) os valores de sua preferência para credenciais do banco
  - POSTGRES_PASSWORD
  - POSTGRES_USER
  - POSTGRES_DB
- Altere o arquivo `src/main/resources/application.yml` e troque as variáveis abaixo para os mesmos valores usados nas variáveis acima
  - ${POSTGRES_DB}
  - ${POSTGRES_PASSWORD}
  - ${POSTGRES_USER}

#### Sem docker-compose
- Altere o arquivo `src/main/resources/application.yml` e troque as variáveis abaixo para as credenciais de banco
  - ${POSTGRES_DB}
  - ${POSTGRES_PASSWORD}
  - ${POSTGRES_USER}

No terminal (na raiz do projeto) execute o comando `./gradlew bootrun` ou ainda `java -jar build/libs/{projectRootName}-{projectVersion}.jar`
- projectRootName está no arquivo `settings.gradle` na variável `rootProject.name`
- projectVersion está no arquivo `build.gradle` na variável `version`

### Exemplo de request

```
curl --location --request POST 'localhost:8080/users' \
--form 'name="house"' \
--form 'image=@"/Users/{seuusuario}/IMG_F0C706721080-1.jpeg"' \
--form 'birthDate="2022-08-05"'
```

**Atributos opcionais**
- image (extensões aceitáveis para o arquivo são `png`, `jpg`, `jpeg`, ` `)
- birthDate (deve estar no formato iso de data `yyyy-MM-dd`)

### Exemplo de response
```json
{
    "name": "house",
    "birthDate": "2022-08-05",
    "id": 2,
    "imageName": "IMG_F0C706721080-1.jpeg"
}
```
