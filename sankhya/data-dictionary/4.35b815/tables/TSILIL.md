# TSILIL — Log importação legacy

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 14
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LogImportacaoLegacy | Log Importação Legacy | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATUALIZAR | Atualizar registros existentes | S |  |  | N |
| CODLIL | Id importação | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DHEVENTO | Data importação | H |  |  | N |
| EVENTO | Evento | S |  |  | N |
| LOGFINAL | Processo de importação | S |  |  | N |
| NOMEARQUIVO | Arquivo processado | S |  |  | N |
| REGRAENDERECO | Regra de Importação de Endereço | S |  |  | N |
| REGRAS | Validação de regras de negócio | S |  |  | N |
| TEMPOTOTAL | Tempo de execução | S |  |  | N |
| TOTATUALIZADO | Registros atualizados | I |  |  | N |
| TOTREG | Total de registros | I |  |  | N |
| TOTREGPROC | Registros inseridos | I |  |  | N |
| TOTREJEITADO | Registros rejeitados | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ImportacaoLegacyDetalhe / [TSIILD](TSIILD.md) | LogImportacaoLegacy / [TSILIL](TSILIL.md) | CODLIL->CODLIL | I |
| LogImportacaoLegacy / [TSILIL](TSILIL.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
