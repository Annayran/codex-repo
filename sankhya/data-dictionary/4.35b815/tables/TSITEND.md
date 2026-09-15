# TSITEND — Tipo de Endereço

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TipoEndereco | Tipo de Endereço | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCRICAO | Descrição | S |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Endereco / [TSIEND](TSIEND.md) | TipoEndereco / [TSITEND](TSITEND.md) | TIPO->TIPO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
