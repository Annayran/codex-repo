# TGFCLASTRIBNAC — Classificação Tributária Nacional

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ClassTributariaNacional | Classificação Tributária Nacional | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODIGOPAI | Código Pai | S |  |  | N |
| CODTRIBNAC | Código Classificação Tributária Nacional | S |  |  | N |
| DESCTRIBNAC | Descrição da Classificação Tributária Nacional | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Servico / [TGFPRO](TGFPRO.md) | ClassTributariaNacional / [TGFCLASTRIBNAC](TGFCLASTRIBNAC.md) | CODCLASTRIBNAC->CODTRIBNAC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
