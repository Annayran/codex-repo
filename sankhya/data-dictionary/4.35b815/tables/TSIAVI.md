# TSIAVI — Avisos do sistema

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AvisoSistema | Aviso do sistema | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGRUPO | Grupo | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| CODUSUREMETENTE | Remetente | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHCRIACAO | Dh. Criação | H |  |  | N |
| DTEXPIRACAO | Dt. Expiração | D |  |  | N |
| DTNOTIFICACAO | Dt. Notificação | D |  |  | N |
| IDENTIFICADOR | Identificador | S |  |  | N |
| IMPORTANCIA | Importância | I |  |  | N |
| NUAVISO | Aviso | I |  |  | N |
| NUAVISOPAI | Aviso Pai | I |  |  | N |
| ORDEM | Ordem | I |  |  | N |
| SOLUCAO | Solução | S |  |  | N |
| TIPO | Tipo | S |  |  | N |
| TITULO | Título | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AvisoSistema / [TSIAVI](TSIAVI.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPO->CODGRUPO | I |
| AvisoSistema / [TSIAVI](TSIAVI.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
