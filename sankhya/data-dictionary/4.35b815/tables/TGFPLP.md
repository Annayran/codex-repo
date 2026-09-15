# TGFPLP — Planejamento de produção

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PlanejamentoProducaoQuibasa | Planejamento de produção | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Cód. Empresa | I |  |  | N |
| CODPROD | Cód. produto | I |  |  | N |
| CONCLUIDO | Concluído | S |  |  | N |
| DTALTER | Data da Geração | D |  |  | N |
| DTVAL | Dt. Validade Planejamento | D |  |  | N |
| NUMLOTE | Núm lote | I |  |  | N |
| NUMPLAN | Núm. Planejamento | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| QTDPROD | Quantidade | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PlanejamentoProducaoQuibasa / [TGFPLP](TGFPLP.md) | EtapaPlanejamentoProducao / [TGFEPL](TGFEPL.md) | NUMPLAN->NUMPLAN | I |
| PlanejamentoProducaoQuibasa / [TGFPLP](TGFPLP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
