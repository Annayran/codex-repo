# TGFECQ — TABLE TGFECQ

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EventosCheque | EventosCheque | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DETALHE | Detalhes | C |  |  | N |
| DHEVENTO | Data do Evento | H |  |  | N |
| NUCHQ | Nro. Único do Cheque | I |  |  | N |
| NUEVENTO | Nro. Evento | I |  |  | N |
| TIPO | Tipo de Evento | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EventosCheque / [TGFECQ](TGFECQ.md) | Cheque / [TGFCHQ](TGFCHQ.md) | NUCHQ->NUCHQ | I |
| EventosCheque / [TGFECQ](TGFECQ.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
