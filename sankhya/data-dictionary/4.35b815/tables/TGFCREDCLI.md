# TGFCREDCLI — Tabela de cliente de crediário

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 26
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ClienteCrediario | Tabela de cliente de crediário | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CARGO | Cargo | S |  |  | N |
| CELULAR | Celular | S |  |  | N |
| CEPEMPREGO | CEP | S |  |  | N |
| CODBAIEMPREGO | Código bairro | I |  |  | N |
| CODCIDEMPREGO | Código cidade | I |  |  | N |
| CODCLIENTE | Código do cliente | I |  |  | N |
| CODENDEMPREGO | Cod. endereço emprego | I |  |  | N |
| CODUSUCADASTRO | Usuário cadastro | I |  |  | N |
| CONJUGE | Cônjuge | I |  |  | N |
| DTADMISSAO | Data de admissão | H |  |  | N |
| DTCADASTRO | Data cadastro | H |  |  | N |
| EMPRESAEMPREGO | Empresa | S |  |  | N |
| ESTADOCIVIL | Estado civil | I |  |  | N |
| FILIACAOMAE | Filiação mãe | S |  |  | N |
| FILIACAOPAI | Filiação pai | S |  |  | N |
| GRAUINSTRU | Grau de instrução | I |  |  | N |
| LIMCREDITO | Limite de crédito | F |  |  | N |
| MORADIAPROPRIA | Moradia própria | S |  |  | N |
| NATURALIDADE | Naturalidade | I |  |  | N |
| NOMEREFEMPREGO | Nome referência | S |  |  | N |
| PROFISSAO | Profissão | S |  |  | N |
| SCORE | Score | F |  |  | N |
| TELEMPREGO | Telefone emprego | S |  |  | N |
| TELREFEMPREGO | Telefone referência | S |  |  | N |
| TEMPOMORADIA | Tempo moradia | I |  |  | N |
| TIPEMPREGO | Tipo de Emprego | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AuditoriaCadastroClienteCred / [TGFCREDAUDIT](TGFCREDAUDIT.md) | ClienteCrediario / [TGFCREDCLI](TGFCREDCLI.md) | CODCLIENTE->CODCLIENTE | I |
| ClienteCrediario / [TGFCREDCLI](TGFCREDCLI.md) | Cidade / [TSICID](TSICID.md) | NATURALIDADE->CODCID | I |
| ClienteCrediario / [TGFCREDCLI](TGFCREDCLI.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODCLIENTE->CODPARC | I |
| RendaClienteCrediario / [TGFCREDCLIREND](TGFCREDCLIREND.md) | ClienteCrediario / [TGFCREDCLI](TGFCREDCLI.md) | CODCLIENTE->CODCLIENTE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
