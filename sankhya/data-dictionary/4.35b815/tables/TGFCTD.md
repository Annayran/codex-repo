# TGFCTD — Custo de devolucao

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CustoDevolucao | Custo de Devolução | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | CODUSU | I |  |  | N |
| DHALTER | DHALTER | H |  |  | N |
| DHCUSTO | DHCUSTO | H |  |  | N |
| NUNOTA | NUNOTA | I |  |  | N |
| NUNOTACOTACAO | NUNOTACOTACAO | I |  |  | N |
| NUNOTAORIG | NUNOTAORIG | I |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| SEQUENCIAORIG | SEQUENCIAORIG | I |  |  | N |
| VLRCUS | VLRCUS | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemNota / [TGFITE](TGFITE.md) | CustoDevolucao / [TGFCTD](TGFCTD.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
