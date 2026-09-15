# TCSSES — Relacionamento serviço status

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RelacionamentoServicoStatus | Status SubOS | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| CODSIT | Status | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RelacionamentoServicoStatus / [TCSSES](TCSSES.md) | SituacaoSubOs / [TCSITS](TCSITS.md) | CODSIT->CODSIT | I |
| Servico / [TGFPRO](TGFPRO.md) | RelacionamentoServicoStatus / [TCSSES](TCSSES.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
