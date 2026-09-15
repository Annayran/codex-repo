# TGACLT — Tipos de Classificação de Produto

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 9
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PadraoClassificacao | Padrão de Classificação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CLASSIFICACAO | Classificação | I |  |  | N |
| CODCLT | Código | I |  |  | N |
| CODGPC | Grupo P. Class | I |  |  | N |
| CODGRUPOPROD | Grupo de produtos | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CONFNEGADO | Permite confirmar quando laudo for rejeitado | S |  |  | N |
| DESCRCLT | Descrição | S |  |  | N |
| EXIGELIB | Exige Liberação | S |  |  | N |
| FORMULA | Fórmula | S |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| PRAZOVAL | Prazo de validade | I |  |  | N |
| SIGLA | Sigla | S |  |  | N |
| TIPOLAUDO | Tipo de Laudo | S |  |  | N |
| TIPOREFPRZVAL | Calcular o Prazo de validade a partir da | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CaracteristicaClassificacao / [TGACLI](TGACLI.md) | PadraoClassificacao / [TGACLT](TGACLT.md) | CODCLT->CODCLT | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | PadraoClassificacao / [TGACLT](TGACLT.md) | PADCLASS->CODCLT | I |
| ItemLaudo / [TGACLR](TGACLR.md) | PadraoClassificacao / [TGACLT](TGACLT.md) | CODCLT->CODCLT | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | PadraoClassificacao / [TGACLT](TGACLT.md) | CODCLT->CODCLT | I |
| PadraoClassificacao / [TGACLT](TGACLT.md) | CaracteristicaClassificacao / [TGACLI](TGACLI.md) | CODCLT->CODCLT | I |
| PadraoClassificacao / [TGACLT](TGACLT.md) | GrupoPadraoClass / [TGAGPC](TGAGPC.md) | CODGPC->CODGPC | I |
| PadraoClassificacao / [TGACLT](TGACLT.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| PadraoClassificacao / [TGACLT](TGACLT.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Projeto / [TCSPRJ](TCSPRJ.md) | PadraoClassificacao / [TGACLT](TGACLT.md) | CODCLT->CODCLT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
