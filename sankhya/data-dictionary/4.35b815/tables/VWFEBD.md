# VWFEBD — Campos Embarcado Tarefa

- Módulo técnico: [VWF](../modules/VWF.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewCampoEmbarcadoTarefa | Campos Embarcado Tarefa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DEFAULTLONG | Valor padrão longo | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DTA | Data e Hora | H |  |  | N |
| IDINSTPRN | Nro Solicitação | I |  |  | N |
| IDINSTTAR | Id Instância da Tarefa | I |  |  | N |
| NOME | Nome | S |  |  | N |
| NUMDEC | Número decimal | F |  |  | N |
| NUMINT | Número inteiro | I |  |  | N |
| TEXTO | Texto | S |  |  | N |
| TEXTOLONGO | Texto formatado | S |  |  | N |
| TIPO | Tipo | S |  |  | N |
| VALORPADRAO | Valor padrão | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ViewCampoEmbarcadoTarefa / [VWFEBD](VWFEBD.md) | InstanciaProcesso / [TWFIPRN](TWFIPRN.md) | IDINSTPRN->IDINSTPRN | I |
| ViewCampoEmbarcadoTarefa / [VWFEBD](VWFEBD.md) | InstanciaTarefa / [TWFITAR](TWFITAR.md) | IDINSTTAR->IDINSTTAR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
