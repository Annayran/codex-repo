# TIMPGT — Armazena informações de financeiros gerados pela imobiliário e loteamento.

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimFormaPagamento | Histórico Parcelas Comissão | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DTCAD | DTCAD | H |  |  | N |
| DTVENC | DTVENC | H |  |  | N |
| NUNOTA | NUNOTA | I |  |  | N |
| PGTCODIGO | PGTCODIGO | I |  |  | N |
| VLRDESDOB | VLRDESDOB | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimFormaPagamento / [TIMPGT](TIMPGT.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
