# Resultado do preflight de limpeza - 03/11/2026

Fontes: Downloads/Resultado_da_Query (12).xls, (13).xls e (14).xls,
exportados em 03/11/2026. Leitura local com jxl do SDK existente; arquivos
originais preservados. A consulta 4 nao retornou linhas, conforme usuario.

## Resultado

- Export 12: 23 movimentos fora da consulta de origem. Sao 18 PENDENTE,
  2 REPROCESSAR com HTTP 408 na consulta de material e 3 REVISAO: um conflito
  de vinculo local e dois POST de cliente rejeitados por documento existente.
  Todos sem protocolo remoto nesse controle. Isso nao prova ausencia de
  cadastro ou de tentativa no controle tecnico/historico. Atualizacoes no
  intervalo 15 a 30/10/2026. Nenhuma exclusao executada.
- Export 13: material remoto 233 possui linhas locais 233 e 309 na base 1.
  Apenas igualdade de ID remoto foi demonstrada, nao equivalencia integral.
  Comparar todos os atributos e modelos dependentes antes de consolidar.
- Export 14: 5.000 grupos produto/serie. Em TODOS, quantidade de linhas e
  quantidade de IDs remotos distintos coincidem. Nao sao repeticoes locais
  comprovadas do mesmo ID. Nao excluir sob a autorizacao de duplicatas
  equivalentes. Os 5.000 resultados podem refletir limite do export; confirmar
  total por COUNT, sem pressupor que representam a base inteira.
- Consulta 4: sem FK encontrada no escopo USER_CONSTRAINTS. Nao demonstra
  ausencia de dependencias logicas de Jape, consultas, telas ou API.

## Proxima acao

Executar conferencia-limpeza-resultados-20261103.sql (somente SELECT), mais
diagnostico-fora-selecao-20261103.sql para os movimentos. Nao foi identificado
ate aqui nenhum lote comprovadamente equivalente e sem dependencias pronto
para DELETE. Usuario autorizou considerar todo o periodo, preservando
identidades diferentes. Preparar exclusao por IDs apenas apos evidencia,
backup e rollback, sem apagar historico de API para reduzir indicadores.
