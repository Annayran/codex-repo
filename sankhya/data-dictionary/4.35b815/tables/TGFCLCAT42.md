# TGFCLCAT42 — Código Legal CAT 42 por CFOP

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CodLegalCat42PorCFOP | Código Legal CAT 42 por CFOP | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CLASSIFICMS | Classificação ICMS | S |  |  | N |
| CODCFO | CFOP | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODLEGAL | Cód. Enquadramento Legal | I |  |  | N |
| NUFOP | Finalidade Operação | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ClassificacaoFiscalOperacao / [TGFCFO](TGFCFO.md) | CodLegalCat42PorCFOP / [TGFCLCAT42](TGFCLCAT42.md) | CODCFO->CODCFO | I |
| CodLegalCat42PorCFOP / [TGFCLCAT42](TGFCLCAT42.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CodLegalCat42PorCFOP / [TGFCLCAT42](TGFCLCAT42.md) | FinalidadeOperacao / [TGFFOP](TGFFOP.md) | NUFOP->NUFOP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
