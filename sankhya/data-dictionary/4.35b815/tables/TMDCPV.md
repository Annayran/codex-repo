# TMDCPV — Tabela de ligação entre Campos e Variáveis

- Módulo técnico: [TMD](../modules/TMD.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MSDCampoVariavel | MSDCampoVariavel | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCAM | Código do campo | I |  |  | N |
| CODVAR | Código da variável | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MSDCampoVariavel / [TMDCPV](TMDCPV.md) | MSDCampo / [TMDCAM](TMDCAM.md) | CODCAM->CODCAM | I |
| MSDCampoVariavel / [TMDCPV](TMDCPV.md) | MSDVariavel / [TMDVAR](TMDVAR.md) | CODVAR->CODVAR | I |
| MSDVariavel / [TMDVAR](TMDVAR.md) | MSDCampoVariavel / [TMDCPV](TMDCPV.md) | CODVAR->CODVAR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
