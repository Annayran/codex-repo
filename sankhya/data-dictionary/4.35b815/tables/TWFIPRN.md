# TWFIPRN — Instância de Processos

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 16
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| InstanciaProcesso | Instâncias de Processo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPRN | Cód. Processo | I |  |  | N |
| CODUSUCANCEL | Usuário responsável pelo cancelamento | I |  |  | N |
| CODUSUINC | Usuário inclusão | I |  |  | N |
| DHCONCLUSAO | Dh. conclusão | H |  |  | N |
| DHINCLUSAO | Dh. inclusão | H |  |  | N |
| DHVENCIMENTO | Dh. final do SLA do processo | H |  |  | N |
| IDINSTPRN | Nro Solicitação | I |  |  | N |
| IDPRNMESTRE | Inst. Processo mestre | I |  |  | N |
| OBSCANCEL | Observação de cancelamento | S |  |  | N |
| PERCDECORRIDO | Percentual decorrido | F |  |  | N |
| SITUACAO | Situação | S |  |  | N |
| SITUACAOEXEC | Situação da execução | S |  |  | N |
| SLAATUAL | Saldo atual | I |  |  | N |
| TEMPODECORRIDO | Tempo decorrido | F |  |  | N |
| TEMPOLIMITE | Tempo limite | F |  |  | N |
| VERSAO | Versão | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| HistoricoInstanciaProcesso / [TWFIHIS](TWFIHIS.md) | InstanciaProcesso / [TWFIPRN](TWFIPRN.md) | IDINSTPRN->IDINSTPRN | I |
| InstanciaProcesso / [TWFIPRN](TWFIPRN.md) | ProcessoNegocio / [TWFPRN](TWFPRN.md) | CODPRN->CODPRN; VERSAO->VERSAO | I |
| InstanciaProcesso / [TWFIPRN](TWFIPRN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUINC->CODUSU | I |
| InstanciaProcesso / [TWFIPRN](TWFIPRN.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUCANCEL->CODUSU | I |
| InstanciaTarefa / [TWFITAR](TWFITAR.md) | InstanciaProcesso / [TWFIPRN](TWFIPRN.md) | IDINSTPRN->IDINSTPRN | I |
| ViewCampoEmbarcadoTarefa / [VWFEBD](VWFEBD.md) | InstanciaProcesso / [TWFIPRN](TWFIPRN.md) | IDINSTPRN->IDINSTPRN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
