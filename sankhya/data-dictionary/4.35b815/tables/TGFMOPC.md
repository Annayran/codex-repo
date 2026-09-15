# TGFMOPC — Moedas p/ Portal cot.

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MoedaPortalCotacao | Moedas p/ Portal cot. | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMOEDA | Moeda | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| DESCRMOEDA | Descrição para o portal | S |  |  | N |
| PADRAO | Usar como padrão | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MoedaPortalCotacao / [TGFMOPC](TGFMOPC.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| Parceiro / [TGFPAR](TGFPAR.md) | MoedaPortalCotacao / [TGFMOPC](TGFMOPC.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
