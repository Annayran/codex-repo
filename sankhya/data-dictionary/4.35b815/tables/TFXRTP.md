# TFXRTP — Restrições de Tipo de Negociação

- Módulo técnico: [TFX](../modules/TFX.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RestricaoPreco | Restrição de Preço | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODTPV | Cód. Tipo de Negociação | I |  |  | N |
| DHALTER | Data & Hora de Alteração | H |  |  | N |
| DHALTERTPV | Data & Hora Alteração Tipo de Negociação | H |  |  | N |
| ID | Identificação | I |  |  | N |
| NUTAB | Nro. Tabela de Preços | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RestricaoPreco / [TFXRTP](TFXRTP.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | NUTAB->NUTAB | I |
| RestricaoPreco / [TFXRTP](TFXRTP.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTPV->CODTIPVENDA; DHALTERTPV->DHALTER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
