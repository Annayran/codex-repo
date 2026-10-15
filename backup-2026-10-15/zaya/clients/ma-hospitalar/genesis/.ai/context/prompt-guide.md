# Guia de Uso com IA

## Como iniciar uma nova conversa

Ao iniciar uma nova conversa, usar:

```txt
Leia os arquivos em `/.ai/context` antes de propor alterações.
Considere estes arquivos como fonte principal de contexto do projeto.
Não altere código sem explicar antes o plano.
Não invente informações não confirmadas.
Quando algo não estiver claro, marque como “A confirmar”.
```

## Fluxo recomendado para futuras sessões

1. Ler todos os arquivos em `/.ai/context`.
2. Verificar se houve mudança no código-fonte desde o último checkpoint.
3. Confirmar o objetivo da sessão.
4. Propor plano curto antes de alterar código.
5. Implementar mudanças pequenas e verificáveis.
6. Rodar testes/build quando existirem comandos identificáveis.
7. Atualizar `current-sprint.md`, `known-issues.md`, `decisions.md` e `checkpoint.md` quando houver informação nova relevante.

## Regras para a IA neste projeto

- Não alterar código-fonte sem necessidade clara.
- Não apagar arquivos existentes.
- Não assumir stack, arquitetura ou regras sem evidência.
- Preferir evidências do código, documentação versionada e comandos executados.
- Registrar como "A confirmar" tudo que depender de validação humana, OCR ou acesso a fontes ainda ausentes.
- Preservar decisões importantes em `decisions.md`.

## Contexto inicial conhecido

- Workspace alvo: `C:\zy-tools\workspace\MA Hospitalar`.
- Repositório analisado originalmente: `C:\Users\annayran.ferreira\Documents\Sankhya - MA Hospitalar`.
- Estado inicial do Git analisado: sem commits em `master`.
- Documento mencionado: `2764 - Definição de Escopo de Customização - API Genesis.pdf`.
- Limitação: PDF sem texto extraível automaticamente na análise inicial.
