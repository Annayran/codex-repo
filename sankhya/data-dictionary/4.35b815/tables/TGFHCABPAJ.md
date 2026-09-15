# TGFHCABPAJ — Histórico Cabeçalho Processos Administrativos Judiciais

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistCabProcessoAdmJudicial | Histórico Cabeçalho Processos Administrativos Judiciais | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUMPROCESSO | Nro. do Processo | S |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| NUPROCESSO | Nro Único do processso | I |  |  | N |
| ORIGEMPROCESSO | Origem do Processo | I |  |  | N |
| TIPATOCONCES | Tipo do Ato Concessório | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| HistCabProcessoAdmJudicial / [TGFHCABPAJ](TGFHCABPAJ.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| HistCabProcessoAdmJudicial / [TGFHCABPAJ](TGFHCABPAJ.md) | ProcessoAdmJudicial /  | NUPROCESSO->NUPROCESSO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
