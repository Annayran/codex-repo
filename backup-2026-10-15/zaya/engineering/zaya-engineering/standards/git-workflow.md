# Fluxo Git

## Fluxo obrigatório

1. Criar ou vincular uma Issue ou OS.
2. Criar branch temporária a partir da base aprovada.
3. Desenvolver somente o escopo autorizado.
4. Abrir Draft Merge Request cedo.
5. Executar testes aplicáveis e anexar evidências.
6. Solicitar revisão técnica e funcional quando necessária.
7. Homologar no ambiente definido.
8. Obter aprovação formal.
9. Realizar merge conforme política do repositório.
10. Criar tag quando a mudança for publicável.
11. Publicar usando procedimento documentado e rollback disponível.

## Nomenclatura de branches

- `feature/os-<numero>-<descricao>`
- `fix/os-<numero>-<descricao>`
- `docs/<descricao>`
- `chore/<descricao>`

Use descrição curta, em minúsculas e separada por hífen. Não inclua nome de cliente quando o repositório já determina esse contexto.

## Regras

- Não trabalhar diretamente na branch protegida.
- Não misturar refatorações paralelas com a demanda.
- Não incluir credenciais ou artefatos locais.
- Não reescrever histórico compartilhado sem autorização.

Políticas de squash, merge commit, branches protegidas e versionamento semântico: **Pendente de validação pela equipe**.