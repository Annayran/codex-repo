# TGFUSE — Uso da separação

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UsoSeparacao | Uso da separação | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AGRUPFINNOTAS | Agrupar financeiros | S |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODTIPOPER | Tipo Operação | I |  |  | N |
| CODTIPOPERPRINC | Tipo Operação Principal | I |  |  | N |
| CODTIPOPERSEP | Tipo Operação Separação | I |  |  | N |
| CONFIRMNOTASFAT | Confirmar documento ao faturar | S |  |  | N |
| SERIEPRINC | Série Principal | S |  |  | N |
| SERIESEPARACAO | Série separação | S |  |  | N |
| USOSEPARACAO | Uso da separação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TipoOperacao / [TGFTOP](TGFTOP.md) | UsoSeparacao / [TGFUSE](TGFUSE.md) | CODTIPOPER->CODTIPOPER | I |
| UsoSeparacao / [TGFUSE](TGFUSE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| UsoSeparacao / [TGFUSE](TGFUSE.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPERSEP->CODTIPOPER | I |
| UsoSeparacao / [TGFUSE](TGFUSE.md) | TopPrincipal / [TGFTOP](TGFTOP.md) | CODTIPOPERPRINC->CODTIPOPER | I |
| UsoSeparacao / [TGFUSE](TGFUSE.md) | TopSeparacao / [TGFTOP](TGFTOP.md) | CODTIPOPERSEP->CODTIPOPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
