# TGFCOMB — Embarcações do Comboio

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EmbarcacoesComboio | EmbarcacoesComboio | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODVEICULO | Cód. Embarcação | I |  |  | N |
| NUVIAG | NUVIAG | I |  |  | N |
| SEQMDFE | SEQMDFE | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EmbarcacoesComboio / [TGFCOMB](TGFCOMB.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | EmbarcacoesComboio / [TGFCOMB](TGFCOMB.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
