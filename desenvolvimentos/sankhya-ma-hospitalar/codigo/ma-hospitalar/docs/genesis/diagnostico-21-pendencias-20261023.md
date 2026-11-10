# Diagnostico dos 21 movimentos em revisao

Demanda: DEM-b5f5adb804fe6bd8. Projeto MA Hospitalar / Genesis.
Data da verificacao: 2026-10-23. Fonte: print enviado pelo solicitante e SELECTs
executados pelo coordenador na sessao Sankhya, via DBExplorer. Nao foram lidos
tokens nem exportados corpos HTTP, documentos, contatos ou nomes de clientes.
Codigo e alteracoes locais anteriores, inclusive M1/M3, preservados.

## Coorte confirmada no banco

Ultima linha por chave de movimento, ordenada por atualizacao e ID, excluindo
controles API; depois filtrar ERRO, REVISAO e ENVIO_INCERTO. A mesma regra do
resumo do painel, sem confundir com PENDENTE/REPROCESSAR nem contar historico.

| Causa | Movimentos | Contexto |
| --- | ---: | --- |
| Codigo de modelo divergente | 16 | 15 series do produto 14862/nota 114375; uma do produto 16072/nota 117992 |
| Material com multiplos vinculos | 3 | Produto 13482; duas series na nota 120187 e uma na 121390 |
| Item/serie ambiguo | 1 | Produto 12230, serie 23343174, nota 118405 |
| Vinculo local conflitante | 1 | Movimento 497, nota 117392, item 2, produto 14074; registro antigo |
| Total | 21 | Todos em REVISAO na consulta de detalhe |

Vinte chaves constam na view atual. O movimento 497 nao consta: a serie antiga
83.60.360101_0451 nao aparece mais no item 2 da nota. As 17 series presentes
nesse item sao outras; o historico consolidado mostra 17 ENVIADO e uma REVISAO
antiga. Isso nao prova por que a serie mudou nem autoriza encerrar o antigo.

## 1. Dezesseis bloqueios, dois modelos

| Produto | Registro local | ID modelo | Codigo local | Destino HSP |
| --- | ---: | ---: | --- | --- |
| 14862 | 6104 | 6156 | SNK-MOD-14862 | MC-EP90IMB |
| 16072 | 6105 | 6157 | SNK-MOD-16072 | E-SCAIOL-K |

Preflight local: para cada um, uma fonte HSP, um vinculo por produto, um vinculo
por ID e zero associacoes do destino a outro produto. Controle normal MODELO
permanece API_SINCRONIZADO com HTTP 201 e IDs 6156/6157. Historico mostra os
POSTs originais em 13/10 e 15/10, persistencia CONFIRMADA e flag de identidade
legada PENDENTE; GETs da mesma epoca retornaram 200. Nao usar isso como prova de
identidade atual. Nenhum controle MIGRA_MODELO foi localizado na consulta inicial.

O codigo atual bloqueia codigo local diferente de HSP antes de continuar o
item. A acao dedicada consulta o ID existente e o destino, valida conflitos e
executa PUT apenas quando necessario; confirma por GET e so entao persiste o
codigo local e invalida o estado sincronizado do controle normal. Se o remoto
ja estiver correto, reconcilia sem outro PUT. Nenhuma etapa autoriza POST.

O codigo remoto atual nao foi consultado diretamente pelo agente. Portanto,
nao afirmar que continua SNK-MOD remoto nem que PUT ja ocorreu com sucesso.

## 2. Tres bloqueios de material nao sao aliases identicos

| Registro | ID remoto | Codigo local | Grupo local | Descricao local | Conciliacao |
| --- | ---: | --- | --- | --- | --- |
| 233 | 233 | E110 | vazio | E110SONDAS GMI | PENDENTE |
| 309 | 233 | SNK-MAT-10802 | 10802 | BOMBA ENTERAL | CONCILIADO |

Modelo 575, produto 13482, aponta para material 233. Os registros compartilham
ID remoto, mas discordam em codigo, descricao, grupo e conciliacao. Nao escolher
um por ID maior/menor, data ou status. Nao excluir duplicata nem trocar material
do modelo sem confirmar GET do material 233 e GET do modelo 575, inventariar
todos os modelos dependentes, salvar estado anterior e obter plano aprovado.
Se o cadastro remoto resolver a identidade sem conflito funcional, conciliacao
e tecnica. Se categoria/modelo estiver incorreto ou compartilhamento for
intencional, Luiz deve confirmar qual material deve atender o produto 13482.

## 3. Uma serie, quatro cadastros remotos e outro cliente na nota

| Registro local | ID item | ID cliente | ID modelo | ID setor |
| --- | ---: | ---: | ---: | ---: |
| 9011 | 56087 | 9700 | 2507 | 9808 |
| 13880 | 51218 | 6389 | 2507 | 6491 |
| 22970 | 42125 | 5857 | 2507 | 5959 |
| 24530 | 40565 | 5384 | 2507 | 5381 |

Todos usam codigo CON-2450, produto 12230 e serie 23343174; estao PENDENTE na
conciliacao. A nota 118405 usa parceiro Sankhya 2464, conciliado ao cliente
Genesis 2368; nenhum dos quatro itens aponta para esse cliente. O modelo
esperado e 2507. Nao existe candidato unico pelo cliente da nota.

Pergunta objetiva a Luiz: esses quatro registros representam historico de
transferencias do mesmo equipamento, duplicacoes ou reutilizacao de serie?
Qual ID representa o equipamento da nota 118405? Confirmar no remoto e obter
orientacao para transferencia/regularizacao antes de mudar cliente, selecionar
ID ou criar outro item. Nao trocar proprietario automaticamente.

## 4. Um conflito antigo fora da selecao atual

Movimento 497: chave 2|117392|2|14074|83.60.360101_0451; ultima alteracao observada
15/10/2026 15:13; etapa RESOLVER_CADASTRO, entidade MATERIAL, operacao GET. Codigo
de erro antigo nao informa a chave do cadastro conflitante. Modelo local 4779,
ID remoto 4780, material 22. Material 22 tem grupo local 0; produto tem grupo
10102. Codigo atual ja preserva o material herdado do modelo sem substituir
grupo conciliado, mas isso nao encerra automaticamente um movimento historico.

Na nota atual: tipo M1, operacao 1101 configurada, 17 outras series enviadas.
Nao atribuir o caso a M3. Perguntar se a serie antiga foi corrigida/substituida.
Se confirmado, tratar fechamento administrativo rastreavel separado de envio,
sem marcar ENVIADO indevidamente, apagar historico ou reconstruir serie antiga.

## Intervencao operacional autorizada

Na tela Acoes Agendadas foi encontrada a acao 6:
`[ZAYA.IT] - Atualiza Cod. Modelo Equipamentos`, classe
`br.com.zaya.mahospitalar.genesis.schedule.MigracaoCodigoModeloGenesisScheduleAction`.
Ativa, intervalo min:5, modulo 11. Transacao automatica estava marcada, em
desacordo com o procedimento da classe, que utiliza transacoes proprias Jape.

O usuario autorizou expressamente desmarcar essa opcao e acompanhar GET/PUT
dos modelos existentes, sem POST, exclusoes ou alteracao de vinculos ambiguos.
O coordenador desmarcou e salvou via UI; tela apos salvar confirmou opcao 0,
acao ativa mantida e intervalo inalterado. Nenhuma outra configuracao alterada.
Isso e correcao de configuracao, nao prova conclusiva da causa de ausencia de
migracao. O acompanhamento seguinte identificou o bloqueio tecnico abaixo.

### Causa tecnica confirmada no primeiro ciclo acompanhado

Em Acoes Agendadas > Acoes ativas, o job 6 tinha duas execucoes registradas;
ultima em 23/10/2026 11:00:01, status error, duracao 241 ms. Log do fluxo
MIGRACAO_MODELO_HSP: produto 13847/IDMODELO 6155; SQLSyntaxErrorException,
ORA-00904: HASH_PAYLOAD identificador invalido. Resumo http=0, requisicoes=0.
Nenhum GET/PUT da migracao foi executado nesse ciclo. Consulta as 10:57:50 e
11:01:19 mostrou os codigos locais ainda SNK-MOD e nenhum controle MIGRA.
Selecao do job retornava tres candidatos; cooldown 429 ativo retornou zero.

USER_TAB_COLUMNS confirmou AD_GENAPICTRL.PAYLOAD_HASH VARCHAR2(100), nullable;
HASH_PAYLOAD nao existe. O XML de metadados do projeto tambem define PAYLOAD_HASH.
O repositorio de migracao usava o nome invertido em cinco comandos de reserva,
validacao do lease, invalidacao do controle normal, confirmacao e falha.
Portanto existe erro no codigo, nao necessidade de criar coluna nova no banco.
Correcao local implementada: usar PAYLOAD_HASH em todas essas referencias,
mantendo UUID, prazo, CAS e controles separados por chave. Suite completa
do runner -UnitOnly: 186/186 testes aprovados; seis testes direcionados tambem
aprovados pelo worker. Revisao independente final aprovada na delta. Teste
novo confere as cinco referencias e o nome/tipo/capacidade do metadado existente.
Preflight textual: sql/genesis/modelo-hsp/02_validar_coluna_lease.sql.

Desmarcar transacao automatica nao corrigiu esse erro SQL; ambos os fatos
ficam registrados separadamente. A biblioteca instalada ainda precisa receber
o hotfix homologado para prosseguir. Nenhum deploy de JAR foi autorizado ou
executado nesta intervencao de configuracao; M1/M3 permanece sem deploy.

Candidato de build local completo: build/homologacao-20261023-110752.
JAR SHA256: 9DE476B2FC647225B9C795B92214523DE882025558026CAD09CB3A6CF1427D93.
ATENCAO: esse build utiliza o working tree completo, incluindo a melhoria M1/M3
ainda nao implantada. Nao e pacote hotfix isolado para producao. Para corrigir
primeiro a migracao, preparar entrega separada sobre a versao instalada validada,
com backup e autorizacao especifica; nao ampliar M3 por efeito colateral.

SELECT equivalente a selecao do job retornou tres candidatos, nesta ordem:
produto 13847/registro 6103/ID 6155, SNK-MOD-13847 -> MAS-9515IMB;
produto 14862/ID 6156 e produto 16072/ID 6157. Um modelo e tratado por execucao.
O primeiro nao explica os 21 erros mostrados, mas integra o escopo da rotina
ja cadastrada. Nao houve ampliacao ou restricao manual da selecao do job.

Nao executar manualmente POST, limpar IDs/controles nem repetir PUT fora da
rotina validada. O job pode tratar outros modelos legados elegiveis: inventariar
e monitorar antes de considerar o saneamento encerrado. Falha exige log da
execucao MIGRACAO_MODELO_HSP e verificacao da biblioteca instalada/configuracao,
sem exportar credenciais. Restaurar JAR/configuracao nao reverte PUT remoto;
qualquer reversao de dados exige GET, conflitos, backup e autorizacao por ID.

## Evidencia, limites e proxima acao

SQL de consulta: `sql/genesis/diagnostico-21-pendencias-20261023.sql`, sete
SELECTs independentes. Consultas equivalentes focadas foram executadas com
sucesso no Oracle via UI para contagem, detalhes, modelos, materiais, itens,
unicidade local e series atuais. O arquivo completo requer validacao propria;
nao confundir consultas focadas executadas com execucao de todos os SELECTs.
Nao foram executados DML manual, POST, exclusoes ou deploy da melhoria M1/M3.
Nao foram acessados tokens nem dados pessoais nao necessarios ao diagnostico.

Proximo passo: conferir controles e codigos apos execucao do job corrigido;
o motivo exato foi identificado e corrigido localmente. Agora falta homologar e
autorizar a implantacao isolada do hotfix do JAR, antes de outra tentativa.
Depois conferir os 16 movimentos sem liberar reservas de envio incerto.
Os materiais e o item exigem GET remoto e as decisoes pontuais acima; o caso
antigo exige confirmar mudanca de serie antes de qualquer fechamento.
Manter a melhoria M1/M3 sem deploy ate esta revisao operacional.

## Perguntas objetivas para Luiz

- Produto 13482: o material esperado e a categoria de bomba enteral? O modelo
  575 aponta ao ID 233, cujos mapas locais divergem entre sondas e bomba enteral.
  Confirmar a identidade remota antes de escolher/consolidar um mapa.
- Nota 118405/produto 12230/serie 23343174: qual dos quatro IDs corresponde ao
  equipamento real? Ha historico de transferencias ou duplicacao de cadastro?
- Nota 117392/item 2: a serie antiga 83.60.360101_0451 foi corrigida/substituida?
  As 17 series atualmente presentes ja constam como ENVIADO. Confirmar antes
  de propor fechamento rastreavel do movimento antigo, sem falso ENVIADO.

O erro de coluna e operacional/tecnico e nao exige decisao de negocio do cliente.
As perguntas acima sao somente sobre identidades/historico sem evidencia unica.
