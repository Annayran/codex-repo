# Importacao de metadados Genesis

Pacotes locais candidatos a homologacao. Nao foram importados no Sankhya.

- Metadados_AD_GENAPICTRL_corrigido.zip: nova tabela, 14 campos, PK IDINTGENESIS.
- Metadados_AD_GENREQLOG_corrigido.zip: nova tabela, 22 campos, PK textual IDREQ.
- Cada ZIP possui somente metadata.xml na raiz, ISO-8859-1.
- Referencia: exportacao AD_GENESTCTRL Sankhya 4.36b159, sequenceType=M.
- CLOB dataType=C comprovado em exportacao local AD_GENMOVEST.
- size usa a convencao ja existente nos metadados da base instalada.
- Sem conexao interna, credencial, eventos, scripts ou relacionamentos.
- Bloco dbMetadata obrigatorio preservado, com dbUser/urlConnection vazios;
  driver e DBMS mantidos da referencia. Versoes anteriores sem esse bloco foram
  rejeitadas pelo importador e nao devem ser utilizadas.
- Nao altera AD_GENESTCTRL nem AD_GENMOVEST. Sem CREATE TABLE ou DML.

## Antes de importar

Pausar os quatro jobs. Fazer backup restauravel. Conferir se as duas tabelas
novas ja existem: se existirem, nao importar como criacao nem aceitar substituicao.
Importar cada ZIP separadamente pelo mecanismo de metadados do Construtor,
primeiro em homologacao. Nao importar os XMLs avulsos se o importador espera ZIP.

Conferir no Dicionario tipos, tamanhos, PK, nulabilidade e sequencia manual.
Campos I com 19 digitos e textos S com 1000 caracteres precisam de validacao
fisica apos importacao. Nao ha exportacao original com indice unico secundario:
configurar a unicidade de CHAVE_MOVIMENTO em AD_GENAPICTRL pelo mecanismo
suportado no sistema, sem inventar XML de indice.

Conferir CLOBs: MENSAGEM_ERRO, REQUISICAO, RESPOSTA, DETALHE.
Usar sql/genesis/rastreabilidade/01_validar.sql para verificacao somente leitura.

## Pendente: AD_GENMOVEST

Adicionar opcionalmente (sem obrigatoriedade/default):

| Campo | Tipo | Tamanho |
|---|---|---|
| ETAPA_ATUAL | Texto S | 50 |
| ENTIDADE_BLOQ | Texto S | 30 |
| ULT_OPERACAO | Texto S | 10 |
| PROX_TENTATIVA | Data e hora H | - |

Nao foi gerado ZIP parcial de atualizacao: a referencia recebida e uma criacao
de outra tabela, e nao comprova merge de campos. Para preparar a alteracao com
preservacao dos existentes, solicitar exportacao atual da AD_GENMOVEST e
validar o comportamento de atualizacao do importador em homologacao.

Somente depois desses quatro campos, unicidade, validacao e migracao controlada
dos registros API|, instalar o JAR de rastreabilidade. O roteiro completo esta
em docs/genesis/rastreabilidade-controles.md.

## Rollback

Se a importacao falhar, nao repetir cegamente: conferir o que foi criado.
Manter jobs pausados e binarios anteriores. Preservar exportacoes e dados;
nao excluir tabelas preenchidas. Reversao de metadados somente com procedimento
restauravel homologado/administrador, pois importer pode ter aplicado DDL.
