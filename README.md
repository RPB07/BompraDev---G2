# BompraDev

O BompraDev é uma plataforma mobile voltada à comunidade de Computação e Tecnologia. A proposta reúne conteúdo, participação da comunidade, divulgação de serviços e experiências promocionais em uma única aplicação.

O projeto integra as disciplinas de Projeto, Design e Engenharia de Processos, Paradigmas de Linguagem de Programação e Engenharia de Software. O desenvolvimento será incremental, com entregas organizadas por etapas e registro das decisões técnicas.

## Situação atual

O escopo inicial e a fundação técnica foram consolidados. O ambiente Docker utiliza o Consul para descoberta e configurações centralizadas. Há cadastro, login e perfis integrados ao auth-service pelo API Gateway. A etapa de serviços começou com catálogo, filtro, detalhes e publicação e edição pelo provider. As seções de conteúdo apresentam exemplos locais para navegação; enquetes persistentes, promoções reais, agendamentos e moderação ainda serão implementados.

Para executar o projeto, consulte as orientações do [backend](backend/README.md) e do [aplicativo](mobile/README.md).

## Perfis previstos

- `USER`: consulta conteúdos e serviços, participa de enquetes e acompanha suas interações.
- `PROVIDER`: cadastra e administra os próprios serviços dentro das regras da plataforma.
- `ADMIN`: gerencia a operação, os conteúdos, as permissões e os recursos administrativos.

## Módulos do produto

- **BOMPRADROP:** conteúdos curtos, notícias e dicas.
- **BOMPRALAB:** tutoriais, guias e materiais técnicos.
- **BOMPRAVOZ:** enquetes e pesquisas com a comunidade.
- **BOMPRAPRÊMIO:** itens promocionais e solicitações de resgate, sem pagamentos.
- **Serviços:** catálogo de serviços publicados por providers e contratação por agendamento.

## Documentação

- [Visão e escopo do MVP](docs/visao-e-escopo.md)
- [Backlog inicial](docs/backlog-inicial.md)
- [Arquitetura inicial](docs/arquitetura-inicial.md)

## Estrutura do repositório

- `backend`: API Gateway e microsserviços Spring Boot.
- `mobile`: aplicativo React Native com Expo.
- `docs`: escopo, arquitetura e planejamento das entregas.
- `compose.yaml`: infraestrutura e execução integrada dos serviços.

