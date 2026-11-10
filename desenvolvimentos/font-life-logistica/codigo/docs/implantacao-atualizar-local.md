# Atualizacao da ordem de carga — Font Life

## Regra e escopo

- Classe mantida: `br.com.sankhya.bhz.central.AtualizarLocal`.
- O botao continua recebendo pedidos da TGFCAB e o parametro CODEMP.
- Localiza a ordem por **nova empresa (parametro CODEMP) + ORDEMCARGA do pedido**.
- Antes das gravacoes, valida a elegibilidade dos pedidos e coleta as ordens existentes na nova empresa. Pedido sem ordem (nula/zero) ou sem ordem correspondente na nova empresa continua o fluxo original de troca; nesses casos nao ha DTALTER para atualizar. Nao cria ordens nem atualiza a ordem da empresa anterior.
- Depois de concluir TODOS os pedidos, itens e financeiros, grava `TGFORD.DTALTER` via Jape `OrdemCarga`, como `java.sql.Timestamp` do servidor de aplicacao. Ordens repetidas sao atualizadas uma vez, com o mesmo instante para o lote.
- Uma excecao anterior impede a nova etapa de DTALTER. Excecoes de persistencia sao propagadas; nao ha commit manual.
- A exclusao/regeneracao financeira ja existia no modulo original e foi preservada. O fechamento de AcessoBanco passou a usar finally.
- Checkbox nao condiciona DTALTER nem implementa envio a Lincros nesta entrega.

## Dependencias e limitacoes

Fonte reconstruido do bytecode enviado; demais entradas do JAR sao preservadas. Compilacao Java 8 com SDK local 4.35b99; versao do servidor ainda nao informada.

Responsavel tecnico pela implantacao: equipe Zaya, operador a definir. Exige permissao administrativa para modulo Java e dicionario de dados. Nenhuma alteracao foi aplicada ao ambiente Font Life.

## Deploy em homologacao

1. Registrar versao Sankhya/banco, configuracao do botao e transacao gerenciada. Exportar modulo atual e metadados dos campos para rollback.
2. Confirmar no ambiente que a instancia OrdemCarga mapeia TGFORD e DTALTER aceita data e hora. Confirmar fuso e relogio do servidor usados pela integracao.
3. No configurador nativo de campos adicionais, verificar se TGFORD.AD_ENTRACK ja existe. Se existir, conferir tipo e uso; nao recriar nem sobrescrever configuracao divergente.
4. Se ausente, criar o campo adicional ENTRACK (nome fisico AD_ENTRACK) em TGFORD, descricao **Enviar Tracking**, apresentacao **check box/Sim-Nao**. Proposta inicial: desmarcado (N), marcado S; confirmar essa convencao no ambiente. Nao atualizar em massa dados existentes sem definir o tratamento de nulos com a integracao. A criacao fisica e metadados devem ocorrer pelo configurador, sem INSERT manual nas tabelas de dicionario.
5. Configurar DTALTER como somente leitura/protegido na tela de Ordens de Carga e nos layouts usados pelo cliente. Manter permissao de gravacao da rotina/servidor. Nao criar trigger que bloqueie toda atualizacao de DTALTER. Validar com perfil de usuario comum e layouts alternativos.
6. Substituir o modulo existente por `dist/FileViewer-atualizado-v2.jar`, mantendo o FQCN e os parametros do botao; nao cadastrar duas copias das mesmas classes. Seguir o procedimento do ambiente para recarga do modulo.
7. Executar a validacao abaixo antes de disponibilizar para producao.

## Validacao funcional e transacional obrigatoria

- Capturar CODEMP/ORDEMCARGA/DTALTER das ordens original e destino antes da execucao.
- Executar com nova empresa diferente: somente a ordem da nova empresa deve receber data E hora atuais; conferir empresa/local dos pedidos e itens e financeiro quando aplicavel.
- Selecionar pedidos da mesma ordem: validar uma atualizacao final. Testar tambem ordens distintas no lote.
- Simular erro controlado em item/financeiro: DTALTER nao deve ser atualizado. Confirmar rollback integral do lote na transacao do botao.
- Simular falha na gravacao de uma ordem: confirmar rollback de todas as alteracoes, inclusive DTALTER de outras ordens. O comportamento transacional real nao foi comprovado localmente; nao liberar se houver persistencia parcial.
- Pedido sem ordem (nula/zero) ou ordem inexistente na nova empresa: troca deve concluir, sem gravacao de DTALTER. Validar nos dois sentidos 100 -> 1 e 1 -> 100, inclusive em lote misto com pedidos com ordem.
- Confirmar que usuario nao edita DTALTER manualmente, mas a acao continua gravando.
- Marcar e desmarcar Enviar Tracking, salvar e reabrir. Conferir persistencia e representacao fisica.
- Usuario chave/Lincros confirma deteccao da alteracao e sincronizacao sem perder o componente de hora.

## Rollback

1. Suspender o uso do botao e restaurar o JAR original exportado. Recarregar conforme procedimento do ambiente.
2. Restaurar configuracao anterior de DTALTER a partir do backup dos metadados/layouts.
3. Preferir ocultar/desativar AD_ENTRACK mantendo valores. Remocao fisica apenas apos backup, verificacao de dependencias e autorizacao especifica.
4. Restaurar DTALTER ou demais dados de negocio somente a partir de backup auditado e com coordenacao da Lincros; reverter timestamps pode impedir sincronizacoes. Nao executar rollback massivo de dados.

## Consulta de verificacao (somente leitura)

Executar `database/validation/ordem-carga.sql` com os parametros da ordem de destino. Nenhum DDL/DML foi executado por esta entrega.

## Evidencias locais

- Build concluido com SDK 4.35b99; classfile major 52 (Java 8).
- Suite `test-atualizar-local.ps1`: 15 cenarios, incluindo trocas 100 -> 1 e 1 -> 100 com ordem nula, zero ou existente, destino ausente e lote misto. Preservados os cenarios de erro de item/financeiro/persistencia, elegibilidade, selecao vazia e deduplicacao.
- Verificada a busca pela nova empresa, o tipo Timestamp, o instante do lote e a gravacao posterior ao financeiro.
- Comparacao SHA-256 de cada entrada dos dois JARs: mesma lista de entradas, somente AtualizarLocal.class difere. Classes de teste nao estao no artefato.
- Identificacao da entrega atual: `FileViewer-atualizado-v2.jar`; o JAR anterior foi mantido apenas como historico local.
- Sem teste integrado contra banco/servidor; os testes usam substitutos das dependencias externas e nao comprovam rollback, eventos Jape, protecao visual ou sincronizacao Lincros.

## Correcao v2

O teste do cliente revelou que a validacao adicionada na primeira entrega bloqueava a troca quando o pedido estava sem ordem de carga. A v2 remove esse requisito adicional: a ausencia de uma ordem atualizavel nao impede a troca. Falhas reais de consulta/persistencia continuam sendo propagadas.
