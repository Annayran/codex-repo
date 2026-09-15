# TCSPRE — Preços do Contrato

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 8
- Instâncias DWF: 2
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PrecoContrato | Preço | S | N |
| UltimoPrecoContrato | Último Preço | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| CODSERV | Serviço | I |  |  | N |
| CODTERRESPAR | Cód. Terceiro Responsável | I |  |  | N |
| CODUSUALTREG | Cód. Usuário Alteração | I |  |  | N |
| DHALTREG | Dt. Alteração | H |  |  | N |
| NUMCONTRATO | Número do contrato | I |  |  | N |
| REFERENCIA | Referência | D |  |  | N |
| VALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PrecoContrato / [TCSPRE](TCSPRE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| PrecoContrato / [TCSPRE](TCSPRE.md) | ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| PrecoContrato / [TCSPRE](TCSPRE.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | PrecoContrato / [TCSPRE](TCSPRE.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | UltimoPrecoContrato / [TCSPRE](TCSPRE.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| ProdutoServicoContrato2 / [TCSPSC](TCSPSC.md) | PrecoContrato / [TCSPRE](TCSPRE.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
