# Regras do projeto Java/Sankhya

## Obrigatórias

- Trabalhe somente no escopo da Issue ou OS aprovada.
- Confirme diretório, raiz Git, branch, origin e status antes de alterar o projeto.
- Preserve Java 8, salvo compatibilidade diferente comprovada e aprovada.
- Use o prefixo `br.com.zaya.<cliente>.<solucao>` definido no README.
- Use MVC conforme `standards/mvc.md`; controller e view não contêm regra
  funcional crítica ou persistência.
- Crie somente os pacotes necessários à solução.
- Não altere FQCN cadastrado no Sankhya sem adaptador, homologação e rollback.
- Não inclua JARs proprietários, credenciais ou dados específicos do cliente.
- Mudanças de banco exigem deploy, validação e rollback.
- Atualize README e CHANGELOG quando a operação, publicação ou arquitetura mudar.
- Execute o build e registre testes, riscos e pendências na Merge Request.
- Gere o JAR como `ZAYA-<SIGLA>-V<MAJOR>.<MINOR>.jar` pelo Gradle; não publique
  `SNAPSHOT` nem renomeie o artefato manualmente.
- Importe no IntelliJ como projeto Gradle e não versione metadados locais da IDE.
- Não execute commit, push, pull, merge, rebase ou checkout sem autorização explícita.

## Sankhya

- Avalie JapeSession, transações, rollback, exceções e processamento duplicado.
- Avalie TOP, versão da TOP, TIPMOV, TGFVAR, estoque, financeiro, fiscal,
  confirmação e cancelamento quando aplicável.
- Não invente regras funcionais. Use **Pendente de validação pela equipe** para
  conteúdo ainda não comprovado.

## Referências

Aplicam-se os padrões corporativos mantidos em `zaya-engineering`, especialmente
`standards/project-structure.md`, `standards/java.md` e `standards/gradle.md`.
