# Sprint / Ciclo Atual

## Objetivo Atual

Homologar o pacote unico Telecontrol, versionar a documentacao no GitLab e manter contexto suficiente para retomadas futuras.

## Estado Atual

- Telecontrol consolidado em `C:\zy-tools\workspace\Polyvox\PolyvoxTelecontrol`.
- JAR final conhecido: `build/libs/TelecontrolPolyvox.jar`.
- Build Gradle conhecido: `BUILD SUCCESSFUL`.
- Desenho geral criado em `docs/telecontrol-visao-geral.md`.
- README e visao geral foram enviados ao GitLab no MR #2, branch `master` para `main`.
- Branch remota antiga `codex/telecontrol-readme-visao-geral` foi excluida.
- Series usam `AD_TCLSER` como view de origem, `AD_TCLLOGSER` como fila/controle e `TGFPRO.REFERENCIA` como referencia preferencial do payload.
- Operacoes `AD_TCLSER` -> `AD_TCLLOGSER` e `PROCESSADO = 'S'` estao centralizadas em `SerieRepository`.
- Lista basica ganhou action de GET por `AD_TCLPRO.REFERENCIA` para persistir em `AD_TCLLTB`.
- Lista basica ganhou action de DELETE por `AD_TCLPRO.REFERENCIA` na API Telecontrol.
- Logs HTTP foram reforcados em `AD_TCLLGT`.
- Actions passaram a ter mensagens de erro mais amigaveis via `ActionMessageUtil`.
- Estrutura `/.ai/context`, `/.ai/sankhya`, `/.ai/prompts` e `/.ai/scripts` esta criada para guiar novas sessoes.

## Tarefas em Andamento

- Revisar/aprovar o MR #2 no GitLab.
- Homologar envio de series.
- Homologar envio de precos.
- Homologar GET/DELETE de lista basica.
- Validar logs em `AD_TCLLGT`.
- Confirmar FQCNs cadastrados no Sankhya.
- Validar scripts SQL/JSP de compensacao financeira.

## Proximas Acoes

- Publicar `TelecontrolPolyvox.jar` em homologacao.
- Testar `PostSerieSc` ou bridge cadastrada equivalente.
- Conferir se a view `AD_TCLSER` alimenta `AD_TCLLOGSER` sem duplicar registros.
- Conferir no `AD_TCLLGT.REQUEST` se series com referencia divergente usam `TGFPRO.REFERENCIA`.
- Testar `EnvioPrecosBt`.
- Testar `BuscaListaBasicaBt` e `DeleteListaBasicaProdutoBt` em produto selecionado de `AD_TCLPRO`.
- Validar se HTML das mensagens de action renderiza corretamente no Sankhya.
- Executar fluxo controlado de compensacao: gerar aviso, visualizar, compensar e conferir relatorio.

## Bloqueios

- A confirmar: FQCNs usados em botoes, eventos e agendadores Sankhya.
- A confirmar: dados reais para homologar `AD_TCLSER`, `AD_TCLLOGSER`, `EP_SERIE`, `EP_PRECOS` e tabelas de preco.
- A confirmar: formato real do response do GET de lista basica e se todos os itens retornam referencia de peca compativel com `AD_TCLPCA.REFERENCIA`.
- A confirmar: se `TGFPRO.REFERENCIA` e regra universal para series.
- A confirmar: periodicidade das rotinas.
- A confirmar: disponibilidade de `codex exec` nos ambientes onde os scripts `.ai/scripts` forem usados.

## Riscos

- Bridges legadas aumentam superficie do JAR, mas ainda protegem compatibilidade.
- Duplicidade de series e marcada como processada; validar se isso atende a regra de negocio.
- Insert Java na `AD_TCLLOGSER` usa numeracao baseada em `MAX(ID)` como a STP antiga; validar concorrencia em ambientes com execucoes paralelas.
- Se `TGFPRO.REFERENCIA` estiver incorreta ou desatualizada, a API pode seguir retornando `404 Produto nao encontrado`.
- `ApagarLOGBt` remove registros de `AD_TCLLGT`; usar apenas com confirmacao explicita.

## Observacoes

Atualizar este arquivo ao final de sessoes relevantes com IA.

## Atualizacao 2026-06-26 - Sessao de Contexto PVX

### Objetivo da sessao

- Revalidar e complementar a estrutura `/.ai/context` sem alterar codigo-fonte da aplicacao.

### Concluido nesta sessao

- Estrutura atual do workspace analisada.
- Arquivos obrigatorios em `.ai/context` e `.ai/sankhya` lidos antes de alteracoes.
- Evidencias revisadas em `docs`, `sql`, `jsp` e `artifacts`.
- Contexto atualizado para refletir o JAR local, dashboard OS TLC e frente de compensacao financeira.

### Proximas acoes sugeridas

- Confirmar no Sankhya os FQCNs e IDs de actions usados pelo dashboard OS.
- Homologar o JAR `TelecontrolPolyvox.jar` com casos de series, precos, OS com peca e OS sem peca.
- Validar se os scripts de compensacao financeira estao publicados e agendados.
- Confirmar se o fonte Java oficial para alteracoes fica em `C:\zy-tools\workspace\Polyvox\PolyvoxTelecontrol`.
