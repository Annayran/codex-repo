# apontamento-producao

## Objetivo

Pendente de validação pela equipe.

## Tecnologias identificadas

Java, Gradle, SQL, HTML5.

## Desenvolvimento

Use o build e a versão Java declarados pelo projeto. Não atualize dependências sem autorização.

O código novo deste projeto utiliza o prefixo
`br.com.zaya.polyvox.apontamentoproducao`. Integrações Sankhya ficam sob
`sankhya`, separadas por tipo de ponto de entrada.

As dependências locais da Sankhya são lidas, nesta ordem, de:

1. propriedade Gradle `sankhyaDependenciesDir`;
2. variável de ambiente `SANKHYA_DEPENDENCIES_DIR`;
3. `C:/dev/zaya/platform/modulos-sankhya/Dependencias`.

Execute o build com `gradlew.bat build`. O alvo Java permanece na versão 8.

## Compatibilidade Sankhya

O FQCN legado
`br.com.zaya.apontamentoProducao.scheduleActions.ApontamentoProducaoSchedule`
foi preservado como adaptador. Novos cadastros devem utilizar
`br.com.zaya.polyvox.apontamentoproducao.sankhya.schedulers.ApontamentoProducaoScheduler`.

A substituição do FQCN cadastrado e a remoção futura do adaptador estão
**Pendentes de validação pela equipe**.

## Banco de dados

Toda mudança deve possuir deploy, validação e rollback. Quando não aplicável, registre a justificativa na Merge Request.

## Publicação

Pendente de validação pela equipe.

## Rollback

Pendente de validação pela equipe.
