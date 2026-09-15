# TMDAXM — Anexo por Mensagem

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
| AnexoPorMensagem | Anexo por Mensagem | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODFILA | CODFILA | I |  |  | N |
| NUANEXO | NUANEXO | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnexoMensagem / [TMDAMG](TMDAMG.md) | AnexoPorMensagem / [TMDAXM](TMDAXM.md) | NUANEXO->NUANEXO | I |
| AnexoPorMensagem / [TMDAXM](TMDAXM.md) | AnexoMensagem / [TMDAMG](TMDAMG.md) | NUANEXO->NUANEXO | I |
| MSDFilaMensagem / [TMDFMG](TMDFMG.md) | AnexoPorMensagem / [TMDAXM](TMDAXM.md) | CODFILA->CODFILA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
