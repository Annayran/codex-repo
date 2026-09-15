# TCSPSS — Produto Serviço Série

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProdutoServicoSerie | Série | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Data da alteração | D |  |  | N |
| NUMCONTRATO | Número do contrato | I |  |  | N |
| PRIORIDADE | Prioridade | I |  |  | N |
| SERIE | Nro. Série | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | ProdutoServicoSerie / [TCSPSS](TCSPSS.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| ProdutoServicoSerie / [TCSPSS](TCSPSS.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoServicoSerie / [TCSPSS](TCSPSS.md) | ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| ProdutoServicoSerie / [TCSPSS](TCSPSS.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
