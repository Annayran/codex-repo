# Changelog

## 4.5 — Recorte de movimentação do agendamento

- A ScheduledAction considera somente parceiros com pelo menos um registro
  na TGFCAB com DTMOV maior ou igual a 01/01/2026.
- Utilizado EXISTS para evitar duplicidade de parceiros e parâmetro de data
  tipado, sem depender do formato regional do banco.
- Preservados os filtros de parceiro ativo, vertentes pendentes, proteção
  contra reprocessamento e limite de 100 parceiros por execução.
- Botão e evento programável permanecem sem filtro de movimentação.

## 4.4 — Usuário efetivo do evento programável

- O evento programável passa a executar a criação e o vínculo das contas com o
  usuário efetivo SUP (`CODUSU = 0`), conforme definição do processo.
- A propriedade Jape `usuario_logado` é substituída somente durante o bloco de
  criação contábil e restaurada ao final, inclusive em erro.
- O `CODUSU` das contas criadas pelo evento também é gravado como `0`, mantendo
  coerência entre autoria e contexto dos listeners Sankhya.
- A ação agendada continua utilizando o usuário configurado em seu cadastro.
- Adicionados testes para ativação do SUP, remoção do contexto temporário e
  restauração de um usuário anterior.

## 4.3 — Limite da descrição da conta

- A descrição gravada em `PlanoConta.DESCRCTA` passa a respeitar o limite de
  40 caracteres da entidade Sankhya.
- O nome do parceiro é aparado nas extremidades e truncado somente quando excede
  o limite, preservando nomes menores.
- Mantido o fallback `PARCEIRO <CODPARC>` quando o nome estiver vazio.
- Adicionados testes para descrição longa, nome dentro do limite e fallback.

## 4.2 — Contexto Jape do usuário no agendamento

- A ação agendada passa a registrar temporariamente `usuario_logado` no
  `JapeSessionContext` antes da criação das contas.
- O valor utiliza o usuário configurado no cadastro da Ação Agendada, incluindo
  o SUP (`CODUSU = 0`).
- O contexto anterior é restaurado em `finally`, inclusive quando algum parceiro
  falha, evitando vazamento de estado para outro job executado na mesma thread.
- Corrigida a falha do listener padrão do Plano de Contas: `Propriedade de sessão
  requerida não encontrada: 'usuario_logado'`.

## 4.1 — Compatibilidade com o usuário SUP

- Corrigida a validação de usuário do botão e da ação agendada para aceitar
  `CODUSU = 0`, código válido do usuário SUP no Sankhya.
- Mantido o bloqueio para usuário ausente ou com código negativo.
- A exceção final da ação agendada passa a apresentar também o motivo funcional,
  evitando que a tela exiba somente o encapsulamento `IllegalStateException`.

## 4.0 — Processamento em lote e ação agendada

- O botão de ação passa a aceitar uma ou várias linhas da TGFPAR.
- Cada parceiro selecionado é executado em transação própria; uma falha reverte
  somente a linha afetada e o lote continua.
- Adicionado resumo visual com parceiros atualizados, contas criadas, registros
  já protegidos e falhas por parceiro.
- Adicionada `GerarContaContabilScheduledAction`, compatível com o contrato
  `org.cuckoo.core.ScheduledAction` disponível nas dependências do projeto.
- A ação agendada busca até 100 parceiros ativos por execução, usando os tipos
  Cliente/Fornecedor e os checkboxes de proteção como critério de pendência.
- O agendamento usa o usuário configurado na ação e a empresa do parâmetro global
  `ZYNACCODEMPCTA`.
- Incluídos logs de início, progresso, commit individual, rollback por parceiro e
  resumo da execução.
- Documentada a exigência de manter `Transação automática` desmarcada para
  preservar o isolamento transacional por parceiro.

## 3.0 — Proteção persistente contra reprocessamento

- Adicionados os checkboxes `AD_ZYNACCTACLI` e `AD_ZYNACCTAFOR` como marcas
  persistentes das vertentes processadas pelo desenvolvimento.
- Conta preenchida sem a respectiva marca `S` passa a ser substituída pela conta
  criada no fluxo Zaya.
- A criação da conta, o vínculo e a marcação `S` ocorrem na mesma transação.
- Uma vertente marcada com `S` não gera nova conta, tanto no evento quanto no
  botão de ação.
- Parceiros Cliente e Fornecedor são protegidos por vertente: uma conta pode ser
  criada enquanto a outra, já marcada, é ignorada.
- Logs passam a registrar os dois campos contábeis e os dois checkboxes de
  proteção para auditoria do processamento.
- Adicionada mensagem informativa no botão quando todas as vertentes já foram
  processadas.

## 2.0 — Parceiro cliente e fornecedor

- Parceiros marcados simultaneamente como Cliente e Fornecedor passam a gerar
  as duas contas contábeis na mesma execução.
- A conta de cliente é vinculada em `CODCTACTB2` e a conta de fornecedor em
  `CODCTACTB`, tanto pelo evento quanto pelo botão de ação.
- As duas criações permanecem na mesma transação; uma falha solicita rollback
  integral para impedir vínculo parcial.
- Mensagens e logs passam a apresentar a quantidade e os dois vínculos gerados.
- Mantidas as proteções contra conta preexistente e execução duplicada.

## 1.9 — Tratamento dos valores automáticos do Sankhya

- Comprovado por log que o evento recebe as contas vazias, mas o fluxo de
  persistência as preenche antes da releitura da TGFPAR.
- O evento passou a validar somente o tipo Cliente/Fornecedor na releitura,
  preservando a decisão tomada sobre o registro original.
- Cliente exclusivo passa a manter apenas `CODCTACTB2`; fornecedor exclusivo,
  apenas `CODCTACTB`.
- O campo contábil incompatível preenchido automaticamente é limpo pelo Jape na
  mesma transação.
- O botão manual preserva a validação estrita contra contas preexistentes.

## 1.8 — Diagnóstico e execução única por transação

- Adicionada marca no `TransactionContext` para impedir o segundo disparo do
  evento para o mesmo parceiro na mesma transação.
- Adicionado log do estado recebido pelo evento e do estado efetivamente lido
  da TGFPAR.
- Adicionado bloqueio quando `CODCTACTB` e `CODCTACTB2` aparecem preenchidos
  simultaneamente, situação que a rotina não produz em uma única execução.

## 1.7 — Idempotência do evento

- Corrigido o rollback indevido quando uma segunda execução do evento encontra
  a conta vinculada pela primeira execução na mesma transação.
- O evento agora ignora a conta já vinculada, registra a ocorrência e permite o
  salvamento do parceiro.
- O botão manual preserva a validação estrita contra conta já existente.

## 1.6 — Correção da consulta do parâmetro

- Corrigida a leitura da entidade `ParametroSistema`, cuja chave é composta.
- Substituído o `findByPK` incompleto por consulta parametrizada de `CHAVE` e
  `CODUSU`.
- Definido `CODUSU = 0` para utilizar exclusivamente a preferência global.
- Mantida a leitura do código da empresa em `TSIPAR.INTEIRO`.

## 1.5 — Empresa padrão compartilhada

- Definido `ZYNACCODEMPCTA` como parâmetro inteiro da empresa padrão.
- O valor é lido exclusivamente de `TSIPAR.INTEIRO` pela entidade Jape
  `ParametroSistema`.
- Botão e evento passaram a utilizar o mesmo fallback centralizado.
- O `CODEMP` informado diretamente no fluxo permanece prioritário.

## 1.4 — Fase 2

- Adicionado evento automático `Após Inserir` na entidade Parceiro.
- Mantido o botão manual da Fase 1 como contingência.
- Adicionada resolução da empresa por `TGFPAR.CODEMP` e pelo parâmetro
  `ZYNACCODEMPCTA`.
- Separada a execução transacional do botão da execução dentro da transação
  Jape do evento.
- Adicionadas proteções contra reprocessamento, logs de contexto e mensagem de
  rollback específica para o salvamento automático.
- Atualizada a documentação de implantação, homologação e rollback.

## 1.3 — Fase 1

- Botão manual de geração de conta contábil para Cliente ou Fornecedor.
- Criação da conta, vínculo referencial e atualização da TGFPAR na mesma
  transação.
- Validações funcionais, controle de sequência, logs e mensagens ao usuário.
