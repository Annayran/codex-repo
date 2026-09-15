# TSISMS — Conta SMS

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ContaSMS | Conta SMS | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCONTASMS | Código | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| NOMEREMETENTE | Nome Remetente | S |  |  | N |
| PLATAFORMA | Plataforma | S |  |  | N |
| SENHA | Senha | S |  |  | N |
| USUARIO | Usuário | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MSDFilaMensagem / [TMDFMG](TMDFMG.md) | ContaSMS / [TSISMS](TSISMS.md) | CODCONTASMS->CODCONTASMS | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
