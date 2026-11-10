# Topnutri - Importação de Fórmulas de Produção

## Identificação

- Cliente: Topnutri
- Solução: importação de fórmulas de produção do Optimal para o Sankhya
- Pacote-base: `br.com.zaya.topnutri.importacaoformulas`
- Modelo: módulo Java tradicional com eventual Dynaform configurado manualmente
- Java de destino: 8
- Código do artefato: `TPN`
- Versão do projeto: `1.9`
- Ambiente comprovado: Sankhya Om `4.36b109`, Manufatura `5.5.12`, ERP Core `5.12.1`

## Objetivo

Ler o arquivo TXT gerado pelo Optimal, validar integralmente suas fórmulas e
preparar a substituição versionada das composições do produto acabado e, quando
aplicável, do respectivo PI de micronutrientes.

## Escopo implementado neste incremento

- leitura preferencial em UTF-8, com suporte a BOM e fallback ISO-8859-1 para
  arquivos legados;
- contrato dos registros `0` e `1`, com separador e terminador `;`;
- preservação dos códigos com zeros à esquerda;
- armazenamento, sem uso funcional, do quinto campo do registro `0`;
- validações estruturais acumuladas por linha;
- conferência da massa com tolerância de até 5% da quantidade do produto acabado;
- cálculo SHA-256 do arquivo recebido;
- classificação explícita do TXT: `AD_CATEGORIA = 'M'` para micro e `A` para
  macro; categoria vazia, `E`, `O` ou desconhecida bloqueia a importação;
- separação dos micronutrientes, cálculo da quantidade do PI no PA e
  normalização proporcional da composição do PI para exatamente 1 kg;
- resolução determinística do PI pelo prefixo `200` aplicado ao código numérico
  canônico do PA (`22 → 20022`, `847 → 200847`);
- contratos para consulta da categoria e resolução do PI.
- componente HTML5 para upload e pré-validação estrutural do TXT;
- ação Java de pré-validação com resposta JSON e SHA-256;
- simulação somente leitura da composição futura do PA e do PI/Batida, incluindo
  descrições, unidades, categorias e embalagens recuperadas da versão anterior;
- ação de confirmação com revalidação do conteúdo e proteção por hash.

## Decisões funcionais confirmadas

- divergência de massa somente bloqueia quando o valor absoluto da diferença
  ultrapassa 5% da quantidade do produto acabado; diferenças iguais a 5% são
  aceitas, para cima ou para baixo, sem correção automática dos valores;

- cada registro `0` abre um PA distinto; registros `1` pertencem a ele até o
  próximo `0` ou fim do arquivo; erro em qualquer bloco invalida o arquivo;
- mapeamento de categoria para tipo: `M → Micro`, `A → Macro`,
  `E → EmbRotulo`, `O → Outro`; o TXT aceito nesta operação contém somente
  materiais `M` ou `A`;
- o mapeamento é aplicado na inclusão Jape da lista de materiais;
- o PA pertence ao processo produtivo de código `1` e o PI ao processo `6`;
- a quantidade do PI na composição do PA é a soma decimal exata dos micros do
  TXT; os valores originais do TXT não são arredondados;
- a composição do PI usa a proporção `quantidade do micro / soma dos micros`;
  a divisão derivada usa `DECIMAL128` e o último componente absorve o resíduo
  decimal para que o total persistido seja exatamente 1 kg;
- etiqueta e sacaria são recuperadas da versão anterior do PA, preservadas
  quando já copiadas e reinseridas como `1 UN` quando ausentes na versão nova;
- a importação bloqueia quando não há pelo menos duas embalagens/rótulos na
  versão anterior, pois o TXT não informa seus códigos.

## Integração Sankhya produtiva

- localiza e versiona separadamente os processos `1` (PA) e `6` (Micro) pelo
  helper nativo da Manufatura;
- preserva PAs não presentes no TXT e substitui integralmente somente as
  composições dos PAs importados;
- substitui no PA somente a parte nutricional, preserva as embalagens e grava o
  PI como material `Micro`; no processo `6`, substitui a fórmula do PI pelos
  micronutrientes normalizados para 1 kg;
- inclui materiais pela entidade Jape `ListaMateriaisAtividade`, permitindo a
  execução dos listeners e preenchimentos padrão do Sankhya;
- deriva o tipo de controle da MP de `TGFPRO.TIPCONTEST`: sem controle usa
  Literal, lote usa Automático, lista preserva valor literal ou herda do PA e
  série usa a configuração copiada ou Automático;
- atualiza componentes já copiados pela nova versão sem recriá-los, preservando
  as demais configurações da matéria-prima; componentes novos recebem a unidade
  padrão do produto e defaults operacionais mínimos;
- valida a composição, realiza o deploy e atualiza as fórmulas pelo fluxo nativo;
- propaga qualquer erro para rollback da transação da ação.

Pendente de homologação: comprovar as assinaturas no `mgeprod-model` 4.36b109,
o versionamento conjunto dos processos `1` e `6`, a atividade escolhida para PA
ou PI novo, a concorrência e o rollback operacional. As dependências locais disponíveis são
4.23/4.35; por isso as chamadas internas são verificadas em runtime antes do uso.

As opções `Micro 2` e `Outro` de `TPRLMP.AD_TIPOMP` não são aceitas no TXT neste
incremento. `Macro` permanece no PA, `Micro` compõe o PI e `EmbRotulo` é
preservado exclusivamente a partir da versão anterior do PA.

## Interface HTML5

A conferência V1.9 apresenta uma simulação expansível por PA, busca por código
ou descrição e filtro de produtos com pendências. Para cada PA, a tela mostra
como ficará o processo `1`, distinguindo Macro, PI/Batida e Embalagem, e como
ficará o processo `6`, com os micros normalizados para 1 kg. Códigos, descrições,
unidades, categorias e embalagens são consultados no Sankhya em modo somente
leitura; a conferência não cria versão e não grava dados.
Pendências cadastrais ou funcionais encontradas nessa simulação bloqueiam a
confirmação, assim como os problemas estruturais do TXT. A regra de tolerância
é calculada no backend com BigDecimal, sem alterar as quantidades.
O botão de confirmação é habilitado somente após uma conferência válida. Ao ser
acionado, envia novamente o mesmo arquivo e o SHA-256 para revalidação no backend.
O JSON usa escapes Unicode para evitar perda de acentos no transporte da mensagem
da ação. O parser reconhece UTF-8 e mantém fallback para o formato legado.
A interface utiliza o logo oficial empacotado localmente, as cores institucionais
`#1C3A6D`, `#00AEB9` e `#6F6F6E`. Os pesos 400, 600 e 700 da fonte Poppins são
empacotados no componente, com fallbacks locais. Nenhum recurso visual depende
da internet durante a execução.
O cabeçalho externo pertence ao modal do Sankhya e é estilizado pelo lançador
`src/main/resources/sankhya/abrirImportador.js`; alterações nele exigem reaplicar
o JavaScript no Dynaform, além da atualização do ZIP HTML5.

O componente está em `src/main/webapp/html5/importacaoFormulas.jsp`. O build
gera também `build/distributions/TPN_html5Component.zip`. A configuração manual
do Dynaform, das ações e do lançador está documentada em
`docs/configuracao-dynaform.md`.

A ação de confirmação executa o adaptador produtivo após revalidar conteúdo e
SHA-256. Não existe fallback com DML direto nas tabelas produtivas: ausência ou
incompatibilidade da API nativa aborta a transação com erro funcional.

## Build

```powershell
.\gradlew.bat clean build
```

Se o Java no Windows falhar com `Unable to establish loopback connection`
e o stack trace indicar `UnixDomainSockets.connect`, execute a partir da raiz
do projeto com um diretório temporário específico para sockets. O procedimento
abaixo foi validado com o JDK 21 local; não altera o Java 8 de destino nem o
ambiente permanentemente:

```powershell
New-Item -ItemType Directory -Force -Path build/java-tmp | Out-Null
$fileSystem = New-Object -ComObject Scripting.FileSystemObject
$socketPath = $fileSystem.GetFolder((Resolve-Path 'build/java-tmp').Path).ShortPath.Replace('\', '/')
$previousJavaToolOptions = $env:JAVA_TOOL_OPTIONS
try {
    $env:JAVA_TOOL_OPTIONS = ($previousJavaToolOptions + ' -Djdk.net.unixdomain.tmpdir=' + $socketPath).Trim()
    .\gradlew.bat jar test html5ComponentZip --no-daemon
} finally {
    $env:JAVA_TOOL_OPTIONS = $previousJavaToolOptions
}
```

Não use `clean` nesse comando alternativo: ele removeria o diretório temporário
durante o build. O erro do socket é independente do erro de pré-validação no ERP.

O artefato esperado é `build/libs/ZAYA-TPN-V1.9.jar`. Bibliotecas fornecidas
pelo runtime Sankhya são `compileOnly` e não são incorporadas ao JAR.

Os resultados do incremento atual estão registrados em
[`docs/validacao.md`](docs/validacao.md).

Importe este diretório no IntelliJ como projeto Gradle. Não versione `.idea`,
`*.iml`, `out` ou configurações locais.
