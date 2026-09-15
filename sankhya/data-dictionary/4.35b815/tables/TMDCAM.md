# TMDCAM — Tabela de campos para variáveis do tipo tabela

- Módulo técnico: [TMD](../modules/TMD.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MSDCampo | Campos para variáveis do tipo tabela | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCAM | Código do campo | I |  |  | N |
| DESCCAM | Descrição do campo | S |  |  | N |
| NOMECAM | Nome do campo | S |  |  | N |
| TAMANHO | Tamanho do campo | I |  |  | N |
| TIPOCAM | Tipo do campo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MSDCampoVariavel / [TMDCPV](TMDCPV.md) | MSDCampo / [TMDCAM](TMDCAM.md) | CODCAM->CODCAM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
