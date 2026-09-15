# TGFPAS — Parceiro Serviço

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ParceiroServico | Parceiro Serviço | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODSERV | Serviço | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Parceiro / [TGFPAR](TGFPAR.md) | ParceiroServico / [TGFPAS](TGFPAS.md) | CODPARC->CODPARC | I |
| ParceiroServico / [TGFPAS](TGFPAS.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ParceiroServico / [TGFPAS](TGFPAS.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
