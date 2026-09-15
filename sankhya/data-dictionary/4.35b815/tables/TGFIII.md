# TGFIII — Impostos de Importação

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ImpostosImportacao | Impostos de Importação - II | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BASEIMPOSTO | Base de Cálculo do Imposto de Importação | F |  |  | N |
| CODUSU | CODUSU | I |  |  | N |
| DHALTER | DHALTER | H |  |  | N |
| IMPTAGEXCNOTNAC | IMPTAGEXCNOTNAC | S |  |  | N |
| NUNOTA | NUNOTA | I |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| VLRDESPADUA | Valor das Despesas Aduaneiras | F |  |  | N |
| VLRIMPOSTO | Valor do Imposto de Importação | F |  |  | N |
| VLRIOF | Valor do IOF | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ImpostosImportacao / [TGFIII](TGFIII.md) | DeclaracaoImportacao / [TGFIDI](TGFIDI.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| ItemNota / [TGFITE](TGFITE.md) | ImpostosImportacao / [TGFIII](TGFIII.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
