# TSIDIS — Rotas para Cargas

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RotaCarga | Rotas para Cargas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCIDDEST | Cód. Cidade Destino | I |  |  | N |
| CODCIDORIG | Cód. Cidade Origem | I |  |  | N |
| CODPARCDEST | Cód. Parceiro Destino | I |  |  | N |
| CODPARCORIG | Cód. Parceiro Origem | I |  |  | N |
| DISTANCIA | Distância em km | F |  |  | N |
| OBS | Observações | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RotaCarga / [TSIDIS](TSIDIS.md) | CidadeDestino / [TSICID](TSICID.md) | CODCIDDEST->CODCID | I |
| RotaCarga / [TSIDIS](TSIDIS.md) | CidadeOrigem / [TSICID](TSICID.md) | CODCIDORIG->CODCID | I |
| RotaCarga / [TSIDIS](TSIDIS.md) | ParceiroDestino / [TGFPAR](TGFPAR.md) | CODPARCDEST->CODPARC | I |
| RotaCarga / [TSIDIS](TSIDIS.md) | ParceiroOrigem / [TGFPAR](TGFPAR.md) | CODPARCORIG->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
