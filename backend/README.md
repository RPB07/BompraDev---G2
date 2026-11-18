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

Com o gateway, o `auth-service`, o Consul e o banco em execução, envie `POST /auth/signup` ao gateway na porta 8080:

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

Envie `POST /auth/signin` ao gateway na porta 8080 com o e-mail e a senha cadastrados:

```json
{
  "email": "ana@example.com",
  "senha": "senha123"
}
```

A resposta contém `token`, `tipoToken`, `expiraEmSegundos` e os dados de `usuario`. O token expira após uma hora. Credenciais incorretas, usuários inativos e tokens inválidos são recusados.

## Perfil e gateway

Os seguintes caminhos usam o gateway na porta 8080. Nas rotas protegidas, envie `Authorization: Bearer <token>`:

- `GET /auth/me`: consulta a própria conta autenticada.
- `PUT /auth/me`: atualiza o nome, a biografia e o portfólio da própria conta. Exemplo: `{"nome":"Ana","biografia":"Desenvolvedora","portfolio":"https://exemplo.com"}`.
- `POST /auth/me/provider`: permite a uma conta `USER` ativar o perfil `PROVIDER` e devolve um novo token.
- `GET /perfis/{id}`: consulta pública do nome, tipo, biografia e portfólio, sem retornar o e-mail.

O gateway consulta o Consul para localizar o auth-service e valida o JWT nas rotas protegidas. O auth-service também valida o token. As regras específicas de acesso a serviços e à moderação serão aplicadas junto com esses recursos.

Para iniciar apenas a infraestrutura necessária à etapa de acesso e perfis, execute na raiz:

```powershell
docker compose up -d --build auth-service catalog-service api-gateway
```

A inicialização pode demorar enquanto as imagens são compiladas e os serviços registrados. A migração `V2` acrescenta os campos de perfil ao banco de autenticação existente sem substituir a migração anterior.

O login segue o exemplo `auth-api` do professor Luciano: usa `AuthenticationManager`, `UserDetailsService`, BCrypt e JJWT 0.13.0. As rotas e o filtro do gateway foram adaptados do exemplo `gateway-api` dele. A rota `/auth/me` foi inspirada no material do professor Augusto; no BompraDev, ela retorna diretamente os dados do usuário, sem o objeto `user` que envolve a resposta no exemplo dele. As regras de perfil e ativação de prestador são adaptações ao escopo do BompraDev. A chave é fornecida por variável de ambiente e não fica no código.

## Catálogo de serviços

O `catalog-service` mantém categorias e serviços em seu próprio banco. A migração `V2` cria as categorias iniciais de tecnologia. Serviços criados por `PROVIDER` ficam disponíveis imediatamente, usando o estado interno `APROVADO`; esse nome de estado não significa aprovação prévia por um administrador. Título, descrição, preço, duração em minutos e categoria são obrigatórios.

Pelo gateway na porta 8080:

- `GET /catalogo/categorias`: categorias ativas.
- `GET /catalogo/servicos`: lista pública; aceita filtro opcional `?categoriaId=<id>`.
- `GET /catalogo/servicos/{id}`: detalhes públicos.
- `GET /catalogo/meus-servicos`: serviços do provider autenticado.
- `POST /catalogo/servicos`: publica um serviço do provider autenticado.
- `PUT /catalogo/servicos/{id}`: altera um serviço disponível pertencente ao provider autenticado.

Nas rotas protegidas, envie `Authorization: Bearer <token>`. Exemplo de corpo para cadastro e edição:

```json
{"categoriaId":"<id-da-categoria>","titulo":"Serviço","descricao":"Descrição do serviço","valor":120.00,"duracaoMinutos":60}
```

O gateway e o catálogo validam o JWT, e o catálogo verifica o perfil e a propriedade do serviço. A moderação administrativa, a inativação e os agendamentos ainda não estão implementados. A organização de entidades, repositórios, serviços e controllers adapta os exemplos do `product-api` do professor Luciano; as regras de publicação e propriedade pertencem ao BompraDev.

