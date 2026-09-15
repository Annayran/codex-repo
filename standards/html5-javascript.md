# HTML5 e JavaScript

## Obrigatória

- Não armazenar credenciais no navegador, JSP, JavaScript ou HTML.
- Não implementar regra crítica exclusivamente no JavaScript.
- Tratar e validar dados também no servidor.
- Preservar contratos, IDs e aliases consumidos pela solução.
- Documentar compatibilidade e procedimento de publicação.
- Não oferecer ao navegador função genérica que aceite SQL arbitrário.
- Não formar SQL com DOM, URL, cookie, parâmetro ou entrada do usuário.
- Não expor `JSESSIONID` ou token em URL, log ou armazenamento local.
- Não usar `no-cors` para contornar erro de CORS.
- Restringir serviços, módulos, actions, procedures e entidades por allowlist no
  servidor.
- Validar método, URL, timeout, cancelamento, `response.ok`, status funcional e
  schema da resposta.
- Propagar ou converter rejeições; um `catch` não pode apenas escrever no console.
- Declarar atomicidade e sucesso parcial em operações múltiplas.
- Evitar seletores internos/ofuscados, temporizadores fixos e dependência de
  `window.parent.parent` sem compatibilidade comprovada.
- Não inserir valor dinâmico com `innerHTML`; preferir APIs seguras do DOM.
- Restringir `postMessage` por `targetOrigin`, origem recebida e schema.
- Fixar dependências em versão imutável; não usar `main` ou `latest` em produção.
- Gerar artefato minificado por build reproduzível a partir da fonte versionada.

## Recomendada

Separar transporte HTTP, adaptação Sankhya, DOM e regra de aplicação; limitar
dependências globais; registrar erros sem dados sensíveis; testar carregamento,
permissões, rejeições, concorrência, payloads por versão e estados vazios.

## Referência comunitária SankhyaJX

O repositório https://github.com/wansleynery/SankhyaJX pode ser usado somente
como catálogo de ideias. O snapshot analisado foi `main@d097c25`.

Ele não é documentação oficial e, na análise realizada, não possuía licença,
testes, build reproduzível ou matriz de compatibilidade. Não copiar ou distribuir
seu código sem validação jurídica e técnica. Serviços observados como
`DbExplorerSP`, `CRUDServiceProvider`, `DatasetSP`, `ActionButtonsSP` e
`ManutencaoPreferenciasSP` não são automaticamente APIs públicas.

Matriz oficial de navegadores suportados: **Pendente de validação pela equipe**.
