# TGFINCT — Relação de Itens do CTe

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RelacaoItemCTe | Relação de Itens do CTe | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCPROD | Descrição do Produto | S |  |  | N |
| NUNOTA | Nro. Nota | I |  |  | N |
| QUANTIDADE | Quantidade | F |  |  | N |
| SEQNOTA | Sequência NF-e | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| UNIDADE | Unidade | S |  |  | N |
| VALTOTAL | Valor Total | F |  |  | N |
| VALUNIT | Valor Unitário | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RelacaoItemCTe / [TGFINCT](TGFINCT.md) | NotaConhecimentoTransporte / [TGFNCT](TGFNCT.md) | NUNOTA->NUNOTA; SEQNOTA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
