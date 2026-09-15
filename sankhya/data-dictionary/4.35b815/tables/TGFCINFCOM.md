# TGFCINFCOM — Código de Itens da NFCom

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CodigoItemNFCom | Código de Itens da NFCom | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODIGO | Codigo | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Produto / [TGFPRO](TGFPRO.md) | CodigoItemNFCom / [TGFCINFCOM](TGFCINFCOM.md) | CODIGONFCOM->CODIGO | I |
| Servico / [TGFPRO](TGFPRO.md) | CodigoItemNFCom / [TGFCINFCOM](TGFCINFCOM.md) | CODIGONFCOM->CODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
