# TGFNAS — TGFNAS

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NaturezaOperacaoMunicipio | Natureza da Operação por Município | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMUNFIS | Município Fiscal | I |  |  | N |
| CODNATOPER | Natureza da Operação | S |  |  | N |
| DESCRNATOPER | Descrição | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TopNaturezaOperacaoMunicipio / [TGFTNA](TGFTNA.md) | NaturezaOperacaoMunicipio / [TGFNAS](TGFNAS.md) | CODNATOPER->CODNATOPER; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
