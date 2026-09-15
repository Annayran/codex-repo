# TGFDEP — Descontos Especiais

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DescontosEspeciais | Descontos Especiais | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATUNUVERSAO | Se atualiza NUVERSAO | S |  |  | N |
| CODPRODGANHE | Codigo do produto de brinde | I |  |  | N |
| LEVE | Numero de produtos levados | F |  |  | N |
| NUPROMOCAO | Codigo do desconto na TGFDES | I |  |  | N |
| NUVERSAO | Numero versão para checkout | I |  |  | N |
| PAGUE | Numero de produtos a pagar | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Desconto / [TGFDES](TGFDES.md) | DescontosEspeciais / [TGFDEP](TGFDEP.md) | NUPROMOCAO->NUPROMOCAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
