# TCSFPE — Insumos de etapas

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| InsumoEtapa | Insumo de Etapa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODLOCAL | Local | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODVOL | Volume | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| NUFAP | FAP | I |  |  | N |
| NUMETAPA | Etapa | I |  |  | N |
| QTDNEG | Qtd. Negociada | F |  |  | N |
| QTDREQUISITADA | Qtd. Requisitada | F |  |  | S |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EtapaProjeto / [TCSFET](TCSFET.md) | InsumoEtapa / [TCSFPE](TCSFPE.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |
| InsumoEtapa / [TCSFPE](TCSFPE.md) | EtapaProjeto / [TCSFET](TCSFET.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |
| InsumoEtapa / [TCSFPE](TCSFPE.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| InsumoEtapa / [TCSFPE](TCSFPE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| InsumoEtapa / [TCSFPE](TCSFPE.md) | ProjetoServico / [TCSFAP](TCSFAP.md) | NUFAP->NUFAP | I |
| InsumoEtapa / [TCSFPE](TCSFPE.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
