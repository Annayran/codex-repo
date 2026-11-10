# Retencao do historico de integracao Genesis

## Objetivo

Preservar a rastreabilidade e a idempotencia da integracao enquanto o dashboard
diferencia a situacao atual dos equipamentos do acumulado historico da
`AD_GENMOVEST`.

## Regra adotada

- Os indicadores de situacao atual consideram somente os equipamentos atuais da
  `AD_VW_GENESIS_ESTOQUE`, consolidados por numero unico e serie.
- Para cada `CHAVE_MOVIMENTO`, somente o registro mais recente da
  `AD_GENMOVEST` define o status atual.
- O bloco **Historico de integracao** mantem a contagem acumulada das chaves
  registradas na tabela de controle.
- Esta entrega nao exclui, altera nem arquiva registros da `AD_GENMOVEST`.

## Condicoes para arquivamento ou exclusao

Qualquer limpeza futura depende de todas as etapas abaixo:

1. Gerar backup restauravel e validar sua leitura.
2. Homologar o procedimento em ambiente nao produtivo.
3. Conciliar as chaves com a API Genesis e com a view vigente.
4. Confirmar que nenhuma chave arquivada ou excluida voltara a ser elegivel para
   envio.
5. Validar o mecanismo de idempotencia e o fluxo de reprocessamento.
6. Obter aprovacao funcional e tecnica para a janela de execucao.
7. Preparar e testar o rollback antes da aplicacao.

Sem essas confirmacoes, a `AD_GENMOVEST` deve permanecer integra. A remocao de
uma chave usada no controle pode fazer o agendador interpreta-la como movimento
novo e repetir uma requisicao ja enviada.

## Validacao apos uma futura manutencao

- Comparar contagens antes e depois por status e por ambiente.
- Confirmar que os equipamentos atuais continuam com um unico ultimo status.
- Executar o agendador inicialmente com lote controlado.
- Verificar que nenhuma chave historica foi reenviada.
- Manter evidencias do backup, da homologacao, da aprovacao e do rollback.

