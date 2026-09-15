# TGMTVO — Transferência de Verba

- Módulo técnico: [TGM](../modules/TGM.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TransferenciaVerbaOrcamento | Transferência de Verba | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMETA | Meta/Orçamento | I |  |  | N |
| DHTRANSF | Dh. transferência | H |  |  | N |
| NUTVO | Nro. único | I |  |  | N |
| VLRTRANSF | Vlr. transferência | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TransferenciaVerbaOrcamento / [TGMTVO](TGMTVO.md) | Transferencia / [TGMTRA](TGMTRA.md) | NUTVO->NUTVO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
