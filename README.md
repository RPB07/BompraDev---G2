# Grupo:

Roan Pablo Bortolini RA 1139707
Vinicius Gehring Capellari RA 1138972

# O projeto BompraDev

BompraDev é uma plataforma mobile voltada a estudantes, profissionais e entusiastas de Computação e Tecnologia. Seu propósito é centralizar conteúdos relevantes, estimular o desenvolvimento de competências, promover a participação da comunidade e aproximar usuários de experiências e oportunidades relacionadas ao ecossistema tecnológico.
O projeto propõe um ambiente digital organizado em quatro categorias funcionais, combinando informação, aprendizagem, experiências promocionais e participação comunitária.

# Situação atual

O ambiente Docker utiliza o Consul para descoberta e configurações centralizadas. Há cadastro, login, perfis, catálogo de serviços e agenda com solicitação, aprovação, recusa de horários e registro de pagamento simulado. As seções de conteúdo apresentam exemplos locais; enquetes persistentes, promoções, cancelamento, remarcação e moderação ainda não estão implementados.

Para executar o projeto, consulte as orientações do [backend](backend/README.md) e do [aplicativo](mobile/README.md).

# Perfis previstos

- `USER`: consulta conteúdos e serviços, participa de enquetes e acompanha suas interações.
- `PROVIDER`: cadastra e administra os próprios serviços dentro das regras da plataforma.
- `ADMIN`: gerencia a operação, os conteúdos, as permissões e os recursos administrativos.

# Módulos do produto

- **BOMPRADROP:** conteúdos curtos, notícias e dicas.
- **BOMPRALAB:** tutoriais, guias e materiais técnicos.
- **BOMPRAVOZ:** enquetes e pesquisas com a comunidade.
- **BOMPRAPRÊMIO:** itens promocionais e solicitações de resgate, sem pagamentos.
- **Serviços:** catálogo de serviços publicados por providers e contratação por agendamento.

# Estrutura do repositório 

- `backend`: API Gateway e microsserviços Spring Boot.
- `mobile`: aplicativo React Native com Expo.
- `compose.yaml`: infraestrutura e execução integrada dos serviços.

# Segurança e privacidade

Como avanços futuros, a arquitetura prevê autenticação protegida por tokens, senhas armazenadas com hash seguro, controle de acesso aplicado no backend.
Proteção de sessões e credenciais no aplicativo, comunicação HTTPS em produção, validação de dados e arquivos enviados, registro de operações administrativas relevantes, minimização e proteção de dados pessoais conforme a LGPD.

# Qualidade e testes

Num primeiro momento, a qualidade foi validada por testes unitários realizados ao longo do processo de programação. Futuramente,  testes de integração, testes de API, testes de interface e testes de aceitação também podem ser incorporados ao projeto. 

# Diretrizes de desenvolvimento

O projeto adotou como princípios: separação de responsabilidades e arquitetura modular, código organizado, legível e reutilizável, validação de dados no frontend e backend, commits pequenos e descritivos, testes para funcionalidades críticas, versionamento e revisão de código. 

# Situação do projeto

O desenvolvimento deverá seguir entregas incrementais, com validação técnica e funcional de cada etapa nos semestres seguintes. 
