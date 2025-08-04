# Serviço de Gerenciamento de Pagamentos(Desafio Sefa)
## Tecnologias usadas
### API de Gração de Pagamentos
- Java jdk 17 e SpringBoot 3(maven)
- Mongo 8
- Kafka 4
### API de Processamento de Pagamentos
- Kotlin jdk 17 e SpringBoot 3(gradle)
- Mongo 8
- kafka 4
### Frontend
- Angular 20
- TypeScript
- Tailwind 4
- Gerador de client a partir da doc do swagger(lib swagger-typescript-api)
## Prints
### API de geração de pagamentos
![gera](./doc/imagens/api-gera-pagamentos.png)
---
### API de criação de pagamentos
![processa](./doc/imagens/api-processa-pagamentos.png)
### Frontend de pagamentos
![front-lista](./doc/imagens/tela-listagem.png)
![front-formulario](./doc/imagens/tela-formulario.png)
## Fluxo da Aplicação
![diagrama de fluxo](./doc/imagens/diagrama-fluxo.png)