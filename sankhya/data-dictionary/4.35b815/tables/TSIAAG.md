# TSIAAG — Ação agendada

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AcaoAgendada | Ação Agendada | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACAO | Ação | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| AUTOTRAN | Transação automática | S |  |  | N |
| CODMODULO | Cód. módulo | I |  |  | N |
| CODUSULOGIN | Usuário Logado | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| EXPGATILHO | Expressão de gatilho | S |  |  | N |
| FONTEDADOS | Fonte de dados | S |  |  | N |
| NOME | Nome | S |  |  | N |
| NUAAG | Nro. único | I |  |  | N |
| TIPOACAO | Tipo de ação | S |  |  | N |
| TIPOGATILHO | Tipo de gatilho | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcaoAgendada / [TSIAAG](TSIAAG.md) | ModuloAdicional / [TSIMOD](TSIMOD.md) | CODMODULO->CODMODULO | I |
| AcaoAgendada / [TSIAAG](TSIAAG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSULOGIN->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
