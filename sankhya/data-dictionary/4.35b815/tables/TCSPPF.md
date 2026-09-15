# TCSPPF — Preço por Faixa

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PrecoPorFaixa | Preço por Faixa | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Dt. Alteração | D |  |  | N |
| NUMCONTRATO | Número do Contrato | I |  |  | N |
| PRECOUNIT | Preço Unitário | F |  |  | N |
| QTDFINAL | Qtd. Final | I |  |  | N |
| QTDINICIAL | Qtd. Inicial | I |  |  | N |
| VLRFATMIN | Faturamento Mínimo | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PrecoPorFaixa / [TCSPPF](TCSPPF.md) | ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| PrecoPorFaixa / [TCSPPF](TCSPPF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | PrecoPorFaixa / [TCSPPF](TCSPPF.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
