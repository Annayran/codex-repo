# TCICEX — Credito extemporâneo

- Módulo técnico: [TCI](../modules/TCI.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CredExtemp | Crédito Extemporâneo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BASECREDITO | Base do crédito | F |  |  | N |
| CODBEM | CODBEM | S |  |  | N |
| CODPROD | CODPROD | I |  |  | N |
| NUMPARCELA | Número da parcela | I |  |  | N |
| REFERENCIA | Referência p/apropriação | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Imobilizado / [TCIBEM](TCIBEM.md) | CredExtemp / [TCICEX](TCICEX.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
