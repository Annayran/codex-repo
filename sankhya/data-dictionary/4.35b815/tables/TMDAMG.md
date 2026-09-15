# TMDAMG — Anexo Mensagens

- Módulo técnico: [TMD](../modules/TMD.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AnexoMensagem | Anexo Mensagens | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANEXO | ANEXO | B |  |  | N |
| CID | Content-ID | S |  |  | N |
| NOMEARQUIVO | NOMEARQUIVO | S |  |  | N |
| NUANEXO | NUANEXO | I |  |  | N |
| TIPO | TIPO | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnexoMensagem / [TMDAMG](TMDAMG.md) | AnexoPorMensagem / [TMDAXM](TMDAXM.md) | NUANEXO->NUANEXO | I |
| AnexoPorMensagem / [TMDAXM](TMDAXM.md) | AnexoMensagem / [TMDAMG](TMDAMG.md) | NUANEXO->NUANEXO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
