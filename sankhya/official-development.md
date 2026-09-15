# Desenvolvimento Sankhya baseado em fontes oficiais

## Obrigatória

- Consulte a documentação oficial aplicável ao tipo de projeto e à versão real.
- Use Recipes como exemplos, não como substitutas da análise funcional,
  transacional, de segurança ou compatibilidade.
- Classifique o projeto como Add-on Studio, módulo Java legado, DWF/JSP/HTML5,
  integração por API ou outro modelo comprovado antes de escolher APIs e template.
- Preserve JDK, Gradle Wrapper, SDK, identificadores publicados, FQCNs e contratos.
- Recursos beta ou early access exigem validação de versão, risco documentado e a
  marcação `Pendente de validação pela equipe.`

## Persistência e transação

- Prefira Jape/JapeWrapper para entidades mapeadas. NativeSql exige justificativa,
  parâmetros nomeados e liberação segura de recursos.
- Abra JapeSession somente quando necessário e feche em `finally`.
- Mutações exigem transação ativa. Use o mecanismo do contexto; não aninhe ou
  duplique transação já controlada por serviço, listener, procedure ou ação.
- Em controle manual, use `SessionHandle` e `execWithTX` conforme a versão e
  propague a exceção para permitir rollback.
- Não engula exceções nem produza sucesso implícito após falha.

## Documentos

- Confirmação, faturamento, cancelamento e geração devem usar serviço ou API
  pública compatível. Não simule o processo com DML direto em TGFCAB, TGFITE ou
  TGFVAR.
- Valide TOP, versão da TOP, TIPMOV, estoque, financeiro, fiscal, confirmação,
  cancelamento, idempotência e processamento duplicado.
- Evite efeito externo irreversível antes da confirmação da transação.

## Add-ons e serviços

- Novo Add-on Studio parte do template oficial do componente.
- Use prefixo próprio e único; não use `AD_` em novos objetos.
- Não defina valor padrão em campo adicional sobre tabela nativa sem compatibilidade
  comprovada.
- Não mantenha estado mutável de requisição em campos globais ou estáticos.
- Respeite classloader e APIs públicas; dependência em classe interna exige
  contrato e compatibilidade comprovados.
- Não crie threads manualmente dentro do contêiner.

## Segurança, desempenho e qualidade

- Externalize URLs, credenciais e configurações; não registre segredos ou dados
  pessoais desnecessários.
- Evite N+1, consultas sem filtro, carga integral e uma chamada ao banco por item.
- Exija testes proporcionais ao risco, revisão, análise estática quando disponível,
  README, CHANGELOG, publicação, validação e rollback.

## Referências oficiais

- https://developer.sankhya.com.br/recipes
- https://developer.sankhya.com.br/docs/guia-de-boas-praticas
- https://developer.sankhya.com.br/docs/jape
- https://developer.sankhya.com.br/docs/add-on-studio
- https://developer.sankhya.com.br/docs/transa%C3%A7%C3%A3o-manual-para-a%C3%A7%C3%B5es
- https://developer.sankhya.com.br/docs/faq
- https://developer.sankhya.com.br/llms.txt
