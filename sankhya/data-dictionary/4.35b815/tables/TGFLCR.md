# TGFLCR — LocalRetirada

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LocalRetirada | LocalRetirada | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODEMP | Código | I |  |  | N |
| CODPARCRETIRADA | Cód. Parceiro Retirada | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Empresa / [TSIEMP](TSIEMP.md) | LocalRetirada / [TGFLCR](TGFLCR.md) | CODEMP->CODEMP | I |
| LocalRetirada / [TGFLCR](TGFLCR.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| LocalRetirada / [TGFLCR](TGFLCR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCRETIRADA->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
