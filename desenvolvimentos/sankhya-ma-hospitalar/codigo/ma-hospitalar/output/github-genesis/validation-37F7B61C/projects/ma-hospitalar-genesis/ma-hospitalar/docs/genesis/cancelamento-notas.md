# Cancelamento de notas e inativacao Genesis

## Entrega e limites

O evento captura a nota, cria a fila AD_GENEXCFILA e libera as referencias
NUNOTA das tabelas adicionais na mesma transacao. Nao chama a API, nao faz
commit e propaga falhas para rollback do cancelamento. NUNOTA_ORIGEM na fila
e apenas uma referencia de auditoria, sem FK para TGFCAB.

Envios passam a guardar ORIGEM_ITEM. Somente CRIADO com envio confirmado e ID
positivo entra em PENDENTE; ATUALIZADO, SEM_ALTERACAO, origem desconhecida e
falhas de envio entram em REVISAO. Envios antigos nao sao presumidos criados.
Controles sem evidencia de envio/falha sao removidos. Controles com fila sao
preservados e desvinculados; a base local tambem perde apenas NUNOTA.
O controle preservado recebe CANCELADO. O resultado externo permanece no
STATUS da fila; CANCELADO nao significa que o equipamento ja foi inativado.
Controles apagados podem ser recuperados pela chave em AD_GENITEMMAT quando
identificavel, com ID unico, sempre sujeitos a REVISAO. Nao ha busca remota
automatica para reconstruir historico ou origem desconhecida.

A acao agendada utiliza DELETE de item-material (inativacao, nao exclusao
fisica). Somente processa notas presentes em TGFCAN com DTCANC preenchida e
ausentes em TGFCAB. Exclusao comum do cabecalho nao autoriza DELETE remoto.
Um item ligado localmente a outro movimento nao e inativado automaticamente.
Sucesso HTTP 200 (documentado) e ausencia 404 concluem a fila; outros 2xx exigem
revisao para nao assumir conclusao de uma resposta assincrona. A base local
recebe STATUSGENESIS=Inativo apos a conclusao. 5xx/timeout permitem tentativas
limitadas; erros funcionais e autenticacao exigem intervencao. Simulacao nao
chama DELETE nem consome tentativas. Uma chave primaria por controle evita
duplicacao da solicitacao. Envio, cancelamento e job compartilham trava na
Central: execucoes concorrentes podem aguardar a transacao de envio.
Reprocessamento automatico de envio/inativacao respeita intervalo exponencial
de 2,4,8,16... minutos (maximo 60), alem das 5 falhas configuradas atualmente.
Envios REPROCESSAR antigos com HTTP funcional ou limite atingido sao colocados
em ERRO, preservando protocolo e mensagem. Erros funcionais nao sao repetidos.
O monitor do dashboard separa envios cancelados dos resultados da fila.

## Implantacao (nao executada automaticamente)

1. Parar o envio agendado e obter backup das tabelas adicionais afetadas.
   Confirmar em USER_TAB_COLUMNS que NUNOTA em AD_GENMOVEST/AD_GENITEMMAT aceita
   NULL; se houver NOT NULL, ajustar a modelagem aprovada antes da implantacao.
2. Criar a tabela/campos pelo Construtor conforme cadastro-manual-cancelamento.md.
   Executar 01_deploy.sql apenas como conferencia (SELECT), nao como criacao.
3. Instalar MAHospitalar.jar preservando configuracoes existentes.
4. Registrar br.com.zaya.mahospitalar.genesis.event.CancelamentoNotaGenesisEvent
   na instancia CabecalhoNota (TGFCAB), beforeDelete.
5. Registrar br.com.zaya.mahospitalar.genesis.event.RegistroNotaCanceladaGenesisEvent
   na instancia NotaCancelada (TGFCAN), beforeInsert. TGFCAN e comprovada no
   dicionario 4.35b815; a ordem real e a passagem pelo Jape precisam de teste.
6. Cadastrar br.com.zaya.mahospitalar.genesis.schedule.InativacaoGenesisScheduleAction,
   inicialmente desativada. Nao registrar em tabela de tentativas/ocorrencias.
7. Validar em homologacao e executar 02_validacao.sql. Ativar os jobs somente
   apos aceite. Nao habilitar DELETE em producao antes da validacao da API.
   A configuracao existente continua fixa em homologacao, lote 1 e 5 tentativas;
   esta entrega nao altera ambiente, credencial ou parametros existentes.

## Roteiro de homologacao obrigatorio

- Nota sem vinculo: cancelamento normal, sem fila.
- Controle pendente/simulado sem envio: removido; nenhuma chamada remota.
- Item criado por POST: fila unica e vinculo liberado; DELETE somente apos commit.
- Item atualizado ou legado: REVISAO, sem DELETE.
- Cancelamento com falha posterior: fila e desvinculacao devem sofrer rollback.
- Insercao TGFCAN antes/depois da exclusao TGFCAB: validar ambos os eventos.
- Exclusao comum de nota: nenhuma inativacao sem TGFCAN/DTCANC.
- Movimento posterior no mesmo equipamento: conflito para revisao.
- Repeticao dos eventos/job: sem fila duplicada; DELETE repetido/404 seguro.
- HTTP 401/403: interromper lote; 500/timeout: repetir ate maximo configurado.
- Falha apos DELETE e antes do commit: confirmar que repetir DELETE de item ja
  inativo e aceito pela API. Esse contrato nao e garantido pelo Swagger.
- Concorrencia de envio/cancelamento/job: nenhum envio apos a desvinculacao.
- Estorno sem exclusao TGFCAB ou sem TGFCAN nao esta coberto; requer identificar
  o evento real antes de ampliar a regra.

## Operacao e rollback

Consultar a fila pelo SQL de validacao. ERRO/REVISAO nao retornam automaticamente
ao processamento. Corrigir credencial/causa e aprovar reprocessamento pontual;
nao promover REVISAO sem comprovar origem e identidade. Rollback operacional:
desativar os novos eventos e job e reinstalar JAR anterior, preservando fila e
coluna para auditoria (03_rollback.sql). Nao ha reativacao remota automatica.
Desvinculacoes confirmadas nao sao reconstruidas automaticamente. Preservar o
backup; nao restaurar FK de nota que ja foi cancelada/excluida.


---
Copia de consulta para entrega; fonte canonica local: `ma-hospitalar/docs/genesis/cancelamento-notas.md`. Sanitizacao de caminhos e enderecos aplicada; nao representa homologacao.
