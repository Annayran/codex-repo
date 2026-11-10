# OS 8677 - MRR

Fonte: SQL de producao fornecido pelo usuario nesta conversa.

## Aplicacao

1. Exportar o DDL atual no banco alvo e comparar com rollback.sql. Se houver diferencas funcionais, reconciliar antes da execucao. O rollback fornecido e baseado no anexo, nao em extracao direta do banco.
2. Executar deploy.sql em homologacao, no schema proprietario da view.
3. Executar validation.sql: esperado somente REDUCAO, contrato 338 com VLR_CAN 873,185 e contrato 627 com VLR_CAN 1369,044.
4. Validar os valores com a responsavel e conferir grade e graficos atualizados.
5. Aplicar em producao conforme o processo de deploy e repetir a validacao.

## Mudanca

Somente o bloco ALTERACAO recebe AND OCO.CODOCOR <> 14. Os outros cinco blocos foram preservados, inclusive os problemas independentes ja identificados. Duas continuacoes de comentarios quebradas na colagem foram comentadas para manter SQL executavel.

## Rollback

Executar o DDL exportado antes do deploy, preferencialmente. rollback.sql reproduz o SQL do anexo com as continuacoes de comentarios corrigidas.

Scripts preparados localmente; nao executados em banco. A simulacao anterior confirmou a eliminacao das linhas ALTERACAO nos dois casos, mas o aceite funcional dos valores nao foi registrado.
