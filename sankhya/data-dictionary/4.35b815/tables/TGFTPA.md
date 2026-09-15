# TGFTPA — Tipo Atendimento

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TipoAtendimento | Tipo Atendimento | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARCFAT | Cód. Parceiro Faturar para | S |  |  | N |
| CODTAB | Cód. Tabela de Preço | I |  |  | N |
| CODTPA | Cód. Tipo Atendimento | I |  |  | N |
| DESCRICAO | Descr. Tipo Atendimento | S |  |  | N |
| MODELO | Modelo da Nota | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemNota / [TGFITE](TGFITE.md) | TipoAtendimento / [TGFTPA](TGFTPA.md) | CODTPA->CODTPA | I |
| TipoAtendimento / [TGFTPA](TGFTPA.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | MODELO->NUNOTA | I |
| TipoAtendimento / [TGFTPA](TGFTPA.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
