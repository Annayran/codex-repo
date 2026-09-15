# TGFPEM — Empresa produtos e impostos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 48
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EmpresaProdutoImpostos | Impostos / Informações por empresa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQGERAL | Alíquota Geral | F |  |  | N |
| ALIQICMSINTEFD | Alíquota Interna de ICMS | F |  |  | N |
| CALCDIFAL | Calcular DIFAL ? | S |  |  | N |
| CAT1799SPRES | Ressarcimento de ST (CAT-17/99) | S |  |  | N |
| CODCTACTBEFD | Conta Contábil para EFD | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODENQIPIENT | Código Enq. Legal IPI Entrada | I |  |  | N |
| CODENQIPISAI | Código Enq. Legal IPI Saída | I |  |  | N |
| CODESPECST | Código Especificador ST | I |  |  | N |
| CODFCI | Código da FCI | S |  |  | N |
| CODICMSFAST | Alíquota ICMS do Fast Service | F |  |  | N |
| CODLOCALPAD | Local Padrão | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODPRODROI | Produto | S |  |  | N |
| CSTIPIENT | Código Sit.Trib.IPI Entrada | I |  |  | N |
| CSTIPISAI | Código Sit.Trib.IPI Saída | I |  |  | N |
| DESCMAX | Descrição | F |  |  | N |
| ENQREINTEGRA | Enquadrado no Reintegra/Prev. | I |  |  | N |
| ESTMAX | Estoque Máximo | F |  |  | N |
| ESTMIN | Estoque Mínimo | F |  |  | N |
| GRADEPADRAO | Grade Padrão | C |  |  | N |
| GRUPOICMS | Grupo de ICMS | I |  |  | N |
| GRUPOICMS2 | Grupo de ICMS 2 | I |  |  | N |
| IDGRADE | Grade Produto | I |  |  | N |
| LEADTIME | Lead time de compra | I |  |  | N |
| LOCALIZACAO | Localização | S |  |  | N |
| MVAPADRAO | MVA Padrão | F |  |  | N |
| OBTSTANTMEDENT | Obtém ST Anterior pela média das entradas? | S |  |  | N |
| ORIGPROD | Origem do Produto | S |  |  | N |
| PERCCMTEST | % Carga Média Trib. Estadual | F |  |  | N |
| PERCCMTFED | % Carga Média Trib. Federal | F |  |  | N |
| PERCCMTIMP | % Carga Média Trib. Importação | F |  |  | N |
| PERCCMTNAC | % Carga Média Trib. Nacional | F |  |  | N |
| PERCREDBASEICMSEFET | Perc. Red. Base Icms Efetivo | F |  |  | N |
| RASTSTULTENTRADA | Usa Rastro/ST pela Ult. Entrada | S |  |  | N |
| TEMICMS | Tem ICMS | S |  |  | N |
| TEMIPICOMPRA | IPI na Entrada | S |  |  | N |
| TEMIPIVENDA | IPI na Saída | S |  |  | N |
| TEMRASTROLOTE | Tem Rastro do Lote | S |  |  | N |
| TIPCONTEST | Tipo de Controle de Estoque | S |  |  | N |
| TIPOITEMSPED | Tipo do item p/ SPED | S |  |  | N |
| TIPSUBST | Tipo de substituição | S |  |  | N |
| USAIDPALETE | Usa Pálete Identificado | S |  |  | N |
| USALOTEDTFAB | Usa Data de Fabricação | S |  |  | N |
| USALOTEDTVAL | Usa Data de Validade | S |  |  | N |
| USOPROD | Uso do Produto | S |  |  | N |
| VLRCOMERC | Valor de Comercialização - (R$) | F |  |  | N |
| VLRPARCIMPEXT | Valor da Parcela Importada do Exterior - (R$) | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConsolidacaoGiro / [TGFGIR1](TGFGIR1.md) | EmpresaProdutoImpostos / [TGFPEM](TGFPEM.md) | CODEMP->CODEMP; CODPROD->CODPROD | I |
| EmpresaProdutoImpostos / [TGFPEM](TGFPEM.md) | ConfiguracaoGradeProduto / [TGFGRD](TGFGRD.md) | IDGRADE->IDGRADE | I |
| EmpresaProdutoImpostos / [TGFPEM](TGFPEM.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| EmpresaProdutoImpostos / [TGFPEM](TGFPEM.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| EmpresaProdutoImpostos / [TGFPEM](TGFPEM.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCALPAD->CODLOCAL | L |
| EmpresaProdutoImpostos / [TGFPEM](TGFPEM.md) | PlanoConta /  | CODCTACTBEFD->CODCTACTB | L |
| EmpresaProdutoImpostos / [TGFPEM](TGFPEM.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | EmpresaProdutoImpostos / [TGFPEM](TGFPEM.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
