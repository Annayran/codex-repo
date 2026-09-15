# TCSTFU — Tipo de Recursos

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TipoRecurso | Recurso | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ABREVFUNCAO | Abreviação | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CODTIPFUNCAO | Código | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRFUNCAO | Descrição | S |  |  | N |
| NOMEFUNCAO | Função | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EquipeEtapa / [TCSFEE](TCSFEE.md) | TipoRecurso / [TCSTFU](TCSTFU.md) | CODTIPFUNCAO->CODTIPFUNCAO | I |
| EquipeProjeto / [TCSFEQ](TCSFEQ.md) | TipoRecurso / [TCSTFU](TCSTFU.md) | CODTIPFUNCAO->CODTIPFUNCAO | I |
| RecursoModEtapa / [TCSMFU](TCSMFU.md) | TipoRecurso / [TCSTFU](TCSTFU.md) | CODTIPFUNCAO->CODTIPFUNCAO | I |
| TipoRecurso / [TCSTFU](TCSTFU.md) | Executante / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
