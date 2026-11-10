# Genesis - entrega seletiva

Snapshot seletivo de codigo Java, testes, painel web, prototipo React,
documentos, SQL e metadados aprovados pelos filtros locais. Nao e copia
integral nem projeto independente. Arquivos excluidos constam no relatorio local.

Fonte canonica: repositorio local do projeto MA Hospitalar; origin GitLab
separado e preservado. O destino GitHub e uma entrega de consulta, sem migracao
da origem. Conteudo inclui alteracoes locais nao commitadas; nenhum commit
de origem representa integralmente este snapshot.

## Build local

Da pasta ma-hospitalar: definir SNK_LIB para a pasta do SDK Sankhya licenciado
(subpastas Jar, 4_35, jrepository, platina). Usar os JDK/Gradle ja homologados
localmente; nenhuma atualizacao de versao e autorizada por esta entrega.
Executar `gradle test jar` com Gradle local, ou o runner existente adaptado:
`& ./tools/BuildGenesisHomologacao.ps1 -Sdk $env:SNK_LIB`.
O runner usa javac/java/jar com suporte a --release 8 (JDK 9+), SDK local e
Jackson 2.17.0/JUnit previamente disponiveis no cache Gradle do usuario.
O build.gradle conserva as versoes Jackson 2.17.0 e JUnit BOM 5.10.0 da origem,
resolve dependencias publicas via mavenCentral e compila somente pacotes Genesis.
Wrapper binario nao distribuido; usar Gradle local aprovado ou runner.
Scripts SQL de estrutura/conciliacao requeridos pelos contratos foram revisados
como referencia textual, nao contem cargas/dumps e nunca sao executados pelo
build ou exportador. Executa-los exige autorizacao de deploy/validacao/rollback.
Nao considerar suite integral verde antes da validacao da copia pelo coordenador.

Da pasta ma-hospitalar/prototypes/genesis-workspace:
`pnpm install --frozen-lockfile` e `pnpm run build:readonly`.
Usar pnpm local compativel com lockfileVersion 9, sem atualizar dependencias.
Resultado: dist/sankhya-readonly/index.jsp, para homologacao no componente BI.
package.json e copia ajustada com apenas build:readonly; demo/start foram
retirados porque o produto exportado e somente leitura. Nao ha servidor Node
necessario para executar o JSP no Sankhya. build.cjs de demo nao e ponto de entrada
do produto. O runner Java e build.gradle sao copias ajustadas identificadas.
Nao foram incluidos node_modules, dist, QA, demo persistida, cargas ou binarios.

Documentos sao copias identificadas; caminhos pessoais e enderecos nao publicos
foram omitidos. A filtragem e conservadora e heuristica, nao certificacao de
ausencia de PII. Revisao humana do manifesto pelo coordenador precede envio.
SQL/XML exportados nao foram executados/importados; deploy, validacao funcional,
permissoes, seguranca e rollback precisam de homologacao no ambiente autorizado.

Destino: Annayran/codex-repo (privado confirmado pelo coordenador), head
codex/genesis-integracao, base codex/ma-hospitalar-genesis (criadas pelo
coordenador a partir da mesma SHA). Prefixo exclusivo projects/ma-hospitalar-genesis/.
Este preparo nao cria branches, commits,
pull requests, merge ou publicacao. Nao usar main/master.