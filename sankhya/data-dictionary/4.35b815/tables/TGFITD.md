# TGFITD — Descontos da Cotação

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DescontoCotacao | Descontos da Cotação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CABECALHO | Cabeçalho | S |  |  | N |
| CODLOCAL | Local do produto | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CONTROLE | Controle do produto | S |  |  | N |
| DIFERENCIADOR | Diferenciador | I |  |  | N |
| NUMCOTACAO | Número da Cotação | I |  |  | N |
| PERCENTUAL | Percentual do desconto | F |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPO | Tipo do desconto | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DescontoCotacao / [TGFITD](TGFITD.md) | DescontoCotacao / [TGFITD](TGFITD.md) | CABECALHO->CABECALHO; CODLOCAL->CODLOCAL; CODPARC->CODPARC; CODPROD->CODPROD; CONTROLE->CONTROLE; DIFERENCIADOR->DIFERENCIADOR; NUMCOTACAO->NUMCOTACAO | I |
| DescontoCotacao / [TGFITD](TGFITD.md) | ItemCotacao / [TGFITC](TGFITC.md) | CABECALHO->CABECALHO; CODLOCAL->CODLOCAL; CODPARC->CODPARC; CODPROD->CODPROD; CONTROLE->CONTROLE; DIFERENCIADOR->DIFERENCIADOR; NUMCOTACAO->NUMCOTACAO | I |
| DescontoCotacao / [TGFITD](TGFITD.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| DescontoCotacao / [TGFITD](TGFITD.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemCotacao / [TGFITC](TGFITC.md) | DescontoCotacao / [TGFITD](TGFITD.md) | CABECALHO->CABECALHO; CODLOCAL->CODLOCAL; CODPARC->CODPARC; CODPROD->CODPROD; CONTROLE->CONTROLE; DIFERENCIADOR->DIFERENCIADOR; NUMCOTACAO->NUMCOTACAO | I |
| ItemCotacaoParceiro / [TGFITC_COT](TGFITC_COT.md) | DescontoCotacao / [TGFITD](TGFITD.md) | CABECALHO->CABECALHO; CODLOCAL->CODLOCAL; CODPARC->CODPARC; CODPROD->CODPROD; CONTROLE->CONTROLE; DIFERENCIADOR->DIFERENCIADOR; NUMCOTACAO->NUMCOTACAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
