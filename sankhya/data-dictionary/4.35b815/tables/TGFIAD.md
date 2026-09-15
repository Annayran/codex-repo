# TGFIAD — Adições Importação

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AdicoesImportacao | Adições de Importação - AD | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODFABRICANTE | Código do Fabricante | S |  |  | N |
| CODUSU | CODUSU | I |  |  | N |
| DHALTER | DHALTER | H |  |  | N |
| NROADICAO | Nro. Adição | I |  |  | N |
| NUNOTA | NUNOTA | I |  |  | N |
| SEQAD | Seq. AD | I |  |  | N |
| SEQDI | SEQDI | I |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| VLRDESC | Valor Desconto | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DeclaracaoImportacao / [TGFIDI](TGFIDI.md) | AdicoesImportacao / [TGFIAD](TGFIAD.md) | NUNOTA->NUNOTA; SEQDI->SEQDI; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
