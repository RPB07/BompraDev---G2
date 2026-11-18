# Visão e escopo do MVP

## Contexto

Profissionais, estudantes e entusiastas de tecnologia encontram conteúdos, oportunidades e serviços em fontes diferentes. O BompraDev busca organizar parte dessa experiência em um aplicativo mobile simples, permitindo também a participação da comunidade e a atuação de prestadores de serviços.

## Objetivo

Desenvolver uma aplicação mobile integrada a um backend baseado em microsserviços. A solução deve permitir acesso a conteúdos de tecnologia, participação em enquetes, consulta a experiências promocionais e contratação agendada de serviços publicados por providers.

## Perfis e responsabilidades

O cadastro exige nome, e-mail e senha. Uma opção de marcação permite declarar que a pessoa oferece serviços: quando selecionada, a conta recebe o perfil `PROVIDER`; caso contrário, recebe `USER`. Não há necessidade de comprovação nem aprovação administrativa para se tornar provider. Um user também poderá ativar o perfil provider posteriormente na própria conta. A atribuição de `ADMIN` não faz parte do cadastro público.

Todos os usuários terão perfil público com biografia editável e espaço para portfólio. Os dados de acesso não serão públicos.

### USER

- Criar conta e autenticar-se.
- Consultar conteúdos publicados.
- Consultar serviços disponíveis.
- Selecionar um horário disponível e solicitar o agendamento de um serviço.
- Acompanhar os próprios agendamentos.
- Participar de enquetes abertas, respeitando a regra de voto único.
- Consultar itens promocionais e solicitar resgates.
- Acompanhar o próprio histórico.

### PROVIDER

- Utilizar as funcionalidades permitidas ao usuário autenticado.
- Cadastrar e manter os próprios serviços.
- Alterar a disponibilidade e os dados dos próprios serviços.
- Disponibilizar horários para contratação.
- Aprovar ou recusar as solicitações de agendamento recebidas.
- Acompanhar os agendamentos relacionados aos próprios serviços.

O provider não poderá administrar serviços pertencentes a outro provider nem atribuir a si mesmo permissões administrativas.

### ADMIN

- Gerenciar conteúdos e seus estados de publicação.
- Moderar serviços e administrar regras da plataforma.
- Gerenciar enquetes, itens promocionais e solicitações de resgate.
- Consultar indicadores e registros de auditoria.
- Administrar usuários dentro das permissões estabelecidas.

## Escopo inicial

- Aplicativo Android desenvolvido com React Native e Expo.
- Cadastro, autenticação, sessão e autorização por perfil.
- Conteúdos BOMPRADROP e BOMPRALAB.
- Enquetes BOMPRAVOZ.
- Catálogo e solicitações do BOMPRAPRÊMIO, sem operação financeira.
- Cadastro e consulta de serviços.
- Disponibilidade de horários e contratação de serviços por agendamento.
- Aprovação do agendamento pelo provider responsável pelo serviço.
- Pagamento simulado dos serviços, sem movimentação de dinheiro.
- Cancelamento pelo contratante com menos de duas horas de antecedência: cobrança simulada de 50% do valor do serviço.
- Cancelamento pelo provider: o contratante poderá remarcar para horário disponível sem novo pagamento.
- Horários solicitados ficam indisponíveis na agenda para evitar reservas simultâneas.
- Histórico de agendamentos para users e providers.
- Upload controlado de imagens.
- Histórico das interações relevantes.
- Área administrativa e indicadores básicos.
- Backend organizado em microsserviços com entrada por API Gateway.
- Persistência versionada, testes e documentação de execução.

## Fora do escopo confirmado

- Pagamentos por cartão, PIX, boleto ou carteira digital.
- Split de pagamentos e repasses financeiros.
- Marketplace com múltiplos vendedores de produtos.
- Carrinho e checkout.
- Gamificação avançada.
- Cursos completos e emissão de certificados.
- Rede social interna e mensagens privadas.
- Recomendações personalizadas por inteligência artificial.

## Decisões pendentes

Os pontos abaixo não serão implementados até que suas regras sejam validadas:

- Estados adicionais e transições posteriores à aprovação do agendamento.
- Demais condições de reagendamento e cancelamento, incluindo o tratamento do valor pago em outras situações.
- Duração dos serviços e formação dos horários disponíveis.
- Tratamento de conflitos e concorrência na reserva de horários.
- Avaliações de providers e serviços.
- Comunicação entre user e provider.
- Critério de aprovação ou moderação de serviços.

## Restrições acadêmicas

- A evolução do projeto deve ocorrer em entregas incrementais verificáveis.
- O frontend deve apresentar navegação clara, componentização, tratamento de erros e organização consistente.
- O backend deve utilizar microsserviços independentes e demonstrar comunicação resiliente entre serviços.
- A solução deve possuir configuração centralizada, descoberta de serviços, API Gateway, autenticação stateless, observabilidade e containerização.
- O código deve seguir princípios de legibilidade, baixo acoplamento, coesão, simplicidade e testabilidade.
- As decisões e adaptações ao modelo apresentado em aula devem ser justificadas na documentação.

