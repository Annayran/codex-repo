# Nexo AI — investigação de incidentes

Monitoramento incremental por ação agendada: veja [instalação, limites e homologação](docs/scheduled-log-monitor.md). O módulo V1.8 envia novos erros filtrados para a central local de issues/OS, sem alterar operações do ERP.

Primeiro fluxo executável de investigação de erro ao confirmar nota. Serviço externo ao ERP; Java 21, Quarkus 3.33.2 e extensão LangChain4j 1.12.0. Pacote de ferramenta interna `br.com.zaya.engineering.nexo`. Nenhuma dependência ou runtime do ERP é alterado.

O fluxo principal agora é **botão de ação do cliente → pacote de evidências por HTTPS → nosso serviço central**. O receptor `POST /investigations/packages` não consulta o ERP; valida os dados coletados e mantém identidade/ambiente definidos pela autenticação. Consulte [integração por botão de ação](docs/action-button.md) para testar, configurar nosso servidor e compilar o adaptador Java 8. O coletor inicial do botão envia somente NUNOTA e relato; coleta completa de estoque e configurações ainda depende de homologação na instalação alvo.

Alvo informado pelo usuário: Sankhya OM `4.36b159`, ERP Core `5.21.4`. O SDK local `4.35b99` foi encontrado e usado para compilar o botão pelo Gradle corporativo. Artefato: `integration/sankhya-button/build/libs/ZAYA-NEXO-V1.6.jar`. SDK/dicionário exatos da versão alvo não foram localizados, e a execução em 4.36b159 exige homologação. Sem instalação ou deploy na hospedagem do cliente nesta entrega.

## Iniciar

Requer JDK 21 e Maven 3.9+. Execute `mvn test`, `mvn package`, ou `mvn quarkus:dev`. Neste computador o Maven disponível é `C:\Program Files\Apache NetBeans\java\maven\bin\mvn.cmd`.

Artefato: `java -jar target/quarkus-app/quarkus-run.jar`. Em produção não existem usuários padrão: configure autenticação e o manifesto de identidades antes de iniciar. No modo dev/test os usuários de demonstração são vinculados ao próprio ambiente fixture. Não exponha essa configuração de desenvolvimento à rede.

Neste Windows o JDK falhou ao criar sockets locais no diretório temporário padrão. A verificação isolada passou usando `target/`; o profile Maven `windows-local-sockets` aplica esse ajuste automaticamente nos testes. Para iniciar o JAR local com usuários de fixture: `java -Djdk.net.unixdomain.tmpdir=target -Dquarkus.profile=dev -jar target/quarkus-app/quarkus-run.jar`. O diretório `target` deve existir. A [documentação Java](https://docs.oracle.com/en/java/javase/21/core/java-networking.html) descreve a propriedade de diretório temporário dos sockets Unix-domain. Não foi necessário trocar ou atualizar o JDK.

PowerShell, exemplo legado de investigação por fixtures, com o serviço em modo dev (o envio pelo botão está em [docs/action-button.md](docs/action-button.md)):

```powershell
$pair = [Convert]::ToBase64String([Text.Encoding]::UTF8.GetBytes('demo-a:fixture-only-a'))
$headers = @{Authorization="Basic $pair"}
$body = @{nunota=1001; action='CONFIRMAR_NOTA'; error='Erro sintético de estoque'} | ConvertTo-Json
$report = Invoke-RestMethod 'http://127.0.0.1:8080/investigations' -Method Post -Headers $headers -ContentType 'application/json' -Body $body
$report | ConvertTo-Json -Depth 10
Invoke-RestMethod "http://127.0.0.1:8080/investigations/$($report.id)" -Headers $headers
```

Credenciais acima são exclusivas de fixtures, sem relação com ERP. O corpo não define cliente, ambiente ou permissões. Autenticação mapeia `demo-a`/`demo-b` para seu próprio escopo fixture. Nota inexistente no escopo retorna 404.

Resposta reduzida (campos adicionais no relatório persistido):

```json
{"simulated":true,"symptom":"Erro sintético de estoque","action":"CONFIRMAR_NOTA","process":"confirmation-stock","state":"HIPOTESE","rootCause":null,"divergences":[{"kind":"DISPONIBILIDADE","detail":"Availability supplied by validated policy is below required quantity; origin not established","evidenceIds":["fixture-context"]}],"missing":["ERP event history and related documents unavailable","Customizations not inspected","Client-specific reviewed library has not been imported"]}
```

## Cenários e critérios

Notas 1001–1006 (`demo-a`) e 2001–2006 (`demo-b`): insuficiência, local divergente, unidade diferente, controle diferente, disponibilidade limitada por política sintética e política ausente. Todas são simuladas, sem consultas ERP. A disponibilidade é fornecida pelo gateway: nunca calculada como saldo menos reserva. Comparação só ocorre com política validada e dimensões equivalentes. Conversões ausentes não são inventadas.

CONFIRMADA exige origem demonstrada, referências causais existentes e evidências não simuladas. HIPOTESE indica divergência com origem não demonstrada. INCONCLUSIVA indica ausência de divergência demonstrável. Fixture nunca confirma causa real. Um saldo insuficiente descreve a divergência, não sua origem.

## Configuração e limites

`nexo.gateway=disabled` por padrão; dev/test habilitam fixtures para o endpoint antigo. O receptor de pacotes independe do gateway e nunca inicia consultas ao ERP. `nexo.max-items=100`, payload até 256 KiB e validação de schema, timestamp e referências limitam a coleta recebida. Políticas de disponibilidade precisam de aprovação administrativa por cliente, ambiente, versão ERP e coletor; o flag do remetente não basta. Relatório e contexto normalizado são persistidos atomicamente em `data/<tenant>/<environment>/<uuid>.json`. O erro original pode conter informação sensível, portanto restrinja ACLs e defina retenção antes de uso real. Nenhum segredo de autenticação é salvo nos relatórios.

IA desligada por padrão. Para testar configure `NEXO_AI_ENABLED=true`, `OPENAI_API_KEY` e `NEXO_MODEL` por ambiente. A IA escolhe a ordem das duas ferramentas autorizadas; Java valida a escolha e executa ambas obrigatoriamente. Há no máximo duas invocações de modelo por investigação, timeout de 10s e `max-retries=1` (o mínimo aceito pela extensão). Somente ação, achados estruturados, lacunas e trechos revisados de conhecimento são enviados; o texto bruto do erro não é enviado. Falha do modelo preserva diagnóstico determinístico. Sem memória compartilhada, SQL, mutação de ERP ou ferramentas de escrita. Interpretação do relato bruto e seleção de ferramentas reais adicionais aguardam política de tratamento de dados e homologação.

`scripts/Import-Knowledge.ps1 -SourceFile <arquivo-revisado> -Version <versao> -Tenant demo-a -Process confirmation-stock -Environment fixture -Reviewed` importa um documento escolhido em `knowledge/reviewed.json`. Use `-Simulated` para referências sintéticas. Revise a ausência de dados sensíveis antes de importar; não há coleta ampla. Configure `NEXO_KNOWLEDGE_MANIFEST` com o caminho absoluto para carregar no startup. O manifesto persistente é ignorado pelo Git e deve ter ACL restrita. Limites: 50 documentos, manifesto de 1 MB, conteúdo de 20000 caracteres por documento e cinco referências por recuperação. `KnowledgeStore` separa cliente, ambiente e processo. Conteúdo é dado não confiável e não altera permissões; trechos enviados à explicação são limitados a 1000 caracteres por documento.

## Inspeção e homologação

Repositório inicialmente vazio: sem código, build, integrações, AGENTS.md físico ou profile. Instruções AGENTS fornecidas no chat aplicadas. Biblioteca localizada em `C:\dev\zaya\engineering\zaya-engineering`; consultados seletivamente `standards/java.md`, `standards/project-structure.md` e catálogo de tabelas 4.35b815. TGFCAB, TGFITE, TGFEST e TGFTOP existem no catálogo; campos, vínculos, TOP histórica, regras de disponibilidade e versão do ERP **não foram verificados no ambiente**. Nenhuma consulta SQL foi criada com essas suposições. Instruções de agentes do Codex não são agentes de produção.

Para conectar o botão: fornecer o SDK e dicionário da instalação alvo; definir autenticação central e configuração protegida da integração no cliente; validar consultas locais de coleta e política de disponibilidade; completar o coletor de evidências; revisar saída HTTPS da hospedagem, ACL e retenção; avaliar um incidente conhecido. Sem credenciais ou homologação no ERP nesta entrega. O contrato de gateway antigo foi preservado para fixtures, mas não participa do envio central.

Versões selecionadas pelo [POM oficial LangChain4j 1.12.0](https://github.com/quarkiverse/quarkus-langchain4j/blob/1.12.0/pom.xml), que declara Quarkus 3.33.2; pré-requisitos no [guia Quarkus](https://quarkus.io/guides/getting-started). [Backlog](docs/backlog.md), [validação e limitações](docs/validation.md), [requisição](docs/examples/request.json) e [resposta simulada completa](docs/examples/response-simulated.json).


Validação local automatizada do receptor: execute ./scripts/Test-NexoConnection.ps1 após o build. Inicia e encerra o serviço local, envia evidências sintéticas e confere recibo, diagnóstico e contexto, sem ERP ou IA. Servidor/domínio central ainda não disponível (09/10/2026).


V1.2: o botão não lê parâmetros NEXO nem variáveis de ambiente do servidor. Ainda exige provisionamento de receptor e identidade pela Zaya; não há provedor concreto. Veja [instalação sem parâmetros](docs/zero-parameter-installation.md).


V1.3: sessão temporária de homologação e pacote individual expirável, além de sinais agregados do log. Veja [escopo e limites](docs/temporary-homologation.md). Artefato comum não inclui credenciais. HTTPS público ainda precisa de validação pela hospedagem.


V1.5: coleta de itens, produto ativo e linhas de estoque por SELECT fixo e limitado; consulte [escopo e homologação](docs/product-stock-collection.md). Não calcula disponibilidade. Receptor atualizado preserva os novos campos; nenhuma consulta real foi executada pelo agente. IA continua desligada.


V1.6: trechos filtrados de log podem acompanhar o incidente e gerar anexo .log autenticado. Veja [coleta, correlação e limites](docs/incident-log-excerpts.md). Protocolos anteriores precisam ser reenviados para coletar o trecho. Não há envio de log completo nem execução de IA.

### Homologação com IA local (15/10/2026)

O perfil `local` usa Ollama qwen2.5:3b no próprio computador, sem chave de API paga. A interface está em `/nexo.html`. Execute `scripts/Start-NexoLocal.ps1`; detalhes e encerramento em [docs/local-homologation.md](docs/local-homologation.md).

O botão envia e persiste evidências sem esperar por inferência. Na tela, consulte o protocolo e solicite a análise em `/investigations/{id}/review`; ela gera novo protocolo, preservando o original. O perfil sintético local nunca deve ser exposto publicamente. A publicação temporária com identidade individual aceita `-LocalAi` e permanece limitada ao prazo autorizado.

### Central local de issues/OS da Skala

Execute `scripts/Start-NexoDesk.ps1 -CopyPassword` e abra http://127.0.0.1:18085/issues.html. O monitor agrupa falhas, mantém OS locais, permite triagem e validação manual e separa histórico de captura ao vivo. Veja [docs/monitoring-skala.md](docs/monitoring-skala.md) para limites, uso e implantação pendente. Não há publicação de túnel nem correção automática do ERP.
