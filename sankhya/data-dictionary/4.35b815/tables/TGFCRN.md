# TGFCRN — Centro de Resultado e Natureza

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CentroResultadoNatureza | Naturezas | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| MULTCONSULTORES | Multiplica Consultores | S |  |  | N |
| MULTDIAS | Multiplica Dias | S |  |  | N |
| MULTHORAS | Multiplica Horas | S |  |  | N |
| MULTHORASSALA | Multiplica Horas Sala | S |  |  | N |
| MULTPARTICIPANTES | Multiplica Participantes | S |  |  | N |
| MULTPERIODOS | Multiplica Períodos | S |  |  | N |
| MULTTURMAS | Multiplica Turmas | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CentroResultado / [TSICUS](TSICUS.md) | CentroResultadoNatureza / [TGFCRN](TGFCRN.md) | CODCENCUS->CODCENCUS | I |
| CentroResultadoNatureza / [TGFCRN](TGFCRN.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
