# TCSCPR — Comercial Produto

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 14
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ComercialProduto | Produtos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODFORN | Fornecedor | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| COMPLDESC | Complemento | S |  |  | S |
| DTPREVINST | Data Prevista | D |  |  | N |
| GRUPO | Grupo | I |  |  | N |
| IMPNOTA | Imprime na Nota | S |  |  | N |
| IMPOS | Imprime na Ordem de Serviço | S |  |  | N |
| NUMCONTRATO | Número do Contrato | I |  |  | N |
| PRINCIPAL | Principal | S |  |  | N |
| QTDNEG | Quantidade Cliente | F |  |  | N |
| QTDPED | Quantidade Requisitada | F |  |  | N |
| QTDPEDIR | Quantidade P/ Gerar Ped | F |  |  | N |
| QTDPEDVDA | Quantidade Entregue | F |  |  | N |
| VALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ComercialGrupo / [TCSCGR](TCSCGR.md) | ComercialProduto / [TCSCPR](TCSCPR.md) | GRUPO->GRUPO; NUMCONTRATO->NUMCONTRATO | I |
| ComercialProduto / [TCSCPR](TCSCPR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODFORN->CODPARC | I |
| ComercialProduto / [TCSCPR](TCSCPR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| GrupoInstalacao / [TCSCGR](TCSCGR.md) | ComercialProduto / [TCSCPR](TCSCPR.md) | GRUPO->GRUPO; NUMCONTRATO->NUMCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
