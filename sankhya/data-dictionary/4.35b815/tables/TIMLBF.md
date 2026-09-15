# TIMLBF — Benfeitorias do Lote

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimBenfeitoriasLote | Benfeitorias do Lote | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| LBFCODIGO | Código | I |  |  | N |
| LBFCONTRATO | Contrato | I |  |  | N |
| LBFDESCRICAO | Descrição | S |  |  | N |
| LBFDTBENFT | Dt. Inclusão | D |  |  | N |
| LBFUSUARIO | Usuário Criador | I |  |  | N |
| LBFVALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimBenfeitoriasLote / [TIMLBF](TIMLBF.md) | Usuario / [TSIUSU](TSIUSU.md) | LBFUSUARIO->CODUSU | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimBenfeitoriasLote / [TIMLBF](TIMLBF.md) | LTVCODIGO->LBFCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
