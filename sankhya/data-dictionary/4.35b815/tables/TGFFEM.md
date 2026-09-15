# TGFFEM — Formatador Remessa por Empresa

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
| FormatadorRemessaPorEmpresa | Formatador remessa por empresa | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODFORMREM | Formatador Remessa | I |  |  | N |
| MODULO | Módulo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FormatadorRemessa / [TSIREM](TSIREM.md) | FormatadorRemessaPorEmpresa / [TGFFEM](TGFFEM.md) | CODIGO->CODFORMREM; MODULO->MODULO | I |
| FormatadorRemessaPorEmpresa / [TGFFEM](TGFFEM.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| FormatadorRemessaPorEmpresa / [TGFFEM](TGFFEM.md) | FormatadorRemessa / [TSIREM](TSIREM.md) | CODFORMREM->CODIGO; MODULO->MODULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
