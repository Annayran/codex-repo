# Mapa de dependências

## Dependências entre projetos

- Skala/sankhya-om-plataforma-main é uma plataforma multi-módulo com SKL-Core-Action, SKL-Core-Model e SKL-Core-VC; a direção e publicação exatas entre módulos precisam ser confirmadas.
- As múltiplas árvores em Skala/_pacotes_codex_para_consolidar reutilizam classes/actions e scripts SQL de mesma origem aparente; não foi assumida equivalência.
- Creative Like contém momento-negociacao e um repositório separado PersonalizacoesCompraLike; o vínculo de release precisa ser confirmado.
- TennisPlace e CRM Bonus/TennisPlace/TennisPlace possuem estrutura e artefato CRMBonusIntegration semelhantes.
- Polyvox/PolyvoxTelecontrol possui cópia PolyvoxTelecontrol.codex-backup-20260611-1605.
- Lidon/CriacaoNotaEst reaparece dentro de lidon-lacamentoos-main 2.

## JARs compartilhados

Foram encontrados 55 JARs locais. A maior parte está em uild/libs, out/artifacts ou gradle/wrapper. Artefatos relevantes incluem SKL Core, CRMBonusIntegration, TelecontrolPolyvox, ImportadorPVX, BarramentosRegrasLidon e CriacaoNotaEst. Não foi possível confirmar quais JARs são consumidos por outros projetos sem validar os ambientes de implantação.

## Pastas referenciadas e caminhos absolutos

Há referências com caminho absoluto em arquivos Gradle, documentação, testes, Java e JSP. Os builds Java usam com frequência dependências locais do ambiente Sankhya; a portabilidade deve ser validada. Não alterado nesta fase.

## Módulos Sankhya

Referências encontradas: JapeSession (404 arquivos), NativeSql (288), JdbcWrapper (374), EntityFacade (389), DynamicVO (710), TGFCAB (281), TGFITE (235), TGFVAR (77), TGFTOP (117), TGFFIN (28) e TGFEST (54). Esses números indicam presença, não dependência exclusiva nem correção funcional.

## HTML5 compartilhado

Dashboards e componentes HTML5/JSP aparecem em Creative Like, CRM Bonus/TennisPlace, MA Hospitalar, Polyvox, Portomag e TennisPlace. Existem ZIPs de componentes HTML5, mas não foram descompactados.

## Dependências não confirmadas

- Versões exatas das bibliotecas Sankhya instaladas nos ambientes.
- Compatibilidade binária dos JARs compilados.
- Ownership dos artefatos e destino de deploy.
- Relação de precedência entre cópias Skala, Lidon, TennisPlace e Polyvox.
- Uso real de pastas out, uild, in e arquivos ZIP em produção.