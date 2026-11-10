# Changelog

## V1.10 - 2026-10-06

- Inicializacao financeira do novo cabecalho: zera totais/descontos/frete do modelo
  e preenche VLRNOTA com o valor da atividade antes da persistencia.
- Inicializa VLRDESC, PERCDESC, VLRIPI e VLRREPRED do item com zero.
- Apos totalizacao, confere VLRNOTA e soma de VLRTOT por SELECT parametrizado
  na mesma transacao, sem depender do VO retornado/cacheado.
- Mantem rejeicao de divergencias antes de NUOS; nao forca total apos calcular.
- Acrescenta log TOTAL_BANCO e testes da inicializacao com VO simulado.
- Sem alterar documentos existentes, dependencias ou confirmacao/aprovacao.

## V1.9 - 2026-10-02

- Corrigido o preco: VLRTOT recebe AD_APONTAMENTO.VLRSERVICO, arredondado a centavos.
- QTDNEG preservada; VLRUNIT calculado como valor da atividade / quantidade (8 casas).
- VLRUNIT/VLRTOT do produto instalado nao sao utilizados como preco do servico.
- OBSERVACAO do cabecalho passa a receber OBSSERVICO (Observacao Atividade).
- Totalizacao via ImpostosHelpper antes de atualizar NUOS, com rejeicao de divergencia.
- Validacao de valor positivo antes do cabecalho e de valor/quantidade do item persistido.
- Calculador puro ValorAtividade e teste executavel sem SDK/banco.
- Sem confirmacao, refazimento forcado do financeiro ou alteracao de documentos existentes.

## V1.8 - 2026-10-02

- Descricao do produto instalado passa de AD_DESCRPRODNF para TGFITE.AD_PRODINST.
- AD_PRODINST recebe TGFPRO.DESCRPROD; nao recebe o CODPROD original.
- CODPROD 202160 e PRODUTONFE com referencia de engenharia permanecem inalterados.
- Testes estaticos, SQL e roteiro de homologacao atualizados.
- Sem mudanca de preco, quantidade, transacao, TOP ou fluxo de aprovacao.

## V1.7 - 2026-10-02

- Item da OS passa a usar sempre CODPROD 202160 (Servico de Instalacao).
- Codigo de engenharia de REFERENCIA gravado em TGFITE.PRODUTONFE.
- Descricao do produto original gravada em TGFITE.AD_DESCRPRODNF.
- Fallback da referencia para TGFPRO.REFERENCIA; REFFORN permanece fora da regra.
- Produtos e dados descritivos validados antes da persistencia do cabecalho.
- Demais regras da V1.6 preservadas; homologacao funcional pendente.

## V1.6 - 2026-09-04

- Corrigida busca da TOP no helper de item por chave exata CODTIPOPER/DHALTER.
- Removida chamada por data de referencia que usa DHALTER < data (CORE_E02676).
- Valida chave completa/resultado e adiciona log TOP_ITEM_CHAVE_EXATA.
- Atualizados contratos estaticos (17 verificacoes), sem nova dependencia.
- Demais regras V1.5 preservadas; homologacao funcional ainda pendente.

## V1.5 - 2026-09-04

- Corrigidos CODEMP, chave CODTECNICO, cidade CIDADE e conteudo do relatorio fotografico.
- Validacao previa de item/site, empresa/data, tecnico/parceiro e NUOS.
- Lock por NUNOTA/SEQUENCIA com NOWAIT e bloqueio de NUOS existente; requer transacao automatica.
- Atualiza NUOS somente da linha gerada, apos insercao do item.
- NUNOTA obtido da entidade persistida; retorno funcional com numeros processados.
- Clone inicia pendente, com identificador/numero e campos minimos de liberacao limpos.
- TIPMOV O validado e mesma versao da TOP usada pelo helper de item.
- Adicionados 15 contratos estaticos e roteiro/SELECTs de homologacao.
- Sem confirmacao automatica, migracao de API ou alteracao de banco. Validacao integral pendente.

## V1.4 - 2026-09-04

- Consulta parametrizada seleciona exclusivamente MAX(DHALTER) da TOP configurada.
- Valida TOP, versao e AD_MODNOTA; consulta o modelo antes de apontamento/tecnico.
- Modelo ausente ou falha de consulta interrompe o fluxo com contexto e causa preservada.
- Cabecalho recebe DHTIPOPER da mesma versao consultada, sem herdar a do modelo.
- Mantidos Java 8, logs, helper e demais regras legadas; sem publicacao ou mudanca no banco.

## V1.3 - 2026-09-03

- Corrigida a chave TSIPAR para CODTIPOPERMODCA, confirmada pelo usuario.
- Valores ausentes, vazios ou diferentes de digitos inteiros sao rejeitados antes do SQL.
- Consulta de TOP usa parametro nomeado BigDecimal; ResultSet e NativeSql liberados.
- Mantidos logs, selecao V1.2, escolha legada da TOP/modelo e regras de geracao.
- Script de diagnostico atualizado com a chave correta, sem execucao no banco.

## V1.2 - 2026-09-03

- Corrigida somente a condicao de selecao, conforme autorizacao do usuario.
- Selecao nula/vazia encerra com aviso; selecao preenchida entra no laco.
- Mantidos logger, etapas, stack traces e regras de geracao/transacao legadas.
- Substituido o aviso de bloqueio por SELECAO_APTA_PROCESSAMENTO.
- Sem correcoes de campos, tecnico, TOP, modelo, duplicidade ou persistencia.

## V1.1 - 2026-09-03

- Logs JUL com prefixo PORTOMAG-OS e identificador por execucao.
- Inicio, quantidade selecionada, etapas, excecoes com stack trace e duracao.
- Aviso explicito e mensagem na tela para selecao ignorada pela condicao legada.
- Sem correcao da condicao de processamento, regras, campos ou transacoes.
- JAR V1.0 preservado para comparacao; nenhuma nova dependencia.

## V1.0 - 2026-09-03

- Primeira baseline de compilacao controlada do fonte legado recebido.
- Build Gradle com compilador Java 8, manifesto e JAR deterministico.
- Classes publicas e comportamento original preservados, inclusive defeitos conhecidos.
- Sem alteracoes de banco, metadados ou regras funcionais.
