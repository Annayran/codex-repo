# Problemas Conhecidos

## Bugs

- A confirmar. Não há testes automatizados ou issues locais suficientes para classificar bugs funcionais com segurança.

## Débitos Técnicos

- Gradle wrapper incompleto: falta `gradle/wrapper/gradle-wrapper.jar`.
- `settings.gradle.kts` inclui `plugin`, mas a pasta `plugin` não existe no clone atual.
- Existem dois arquivos de build (`build.gradle` e `build.gradle.kts`) com configurações diferentes.
- `org.example.Main` parece ser resíduo de template de IDE.
- Código principal aparenta ter sido decompilado, conforme comentário no arquivo Java.
- Uso de `System.out.println` e `printStackTrace` em vez de logging estruturado.
- SQL dinâmico por concatenação em updates/consultas internas.

## Pontos de Atenção

- Fechamento de `rsApontamentos` ocorre em `try/catch/finally`; revisar antes de mexer para evitar fechamento duplicado ou `NullPointerException`.
- Em `AtualizarSerie`, `nativeSql` pode estar nulo se a falha ocorrer antes da inicialização; revisar antes de refatorar tratamento de exceção.
- O update em `TPRSERPA` concatena `SERIEPA = ` com valor textual sem aspas explícitas; confirmar tipo do campo e comportamento esperado.
- Mensagens de erro são concatenadas em SQL para `TPRAPO.OBSERVACAO`; aspas no texto da exceção podem quebrar SQL.

## Riscos Operacionais

- Processamento executa autenticado como `SUP`; impacto de auditoria e permissões precisa ser confirmado.
- Falhas parciais podem deixar apontamentos criados/alterados sem status claramente atualizado na origem `AD_VGFAPO`.
- Dependência de APIs Sankhya e contexto de sessão torna difícil testar fora do ambiente real.
- Dependências locais precisam existir no caminho configurado para compilar.

## A Confirmar

- Se há issues remotas no GitLab não clonadas/localmente documentadas.
- Se o relatório em `build/reports/problems/problemas-report.html` é relevante para o estado atual ou artefato antigo.
- Se `old/apontamento7.rar` é referência histórica necessária ou pode ser arquivado fora do repositório.