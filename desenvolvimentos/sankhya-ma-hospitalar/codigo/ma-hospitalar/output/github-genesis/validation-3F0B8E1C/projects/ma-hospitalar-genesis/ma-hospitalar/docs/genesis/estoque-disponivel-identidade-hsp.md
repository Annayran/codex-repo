# Estoque disponivel e identidade HSP

Decisao de 16/10/2026: qtd vem de TGFEST.ESTOQUE - TGFEST.RESERVADO, somando
as empresas de AD_CENTPARAMEMP e os locais de AD_CENTPARAMLOC, NUPAR=1.
Ambos precisam estar configurados; vazio nao significa todas as empresas/locais.
As consultas usam EXISTS para nao multiplicar saldo por configuracao repetida.
As colunas esperadas sao CODEMP e CODLOCAL: validar no ambiente com o preflight.

Inclui E.TIPO P e T, sem filtrar CODPARC=0. Calcula subtotal proprio, terceiro e
total. Sem posicoes dentro do escopo, total=0. Zero nao vira uma unidade; saldo
total negativo bloqueia o envio, sem zerar ou arredondar silenciosamente.
Servicos continuam elegiveis pelas regras existentes e recebem a mesma leitura
de saldo; nao foi mantida quantidade artificial para servico sem TGFEST.
API deve aceitar zero em homologacao; nao houve chamada remota neste ajuste.

codigo e codigoExterno do catalogo passam a TGFPRO.HSP_IDEXTERNO como texto,
removendo somente espacos nas extremidades e preservando zeros a esquerda.
GET /estoques/?codigoExterno={HSP_IDEXTERNO}. CODPROD identifica apenas o produto
local no controle. Nao ha fallback para CODPROD/AD_IDEXTERNO.

Fonte vazia ou repetida entre produtos bloqueia chamadas. Codigo anterior do
controle diferente permite somente GET por HSP para reconciliar identidade.
Atualiza o controle apenas quando codigo e ID remoto conferem com o ID legado
(ou quando nao havia ID local). ID divergente bloqueia mutacao e exige revisao.
Nao escolhe um dos registros remotos nem cria outro para substituir o legado.
Se GET retornar multiplos registros, a classificacao CONFLITO continua bloqueando
mutacao. Controles antigos devem ser reconciliados individualmente com a Genesis.

## Criacao incerta

O job reserva STATUS=ENVIO_INCERTO em AD_GENESTCTRL antes de POST, sob o lock da
central. ID ja existente ou reserva anterior impedem outro POST. Sucesso local
confirma o ID, hash, estado e evidencia em transacao autonoma por produto.
Timeout/5xx/409 ou falha de persistencia preservam reserva. Proximas tentativas
fazem GET para recuperar o cadastro; ausencia ainda nao autoriza repetir POST.
Rejeicao explicita POST 4xx, exceto 408/409, libera para corrigir e tentar de novo.
O controle nao utiliza limite de tentativas, mas respeita backoff/cooldown.

Essa protecao existe no job pelo callback de reserva; a classe de servico tambem
possui construtor de compatibilidade para testes/consumidores antigos. Nao oferece
transacao distribuida ou garantia de exatamente uma criacao remota.

## Rastreio e campos descritivos

- AD_GENESTCTRL guarda o estado atual por CODPROD e o codigo/ID confirmados.
- AD_GENREQLOG, FLUXO=ESTOQUES_PRECOS, guarda GET/POST/PUT/DELETE e JSON sanitizado.
- DETALHE da chamada aplicada inclui HSP_IDEXTERNO e subtotal P/T/total.
- Se o hash nao muda, nao faz HTTP; o saldo calculado aparece no log da rodada.
- tag/localizacao permanecem omitidos. Recomendacao de modelagem: tipo P/T e
  classificacao, nao endereco fisico; tag e mais coerente, mas significado e
  tamanho aceitos pela Genesis ainda precisam de alinhamento. Um unico cadastro
  pode somar P e T; nao enviar uma etiqueta exclusiva que esconda o outro saldo.
- controlarEstoque, categoria, fabricante, fornecedor, precos, IPI e custo
  mantem as regras anteriores. Nao alterou HSP_CODPARC nem envio de clientes.

## Homologacao e deploy

1. Pausar job de estoque; exportar AD_GENESTCTRL e preservar JAR anterior.
2. Executar sql/genesis/estoque-disponivel/01_diagnostico.sql (somente leitura).
3. Conferir campos/configuracoes, saldos, fontes vazias, repetidas e controles
   divergentes. Conferir tamanho fisico de CODIGOEXTERNO contra HSP_IDEXTERNO.
4. Reconciliar controles antigos por evidencia remota, sem limpar IDs/hashes em
   massa. Esta entrega nao fornece DML de conciliacao nem exclusao de duplicados.
5. Substituir JAR com jobs pausados e testar produto conhecido antes de novo POST.
6. Testar saldo fracionario, zero, reservas, P/T, escopo com configuracao duplicada,
   chave com zeros, fonte ausente/repetida, retorno ambiguo, 429 e POST incerto.

Nao requer novas tabelas ou campos. Nao alterou DDL, endpoints de clientes,
eventos de exclusao/cancelamento, JSP ou agendamentos remotos.
Rollback: pausar job e preservar as reservas/IDs criados. Restaurar JAR antigo
nao e suficiente para reativar estoque: ele usa CODPROD e quantidade fixa.
So retomar depois de reconciliar identidades e avaliar risco de nova duplicacao.

## Pendente separado

HSP_CODPARC depende de confirmar seu significado. Exclusao simples da nota,
sem TGFCAN, e concorrencia com envio iniciado precisam de ajuste/teste separado;
beforeDelete existente deve estar cadastrado na TGFCAB. SQL direto nao aciona
EventoProgramavelJava. Nao confundir desvincular controles com apagar itens que
ja existiam remotamente (origem SEM_ALTERACAO).


---
Copia de consulta para entrega; fonte canonica local: `ma-hospitalar/docs/genesis/estoque-disponivel-identidade-hsp.md`. Sanitizacao de caminhos e enderecos aplicada; nao representa homologacao.
