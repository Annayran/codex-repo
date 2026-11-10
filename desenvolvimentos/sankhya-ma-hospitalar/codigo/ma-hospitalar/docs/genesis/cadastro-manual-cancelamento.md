# Cadastro manual pelo Construtor de Telas

Nao executar CREATE TABLE ou ALTER TABLE. Criar/publicar a estrutura pelo
Construtor antes de instalar o JAR e o dashboard desta entrega.

## Tabela AD_GENEXCFILA

Descricao sugerida: Fila de inativacao Genesis. Criar uma tela consultavel,
com filtros por nota de origem, status, ID Genesis e data de atualizacao.

| Campo | Tipo | Tamanho | Obrigatorio | Configuracao |
|---|---|---:|---|---|
| IDINTGENESIS | Numero inteiro | 19 digitos | Sim | Chave primaria; valor informado pelo Java, sem geracao paralela |
| NUNOTA_ORIGEM | Numero inteiro | 19 digitos | Sim | Apenas auditoria; SEM relacionamento com TGFCAB |
| CHAVE_MOVIMENTO | Texto | 400 | Sim | Chave do movimento original |
| IDGENESIS | Texto | 100 | Nao | ID numerico retornado pela API, nao codigo externo |
| STATUS | Texto/opcoes | 20 | Sim | PENDENTE, CONCLUIDO, ERRO, REVISAO |
| TENTATIVAS | Numero inteiro | 10 digitos | Sim | Padrao 0 |
| HTTP_STATUS | Numero inteiro | 5 digitos | Nao | Codigo HTTP retornado |
| MENSAGEM | Texto | 4000 | Nao | Detalhe sanitizado da falha/revisao |
| DH_INCLUSAO | Data e hora | - | Sim | Preenchido pelo Java |
| DH_ATUALIZACAO | Data e hora | - | Sim | Preenchido pelo Java |

Nao criar relacionamento obrigatorio para AD_GENMOVEST: a fila deve permanecer
mesmo se o controle for removido. O IDINTGENESIS e preservado para auditoria;
o gerador Java considera tanto controles quanto fila para nao reutilizar IDs.
Restringir edicao/exclusao manual da fila; nao mudar REVISAO para PENDENTE sem
confirmar origem, identidade e ausencia de movimento posterior.

## Tabela AD_GENMOVEST

- Adicionar ORIGEM_ITEM: Texto/opcoes, tamanho 20, opcional. Opcoes CRIADO,
  ATUALIZADO, SEM_ALTERACAO. Nao preencher valores antigos por suposicao.
- Adicionar CANCELADO as opcoes existentes de STATUS_INTEGRACAO (tamanho minimo
  20), preservando PENDENTE, ENVIADO, ERRO, REPROCESSAR e SIMULADO.
- NUNOTA deve aceitar vazio. Preservar PROTOCOLO_GENESIS, datas e hash.
- AD_GENITEMMAT.NUNOTA tambem deve aceitar vazio.

## Status e processamento

ENVIADO = envio confirmado com ID recebido. REPROCESSAR = falha transitoria
aguardando nova tentativa automatica. ERRO = correcao manual ou limite atingido.
CANCELADO = envio vinculado a nota cancelada; nao significa que o DELETE ja
concluiu. Consultar STATUS na AD_GENEXCFILA para esse resultado.

O job de envio deve permanecer agendado. Ele tenta novamente sem botao para
timeout/conexao, HTTP 408/429/5xx, respeitando 2,4,8,16... minutos (maximo 60) e
o limite atual de 5 falhas. Ao atingir o limite, o controle fica em ERRO.
HTTP 400/401/403/404 em escrita/409/422 e outros erros funcionais nao entram
em repeticao automatica. Correcao e liberacao manual continuam necessarias.
O limite se aplica a tentativas, nao ao total de equipamentos consultaveis.

O botao de reprocessamento existente libera ERRO/REPROCESSAR e zera a contagem;
nao usar para CANCELADO. Falha de tentativa posterior nao apaga o ID Genesis.
Nao limpar controles para reiniciar: perde-se origem e rastreabilidade.

Se o controle foi apagado mas existe AD_GENITEMMAT com a chave do movimento,
o evento recupera um controle de auditoria e o ID local quando unico. Vai para
REVISAO, nunca para DELETE automatico. Chave confirmada no projeto:
CODEMP|NUNOTA|SEQUENCIA|CODPROD|SERIE. Sem chave/ID local nao ha recuperacao
automatica ou busca remota nesta entrega. Notas canceladas antes de cadastrar
os eventos nao sao varridas automaticamente; precisam de saneamento acordado.

## Implantacao e verificacao

1. Desativar os jobs e obter backup das tabelas adicionais afetadas.
2. Criar os campos/tabela pelo Construtor e publicar/sincronizar a modelagem.
3. Executar 01_deploy.sql (somente SELECT) para conferir campos, nulabilidade e
   chave primaria. Confirmar que nao ha FK de NUNOTA_ORIGEM para TGFCAB.
4. Instalar o novo JAR e cadastrar os eventos/job conforme cancelamento-notas.md.
5. Substituir o dashboard pelo novo JSP somente depois da tabela existir.
6. Validar em homologacao envio, erro transitorio, limite, recuperacao de ID,
   cancelamento, concorrencia e rollback antes de ativar processamento real.

O rollback e operacional: parar eventos/job, reinstalar artefatos anteriores,
preservar fila/coluna/opcoes para auditoria; nao excluir estrutura no Construtor.
Inativacao remota nao e automaticamente revertida. Nenhuma estrutura foi criada
no ambiente pelo agente; este documento e o procedimento manual.
