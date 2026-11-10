# Guia de Uso com IA

## Como iniciar uma nova conversa

Ao iniciar uma nova conversa, usar:

```txt
Leia os arquivos em `/.ai/context` antes de propor alteracoes.
Leia tambem o `AGENTS.md` da raiz do projeto quando existir.
Considere estes arquivos como fonte principal de contexto do projeto.
Nao altere codigo sem explicar antes o plano.
Nao invente informacoes nao confirmadas.
Quando algo nao estiver claro, marque como "A confirmar".
Tudo oque for criar usar o diretorio C:\zy-tools\workspace\Polyvox
```

## Diretorio Operacional

- Para novas criacoes, usar `C:\zy-tools\workspace\Polyvox` quando esse caminho estiver disponivel no ambiente.
- Se a sessao abrir em outro workspace, confirmar antes de criar arquivos fora do diretorio solicitado.
- Nesta atualizacao, os arquivos de contexto foram preservados no repositorio ativo da sessao.

## Ordem Recomendada de Leitura

1. `checkpoint.md`
2. `architecture.md`
3. `stack.md`
4. `patterns.md`
5. `business-rules.md`
6. `decisions.md`
7. `current-sprint.md`
8. `known-issues.md`

## Como Pedir Alteracoes

Prefira pedidos com objetivo, escopo e restricoes explicitas:

```txt
Leia `/.ai/context` e implemente [objetivo].
Restrinja as alteracoes a [arquivos ou modulos].
Antes de editar, explique o plano.
Depois de editar, informe o que mudou e como validar.
```

## Como Atualizar o Contexto

Ao final de uma sessao relevante com IA, pedir:

```txt
Atualize `/.ai/context` com as decisoes, mudancas, problemas conhecidos e proximo checkpoint desta sessao.
Nao registre hipoteses como fatos.
```

## Regras para a IA neste Projeto

- Ler o `AGENTS.md` da raiz quando existir; ele define regras globais de atuacao para demandas Sankhya.
- Ler `/.ai/context` antes de mudancas relevantes.
- Tratar informacoes marcadas como "A confirmar" como hipoteses pendentes.
- Nao apagar arquivos existentes sem pedido explicito.
- Preservar alteracoes do usuario no workspace.
- Nao alterar scripts SQL/JSP sem plano quando a mudanca for significativa.
- Registrar decisoes tecnicas em `decisions.md`.
- Registrar problemas conhecidos em `known-issues.md`.
- Atualizar `checkpoint.md` quando o estado do projeto mudar.
- Diferenciar artefatos versionados no repositorio de arquivos externos, como `seriesenvio.jar`.
- Usar `C:\zy-tools\workspace\Polyvox` como diretorio preferencial para novos arquivos quando disponivel.

## Estado Atual Importante

Em 2026-06-22, o repositorio contem scripts SQL/PLSQL e JSP para compensacao financeira de notas de devolucao no Sankhya. O artefato `seriesenvio.jar` foi citado pelo usuario e analisado apenas como contexto externo; sua relacao com esta demanda deve ser confirmada antes de ser usado como base de decisao.

Em 2026-06-26, o workspace tambem contem artefatos locais confirmados do Telecontrol/PVX:

- `artifacts/telecontrol/TelecontrolPolyvox.jar`
- `jsp/dashboard_os_tlc/dashboard_os_tlc.jsp`
- `jsp/dashboard_os_tlc/css/dashboardOsTlc.css`
- `jsp/dashboard_os_tlc/dashboard_os_tlc.zip`
- `docs/telecontrol-visao-geral.md`
- `docs/telecontrol-pagamento-os-resgate.md`

Antes de alterar Java, confirmar se o fonte oficial esta em `C:\zy-tools\workspace\Polyvox\PolyvoxTelecontrol` ou em outro repositorio. O JAR local deve ser usado como evidencia de classes/pacotes, nao como substituto de revisao de fonte quando houver mudanca funcional.

## Separacao de Evidencias

Ao responder demandas futuras, separar explicitamente:

- Confirmado por codigo, SQL, JSP, JAR ou documentacao versionada.
- Inferido por padrao Sankhya.
- A confirmar.

## Formato minimo antes de alterar arquivos

```md
## Entendimento
## Arquivos provavelmente envolvidos
## Regras Sankhya impactadas
## Pontos a confirmar
## Plano tecnico
## Riscos
```

## Formato minimo depois de alterar arquivos

```md
## Resumo do que foi feito
## Arquivos alterados
## Regras atendidas
## Testes recomendados
## Riscos restantes
## Atualizacoes feitas em `/.ai/context` ou `/.ai/sankhya`
```

## Prompts e scripts disponiveis

- `/.ai/prompts/init-context.md`: recriar ou atualizar a estrutura inicial de contexto.
- `/.ai/prompts/update-context.md`: atualizar contexto sem alterar codigo.
- `/.ai/prompts/implement-demand.md`: implementar demanda lendo contexto e guias Sankhya.
- `/.ai/prompts/diagnose-problem.md`: diagnosticar problema sem alterar codigo.
- `/.ai/prompts/review-changes.md`: revisar alteracoes recentes com foco em riscos Sankhya.

Scripts PowerShell:

- `/.ai/scripts/ctx-init.ps1`
- `/.ai/scripts/ctx-update.ps1`
- `/.ai/scripts/ctx-dev.ps1 -DemandFile caminho\demanda.md`
- `/.ai/scripts/ctx-diagnose.ps1 -ProblemFile caminho\problema.md`
- `/.ai/scripts/ctx-review.ps1`
