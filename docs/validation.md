# Validação do primeiro fluxo

Data: 2026-10-06. Ambiente: Windows, Temurin JDK 21.0.10 e Maven instalado com Apache NetBeans. Serviço Quarkus 3.33.2 e extensão LangChain4j 1.12.0.

## Execução

```powershell
& 'C:\Program Files\Apache NetBeans\java\maven\bin\mvn.cmd' '-Dmaven.repo.local=target/m2' -B -ntp verify
```

Cache de dependências localizado dentro de `target/m2` porque o cache global não era gravável nesta sessão. O profile Windows dos testes direciona sockets temporários do JDK para `target`, após falha de conexão no temporário padrão. Não houve instalação nem atualização de ferramentas.

Na primeira entrega seis métodos de teste passaram. Após adaptação para pacote enviado pelo botão, dezesseis métodos passaram, sem falhas, erros ou skips:

- Seis cenários sintéticos sem inventar origem: notas 1001–1006.
- Tipos de divergência esperados: disponibilidade, local, unidade e controle; política sintética limitando disponibilidade.
- Cliente autenticado separa notas e persistência; tenant/ambiente extras no corpo não mudam o escopo.
- Autenticação e limites: 401, ação inválida, texto extenso, identificador extremo e payload acima de 256 KiB.
- Orçamento de execução e conhecimento isolado por cliente e ambiente.
- Manifesto revisado carregado e referenciado somente no escopo correspondente.
- Seis cenários recebidos por pacote sem consulta ao gateway, inclusive uma NUNOTA inexistente nas fixtures.
- Rejeição de schema, timestamp e referências inválidos; limite de itens.
- Tentativas de declarar tenant/ambiente/causa/estado/aprovação não mudam o escopo nem confirmam origem.
- Campos ausentes ou dimensões em branco produzem informação ausente, sem comparar disponibilidade indevidamente.
- Contexto normalizado e diagnóstico persistidos e recuperados somente no próprio escopo.
- Referências de achados restritas ao item correspondente e opção de protocolo curto para o botão.
- Aprovação de política não se transfere para outra versão ERP ou coletor.
- Transporte e serialização do botão compilados com `--release 8` e executados contra servidor HTTP local: JSON escapado, POST autenticado, protocolo curto, redirects bloqueados, falha HTTP e limites de entrada/resposta. Execução dos testes ocorreu no JDK 21; não é validação do runtime Java 8 da hospedagem.

Build produziu `target/quarkus-app/quarkus-run.jar`. O JAR empacotado foi iniciado localmente na porta 18080 com profile dev, importação sintética e IA desabilitada. POST retornou HIPOTESE, `simulated=true`, causa raiz ausente e três evidências; GET recuperou o relatório pelo mesmo ID. O processo foi encerrado após a verificação. O importador PowerShell também foi executado com um arquivo sintético explicitamente revisado.

Exemplos capturados: [requisição](examples/request.json) e [resposta simulada](examples/response-simulated.json). UUID e tempos pertencem somente à execução de demonstração.

Após adaptação, o JAR final foi verificado novamente em duas configurações locais, usando somente dados e credenciais sintéticos:

- Profile dev: `POST /investigations/packages?receipt=true` e GET de diagnóstico/contexto passaram; política fixture retornou HIPOTESE e `simulated=true`, sem consulta ao ERP.
- Profile normal com manifesto administrado de identidades: mesmo fluxo passou, tenant foi definido pelo principal e política não aprovada resultou em INCONCLUSIVA. O endpoint antigo de consulta direta retornou 503, conforme configuração desabilitada.

Ambos os processos foram encerrados. [Pacote sintético](examples/package-simulated.json) e [diagnóstico capturado](examples/package-response-simulated.json). Isso verifica configuração central local; não é deploy, identidade corporativa ou comunicação com a hospedagem do cliente.

## Limites da validação

Não houve consulta ao ERP, validação de SQL/campos/vínculos no ambiente, incidente real, configuração histórica ou autenticação corporativa. O alvo informado pelo usuário é OM 4.36b159/Core 5.21.4; SDK e dicionário exatos dessa versão não foram localizados. Após nova busca, `SendIncidentAction` compilou contra as dependências locais 4.35b99 pelo Wrapper Gradle corporativo 9.0.0. `build.ps1` passou, incluindo `transportCheck`. O artefato `ZAYA-NEXO-V1.0.jar` contém cinco classes com major version 52 (Java 8), manifesto corporativo 1.0 e nenhuma biblioteca do ERP. SHA-256: `C8D45239021B88A149D404382D8ABFC53A62DCB6F17F86271D89F8DBD60C888E`. Isso não valida execução em 4.36b159. Nenhuma chamada a um provedor de IA foi executada. Os testes sintéticos não homologam regras Sankhya nem comprovam causas reais.

Ainda é necessário testar SDK/ação e coleta real, configuração nativa protegida, saída HTTPS da hospedagem, identidade corporativa, armazenamento/retenção e incidente de causa conhecida. O coletor mínimo entregue envia nota/relato e indica a ausência de estoque/configurações; não diagnostica estoque real sozinho. Reenvio ainda não possui idempotência. Não houve commit, push, deploy ou alteração no ERP.
