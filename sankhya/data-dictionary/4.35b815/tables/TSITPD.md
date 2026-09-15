# TSITPD — Tipo de documento

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
| TipoDocumento | Tipo de documento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCRIPTION | Tipo de documento | S |  |  | N |
| DOCTYPE | Código | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TrabalhoImpressao / [TSIJPR](TSIJPR.md) | TipoDocumento / [TSITPD](TSITPD.md) | TIPODOC->DOCTYPE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
