# TGFREJNFE — Rejeição NFe

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RejeicaoNfe | Rejeição NFe | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODREJEICAO | Cód. Rejeição | I |  |  | N |
| HASHUTLREJEICAO | Hash Ultima Rejeição | S |  |  | N |
| NUNOTA | Nro. Nota | I |  |  | N |
| QTDREJEICOES | Qtd. Rejeições | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RejeicaoNfe / [TGFREJNFE](TGFREJNFE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
