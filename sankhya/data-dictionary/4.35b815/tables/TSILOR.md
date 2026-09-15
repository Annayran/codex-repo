# TSILOR — Limites Orçamentário

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LimiteOrcamentarioCR | Limites Orçamentário | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANTECIPACAO | Permite Antecipação | S |  |  | N |
| CODCENCUS | Centro Resultado | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| SUPLEMENTACAO | Permite Suplementação | S |  |  | N |
| TIPOLIMITE | Tipo limite | S |  |  | N |
| TRANSFERENCIA | Permite Transferência | S |  |  | N |
| VALOR | Valor excedido | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CentroResultado / [TSICUS](TSICUS.md) | LimiteOrcamentarioCR / [TSILOR](TSILOR.md) | CODCENCUS->CODCENCUS | I |
| LimiteOrcamentarioCR / [TSILOR](TSILOR.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| LimiteOrcamentarioCR / [TSILOR](TSILOR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
