# Grupo:

Roan Pablo Bortolini RA 1139707
Vinicius Gehring Capellari RA 1138972

# O projeto BompraDev

BompraDev é uma plataforma mobile voltada a estudantes, profissionais e entusiastas de Computação e Tecnologia. Seu propósito é centralizar conteúdos relevantes, estimular o desenvolvimento de competências, promover a participação da comunidade e aproximar usuários de experiências e oportunidades relacionadas ao ecossistema tecnológico.
O projeto propõe um ambiente digital organizado em categorias de conteúdo e serviços, combinando informação, aprendizagem, experiências promocionais e participação comunitária.

# Situação atual

O ambiente Docker utiliza o Consul para descoberta e configurações centralizadas. Há cadastro, login, perfis, catálogo de serviços e agenda com solicitação, aprovação, recusa de horários e registro de pagamento simulado. As seções de conteúdo apresentam exemplos locais; enquetes persistentes, promoções, cancelamento, remarcação e moderação ainda não estão implementados.

Para executar o projeto, consulte as orientações do [backend](backend/README.md) e do [aplicativo](mobile/README.md).

# Perfis previstos

- `USER`: consulta conteúdos e serviços e acompanha seus agendamentos.
- `PROVIDER`: cadastra e administra os próprios serviços e responde às solicitações de agendamento.
- `ADMIN`: perfil previsto para gerenciar a operação, os conteúdos, as permissões e os recursos administrativos, ainda não implementados.

# Módulos do aplicativo 

- **BOMPRADROP:** conteúdos curtos, notícias e dicas.
- **BOMPRALAB:** tutoriais, guias e materiais técnicos.
- **BOMPRAVOZ:** enquetes e pesquisas com a comunidade.
- **BOMPRAPRÊMIO:** itens promocionais e solicitações de resgate, sem pagamentos.
- **Serviços:** catálogo de serviços publicados por providers e contratação por agendamento.

# Repositório

- `backend`: API Gateway e microsserviços Spring Boot.
- `mobile`: aplicativo React Native com Expo.
- `compose.yaml`: infraestrutura e execução integrada dos serviços.

# Segurança e privacidade

O projeto utiliza autenticação por tokens, senhas armazenadas com hash e controle de acesso no backend. Há validação dos dados e das imagens enviadas e proteção da sessão no aplicativo.

Como avanços futuros, estão previstas a configuração de HTTPS em produção, a auditoria de operações administrativas e a revisão das medidas de proteção de dados pessoais conforme a LGPD.

# Qualidade e testes

A qualidade foi verificada por testes unitários e por testes de API e integração realizados durante o desenvolvimento. Testes adicionais de interface e de aceitação podem ampliar essa validação.

# Diretrizes de desenvolvimento

O projeto adotou como princípios: separação de responsabilidades e arquitetura modular, código organizado, legível e reutilizável, validação de dados no frontend e backend, commits descritivos, testes para funcionalidades críticas, versionamento e revisão de código.

# Desenvolvimentos futuros

O desenvolvimento deverá seguir entregas incrementais, com validação técnica e funcional de cada etapa nos semestres seguintes. 
