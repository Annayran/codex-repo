# Equipamentos com serie: selecao M1 e M3

## Decisao e origem

Em 2026-10-23 o solicitante encaminhou conversa de WhatsApp com Luiz Felipe:
o produto 11919 esta corretamente classificado como M3; deve-se incluir M1 e
M3 no filtro de equipamentos por serie. A regra e por tipo de produto, nao
por cliente. Alguns produtos M3 recebem servicos e precisam constar com serie.
O cliente deve continuar integrado no fluxo. Demanda DEM-8f6a2e25170d6c25.
Fonte recebida: imagem codex-clipboard-fe42e0a5-5a6a-424d-ace9-7bc96ad34005.png.
Nao foram transcritos telefone, contatos ou dados de acesso.

## Causa e alteracao

As notas 120671/120872 possuem cinco series do produto 11919. A analise anterior
comprovou que o filtro exclusivo M1 impedia esses candidatos de iniciar o fluxo,
inclusive a etapa de cliente. Nao era evidencia de rejeicao HTTP.

A visao de movimentos e a consulta Java passam a aceitar tipos efetivos M1/M3.
Continua a prioridade do Tipo de Produto principal, com fallback para o campo
alternativo somente quando o principal estiver vazio. Serie, tipo de operacao,
estoque, cancelamento e demais requisitos existentes permanecem obrigatorios.
M2, SV e demais tipos nao passam para equipamentos por serie.
O fluxo independente de saldos M2/M3/SV nao foi alterado.

Cliente continua sendo a primeira dependencia da preparacao, seguido de setor,
material, fabricante e modelo. Nao significa reenviar o cliente por POST a cada
serie: vinculos/identidades conciliados sao reutilizados conforme o fluxo atual.
Nao foi criado envio global de todos os clientes nem condicao por cliente.
Regras de deduplicacao, identidades, HSP_IDEXTERNO e protecao ENVIO_INCERTO
permanecem. Elegibilidade nao comprova envio nem autoriza liberar reservas.
Diagnostico das notas e texto de condicoes no painel acompanham a nova regra.

## Deploy autorizado separadamente

1. Homologar primeiro. Salvar DDL da view instalada, JAR e painel atuais.
2. Pausar as acoes agendadas para nao processar uma combinacao de versoes.
3. Publicar `sql/genesis/view/01_ad_vw_genesis_estoque.sql` e JAR da mesma
   versao. Publicar o JSP React atualizado se esse painel estiver em uso.
4. Executar consultas de `02_validar_ad_vw_genesis_estoque.sql` individualmente;
   verificar view valida, M1/M3 com serie, prioridade/fallback e exclusao M2/SV.
5. Executar `sql/genesis/diagnostico-notas-120671-120872.sql`: conferir cinco
   series originais, cliente/modelo, registros de controle e historico remoto.
6. Retomar somente apos validacao. M3 anteriores sem controle poderao passar a
   ser elegiveis, nao apenas o produto 11919. Medir volume antes de ativar.
7. Para cada caso, confirmar identidade, resposta remota e persistencia local
   antes de considerar enviado. POST incerto requer conciliacao, nunca reset
   ou reenvio em massa. Se houver outro bloqueio, registrar etapa e evidencia.

## Rollback

Pausar agendamentos, restaurar o DDL salvo e o JAR anterior em conjunto.
`04_rollback_m1.sql` e alternativa correspondente ao filtro M1 pre-melhoria;
nao usar o rollback historico 03, que remove o filtro de tipo completamente.
Validar e somente depois retomar. Rollback nao exclui cadastros remotos ja
criados: esses exigem conciliacao especifica, sem exclusao automatica.

## Validacao e entrega

Implementacao local concluida. Runner com alvo Java 8 e -UnitOnly: 185 testes
aprovados, sem falhas; testes HTTP loopback/smoke excluidos. Gradle permanece
bloqueado pela conexao loopback do ambiente; validacao usa o runner existente.
Build React e Playwright aprovados: filtros/datas, abas, temas/mobile, XSS e
zero requests externos. Nenhuma dependencia instalada ou atualizada.
Revisao estatica independente: filtros, guarda real e rollback coerentes;
achado de diagnostico ausente no ZIP corrigido na lista de empacotamento.
Testes de SQL sao contratos textuais, nao execucao no Oracle. Guarda Java
exercitada diretamente. Oracle/Jape, API real e producao nao consultados.
Sem deploy, reprocessamento, commit, push ou atualizacao de PR autorizados
nesta solicitacao. O PR 1 publicado anteriormente nao inclui esta melhoria.

O profile gerado foi atualizado conforme a skill, mas seu scanner nao reconhece
o layout Java legado `src/br/...`: contagem zero nao significa ausencia de Java.
As fontes e o build existente sao a referencia de implementacao.
