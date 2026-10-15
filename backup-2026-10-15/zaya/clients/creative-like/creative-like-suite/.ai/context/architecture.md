# Arquitetura do Projeto

## Visao Geral

Projeto Java/Gradle para extensoes do Sankhya, com foco atual em consulta e simulacao do "Momento da Negociacao" a partir de uma acao de rotina e de um prototipo HTML5.

O repositorio contem:

- uma acao Java (`MomentoNegociacaoAction`) para ser executada como botao de acao no Sankhya;
- camada de servico para montar HTML de retorno com dados da negociacao;
- camada DAO com consultas SQL nativas via APIs Sankhya/JAPE;
- DTOs para trafegar dados de cabecalho e itens;
- prototipo HTML5/JSP importavel no componente HTML5 do Sankhya;
- script JavaScript para abrir o componente HTML5 dentro de um popup/modal do Sankhya;
- zip de entrega do componente HTML5.

## Camadas

### Acao Sankhya

`src/main/java/br/com/zaya/like/Actions/MomentoNegociacaoAction.java`

Responsavel por:

- receber as linhas selecionadas no contexto da acao;
- validar existencia de `NUNEGOCIACAO`;
- chamar o servico;
- retornar HTML para o usuario via `contexto.setMensagemRetorno`;
- exibir erro amigavel via `contexto.mostraErro`.

### Servico

`src/main/java/br/com/zaya/like/Actions/services/MomentoNegociacaoService.java`

Responsavel por:

- buscar dados de momento e itens principais;
- montar HTML de apresentacao;
- aplicar cores por status;
- escapar valores HTML antes de renderizar.

### Acesso a Dados

`src/main/java/br/com/zaya/like/Actions/dao/MomentoNegociacaoDAO.java`

Responsavel por:

- abrir `NativeSql` usando `EntityFacadeFactory.getDWFFacade()`;
- consultar dados de cabecalho da negociacao em `AD_TGLCAB` e `AD_TCRNEGCAB`;
- consultar itens principais com CTEs envolvendo `AD_TCRNEGITE`, `TGFPRO` e `AD_TGLITEART`;
- liberar recursos SQL no `finally`.

### DTOs

`MomentoNegociacaoDTO` e `ItemMomentoNegociacaoDTO`.

Representam dados de cabecalho da negociacao e itens principais.

### Prototipo HTML5

`prototipos/modal-momento-negociacao/html5-component/modalMomentoNegociacao.jsp`

Tela HTML5/JSP simulada para teste visual no componente HTML5 do Sankhya. Opera em modo simulacao e nao grava dados.

### Botao JavaScript

`prototipos/modal-momento-negociacao/botao-acao/momentoNegociacaoModalButton.js`

Script para acao JavaScript no Sankhya. Abre um popup contendo iframe para `/mge/html5component.mge`, passando `entryPoint`, `nuGdg`, `nunegociacao`, `codAcaoJavaMomento` e `modo=SIMULACAO`.

## Modulos Principais

- `src/main/java/br/com/zaya/like/Actions`: acoes de rotina Sankhya.
- `src/main/java/br/com/zaya/like/Actions/services`: logica de apresentacao/servico.
- `src/main/java/br/com/zaya/like/Actions/dao`: acesso SQL nativo.
- `src/main/java/br/com/zaya/like/Actions/dto`: objetos de transporte.
- `prototipos/modal-momento-negociacao`: prototipo HTML5, botao JS e README.
- `outputs`: pacote zip para importacao no HTML5 do Sankhya.
- `simulacoes`: simulacao standalone em HTML para apoio local.

## Fluxo Principal

```text
Usuario seleciona linha no Sankhya
  -> Botao de acao Java ou JavaScript
      -> Leitura de NUNEGOCIACAO
          -> Java: MomentoNegociacaoAction
              -> MomentoNegociacaoService
                  -> MomentoNegociacaoDAO
                      -> NativeSql / tabelas Sankhya
                  -> HTML de retorno
              -> contexto.setMensagemRetorno

          -> JavaScript: momentoNegociacaoModalButton.js
              -> abre /mge/html5component.mge
                  -> modalMomentoNegociacao.jsp
                      -> simulacao visual em modo SIMULACAO
```

## Integracoes

- Sankhya Acao Rotina Java (`AcaoRotinaJava`, `ContextoAcao`, `Registro`).
- Sankhya/JAPE (`EntityFacadeFactory`, `JdbcWrapper`, `NativeSql`).
- Componente HTML5 do Sankhya (`/mge/html5component.mge`).
- Banco de dados Oracle/Sankhya por SQL nativo. O tipo exato do banco nao esta declarado no build, mas o uso de `NVL` e sintaxe Sankhya indica ambiente Oracle/Sankhya. A confirmar no ambiente.

## Dependencias Arquiteturais Importantes

- Dependencias Sankhya locais em `C:/zy-tools/modulos-sankhya/Dependencias`.
- O `nuGdg` do componente HTML5 precisa ser preenchido no script JS antes do uso real.
- O zip importavel deve conter `modalMomentoNegociacao.jsp` na raiz.

## Pontos de Atencao

- O prototipo HTML5 atual e simulado e nao consulta backend.
- A acao Java consulta dados reais, mas apenas monta HTML informativo.
- Regras de cancelamento automatico aparecem na documentacao Word, mas nao estao implementadas como gravacao/cancelamento real neste repositorio.
- Conversas recentes citaram "Recebimento Fiscal - Nota de Retorno de Industrializacao", mas nao ha artefatos confirmaveis desse tema neste repositorio atual. A confirmar.
