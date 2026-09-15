# TSILIM — Limites

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LimiteLiberacao | Limites de Liberações | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGRU | Cód.Grupo | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| ENVIAREMAIL | Enviar E-Mail | S |  |  | N |
| ENVIARSMS | Enviar SMS | S |  |  | N |
| EVENTO | Evento | I |  |  | N |
| LIMITE | Limite | F |  |  | N |
| TIPOEVENTO | Tipo evento | S |  |  | N |
| TIPOLIMITE | Tipo limite | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LimiteLiberacao / [TSILIM](TSILIM.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| LimiteLiberacao / [TSILIM](TSILIM.md) | ViewEventoLiberacao / [VGFLIBEVE](VGFLIBEVE.md) | EVENTO->EVENTO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
