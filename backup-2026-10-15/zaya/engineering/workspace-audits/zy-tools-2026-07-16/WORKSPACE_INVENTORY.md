# Inventário técnico do workspace Zaya.IT

Data do levantamento: 2026-07-16  
Diretório autorizado: $root  
Modo: leitura; somente este diretório de relatório foi criado.

## Resumo geral

- Repositórios Git: 5
- Raízes de projeto/build mapeadas: 29
- Projetos/raízes sem Git: 26
- Candidatos explícitos a duplicação: 14
- Arquivos ZIP: 20
- JARs locais: 55, incluindo wrappers e artefatos compilados
- Fontes: 1.756 Java, 538 SQL, 26 JSP, 308 HTML, 34 JavaScript e 21 CSS
- Artefatos compilados: 2.739 arquivos .class
- Maven: nenhum pom.xml encontrado; Gradle predomina.
- Java: Java 8 identificado nos builds inspecionados; demais casos marcados como A confirmar.

## Repositórios encontrados

| Raiz | Branch | Origin | Status |
|---|---|---|---|
| Creative Like/PersonalizacoesCompraLike | main | https://gitlab.com/zaya9450252/PersonalizacoesCompraLike.git | limpo |
| Lidon/CriacaoNotaEst | master | não configurado | 13 entradas no status |
| Lidon/lidon-lacamentoos-main 2/CriacaoNotaEst | master | não configurado | 8 entradas no status |
| Lidon/lidon-lacamentoos-main 2/lidon-lacamentoos-main | master | https://gitlab.com/zaya9450252/lidon-lacamentoos.git | 8 entradas no status |
| Polyvox/Apontamento Produção | main | https://gitlab.com/zaya9450252/polyvox_apontamento_producao.git | 5 entradas no status |

## Projetos sem versionamento

Foram encontradas 26 raízes de projeto sem repositório Git ancestral. A relação completa está em REPOSITORY_MAP.csv. Destaques: AttTelecontrol, FlowContratosServicos, MA Hospitalar, Polyvox/ImportadorPedido, Polyvox/Koncili, Polyvox/PolyvoxTelecontrol, Portomag/CRMLeituraPDF, Teleword, TennisPlace e as árvores Skala.

## Tecnologias

Java 8, Gradle/Groovy/Kotlin DSL, JSP, HTML5, JavaScript, CSS, Oracle SQL/PLSQL e APIs Sankhya (JapeSession, NativeSql, JdbcWrapper, EntityFacade e DynamicVO). Foram localizadas referências a TGFCAB, TGFITE, TGFVAR, TGFTOP, TGFFIN e TGFEST.

## Principais problemas

1. 26 raízes mapeadas sem cobertura Git.
2. Repositórios com alterações locais e dois sem remote origin.
3. Grandes conjuntos de código compilado, JARs e ZIPs misturados ao código-fonte.
4. Cópias nomeadas como backup/package/consolidar sem comprovação de equivalência.
5. Caminhos absolutos e configurações locais em builds e código.
6. Apenas um AGENTS.md global do workspace e nenhum template de Merge Request detectado.

## Principais riscos

- Possíveis credenciais em código, testes, JSPs e documentação; detalhes sem valores em SECURITY_RISKS.md.
- Perda de histórico em projetos sem Git.
- Implantação do artefato errado devido a múltiplos JARs/ZIPs/cópias.
- Dependência de caminhos absolutos locais.
- Mudanças não rastreadas nos quatro repositórios sujos.