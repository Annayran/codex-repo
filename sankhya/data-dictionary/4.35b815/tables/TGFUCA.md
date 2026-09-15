# TGFUCA — Unidade Carga

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UnidadeCarga | Unidade Carga | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| IDUCA | ID Unidade Carga | I |  |  | N |
| IDUNIDCARGA | Identificação Unidade Carga | S |  |  | N |
| IDUTR | ID Unidade Transporte | I |  |  | N |
| NLACRE | Número Lacre | S |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| QTDRAT | Quantidade Rateada | F |  |  | N |
| TPUNIDCARGA | Tipo da Unidade de Carga | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| UnidadeCarga / [TGFUCA](TGFUCA.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| UnidadeTransporte / [TGFUTR](TGFUTR.md) | UnidadeCarga / [TGFUCA](TGFUCA.md) | IDUTR->IDUTR; NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
