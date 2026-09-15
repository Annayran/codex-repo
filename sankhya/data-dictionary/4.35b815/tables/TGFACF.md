# TGFACF — GF  Acerto de Ordem de Carga Financeiros e Notas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AcertoOrdemCarga | Acerto de Ordem de Carga | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODHIST | Histórico | I |  |  | N |
| NUFIN | Número Único do Financeiro | I |  |  | N |
| NUNOTA | Número Único da Nota | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| ORDEMCARGA | Ordem de Carga | I |  |  | N |
| RETORNADO | Retornado | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcertoOrdemCarga / [TGFACF](TGFACF.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
