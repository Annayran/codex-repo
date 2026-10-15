# Plano de migração do workspace

Este documento é apenas um plano. Nenhuma movimentação, cópia, renomeação ou exclusão foi executada.

## Fase 0: backup e segurança

1. Congelar mudanças durante cada janela de migração.
2. Identificar owner, criticidade e status operacional de cada item.
3. Registrar `git status`, branch, origin, tags e commits de cada repositório.
4. Preservar alterações locais dos quatro repositórios sujos sem commit automático.
5. Criar backup externo verificável e registrar SHA-256/contagem de arquivos.
6. Revisar os caminhos listados em `SECURITY_RISKS.md`, sem copiar valores para relatórios.
7. Rotacionar credenciais confirmadas antes de publicar qualquer novo remoto.
8. Definir retenção de ZIPs, legados e duplicados.

Gate: backup restaurável, riscos críticos tratados e autorização formal.

## Fase 1: estrutura vazia

1. Criar somente as pastas aprovadas sob `C:\dev\tools` e `C:\dev\zaya`.
2. Criar padrões em `engineering`: README, AGENTS.md, `.gitignore`, template de Merge Request e checklist Sankhya.
3. Definir convenção de nomes, ownership, visibilidade e remotes GitLab.
4. Definir modelo de banco com scripts separados de deploy, validação e rollback.

Gate: revisão da árvore e templates sem conteúdo de cliente.

## Fase 2: projeto piloto

Piloto recomendado: `Polyvox\Apontamento Produção` para `C:\dev\zaya\clients\polyvox\apontamento-producao`.

1. Explicar e preservar as cinco entradas atuais do Git status.
2. Criar backup e registrar commit/branch/origin sem alterar histórico.
3. Relocar em janela controlada preservando `.git` e a solução completa Java + HTML5 + SQL.
4. Adicionar/validar README, AGENTS.md e template de Merge Request em mudança separada e aprovada.
5. Validar Java 8, Gradle, build, SQL deploy/validação/rollback e smoke test Sankhya.
6. Testar rollback retornando ao caminho antigo sem perda.

Gate: build reproduzível, Git íntegro, banco documentado e aceite do owner.

## Fase 3: componentes compartilhados

1. Confirmar consumidores e ciclos de release de `sankhya-om-plataforma-main` e `BarramentoRegrasLidon`.
2. Manter módulos publicados juntos quando formarem uma única plataforma.
3. Separar biblioteca apenas com API estável, versionamento independente, testes e ao menos dois consumidores reais.
4. Substituir caminhos absolutos por configuração documentada em mudança futura específica.
5. Publicar dependências por versão; não referenciar builds locais arbitrários.

Gate: mapa de consumidores e estratégia de compatibilidade aprovados.

## Fase 4: projetos ativos

1. Migrar por cliente, nunca todos simultaneamente.
2. Priorizar projetos com Git limpo; depois tratar repositórios sujos e, por último, projetos sem Git.
3. Manter solução publicável completa no mesmo repositório.
4. Para projeto sem Git, criar repositório somente com autorização, após backup e baseline revisada.
5. Validar build, artefato, banco, integração Sankhya e rollback por projeto.

Gate: aceite funcional e técnico de cada cliente antes do próximo lote.

## Fase 5: legados e duplicados

1. Comparar hashes, árvores, histórico Git e regras funcionais.
2. Declarar canônico apenas com owner e evidência de implantação.
3. Copiar itens não canônicos para quarentena de archive; manter origem até aceite.
4. Tratar ZIPs como binários fechados, com checksum e retenção.
5. Não excluir nada nesta fase sem backup restaurável e autorização separada.

Gate: relatório de comparação e retenção assinado.

## Fase 6: encerramento do workspace antigo

1. Confirmar que todos os itens da matriz têm resultado e owner.
2. Executar varredura final de caminhos absolutos e referências ao workspace antigo.
3. Validar remotes, pipelines, builds, deploys e documentação.
4. Manter o workspace antigo somente leitura durante período de observação.
5. Encerrar ou excluir apenas em tarefa futura, com autorização explícita e teste de restauração.

## Dez primeiros movimentos propostos

1. Aprovar owners e classificação dos 31 registros da matriz.
2. Resolver os riscos críticos de credenciais em JSPs/configurações.
3. Documentar as alterações locais dos quatro repositórios sujos.
4. Configurar ou justificar a ausência de origin nos dois repositórios CriacaoNotaEst.
5. Aprovar a estrutura vazia de `C:\dev\zaya` e `C:\dev\tools`.
6. Aprovar templates de README, AGENTS.md, banco e Merge Request.
7. Preparar backup restaurável do piloto Polyvox/Apontamento Produção.
8. Executar ensaio documentado de rollback do piloto.
9. Migrar e validar o piloto em janela autorizada.
10. Fazer retrospectiva e ajustar a matriz antes dos componentes compartilhados.

## Rollback geral

A origem permanece intacta até o aceite. Em falha, interromper consumidores do novo caminho, registrar evidências, restaurar configuração anterior, validar origem e remover apenas o destino incompleto mediante autorização.