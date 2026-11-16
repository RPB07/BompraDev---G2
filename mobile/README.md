# Aplicativo mobile

Aplicativo Android do BompraDev desenvolvido com React Native, Expo SDK 58 e JavaScript.

## Requisitos

- Node.js 24
- npm
- Expo Go ou emulador Android compatível

## Configuração

Copie o arquivo de exemplo e informe o endereço do API Gateway:

```powershell
Copy-Item .env.example .env
```

Em um dispositivo físico, `localhost` aponta para o próprio aparelho. Nesse caso, utilize o endereço IP da máquina que está executando o backend.

## Execução

```powershell
npm install
npm run start
```

## Estrutura

- `src/app`: rotas e layouts do Expo Router.
- `src/components`: componentes reutilizáveis.
- `src/services`: comunicação com serviços externos.
- `src/storage`: adapters de armazenamento local seguro.
- `src/theme`: cores, espaçamentos e tipografia.

