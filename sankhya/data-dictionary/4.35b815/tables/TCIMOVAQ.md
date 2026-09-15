# TCIMOVAQ — Movimentação PIS/COFINS na aquisição

- Módulo técnico: [TCI](../modules/TCI.md)
- Campos: 19
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MovimentacaoPisCofinsAquisicao | Movimentação PIS/COFINS na aquisição | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBEM | Código do bem | S |  |  | N |
| CODCENCUS | Centro resultado | I |  |  | N |
| CODCTACTB | Conta contábil | I |  |  | N |
| CODCTACTBCTPR | Conta contábil contrapartida | I |  |  | N |
| CODEMP | Código empresa | I |  |  | N |
| CODEMPCONTAB | Código empresa contábil | I |  |  | N |
| CODIMP | Tipo imposto | I |  |  | N |
| CODPROD | Código do produto | I |  |  | N |
| DTMOVTO | Data movimento | D |  |  | N |
| NUMLOTE | Número do Lote | I |  |  | N |
| NUPARCELA | Numero da parcela | I |  |  | N |
| PARCACONTAB | Parcelas a contabilizar | I |  |  | N |
| QTDPARC | Quantidade de parcelas | I |  |  | N |
| REFERENCIA | Data referência | D |  |  | N |
| SALACONTAB | Saldo a contabilizar | F |  |  | N |
| STATUS | Status | S |  |  | N |
| VLRAQUI | Valor aquisição | F |  |  | N |
| VLRPARC | Valor parcela | F |  |  | N |
| VLRTOTIMP | Valor total do imposto | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MovimentacaoPisCofinsAquisicao / [TCIMOVAQ](TCIMOVAQ.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| MovimentacaoPisCofinsAquisicao / [TCIMOVAQ](TCIMOVAQ.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| MovimentacaoPisCofinsAquisicao / [TCIMOVAQ](TCIMOVAQ.md) | EmpresaContablidade / [TSIEMP](TSIEMP.md) | CODEMPCONTAB->CODEMP | I |
| MovimentacaoPisCofinsAquisicao / [TCIMOVAQ](TCIMOVAQ.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| MovimentacaoPisCofinsAquisicao / [TCIMOVAQ](TCIMOVAQ.md) | PlanoConta /  | CODCTACTBCTPR->CODCTACTB | I |
| MovimentacaoPisCofinsAquisicao / [TCIMOVAQ](TCIMOVAQ.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
