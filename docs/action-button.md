# Integração por botão de ação → serviço central

O serviço Quarkus roda na infraestrutura da Zaya. Em cada hospedagem do cliente fica apenas o módulo do botão de ação, quando a instalação permitir esse tipo de customização e saída HTTPS. Não há serviço Quarkus, agente de IA ou acesso de entrada ao ERP na hospedagem do cliente.

## Fluxo implementado

1. Usuário seleciona uma nota na tela que disponibiliza NUNOTA e informa `MENSAGEM_ERRO`.
2. Rotina Java coleta contexto permitido e monta um pacote versão 1.
3. Transporte Java 8 envia `POST /investigations/packages?receipt=true` ao serviço central com identidade de integração mantida no backend.
4. Autenticação e registro administrado no servidor definem cliente, ambiente e políticas aprovadas. Campos extras de tenant, ambiente, causa raiz ou estado no pacote não concedem permissões e não são usados.
5. Executor valida pacote, consulta somente a biblioteca local isolada e executa as verificações Java. Não inicia consultas ao Sankhya.
6. Diagnóstico e contexto normalizado são persistidos juntos. O botão recebe `{id,state,simulated}` e exibe somente o ID. Suporte autenticado consulta `GET /investigations/{id}` e `GET /investigations/{id}/context`.

O recebimento e diagnóstico são síncronos nesta versão. Não há fila ou processamento em background no ERP. O transporte tem timeout de conexão de 5s e leitura de 30s, não segue redirects e não faz retries na aplicação. Mantenha IA desabilitada na primeira homologação para reduzir espera no botão. Timeout pode ocorrer após o serviço persistir o relatório; confira a entrega antes de reenviar, pois ainda não há idempotência por ID de coleta.

## Pacote e limites

Use [o exemplo sintético](examples/package-simulated.json) e consulte [o diagnóstico capturado](examples/package-response-simulated.json). O corpo contém:

- `schemaVersion=1`, NUNOTA positiva, ação `CONFIRMAR_NOTA` e erro exato de até 2000 caracteres.
- `collectedAt` UTC, versão ERP e versão do coletor; coleta até 24 horas atrás, tolerância futura de 60 segundos. O horário de coleta não é prova do estado no momento da confirmação.
- Até 100 itens, cada um com campos conhecidos e IDs das próprias evidências. Campos desconhecidos ficam `null`; nunca preencher dimensões ou fórmulas por hipótese.
- Até 200 evidências com ID único, fonte, versão e observação. O serviço aplica tenant/processo; o botão não informa esses controles.
- Lista explícita de informações não coletadas e indicador de simulação. Identidades do ambiente fixture sempre geram relatórios simulados.

Payload máximo: 256 KiB. Evidência até 2000 caracteres; referência por item até 20 IDs existentes no pacote. O pacote não permite SQL, URLs de callback, credenciais, comandos, estado de diagnóstico ou causa raiz. Fontes são referências e não são acessadas pela rede.

Para o alvo informado, use uma identificação que preserve também o módulo relevante, por exemplo `erpVersion="4.36b159/core-5.21.4"`. O provedor deve obter/verificar essa identificação na instalação; o valor relatado pelo usuário não constitui homologação de API ou regra funcional.

`ruleValidated=true` no corpo não valida uma política. O servidor só compara disponibilidade quando há aprovação do conjunto exato cliente/ambiente/referência/versão ERP/versão do coletor e dimensões equivalentes. Nenhuma fórmula de disponibilidade é calculada pelo receptor. Políticas reais começam sem aprovação. Este fluxo não produz causa raiz CONFIRMADA automaticamente: referências históricas ainda precisam de verificação causal revisada.

## Executar localmente

Build na raiz: `mvn verify`. Inicie o JAR com usuários de demonstração:

```powershell
java '-Djdk.net.unixdomain.tmpdir=target' '-Dquarkus.profile=dev' -jar target/quarkus-app/quarkus-run.jar
```

Em outro terminal, dentro da raiz do projeto:

```powershell
$packet = Get-Content docs/examples/package-simulated.json -Raw | ConvertFrom-Json
$packet.collectedAt = [DateTime]::UtcNow.ToString('o')
$pair = [Convert]::ToBase64String([Text.Encoding]::UTF8.GetBytes('demo-a:fixture-only-a'))
$headers = @{Authorization="Basic $pair"}
$receipt = Invoke-RestMethod 'http://127.0.0.1:8080/investigations/packages?receipt=true' `
  -Method Post -Headers $headers -ContentType 'application/json' -Body ($packet | ConvertTo-Json -Depth 20)
Invoke-RestMethod "http://127.0.0.1:8080/investigations/$($receipt.id)" -Headers $headers
Invoke-RestMethod "http://127.0.0.1:8080/investigations/$($receipt.id)/context" -Headers $headers
```

Para receber o diagnóstico completo diretamente, omita `?receipt=true`. O exemplo sintetiza uma divergência e retorna HIPOTESE, não uma causa comprovada no ERP.

## Configurar nosso servidor

`config/central.properties.example` e `config/identities.example.json` mostram a configuração. Copie para `config/private/`, mantenha fora do Git e configure o segredo no servidor central. O manifesto não contém senhas: vincula principal autenticado a tenant/ambiente e políticas aprovadas. O principal precisa também da role `investigator`; registrar o principal no manifesto não o autentica.

Inicialização após configurar os arquivos e a variável `NEXO_INTEGRATION_PASSWORD` no nosso servidor:

```powershell
java '-Djdk.net.unixdomain.tmpdir=target' '-Dquarkus.config.locations=config/private/central.properties' `
  -jar target/quarkus-app/quarkus-run.jar
```

Publique somente atrás de HTTPS configurado na nossa infraestrutura. O listener local foi preservado. Rotacione credenciais por integração, restrinja ACL e retenção de `data/` e dos manifestos. Não use usuários ou políticas de demonstração para dados reais.

Uma aprovação real, após homologação, terá formato:

```json
{"reference":"politica-cliente-v1","erpVersion":"versao-homologada","collectorVersion":"coletor-homologado-v1"}
```

Inclua no `approvedPolicies` do binding correspondente somente após validar a semântica da coleta e disponibilidade. Atualizar ERP ou coletor exige nova revisão. Essa aprovação não demonstra origem de divergência.

## Compilar e cadastrar o botão

Código separado em `integration/sankhya-button`, Java 8. A classe `br.com.zaya.engineering.nexobutton.SendIncidentAction` usa `AcaoRotinaJava`, seleção de uma linha, `getCampo("NUNOTA")`, parâmetro `MENSAGEM_ERRO` e mensagem de retorno. NUNOTA de TGFCAB foi confirmado no catálogo 4.35b815; a tela real precisa disponibilizá-lo e ter permissões apropriadas.

O build Gradle corporativo foi localizado e aplicado ao módulo. Dependências locais encontradas em `C:/dev/zaya/platform/modulos-sankhya/Dependencias/4_35`, incluindo `mge-modelcore-4.35b99.jar`. A ação foi compilada com essas APIs, sem empacotar bibliotecas Sankhya. O SDK exato de 4.36b159 continua não localizado; compilação local não é homologação nessa versão.

JAR gerado: `integration/sankhya-button/build/libs/ZAYA-NEXO-V1.3.jar`.

Para reconstruir com as dependências corporativas existentes:

```powershell
./integration/sankhya-button/build.ps1
```

O helper executa o Wrapper Gradle 9.0.0 do template corporativo e direciona cache/sockets temporários ao `target/` deste repositório, restaurando as variáveis de ambiente depois. O serviço central continua com seu build Maven separado. Também é possível executar `gradlew.bat build` dentro do módulo, configurando o diretório de sockets do JDK neste Windows se necessário.

Após exportar a biblioteca de extensões exata da instalação alvo, é possível usar a substituição explícita:

```powershell
./integration/sankhya-button/build.ps1 -ExtensionsJar '<caminho-do-JAR-da-instalação>'
```

O Gradle é a fonte única do nome/versão do artefato e gera bytecode Java 8 (`--release 8`), sem migrar o runtime do ERP. Diretório de dependências pode ser configurado por `sankhyaDependenciesDir`, `SANKHYA_DEPENDENCIES_DIR` e `sankhyaVersionDir`; `sankhyaExtensionsJar` substitui explicitamente pelo JAR exportado. Compilação da ação, teste de transporte, manifesto, presença das cinco classes e ausência de dependências ERP no JAR foram verificados. A tarefa `test` padrão é desabilitada neste módulo porque os testes existentes são executáveis sem JUnit, rodados por `transportCheck`, obrigatório no `build`. Isso não equivale a executar o botão dentro do Sankhya alvo.

Cadastro, se autorizado pela hospedagem: módulo Java com o JAR, ação tipo Rotina Java na instância da nota, classe acima e parâmetro obrigatório de texto `MENSAGEM_ERRO`. Restrinja quem pode acionar. Esse procedimento segue a [documentação oficial de Rotina Java](https://developer.sankhya.com.br/docs/rotina-java) e [ações personalizadas](https://ajuda.sankhya.com.br/hc/pt-br/articles/360045111093-Configurando-A%C3%A7%C3%B5es-Personalizadas); não confirma permissões de SankhyaCloud ou Datacom para o cliente.

O botão suporta `ButtonConfigurationProvider`, carregado por ServiceLoader, para obter configuração do mecanismo nativo protegido que a hospedagem permitir. Sua implementação recebe `ContextoAcao` e retorna endpoint, principal, segredo e versão ERP definidos administrativamente. Cadastre o FQCN da implementação em `src/main/resources/META-INF/services/br.com.zaya.engineering.nexobutton.ButtonConfigurationProvider`; o build inclui os recursos no JAR. Esse provedor não precisa de alteração no ambiente/JVM da hospedagem.

Na V1.2 não há leitura de parâmetros administrativos nem variáveis de ambiente do servidor. A integração depende exclusivamente do ButtonConfigurationProvider provisionado pela Zaya. Sem provedor, a ação informa instalação pendente antes do envio. Consulte zero-parameter-installation.md; ainda não existe provedor concreto nem receptor HTTPS publicado.

## Coletor mínimo e coleta completa

`MinimalPackage` envia somente NUNOTA selecionada e relato, com itens vazios e lacunas explícitas. É um envio real, mas resulta em INCONCLUSIVA para estoque. **A classe entregue não coleta estoque, TOP ou histórico.** O receptor já aceita o pacote completo e foi testado com itens e evidências sintéticas; o próximo trabalho no cliente é substituir esse coletor pela leitura validada desses dados.

Antes de habilitar consultas: validar versão ERP/DB, campos e dimensões, versão histórica de TOP, conversões de unidade, controles, disponibilidade aplicável, documentos e eventos acessíveis. Coletar em lote com filtros da nota, limites e fechamento de recursos. Não liberar SQL vindo do servidor, modelo ou usuário. O botão não altera notas, estoque ou configurações.

Homologação deve verificar SDK, permissões da ação e coleta, secret store disponível, saída HTTPS/TLS da hospedagem e tempo de execução permitido. Sem essas verificações não há instalação homologada nem diagnóstico real de estoque.


### V1.2 — sem parâmetros manuais

A configuração da V1.1 foi substituída. Não cadastrar NEXO_ENDPOINT, NEXO_PRINCIPAL ou NEXO_ERP_VERSION no Sankhya. Provisionamento pela Zaya ainda pendente; veja [decisão e limitações](zero-parameter-installation.md).

## Validação local automatizada

Execute `./scripts/Test-NexoConnection.ps1` após compilar o serviço. O script inicia o JAR em loopback, porta 18080 (substituível por `-Port`), com identidades de demonstração, IA desligada e armazenamento isolado em `target/connection-check-<id>`. Atualiza o horário do pacote sintético, envia o pacote e valida o recibo, o diagnóstico e o contexto recuperado. Encerra o processo iniciado mesmo em caso de falha. Uma porta já ocupada causa falha antes de iniciar o serviço.

Este teste não instala o botão, consulta o ERP, valida saída HTTPS da hospedagem ou avalia respostas de um modelo. Dados e logs sintéticos permanecem em `target/` para inspeção.

Em 09/10/2026 foi confirmado que ainda não existe servidor/domínio para o receptor. O próximo marco externo exige escolher a infraestrutura e disponibilizar HTTPS; localhost da máquina de desenvolvimento não é um endpoint acessível pelo Sankhya hospedado. Antes de dados reais, também é necessário implementar a configuração protegida da credencial na hospedagem.

## Homologação temporária V1.3

Consulte [sessão de homologação](temporary-homologation.md). O JAR privado de sessão é provisionado com credencial individual expirável; o artefato comum continua sem configuração. A ação envia nota, relato e sinais agregados do log, sem estoque ou log bruto. Nenhuma rotina de SQL livre foi adicionada.
