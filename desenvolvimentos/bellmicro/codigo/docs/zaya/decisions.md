# Decisões do projeto

## 29/10/2026 — manter DESCRICAO100 e adaptar o código

Cliente não pode ampliar AD_RATEVT.DESCRICAO agora. Java7.14 mantém RAT1 e
SHA256 inteiro, grava fragmentos com até99 caracteres e reconstrói a fotografia
completa. Sem alterar campos; teto4000 e rollback por excesso. Substitui a
exigência anterior de ampliar4000. Detalhes em
[histórico em campo100](eventos-100-caracteres-2026-10-29.md).

## 29/10/2026 — simulação bloqueada por divergência de estrutura e efetivação pendente

Print confirmou DESCRICAO da AD_RATEVT em100, contra4000 do manual/contrato.
Corrigir metadata e coluna pela ferramenta Sankhya, sem truncar fotografias.
Cliente informou que TOP/tipo de título auxiliares serão verificados na reunião
de amanhã. TGFRAT/RateioRecDesp confirmados no dicionário local; não há ainda
efetivação financeira implementada. Fonte e pendências completas em
[fechamento da homologação](fechamento-homologacao-2026-10-29.md).

## 29/10/2026 — identificar o motor antes de interpretar erros ou bloquear

Códigos JDBC1003/155 não identificam Oracle. A interpretação anterior foi
precipitada. A versão7.13 detecta o produto da conexão e separa bloqueios de
Oracle/SQL Server, sem registrar URLs ou dados de conexão. O motor real e o
resultado da simulação ainda precisam ser confirmados no ambiente. Consulte
[dialeto dos bloqueios](dialeto-bloqueios-2026-10-29.md), que corrige as hipóteses
dos diagnósticos anteriores sem apagar seu histórico.

## 28/10/2026 — dashboard315 com layout preservado e React

Cliente autorizou corrigir a integração e regras do ZIP enviado sem mudar o
layout, tomando TOP Nutri como referência de chamadas e MA Hospitalar como
referência de organização. CSS original preservado; JSP/CSS/JS separados;
React administra o ciclo de vida da view existente. Cálculo, critérios e
fotografias operacionais vêm do Java. Não representar CRUD ou efetivação
inexistentes como sucesso local. Detalhes em [dashboard315](dashboard315.md).

## 28/10/2026 — ações na TGFFIN e modal pelo JavaScript

Por correção explícita do cliente, publicar localmente três entradas Java para
cadastro no Financeiro: Simular, Recalcular e Efetivar. O dashboard abre o modal
HTML e chama os IDs reais; o Java deixa de abrir o modal. A classe antiga fica
preservada com aviso de migração. O provider do backend não exige lançador HTML.
Efetivar expõe contrato e bloqueia por padrão até existir serviço financeiro
implementado e homologado. Não presumir título criado por haver botão cadastrado.
Contrato, limites e instruções em [ações do Financeiro](acoes-financeiro.md).

## 27/10/2026 - autoridade do escopo

O usuário definiu explicitamente ZY-BLM-2026-RATEIO-INTPDD-001 v1.1 como
escopo final e documento principal. PDD, ata, transcrição e documentos de
tabelas complementam as regras quando compatíveis; não substituem decisões
do IntPDD. A disponibilidade do documento no chat não comprova aceite,
instalação ou homologação no ambiente.

- Fluxo manual: simular, analisar e efetivar mediante ação explícita.
- Não reintroduzir automação por lançamento descrita na ata de 06/10.
- Desempate monetário: empresa, CR e componente. ORDEM da matriz não muda
  silenciosamente esse resultado, mesmo que o HTML o descreva como desempate.
- Layout físico AD_RAT* e limites 80/255: complemento do manual confirmado
  anteriormente, sem novos campos ou mudança de schema por inferência.
- Base e competência vêm do mapeamento aprovado; ausência não é substituída
  pela data atual, valor bruto ou valor de parcela de uma nota.
- Cadastros e origem devem ser revalidados no servidor. Auxiliares nunca são
  origens; financeiros ligados a nota convergem para a identidade NUNOTA.

## 27/10/2026 - continuidade implementada

OrigemRepositoryJdbc lê apenas os campos de identidade/cabeçalho necessários,
rejeita marcador antes de converter título para nota e valida destinos por
conjuntos, em lotes de 500. A origem da base/competência é um contrato explícito
MapeamentoOrigem para aplicar a regra definida por categoria, sem permitir que
esse contrato altere empresa, natureza, parceiro ou TOP lidos do documento.

A abertura do contexto apresenta pendências funcionais sem gravar ou calcular;
falhas técnicas e ausência de permissão continuam propagando. Não converter
indisponibilidade de banco em uma simulação válida.

O build completo com SDK local passou usando fallback TCP do próprio JDK,
reproduzível pelo script Build-Sankhya.ps1 -TcpOnlyJdk. O ajuste é restrito ao
processo de build e restaura JDK_JAVA_OPTIONS ao final. SDK selecionado tem
prioridade de classpath sobre versões antigas no diretório compartilhado.

## 27/10/2026 - instâncias confirmadas pelo cliente

Captura do dicionário enviada pelo usuário mostra as onze instâncias e
tabelas com nomes iguais: AD_RATCRI, AD_RATVER, AD_RATDES, AD_RATSIM,
AD_RATPAR, AD_RATGRP, AD_RATEXE, AD_RATEVT, AD_RATINT, AD_RATVIN e AD_RATFIN.
InstanciasRateio registra esse mapeamento e ConfiguracaoRateio.instanciasJape
passa a usá-lo como padrão. A nomenclatura não está mais pendente nem é
inferida de um nome físico. A captura não comprova chaves/campos internos,
permissões ou comportamento transacional; esses aspectos continuam na homologação.
