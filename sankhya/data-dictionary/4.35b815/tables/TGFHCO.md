# TGFHCO — Histórico de comissões

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoComissoes | Histórico de comissões | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODHISTCOM | Código | I |  |  | N |
| DESCRHIST | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ExtratoVendedor / [TGFEXT](TGFEXT.md) | HistoricoComissoes / [TGFHCO](TGFHCO.md) | CODHISTCOM->CODHISTCOM | I |
| FormulasComissao / [TGFFOC](TGFFOC.md) | HistoricoComissoes / [TGFHCO](TGFHCO.md) | HISTREGPROP->CODHISTCOM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
