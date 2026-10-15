# Validação planejada

## Contrato
Exemplo fictício deve satisfazer schema; rejeitar ausência de referência, datas inválidas, campos extras e evidência sem fonte. Formato de data exige validador com format habilitado.

## Homologação funcional
1. Selecionar caso já resolvido com estoque insuficiente e evidência da causa.
2. Acionar coleta em documento autorizado e comparar pacote com fontes reais.
3. Testar indisponibilidade de uma fonte: gap explícito, sem valor zero inventado.
4. Testar isolamento entre clientes/empresas e documento sem permissão.
5. Testar timeout, queda do receptor e resposta perdida: protocolo apenas após persistência e deduplicação no reenvio.
6. Medir duração e consultas, sem afetar estado operacional.
7. Comparar esforço do analista antes/depois e registrar informações ainda solicitadas.
8. Testar saldo alterado após falha: apresentar instante da coleta, sem causa histórica falsa.

Critérios numéricos serão definidos a partir da medição em homologação. Estes cenários ainda não foram executados.
