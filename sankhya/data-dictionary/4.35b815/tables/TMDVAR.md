# TMDVAR — Tabela de definição de variáveis

- Módulo técnico: [TMD](../modules/TMD.md)
- Campos: 19
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MSDVariavel | MSDVariavel | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVA | Ativa? | S |  |  | N |
| CFGDTRESOL | Configuração da data de resolução | S |  |  | N |
| CODGRUPO | Código do Grupo | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODVAR | Código da Variável | I |  |  | N |
| DESCRVAR | Descrição da Variável | S |  |  | N |
| DTALTER | Data de Alteração | H |  |  | N |
| EXPDTREF | Expressão de Data de Referência | S |  |  | N |
| FORMULA | Fórmula | C |  |  | N |
| NOMEEXIB | Nome de Exibição | S |  |  | N |
| NOMEVAR | Nome da Variável | S |  |  | N |
| OBS | Observação | S |  |  | N |
| ORDEM | Ordem | I |  |  | N |
| PERIODICIDADE | Periodicidade | I |  |  | N |
| PROXRESOL | Data para a próxima resolução da variável | H |  |  | N |
| TIPO | Tipo | S |  |  | N |
| TIPOSAIDA | Tipo de Saída | S |  |  | N |
| ULTRESOL | Data da última resolução da variável | H |  |  | N |
| VALORPADRAO | A variável permite valor padrão? | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MSDCampoVariavel / [TMDCPV](TMDCPV.md) | MSDVariavel / [TMDVAR](TMDVAR.md) | CODVAR->CODVAR | I |
| MSDGrupoVariavel / [TMDGRV](TMDGRV.md) | MSDVariavel / [TMDVAR](TMDVAR.md) | CODVAR->CODVAR | I |
| MSDVariavel / [TMDVAR](TMDVAR.md) | MSDCampoVariavel / [TMDCPV](TMDCPV.md) | CODVAR->CODVAR | I |
| MSDVariavel / [TMDVAR](TMDVAR.md) | MSDGrupo / [TMDGRU](TMDGRU.md) | CODGRUPO->CODGRUPO | I |
| MSDVariavel / [TMDVAR](TMDVAR.md) | MSDGrupoVariavel / [TMDGRV](TMDGRV.md) | CODVAR->CODVAR | I |
| MSDVariavel / [TMDVAR](TMDVAR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| MSDVarsResolvidas / [TMDVRE](TMDVRE.md) | MSDVariavel / [TMDVAR](TMDVAR.md) | CODVAR->CODVAR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
