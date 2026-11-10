# Captura de falhas para Nexo — piloto local

Preparado em 15/10/2026 nas actions ParticionarPedidosAction e DesfazerParticionamentoAction. O fluxo original foi extraído para executeAction; um catch externo registra falha pelo emissor opcional e relança a mesma exceção. Não foram alterados algoritmos, SQL, FQCNs, Java 8, Gradle ou SDK. Erro da própria captura é isolado, inclusive ausência de metadados de pacote.

A biblioteca br.com.zaya.engineering.nexobutton.monitoring tem emissores HTTPS via SPI. Sem configuração protegida e válida, o JAR normal não envia. Recurso temporário homologado precisa conter identidade individual e expiração; não usar segredo em parâmetros ou fonte. O transporte síncrono é fora do bloco Jape explícito, mas a transação externa do container precisa ser verificada em homologação. Timeouts de conexão/leitura não são prazo global; não há fila durável. Ausência de evento não prova ausência de erro.

A central de issues/OS fica no projeto Nexo AI, em localhost:18085, com credencial local protegida. Histórico e eventos ao vivo são distinguidos. Nenhuma instalação de JAR, alteração de banco ou correção funcional foi executada neste piloto.

A mensagem exata de combinação global presente nos relatos antigos não foi localizada na cópia consultada. Não há outra pasta informada pelo usuário; esta é a fonte disponível, sem correspondência de versão implantada comprovada. Antes de instalar, comparar o JAR atual e homologar esta captura, sem substituir regras mais recentes por esta cópia.

Validação: Gradle build e runScenarios concluídos; cenários existentes passaram. Classes do emissor sincronizadas com o módulo Nexo; checks Java 8 executados no Nexo. Não houve teste real na hospedagem.

Deploy pendente: confirmar fonte/artefato, provisionar endpoint HTTPS autorizado, instalar em homologação, provocar erro conhecido e conferir evento e exceção original. Rollback proposto: restaurar o JAR anterior pelo mesmo mecanismo de instalação, revogar a identidade e verificar a operação. Não há ferramenta de deploy automático ou DML no emissor.

## JAR de falhas controladas

O artefato de homologação `PARTICIONAMENTOSKALA-1.2-NEXO-FAULT-TEST.JAR` é derivado diretamente do JAR produtivo 1.2. Ele não deve ser instalado em produção. A instrumentação preserva as classes de negócio e envolve somente as actions de particionar/desfazer, além de inserir um ponto de falha após a criação das OEPs dentro da transação.

Para ativar um cenário, informar simultaneamente os parâmetros da action:

- `NEXO_TESTE_CONFIRMAR = FORCAR_ERRO_NEXO`
- `NEXO_TESTE_ERRO = <cenario>`

Cenários disponíveis:

| Cenário | Categoria Nexo | Momento |
| --- | --- | --- |
| `PRODUTO_INATIVO` | `PRODUCT_INACTIVE` | Antes da persistência |
| `QUANTIDADE_ENTREGUE` | `DELIVERED_QTY` | Após gerar as OEPs, ainda dentro da transação; deve ocorrer rollback |
| `COMBINACAO_GLOBAL` | `NO_GLOBAL_COMBINATION` | Antes da persistência |
| `SEM_PARTICIONAMENTO_ATIVO` | `NO_ACTIVE_PARTITION` | Antes da execução da action |
| `NAO_CLASSIFICADO` | `UNCLASSIFIED` | Antes da persistência |

Sem os dois parâmetros corretos, o JAR executa o fluxo produtivo sem injetar falha. Toda falha observada gera no `server.log` um marcador `[NEXO_MONITORING_EVENT]` sem mensagem funcional, pedido, usuário ou credenciais. Falhas controladas também imprimem stack trace sintético para permitir o teste do analisador de logs.
