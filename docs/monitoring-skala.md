# Central local de issues e OS — Skala

Implementado em 15/10/2026 para organizar falhas e relatos do particionamento. Não é monitoramento de todo o ERP nem implantação autônoma.

## Uso

Execute `scripts/Start-NexoDesk.ps1 -CopyPassword`. Abra http://127.0.0.1:18085/issues.html. O comando informa o usuário e copia a senha sem imprimi-la. Identidade persistente e dados ficam em config/private/desk-skala, com ACL. Não depende do túnel expirado nem do acesso público de demonstração. Para encerrar: `scripts/Stop-NexoDesk.ps1`; os dados permanecem.

Carregue os casos, abra a OS, revise criticidade, atribua responsável e registre análise/proposta/rollback e validação. Resolução exige proposta e validação preenchidas, declaradas pelo operador; o conteúdo não é comprovado automaticamente. Baixe a OS em JSON. Atualização a cada dez segundos é opcional, depende da página aberta e do receptor ativo.

## Histórico

O importador usa três diretórios explícitos das homologações anteriores, sem varredura recursiva. Importa relatórios originais recebidos pelo botão para a nota usada nos testes; exclui revisões de IA e smoke fixtures. Normaliza a categoria localmente; não envia mensagens brutas ou logs para a central de OS.

Oito relatos foram agrupados em cinco issues/OS: PRODUCT_INACTIVE (quatro relatos), DELIVERED_QTY, NO_ACTIVE_PARTITION, NO_GLOBAL_COMBINATION e UNCLASSIFIED. Origem HISTORICAL, componente HistoricalReport e versão UNVERIFIED. O horário corresponde à gravação do artefato, não à execução comprovada no ERP. Alguns relatos foram provocados manualmente nos testes; a importação não comprova falhas de produção. Reimportação é idempotente.

## Contrato e armazenamento

- POST /monitoring/events: UUID do evento/execução, componente, versão, categoria, exceção, horário, impacto reportado, origem e protocolo opcional.
- GET /monitoring/issues?offset=0: até cem registros por página, no escopo autenticado.
- GET /monitoring/issues/{id}: caso e OS vinculada.
- PUT /monitoring/issues/{id}: revisão de severidade, responsável, status, motivo, proposta e validação; registra autor/horário/motivo.

Evento e contador são gravados juntos em snapshot JSON com troca atômica. Mesmo eventId com conteúdo diferente é rejeitado. Agrupamento usa componente + versão + categoria + exceção: não comprova causa comum, especialmente para UNCLASSIFIED. Protocolo opcional só é aceito no mesmo escopo. Nova falha LIVE posterior à resolução reabre o caso; histórico antigo não reabre automaticamente.

Limites: um processo por armazenamento; quinhentas issues, dez mil eventos, cem vínculos e duzentas entradas de auditoria por issue, snapshot até 8 MiB. Capacidade excedida rejeita gravação sem descarte silencioso. Retenção/arquivamento, backup automatizado, múltiplos processos e edição concorrente por vários operadores ainda precisam ser projetados. A auditoria não é versionamento completo de propostas/anexos.

## Criticidade

BAIXA: falha reportada como recuperada. MEDIA: bloqueio localizado ou impacto desconhecido. ALTA: indisponibilidade de processo reportada ou dez falhas LIVE únicas em quinze minutos. Histórico e duplicatas não contam na janela. Classificação automática é provisória; não comprova abrangência. Elevação automática não decai simplesmente com o tempo. CRITICA exige triagem com responsável e justificativa. Nenhum modelo classifica ou encerra automaticamente.

## Captura Java 8

Fonte consultada: C:/Users/annayran.ferreira/Documents/SKala, projeto zaya-skala-particionamento. ParticionarPedidosAction e DesfazerParticionamentoAction receberam um catch externo; sucesso não gera evento. Falha da captura preserva a exceção original. SQL, algoritmo, FQCNs e chamadas transacionais existentes não foram alterados.

O emissor transmite categoria, componente, versão disponível, classe da exceção, horário e IDs; não transmite mensagem bruta, stack trace, parâmetros, senha ou usuário ERP. HTTPS com identidade protegida via SPI, sem parâmetros de formulário, redirects ou retry. Timeouts de conexão/leitura: 500 ms; DNS/TLS/escrita não têm prazo global garantido. Emissão síncrona exige homologação das transações externas do container. Falhas de entrega são ignoradas para preservar o erro original; não há fila durável. Ausência de evento não prova saúde.

O JAR ordinário não tem identidade/endpoint e não transmite. O provedor temporário lê nexo-temporary-session.properties com expiração; ausente/expirado, não envia. Nenhum JAR foi instalado no ERP nem DML executado nesta etapa. Exceções tratadas internamente, falhas após retorno da ação e outros módulos não estão cobertos.

A mensagem exata de combinação global dos relatos não foi encontrada nessa fonte. É obrigatório confirmar a versão realmente implantada antes de substituir o JAR. Identificação exata de build, fila de entrega, coleta contextual automática e correlação ainda estão pendentes.

## Validação e sequência

Maven: 33 testes passaram, cobrindo autenticação, isolamento, deduplicação, agrupamento, janela de recorrência, triagem, validação obrigatória e reabertura. Gradle Nexo: checks de transporte e captura Java 8 passaram. Gradle Skala: build e runScenarios passaram. Tela verificada por HTTP e sintaxe JavaScript; sem inspeção visual automatizada.

Próximo marco: confirmar fonte/versão, provisionar HTTPS autorizado, instalar em homologação e provocar falha conhecida. Comparar erro original, evento e OS. Só depois propor correção na versão exata, com testes, revisão e rollback. O monitor não tem ferramenta de deploy, execução de SQL ou acesso direto ao ERP.
