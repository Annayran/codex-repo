# TGPESC — TABLE TGPESC

- Módulo técnico: [TGP](../modules/TGP.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Escala | Escala | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODESCALA | CODESCALA | I |  |  | N |
| CODUSU | CODUSU | I |  |  | N |
| CONCEITO | CONCEITO | S |  |  | N |
| DESCRESCALA | DESCRESCALA | S |  |  | N |
| DHALTER | DHALTER | H |  |  | N |
| GRAUALTOIMPACTO | GRAUALTOIMPACTO | I |  |  | N |
| TIPOESCALA | TIPOESCALA | I |  |  | N |
| TIPOPONTUACAO | TIPOPONTUACAO | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DescricaoTarefas / [TGPTAR](TGPTAR.md) | Escala / [TGPESC](TGPESC.md) | CODESCALA->CODESCALA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
