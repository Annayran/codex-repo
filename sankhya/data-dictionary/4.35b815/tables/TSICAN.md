# TSICAN — Cancelamento de NFSe

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CancelamentoNFSe | Cancelamento NFS e | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODCAN | Código | S |  |  | N |
| CODCID | Cód. Cidade | I |  |  | N |
| ENVIARPREFEITURA | Enviar a prefeitura | S |  |  | N |
| MOTIVO | Motivo/Descrição | S |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Cidade / [TSICID](TSICID.md) | CancelamentoNFSe / [TSICAN](TSICAN.md) | CODCID->CODCID | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
