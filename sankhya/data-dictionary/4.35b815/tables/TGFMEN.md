# TGFMEN — Mensagem entre Usuários

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MensagemEntreUsuario | Mensagem entre Usuários | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSUDEST | Cód. Usuário Destino | I |  |  | N |
| CODUSUORIG | Cód. Usuário Origem | I |  |  | N |
| DHDEST | Data e hora destino | H |  |  | N |
| DHORIG | Data e hora origem | H |  |  | N |
| MENSAGEM | Mensagem | S |  |  | N |
| PENDENTE | Pendente | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MensagemEntreUsuario / [TGFMEN](TGFMEN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUORIG->CODUSU | I |
| MensagemEntreUsuario / [TGFMEN](TGFMEN.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUDEST->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
