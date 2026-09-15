# TSIRLG — log acessos

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
| LogAcessos | Log de acessos | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AGENTE | Navegador | S |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| EVENTO | Evento | S |  |  | N |
| IP | Endereço IP | S |  |  | N |
| LOGIN | Dh. Entrada | H |  |  | N |
| LOGOUT | Dh. Saída | H |  |  | N |
| SEQACESSO | Sessão | I |  |  | N |
| SESSIONID | SESSIONID | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LogAcessos / [TSIRLG](TSIRLG.md) | AcessoMenu / [TSIACM](TSIACM.md) | CODUSU->CODUSU; SEQACESSO->SEQACESSO | I |
| LogAcessos / [TSIRLG](TSIRLG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
