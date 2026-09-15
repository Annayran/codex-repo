# TGFFCI — Separação para faturamento

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| SeparacaoFaturamento | Separação para faturamento | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODTIPOPER | Tipo Operação | I |  |  | N |
| CODTIPOPERDEST | Tipo Operação separação | I |  |  | N |
| CONFIRMDOCFAT | Confirmar nota ao faturar? | S |  |  | N |
| CRITERIOSEP | Critério p/ separação de itens | C |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| SERIEDEST | Série separação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| SeparacaoFaturamento / [TGFFCI](TGFFCI.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| SeparacaoFaturamento / [TGFFCI](TGFFCI.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| SeparacaoFaturamento / [TGFFCI](TGFFCI.md) | TopSeparacao / [TGFTOP](TGFTOP.md) | CODTIPOPERDEST->CODTIPOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | SeparacaoFaturamento / [TGFFCI](TGFFCI.md) | CODTIPOPER->CODTIPOPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
