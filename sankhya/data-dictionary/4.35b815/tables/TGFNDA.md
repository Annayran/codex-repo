# TGFNDA — Notas do Documento de Arrecadação

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NotasDocumentoArrecadacao | Notas do Documento de Arrecadação | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUFIN | Nro único Financeiro | I |  |  | N |
| NUNOTA | Nro único Nota | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Financeiro / [TGFFIN](TGFFIN.md) | NotasDocumentoArrecadacao / [TGFNDA](TGFNDA.md) | NUFIN->NUFIN | I |
| NotasDocumentoArrecadacao / [TGFNDA](TGFNDA.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| NotasDocumentoArrecadacao / [TGFNDA](TGFNDA.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
