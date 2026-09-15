# TGFCLX — Configuração para Lançamento de Extrato

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 10
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConfiguracaoLancamentoExtrato | Configuração para Lançamento de Extrato | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CATLANC | Categoria de Lançamento (EDI) | I |  |  | N |
| CODBCO | Banco | I |  |  | N |
| CODCENCUS | Centro de Resultado | I |  |  | N |
| CODEMP | Empresa do Financeiro | I |  |  | N |
| CODLANC | Lançamento | I |  |  | N |
| CODNAT | Natureza | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODPROJ | Projeto | I |  |  | N |
| CODTIPOPER | Tipo de Operação | I |  |  | N |
| CODTIPOPERBAIXA | Tipo de Operação Baixa | I |  |  | N |
| CODTIPTIT | Tipo de Título | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | Dt. alteração | H |  |  | N |
| HIST | Histórico | S |  |  | N |
| TIPOTRANSACAO | Tipo de transação (OFX) | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | HistoricoBancario / [TGFHBC](TGFHBC.md) | CODLANC->CODLANC | I |
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | TipoOperacaoBaixa / [TGFTOP](TGFTOP.md) | CODTIPOPERBAIXA->CODTIPOPER | I |
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
