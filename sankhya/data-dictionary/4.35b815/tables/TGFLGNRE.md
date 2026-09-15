# TGFLGNRE — Lote GNRE

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LoteGNRE | LoteGNRE | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Usuário | I |  |  | N |
| DHLOTE | Data do lote | H |  |  | N |
| NROLOTEGNRE | Nro. Lote GNRE | I |  |  | N |
| NROPROTLOTE | Nro. Protocolo | S |  |  | N |
| QTDTIT | Quantidade de titulos | I |  |  | N |
| SITUACAOATUALLOTE | Situação atual do Lote | S |  |  | N |
| STATUSLOTE | Status do Lote | S |  |  | N |
| VLRTOT | Valor total | F |  |  | N |
| XMLLOTE | XML  do Lote | C |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LoteGNRE / [TGFLGNRE](TGFLGNRE.md) | Financeiro / [TGFFIN](TGFFIN.md) | NROLOTEGNRE->NROLOTEGNRE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
