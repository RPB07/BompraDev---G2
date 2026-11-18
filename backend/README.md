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
if (-not (Test-Path .env)) { Copy-Item .env.example .env }
docker compose config
```

Antes da execução, substitua `POSTGRES_PASSWORD` e `JWT_SECRET` no arquivo `.env`. A chave JWT deve ser aleatória, ter pelo menos 32 bytes e não deve ser compartilhada nem versionada. É possível gerá-la com `openssl rand -base64 32` e inserir o resultado em `JWT_SECRET`. Se o banco já existir em um volume, sua senha continua sendo a definida na criação do volume: alterar `POSTGRES_PASSWORD` no `.env` não altera automaticamente a senha armazenada no PostgreSQL. O arquivo `.env` não deve ser versionado.

`AUTH_DB_PORT` altera somente a porta do PostgreSQL de autenticação no computador local. `AUTH_DATA_VOLUME` escolhe o volume Docker desse banco; o padrão mantém o volume existente. Não substitua ou apague um volume com dados sem primeiro preservar uma cópia e confirmar seu conteúdo.

## Cadastro inicial

Com o `auth-service` e seu banco em execução, envie `POST /auth/signup` para a porta 8081:

```json
{
  "nome": "Ana Exemplo",
  "email": "ana@example.com",
  "senha": "senha123",
  "provider": true
}
```

O campo `provider` seleciona o perfil `PROVIDER`; ausente ou `false`, cria um `USER`. `ADMIN` não pode ser escolhido no cadastro público. A senha precisa ter pelo menos 8 caracteres e no máximo 72 bytes em UTF-8; ela é armazenada como hash BCrypt e não aparece na resposta. E-mails são únicos, independentemente de maiúsculas ou minúsculas.

## Login

Envie `POST /auth/signin` ao `auth-service` na porta 8081 com o e-mail e a senha cadastrados:

```json
{
  "email": "ana@example.com",
  "senha": "senha123"
}
```

A resposta contém `token`, `tipoToken`, `expiraEmSegundos` e os dados básicos de `usuario`. O token expira após uma hora. Para consultar a própria conta em `GET /auth/me`, envie o cabeçalho `Authorization: Bearer <token>`. Credenciais incorretas, usuários inativos e tokens inválidos são recusados. O gateway, a autorização por perfil e as telas de cadastro e login ainda serão integrados nas próximas entregas; por enquanto, use as rotas diretamente no serviço.

O login segue o exemplo `auth-api` do professor Luciano: usa `AuthenticationManager`, `UserDetailsService`, BCrypt e JJWT 0.13.0. A rota `/auth/me` foi inspirada no material do professor Augusto; no BompraDev, ela retorna diretamente os dados do usuário, sem o objeto `user` que envolve a resposta no exemplo dele. A integração da validação JJWT com o Spring Security na rota protegida adapta os exemplos à estrutura já existente do projeto; a chave é fornecida por variável de ambiente e não fica no código.

