# TFCEXP — Tabela experiência profissional - currículo

- Módulo técnico: [TFC](../modules/TFC.md)
- Campos: 22
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ExperienciaProfissional | Experiência Profissional | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| Atual | Emprego Atual | S |  |  | S |
| BAIRRO | Bairro | S |  |  | N |
| CEP | CEP | S |  |  | N |
| CIDADE | Cidade | S |  |  | N |
| COMPLEND | Complemento | S |  |  | N |
| DATADM | Data Início | D |  |  | N |
| DATDEM | Data Saída | D |  |  | N |
| DESCRATIVIDADES | Descrição Atividades | S |  |  | N |
| EMAIL | E-mail | S |  |  | N |
| FAIXASALARIALFINAL | FAIXASALARIALFINAL | F |  |  | N |
| FAIXASALARIALINICIAL | FAIXASALARIALINICIAL | F |  |  | N |
| NOMEEMPRESA | Nome da empresa | S |  |  | N |
| NOMEEND | Endereço | S |  |  | N |
| NUCURRICULO | NUCURRICULO | I |  |  | N |
| NUMEND | Número | S |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| TELEFONE | Telefone | S |  |  | N |
| TIPOEMPRESA | Tipo | S |  |  | N |
| TIPOEND | Tipo Endereço | S |  |  | N |
| UF | UF | S |  |  | N |
| ULTCARGOOCUPADO | Último Cargo | S |  |  | N |
| ULTSALARIO | Último salário | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Curriculo / [TFCBCO](TFCBCO.md) | ExperienciaProfissional / [TFCEXP](TFCEXP.md) | NUCURRICULO->NUCURRICULO | L |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
