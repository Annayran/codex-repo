# VSIUSU — View Usuário/Grupo/Equipe

- Módulo técnico: [VSI](../modules/VSI.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewUsuarioGrupo | Usuário/Grupo/Equipe | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário/Grupo/Equipe | I |  |  | N |
| NOMEUSU | Nome Usuário/Grupo/Equipe | S |  |  | N |
| TIPUSUGRU | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CompartilhamentoProcesso / [TWFCPN](TWFCPN.md) | ViewUsuarioGrupo / [VSIUSU](VSIUSU.md) | CODUSU->CODUSU; TIPUSUGRU->TIPUSUGRU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
