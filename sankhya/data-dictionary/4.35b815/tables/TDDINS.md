# TDDINS — Mapeamento de Instâncias

- Módulo técnico: [TDD](../modules/TDD.md)
- Campos: 18
- Instâncias DWF: 3
- Relações DWF: 16
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FormularioFormatado | Formulário Formatado | S | S |
| Instancia | Mapeamento de Instâncias | S | S |
| InstanciaCadastro | Instância Cadastro | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ADICIONAL | Campo Adicional | S |  |  | N |
| ATIVO | Instância é Ativa | S |  |  | N |
| CATEGORIA | CATEGORIA | S |  |  | N |
| DEFINICAOINST | Definição Instância | S |  |  | N |
| DESCRINSTANCIA | Descrição da Instância | S |  |  | N |
| DESCRTELA | DESCRTELA | S |  |  | N |
| EXPRESSAO | Expressão | S |  |  | N |
| FILTRO | Filtro | S |  |  | N |
| ISLIB | isLib | S |  |  | N |
| NOMEINSTANCIA | Nome da Instância | S |  |  | N |
| NOMESCRIPTCHAVE | Nome Script Chave | S |  |  | N |
| NOMETAB | Nome da Tabela | S |  |  | N |
| NUINSTANCIA | Núm. da Instância | I |  |  | N |
| NUINSTANCIAEXT | Instância de Extensão | I |  |  | N |
| NUINSTANCIAPAI | Núm. Instância Pai | I |  |  | N |
| RAIZ | Raiz | S |  |  | N |
| RESOURCEID | ID do cadastro | S |  |  | N |
| TIPOFORM | TIPOFORM | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CadastroFiltrosAPI / [TSICFA](TSICFA.md) | Instancia / [TDDINS](TDDINS.md) | NUINSTANCIA->NUINSTANCIA | I |
| DestinatarioPlanejamento / [TPQDPL](TPQDPL.md) | Instancia / [TDDINS](TDDINS.md) | NUINSTANCIA->NUINSTANCIA | I |
| EnvioQuestionario / [TPQENV](TPQENV.md) | Instancia / [TDDINS](TDDINS.md) | NUINSTANCIA->NUINSTANCIA | I |
| FormularioFormatado / [TDDINS](TDDINS.md) | BotaoAcao / [TSIBTA](TSIBTA.md) | NOMEINSTANCIA->NOMEINSTANCIA | I |
| FormularioFormatado / [TDDINS](TDDINS.md) | EventoProgramavel / [TSIEVP](TSIEVP.md) | NOMEINSTANCIA->NOMEINSTANCIA | I |
| FormularioFormatado / [TDDINS](TDDINS.md) | Ligacao / [TDDLIG](TDDLIG.md) | NUINSTANCIA->NUINSTORIG | I |
| FormularioFormatado / [TDDINS](TDDINS.md) | Tabela / [TDDTAB](TDDTAB.md) | NOMETAB->NOMETAB | I |
| Instancia / [TDDINS](TDDINS.md) | BotaoAcao / [TSIBTA](TSIBTA.md) | NOMEINSTANCIA->NOMEINSTANCIA | I |
| Instancia / [TDDINS](TDDINS.md) | EventoProgramavel / [TSIEVP](TSIEVP.md) | NOMEINSTANCIA->NOMEINSTANCIA | I |
| Instancia / [TDDINS](TDDINS.md) | Ligacao / [TDDLIG](TDDLIG.md) | NUINSTANCIA->NUINSTORIG | I |
| Instancia / [TDDINS](TDDINS.md) | Tabela / [TDDTAB](TDDTAB.md) | NOMETAB->NOMETAB | I |
| ItensPacote / [TSSITP](TSSITP.md) | Instancia / [TDDINS](TDDINS.md) | NUINSTANCIA->NUINSTANCIA | I |
| ItensPacote / [TSSITP](TSSITP.md) | InstanciaCadastro / [TDDINS](TDDINS.md) | NUINSTANCIACAD->NUINSTANCIA | I |
| Ligacao / [TDDLIG](TDDLIG.md) | Instancia / [TDDINS](TDDINS.md) | NUINSTDEST->NUINSTANCIA | I |
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | Instancia / [TDDINS](TDDINS.md) | CODINSTQUEBRA->NUINSTANCIA | I |
| Relatorio / [TSIRFE](TSIRFE.md) | Instancia / [TDDINS](TDDINS.md) | NUINSTANCIA->NUINSTANCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
