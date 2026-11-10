# Arquitetura - Integracao Genesis

> Evolucao local de 13/10/2026: [rastreabilidade-controles.md](rastreabilidade-controles.md).
> Controles API/reconsulta passam a AD_GENAPICTRL; historico HTTP em AD_GENREQLOG.
> Depende de cadastro manual e migracao antes de instalar o novo pacote.

> Referencia vigente em 09/10/2026: [base-unica-post-get.md](base-unica-post-get.md).
> O novo runner usa IDBASE=1, prepara cadastros por fase e nao faz PUT de
> cadastros/equipamentos. As secoes anteriores abaixo sao historico.

> Revisao 08/10/2026. As secoes abaixo substituem a descricao historica ao final.
> Representam o codigo local, nao comprovam o JAR instalado ou jobs ativos.

## Referencia atual

### Dependencias compartilhadas por lote

GenesisSyncService reutiliza o resultado ou a falha de cada cadastro com a mesma
entidade, chave local, codigo externo e payload durante uma execucao. Equipamentos
distintos continuam sendo processados individualmente. A proxima execucao cria
um novo servico e permite nova tentativa. A primeira reconciliacao pode consultar
codigo externo, ID e documento; essas etapas nao se repetem para cada equipamento
que dependa do mesmo cadastro. HTTP 500 continua sem autorizar POST.

### Reprocessamento automatico sem limite de tentativas

Os tres jobs (equipamentos, inativacao e catalogo) reavaliam falhas sem teto de
tentativas. O contador permanece diagnostico; o parametro legado de limite nao
encerra mais registros. ERRO e REVISAO entram novamente quando a espera vence.
Mantem-se espera progressiva de 2 a 60 minutos e cooldown compartilhado da API.
Envios ENVIADO/CANCELADO ficam protegidos, e notas canceladas nao voltam ao envio.
Na EXCFILA, cada DELETE exige cancelamento confirmado, origem CRIADO e identidade
remota coerente com o controle. Pendencias de identidade sao reavaliadas sem
forcar exclusao. O catalogo acompanha o produto, independentemente de notas.
Nao e necessario cadastrar botoes manuais para retomar estas filas.

### Implementacao incremental em 08/10/2026

Catalogo implementado localmente: EstoqueGenesisScheduleAction,
AcaoReprocessarEstoqueGenesis, DTO/servico proprio, preco vigente tabela 101 via
helper e custo medio com contexto unico. Controle AD_GENESTCTRL ainda deve ser
cadastrado pelo Construtor. O dashboard mostra secao separada e paginada.
Consulte catalogo-estoques.md antes de ativar; nao houve deploy remoto.

Revisao de compatibilidade: pacote anterior 135238 substituido; reprocessamento
corrigido no botao real Registro.setCampo/save, nao apenas no repository.
Aviso de cooldown integrado ao monitor JSP existente; fallback de pacote usa
layout java/webapp/docs/sql. 70 testes locais aprovados. Oracle/JSP continuam
pendentes de homologacao; consultar homologacao-consolidada.md antes de deploy.

Atualizacao posterior: cooldown persistido nos controles existentes, minimo
60 segundos e prazo ampliado por Retry-After/mensagem Genesis. Envio e EXCFILA
consultam o bloqueio antes de iniciar; dashboard JSP exibe o fim da espera.
Pacote candidato gerado em build/homologacao-20261008-135238, com compilacao
Java 8 e 67 testes aprovados. Oito testes HTTP bloqueados por loopback local.
Catalogo de estoques/precos continua pendente de contexto comercial e controle
proprio. Consulte homologacao-consolidada.md para limites, aceite e rollback.
Os itens abaixo documentam a primeira etapa, anterior a essa atualizacao.

Primeira etapa aplicada somente no codigo local:
- A gravacao generica de resultado nao altera ENVIADO nem CANCELADO.
- Consulta alternativa com erro 400/405/429/500 nao autoriza criacao por POST.
- Item/material/modelo com ID local e ausencia por codigo externo exige revisao,
  sem recriacao automatica. Nao foi implementada reconciliacao automatica por ID
  para essas entidades nesta etapa.
- Execucao de envio e job EXCFILA interrompem o lote no primeiro HTTP 429.
  Isso nao substitui cooldown persistente compartilhado entre execucoes/jobs.
- Acrescentados testes de regressao para essas protecoes.

Validacao: testes Gradle tentados, mas nao executados por falha de conexao
loopback do Gradle, inclusive sem daemon. JAR nao gerado nem publicado.
Campos adicionais, historico de tentativas, cooldown persistente, checkpoints
transacionais e alteracoes do dashboard permanecem pendentes. Nenhum DDL/DML
remoto foi executado. Para deploy futuro, manter JAR anterior como rollback e
homologar os criterios da etapa antes de ativar.

Separar quatro responsabilidades: cadastros compartilhados, movimentos de
equipamentos, cancelamento de notas e catalogo de estoques. Base instalada e
dashboard sao consultas, nao prova isolada de envio confirmado.

| Fluxo | Fontes/controles | Situacao |
| --- | --- | --- |
| Configuracao | AD_CENTPARAM, AD_CENTPARAMTOP | Implementado localmente |
| Equipamentos | AD_VW_GENESIS_ESTOQUE, TGFPRO, AD_GENMOVEST | M1/M3 com serie; HSP_TIPOPRODUTO prioritario, AD somente quando vazio |
| Cadastros | AD_GENCLIENTE, AD_GENSETOR, AD_GENMATERIAL, AD_GENEMPRESA, AD_GENMODELO | Consulta, conciliacao, criacao ou atualizacao de dependencias |
| Base instalada | AD_GENBASE, AD_GENITEMMAT e cadastros relacionados | Base importada e resultados da sincronizacao; nao substituir o controle de movimento |
| Cancelamento | TGFCAB, TGFCAN, AD_GENEXCFILA | Eventos e job locais; cobertura real a homologar |
| Estoques | TGFPRO, TGFIPI, helper de preco/custo | Demanda mapeada, nao implementada neste levantamento |

### Cadastro e envio

```text
Parametros/TOPs -> view e tipo efetivo M1/M3 -> candidato -> GENMOVEST
  -> cliente -> setor
  -> modelo existente OU material -> fabricante -> modelo
  -> item-material na API
  -> GENITEMMAT + origem da operacao + GENMOVEST -> dashboard
```

O servico consulta primeiro por codigo externo. Encontrando o cadastro, compara
o payload e reutiliza ou atualiza por PUT. Quando a consulta indica ausencia,
tenta reconciliacao alternativa para determinados cadastros e pode criar por
POST. Nao e um fluxo exclusivamente de POST nem somente de base importada.

IDs de dependencias sao registrados durante o fluxo, mas dentro da transacao
do executor: isso nao equivale a commits independentes por etapa. Sucesso remoto
com falha de persistencia local precisa de reconciliacao antes de novo POST.

Identidades distintas: CHAVE_MOVIMENTO identifica o evento da nota;
codigoExterno apoia reconciliacao; PROTOCOLO_GENESIS guarda o ID remoto do item
no controle; AD_GENITEMMAT.IDGENESIS guarda o ID na base local. Um GET que
reutiliza item pode terminar como ENVIADO sem ter ocorrido POST.

HTTP 200 isolado nao basta: exigir resposta valida, identidade correspondente,
ID utilizavel e persistencia concluida. GET de cliente bem-sucedido nao comprova
que o equipamento inteiro foi enviado.

### Protecoes e lacunas comprovadas

| Evidencia | Consequencia |
| --- | --- |
| Selecao normal exclui ENVIADO/CANCELADO | Protecao existente |
| Reprocessamento manual aceita ERRO/REPROCESSAR e exclui fila/cancelamento | Preservar essa restricao |
| Protocolo e hash usam COALESCE | Falha sem novo valor nao apaga esses campos |
| ORIGEM_ITEM preserva primeira operacao | Nao substituir por ultima operacao: protege item preexistente no cancelamento |
| Gravacao generica bloqueia CANCELADO e ENVIADO | Protecao aplicada localmente; homologacao pendente |
| Consultas de dependencias se repetem por candidato | Avaliar cache por execucao e reconciliacao controlada |
| 429 interrompe o lote | Falta cooldown compartilhado respeitando prazo da API |
| Consulta alternativa com erro bloqueia POST | Ausencia precisa ser classificada como NAO_ENCONTRADO |
| HTTP durante transacao e lock compartilhado | Reavaliar duracao de locks e checkpoints preservando exclusao mutua |

GET_ID_LOCAL 500 nao prova inexistencia nem identidade correta. Deve preservar
o vinculo conhecido e suspender a tentativa, sem criar duplicata. O 429 observado
pede 60 segundos de espera; nao deve provocar novas chamadas imediatas.

### Cancelamento e EXCFILA

```text
Evento local -> identificar vinculos -> registrar fila/evidencia
  -> desvincular NUNOTA e marcar CANCELADO -> commit local
Job EXCFILA -> confirmar cancelamento e identidade -> DELETE autorizado
  -> registrar resultado da fila e inativacao local
```

Eventos: CancelamentoNotaGenesisEvent em TGFCAB.beforeDelete e
RegistroNotaCanceladaGenesisEvent em TGFCAN.beforeInsert. Homologar ordem e
cobertura de cancelamento/estorno; nao assumir que todo estorno passa por eles.

Fila automaticamente PENDENTE exige ENVIADO, protocolo numerico positivo e
ORIGEM_ITEM=CRIADO. Identidade/origem incerta ou item reutilizado exige REVISAO.
Recuperar ID na base local nao autoriza exclusao automatica sem prova de origem.

O job exige TGFCAN com DTCANC e ausencia da nota em TGFCAB, alem de validar
identidade e proteger movimento posterior. Hoje HTTP 200/404 conclui a fila;
homologar semantica de DELETE e 404. NUNOTA_ORIGEM e auditoria sem recriar o
vinculo impeditivo com TGFCAB. Nao apagar fila/historico para resolver erros.

### Padrao proposto, ainda nao implementado

Separar estado do negocio, confirmacao remota e ultima tentativa. Definir campos
no Construtor antes da implementacao; preservar compatibilidade dos existentes.

| Dimensao | Informacao |
| --- | --- |
| Negocio | Movimento vigente/cancelado; intencao de envio ou inativacao |
| Confirmacao | ID, data e hash confirmados; origem inicial preservada |
| Tentativa | Etapa, GET/POST/PUT/DELETE, HTTP, erro, quantidade, proxima tentativa |
| Auditoria | Transicoes e correlacao com entidade e movimento |

1. Falha posterior nao remove evidencia de sucesso, ID ou hash confirmado.
2. ENVIADO nao volta a REPROCESSAR pelo job/botao normal.
3. Atualizacao legitima exige nova intencao/versionamento; nao forcar POST via erro.
4. Timeout apos POST exige reconciliacao antes de repetir criacao.
5. Transitorio tem espera e limite; erro funcional exige correcao.
6. 429 aplica cooldown compartilhado; nao dormir segurando transacao.
7. Inativacao de equipamento passa somente pela EXCFILA e suas salvaguardas.

### Nova demanda: estoques

Catalogo independente M2/M3/SV, mesma prioridade HSP/AD. Fontes TGFPRO e
TGFIPI, relacionamento por CODIPI, enviando PERCENTUAL. Identificar e validar
helper Sankhya de preco/custo informado. Referencia funcional: Protheus GENM070.

POST /api/v1/estoques/, PUT e DELETE /api/v1/estoques/{id}; GET para verificar
e reconciliar. Inativos nao entram inicialmente; previamente enviados precisam
de acompanhamento para inativacao e reativacao sem perder ID. Frequencia 15/30
minutos ainda a definir. Controle proprio e subaba ainda precisam ser feitos.

Nao e saldo fisico nem envio de documentos de orcamento. Nao reutilizar controles
de notas indiscriminadamente. Homologar reativacao, filtros GET, unicidade e DELETE.

### Caminho de evolucao e aceite

| Etapa | Entrega e aceite |
| --- | --- |
| 1. Baseline | Conferir JAR instalado, jobs, eventos e campos; distinguir ambiente de codigo local |
| 2. Protecao | ENVIADO nao regressa em falha; CANCELADO nao reentra no envio |
| 3. Recuperacao | Timeout/retry nao duplica; 429 interrompe chamadas pelo prazo indicado |
| 4. Cancelamento | Rollback nao dispara DELETE; origem incerta vai a REVISAO; movimento posterior protegido |
| 5. Monitoramento | Separar confirmacao de ultima tentativa, etapa, operacao, ID e proxima tentativa |
| 6. Estoques | Homologar criacao, alteracao, inativacao, reativacao e recuperacao independentes |

Dashboard proposto: Envios, Base instalada, Cancelamentos, Cadastros/conciliacao
e Estoques. Base instalada continua paginada no servidor, sem carregar todo o
volume na abertura. Consulta local nao precisa chamar a API.

Mudanca de banco exige campos no Construtor, backup da configuracao, deploy,
validacao e rollback. Testar concorrencia, sucesso remoto/falha local, 429, 500,
identidade divergente, cancelamento durante envio e regressao de base instalada.

Manter aqui fatos, decisoes, evidencias e situacao de deploy a cada etapa.
Fontes: IntegracaoGenesisRunner, GenesisSyncService, MovimentoGenesisRepository,
CandidatoGenesisRepository, CancelamentoGenesisRepository e
InativacaoGenesisScheduleAction. HTML associado: arquitetura.html.

## Descricao historica (nao usar como arquitetura atual)

## Escopo

A ação agendada lê movimentos elegíveis em `AD_VW_GENESIS_ESTOQUE`, usa a
`AD_GENMOVEST` como controle e resolve as dependências na Base Instalada
previamente importada e conciliada. O fluxo não cria cadastros mestres na API.

Somente produtos classificados como equipamentos, com
`TGFPRO.AD_TIPOPRODUTO='M1'`, são elegíveis. Partes e peças (`M2`), acessórios
(`M3`), consumíveis (`M4`), serviços (`SV`), administrativos (`AD`) e produtos
sem classificação são bloqueados antes de qualquer chamada HTTP.

## Fluxo completo

```text
AD_CENTPARAM/AD_CENTPARAMTOP (NUPAR=1)
                 |
AD_VW_GENESIS_ESTOQUE + TGFPRO.AD_IDEXTERNO
                 |
                 v
AD_GENMODELO conciliado -> ID do modelo
                 v
AD_GENCLIENTE conciliado -> ID do cliente
                 v
AD_GENSECTOR conciliado/padrão -> ID do setor
                 v
GET/POST/PUT itens-materiais
                 |
                 v
Atualiza AD_GENITEMMAT, AD_GENMOVEST e dashboard
```

Um item já presente em `AD_GENITEMMAT` é atualizado por `PUT /{IDGENESIS}`. Para
item ainda não importado, a rotina pesquisa pelo `codigoExterno`, cria por `POST`
quando recebe `404` e registra o ID retornado em `AD_GENITEMMAT`.

## Chaves externas

- modelo: `TGFPRO.AD_IDEXTERNO` corresponde a
  `AD_GENMODELO.CODIGOEXTERNO`;
- cliente: vínculo conciliado `AD_GENCLIENTE.CODPARC`;
- setor: único setor conciliado ou registro com `PADRAO='S'`;
- item novo: `SNK-ITEM-{CODPROD}-{SERIE}`;
- item legado: código externo preservado da `AD_GENITEMMAT`.

`CHAVE_MOVIMENTO` identifica o evento da nota na `AD_GENMOVEST`; `CODPROD|SERIE`
identifica o equipamento enviado ao Genesis.

## Dashboard

O JSP executa somente `snk:query`. Ele cruza a view com `AD_GENMOVEST` e mostra
entrada/saída, poder, nota, TOP, produto, série, status, HTTP, erro e protocolo.
Não chama a API Genesis e não expõe token.
