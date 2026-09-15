# TGFFVR — Financeiro Venda Rápida

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 26
- Instâncias DWF: 1
- Relações DWF: 11
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FinanceiroVendaRapida | Financeiro Venda Rápida | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CARTAODESC | Taxa Administradora | F |  |  | N |
| CODBCO | Banco | I |  |  | N |
| CODCENCUS | Centro Resultado | I |  |  | N |
| CODCTABCOINT | Conta Bancária | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODNAT | Natureza | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODTIPOPER | Tipo Operação | I |  |  | N |
| CODTIPTIT | Tipo de Título | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESDOBRAMENTO | Desdob. | S |  |  | N |
| DESPCART | Despesas c/ Cartório | F |  |  | N |
| DHMOV | Dt/Hr Movimentação | H |  |  | N |
| DHTIPOPER | Dt/Hr Operação | H |  |  | N |
| DTALTER | Últ. Alteração | H |  |  | N |
| DTENTSAI | Dt. Entrada e Saída | D |  |  | N |
| DTNEG | Dt. Negociação | D |  |  | N |
| DTVENC | Dt. Vencimento | D |  |  | N |
| HISTORICO | Histórico | S |  |  | N |
| NUCVR | Nro Cab. Venda Rápida | I |  |  | N |
| NUFVR | Nro Único | I |  |  | N |
| NUNOTA | Nro Único Nota | I |  |  | N |
| ORIGEM | Origem | S |  |  | N |
| PROVISAO | Provisão | S |  |  | N |
| RECDESP | Receita/Despesa | I |  |  | N |
| VLRDESDOB | Vlr do Desdobramento | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | NUCVR->NUCVR | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | NUCVR->NUCVR | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
