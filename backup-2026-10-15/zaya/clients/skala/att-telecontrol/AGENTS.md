# Regras do projeto

## Obrigatórias

- Trabalhe somente no escopo da Issue ou OS aprovada.
- Preserve a versão Java e o sistema de build existentes.
- Não altere dependências, Gradle, frameworks ou integrações sem autorização.
- Não inclua credenciais, tokens, senhas, chaves ou dados confidenciais.
- Não faça refatorações paralelas.
- Não altere classes públicas ou FQCNs usados por metadados Sankhya sem validação.
- Mudanças de banco exigem deploy, validação e rollback.
- Avalie transação, rollback, exceções e processamento duplicado.
- Avalie TOP, versão da TOP, TIPMOV, TGFVAR, estoque, financeiro, fiscal, confirmação e cancelamento quando aplicável.
- Atualize README e CHANGELOG quando a mudança alterar operação, publicação ou regra documentada.
- Toda mudança exige revisão por Merge Request.

## Recomendações

- Use branches temporárias por OS.
- Mantenha mudanças pequenas e rastreáveis.
- Execute build e testes proporcionais ao risco.
- Registre evidências sem dados sensíveis.

Conteúdo funcional não comprovado deve ser marcado como: **Pendente de validação pela equipe**.