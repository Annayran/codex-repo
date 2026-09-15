# TIMFILCHV — TABLE TIMFILCHV

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimImovelFilaChaves | Fila de Reserva p/ Chaves | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| FILDHENT | Data de Entrada | H |  |  | N |
| FILDHEXPIRACAO | Dt. Expiração | H |  |  | N |
| FILFAC | FAC | I |  |  | N |
| FILIMOVEL | Imóvel | I |  |  | N |
| FILSEQ | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimImovelFilaChaves / [TIMFILCHV](TIMFILCHV.md) | TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | FILFAC->FACCODIGO | I |
| TimImovelFilaChaves / [TIMFILCHV](TIMFILCHV.md) | TimImovel / [TIMIMV](TIMIMV.md) | FILIMOVEL->IMVCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
