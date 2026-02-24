# RykunEventos

Base de plugin para Hytale com configuração **in-game** via comando `/re`.

## Comandos
- `/re reload`
- `/re save`
- `/re config list <chat|arena> <evento>`
- `/re config toggle <chat|arena> <evento>`
- `/re config set <chat|arena> <evento> <chave> <valor>`

## Fluxo recomendado
1. Copie o plugin para o servidor.
2. Execute `/re config list chat votacao` para ver as opções.
3. Ajuste com `/re config set chat votacao reward 500`.
4. Ative/desative com `/re config toggle chat votacao`.
5. Salve com `/re save`.
