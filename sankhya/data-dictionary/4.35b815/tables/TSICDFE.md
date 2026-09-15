# TSICDFE — Consulta de documentos por empresa

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConfiguracaoDFe | ConfiguracaoDFe | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| MDEPROXCONSCTE | Próxima consulta CT-e | H |  |  | N |
| MDEPROXCONSULTA | Próxima consulta NF-e | H |  |  | N |
| MDEPROXDOWNLOAD | Próxima verificação de Download DF-e | H |  |  | N |
| ULTNSU | Último NSU | S |  |  | N |
| ULTNSUCTE | Último NSU CT-e | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoDFe / [TSICDFE](TSICDFE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
