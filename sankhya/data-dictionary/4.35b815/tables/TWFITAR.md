# TWFITAR — Instância de Tarefas

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 17
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| InstanciaTarefa | Instância de Tarefa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSUALTER | Cod. usuário alteração dono | I |  |  | N |
| CODUSUDONO | Usuário dono | I |  |  | N |
| CODUSUSOLICITANTE | Usuário solicitante | I |  |  | N |
| DHACEITE | Dh. aceite | H |  |  | N |
| DHALTER | Dh. alteração dono | H |  |  | N |
| DHCONCLUSAO | Dh. conclusão | H |  |  | N |
| DHCRIACAO | Dh. criação | H |  |  | N |
| DHVENCIMENTO | Dh. Vencimento | H |  |  | N |
| IDELEMENTO | Id Elemento | S |  |  | N |
| IDINSTPRN | Id Instância do Processo | I |  |  | N |
| IDINSTTAR | Id Instância da Tarefa | I |  |  | N |
| NOMEELEMENTO | Nome | S |  |  | S |
| PERCDECORRIDO | Percentual Decorrido | F |  |  | N |
| SITUACAO | Situação | S |  |  | N |
| SITUACAOEXEC | Situação da execução | S |  |  | N |
| TEMPODECORRIDO | Tempo Decorrido | F |  |  | N |
| TEMPOLIMITE | Tempo Limite | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ApontamentoExecucaoTarefa / [TWFIEXE](TWFIEXE.md) | InstanciaTarefa / [TWFITAR](TWFITAR.md) | IDINSTPRN->IDINSTPRN; IDINSTTAR->IDINSTTAR | I |
| InstanciaTarefa / [TWFITAR](TWFITAR.md) | InstanciaProcesso / [TWFIPRN](TWFIPRN.md) | IDINSTPRN->IDINSTPRN | I |
| InstanciaTarefa / [TWFITAR](TWFITAR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUDONO->CODUSU | I |
| InstanciaTarefa / [TWFITAR](TWFITAR.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUSOLICITANTE->CODUSU | I |
| InstanciaTarefa / [TWFITAR](TWFITAR.md) | Usuario3 / [TSIUSU](TSIUSU.md) | CODUSUALTER->CODUSU | I |
| InstanciaVariavel / [TWFIVAR](TWFIVAR.md) | InstanciaTarefa / [TWFITAR](TWFITAR.md) | IDINSTPRN->IDINSTPRN; IDINSTTAR->IDINSTTAR | I |
| ViewCampoEmbarcadoTarefa / [VWFEBD](VWFEBD.md) | InstanciaTarefa / [TWFITAR](TWFITAR.md) | IDINSTTAR->IDINSTTAR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
