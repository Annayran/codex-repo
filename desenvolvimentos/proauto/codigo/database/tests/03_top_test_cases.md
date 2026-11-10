# Casos de teste — elegibilidade das 72 TOPs

## Objetivo

Comprovar a diferença entre criar/manter `AD_TGFSEQENT` e apenas proteger o estoque reservado.

## Grupos obrigatórios

| Grupo | TIPMOV | Quantidade levantada | Resultado candidato |
|---|---:|---:|---|
| Pedido de venda | `P` | 14 | Criar reserva, sujeito a validação individual |
| Ordem de produção | `J` | 1 | Criar reserva, sujeito a validação funcional |
| Saída/faturamento | `V` | 30 | Não criar fila; validar proteção |
| Entrada/devolução | `E` | 13 | Não criar fila; validar proteção |
| Transferência | `T` | 8 | Não criar fila; validar proteção |
| Requisição/baixa | `Q` | 3 | Não criar fila; validar proteção |
| Produção | `F` | 1 | Não criar fila; validar proteção |
| Ajuste/custo | `C` | 2 | Não criar fila; validar proteção |

## Execução mínima por TOP

Para cada TOP homologável:

1. Criar documento com um produto sem reserva e confirmar.
2. Repetir com produto presente em `AD_VGFRESEST`.
3. Conferir criação, alteração ou ausência de linha em `AD_TGFSEQENT`.
4. Conferir mensagem/bloqueio de estoque.
5. Cancelar ou excluir e validar ausência de resíduo.
6. Repetir a confirmação e validar idempotência.
7. Registrar `NUNOTA`, TOP/versão, TIPMOV, antes/depois e usuário.

## Casos focais

- TOPs `900`, `901`, `902`: pedidos de venda regulares.
- TOPs `906`, `911`: entrega futura.
- TOP `401`: fila para ordem de produção.
- TOPs `500` a `502`: baixas/requisições.
- TOPs `700`, `701`, `704`, `1706` a `1710`: transferências.
- TOPs descritas como inativas: não devem ser testadas como válidas sem decisão do responsável.
- TOPs de ajuste de estoque: validar em conjunto com a regra 26.

## Aceite

- Somente TOPs aprovadas criam posição ativa.
- Operações de proteção não deixam registros ocultos na tabela da fila.
- Exclusão/cancelamento não deixa órfãos.
- Nenhum cenário gera commit parcial.
- Reexecução não duplica posição ou quantidade.

