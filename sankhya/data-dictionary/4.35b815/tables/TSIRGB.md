# TSIRGB — TABLE TSIRGB

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 19
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RegrasCustomizadas | Regras de customização | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ABRANGENCIA | Abrangência | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| ATUALIZAR | Update | S |  |  | N |
| CODRGB | CODRGB | I |  |  | N |
| CODUSU | Criado por | I |  |  | N |
| DELETAR | Delete | S |  |  | N |
| DESCRREGRA | Descrição | S |  |  | N |
| DOCUMENTACAO | Documentação | S |  |  | N |
| DTULTPUBLI | Dt. Última Publicação | H |  |  | N |
| GRUPOREGRA | Grupo | S |  |  | N |
| INSERIR | Insert | S |  |  | N |
| MOMENTOEXEC | Momento de Execução | S |  |  | N |
| NOMEREGRA | Nome Regra | S |  |  | N |
| ORDEM | ORDEM | I |  |  | N |
| PUBLICADO | Publicado | S |  |  | N |
| REGRA | Regra | C |  |  | N |
| STATUSPUBLI | Status Publicação | S |  |  | N |
| TABELA | Tabela Relacionada | S |  |  | N |
| TIPOREGRA | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RegrasCustomizadas / [TSIRGB](TSIRGB.md) | HistoricoAlteracaoRegras / [TSIHAB](TSIHAB.md) | CODRGB->CODRGB | I |
| RegrasCustomizadas / [TSIRGB](TSIRGB.md) | Tabela / [TDDTAB](TDDTAB.md) | TABELA->NOMETAB | I |
| RegrasCustomizadas / [TSIRGB](TSIRGB.md) | TelemetriaFramework / [TSITLM](TSITLM.md) | CODRGB->CODRGB | I |
| RegrasCustomizadas / [TSIRGB](TSIRGB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
