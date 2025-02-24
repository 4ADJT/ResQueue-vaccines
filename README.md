# 💉 Resqueue Vaccine Service

## 📖 Sobre o Projeto
O **Resqueue Vaccine Service** é um **microserviço** responsável pelo gerenciamento de vacinas dentro do sistema **Resqueue**. Ele utiliza **Spring Boot 3**, **Spring Security**, **OAuth 2.0**, **Eureka Client**, **RabbitMQ** para comunicação assíncrona e **PostgreSQL** como banco de dados.

Este serviço faz parte da arquitetura de **microserviços**, sendo **descoberto dinamicamente** via **Eureka Server** e integrado ao ecossistema **Resqueue**.

---

## 🚀 **Tecnologias Utilizadas**
- **Java 21 (Corretto)**
- **Spring Boot 3 (Web, Security, Eureka Client)**
- **Spring Cloud (OpenFeign, Netflix Eureka, Stream RabbitMQ)**
- **Spring Security (OAuth 2.0, JWT Resource Server)**
- **RabbitMQ (Mensageria)**
- **Hibernate (JPA)**
- **Flyway (Migrations)**
- **PostgreSQL**
- **Maven**

---

## ⚙️ **Configuração do Ambiente**
### 🔧 **Variáveis de Ambiente**
Antes de rodar o serviço, configure as seguintes variáveis no **`application.yml`** ou no ambiente:

```yaml
server:
  port: 0  # Definido dinamicamente pelo Eureka

spring:
  application:
    name: resqueue-vaccine

  rabbitmq:
    host: ${RABBITMQ_HOST:jackal.rmq.cloudamqp.com}
    port: ${RABBITMQ_PORT:5672}
    username: ${RABBITMQ_USERNAME:qfbirurm}
    password: ${RABBITMQ_PASSWORD:I8GlIzgSlp05u4YUuhcF3hU26URfTQxq}
    virtual-host: ${RABBITMQ_VIRTUAL_HOST:qfbirurm}

  cloud:
    stream:
      defaultBinder: rabbit
      bindings:
        notifications:
          destination: notifications-exchange
          contentType: application/json
          binder: rabbit
      rabbit:
        bindings:
          notifications:
            producer:
              exchange-type: topic
              routing-key: notifications
              delivery-mode: PERSISTENT

  datasource:
    driver-class-name: org.postgresql.Driver
    url: ${DATASOURCE_URL:jdbc:postgresql://localhost:5433/resqueue_vaccine}
    username: ${DATASOURCE_USERNAME:admin}
    password: ${DATASOURCE_PASSWORD:admin}
    hikari:
      maximum-pool-size: 10
      minimum-idle: 2
      idle-timeout: 30000
      connection-timeout: 20000
      max-lifetime: 1800000

  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: ${KC_BASE_ISSUER_URL:http://localhost:9000}/realms/resqueue

  jpa:
    database: postgresql
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect

  flyway:
    baseline-on-migrate: true
    enabled: true

eureka:
  client:
    fetch-registry: true
    register-with-eureka: true
    service-url:
      defaultZone: ${EUREKA_URL:http://localhost:8761/eureka/}

springdoc:
  api-docs:
    enabled: true
    path: /vaccine/v3/api-docs
  swagger-ui:
    enabled: true
    path: /docs
    config-url: /vaccine/v3/api-docs/swagger-config
    urls:
      - name: vaccine-service
        url: /vaccine/v3/api-docs
```

### 🔑 **Variáveis Explicadas**
| Variável                | Descrição |
|-------------------------|-----------|
| `KC_BASE_ISSUER_URL`    | URL base do **Keycloak** para autenticação JWT |
| `EUREKA_URL`            | URL do **Eureka Server** para registro do serviço |
| `DATASOURCE_URL`        | URL de conexão do **banco de dados PostgreSQL** |
| `DATASOURCE_USERNAME`   | Usuário do banco de dados |
| `DATASOURCE_PASSWORD`   | Senha do banco de dados |
| `RABBITMQ_HOST`         | Host do **RabbitMQ** |
| `RABBITMQ_PORT`         | Porta do **RabbitMQ** |
| `RABBITMQ_USERNAME`     | Usuário do **RabbitMQ** |
| `RABBITMQ_PASSWORD`     | Senha do **RabbitMQ** |

---

## 🚀 **Executando o Projeto**
### **Rodando com Docker**
Uma imagem Docker já está disponível no **Docker Hub**:

```sh
docker pull rodrigobrocchi/resqueue-vaccine:latest
docker run -p 8084:8084 \
  -e KC_BASE_ISSUER_URL=http://localhost:9000 \
  -e EUREKA_URL=http://localhost:8761/eureka \
  -e DATASOURCE_URL=jdbc:postgresql://localhost:5433/resqueue_vaccine \
  -e DATASOURCE_USERNAME=admin \
  -e DATASOURCE_PASSWORD=admin \
  -e RABBITMQ_HOST=jackal.rmq.cloudamqp.com \
  -e RABBITMQ_PORT=5672 \
  -e RABBITMQ_USERNAME=qfbirurm \
  -e RABBITMQ_PASSWORD=I8GlIzgSlp05u4YUuhcF3hU26URfTQxq \
  rodrigobrocchi/resqueue-vaccine:latest
```

---

## 📄 **Documentação da API**
A documentação da API está disponível através do **Gateway do Resqueue**, que pode ser acessado no repositório:

🔗 **[ResQueue Gateway - GitHub](https://github.com/4ADJT/ResQueue-gateway)**
