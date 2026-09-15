# TGMAI — Autorização de Investimento

- Módulo técnico: [TGM](../modules/TGM.md)
- Campos: 18
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AutorizacaoInvestimento | Autorização de Investimento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BLOQUEADO | Bloqueada | S |  |  | N |
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODMETA | Orçamento | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| CODPROJ | Projeto | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | Dt.Alteração | H |  |  | N |
| DTAUTINV | Dt.Autorização | H |  |  | N |
| NUAUTINV | Nro Autorização | I |  |  | N |
| OBSERVACAO | Observações | S |  |  | N |
| VLRAUTINV | Vlr.Autorização | F |  |  | N |
| VLRCANCELADO | Vlr.Cancelado | F |  |  | N |
| VLRCOMPROMISSO | Vlr Compromisso | F |  |  | N |
| VLRSUPLEMENTO | Vlr.Suplementação | F |  |  | N |
| VLRTRANSFERENCIA | Vlr.Transferência | F |  |  | N |
| VLRTRANSFSALDO | Transferência Saldo | F |  |  | N |
| VLRUSADO | Vlr.Usado | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AutorizacaoInvestimento / [TGMAI](TGMAI.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| AutorizacaoInvestimento / [TGMAI](TGMAI.md) | ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CODMETA->CODMETA | I |
| AutorizacaoInvestimento / [TGMAI](TGMAI.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| AutorizacaoInvestimento / [TGMAI](TGMAI.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| AutorizacaoInvestimento / [TGMAI](TGMAI.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| AutorizacaoInvestimento / [TGMAI](TGMAI.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
