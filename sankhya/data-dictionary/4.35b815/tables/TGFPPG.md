# TGFPPG — Gestao Fiscal Parcelas de Pagamento

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 27
- Instâncias DWF: 1
- Relações DWF: 11
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ParcelaPagamento | Gestão Fiscal de Parcelas de Pagamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BAIXA | Baixa na confirmação | S |  |  | N |
| BASEPRAZO | Base do prazo | I |  |  | N |
| CODBCOPAD | Banco | I |  |  | N |
| CODCENCUSPAD | Centro Resultado Padrão | I |  |  | N |
| CODCTABCOINT | Conta Bancária | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODNATPAD | Natureza Padrão | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODPROJPAD | Projeto | I |  |  | N |
| CODTIPTITPAD | Tipo de Título | I |  |  | N |
| CODTIPVENDA | Tipo de Negociação | I |  |  | N |
| COR | Cor da fonte | S |  |  | N |
| DIGVENDA | Opção de digitação | S |  |  | N |
| DTFIX | Vencto fixo | H |  |  | N |
| FORMULA | Fórmula | S |  |  | N |
| PARCEXCLUSIVAFCPST | Parcela exclusiva FCP-ST | S |  |  | N |
| PERCENTUAL | Percentual | F |  |  | N |
| PRAZO | Prazo | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| SOMAPRAZOCLIENTE | Soma prazo de cliente | S |  |  | N |
| TEXTOCENTRAL | Texto parcela | S |  |  | N |
| TIPOEMP | Tipo de Empresa | S |  |  | N |
| TIPOFIN | Tipo de Financeiro (REC/DESP) | S |  |  | N |
| TIPOPAR | Tipo de Parceiro | S |  |  | N |
| TIPRECDESP | Tipo de Receita/Despesa | S |  |  | N |
| USARDTENTSAIGNRE | Usar Dt. Entrada/Saída para vencimento da GNRE? | S |  |  | N |
| VENCNAOUTIL | Vencimento não útil | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ParcelaPagamento / [TGFPPG](TGFPPG.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCOPAD->CODBCO | I |
| ParcelaPagamento / [TGFPPG](TGFPPG.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUSPAD->CODCENCUS | I |
| ParcelaPagamento / [TGFPPG](TGFPPG.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| ParcelaPagamento / [TGFPPG](TGFPPG.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ParcelaPagamento / [TGFPPG](TGFPPG.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNATPAD->CODNAT | I |
| ParcelaPagamento / [TGFPPG](TGFPPG.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ParcelaPagamento / [TGFPPG](TGFPPG.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJPAD->CODPROJ | I |
| ParcelaPagamento / [TGFPPG](TGFPPG.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| ParcelaPagamento / [TGFPPG](TGFPPG.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTITPAD->CODTIPTIT | I |
| TaxasEspeciais / [TGFTAX](TGFTAX.md) | ParcelaPagamento / [TGFPPG](TGFPPG.md) | CODTIPVENDA->CODTIPVENDA | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | ParcelaPagamento / [TGFPPG](TGFPPG.md) | CODTIPVENDA->CODTIPVENDA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
