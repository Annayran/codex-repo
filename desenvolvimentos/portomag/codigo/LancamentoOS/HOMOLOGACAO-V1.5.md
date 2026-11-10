# Homologacao V1.5

Nao considerar encerrado apenas porque o JAR compila ou a mensagem aparece.

1. Usar base de homologacao isolada com backup e cadastro/modulo anterior preservado.
2. Substituir somente o modulo da classe view.GerarPedidoCompra; manter transacao
   automatica. O log anterior comprova executeJavaTXAutomatica naquela execucao,
   nao em outro cadastro/ambiente. Nao usar controle manual para este JAR.
3. Conferir NUOS numerico na tabela AD_APONTAMENTO e no dicionario. Ausente no
   ZIP recebido: sua criacao requer deploy, validacao e rollback proprios; nao
   importar o ZIP nem criar via SQL improvisado.
4. Conferir campos adicionais no cabecalho/item exigidos pelo fonte, TOP/modelo,
   parceiro fornecedor, produto/servico, unidade e permissao do usuario.
5. Selecionar UMA linha com NUOS vazio, CODEMP, DTNEG, CODTECNICO, CODPROD,
   QTDNEG positiva, VLRUNIT/VLRTOT nao negativos e SITEDEF numerico existente.
6. Executar e guardar exec UUID e NUNOTA retornado. Esperar etapas de cabecalho,
   item e NUOS; consultar validar-nota-gerada.sql apos o retorno.
7. Exigir cabecalho existente, item correspondente, total coerente e NUOS apenas
   da linha selecionada. Conferir Portal de Compras, TOP/data, empresa/parceiro,
   status pendente, quantidade, valor e campos adicionais. Nao confirmar automaticamente.
8. Validar valores do documento, descontos/impostos herdados, estoque/financeiro,
   fluxo de alcada e cancelamento pela plataforma. Se divergirem, nao liberar.
9. Repetir a mesma linha: deve bloquear por NUOS sem criar outra nota.
10. Testar duas sessoes simultaneas: a segunda deve falhar no lock ou no NUOS;
    comprovar que existe somente uma nota para a linha. Erro de lock deve ser
    avaliado pelo operador, sem retry automatico.
11. Provocar erro controlado de validacao de item no ambiente isolado e comprovar
    rollback: nenhum novo cabecalho/item ou NUOS parcial. Testar tambem falha apos
    cabecalho com regra de homologacao autorizada; nao alterar producao para simular.
12. Testar duas linhas distintas: uma nota/item por linha, sem sobrescrever vinculos.
    Qualquer erro deve reverter o conjunto sob transacao automatica; comprovar no banco.
13. Na V1.8, conferir que o item usa CODPROD 202160, PRODUTONFE recebe a
    referencia de engenharia e AD_PRODINST recebe a descricao do produto
    original. Nao deve existir um segundo item somente para representar o produto.

Pendente funcional: cliente deve confirmar se a regra desejada e uma nota por
linha (laco legado mantido) ou agrupamento; confirmar preco vindo de VLRUNIT/VLRTOT
ou VLRSERVICO. Nao foi inventada regra nova de agrupamento/preco.

Rollback: desabilitar acao, restaurar V1.4/cadastro anterior. Documentos eventualmente
criados exigem cancelamento oficial, nao DELETE. JAR nao desfaz gravacoes.
# Casos adicionais obrigatorios V1.9

- VLRSERVICO=37550, QTDNEG=1, OBSSERVICO="Teste 200": item e cabecalho
  com total 37550, observacao copiada e CODPROD 202160. Nao usar o preco 16970,16.
- Quantidade 3: unitario 12516,66666667; total do item/nota 37550,00.
  Conferir arredondamento efetivo pela precisao do produto/servidor.
- VLRSERVICO nulo, zero ou negativo: erro antes da persistencia do cabecalho.
- Modelo com desconto/frete/regra que mude VLRNOTA: erro de divergencia;
  confirmar rollback integral e NUOS sem vinculo na transacao automatica.
- Nao reaproveitar a linha vinculada a 1199490 nem limpar NUOS diretamente.
  O tratamento de documentos existentes deve seguir as rotinas oficiais.
- Esta regra substitui a pendencia historica de escolha VLRUNIT/VLRTOT versus VLRSERVICO.

# Casos adicionais V1.10

- Repetir em homologacao valores 5980,00; 12650,00; 98120,00 com quantidade 1.
  Conferir log TOTAL_BANCO: esperado, itens e cabecalho devem ser iguais.
- Usar modelo com descontos/frete antigos: nova OS deve limpar esses valores;
  conservar configuracoes da TOP, empresa e negociacao.
- Conferir componentes VLRIPI/VLRDESC/VLRREPRED sem NULL no novo item,
  comportamento fiscal efetivo, aprovacao e rollback em divergencia.
- A V1.10 nao deve sobrescrever o total depois de totalizar para passar na trava.
- JAR nao corrige a OS 1199490 nem documentos residuais de testes anteriores.

