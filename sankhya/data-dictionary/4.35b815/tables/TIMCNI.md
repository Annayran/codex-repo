# TIMCNI — Tim Modelo Nota Criterio

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimModeloNotaCriterio | Critério de Conf. Modelo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CNILANCAMENTO | Lançamento | S |  |  | N |
| CNINUNOTA | Modelo de Nota | I |  |  | N |
| CNIPRODUTO | Produto | S |  |  | N |
| CNISEQUENCIA | Código | I |  |  | N |
| CNITIPO | Utilizado Para | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimModeloNota / [TIMCNF](TIMCNF.md) | TimModeloNotaCriterio / [TIMCNI](TIMCNI.md) | CNFNUNOTA->CNINUNOTA | I |
| TimModeloNotaCriterio / [TIMCNI](TIMCNI.md) | TimModeloNota / [TIMCNF](TIMCNF.md) | CNINUNOTA->CNFNUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
