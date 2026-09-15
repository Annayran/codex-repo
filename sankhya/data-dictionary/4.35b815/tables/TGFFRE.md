# TGFFRE — Acerto de Frete

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
| AcertoFrete | Acerto de Frete | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | Data de Alteração | H |  |  | N |
| NUACERTO | Número do acerto | I |  |  | N |
| NUFIN | Número do financeiro | F |  |  | N |
| NUFINORIG | Número único Financeiro Origem | F |  |  | N |
| NUNOTA | Número da Nota | F |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPACERTO | Tipo de Acerto | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcertoFrete / [TGFFRE](TGFFRE.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
