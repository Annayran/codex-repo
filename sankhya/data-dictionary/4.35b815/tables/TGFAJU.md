# TGFAJU — Tabela de Utilização de Créditos Fiscais

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UtilizacaoCreditosFiscais | Utilização de Créditos Fiscais | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. usuário | I |  |  | N |
| DTALTER | Dt. alteração | D |  |  | N |
| NUAJA | Nro. único ajuste | I |  |  | N |
| NUMDOC | Num. Documento | I |  |  | N |
| SEQAJU | Sequência Ajuste | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPUTIL | Tipo de utilização | I |  |  | N |
| TOTALCRED | Tot. Crédito Utilizado | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ControleCreditosFiscais / [TGFAJC](TGFAJC.md) | UtilizacaoCreditosFiscais / [TGFAJU](TGFAJU.md) | NUAJA->NUAJA; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
