# Evento de Sinal CRM

Projeto Java Teleworld (`br.com.zaya.teleworld.flow`) com Gradle Wrapper 9.0.0,
conforme template corporativo. Java alvo: 8; executar Gradle com JDK 17 ou superior.
Compatibilidade com o servidor Sankhya ainda depende de homologacao.
Artefato configurado: `build/libs/ZAYA-CRM-V2.0.jar`.

## Responsabilidades

Revisao de produtos: `SinalInclusaoProduto` sinaliza a inclusao na TGFITE;
`ValidaRevisaoProdutosConfirmacao` bloqueia a confirmacao de produtos vinculados
com revisao pendente na TGFPRO. Cadastro, escopo e homologacao em
[docs/zaya/revisao-produtos.md](docs/zaya/revisao-produtos.md).

A mesma regra compara NCM e IPI do XML da TGFIXN com o cadastro do produto.
Fontes, bloqueios e criterios provisorios para conferencia em
[docs/zaya/validacao-fiscal-xml.md](docs/zaya/validacao-fiscal-xml.md).

- `SinalLiberacaoProcesso`: adaptador de evento; FQCN anterior preservado.
- `controller/LiberacaoProcessoController`: converte o evento Jape em entrada do model.
- `model/service/LiberacaoProcessoService`: transicao para N e validacao da instancia.
- `sankhya/services/FlowSinalizador`: chamada da API Flow.
- Sem view, pois esta rotina nao possui interface.

## Build

```powershell
.\gradlew.bat clean build -PflowApiJar=C:/caminho/SankhyaFlowAPI.jar
```

Dependencias SDK: `-PsankhyaDependenciesDir`, variavel `SANKHYA_DEPENDENCIES_DIR`
ou fallback `C:/dev/zaya/platform/modulos-sankhya/Dependencias`.
Pasta de versao opcional: `-PsankhyaVersionDir=...` (confirmar com o ambiente).
Flow API: `-PflowApiJar=...` ou `SANKHYA_FLOW_API_JAR`; pode ser omitido se o JAR
ja estiver no diretorio do SDK. Todas essas dependencias sao `compileOnly`.
Nao ha atualizacao nem copia de bibliotecas proprietarias para o projeto.

## Validacao local

Build Gradle concluido: BUILD SUCCESSFUL. O erro de socket local foi contornado com jdk.net.unixdomain.tmpdir apontando para uma pasta do projeto.
A pasta SDK local contem `jape-4.23b244.jar`; isso nao confirma a versao do servidor.
Veja `evento-sinal-crm/README.md` para regra, cadastro e homologacao funcional.
AD_SOLMIN implementado; regras atuais em evento-sinal-crm/README.md.

Compilacao direta com `javac --release 8` e SDK local aprovada (bytecode 52).
Verificacao isolada do model aprovada: transicao, ID direcionado, ausencia de
reenvio em N para N, outros estados e rejeicao de IDs invalidos. Esse teste local
nao substitui homologacao transacional nem valida o empacotamento Gradle.

## Gerar o JAR no Windows

```powershell
.\scripts\Build.ps1 -FlowApiJar 'C:/caminho/SankhyaFlowAPI.jar'
```

O script usa o Wrapper e define o diretorio de sockets apenas durante a execucao,
restaurando as opcoes Java ao terminar. Saida: `build/libs/ZAYA-CRM-V2.0.jar`.
Validado: manifesto 2.0, bytecode Java 8 (52), somente classes do projeto no JAR.
Testes de regras integrados a tarefa check por testRegras (JavaExec), sem dependencias adicionais.
Nao ha recursos SQL nesta entrega. Deploy e homologacao no servidor pendentes.

## IntelliJ

Instalacao mais recente encontrada: IntelliJ IDEA 2026.2.2.
Abrir a raiz Teleworld como projeto Gradle, usando o Wrapper. No terminal do
IntelliJ, executar o script acima para gerar o artefato com o Flow API externo.


### Correcao da sincronizacao no IntelliJ (2026-09-15)

A falha `Invalid argument: connect` ocorria em UnixDomainSockets/PipeImpl do
processo Java da IDE. A opcao de usuario do IntelliJ 2026.2 agora define
`jdk.net.unixdomain.tmpdir` em uma pasta local de sockets. Reiniciar a IDE
para carregar a opcao; depois usar Reload All Gradle Projects.
A alteracao segue o mecanismo de Custom VM Options da JetBrains e nao altera
os arquivos da instalacao. Confirmacao visual da sincronizacao pendente.

`gradle-local.properties` (ignorado no Git) referencia o Flow API externo.
Prioridade: parametro Gradle, variavel de ambiente, arquivo local.
Build sem -PflowApiJar validado com sucesso. Em outra maquina, criar o arquivo
local com `flowApiJar=C:/caminho/SankhyaFlowAPI.jar` ou definir a variavel.

