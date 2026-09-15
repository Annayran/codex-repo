# TGFCABVM — Cabecalho Nota Venda Mais

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CabecalhoNotaVendaMais | Cabecalho Nota Venda Mais | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODAUTORIZACAOVENDAMAIS | Código de Autorização Venda Mais | S |  |  | N |
| CODOPERACAOVENDAMAIS | Código de Operação Venda Mais | S |  |  | N |
| DHAPROVACAO | Dt. Aprovação | H |  |  | N |
| DTVALIDADE | Dt. Validade Crédito | D |  |  | N |
| ESTORNADO | ESTORNADO | S |  |  | N |
| NUNOTA | Número único Nota | I |  |  | N |
| TIPMOV | Tipo de Movimento | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNotaVendaMais / [TGFCABVM](TGFCABVM.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
