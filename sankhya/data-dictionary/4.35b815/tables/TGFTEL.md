# TGFTEL — Relacionamento Telemarketing

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 20
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Relacionamento | Relacionamento Telemarketing | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODATENDENTE | Atendente | I |  |  | N |
| CODCONTATO | Contato | I |  |  | N |
| CODHIST | Histórico | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODUSU | Usuário Executante | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| COMENTARIOS | Comentários | S |  |  | N |
| COMENTARIOS2 | Comentários 2 | S |  |  | N |
| DHCHAMADA | Data/Hora Chamada | H |  |  | N |
| DHPROXCHAM | Data/Hora Agendamento | H |  |  | N |
| DTALTER | Data/Hora Alteração | H |  |  | N |
| NUAVISO | Aviso | I |  |  | N |
| NUMOS | Nro. OS | I |  |  | N |
| NUREL | Nro. Único | I |  |  | N |
| PENDENTE | Situação | S |  |  | N |
| SITUACAO | Situação(não usado) | S |  |  | N |
| TELEFONEPARC | Telefone | S |  |  | S |
| TEMPPREVISTO | Tempo Previsto | H |  |  | N |
| TIPCHAM | Tipo Chamada | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Relacionamento / [TGFTEL](TGFTEL.md) | Atendente / [TSIUSU](TSIUSU.md) | CODATENDENTE->CODUSU | I |
| Relacionamento / [TGFTEL](TGFTEL.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| Relacionamento / [TGFTEL](TGFTEL.md) | Executante / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Relacionamento / [TGFTEL](TGFTEL.md) | HistoricoTele / [TGFHTE](TGFHTE.md) | CODHIST->CODHIST | I |
| Relacionamento / [TGFTEL](TGFTEL.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
