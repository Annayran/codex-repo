# Plano Tecnico - Integracao Sankhya x Genesis

## Objetivo

Estruturar a integracao para enviar movimentos de entrada e saida de estoque controlados por serie do Sankhya para a Genesis, mantendo dashboard e rastreabilidade de envio.

## Etapas

1. Validar ambiente Sankhya, SDK Java e ferramenta de build.
2. Definir modelagem final de `Status Estoque` por serie.
3. Criar campos adicionais de garantia e status conforme decisao de modelagem.
4. Criar tabela de controle `AD_GENMOVEST`.
5. Criar VIEW `AD_VW_GENESIS_ESTOQUE` usando as tabelas Sankhya confirmadas.
6. Criar dashboard sobre a VIEW.
7. Implementar repository para buscar pendentes e atualizar status.
8. Implementar builder para validar e montar `EstoqueGenesisDTO`.
9. Implementar cliente HTTP Genesis com token em cofre/parametro seguro.
10. Adaptar `IntegracaoGenesisScheduleAction` para `com.sankhya.schedule.ScheduleAction` quando o SDK Sankhya estiver disponivel.
11. Homologar API Genesis com uma entrada e uma saida.
12. Executar matriz de testes e ajustar retry/idempotencia.

## Decisoes em aberto

- Local final do `Status Estoque`: `TGFITE`, `TGFSER` ou tabela adicional.
- Unidade de epoch da Genesis: segundos ou milissegundos.
- Se `codigoExterno` sera chave de upsert.
- TOPs finais elegiveis.
- Intervalo da acao agendada.
- Volume diario esperado.

## Criterio de pronto inicial

- Projeto estruturado.
- SQL base versionado.
- Classes principais criadas.
- Nenhuma credencial hardcoded.
- Contexto `.ai/context` atualizado.

