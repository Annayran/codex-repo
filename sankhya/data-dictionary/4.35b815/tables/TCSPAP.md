# TCSPAP — ParceiroProspect

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 59
- Instâncias DWF: 1
- Relações DWF: 13
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ParceiroProspect | Parceiro Prospect | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CARTAOCREDITO | Bandeira do Cartão | S |  |  | N |
| CARTEIRATRAB | Carteira de Trabalho | I |  |  | N |
| CELULARCONJUGE | Celular do Cônjuge | S |  |  | N |
| CEP | CEP | S |  |  | N |
| CGC_CPF | CNPJ/CPF | S |  |  | N |
| CNAE | CNAE principal do contribuinte | S |  |  | N |
| CODPAP | Prospect | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPARCB2B | Cód.Parceiro B2B | I |  |  | N |
| CODTIPPARC | Cód. Perfil Principal | I |  |  | N |
| CODUF | UF | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| COMPLEMENTO | Complemento | S |  |  | N |
| DATAEMISSAORG | Dt. de Emissão Identidade | D |  |  | N |
| DHCADRF | Data/Hora da Última Consulta ReceitaWS | H |  |  | N |
| DHCADSEFAZ | Data/Hora da Última Consulta SEFAZ | H |  |  | N |
| DIAPAGTO | Dia pagto | I |  |  | N |
| DTBAIXA | Data da baixa do contribuinte | D |  |  | N |
| DTCAD | Data do Cadastro | H |  |  | N |
| DTINIATIV | Início da Atividade do Contribuinte | D |  |  | N |
| DTNASC | Data de Nascimento | D |  |  | N |
| DTULTSIT | Última mod. da situação cadastral | D |  |  | N |
| EMAIL | E-mail | S |  |  | N |
| EMISSAORG | Órgão Emissor | S |  |  | N |
| ENDERECO | Endereço | S |  |  | N |
| ESCOLARIDADE | Escolaridade | S |  |  | N |
| ESTADOCIVIL | Estado Civil | S |  |  | N |
| IDENTINSCESTAD | Insc. Estadual | S |  |  | N |
| INDCREDCTE | Contribuinte credenciado a emitir CTe | I |  |  | N |
| INDCREDNFE | Contribuinte credenciado a emitir NF-e | I |  |  | N |
| INTERVANALISCRED | Intervalo p/ análise de crédito | I |  |  | N |
| ISPROPOSTACARTAO | Informa se cadastrado foi na tela Proposta Cartão | S |  |  | N |
| LIMCRED | Limite crédito | F |  |  | N |
| NATURALIDADE | Naturalidade | I |  |  | N |
| NOMEBAI | Bairro | S |  |  | N |
| NOMECARTAO | Nome no cartão | S |  |  | N |
| NOMECID | Cidade | S |  |  | N |
| NOMECONJUGE | Nome do Cônjuge | S |  |  | N |
| NOMEMAE | Nome da mãe | S |  |  | N |
| NOMEPAI | Nome do pai | S |  |  | N |
| NOMEPAP | Nome | S |  |  | N |
| NROANS | NROANS | I |  |  | N |
| NROBEN | NROBEN | I |  |  | N |
| NUMEND | Número | S |  |  | N |
| OBSERVACOES | Observações | S |  |  | N |
| POSSUICARTAOCREDITO | Possui Cartao de Crédito | S |  |  | N |
| QTDEDEPENDENTES | Qtd. Dependentes | I |  |  | N |
| RAZAOSOCIAL | Razão Social | S |  |  | N |
| REGAPUR | Regime de apuração | S |  |  | N |
| RG | Identidade | S |  |  | N |
| SEXO | Sexo | S |  |  | N |
| SITCADRF | Situação Cadastral ReceitaWS | I |  |  | N |
| SITCADSEFAZ | Situação Cadastral SEFAZ | S |  |  | N |
| STATUSPROPOSTA | Status da Proposta | S |  |  | N |
| TELCELULAR | Telefone Celular | S |  |  | N |
| TELEFONE | Telefone Prospect | S |  |  | N |
| TIPORESIDENCIA | Tipo de Residência | S |  |  | N |
| TIPPESSOA | Tipo de pessoa | S |  |  | N |
| UFRG | Estado emissor da RG | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContatoProspect / [TCSCTT](TCSCTT.md) | ParceiroProspect / [TCSPAP](TCSPAP.md) | CODPAP->CODPAP | I |
| EventoAgendado / [TCSEAG](TCSEAG.md) | ParceiroProspect / [TCSPAP](TCSPAP.md) | CODPAP->CODPAP | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | ParceiroProspect / [TCSPAP](TCSPAP.md) | CODPAP->CODPAP | I |
| ParceiroProspect / [TCSPAP](TCSPAP.md) | Cidade / [TSICID](TSICID.md) | NATURALIDADE->CODCID | I |
| ParceiroProspect / [TCSPAP](TCSPAP.md) | ContatoProspect / [TCSCTT](TCSCTT.md) | CODPAP->CODPAP | I |
| ParceiroProspect / [TCSPAP](TCSPAP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ParceiroProspect / [TCSPAP](TCSPAP.md) | ParceiroF2 / [TGFPAR](TGFPAR.md) | CODPARCB2B->CODPARC | I |
| ParceiroProspect / [TCSPAP](TCSPAP.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| ParceiroProspect / [TCSPAP](TCSPAP.md) | PerfilProspect / [TCSPPP](TCSPPP.md) | CODPAP->CODPAP | I |
| ParceiroProspect / [TCSPAP](TCSPAP.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| ParceiroProspect / [TCSPAP](TCSPAP.md) | UnidadeFederativaDestino / [TSIUFS](TSIUFS.md) | UFRG->CODUF | I |
| ParceiroProspect / [TCSPAP](TCSPAP.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| PerfilProspect / [TCSPPP](TCSPPP.md) | ParceiroProspect / [TCSPAP](TCSPAP.md) | CODPAP->CODPAP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
