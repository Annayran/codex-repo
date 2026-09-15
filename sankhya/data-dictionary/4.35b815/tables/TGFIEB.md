# TGFIEB — Importação Extrato Bancario

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ImportacaoExtratoBancario | Importação de Extrato Bancaário | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário Alteração | I |  |  | N |
| DHALTER | Data alteração | H |  |  | N |
| DHPROCESSO | Data/Hora Processamento | H |  |  | N |
| DTIMPORT | Data Importação | H |  |  | N |
| HASH_ARQUIVO | Hash Arquivo | S |  |  | N |
| NOMEARQ | Nome Arquivo | S |  |  | N |
| NUIMPORT | Núm. Importação | I |  |  | N |
| PROCESSADO | Processado | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ImportacaoExtratoBancario / [TGFIEB](TGFIEB.md) | ExtratoBancario / [TGFEXB](TGFEXB.md) | NUIMPORT->NUIMPORT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
