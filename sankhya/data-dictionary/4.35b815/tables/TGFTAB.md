# TGFTAB — TabelaPreco

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 3
- Relações DWF: 14
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TabelaPreco | Tabela de Preço | S | N |
| TabelaPrecoMinimo | Tabela de Preço Mínimo | S | N |
| TabelaPrecoPF | Tabela de Preço | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTAB | Código Tabela | I |  |  | N |
| CODTABORIG | Tabela de Origem | I |  |  | N |
| DTALTER | Dt. Alteração | D |  |  | N |
| DTVIGOR | Dt. Vigor | D |  |  | N |
| JAPE_ID | JAPE_ID | S |  |  | N |
| NUTAB | Nro. Único | I |  |  | N |
| PERCENTUAL | Percentual % | F |  |  | N |
| UTILIZADECCUSTO | Utiliza decimais do custo para preço | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EmpresaTabPreco / [TGFEMP](TGFEMP.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | CODTAB->CODTAB | I |
| Excecao / [TGFEXC](TGFEXC.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | NUTAB->NUTAB | I |
| LocalTabPreco / [TGFLOC](TGFLOC.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | CODTAB->CODTAB | I |
| NomeTabelasPreco / [TGFNTA](TGFNTA.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | CODTAB->CODTAB | I |
| ParceiroEmpresaTabPreco / [TGFPAEM](TGFPAEM.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | CODTAB->CODTAB | I |
| ParceiroTabPreco / [TGFPAR](TGFPAR.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | CODTAB->CODTAB | I |
| Perfil / [TGFTPP](TGFTPP.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | CODTAB->CODTAB | I |
| PerfilTabPreco / [TGFTPP](TGFTPP.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | CODTAB->CODTAB | I |
| RegiaoTabPreco / [TSIREG](TSIREG.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | CODTAB->CODTAB | I |
| RestricaoPreco / [TFXRTP](TFXRTP.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | NUTAB->NUTAB | I |
| TabelaPreco / [TGFTAB](TGFTAB.md) | Excecao / [TGFEXC](TGFEXC.md) | NUTAB->NUTAB | I |
| TabelaPreco / [TGFTAB](TGFTAB.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| TabelaPreco / [TGFTAB](TGFTAB.md) | NomeTabelasPrecoOrigem / [TGFNTA](TGFNTA.md) | CODTABORIG->CODTAB | I |
| VendedoresTabPreco / [TGFNPV](TGFNPV.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | CODTAB->CODTAB | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
