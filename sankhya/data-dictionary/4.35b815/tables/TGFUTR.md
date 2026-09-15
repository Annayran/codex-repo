# TGFUTR — Unidade Transporte

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UnidadeTransporte | Unidade Transporte | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| IDUNIDTRANSP | Identificação Unidade Transporte | S |  |  | N |
| IDUTR | ID Unidade Transporte | I |  |  | N |
| NLACRE | Número Lacre | S |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| TPUNIDTRANSP | Tipo da Unidade de Transporte | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | UnidadeTransporte / [TGFUTR](TGFUTR.md) | NUNOTA->NUNOTA | I |
| UnidadeTransporte / [TGFUTR](TGFUTR.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| UnidadeTransporte / [TGFUTR](TGFUTR.md) | UnidadeCarga / [TGFUCA](TGFUCA.md) | IDUTR->IDUTR; NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
