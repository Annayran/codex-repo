# TCSRUS — Relacionamento de usuário

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RelacionamentoUsuario | Relacionamento de Usuários | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| CODUSUREL | Cód. Usuário Relacionamento | I |  |  | N |
| LIDERIMEDIATO | Líder Imediato | S |  |  | N |
| TIPO | Tipo de Usuário | S |  |  | N |
| VINCULO | Liderança | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RelacionamentoUsuario / [TCSRUS](TCSRUS.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| RelacionamentoUsuario / [TCSRUS](TCSRUS.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUREL->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
