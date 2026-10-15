# AGENTS.md Global - Annayran / Sankhya

## Perfil de atuação

O usuário trabalha principalmente com customizações no ERP Sankhya, usando Java 8, Oracle SQL, PL/SQL, Jape, NativeSql, JdbcWrapper, EntityFacade, DynamicVO, HTML5/JSP, dashboards, actions, jobs, integrações e tabelas customizadas AD_.

Responda sempre em português do Brasil.

## Regra principal

Não trate Sankhya como um sistema CRUD Java/SQL comum.

Antes de propor código, sempre considere:

* processo de negócio do ERP;
* documento de origem;
* documento de destino;
* TOP;
* tipo de movimento;
* estoque;
* financeiro;
* fiscal;
* confirmação/liberação;
* transação;
* integrações;
* tabelas nativas;
* tabelas customizadas AD_;
* impacto em telas, dashboards, Jasper, Flow e rotinas agendadas.

Quando algo não puder ser confirmado, registre como "A confirmar".

---

# Regras base de Sankhya

## Documentos / Notas / Pedidos

Sempre que a demanda envolver nota, pedido, transferência, devolução, remessa, faturamento, compra, venda, movimentação interna ou geração de documento:

1. Identifique TGFCAB.
2. Identifique TGFITE.
3. Verifique se há documento de origem.
4. Verifique se há documento de destino.
5. Verifique se há necessidade de vínculo em TGFVAR.
6. Verifique TOP/TGFTOP.
7. Verifique TIPMOV.
8. Verifique impacto em estoque.
9. Verifique impacto financeiro.
10. Verifique impacto fiscal.
11. Verifique confirmação/liberação.
12. Verifique integrações dependentes.
13. Verifique campos AD_ envolvidos.

Nunca crie uma nota apenas inserindo registros em TGFCAB/TGFITE sem validar o padrão do projeto ou rotina Sankhya apropriada.

Nunca simule confirmação de nota apenas alterando status/campo manualmente.

Nunca ignore quantidade pendente, entregue, faturada ou vinculada quando houver origem/destino.

---

# TGFVAR

A TGFVAR deve ser analisada sempre que houver relação entre documento de origem e documento de destino.

Exemplos de cenários em que a TGFVAR deve ser investigada:

* faturamento de pedido;
* geração de nota a partir de pedido;
* transferência;
* devolução;
* remessa;
* cancelamento com documento relacionado;
* rastreabilidade entre documentos;
* consulta de documentos vinculados;
* baixa/pendência de itens;
* dashboards ou relatórios que localizam documento filho/pai.

Antes de alterar ou criar documento, verificar:

* NUNOTA de origem;
* NUNOTA de destino;
* sequência de item de origem;
* sequência de item de destino;
* quantidade vinculada;
* regra de pendência;
* regra de entrega/faturamento;
* impacto em consultas existentes.

Se não houver certeza sobre os campos e comportamento da TGFVAR no projeto, marque como "A confirmar" e investigue exemplos existentes no código antes de implementar.

---

# Transação

Antes de qualquer alteração que grave dados:

1. Verifique se a rotina já roda dentro de transação gerenciada pelo Sankhya.
2. Verifique padrão do projeto para JapeSession, JdbcWrapper, NativeSql, EntityFacade ou serviços internos.
3. Não faça commit/rollback manual sem evidência de que o projeto usa esse padrão.
4. Em erro crítico, lance exceção para permitir rollback.
5. Não engula exceções.
6. Não deixe alteração parcial em TGFCAB, TGFITE, TGFVAR, TGFFIN, TGFEST ou tabelas AD_.
7. Feche recursos como ResultSet, NativeSql, JdbcWrapper e conexões conforme padrão do projeto.
8. Evite múltiplas transações independentes na mesma regra de negócio.
9. Evite integração externa no meio da transação sem avaliar risco.

Quando houver dúvida, parar e registrar "A confirmar".

---

# Acesso a dados

## SQL / Oracle

* Usar bind parameters sempre que possível.
* Evitar concatenação insegura de SQL.
* Preservar aliases usados por dashboards, JSPs, Jasper, relatórios ou integrações.
* Cuidado com LEFT JOIN que vira INNER JOIN por filtro no WHERE.
* Cuidado com cardinalidade e duplicidade.
* Cuidado com queries dentro de loop.
* Cuidado com update/delete em tabelas nativas.
* Antes de alterar query, entender quem consome os aliases e o resultado.

## Java / Sankhya

* Manter compatibilidade com Java 8.
* Tratar BigDecimal corretamente.
* Não comparar BigDecimal com `==`.
* Tratar null antes de usar DynamicVO, BigDecimal, String ou datas.
* Preservar package e padrão do projeto.
* Não mudar assinatura pública sem necessidade.
* Não adicionar dependência nova sem justificar.
* Não refatorar legado fora do escopo.
* Usar logs úteis e objetivos.
* Não expor dados sensíveis em log.

---

# TOP / Fluxo de negócio

Nunca assumir que uma TOP se comporta igual a outra.

Sempre verificar:

* CODTIPOPER;
* TIPMOV;
* empresa;
* parceiro;
* natureza;
* centro de resultado;
* local;
* atualização de estoque;
* geração financeira;
* confirmação;
* permissões;
* integração externa;
* campos AD_ na TOP;
* regras customizadas por TOP.

---

# Checklist obrigatório antes de codar

Antes de implementar qualquer demanda Sankhya, responda mentalmente ou documente:

1. Qual processo Sankhya está envolvido?
2. Quais tabelas nativas são impactadas?
3. Quais tabelas AD_ são impactadas?
4. Existe documento origem?
5. Existe documento destino?
6. Precisa TGFVAR?
7. Existe TOP específica?
8. Existe transação?
9. Existe risco de alteração parcial?
10. Existe impacto em estoque?
11. Existe impacto financeiro?
12. Existe impacto fiscal?
13. Existe integração dependente?
14. Existe dashboard/Jasper/tela consumindo a query?
15. Existe padrão semelhante no projeto?

Se qualquer item crítico não estiver claro, marque como "A confirmar" antes de codar.

---

# Anti-padrões proibidos

Evite:

* tratar Sankhya como CRUD comum;
* inserir nota direto em TGFCAB/TGFITE sem validar rotina/padrão;
* ignorar TGFVAR em origem/destino;
* confirmar nota alterando campo manualmente;
* fazer commit/rollback manual sem entender transação;
* engolir exception;
* deixar NativeSql/JdbcWrapper/ResultSet aberto;
* concatenar SQL com entrada externa;
* alterar alias sem verificar consumo;
* duplicar item sem validar quantidade pendente;
* atualizar estoque/financeiro manualmente sem regra clara;
* assumir que TOPs diferentes têm o mesmo comportamento;
* refatorar fora do escopo;
* criar biblioteca nova sem necessidade;
* remover código legado sem autorização.

---

# Formato obrigatório de resposta em demandas

Antes de codificar, responda:

## Entendimento

## Arquivos provavelmente envolvidos

## Regras Sankhya impactadas

## Pontos a confirmar

## Plano técnico

## Riscos

Depois de codificar, responda:

## Resumo do que foi feito

## Arquivos alterados

## Regras atendidas

## Testes recomendados

## Riscos restantes

## Atualizações feitas em `/.ai/context` ou `/.ai/sankhya`
