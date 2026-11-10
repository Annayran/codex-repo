# Modelagem DWF - Base Instalada Genesis

## Objetivo

Especificar os objetos que devem ser criados manualmente no Dicionário de Dados
e no Construtor de Telas Sankhya para importar, conciliar e consultar a Base
Instalada Genesis sem inserir registros diretamente em tabelas padrão.

A tela mestre deverá se chamar:

`[ZAYA.IT] - Base Instalada Genesis`

## Regras gerais de cadastro

- Criar as instâncias pelo Dicionário de Dados/Construtor de Telas.
- Usar chave local autonumerada em cada tabela.
- Manter o ID Genesis em campo separado; ele não deve ser a chave física porque
  uma nova fotografia da base poderá repetir os mesmos IDs.
- Todas as tabelas detalhe devem possuir `IDBASE` ligado à tabela mestre.
- Não habilitar exclusão em cascata da base para os detalhes.
- Códigos externos, CPF, CNPJ, CEP, telefone, série e RM devem ser texto para
  preservar zeros à esquerda, hífens e outros caracteres.
- Criar índice para `IDBASE` em todas as tabelas detalhe. Quando o ambiente
  permitir, criar também índice lógico para `(IDBASE, IDGENESIS)`.
- Não considerar `CODIGOEXTERNO`, CNPJ, CPF, nome ou série como chave única.

## 1. Tabela mestre - `AD_GENBASE`

Descrição da instância: `Base Instalada Genesis`.

| Campo | Descrição | Tipo | Tamanho | Obrigatório | Observação |
|---|---|---:|---:|:---:|---|
| `IDBASE` | Identificador da carga | Inteiro | 10 | Sim | Chave primária autonumerada |
| `DESCRICAO` | Descrição da base | Texto | 120 | Sim | Ex.: Base Genesis 2026-08-26 |
| `AMBIENTE` | Ambiente de origem | Texto | 10 | Sim | `HML` ou `PRD`; confirmar antes da carga |
| `DTREFERENCIA` | Data de referência | Data/Hora | - | Sim | Data da extração da Genesis |
| `STATUS` | Status da carga | Texto | 20 | Sim | `PREPARACAO`, `IMPORTADA`, `CONCILIADA`, `ATIVA`, `INATIVA` |
| `ORIGEM` | Origem dos dados | Texto | 30 | Sim | Ex.: `PLANILHA_GENESIS` |
| `ARQUIVOORIGEM` | Nome do arquivo | Texto | 255 | Não | Guardar somente o nome, sem caminho local |
| `DHCARGA` | Data/hora da carga | Data/Hora | - | Sim | Preenchimento no início da importação |
| `CODUSUCARGA` | Usuário responsável | Inteiro | 10 | Não | Relacionar com usuário Sankhya, se aplicável |
| `OBSERVACAO` | Observações da carga | Texto longo | 4000 | Não | Pendências, origem e validações |

## 2. Detalhe Clientes - `AD_GENCLIENTE`

Importar a aba `Cliente`.

| Campo | Descrição | Tipo | Tamanho | Obrigatório |
|---|---|---:|---:|:---:|
| `IDREGCLI` | Identificador local | Inteiro | 18 | Sim |
| `IDBASE` | Base instalada | Inteiro | 10 | Sim |
| `IDGENESIS` | ID do cliente na Genesis | Inteiro | 18 | Sim |
| `CODIGOEXTERNO` | Código externo legado | Texto | 200 | Não |
| `RAZAOSOCIAL` | Razão social | Texto | 255 | Sim |
| `NOMEFANTASIA` | Nome fantasia | Texto | 255 | Sim |
| `CNPJ` | CNPJ | Texto | 20 | Não |
| `CPF` | CPF | Texto | 14 | Não |
| `ENDERECO` | Logradouro | Texto | 255 | Não |
| `ENDERECONUMERO` | Número | Texto | 30 | Não |
| `ENDERECOBAIRRO` | Bairro | Texto | 120 | Não |
| `ENDERECOCIDADE` | Cidade | Texto | 120 | Não |
| `ENDERECOUF` | UF | Texto | 2 | Não |
| `CEP` | CEP | Texto | 10 | Não |
| `TELEFONE` | Telefone | Texto | 30 | Não |
| `EMAIL` | E-mail | Texto | 255 | Não |
| `STATUSGENESIS` | Status na Genesis | Texto | 20 | Não |
| `CODPARC` | Parceiro Sankhya conciliado | Inteiro | 10 | Não |
| `STATUSCONC` | Status da conciliação | Texto | 20 | Sim |
| `MENSAGEMCONC` | Motivo da pendência | Texto longo | 4000 | Não |
| `DHCONCILIACAO` | Data/hora da conciliação | Data/Hora | - | Não |

Opções de `STATUSCONC`: `PENDENTE`, `CONCILIADO`, `DIVERGENTE`, `IGNORADO`.

## 3. Detalhe Setores - `AD_GENSECTOR`

Importar a aba `Setor`. Um cliente pode possuir nenhum, um ou vários setores.

| Campo | Descrição | Tipo | Tamanho | Obrigatório |
|---|---|---:|---:|:---:|
| `IDREGSET` | Identificador local | Inteiro | 18 | Sim |
| `IDBASE` | Base instalada | Inteiro | 10 | Sim |
| `IDGENESIS` | ID do setor na Genesis | Inteiro | 18 | Sim |
| `IDCLIENTEGEN` | ID Genesis do cliente | Inteiro | 18 | Sim |
| `CODIGOEXTERNO` | Código externo do setor | Texto | 200 | Não |
| `NOME` | Nome do setor | Texto | 255 | Sim |
| `STATUSGENESIS` | Status na Genesis | Texto | 20 | Não |
| `CODPARC` | Parceiro Sankhya conciliado | Inteiro | 10 | Não |
| `PADRAO` | Setor padrão para novos envios | Texto | 1 | Sim |
| `STATUSCONC` | Status da conciliação | Texto | 20 | Sim |
| `MENSAGEMCONC` | Motivo da pendência | Texto longo | 4000 | Não |
| `DHCONCILIACAO` | Data/hora da conciliação | Data/Hora | - | Não |

Opções de `PADRAO`: `S` e `N`. Não marcar automaticamente quando o cliente tiver
mais de um setor; a escolha depende de validação funcional.

## 4. Detalhe Materiais - `AD_GENMATERIAL`

Importar a aba `Material`. Na integração, material corresponde à classificação
ou grupo do modelo, e não ao equipamento serializado.

| Campo | Descrição | Tipo | Tamanho | Obrigatório |
|---|---|---:|---:|:---:|
| `IDREGMAT` | Identificador local | Inteiro | 18 | Sim |
| `IDBASE` | Base instalada | Inteiro | 10 | Sim |
| `IDGENESIS` | ID do material na Genesis | Inteiro | 18 | Sim |
| `CODIGOEXTERNO` | Código externo legado | Texto | 200 | Não |
| `NOME` | Nome do material | Texto | 255 | Sim |
| `TIPO` | Tipo do material | Texto | 30 | Não |
| `GRAURISCO` | Grau de risco | Texto | 30 | Não |
| `STATUSGENESIS` | Status na Genesis | Texto | 20 | Não |
| `CODGRUPOPROD` | Grupo de produto Sankhya | Inteiro | 10 | Não |
| `STATUSCONC` | Status da conciliação | Texto | 20 | Sim |
| `MENSAGEMCONC` | Motivo da pendência | Texto longo | 4000 | Não |
| `DHCONCILIACAO` | Data/hora da conciliação | Data/Hora | - | Não |

## 5. Detalhe Empresas/Fabricantes - `AD_GENEMPRESA`

Importar a aba `Empresa`. Esta tabela é necessária porque `TGFPRO.MARCA` é texto
e não armazena de forma normalizada o ID da empresa Genesis.

| Campo | Descrição | Tipo | Tamanho | Obrigatório |
|---|---|---:|---:|:---:|
| `IDREGEMP` | Identificador local | Inteiro | 18 | Sim |
| `IDBASE` | Base instalada | Inteiro | 10 | Sim |
| `IDGENESIS` | ID da empresa na Genesis | Inteiro | 18 | Sim |
| `CODIGOEXTERNO` | Código externo legado | Texto | 200 | Não |
| `RAZAOSOCIAL` | Razão social | Texto | 255 | Sim |
| `NOMEFANTASIA` | Nome fantasia | Texto | 255 | Sim |
| `FORNECEDOR` | Indicador de fornecedor | Inteiro | 1 | Não |
| `FABRICANTE` | Indicador de fabricante | Inteiro | 1 | Não |
| `STATUSGENESIS` | Status na Genesis | Texto | 20 | Não |
| `MARCASANKHYA` | Texto da marca no Sankhya | Texto | 100 | Não |
| `STATUSCONC` | Status da conciliação | Texto | 20 | Sim |
| `MENSAGEMCONC` | Motivo da pendência | Texto longo | 4000 | Não |
| `DHCONCILIACAO` | Data/hora da conciliação | Data/Hora | - | Não |

## 6. Detalhe Modelos - `AD_GENMODELO`

Importar a aba `Modelo`. O modelo depende de uma empresa/fabricante e de um
material Genesis.

| Campo | Descrição | Tipo | Tamanho | Obrigatório |
|---|---|---:|---:|:---:|
| `IDREGMOD` | Identificador local | Inteiro | 18 | Sim |
| `IDBASE` | Base instalada | Inteiro | 10 | Sim |
| `IDGENESIS` | ID do modelo na Genesis | Inteiro | 18 | Sim |
| `IDEMPRESAGEN` | ID Genesis do fabricante | Inteiro | 18 | Sim |
| `IDMATERIALGEN` | ID Genesis do material | Inteiro | 18 | Sim |
| `CODIGOEXTERNO` | Código externo legado | Texto | 200 | Não |
| `NOME` | Nome do modelo | Texto | 255 | Sim |
| `STATUSGENESIS` | Status na Genesis | Texto | 20 | Não |
| `NRANVISA` | Registro ANVISA | Texto | 50 | Não |
| `CODPROD` | Produto Sankhya conciliado | Inteiro | 10 | Não |
| `STATUSCONC` | Status da conciliação | Texto | 20 | Sim |
| `MENSAGEMCONC` | Motivo da pendência | Texto longo | 4000 | Não |
| `DHCONCILIACAO` | Data/hora da conciliação | Data/Hora | - | Não |

## 7. Detalhe Itens-Materiais - `AD_GENITEMMAT`

Importar a aba `ItemMaterial`. Esta é a Base Instalada propriamente dita. O
`IDGENESIS` é a identidade confiável do equipamento legado; código externo e
série não são únicos na planilha.

| Campo | Descrição | Tipo | Tamanho | Obrigatório |
|---|---|---:|---:|:---:|
| `IDREGITEM` | Identificador local | Inteiro | 18 | Sim |
| `IDBASE` | Base instalada | Inteiro | 10 | Sim |
| `IDGENESIS` | ID do item-material Genesis | Inteiro | 18 | Sim |
| `IDMODELOGEN` | ID Genesis do modelo | Inteiro | 18 | Sim |
| `IDCLIENTEGEN` | ID Genesis do cliente | Inteiro | 18 | Não |
| `IDSETORGEN` | ID Genesis do setor | Inteiro | 18 | Não |
| `CODIGOEXTERNO` | Código externo legado | Texto | 200 | Não |
| `NSERIE` | Número de série | Texto | 150 | Não |
| `RM` | Identificador RM legado | Texto | 100 | Não |
| `STATUSGENESIS` | Status do item | Texto | 20 | Não |
| `VALORUNITARIO` | Valor unitário | Decimal | 18,2 | Não |
| `DATAAQUISICAO` | Data de aquisição | Data/Hora | - | Não |
| `DATAINSTALACAO` | Data de instalação | Data/Hora | - | Não |
| `ULTIMAINSTALACAO` | Última instalação | Data/Hora | - | Não |
| `MARCA` | Marca informada | Texto | 100 | Não |
| `OUTROS` | Referência/observação legada | Texto | 255 | Não |
| `GARANTIA` | Garantia em meses | Inteiro | 10 | Não |
| `IDPROPRIEDADEGEN` | ID da propriedade Genesis | Inteiro | 18 | Não |
| `DONO` | Descrição da propriedade | Texto | 50 | Não |
| `IDSITUACAOGEN` | ID da situação Genesis | Inteiro | 18 | Não |
| `SITUACAO` | Descrição da situação | Texto | 50 | Não |
| `CODEMP` | Empresa Sankhya conciliada | Inteiro | 10 | Não |
| `CODPARC` | Parceiro Sankhya conciliado | Inteiro | 10 | Não |
| `CODPROD` | Produto Sankhya conciliado | Inteiro | 10 | Não |
| `NUNOTA` | Número único relacionado | Inteiro | 10 | Não |
| `SEQUENCIA` | Sequência do item | Inteiro | 10 | Não |
| `CHAVEMOVIMENTO` | Chave do movimento Sankhya | Texto | 200 | Não |
| `STATUSCONC` | Status da conciliação | Texto | 20 | Sim |
| `MENSAGEMCONC` | Motivo da pendência | Texto longo | 4000 | Não |
| `DHCONCILIACAO` | Data/hora da conciliação | Data/Hora | - | Não |

## 8. Detalhe Domínios - `AD_GENDOMINIO`

Importar as abas `prorpiedade_equipamento` e `situacao_equipamento` na mesma
tabela, diferenciadas por `TIPODOMINIO`.

| Campo | Descrição | Tipo | Tamanho | Obrigatório |
|---|---|---:|---:|:---:|
| `IDREGDOM` | Identificador local | Inteiro | 18 | Sim |
| `IDBASE` | Base instalada | Inteiro | 10 | Sim |
| `TIPODOMINIO` | Tipo do domínio | Texto | 20 | Sim |
| `IDGENESIS` | ID do domínio na Genesis | Inteiro | 18 | Sim |
| `NOME` | Nome do domínio | Texto | 100 | Sim |
| `STATUSGENESIS` | Status na Genesis | Texto | 20 | Não |
| `CODIGOSANKHYA` | Código correspondente no Sankhya | Texto | 20 | Não |
| `STATUSCONC` | Status da conciliação | Texto | 20 | Sim |
| `MENSAGEMCONC` | Motivo da pendência | Texto longo | 4000 | Não |

Opções de `TIPODOMINIO`: `PROPRIEDADE` e `SITUACAO`.

## 9. Central de parâmetros existente - `AD_CENTPARAM`

Manter `NUPAR=1`, `TOKENGENESIS` e a tabela detalhe `AD_CENTPARAMTOP`.
Acrescentar os seguintes campos para retirar configurações fixas do Java:

| Campo | Descrição | Tipo | Tamanho | Obrigatório |
|---|---|---:|---:|:---:|
| `IDBASEATIVA` | Base instalada ativa | Inteiro | 10 | Não |
| `URLGENESIS` | URL base da API | Texto | 500 | Sim |
| `AMBIENTEGEN` | Ambiente Genesis | Texto | 10 | Sim |
| `ATIVOGEN` | Integração ativa | Texto | 1 | Sim |
| `SIMULACAOGEN` | Executar sem envio real | Texto | 1 | Sim |
| `LOTEGEN` | Quantidade por execução | Inteiro | 5 | Sim |
| `MAXTENTGEN` | Máximo de tentativas | Inteiro | 5 | Sim |
| `TIMEOUTCON` | Timeout de conexão em ms | Inteiro | 8 | Sim |
| `TIMEOUTLEI` | Timeout de leitura em ms | Inteiro | 8 | Sim |
| `EPOCHGEN` | Unidade de data epoch | Texto | 2 | Sim |

Opções: `ATIVOGEN` e `SIMULACAOGEN` usam `S/N`; `EPOCHGEN` usa `MS`, `S` ou
`US`. O token deve permanecer restrito e nunca aparecer em log ou dashboard.

## 10. Organização da tela mestre

Na tela `[ZAYA.IT] - Base Instalada Genesis`, usar `AD_GENBASE` como formulário
mestre e adicionar as grades detalhe nesta ordem:

1. Clientes;
2. Setores;
3. Materiais;
4. Empresas/Fabricantes;
5. Modelos;
6. Itens-Materiais;
7. Domínios.

Configurar paginação e filtros na grade de itens-materiais, pois a carga atual
possui mais de 65 mil registros. Exibir inicialmente filtros por ID Genesis,
cliente, setor, modelo, série, código externo e status de conciliação.

## 11. Ordem de importação

1. Criar um registro em `AD_GENBASE`.
2. Importar propriedades e situações em `AD_GENDOMINIO`.
3. Importar clientes.
4. Importar setores.
5. Importar materiais.
6. Importar empresas/fabricantes.
7. Importar modelos.
8. Importar itens-materiais.
9. Conciliar clientes, grupos, fabricantes e produtos com o Sankhya.
10. Definir a base como `ATIVA` somente após validação dos totais e vínculos.

Em todas as planilhas de importação, adicionar `IDBASE` sem modificar o arquivo
original. Preservar `IDGENESIS` e `CODIGOEXTERNO` exatamente como recebidos.

## 12. Validações obrigatórias antes do uso pelo Java

- Nenhum item pode apontar para modelo inexistente na mesma base.
- Setor deve apontar para cliente existente na mesma base.
- Modelo deve apontar para empresa e material existentes na mesma base.
- Registros sem código externo não podem ser tratados automaticamente como novos.
- Cliente com CNPJ/CPF duplicado deve ficar `DIVERGENTE` até validação manual.
- Modelo+série não deve ser usado como chave única de item-material.
- Datas de 1969/1970 e textos com caracteres corrompidos devem ser revisados.
- Registros conciliados devem possuir o código Sankhya correspondente.

## 13. Impacto no Java após a criação

O fluxo deverá resolver os IDs nesta ordem:

1. procurar o cadastro conciliado na base ativa;
2. quando possuir `IDGENESIS`, executar PUT pelo ID preservando o código externo;
3. quando não possuir ID, consultar pelo código externo;
4. executar POST somente quando o cadastro for comprovadamente novo;
5. para item-material legado, usar `AD_GENITEMMAT.IDGENESIS` como identidade;
6. usar a view `AD_VW_GENESIS_ESTOQUE` apenas para movimentos novos;
7. manter `AD_GENMOVEST` como fila e auditoria, sem transformá-la em cadastro.

## 14. Campos opcionais nas tabelas padrão

Não são necessários na primeira versão porque os vínculos locais ficam nas
tabelas adicionais. Se a equipe optar por acesso direto, criar somente após a
conciliação:

- `TGFPAR.AD_IDCLIGEN` número 18 e `TGFPAR.AD_CODEXTCLI` texto 200;
- `TGFGRU.AD_IDMATGEN` número 18 e `TGFGRU.AD_CODEXTMAT` texto 200;
- `TGFPRO.AD_IDMODGEN` número 18 e `TGFPRO.AD_CODEXTMOD` texto 200;
- `TGFPRO.AD_IDEMPGEN` número 18.

Não criar um único setor Genesis em `TGFPAR`, porque existem clientes com vários
setores. Não usar `TGFSER` como repositório único do ID do item-material legado.
