# TRSEPE — Etapa Personalizada

- Módulo técnico: [TRS](../modules/TRS.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EtapaPersonalizada | EtapaPersonalizada | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ARQMODEMAIL | Arquivo Modelo Email | S |  |  | N |
| CODEPE | Código Etapa Personalizada | I |  |  | N |
| CODETAPA | Código Etapa | I |  |  | N |
| CODUSU | Código Usúario | I |  |  | S |
| DTABERTURA | Data Abertura | D |  |  | N |
| DTALTER | Data Alteração | D |  |  | N |
| NUREQUISICAO | Numero Requisição | I |  |  | N |
| NUSELECAO | NUSELECAO | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EtapaPersonalizada / [TRSEPE](TRSEPE.md) | EtapasProcessoSelecao / [TRSETA](TRSETA.md) | CODETAPA->CODETAPA | I |
| EtapaPersonalizada / [TRSEPE](TRSEPE.md) | RequisicaoPessoal / [TRSREQ](TRSREQ.md) | NUREQUISICAO->NUREQUISICAO | I |
| EtapaPersonalizada / [TRSEPE](TRSEPE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ProcessoSelecao / [TRSSEL](TRSSEL.md) | EtapaPersonalizada / [TRSEPE](TRSEPE.md) | NUSELECAO->NUSELECAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
