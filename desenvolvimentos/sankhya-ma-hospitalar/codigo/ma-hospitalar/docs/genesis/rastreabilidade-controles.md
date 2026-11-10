# Controles e rastreabilidade Genesis

## Responsabilidades

- AD_GENMOVEST: estado atual dos movimentos de nota/equipamentos.
- AD_GENAPICTRL: um controle por chave tecnica de cadastro ou reconsulta.
- AD_GENESTCTRL: estado de estoques e precos.
- AD_GENEXCFILA: cancelamentos e inativacoes.
- AD_GENREQLOG: uma linha por chamada HTTP, preservada entre tentativas.
- Base instalada IDBASE=1: cadastros e vinculos conhecidos, nao historico.

Nenhum FQCN de agendamento foi alterado. Nao cadastrar novos botoes manuais.
O cliente HTTP comum dos quatro jobs registra a tentativa antes do HTTP em
transacao autonoma. Se nao conseguir registrar, nao faz a chamada. Finaliza a
propria tentativa depois do HTTP. Se cair entre envio e resposta, DH_FIM fica
vazio: nao significa que a API nao recebeu. Nao autoriza repetir POST incerto.

HTTP, identidade e persistencia sao dimensoes separadas. VALIDADA so e gravada
apos comparacao real de identidade. POST que so retorna ID permanece PENDENTE
na identidade, mesmo com persistencia CONFIRMADA. CONFIRMADA e atualizada na
mesma transacao dos dados locais; rollback mantem PENDENTE. PENDENTE tambem
pode significar chamada consultiva sem aplicacao local; nao implica falha.

Dados pessoais e segredos sao mascarados. Nao se registra header token nem URL
completa. Corpos sao limitados a 65536 caracteres com marcador de truncamento.
Dashboard apresenta previa de 4000; CLOB preserva o restante sanitizado.
Campos alterados refletem comparacao antes/depois do UPDATE dos atributos
remotos, nao o INSERT inicial nem alteracoes de vinculo realizadas antes dele.

## Cadastro manual no Construtor

Criar AD_GENAPICTRL (instancia com mesmo nome, descricao Controle de Cadastros
Genesis), sem ligacao com TGFCAB, sem eventos copiados e sem numeracao automatica.
O codigo gera ID sob lock da central. Apenas IDINTGENESIS e chave primaria;
CHAVE_MOVIMENTO precisa indice unico (configurar pelo mecanismo suportado).

| Campo | Tipo / tamanho | Obrigatorio |
|---|---|---|
| IDINTGENESIS | Numero inteiro, 19 digitos, PK | Sim |
| CHAVE_MOVIMENTO | Texto 200, unico | Sim |
| TIPO_MOVIMENTO | Texto 100 | Sim |
| STATUS_INTEGRACAO | Texto 30 | Sim |
| TENTATIVAS | Numero inteiro | Sim |
| HTTP_STATUS | Numero inteiro | Nao |
| MENSAGEM_ERRO | Texto longo / CLOB | Nao |
| PAYLOAD_HASH | Texto 64 | Nao |
| PROTOCOLO_GENESIS | Texto 100 | Nao |
| ORIGEM_ITEM | Texto 30 | Nao |
| DH_INCLUSAO | Data e hora | Sim |
| DH_ATUALIZACAO | Data e hora | Sim |
| DH_ENVIO | Data e hora | Nao |
| NUNOTA | Numero inteiro, sem FK | Nao |

Adicionar na AD_GENMOVEST, opcionais e sem valor obrigatorio/default:
ETAPA_ATUAL Texto 50; ENTIDADE_BLOQ Texto 30; ULT_OPERACAO Texto 10;
PROX_TENTATIVA Data e hora. Proxima tentativa e estimativa de backoff:
cooldown global, prioridades e agendamento podem postergar o processamento.

Criar AD_GENREQLOG (instancia com mesmo nome, descricao Historico HTTP Genesis),
sem FK que permita apagar evidencia em cascata e sem numeracao automatica:

| Campo | Tipo / tamanho |
|---|---|
| IDREQ | Texto 36, PK obrigatoria |
| EXECUCAO | Texto 36, obrigatorio |
| FLUXO | Texto 50, obrigatorio |
| CONTEXTO | Texto 1000 |
| CHAVE_CONTROLE | Texto 200 |
| CHAVE_MOVIMENTO | Texto 200 |
| ENTIDADE | Texto 50 |
| IDCONSULTADO | Texto 100 |
| METODO | Texto 10, obrigatorio |
| ENDPOINT | Texto 1000, obrigatorio |
| DH_INICIO | Data e hora, obrigatorio |
| DH_FIM | Data e hora |
| DURACAO_MS | Numero inteiro, 19 digitos |
| HTTP_STATUS | Numero inteiro |
| CLASSIFICACAO | Texto 30 |
| IDRETORNO | Texto 100 |
| REQUISICAO | Texto longo / CLOB |
| RESPOSTA | Texto longo / CLOB |
| IDENTIDADE | Texto 20, obrigatorio |
| PERSISTENCIA | Texto 20, obrigatorio |
| DH_PERSISTENCIA | Data e hora |
| DETALHE | Texto longo / CLOB |

Prever indices em EXECUCAO, (FLUXO,DH_INICIO), (HTTP_STATUS,DH_FIM),
TIPO_MOVIMENTO/DH_ATUALIZACAO no controle. Autorizar visualizacao apenas ao
grupo de suporte da integracao. Aprovacao de retencao/expurgo com cliente ainda
pendente: nao ha exclusao automatica de historico neste pacote.

## Deploy e migracao

1. Pausar os quatro jobs e preservar JAR, JSP e exportacao dos controles atuais.
2. Cadastrar as tabelas/campos no Construtor. Validar nomes, tipos e tamanhos
   com sql/genesis/rastreabilidade/01_validar.sql. Nao usar CREATE TABLE.
3. Revisar duplicidades tecnicas. Executar 02_migrar_controles.sql em transacao
   manual. Script bloqueia duplicados, copia chaves API| com IDs e protocolos
   originais e nao apaga nem altera AD_GENMOVEST. Conferir contagens antes de COMMIT.
4. Substituir JAR e JSP ZIP. Manter transacao automatica OFF no orquestrador,
   reconsulta e estoque; codigo gerencia as fases. Inativacao preserva seu contrato.
5. Validar um registro por fluxo. Novo controle e historico sao obrigatorios:
   tabela ausente bloqueia processamento; nao existe fallback silencioso.
6. Retomar jobs gradualmente, conferir registros de evidencia e dados de negocio.

Nao executar a migracao repetidamente depois do cutover: legado fica congelado.
Sem limpeza em massa: registros API| antigos ficam excluidos das visoes de
movimentos, mas continuam disponiveis para auditoria/rollback.

## Aceite de homologacao

- GET 200 de reconsulta nao aumenta enviados de equipamentos.
- POST 201 seguido de falha local mantem evidencia HTTP, sem CONFIRMADA local.
- GET com identidade divergente nao marca VALIDADA nem altera base.
- Refresh sem alteracoes mostra campos alterados=[] e consulta confirmada.
- Refresh alterna entidades pela ultima tentativa da entidade; janela 24h,
  um registro por rodada e backoff. Nao garante cobertura da base em 24h.
- Estoque seleciona um produto por rodada; pode precisar GET e POST/PUT/DELETE
  para esse produto. Nao significa uma unica requisicao HTTP.
- Erro de estoque cabe em MENSAGEM 100 BYTE; detalhe fica no historico.
- Sucesso local de estoque e confirmado em transacao autonoma por produto.
- Fila de cancelamento e protocolos antigos preservados.
- Token, documento, email, telefone e endereco mascarados no historico.
- Nenhum job depende de botao manual; tentativas continuam sem limite.

## Rollback

Pausar jobs, exportar ambas as tabelas novas e reconciliar protocolos criados
apos o cutover antes de restaurar JAR/JSP anteriores. Restaurar apenas o binario
pode perder a protecao de POSTs ja feitos. Copia reversa de controles exige
comparacao por CHAVE_MOVIMENTO e nao pode sobrescrever ENVIADO/CANCELADO nem
protocolos diferentes. Nao apagar tabelas/campos ou registros novos.

## Limites desta entrega

SQL Oracle/Jape e JSP requerem teste no Sankhya; testes locais nao confirmam
comportamento transacional real. Nao ha reconstrucao dos corpos antigos.
Recuperacao automatica de cliente 404 por documento nao foi adicionada: API
nao aceita documento isolado. Retencao, frontend de corpos acima de 4000 e
revisao de politica de repeticao de POST incerto do catalogo exigem homologacao.
