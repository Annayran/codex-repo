# TIMPTP — Telefones Prospect

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimTelefoneProspect | Telefones Prospect | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| PTPCODIGO | Sequencia | I |  |  | N |
| PTPCOMENTARIO | Comentário | S |  |  | N |
| PTPPROSPECT | Prospect | I |  |  | N |
| PTPTELEFONE | Telefone | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | TimTelefoneProspect / [TIMPTP](TIMPTP.md) | FACPROSPECT->PTPPROSPECT | I |
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | TimTelefoneProspect / [TIMPTP](TIMPTP.md) | FACPROSPECT->PTPPROSPECT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
