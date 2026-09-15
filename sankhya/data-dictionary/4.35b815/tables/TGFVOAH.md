# TGFVOAH — Histórico de Unidades Alternativas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoUnidadeAlternativa | Histórico de Unidade Alternativa | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBARRA | Código de Barras | S |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| CODVOL | Unidade | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DHALTER | Dt. Alteração | H |  |  | N |
| QUANTIDADE | Quantidade | F |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| HistoricoUnidadeAlternativa / [TGFVOAH](TGFVOAH.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| HistoricoUnidadeAlternativa / [TGFVOAH](TGFVOAH.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| HistoricoUnidadeAlternativa / [TGFVOAH](TGFVOAH.md) | VolumeAlternativo / [TGFVOA](TGFVOA.md) | CODPROD->CODPROD; CODVOL->CODVOL; CONTROLE->CONTROLE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
