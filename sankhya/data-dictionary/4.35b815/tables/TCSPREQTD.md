# TCSPREQTD — Preços Descontos por faixa de quantidades

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PrecoDescontoFaixaQtd | Preços Descontos por faixa de quantidades | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODPROD | Cód.Produto | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Data da Alteração | H |  |  | N |
| NUMCONTRATO | Número do contrato | I |  |  | N |
| QUANTIDADE | Quantidade | I |  |  | N |
| REFERENCIA | Referência | H |  |  | N |
| SERIE | Série | S |  |  | N |
| TIPO | Tipo | S |  |  | N |
| VALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PrecoDescontoFaixaQtd / [TCSPREQTD](TCSPREQTD.md) | ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| PrecoDescontoFaixaQtd / [TCSPREQTD](TCSPREQTD.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| PrecoDescontoFaixaQtd / [TCSPREQTD](TCSPREQTD.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | PrecoDescontoFaixaQtd / [TCSPREQTD](TCSPREQTD.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
