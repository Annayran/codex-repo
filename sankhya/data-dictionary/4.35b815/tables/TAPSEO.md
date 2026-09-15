# TAPSEO — Serviço por Objeto

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ServicoObjeto | Serviço por objeto | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROJ | Projeto | I |  |  | N |
| CODSERV | Servico | I |  |  | N |
| CODVOL | Unidade | S |  |  | N |
| IDOBJETO | Objeto | S |  |  | N |
| NOMETAB | Tabela | S |  |  | N |
| QTDPLA | Quantidade Planejada | F |  |  | N |
| QTDREAL | Quantidade real | F |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ServicoObjeto / [TAPSEO](TAPSEO.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ServicoObjeto / [TAPSEO](TAPSEO.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
