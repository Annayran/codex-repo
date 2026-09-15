# TGFNTP — Natureza de Operação

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
| NaturezaOperacao | Natureza de Operação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODNATOPERISS | Código da Natureza da Operação | S |  |  | N |
| DESCNATOPERISS | Descrição da Natureza da Operação | S |  |  | N |
| IMUNEISENTA | Imune/Isenta | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaISS / [TGFISS](TGFISS.md) | NaturezaOperacao / [TGFNTP](TGFNTP.md) | CODNATOPERISS->CODNATOPERISS | I |
| ConfiguracaoEmpresa / [TGFSEM](TGFSEM.md) | NaturezaOperacao / [TGFNTP](TGFNTP.md) | CODNATOPERISS->CODNATOPERISS | I |
| Contrato / [TCSCON](TCSCON.md) | NaturezaOperacao / [TGFNTP](TGFNTP.md) | CODNATOPERISS->CODNATOPERISS | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
