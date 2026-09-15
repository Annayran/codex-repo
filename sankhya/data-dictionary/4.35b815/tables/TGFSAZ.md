# TGFSAZ — Sazonalidade estoque mínimo

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| SazonalidadeProduto | Sazonalidade Produto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Cód. Produto | I |  |  | N |
| CODTIPSAZ | Tipo de Sazonalidade | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Dt. Alteração | H |  |  | N |
| DTFIM | Dt. Final | H |  |  | N |
| DTINICIO | Dt. Inicial | H |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| PERCENTUAL | Percentual | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Produto / [TGFPRO](TGFPRO.md) | SazonalidadeProduto / [TGFSAZ](TGFSAZ.md) | CODPROD->CODPROD | I |
| SazonalidadeProduto / [TGFSAZ](TGFSAZ.md) | TipoSazonalidade / [TGFTPS](TGFTPS.md) | CODTIPSAZ->CODTIPSAZ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
