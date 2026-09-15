# TFCBCO — tabela de Currículos - recrutamento e seleção

- Módulo técnico: [TFC](../modules/TFC.md)
- Campos: 75
- Instâncias DWF: 1
- Relações DWF: 12
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Curriculo | Currículo - recrutamento e seleção | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANOCHEGPAIS | Ano de chegada ao país | I |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| BAIRRO | Bairro | S |  |  | N |
| CATEGORIACNH | Categoria CNH | S |  |  | N |
| CEP | CEP | S |  |  | N |
| CIDADE | Cidade | S |  |  | N |
| CNH | Número da CNH | S |  |  | N |
| CODTIPPARC | Cód Parceiro | I |  |  | N |
| CODUSU | Cód Usuário | I |  |  | N |
| COMPLEND | Complemento | S |  |  | N |
| CONSIDERACOES | Considerações | S |  |  | N |
| CPF | CPF | S |  |  | N |
| DEFAUDITIVO | Possui deficiência auditiva? | S |  |  | N |
| DEFFALA | Possui deficiência na fala? | S |  |  | N |
| DEFICIENTE | Deficiente | S |  |  | N |
| DEFICIENTEF | Possui Deficiencia Fisica? | S |  |  | N |
| DEFINDEPENDENTE | É independente para as atividades da vida diária? | S |  |  | N |
| DEFINTELECTUAL | Possui deficiência intelectual? | S |  |  | N |
| DEFOUTRAS | Outras deficiências | S |  |  | N |
| DEFOUTROSRECURSOS | Outros recursos/adaptações que utiliza para realização do trabalho | S |  |  | N |
| DEFRECURSOS | Quais recursos utiliza para a realização do trabalho? | I |  |  | N |
| DEFRECURSOSIMPRES | Recursos imprescindíveis para a acessibilidade no local de trabalho | I |  |  | N |
| DEFVISUAL | Possui deficiência visual? | S |  |  | N |
| DESCRDEFICIENCIA | Descrição Deficiencia | S |  |  | N |
| DISPMUDANCA | Disponibilidade p/ mudança | S |  |  | N |
| DISPVIAGEM | Disponibilidade p/ viagem | S |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |
| DTINCLUSAO | Data de Inclusão | D |  |  | N |
| DTNASC | Data de Nascimento | D |  |  | N |
| DTVALCNH | Data de validade CNH | D |  |  | N |
| DTVALPAIS | Data Volta Pais | D |  |  | N |
| EMAIL | E-mail | S |  |  | N |
| ESTADOCIVIL | Estado Civil | I |  |  | N |
| ESTRANGEIRO | É estrangeiro | S |  |  | N |
| FACEBOOK | Facebook | S |  |  | N |
| FOTO | Foto | B |  |  | N |
| GOOGLEPLUS | Google+ | S |  |  | N |
| GRAUINSTR | Grau de Instrução | I |  |  | N |
| IDENTIDADE | RG | S |  |  | N |
| INAPROPRIADO | Currículo Inapropriado | S |  |  | N |
| INCLUSAOMGE | Inclusão MGE | S |  |  | N |
| INDICACAO | Indicação | S |  |  | N |
| LINKEDIN | Linkedin | S |  |  | N |
| NATURALIDADE | Naturalidade | S |  |  | N |
| NOMECANDIDATO | Nome Completo | S |  |  | N |
| NOMEEND | Endereço | S |  |  | N |
| NUCURRICULO | Cód. Currículo | I |  |  | N |
| NUCURRICULOBT | Número Currículo | I |  |  | N |
| NUMCPS | CTPS | I |  |  | N |
| NUMEND | Número | S |  |  | N |
| ORGAORG | Órgão Expedidor | S |  |  | N |
| PAIS | Nacionalidade | S |  |  | N |
| PIS | PIS | S |  |  | N |
| PRETSALARIAL | Pretensão salarial | F |  |  | N |
| PRETSALARIALFINAL | Pretensão salarial final | F |  |  | N |
| PRETSALARIALINICIAL | Pretensão salarial inicial | F |  |  | N |
| RAMAL | Ramal | S |  |  | N |
| RNE | RNE | S |  |  | N |
| SERIECPS | Série | S |  |  | N |
| SEXO | Sexo | S |  |  | N |
| SITPAIS | Situação Pais | S |  |  | N |
| SKYPE | Skype | S |  |  | N |
| STATUS | Status | I |  |  | N |
| TELCEL | Telefone Celular | S |  |  | N |
| TELCOM | Telefone Comercial | S |  |  | N |
| TELPREF | Telefone Preferencial | S |  |  | N |
| TELRES | Telefone Residencial | S |  |  | N |
| TIPDEFICIENCIA | Tipo Deficiência | I |  |  | N |
| TIPOCURRICULO | Origem do Currículo | I |  |  | N |
| TIPOEND | Tipo Endereço | S |  |  | N |
| TWITTER | Twitter | S |  |  | N |
| UF | UF | S |  |  | N |
| UFCPS | UF CTPS | I |  |  | N |
| USOAPAUDITIVO | Faz uso de aparelho auditivo? | S |  |  | N |
| VEICPROPRIO | Possui veículo próprio | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Candidato / [TRSCAN](TRSCAN.md) | Curriculo / [TFCBCO](TFCBCO.md) | NUCURRICULO->NUCURRICULO | I |
| Curriculo / [TFCBCO](TFCBCO.md) | CargosSelecionados / [TFCCAR](TFCCAR.md) | NUCURRICULO->NUCURRICULO | L |
| Curriculo / [TFCBCO](TFCBCO.md) | CurriculoPerfilAreaProf / [TFCPER](TFCPER.md) | NUCURRICULO->NUCURRICULO | I |
| Curriculo / [TFCBCO](TFCBCO.md) | CurriculoPerfilHierarquico / [TFCPER](TFCPER.md) | NUCURRICULO->NUCURRICULO | I |
| Curriculo / [TFCBCO](TFCBCO.md) | CurriculoPerfilRequisito / [TFCPER](TFCPER.md) | NUCURRICULO->NUCURRICULO | I |
| Curriculo / [TFCBCO](TFCBCO.md) | CursoCurriculo / [TFCCUR](TFCCUR.md) | NUCURRICULO->NUCURRICULO | L |
| Curriculo / [TFCBCO](TFCBCO.md) | Dependentes / [TFCDEP](TFCDEP.md) | NUCURRICULO->NUCURRICULO | L |
| Curriculo / [TFCBCO](TFCBCO.md) | ExperienciaProfissional / [TFCEXP](TFCEXP.md) | NUCURRICULO->NUCURRICULO | L |
| Curriculo / [TFCBCO](TFCBCO.md) | FuncaoCurriculo / [TFCFCO](TFCFCO.md) | NUCURRICULO->NUCURRICULO | I |
| Curriculo / [TFCBCO](TFCBCO.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | UFCPS->CODUF | I |
| Curriculo / [TFCBCO](TFCBCO.md) | ViewProcessoSelecaoCurriculo / [VGFSELECAO_CURRICULO](VGFSELECAO_CURRICULO.md) | NUCURRICULO->NUCURRICULO | I |
| SelecaoCurriculo / [TRSCAN](TRSCAN.md) | Curriculo / [TFCBCO](TFCBCO.md) | NUCURRICULO->NUCURRICULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
