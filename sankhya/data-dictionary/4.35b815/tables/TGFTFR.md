# TGFTFR — HistoricoVeiculo

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoVeiculo | Histórico Veiculo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODPARCTRANSP | Cód. Parceiro transportadora | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODVEICULO | Cód. Veículo | I |  |  | N |
| DTALTER | Data de alteração | H |  |  | N |
| DTVIGOR | Dt. Vigor | H |  |  | N |
| NUTABFRET | Numero tabela de frete | I |  |  | N |
| PRECO | Vlr. Frete | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Veiculo / [TGFVEI](TGFVEI.md) | HistoricoVeiculo / [TGFTFR](TGFTFR.md) | CODVEICULO->CODVEICULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
