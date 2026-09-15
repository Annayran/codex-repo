# Add-on Studio e metadados

## Obrigatória

1. Comece pelo template oficial baixado para o componente registrado; não
   reconstrua o Add-on Studio a partir do template Java legado.
2. Preserve Gradle Wrapper, SDK, `appKey`, `group`, nomes publicados e versão
   mínima compatíveis com o ambiente.
3. Externalize `appKey`, `WILDFLY_HOME`, URLs e segredos. Nunca os versione.
4. Armazene definições em `model/src/main/resources/datadictionary` e valide os
   XMLs com o `metadados.xsd` no IntelliJ.
5. Use um XML por entidade ou responsabilidade principal; não mantenha um XML
   monolítico.
6. Use prefixo próprio, curto e único. Não use `AD_` em novos objetos.
7. Use `nativeTable` para extensão comprovada de tabela nativa; não crie tabela
   paralela apenas para campos adicionais sem justificativa funcional.
8. Mantenha scripts não cobertos por metadados em `dbscripts`. Depois de
   publicados, scripts de versão são imutáveis; correções entram em nova versão.
9. Toda mudança de banco deve possuir implantação, validação, rollback e
   homologação compatíveis com a versão Sankhya.
10. Não publique ou importe metadados em produção sem confirmar origem, versão,
    backup restaurável e efeitos sobre objetos existentes.

## Roteiro de análise

- Classifique o projeto: Add-on Studio, Java legado, DWF/HTML5 ou integração.
- Confirme versão Sankhya, JDK, Gradle, plugin e módulos reais.
- Inventarie tabelas, campos, instâncias, menus, telas, eventos e scripts sem
  reproduzir conteúdo sensível.
- Compare nomes e prefixos com o dicionário da versão comprovada.
- Valide referências cruzadas, ordem dos scripts e compatibilidade de rollback.
- Registre fatos, inferências e itens pendentes separadamente.

## Referências oficiais

- https://developer.sankhya.com.br/docs/add-on-studio
- https://developer.sankhya.com.br/docs/03_conf_addonstudio
- https://developer.sankhya.com.br/docs/03_estrutura-do-projeto
- https://developer.sankhya.com.br/docs/01_dicionario-de-dados
