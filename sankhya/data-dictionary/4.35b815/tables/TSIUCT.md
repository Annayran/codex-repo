# TSIUCT — Contas por Usuário

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ContaUsuario | Contas por Usuário | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCTABCOINT | Cód.Conta | I |  |  | N |
| CODEMP | Cód.Empresa | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContaUsuario / [TSIUCT](TSIUCT.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| ContaUsuario / [TSIUCT](TSIUCT.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ContaUsuario / [TSIUCT](TSIUCT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Usuario / [TSIUSU](TSIUSU.md) | ContaUsuario / [TSIUCT](TSIUCT.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
