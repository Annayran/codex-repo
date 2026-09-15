# TGFCRT — Tabela de Preço por CR

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
| TabelaPrecoCR | Tabela de Compra de Serviços/Produtos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCENCUS | Cód.Centro Resultado Custo | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODTAB | Cód.Tabela | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | Dt Alteração | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Parceiro / [TGFPAR](TGFPAR.md) | TabelaPrecoCR / [TGFCRT](TGFCRT.md) | CODPARC->CODPARC | I |
| TabelaPrecoCR / [TGFCRT](TGFCRT.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| TabelaPrecoCR / [TGFCRT](TGFCRT.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
