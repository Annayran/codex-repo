# TGFCUSITE — Custo Item

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 18
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CustoItem | Custo por Item | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALTPRECO | Alt. Preço Igual | S |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| COMPLCUST | Complemento de Custo | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| CUSGER | CUSGER | F |  |  | N |
| CUSREP | CUSREP | F |  |  | N |
| CUSVARIAVEL | Custo variável | F |  |  | N |
| DTATUAL | DTATUAL | H |  |  | N |
| ENTRADACOMICMS | ENTRADACOMICMS | F |  |  | N |
| ENTRADASEMICMS | ENTRADASEMICMS | F |  |  | N |
| FAMILIA | FAMILIA | S |  |  | N |
| NUNOTA | Nro. Nota | I |  |  | N |
| QTDNEG | Qtd. Negociada | F |  |  | N |
| SEQUENCIA | Sequencia | I |  |  | N |
| SINAL | SINAL | I |  |  | N |
| TIPONOTA | TIPONOTA | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CustoItem / [TGFCUSITE](TGFCUSITE.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| CustoItem / [TGFCUSITE](TGFCUSITE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemNota / [TGFITE](TGFITE.md) | CustoItem / [TGFCUSITE](TGFCUSITE.md) | CODPROD->CODPROD; NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
