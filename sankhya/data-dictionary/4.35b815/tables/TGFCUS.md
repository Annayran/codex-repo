# TGFCUS — Custos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 25
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Custo | Custos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALTPRECO | Altera Preço? | S |  |  | N |
| AUTOMATICO | Automático | S |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| CUSGER | Custo Gerencial | F |  |  | N |
| CUSMED | Custo Médio Gerencial | F |  |  | N |
| CUSMEDCALC | Custo Médio Calculado | S |  |  | N |
| CUSMEDICM | Custo Médio com ICMS | F |  |  | N |
| CUSREP | Custo de Reposição | F |  |  | N |
| CUSSEMICM | Custo Médio sem ICMS | F |  |  | N |
| CUSVARIAVEL | Custo Variável | F |  |  | N |
| DTATUAL | Data Atualização | D |  |  | N |
| ENTRADACOMICMS | Entrada com ICMS | F |  |  | N |
| ENTRADASEMICMS | Entrada sem ICMS | F |  |  | N |
| NUNOTA | Nro. Nota | I |  |  | N |
| PROCESSO | Processo | S |  |  | N |
| QTDNEG | Quantidade Negociada | F |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TOTALCOMICMS | Total com ICMS | F |  |  | N |
| TOTALCOMICMSANT | Total com ICMS Antigo | F |  |  | N |
| TOTALSEMICMS | Total sem ICMS | F |  |  | N |
| TOTALSEMICMSANT | Total sem ICMS Antigo | F |  |  | N |
| VLRVENDAFIXO | Valor de Venda Fixo | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Custo / [TGFCUS](TGFCUS.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Custo / [TGFCUS](TGFCUS.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| Custo / [TGFCUS](TGFCUS.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Custo / [TGFCUS](TGFCUS.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Custo / [TGFCUS](TGFCUS.md) | TarifasCIP /  | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
