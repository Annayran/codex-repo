# TGABD — Bloqueio/Desbloqueio de Estoque

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| BloqDesbloqEstoque | Bloqueio/Desbloqueio de Estoque | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Usuário Ultima alteração | I |  |  | N |
| CODUSUBLOQ | Usuário Bloqueio | I |  |  | N |
| CODUSUDES | Usuário Desbloqueio | I |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |
| DTALTERBLOQ | Data Bloqueio | H |  |  | N |
| DTALTERDES | Data Desbloqueio | H |  |  | N |
| NUBD | Codigo | I |  |  | N |
| NUDIG | Motivo | I |  |  | N |
| NUMCONTRATO | Nro Contrato | I |  |  | N |
| OBS | Observações | S |  |  | N |
| QTD | Quantidade (KG) | F |  |  | N |
| SINAL | Ação | I |  |  | N |
| TIPO | TIPO | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| BloqDesbloqEstoque / [TGABD](TGABD.md) | ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| BloqDesbloqEstoque / [TGABD](TGABD.md) | MotivBloqDesbl / [TGADIG](TGADIG.md) | NUDIG->NUDIG | I |
| BloqDesbloqEstoque / [TGABD](TGABD.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUDES->CODUSU | I |
| BloqDesbloqEstoque / [TGABD](TGABD.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUBLOQ->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
