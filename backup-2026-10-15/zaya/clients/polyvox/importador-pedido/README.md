# ImportadorPedido / Documentação técnica do BI

Este documento descreve a arquitetura, o fluxo de execução, os contratos de dados e os pontos de integração do BI `ImportadorPedido` dentro do Sankhya.

O objetivo é permitir que outra pessoa consiga:

- entender como o BI funciona
- montar e publicar o componente
- localizar rapidamente as responsabilidades de cada pasta
- manter o fluxo sem quebrar a integração com o Sankhya

---

## 1. Visão técnica do componente

O projeto implementa um fluxo de importação baseado em 3 etapas principais:

1. **Captura da planilha no navegador**
2. **Persistência intermediária em JSON e tabelas customizadas**
3. **Geração da nota no Sankhya a partir dos itens importados**

O BI não grava diretamente a nota a partir do Excel. Ele usa uma fila intermediária para garantir rastreabilidade e permitir reprocessamento.

### Estratégia arquitetural

O código foi organizado em camadas para separar responsabilidade:

- **View**: `bi/`
- **Controller**: `controller/`
- **Service**: `service/`
- **Repository**: `repository/`
- **Helper**: `helper/`
- **Model**: `models/`
- **Entry points compatíveis com Sankhya**: `action/`

---

## 2. Fluxo de execução ponta a ponta

### 2.1 Fluxo principal

```text
Usuário abre o BI
  ↓
Seleciona a planilha Excel
  ↓
`bi/script/planilha.js` lê a primeira aba no browser
  ↓
Campos são normalizados e convertidos para JSON
  ↓
JSON é salvo em `AD_PRCSJSON`
  ↓
Rotina Java é acionada
  ↓
`PlanilhaImportService` processa os JSON pendentes
  ↓
Linhas são gravadas em `AD_IMPPED` e `AD_IMPREGPED`
  ↓
`NotaGeracaoService` lê os itens pendentes
  ↓
Cabeçalho da nota é montado
  ↓
Itens são inseridos na nota
  ↓
`NUNOTA` é atualizado nos itens importados
  ↓
Status do processamento é finalizado
```

### 2.2 Estados do processo

O fluxo trabalha com status de processamento na fila:

- `N` = não processado
- `I` = importado / em processamento intermediário
- `S` = processado com nota gerada

Esses status são importantes para controle operacional e reprocessamento.

---

## 3. Camada de interface (`bi/`)

### 3.1 `bi/JSP_IMPORT.jsp`

É a página principal do componente no Sankhya.

Responsabilidades:

- renderizar a tela
- carregar CSS e scripts
- expor os botões de ação
- manter a interface mínima, sem regra de negócio

### 3.2 `bi/script/planilha.js`

É a camada de browser do BI.

Responsabilidades:

- ler o arquivo Excel com `xlsx`
- pegar a primeira planilha da pasta de trabalho
- converter linhas para JSON
- normalizar nomes de colunas
- preservar o campo de código de produto com segurança
- salvar o JSON em `AD_PRCSJSON`
- acionar as rotinas Java do Sankhya

### 3.3 Cuidados importantes no front-end

O front-end faz uma parte crítica do tratamento dos dados:

- evita perda de precisão em códigos de produto
- minimiza variações de cabeçalho da planilha
- prepara um JSON compatível com o backend Java

Se você mudar o cabeçalho da planilha, precisa revisar o mapeamento no `planilha.js` e no modelo Java.

---

## 4. Camada de entrada Java (`action/`)

Essas classes são o ponto de entrada executado pelo Sankhya.

### 4.1 `action/Importador.java`

Aciona a importação do conteúdo da planilha.

### 4.2 `action/GerarNota.java`

Aciona a geração da nota a partir dos itens já importados.

### 4.3 Papel dessas classes

Elas devem permanecer pequenas.
O ideal é que apenas deleguem para a camada de `controller`.

Isso reduz risco de quebra no Sankhya e facilita evolução do código.

---

## 5. Controller (`controller/`)

### 5.1 `controller/ImportadorController.java`

Orquestra a execução da importação.

Responsabilidades:

- abrir sessão JAPE
- executar transação
- chamar o service de importação
- registrar início/fim e retorno

### 5.2 `controller/GerarNotaController.java`

Orquestra a execução da geração da nota.

Responsabilidades:

- chamar o service de geração
- registrar logs de execução
- repassar exceções para o Sankhya

---

## 6. Service (`service/`)

### 6.1 `service/PlanilhaImportService.java`

Executa a leitura dos JSON pendentes e a criação dos registros intermediários.

Funções principais:

- ler `AD_PRCSJSON` com `PROCESSADO = 'N'`
- fazer parse do JSON
- transformar em `DadosPlanilha[]`
- criar/recuperar cabeçalho em `AD_IMPPED`
- gravar itens em `AD_IMPREGPED`
- marcar o JSON como importado

### 6.2 `service/NotaGeracaoService.java`

Executa a geração da nota no Sankhya.

Funções principais:

- localizar chaves pendentes em `AD_IMPREGPED`
- carregar os itens de uma chave `PVX + EMPRESA + CODPARC`
- criar cabeçalho da nota com base no modelo da empresa
- inserir os itens da nota
- atualizar `NUNOTA` nos itens da origem
- marcar o fluxo como processado quando aplicável

### 6.3 Pontos técnicos relevantes no service

O service foi desenhado para:

- evitar perda de linhas em importações grandes
- trabalhar com logs detalhados
- permitir reprocessamento quando houver erro
- manter o processo rastreável por `PVX`, empresa e parceiro

---

## 7. Repository (`repository/`)

### 7.1 `repository/MapPlanilha.java`

É uma fachada de compatibilidade.

Hoje ela existe para manter o nome usado pelo fluxo antigo, mas delega para o service.

### 7.2 `repository/RegPedidos.java`

Responsável por persistir a linha importada em `AD_IMPREGPED`.

Valida campos obrigatórios, normaliza quantidade e grava os dados da planilha.

### 7.3 Observação de arquitetura

Se você for evoluir o projeto, o ideal é que qualquer acesso ao banco fique isolado aqui ou em services específicos de persistência.

---

## 8. Helper (`helper/`)

### 8.1 `helper/ImportadorLogger.java`

Padroniza a estrutura de log no formato:

```text
[EVENTO] mensagem | chave=valor chave2=valor2
```

Isso facilita auditoria e análise de falhas.

### 8.2 `helper/RegistrarLog.java`

Persiste logs operacionais por item.

É usado para registrar sucesso/erro por linha processada.

### 8.3 `helper/SankhyaHelper.java`

Concentra operações de negócio/inserção ligadas ao Sankhya, principalmente a criação de itens da nota.

---

## 9. Models (`models/`)

### 9.1 `models/DadosPlanilha.java`

Representa a linha convertida da planilha.

É o contrato entre o JSON salvo e o backend Java.

Campos principais:

- `PVX`
- `PA`
- `REFERÊNCIA`
- `CÓDIGO`
- `Unit`
- `Unit Price (USD)`
- `Qty`
- `total Price (USD)`
- `Empresa`
- `Parceiro`

### 9.2 `models/ItemImportado.java`

Representa o item já preparado para a geração de nota.

Carrega os dados mais importantes para a fase de faturamento.

---

## 10. Entidades Sankhya usadas

### `AD_PRCSJSON`
Fila do JSON bruto da planilha.

Utilizada para:

- armazenar a entrada do processo
- controlar status do processamento
- permitir reprocessamento

### `AD_IMPPED`
Cabeçalho intermediário do pedido/importação.

### `AD_IMPREGPED`
Itens importados.

É a tabela mais importante do fluxo depois do JSON, porque é dela que a geração de nota consome os dados.

### `AD_LOGIMPPEDCP`
Tabela de log operacional.

Usada para rastrear processamento, erros e sucesso por item.

---

## 11. Como montar e publicar no Sankhya

### 11.1 Pré-requisitos

Antes de publicar, confirme:

- acesso ao ambiente Sankhya
- permissão para criar/alterar entidades customizadas
- permissão para criar/alterar botões/ações
- classes Java compiladas no pacote correto
- biblioteca `xlsx` disponível no BI

### 11.2 Artefatos que precisam ir juntos

Publicar o conjunto completo:

- `bi/JSP_IMPORT.jsp`
- `bi/script/planilha.js`
- `bi/css/mainCSS.css`
- `action/`
- `controller/`
- `service/`
- `repository/`
- `helper/`
- `models/`

### 11.3 Sequência de implantação

1. publicar entidades customizadas
2. publicar classes Java
3. publicar o BI (JSP, CSS e JS)
4. configurar botões/ações no Sankhya
5. validar upload de planilha pequena
6. validar uma planilha maior
7. validar geração de nota

---

## 12. O que você precisa saber para manter o projeto

### 12.1 O código do produto é sensível

Esse campo é um dos principais pontos de erro em importação.

Boas práticas:

- manter a planilha com cabeçalho estável
- não converter o código para notação científica no Excel
- evitar renomear a coluna sem atualizar o mapeamento
- validar com exemplos reais antes de publicar

### 12.2 O fluxo depende de status

Se um registro ficar parado, verifique:

- `AD_PRCSJSON.PROCESSADO`
- `AD_IMPREGPED.NUNOTA`
- logs operacionais

### 12.3 O Sankhya interfere no comportamento

O fluxo depende da configuração do ambiente:

- modelo de cabeçalho por empresa
- relacionamento empresa/parceiro
- permissões de rotina
- comportamento das entidades customizadas

---

## 13. Troubleshooting técnico

### 13.1 Falta linha após importar

Verifique:

- se a planilha foi convertida inteira para JSON
- se algum item causou erro de validação
- se o processamento foi interrompido antes do fim
- se os registros ficaram no status esperado

### 13.2 Código do produto incorreto

Verifique:

- cabeçalho da coluna
- formatação do Excel
- se a planilha gerou número ao invés de texto
- se o mapeamento no `planilha.js` está correto

### 13.3 Nota não gerada

Verifique:

- se há itens em `AD_IMPREGPED` sem `NUNOTA`
- se o modelo de cabeçalho está configurado
- se o parceiro é válido
- se o item foi inserido com erro em algum ponto do Sankhya

### 13.4 Fluxo parece executar, mas não conclui

Verifique:

- transação JAPE
- rollback automático
- permissões do usuário
- logs de erro no Sankhya e na tabela operacional

---

## 14. Regra prática para evoluir o BI sem quebrar

Ao alterar o projeto, siga esta ordem:

1. alterar o contrato do dado no front-end
2. atualizar o model Java
3. ajustar service/repository
4. validar logs e persistência
5. testar importação pequena
6. testar importação grande
7. testar geração de nota

---

## 15. Resumo rápido da arquitetura

- `bi/` = interface e integração do navegador
- `action/` = entrada das ações do Sankhya
- `controller/` = orquestração
- `service/` = regra de negócio
- `repository/` = persistência/compatibilidade
- `helper/` = integração e logging
- `models/` = contratos de dados

---

## 16. Conclusão

Este BI foi desenhado para funcionar dentro do Sankhya com rastreabilidade, reprocessamento e separação de camadas.

Se você mantiver:

- os contratos de dados
- os IDs das ações/botões
- as entidades customizadas
- a estrutura de logs

o componente continua estável e fácil de manter.


