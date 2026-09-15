# TWFUST — Usuário Substituto de Tarefas

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UsuarioSubstitutoTarefa | Usuário Substituto em Tarefas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODANTERIOR | Usuário anterior | I |  |  | N |
| CODPRN | Cód. Processo | I |  |  | N |
| CODSUBSTITUTO | Usuário Substituto | I |  |  | N |
| CODUSU | Alterado por | I |  |  | N |
| DHALTER | Data alteração | H |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ProcessoNegocio / [TWFPRN](TWFPRN.md) | UsuarioSubstitutoTarefa / [TWFUST](TWFUST.md) | CODPRN->CODPRN | I |
| UsuarioSubstitutoTarefa / [TWFUST](TWFUST.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| UsuarioSubstitutoTarefa / [TWFUST](TWFUST.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODSUBSTITUTO->CODUSU | I |
| UsuarioSubstitutoTarefa / [TWFUST](TWFUST.md) | Usuario3 / [TSIUSU](TSIUSU.md) | CODANTERIOR->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
