# Ordem de Serviço - Momento Negociação - BETA V1.0

Data: 15/06/2026

Projeto: Creative Like - Momento Negociação

## 1. Objetivo

Criar uma base técnica inicial para a funcionalidade **Momento Negociação**, permitindo ao usuário consultar, de forma visual e segura, o andamento de uma negociação/proposta a partir do campo `NUNEGOCIACAO`.

Nesta fase, a solução foi planejada e prototipada sem impacto produtivo, priorizando consulta, diagnóstico e simulação visual, sem execução de cancelamentos ou alterações definitivas.

## 2. Contexto Funcional

A demanda está relacionada ao acompanhamento e futuro cancelamento de propostas/negociações. A negociação é identificada pelo campo:

- `NUNEGOCIACAO` - Número Único da Negociação
- Tabela principal de referência: `AD_TGLCAB`

O dashboard existente apresenta o status das principais áreas envolvidas no fluxo da negociação:

- Suprimento
- Layout
- Produção
- Faturamento
- Entrega

Esses status são utilizados como base para orientar o usuário sobre o momento atual da negociação e, futuramente, indicar impedimentos ou ações necessárias antes de um cancelamento.

## 3. Estrutura Técnica Criada

Foi criado um projeto Gradle Java 8 em:

```text
C:\zy-tools\workspace\Creative Like\momento-negociacao
```

O projeto foi organizado dentro de uma subpasta própria para permitir que a pasta `Creative Like` funcione como diretório guarda-chuva para outros projetos futuros.

## 4. Configuração Gradle

Foi criado um projeto Gradle com:

- Plugin Java
- Java 8
- Encoding UTF-8
- Dependências locais Sankhya em:

```text
C:/zy-tools/modulos-sankhya/Dependencias
```

Arquivo principal:

```text
build.gradle
```

Nome do JAR configurado:

```text
CreativeLike.jar
```

## 5. Ação Java Informativa

Foi criada uma ação Java informativa:

```text
br.com.zaya.like.Actions.MomentoNegociacaoAction
```

Responsabilidade:

- Receber a linha selecionada no Sankhya.
- Ler o campo `NUNEGOCIACAO`.
- Consultar o momento atual da negociação.
- Retornar um HTML informativo ao usuário.

Classe principal:

```text
src/main/java/br/com/zaya/like/Actions/MomentoNegociacaoAction.java
```

## 6. Service e DAO

Foi criada a separação em camadas:

```text
src/main/java/br/com/zaya/like/Actions/services/MomentoNegociacaoService.java
src/main/java/br/com/zaya/like/Actions/dao/MomentoNegociacaoDAO.java
src/main/java/br/com/zaya/like/Actions/dto/MomentoNegociacaoDTO.java
src/main/java/br/com/zaya/like/Actions/dto/ItemMomentoNegociacaoDTO.java
```

### 6.1. Ajuste de origem do `NUNEGOCIACAO`

Inicialmente a consulta buscava a capa diretamente em `AD_TCRNEGCAB`. Após validação funcional, foi ajustado para usar `AD_TGLCAB` como tabela principal, pois é nela que se encontra o `NUNEGOCIACAO`.

Consulta ajustada:

```sql
FROM AD_TGLCAB CAB
LEFT JOIN AD_TCRNEGCAB DASH ON DASH.NUNEGOCIACAO = CAB.NUNEGOCIACAO
WHERE CAB.NUNEGOCIACAO = :NUNEGOCIACAO
```

A `AD_TCRNEGCAB` ficou como fonte complementar dos status do dashboard.

### 6.2. Correção de erro Oracle

Durante teste no Sankhya, ocorreu o erro:

```text
ORA-00904: "NUMNEGOCIACAO": identificador inválido
```

Causa:

- A coluna `NUMNEGOCIACAO` não existe na `AD_TGLCAB`.

Correção:

- Removida a referência a `CAB.NUMNEGOCIACAO`.
- O próprio `NUNEGOCIACAO` passou a ser usado como número exibido no HTML.

## 7. Melhorias Visuais no HTML da Ação Java

A primeira versão exibia os itens principais em uma tabela larga. No modal do Sankhya, essa tabela ficou estreita demais e os textos quebraram letra por letra.

Foi realizada melhoria visual:

- Remoção da tabela larga.
- Criação de cards compactos por item.
- Produto exibido em linha própria.
- Informações organizadas em blocos menores:
  - Arte
  - Status arte
  - Suprimento
  - Produção
  - Qtd. negociada
  - Disponível em estoque
  - Pendente de compra
  - Disponível para faturamento
  - Qtd. faturada
  - Liberado CQ
  - Liberado estoque
  - Tipo

Essa alteração melhorou a leitura dentro do modal estreito do Sankhya.

## 8. Protótipo de Modal HTML5

Foi criada uma estrutura de protótipo para evoluir a solução para um fluxo mais robusto com componente HTML5.

Pasta criada:

```text
prototipos/modal-momento-negociacao
```

Arquivos:

```text
prototipos/modal-momento-negociacao/README.md
prototipos/modal-momento-negociacao/botao-acao/momentoNegociacaoModalButton.js
prototipos/modal-momento-negociacao/html5-component/modalMomentoNegociacao.jsp
```

## 9. Conceito Técnico Proposto

Foi proposta a seguinte arquitetura:

```text
Botão de ação JavaScript
    abre modal Sankhya
        carrega iframe com componente HTML5
            exibe dashboard/checklist da negociação
                futuramente chama ações Java específicas
```

Essa abordagem permite:

- Tela mais rica e controlada.
- Melhor experiência para o usuário.
- Evolução por etapas.
- Separação entre visualização, simulação e execução real.
- Menor risco de impacto produtivo.

## 10. IDs Sankhya Informados

### 10.1. Gadget HTML5

```text
356
```

Uso:

```javascript
var nuGdg = '356';
```

Esse é o ID do componente/gadget HTML5 aberto dentro do iframe.

### 10.2. Ação Java

```text
299 - [ZAYA.IT] - Momento Negociação - BETA V1.0 - RJ
```

Uso previsto:

```javascript
var codAcaoJavaMomento = '299';
```

Observação:

- `356` é o gadget HTML5.
- `299` é a ação Java.
- O HTML5 poderá chamar a ação Java futuramente, mas os dois códigos não representam a mesma coisa.

## 11. Script JavaScript de Abertura do Modal

Foi criado o script:

```text
prototipos/modal-momento-negociacao/botao-acao/momentoNegociacaoModalButton.js
```

Função:

- Capturar `NUNEGOCIACAO` da linha selecionada.
- Abrir modal Sankhya.
- Carregar o componente HTML5 `356`.
- Passar parâmetros via URL:

```text
entryPoint=modalMomentoNegociacao.jsp
nuGdg=356
nunegociacao=<NUNEGOCIACAO>
codAcaoJavaMomento=299
modo=SIMULACAO
```

## 12. ZIP para Upload no Sankhya

Como componentes HTML5 do Sankhya são publicados por arquivo `.zip`, foi gerado o pacote:

```text
modal-momento-negociacao-html5.zip
```

Conteúdo do ZIP:

```text
modalMomentoNegociacao.jsp
```

O arquivo foi gerado em:

```text
C:\Users\annayran.ferreira\Documents\Codex\2026-06-15\files-mentioned-by-the-user-creative\outputs\modal-momento-negociacao-html5.zip
```

## 13. Modo Seguro de Teste

O protótipo foi criado com o parâmetro:

```text
modo=SIMULACAO
```

Nesta fase:

- Nenhuma gravação é executada.
- Nenhum cancelamento é realizado.
- As ações exibidas no HTML5 são apenas simulações visuais.
- O usuário consegue validar fluxo, layout, parâmetros e entendimento funcional.

## 14. Testes Realizados

Foram realizadas validações locais:

- Verificação da estrutura do projeto.
- Compilação estática com `javac`.
- Validação dos imports Sankhya usando os JARs locais.
- Ajuste após erro Oracle em runtime.
- Geração de ZIP HTML5.
- Conferência de que a JSP ficou na raiz do ZIP.

Observação:

- A compilação com `javac` foi realizada com JDK 21 usando `-source 8 -target 8`, gerando apenas avisos normais de compatibilidade.

## 15. Pontos Pendentes / Próximos Passos

1. Subir o ZIP no gadget HTML5 `356`.
2. Ajustar o script do botão JavaScript para usar:

```javascript
var nuGdg = '356';
var codAcaoJavaMomento = '299';
```

3. Testar o modal HTML5 em ambiente seguro/homologação.
4. Subir o JAR atualizado da ação Java informativa.
5. Validar se o HTML5 receberá os dados:
   - diretamente por chamadas ao backend;
   - por uma ação Java;
   - ou por serviço intermediário.
6. Evoluir o protótipo para consultar dados reais no HTML5.
7. Criar checklist real de impedimentos.
8. Criar ações em modo `DRY_RUN`.
9. Somente depois liberar ações reais, com log/auditoria.

## 16. Resultado Atual

Foi entregue uma base inicial funcional e organizada para a feature **Momento Negociação - BETA V1.0**, composta por:

- Projeto Gradle Java 8.
- Ação Java informativa.
- Consulta baseada em `AD_TGLCAB` e complementada por `AD_TCRNEGCAB`.
- HTML da ação Java ajustado para melhor leitura.
- Protótipo de modal HTML5.
- Script JavaScript para abertura de modal.
- ZIP para upload no Sankhya.
- Documentação inicial do fluxo.

O trabalho atual não impacta produção, pois está focado em consulta, visualização e simulação.
