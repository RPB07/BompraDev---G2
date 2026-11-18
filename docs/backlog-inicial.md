# Backlog inicial

O backlog está organizado por etapas para que o desenvolvimento e as entregas acadêmicas apresentem uma evolução coerente.

## Etapa 1 — Descoberta

- [x] Consolidar a visão do produto.
- [x] Registrar os perfis `USER`, `PROVIDER` e `ADMIN`.
- [x] Delimitar o escopo inicial e as exclusões do MVP.
- [x] Validar a jornada principal de contratação por agendamento.
- [x] Definir que a confirmação depende da aprovação do provider.
- [ ] Definir os demais estados e regras dos agendamentos.
- [ ] Definir regras de disponibilidade e conflito de horários.
- [x] Definir os dados obrigatórios de cada perfil: nome, e-mail e senha.
- [x] Definir o cadastro e a ativação do perfil `PROVIDER` sem aprovação administrativa.
- [x] Definir perfil público com biografia e portfólio.
- [x] Definir pagamento simulado e as regras informadas de cancelamento e remarcação.
- [ ] Definir critérios de moderação dos serviços.
- [ ] Elaborar os fluxos principais e o modelo conceitual de dados.

## Etapa 2 — Fundação técnica

- [x] Definir a divisão inicial dos microsserviços.
- [x] Definir Java 21 como versão-alvo do backend.
- [x] Utilizar Spring Boot 4.1.2 e Spring Cloud 2025.1.4.
- [x] Estruturar os projetos iniciais do backend.
- [x] Estruturar o projeto mobile com navegação, tema e cliente HTTP.
- [x] Configurar migrações de banco de dados.
- [x] Preparar a base da configuração externa e centralizada.
- [x] Preparar a infraestrutura local inicial com Docker Compose.

## Etapa 3 — Acesso e perfis

- [x] Implementar cadastro inicial de `USER` e `PROVIDER` no auth-service.
- [x] Implementar login e emissão de JWT no auth-service.
- [x] Proteger a consulta da própria conta no auth-service com JWT.
- [ ] Integrar cadastro e login ao gateway e ao aplicativo.
- [ ] Aplicar autorização para `USER`, `PROVIDER` e `ADMIN`.
- [ ] Implementar consulta e atualização do próprio perfil.
- [ ] Criar as telas mobile de acesso e perfil.

## Etapa 4 — Serviços

- [ ] Implementar cadastro e manutenção de serviços pelo provider.
- [ ] Implementar moderação administrativa.
- [ ] Implementar catálogo e detalhes para users.
- [ ] Implementar disponibilidade de horários pelo provider.
- [ ] Implementar contratação por agendamento pelo user.
- [ ] Implementar aprovação ou recusa do agendamento pelo provider.
- [ ] Implementar histórico de agendamentos para user e provider.
- [ ] Implementar pagamento simulado e as regras de cancelamento e remarcação.
- [ ] Implementar upload controlado de imagens.
- [ ] Construir as telas mobile do módulo.

## Etapa 5 — Conteúdo e participação

- [ ] Implementar BOMPRADROP e BOMPRALAB.
- [ ] Implementar BOMPRAVOZ com voto único.
- [ ] Implementar BOMPRAPRÊMIO sem pagamentos.
- [ ] Construir os respectivos fluxos mobile.

## Etapa 6 — Operação e qualidade

- [ ] Implementar auditoria e indicadores.
- [ ] Adicionar timeout, retry e circuit breaker nas integrações aplicáveis.
- [ ] Centralizar logs, métricas e rastreamento distribuído.
- [ ] Cobrir regras críticas com testes automatizados.
- [ ] Validar segurança, privacidade e acessibilidade.

## Etapa 7 — Homologação e entrega

- [ ] Executar testes de aceitação.
- [ ] Consolidar a documentação da arquitetura e dos endpoints.
- [ ] Registrar exemplos de uso com cURL.
- [ ] Documentar a execução completa por Docker Compose.
- [ ] Preparar o relatório acadêmico e as evidências da entrega.

