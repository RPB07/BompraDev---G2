# Arquitetura inicial

## Objetivo

A arquitetura precisa demonstrar os padrões estudados na disciplina de microsserviços sem dividir o sistema de forma artificial. Os limites iniciais foram definidos pelas responsabilidades e pelos dados pertencentes a cada domínio.

## Componentes

### API Gateway

Será o único ponto de entrada utilizado pelo aplicativo mobile. Terá as responsabilidades de roteamento, descoberta dos serviços e validação dos tokens nas rotas protegidas.

### Auth Service

Responsável por contas, credenciais e perfis `USER`, `PROVIDER` e `ADMIN`. Emitirá tokens assinados e manterá os dados necessários para autenticação e autorização.

Banco inicial: `bompradev_auth`.

### Catalog Service

Responsável pelos serviços publicados pelos providers, incluindo descrição, categoria, imagem, duração e estado de publicação. Um provider somente poderá alterar os próprios serviços.

Banco inicial: `bompradev_catalog`.

### Scheduling Service

Responsável pela disponibilidade de horários e pelo ciclo do agendamento. O user solicita um horário e o provider responsável aprova ou recusa a solicitação.

Banco inicial: `bompradev_scheduling`.

## Fluxo principal entre serviços

1. O aplicativo envia a solicitação de agendamento ao API Gateway.
2. O Gateway valida o token e encaminha a requisição ao Scheduling Service.
3. O Scheduling Service consulta o Catalog Service por descoberta de serviço.
4. O catálogo confirma se o serviço está disponível e informa o provider responsável.
5. O Scheduling Service valida o horário e registra a solicitação como pendente.
6. O provider poderá aprovar ou recusar a solicitação.

Essa integração será protegida por timeout, retry e circuit breaker. Falhas no catálogo não deverão gerar agendamentos inconsistentes.

## Persistência

Será adotado o padrão Database per Service. Cada microsserviço será proprietário de suas tabelas e não poderá consultar diretamente o banco de outro serviço. As estruturas serão controladas por migrações Flyway.

## Configuração e descoberta

O Consul será utilizado inicialmente para configuração centralizada, registro e descoberta de serviços, seguindo o exemplo desenvolvido nas aulas. Configurações sensíveis serão fornecidas por variáveis de ambiente e não serão versionadas.

## Containerização

Cada aplicação terá seu próprio Dockerfile. O ambiente completo será orquestrado por Docker Compose, com containers independentes e volumes para os bancos de dados.

## Evoluções previstas

Os módulos de conteúdo, enquetes, itens promocionais, observabilidade e demais serviços de apoio serão incluídos em etapas posteriores. A criação de um novo microsserviço dependerá de uma responsabilidade e de dados próprios claramente identificados.

## Persistência inicial

Cada microsserviço mantém seu próprio banco PostgreSQL e controla a evolução do esquema com migrações versionadas pelo Flyway:

- `auth-service`: usuários e seus perfis `USER`, `PROVIDER` e `ADMIN`;
- `catalog-service`: categorias e serviços publicados pelos providers;
- `scheduling-service`: disponibilidades e agendamentos que aguardam aprovação do provider.

Os identificadores pertencentes a outros microsserviços são armazenados sem chaves estrangeiras entre bancos. A consistência entre esses dados será tratada pelas regras de negócio e pelas integrações entre os serviços.

