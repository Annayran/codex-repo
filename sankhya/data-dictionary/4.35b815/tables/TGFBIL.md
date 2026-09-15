# TGFBIL — Bilhetagem

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Bilhetagem | Bilhetagem | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DTFATUR | Data do Faturamento | H |  |  | N |
| DTMOV | Data da Movimetação | H |  |  | N |
| NUMCONTRATO | Número do Contrato | I |  |  | N |
| NUNOTAFATUR | Número da Nota de Faturamento | I |  |  | N |
| QTDNEG | Quantidade Negociada | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Bilhetagem / [TGFBIL](TGFBIL.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTAFATUR->NUNOTA | I |
| Bilhetagem / [TGFBIL](TGFBIL.md) | ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
