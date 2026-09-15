# TGFLREA — Alíquota REA/DF

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AliquotaReaDf | Alíquota REA/DF | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQUOTA | Alíquota | F |  |  | N |
| ATUALREA | Atualiza REA | I |  |  | N |
| BASE | Base | F |  |  | N |
| CODCFO | CFO | I |  |  | N |
| NUMITEMREA | Nro. Item REA | I |  |  | N |
| NUNOTA | Nro. único da nota | I |  |  | N |
| ORIGEM | Origem | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| VALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaReaDf / [TGFLREA](TGFLREA.md) | ClassificacaoFiscalOperacao / [TGFCFO](TGFCFO.md) | CODCFO->CODCFO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
