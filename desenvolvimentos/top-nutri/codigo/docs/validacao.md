# Validação do incremento inicial

Data da validação: 10/09/2026.

## Ambiente local

- compilador: OpenJDK `21.0.10`;
- alvo de compilação: Java 8 (`--release 8`);
- bytecode conferido: major version `52`;
- ambiente Sankhya alvo: Om `4.36b109`, Manufatura `5.5.12`.

## Testes automatizados

- testes encontrados: 28;
- testes executados: 28;
- testes aprovados: 28;
- falhas: 0.

Cenários cobertos:

- leitura ISO-8859-1 e preservação de acentos;
- preservação de códigos com zeros à esquerda;
- registro e não interpretação de `@none`;
- componente sem cabeçalho e divergência do produto pai;
- fórmula sem componentes;
- divergência de massa;
- separação de categorias diretas e micronutrientes;
- soma dos micronutrientes para quantidade do PI;
- ausência de composição vazia de PI;
- bloqueio de componente sem categoria Macro ou Micro;
- bloqueio de embalagem recebida indevidamente no TXT;
- derivação do PI pelo prefixo `200`, removendo zeros à esquerda do PA;
- normalização proporcional dos micros com soma exata de 1 kg;
- bloqueio quando não existe PI Micro exclusivo.
- serialização da simulação completa de PA e PI/Batida;
- bloqueio da confirmação quando a simulação cadastral possui pendência.

## Arquivo real

O arquivo fornecido foi lido diretamente de sua localização original, sem cópia
para o repositório:

- tamanho: 398 bytes;
- fórmulas: 1;
- componentes: 13;
- inconsistências: 0;
- SHA-256: `68a93043d5d7aa8afafb196243bca151b5f3577df42226cffdc12b2f171cc199`.

## Build Gradle

O Gradle Wrapper `9.0.0` foi obtido e iniciado, mas o build Gradle não chegou à
compilação porque o ambiente de execução bloqueou a conexão loopback usada pelo
processo auxiliar do Gradle (`Unable to establish loopback connection`).

Como validação substituta, fontes e testes foram compilados diretamente com
`javac --release 8`, e a suíte JUnit foi executada com os artefatos do cache
local. O build oficial `.\gradlew.bat clean build` permanece pendente em um
terminal local sem essa restrição.

Validação da V1.8 executada em 30/09/2026:

- 23 fontes principais compiladas pelo Gradle com alvo Java 8;
- 26 testes JUnit executados e aprovados;
- tarefas `jar`, `test` e `html5ComponentZip` concluídas com sucesso;
- bytecode principal confirmado como major version 52;
- JAR `ZAYA-TPN-V1.8.jar`: 59.917 bytes, SHA-256
  `CCC016AD166099644EAA3FAF608336549E2E7160E3EE202CAE2AF3FD079C7064`;
- ZIP `TPN_html5Component.zip`: 35.836 bytes, SHA-256
  `C2CB2521C43DE776952BEC84CCB38580D0A93264E9D6F141EE805D50809A4B24`;
- o contorno do socket do JDK exige o caminho temporário em formato 8.3 e com
  barras `/`, conforme o comando atualizado no README.

Validação da V1.9 executada em 30/09/2026:

- 27 fontes principais compiladas pelo Gradle com alvo Java 8;
- 28 testes JUnit executados e aprovados, sem falhas;
- tarefas `jar`, `test` e `html5ComponentZip` concluídas com sucesso;
- JavaScript da JSP validado pelo parser do Node.js;
- bytecode da ação de pré-validação confirmado como major version 52;
- JAR sem classes proprietárias `br/com/sankhya`, mantendo as dependências como
  `compileOnly`;
- JAR `ZAYA-TPN-V1.9.jar`: 74.528 bytes, SHA-256
  `534515C19C6265CBA3BB1B0BF083EC9A28D342ADDA6EB9B0AACD42ABF7F09FEE`;
- ZIP `TPN_html5Component.zip`: 37.230 bytes, SHA-256
  `C03CF7A2BF4EC707CA2E6044071EC4622A5DB243417FA48420D8FC201E52CCEA`;
- ZIP conferido com JSP, logo SVG, três pesos locais da fonte Poppins e licença;
- simulação cadastral implementada somente com consultas Jape e sem chamadas de
  criação, atualização, exclusão, versionamento ou deploy.

## Interface e ações - incremento atual

- serialização JSON preserva `BigDecimal.toPlainString()` e a escala recebida;
- os bytes são enviados em Base64, sem conversão prévia de encoding no browser;
- o backend continua responsável pela leitura ISO-8859-1;
- a pré-validação consulta TGFPRO e os processos `1`/`6`, monta a composição
  futura do PA e do PI e recupera etiqueta/sacaria da versão anterior sem gravar;
- a confirmação exige o mesmo SHA-256 retornado na pré-validação;
- a ação produtiva usa o versionamento, validação e deploy nativos, com chamadas
  verificadas reflexivamente no runtime;
- teste integrado de `ActionButtonsSP.executeJava` com `rows` vazio: **Pendente
  de validação pela equipe**.

Validação substituta executada em 11/09/2026:

- fontes principais compiladas com `javac --release 8`;
- ações Java compiladas contra as bibliotecas locais disponíveis;
- 8 testes JUnit aprovados;
- JavaScript da JSP validado pelo parser do Node.js;
- ZIP HTML5 gerado com somente `importacaoFormulas.jsp`;
- SHA-256 do ZIP de validação:
  `f530332c793d50c720de33d012f7623160cfee45adb2b50be4d6460cd69bb003`;
- bytecode das ações conferido como major version 52, compatível com Java 8.

As bibliotecas locais identificadas são da série 4.23 e não substituem o teste
de compatibilidade no ambiente alvo 4.36b109. O Gradle Wrapper voltou a falhar
antes da compilação por `Unable to establish loopback connection`.
