# TMIUXG — Usuários por Unidades Gerenciais

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UsuarioPorUnidadeGerencial | Usuário por Unidade Gerencial | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUNG | Cód. Und. Gerencial | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| UsuarioPorUnidadeGerencial / [TMIUXG](TMIUXG.md) | UnidadeGerencial / [TMIUNG](TMIUNG.md) | CODUNG->CODUNG | I |
| UsuarioPorUnidadeGerencial / [TMIUXG](TMIUXG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
