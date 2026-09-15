# TGFIVC — Itens de volume conferido

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemVolumeConferencia | Itens de volume conferido | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBARRA | Cód. Barras | S |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODVOL | UN | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| IMPRIMEAUTO | Impressão automática | S |  |  | N |
| NUCONF | Nro conferência | I |  |  | N |
| QTD | Quantidade | F |  |  | N |
| QTDVOLPAD | Qtd UN padrão | F |  |  | N |
| SEQITEM | Seq. Item do Volume | I |  |  | N |
| SEQVOL | Seq. Volume | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemVolumeConferencia / [TGFIVC](TGFIVC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| VolumeConferencia / [TGFVCF](TGFVCF.md) | ItemVolumeConferencia / [TGFIVC](TGFIVC.md) | NUCONF->NUCONF; SEQVOL->SEQVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
