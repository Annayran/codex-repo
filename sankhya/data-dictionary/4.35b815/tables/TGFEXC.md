# TGFEXC — Tabela de Exeções

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Excecao | Exceção de Preço | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODLOCAL | Local | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODUSUALTREG | Cód. Usuário Alteração | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DHALTREG | Dt. Alteração | H |  |  | N |
| NUTAB | Tabela | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| VARIACAO | Variação % | F |  |  | S |
| VLRANT | Preço anterior | F |  |  | S |
| VLRVENDA | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Excecao / [TGFEXC](TGFEXC.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| Excecao / [TGFEXC](TGFEXC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Excecao / [TGFEXC](TGFEXC.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | NUTAB->NUTAB | I |
| TabelaPreco / [TGFTAB](TGFTAB.md) | Excecao / [TGFEXC](TGFEXC.md) | NUTAB->NUTAB | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
