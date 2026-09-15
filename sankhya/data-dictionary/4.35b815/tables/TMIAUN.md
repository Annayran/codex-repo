# TMIAUN — Acesso Unidade Gerencial

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AcessoUnidadeGerencial | Acesso unidade gerencial | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGRUPO | Grupo | I |  |  | N |
| CODUNG | Un. Gerencial | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| TEMACESSO | Tem acesso | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcessoUnidadeGerencial / [TMIAUN](TMIAUN.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPO->CODGRUPO | I |
| AcessoUnidadeGerencial / [TMIAUN](TMIAUN.md) | UnidadeGerencial / [TMIUNG](TMIUNG.md) | CODUNG->CODUNG | I |
| AcessoUnidadeGerencial / [TMIAUN](TMIAUN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| UnidadeGerencial / [TMIUNG](TMIUNG.md) | AcessoUnidadeGerencial / [TMIAUN](TMIAUN.md) | CODUNG->CODUNG | I |
| Usuario / [TSIUSU](TSIUSU.md) | AcessoUnidadeGerencial / [TMIAUN](TMIAUN.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
