# TCSITS — Tabela de situação

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| SituacaoSubOs | Status Sub-OS | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODSIT | Código | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemOrdemServico / [TCSITE](TCSITE.md) | SituacaoSubOs / [TCSITS](TCSITS.md) | CODSIT->CODSIT | I |
| RelacionamentoServicoStatus / [TCSSES](TCSSES.md) | SituacaoSubOs / [TCSITS](TCSITS.md) | CODSIT->CODSIT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
