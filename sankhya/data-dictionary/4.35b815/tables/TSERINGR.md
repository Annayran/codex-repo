# TSERINGR — Informações Gerais Serasa

- Módulo técnico: [TSE](../modules/TSE.md)
- Campos: 58
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| InformacaoGeralSerasa | InformacaoGeralSerasa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BAIRRO | Bairro | S |  |  | N |
| CELULAR | Celular | S |  |  | N |
| CEP | Cep | S |  |  | N |
| CIDADE | Cidade | S |  |  | N |
| CNAE | CNAE | S |  |  | N |
| CNPJ | CNPJ | S |  |  | N |
| CODMOSAIC | Código Mosaic | S |  |  | N |
| CODSERASA | Cód. Atividade Serasa | S |  |  | N |
| CPFCONJUGE | CPF Cônjuge | S |  |  | N |
| DATACNPJ | Data do Cnpj | D |  |  | N |
| DATAFUND | Data de fundação | D |  |  | N |
| DATANASC | Data de nascimento | D |  |  | N |
| DDDCEL | DDD do Celular | S |  |  | N |
| DDDCOM | DDD Comercial | S |  |  | N |
| DDDRES | DDD Residêncial | S |  |  | N |
| DESCRSITRF | Situação | S |  |  | N |
| DTATUALIZACAO | Data Atualização | D |  |  | N |
| DTRESIDECIA | Residente desde | D |  |  | N |
| DTRG | Data de emissão | D |  |  | N |
| DTUTRG | Data do registro | D |  |  | N |
| EMAIL | E-mail | S |  |  | N |
| EMISRG | Órgão emissor | S |  |  | N |
| ENDERECO | Endereço | S |  |  | N |
| ESCOLARIDADE | Escolaridade | S |  |  | N |
| ESTADOCIVIL | Estado civil | S |  |  | N |
| FILIAIS | Filiais | S |  |  | N |
| FONECOM | Telefone | S |  |  | N |
| FONERES | Telefone residêncial | S |  |  | N |
| HOMEPAGE | Home Page | S |  |  | N |
| INSCRICAOESTADUAL | Inscrição estadual | S |  |  | N |
| MSGMOSAIC | Mensagem Mosaic | S |  |  | N |
| MUNNASC | Cidade de nascimento | S |  |  | N |
| NIRE | Nire | S |  |  | N |
| NOME | Nome | S |  |  | N |
| NOMEFANTASIA | Nome fantasia | S |  |  | N |
| NOMEMAE | Nome da mãe | S |  |  | N |
| NRFAX | Fax | S |  |  | N |
| NRODEPENDENTES | Qtde. dependentes | I |  |  | N |
| NROFILIAIS | Qtde. Filiais | I |  |  | N |
| NRUTRG | Registro | S |  |  | N |
| NUCONSULTA | NUCONSULTA | I |  |  | N |
| NUMEMPREGADOS | Qtde. empregados | I |  |  | N |
| OPCAOTRIBUTARIA | Opção tributária | S |  |  | N |
| PERCEXPORT | Exp. s/ Vendas | I |  |  | N |
| PERCIMPORT | Imp. s/ Compras | I |  |  | N |
| RAMAL | Ramal | I |  |  | N |
| RAMOATV | Ramo de atividade | S |  |  | N |
| RAZAO | Razão social | S |  |  | N |
| RG | RG | S |  |  | N |
| SEXO | Sexo | S |  |  | N |
| SITCPF | Situação do Cpf | I |  |  | N |
| SITUACAORF | Situação da empresa | I |  |  | N |
| TIPODOC | Tipo de documento | S |  |  | N |
| TIPOSOCIEDADE | Tipo de sociedade | S |  |  | N |
| TIPRELATO | Tipo de Relato | S |  |  | N |
| UF | UF | S |  |  | N |
| UFNASC | UF Nascimento | S |  |  | N |
| UFRG | UF RG | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| InformacaoGeralSerasa / [TSERINGR](TSERINGR.md) | AnaliseCreditoSerasa / [TSERACS](TSERACS.md) | NUCONSULTA->NUCONSULTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
