# Java

## Obrigatória

- Preservar a versão Java declarada pelo projeto.
- Novos projetos Sankhya devem iniciar com Java 8, salvo compatibilidade diferente comprovada e aprovada para o ambiente de destino.
- Configurar `sourceCompatibility` e `targetCompatibility` explicitamente no build.
- Usar pacotes em minúsculas conforme `standards/project-structure.md`.
- Tratar `null`, recursos e exceções explicitamente.
- Não comparar `BigDecimal` com `==`.
- Usar logs objetivos sem dados sensíveis.
- Não adicionar dependência sem justificativa e avaliação de compatibilidade.
- Não alterar assinatura pública fora do escopo.
- Não alterar FQCN usado por integração ou cadastro externo sem estratégia de compatibilidade e homologação.
- Manter transações coerentes com o ambiente de execução.
- Manter recursos carregados por classe em `src/main/resources`, no caminho correspondente ao pacote.

## Recomendada

Usar nomes claros, funções pequenas, testes proporcionais ao risco e dependências versionadas de modo reproduzível.

Padrão corporativo de formatação e ferramenta de análise estática: **Pendente de validação pela equipe**.

Consulte também `standards/gradle.md` para builds Gradle reproduzíveis.
