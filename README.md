# congestion-tax-calc

# Git hub

    git clone 
    git cd project dir
    git pull

## Running the project locally

### Running locally in the IDE

- Import to Intellij `mvn clean install`
- then `docker-compose up` will start the rabbit, postgres, postgres admin

### get postgres container name

- Open git bash `docker ps` grab the name of the container for postgres

### Postgres connection manual

    http://localhost:5050/
    url=postgres_container
    username=postgres
    password=password

- run the scripts in the schema.sql and then start the project- sorry for this pain,
- didn't get time to automate, dml auto has got some issues in deleting with postgres
- Start the project

#### Running in local docker

- open project `docker-compose build` and then `docker-compose up`

#### Running in local kube with docker desktop

- Kubernetes along with docker desktop please use command  `kubectl apply -f calc-deployment.yaml`
  (do not forget to set the context to docker desktop).

# Have EH Cache configured it is running awesome.

# postman request - Integration Test - in pipeline with newman - did not write PACT/Contract testing - but have done before

    ...
        curl --location --request POST 'http://localhost:8080/congestion/v1/calculate-tax' \
        --header 'Content-Type: application/json' \
        --header 'Accept: application/json' \
        --header 'X-Internal-Client: InternalClient' \
        --header 'X-Client: externalClient' \
        --data-raw '{
        "registrationNumber":"ABX20O",
        "vehicleType": "PRIVATE_VEHICLE",
        "cityCode":"GOT",
        "executionType": "DAILY"
        }'
     ...

# prometheus and grafana can be configured as well didnt do that .. can do with little more time

- have done that before but metrics have been exposed over /metrics, /health

# Swagger

http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config#/congestion-tax-controller/calculateTax

# to docker container the app - need to do network config for exposing to kube - want to do this - but already spent 6 hours !!

calc:
build:
context: ./ dockerfile: Dockerfile image: calc links:

- postgres environment:
- MYAPP_JDBC_URL=jdbc:postgresql://postgres:5432/postgres ports:
- "8080:8080"