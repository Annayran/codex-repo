# TGFREN — GF Mapeamento das Renegociações

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Renegociacao | Renegociação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | Data e hora da alteração | H |  |  | N |
| NUFIN | Número do financeiro | I |  |  | N |
| NURENEG | Número da renegociação | I |  |  | N |
| NURENEGORIG | Número da renegociação de origem | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Financeiro / [TGFFIN](TGFFIN.md) | Renegociacao / [TGFREN](TGFREN.md) | NUFIN->NUFIN | I |
| Renegociacao / [TGFREN](TGFREN.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| Renegociacao / [TGFREN](TGFREN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
