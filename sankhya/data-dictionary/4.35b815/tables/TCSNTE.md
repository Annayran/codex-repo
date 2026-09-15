# TCSNTE — Números dos Terminais Telefônicos

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NumeroTerminaisTelefonicos | Números dos Terminais Telefônicos | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODAREADDD | Código da Localidade | I |  |  | N |
| CODUFIBGE | UF | S |  |  | N |
| NUMCONTRATO | Número do Contrato | I |  |  | N |
| NUMTERMTEL | Número de Identificação do Terminal Telefônico | I |  |  | N |
| PRINCIPAL | Principal | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Contrato / [TCSCON](TCSCON.md) | NumeroTerminaisTelefonicos / [TCSNTE](TCSNTE.md) | NUMCONTRATO->NUMCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
