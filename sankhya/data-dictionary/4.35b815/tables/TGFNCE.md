# TGFNCE — Coleta e Entrega por Nota

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ColetaEntregaPorNota | Coleta e Entrega por Nota | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CGC_CPF | CNPJ/CPF | S |  |  | N |
| CODMUNFIS | Mun. domicilio fiscal | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| INSCESTAD | Insc. estadual | S |  |  | N |
| NUNOTA | Nota | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| TIPOTRANSP | Tipo transporte | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ColetaEntregaPorNota / [TGFNCE](TGFNCE.md) | NUNOTA->NUNOTA | I |
| ColetaEntregaPorNota / [TGFNCE](TGFNCE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| ColetaEntregaPorNota / [TGFNCE](TGFNCE.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
