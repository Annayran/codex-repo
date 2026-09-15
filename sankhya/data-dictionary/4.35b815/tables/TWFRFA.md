# TWFRFA — Relatorio formatado por atividade

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RelatorioFormatadoAtividade | Relatório por atividade | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCRICAO | Nome do Relatório | S |  |  | N |
| NUELE | Código do Elemento | I |  |  | N |
| NURFE | Código de Relatório | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RelatorioFormatadoAtividade / [TWFRFA](TWFRFA.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFE->NURFE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
