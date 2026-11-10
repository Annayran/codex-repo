# Codigo externo de modelo: HSP_IDEXTERNO

Decisao de 21/10/2026: novos modelos usam TRIM(TGFPRO.HSP_IDEXTERNO) como
codigoExterno, preservando texto/zeros. Sem fallback SNK-MOD/CODPROD. CODPROD
permanece somente chave local do controle API|MODELO|CODPROD. Cliente, setor,
material, fabricante, item-material, estoque e seus codigos nao sao migrados.

CandidatoGenesisRepository carrega HSP diretamente; PayloadGenesisFactory exige
valor; preservarCodigoExterno nao pode restaurar codigo divergente para MODELO.
Preparacao valida fonte unica e vinculo local antes de aproveitar o controle
sincronizado. HSP vazio, repetido, alterado ou destino vinculado a outro produto
bloqueiam. Modelos com codigo diferente do HSP aguardam reconciliacao/migracao,
sem criar outro modelo como alternativa.

## Acao agendada dedicada

Nome sugerido: [ZAYA.IT] - Genesis - Migrar codigo de modelos para HSP
Descricao: Corrige codigoExterno dos modelos SNK-MOD por ID confirmado, um modelo
por execucao, com validacao de unicidade, GET de confirmacao e rastreabilidade.
Classe: br.com.zaya.mahospitalar.genesis.schedule.MigracaoCodigoModeloGenesisScheduleAction
Periodicidade inicial de homologacao: a cada 1 minuto, somente uma instancia ativa.
Transacao automatica: desmarcada; a classe usa transacoes autonomas Jape proprias.
Nao e evento de tabela nem botao de acao. Nao substitui os jobs existentes.

Seleciona AD_GENMODELO.IDBASE=1, codigo exatamente SNK-MOD-<CODPROD>, ID remoto
positivo e HSP diferente do codigo atual. Sem filtro de atividade/tipo: migra
somente modelos ja conciliados, sem criar ou ativar registros novos. Codigos
legitimos, como MC-EP90, nao entram. Importados sem CODPROD precisam de conciliacao.

Fluxo: reserva controle API|MIGRA_MODELO|CODPROD -> valida fonte/vinculos -> GET
/modelos/{id} -> GET /modelos/?codigoExterno=HSP -> PUT /modelos/{id}, se destino
ausente -> GET /modelos/{id} para confirmar. Sao ate quatro chamadas sequenciais,
nao uma unica chamada HTTP. Usa URL/token/configuracao existentes e cooldown.
Nao executa POST em nenhuma etapa. Se remoto ja tem destino, GET por codigo deve
confirmar mesmo ID antes de concluir localmente, sem repetir PUT.

PUT usa copia do registro GET original com apenas codigoExterno alterado. GET
posterior valida ID, codigo e demais campos originais, exceto dtAlteracao e
idUsuarioAlteracao. Falha/divergencia preserva estado para revisao/reconsulta.
O contrato real do PUT e aceitacao dos campos retornados precisam de homologacao;
rejeicao nao autoriza POST nem limpeza de ID.

AD_GENAPICTRL guarda status, ID, HTTP e motivo. PAYLOAD_HASH do controle dedicado
e token de reserva (nao hash de payload), com lease de 10 minutos. Configure
timeouts HTTP menores que esse prazo. Nao ha limite de tentativas; backoff ate
60 minutos. Reserva local evita execucao simultanea usual; nao e bloqueio de
edicao remota. Pausar edicoes concorrentes de modelos durante homologacao/migracao
para evitar sobrescrita de alteracoes entre GET/PUT (API sem ETag validado aqui).

Somente apos GET confirmado: mesma transacao atualiza CODIGOEXTERNO da linha
conciliada, conclui controle da migracao, registra persistencia em AD_GENREQLOG
e invalida hash/estado sincronizado do controle normal MODELO para nova validacao.
Nao limpa ID, nao altera nome/grupo local e nao reabre movimentos ENVIADO.
Falha local apos PUT: proxima tentativa consulta o mesmo ID e reconcilia.

## Deploy, validacao e rollback

1. Pausar jobs para substituir JAR; preservar JAR anterior e exportar AD_GENMODELO,
   AD_GENAPICTRL e evidencias de codigo anterior/ID. Sem DDL nesta entrega.
2. Executar sql/genesis/modelo-hsp/01_preflight.sql e
   sql/genesis/modelo-hsp/02_validar_coluna_lease.sql (somente leitura). Resolver
   ambiguidades, ausencias e capacidade fisica do campo antes de habilitar job.
   O nome da coluna da reserva e PAYLOAD_HASH, nao HASH_PAYLOAD; nao criar
   coluna nova para acomodar referencia incorreta da biblioteca.
3. Confirmar ambiente real de destino da configuracao; rotulo HML nao garante
   isolamento. Nao exibir token em logs/consultas ou documentos.
4. Cadastrar job dedicado, inicialmente pausado. Homologar primeiro um candidato
   conhecido (exemplo: modelo 6156/produto 14862, SNK-MOD-14862 -> MC-EP90IMB),
   com acompanhamento do GET, PUT, GET e controles. Confirmar fonte/ID no ambiente
   antes de ativar; a classe nao contem IDs hardcoded.
5. Conferir nome, fabricante, material, status e demais campos remotos; codigo
   local e historico somente concluidos com persistencia confirmada. MC-EP90
   previamente correto permanece fora da migracao.
6. Monitorar controles e possiveis 429/erros. Desativar job apos saneamento ou
   manter somente com decisao operacional. Nenhum deploy/API/DML foi executado
   pelo agente; testes locais nao validam Oracle/Jape ou servidor Genesis.

Rollback: pausar job. Restaurar JAR nao reverte PUTs remotos ja confirmados.
Reversao de dados exige plano por ID, backup, consulta de conflito e PUT inverso
autorizado com posterior reconciliacao local. Nao executar UPDATE em massa nem
restaurar codigos locais sem verificar estado remoto. Manter jobs pausados se
voltar ao JAR que gera SNK-MOD, para nao recriar identidades indevidas.
