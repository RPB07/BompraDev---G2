# Backend

O backend será composto por aplicações Spring Boot independentes. Cada microsserviço terá seu próprio ciclo de build, configuração, migrações e persistência.

## Estrutura inicial

- `api-gateway`: ponto de entrada das requisições externas e validação dos tokens.
- `auth-service`: contas, perfis, credenciais e emissão de tokens.
- `catalog-service`: serviços publicados pelos providers.
- `scheduling-service`: disponibilidade, solicitações e decisões de agendamento.

Os serviços serão adicionados gradualmente. A infraestrutura local inicial está descrita no arquivo `compose.yaml` da raiz.

## Versões da fundação

- Java 21
- Spring Boot 4.1.2
- Spring Cloud 2025.1.4
- PostgreSQL 17
- Consul 1.21

## Compilação

Cada projeto possui seu próprio Maven Wrapper. No Windows, por exemplo:

```powershell
.\auth-service\mvnw.cmd -f .\auth-service\pom.xml clean package
```

Para validar todo o ambiente declarado, execute na raiz do repositório:

```powershell
Copy-Item .env.example .env
docker compose config
```

Antes da execução, substitua o valor de `POSTGRES_PASSWORD` no arquivo `.env`. Esse arquivo não deve ser versionado.

