# TGFFTT — Formatador porTipo de Titulo

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FormatadorPorTipoTitulo | Formatador Remessa por tipo de título | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODFORMREM | Formatador Remessa | I |  |  | N |
| CODTIPTIT | Tipo Titulo | I |  |  | N |
| MODULO | Modulo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FormatadorPorTipoTitulo / [TGFFTT](TGFFTT.md) | FormatadorRemessa / [TSIREM](TSIREM.md) | CODFORMREM->CODIGO; MODULO->MODULO | I |
| FormatadorPorTipoTitulo / [TGFFTT](TGFFTT.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |
| FormatadorRemessa / [TSIREM](TSIREM.md) | FormatadorPorTipoTitulo / [TGFFTT](TGFFTT.md) | CODIGO->CODFORMREM; MODULO->MODULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
