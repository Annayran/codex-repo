# TGFCFO — Código Fiscal de Operações

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 17
- Instâncias DWF: 8
- Relações DWF: 16
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ClaFiscalCombustivelLubrif | Classificação Fiscal Operações Para Combustíveis e Lubrificantes | S | N |
| ClaFiscalOperacaoEntrada | Classificação Fiscal de Operações Entrada | S | N |
| ClaFiscalOperacaoEntradaEstado | Classificação Fiscal de Operações de Entrada de Fora do Estado. | S | N |
| ClaFiscalOperacaoSaida | Classificação Fiscal de Operações Saída | S | N |
| ClaFiscalOperacaoSaidaEstado | Classificação Fiscal de Operações de Saída de Fora do Estado | S | N |
| ClaFiscalOperacaoTerceiros | Classificação Fiscal de Operações Para Produtos Terceiros | S | N |
| ClaFiscalPrestacaoServico | Código Fiscal de Prestações de Serviço | S | N |
| ClassificacaoFiscalOperacao | Classificação Fiscal de Operações | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CALCDIFICMS | Calcular Diferença de ICMS | S |  |  | N |
| CODCFO | Código | I |  |  | N |
| CODCTACTB | Conta Contábil | I |  |  | N |
| CONVPRODUZ | Convênio Produzir | S |  |  | N |
| DESCONSIDERARCFOREG47 | Desconsiderar CFOP na geração do registro 47 da DIME-SC? | S |  |  | N |
| DESCRCFO | Descrição da CFOP | S |  |  | N |
| EMPCODSIT08EFD | Empresas para considerar a situação do documento '08' nos EFDs | S |  |  | N |
| GRUPOCFO | Grupo da CFOP | I |  |  | N |
| INDAQUISICAO | Indicador de Aquisição | S |  |  | N |
| MOVIMFISICA | Movimentação Física do Item/Produto (IND_MOV C170 EFD) | S |  |  | N |
| OPERESSACOMP | Operação com Ressarcimento/Complemento de ST | S |  |  | N |
| RECBRUTACIAP | Receita Bruta p/ CIAP | S |  |  | N |
| RECBRUTAEFDBLOCOP | Receita bruta p/EFD Contribuições | S |  |  | N |
| TIPICMS | ICMS p/ o Livro Fiscal | S |  |  | N |
| TIPO | Tipo | S |  |  | N |
| TIPOPERPRODEPE | Tipo de Operação PRODEPE | S |  |  | N |
| TRIBUTADASCIAP | Utilizar nas Tributadas do CIAP | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaReaDf / [TGFLREA](TGFLREA.md) | ClassificacaoFiscalOperacao / [TGFCFO](TGFCFO.md) | CODCFO->CODCFO | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ClassificacaoFiscalOperacao / [TGFCFO](TGFCFO.md) | CODCFO->CODCFO | I |
| ClassificacaoFiscalOperacao / [TGFCFO](TGFCFO.md) | CodLegalCat42PorCFOP / [TGFCLCAT42](TGFCLCAT42.md) | CODCFO->CODCFO | I |
| ClassificacaoFiscalOperacao / [TGFCFO](TGFCFO.md) | PlanoConta /  | CODCTACTB->CODCTACTB | I |
| ClassificacaoFiscalOperacao / [TGFCFO](TGFCFO.md) | PlanoContaConta /  | CODCTACTB->CODCTACTB | I |
| ClassificacaoFiscalOperacao / [TGFCFO](TGFCFO.md) | TermoAcordoApuracao / [TGFTAA](TGFTAA.md) | CODCFO->CODCFO | I |
| ClassificacaoFiscalOperacao / [TGFCFO](TGFCFO.md) | TributacoesPorCFOP / [TGFTCFOP](TGFTCFOP.md) | CODCFO->CODCFO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | ClassificacaoFiscalOperacao / [TGFCFO](TGFCFO.md) | CODCFO->CODCFO | I |
| TimModeloNota / [TIMCNF](TIMCNF.md) | ClassificacaoFiscalOperacao / [TGFCFO](TGFCFO.md) | CNFCODCFO->CODCFO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ClaFiscalCombustivelLubrif / [TGFCFO](TGFCFO.md) | CODCFO_COMBUST_LUBRIF->CODCFO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ClaFiscalOperacaoEntrada / [TGFCFO](TGFCFO.md) | CODCFO_ENTRADA->CODCFO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ClaFiscalOperacaoEntradaEstado / [TGFCFO](TGFCFO.md) | CODCFO_ENTRADA_FORA->CODCFO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ClaFiscalOperacaoSaida / [TGFCFO](TGFCFO.md) | CODCFO_SAIDA->CODCFO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ClaFiscalOperacaoSaidaEstado / [TGFCFO](TGFCFO.md) | CODCFO_SAIDA_FORA->CODCFO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ClaFiscalOperacaoTerceiros / [TGFCFO](TGFCFO.md) | CODCFO_TERC->CODCFO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ClaFiscalPrestacaoServico / [TGFCFO](TGFCFO.md) | CODCFPS->CODCFO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
