# Situacao padrao Operacional - 04/11/2026

Decisao explicita do usuario: origem sem situacao deve usar O / Operacional.
Substitui a regra anterior de bloquear origem vazia. Somente o objeto de envio
em memoria e normalizado; nenhum UPDATE da serie e executado.

Lookup do dominio consulta O quando STATUS_ESTOQUE e nulo ou branco. Continua
exigindo exatamente um dominio SITUACAO ativo e conciliado na base 1, codigo O,
com ID positivo. Nao se presume ID 1. Ausencia ou ambiguidade do dominio
continua bloqueando por SITUACAO_NAO_CONCILIADA. Situacao preenchida preservada;
codigo preenchido sem descricao reconhecida continua bloqueado.

## Revisoes existentes

O seletor ja inclui REVISAO, PENDENTE, REPROCESSAR e ERRO. Nao foi feito reset
em massa: depois de instalar o JAR, o job ativo retoma casos elegiveis em lotes,
respeitando o intervalo existente entre tentativas (ate 60 minutos).
Os casos bloqueados apenas por origem vazia podem avancar com dominio O
conciliado. Nao ha garantia de envio de todos: casos fora da view, cancelados,
fila de exclusao, protocolo confirmado, reserva de reativacao, duplicidades,
cliente divergente e outros conflitos continuam sujeitos as protecoes.

## Entrega e validacao

Substituir apenas a biblioteca do modulo existente, com backup e homologacao
controlada antes de liberar. Nao trocar o dashboard: manter o hotfix de abertura
confirmado pelo usuario. Nao criar jobs duplicados nem desabilitar controles.
Executar preflight-operacional-padrao-20261104.sql: primeira consulta deve trazer
exatamente uma situacao. Se nao trouxer, conciliar pela tela com responsavel
autorizado antes do envio; nao escolher um ID nem aplicar DML generico.

Testes cobrem origem nula, branca, situacao preenchida, codigo invalido e ID
ausente; lookup exige dominio unico. Sem teste em API real ou Jape/Oracle.
Build homologacao-20261104-094932: 216 testes Java aprovados; oito testes HTTP
loopback excluidos pela limitacao Java/Windows documentada. Revisao estatica
independente sem novos achados nesse delta. SHA256 do JAR:
C083B423C70C88B6A0F8D39B34584CA2F0F5F5BB55FDA42D72441A658D3202B5.
Nenhum envio remoto, alteracao de banco ou instalacao foi executado aqui.
Rollback: restaurar JAR anterior; nao apagar historico ou desfazer API por lote.
