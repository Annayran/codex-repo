# Correcoes de protecao e acompanhamento - 03/11/2026

## Decisoes do solicitante

- Situacao vazia bloqueia envio para correcao cadastral, com motivo ao usuario.
- Equipamento inativo deve ser reativado quando reaparecer em nota elegivel.
  Operacao/payload ainda dependem da collection e contrato a enviar. Nao assumir
  que PUT /estoques altera o equipamento de /itens-materiais.
- Empresas e locais ainda nao definidos. A rotina de catalogo atual consulta
  saldo disponivel(estoque-reserva) dessas posicoes e envia a quantidade; nao
  configurar todas as empresas/locais automaticamente.

## Implementado neste lote local

1. Situacao obrigatoria antes de preparar dependencias e construir item.
   Ausencia ou codigo sem descricao reconhecida gera SITUACAO_ORIGEM_AUSENTE.
2. Candidato carrega ID de um unico dominio SITUACAO da base1,ativo,conciliado
   e correspondente ao codigo da serie. Zero ou multiplas correspondencias
   bloqueiam com SITUACAO_NAO_CONCILIADA. Nenhum ID ou valor padrao presumido.
3. Payload separa status de cadastro(Ativo) da situacao operacional e envia ID
   conciliado. Isso nao reativa cadastros existentes:nao foi adicionado PUT.
4. GET de equipamento nao conclui se status nao confirma Ativo, situacao esta
   incompleta ou ID de situacao diverge da origem conciliada.
5. POST com ID confirmado preserva ID/origem no controleAPI e movimento antes
   do GET de verificacao,sem marcarENVIADO nem alterarHTTP para2xxno movimento.
   Isso permite retomada sem novoPOST e preserva identidade para analisar o
   cancelamento. Nota ja cancelada usa fila de retorno tardio,sob mesmo lock.
   Limite: sem registro local do item, o job ainda bloqueia DELETE por sua
   verificacao de AD_GENITEMMAT. Esse caso permanece em revisao; nao se relaxou
   a protecao para excluir um cadastro remoto apenas pelo ID do POST.
6. React mostra presenca na consulta de origem e contagem global dos controles
   pendentes/revisao fora dela. Mantem indicadores historicos e nao muda estados.
   Presenca na view nao promete elegibilidade: filtros adicionais ainda existem.
7. Diagnostico diferencia mensagem historica de PENDENTE/proximaetapa de um
   bloqueio atual; acrescenta orientacao amigavel de situacao e estado remoto.
   Evidencia original preservada.

## Validacao

Baseline:186testesJavaunitarios aprovados. Novos cenarios reproduziram4falhas
na versao anterior. Testes cobrem origem semsituacao,dominioausente,IDnaocriado
porpadrao,equipamentoinativo,remotosemsituacao,situacaodivergente,POSTconfirmado
comGETfalho/retomadasemnovoPOST,IDcriadoopreservadosemconcluir/HTTPalterado,
contrato de transacao/cancelamentotardio e consulta de dominio unico.
Suite Java unitaria final: 199/199, tambem executada contra o JAR entregue
como primeira fonte do classpath, sem diretorio de classes de producao.
23checksJSdiagnostico aprovados. Reactbuild ePlaywright aprovados comfixtures:
15SELECTs,somenteleitura,filtros,temas,mobile,XSS,foraorigem,mensagemhistorica,
zero requisicoes externas. CompilacaoJSP/Oracle e servicosJape reais nao testados.
8testesHTTPloopback tentados e bloqueados na inicializacao peloJava/windows:
Unable to establish loopback connection/Invalid argument:connect.
Nenhum teste real comGenesis,nenhuma credencial solicitada no codigo.
Revisao independente: corrigida nomenclatura da consulta de origem; preservado
ID no movimento apos POST. Permanece a limitacao segura do cancelamento sem
item local, documentada acima; nao apresentar como fluxo integral homologado.

## Artefatos candidatos (nao instalar sem homologacao)

- output/correcao-protecao-20261103/MAHospitalar-Protecao-Situacao-Candidato.jar
- output/correcao-protecao-20261103/MAHospitalar-Genesis-React-Protecao-Candidato.zip
- output/correcao-protecao-20261103/preflight-somente-consulta.sql
- Geracao final: build/homologacao-20261103-135913.
- SHA256 JAR: D6F108389282CD734B9555572DC39B5AA275A9A1C65356F429E5060C08C21AB1.
- SHA256 React ZIP: E29C087C6CB44707B8BBB09D2F8FBD914C844F9E288A35599E03D3C8D87B4D54.

## Implantacao nao autorizada/executada

Preparar backup da biblioteca e ZIP do painel. Executar somenteSELECT do
preflight-correcao-situacao-20261103.sql e corrigir os cadastros necessarios por
responsavel autorizado. Com dominios todos pendentes e situacoes de origem
vazias,este candidato bloqueara os envios:isso e protecao aprovada,nao deve ser
contornado por defaultOperacional. Nao recomendar troca cega emproducao.
Homologar JAR+React com massa controlada,confirmacao remota e aceite.
Rollback:restaurar artefatos anteriores;nao apagar controlesnemdesfazer dados
remotosautomaticamente. Sem DDLnovo,sem DMLcorretivo ou migracao de dados.

## Ainda pendente

Collectionanonimizada econtrato:itemexistenteinativo,PUTreativacao eGETconfirmacao;
regra/codigosdominio;empresas/locais. CPF/CNPJjaexistente,ambiguidades de
material/equipamento e migracoes demodelo exigem conciliacao identificada,
nao foram resolvidos arbitrariamente neste lote. Os23fora da origem precisam
deauditoria de series;nenhum foi marcadoENVIADO/CANCELADO para baixarindicador.
Evolucao de clientes16h permanece levantamento,nao implementada aqui.

## Complemento: collection e reativacao (03/11/2026)

Collection fornecida: Genesis - Api.postman_collection.json, SHA256
ac257633c012fa2c3ff87cb95153ac3912a93b46feed27c832a67f6f90a87206.
Somente o contrato relevante foi consultado; collection e credenciais nao foram
importadas. Reativacao de equipamento pertence a PUT /api/v1/itens-materiais/{id},
nao ao recurso de estoques. O exemplo de PUT retorna 200 com objeto vazio;
cliente, setor e modelo sao descritos como obrigatorios. Valores ilustrativos
na collection nao confirmam enums nem comportamento do servidor real.

Implementado candidato GET/PUT/GET no mesmo ID, preservando atributos remotos,
sem POST, sem exclusao e sem transferencia automatica. O usuario confirmou:
cliente ou setor divergente deve bloquear e explicar o motivo. A mensagem
tambem identifica divergencia de modelo ou numero de serie em linguagem de
usuario, e o painel orienta conciliacao. Situacao ausente continua bloqueada.

Validacao local final: 210 testes Java aprovados e 24 checks de diagnostico.
Build: build/homologacao-20261103-141356. Build React aprovado. Oito testes
HTTP loopback permanecem excluidos por limitacao Java/Windows ja registrada.
Nao houve chamada real, DML, publicacao, implantacao, commit ou push.

Ressalva impeditiva para recomendar este candidato em producao: a revisao
independente identificou que uma divergencia de preservacao apos PUT pode
ser ignorada na rodada seguinte quando o cadastro ja estiver ativo. E preciso
persistir uma pendencia de conciliacao que impeca essa confirmacao automatica,
com teste de retomada entre rodadas. O candidato de reativacao ainda nao esta
liberado. Timeout do PUT e concorrencia remota tambem exigem homologacao.

Permanecem pendentes: codigos aprovados de empresas/locais, conciliacao dos
dominios, teste real de reativacao e tratamento do cancelamento sem item local.
A collection descreve custo como obrigatorio no estoque; como a regra local
aprovada omite custo, nao houve alteracao silenciosa desse contrato.
