# Adequação ao desenvolvimento de referência

Data: 06/11/2026. Checkout: Add-on Bancário, branch master, sem origin configurado. Esta etapa foi autorizada após a análise do manual e do ZIP; não houve mudança de branch/remoto, instalação no ERP ou publicação.

## Base adotada

Foi incorporado o snapshot `zaya-api-boletos-main.zip`, SHA-256 `FCE14EBECFA2684FC9CAB96118F979946A4EAC1A4C9841C5224EE752CC42446F`, fornecido pelo solicitante. Os 44 arquivos Java, incluindo os testes, foram conferidos por hash e permaneceram idênticos à referência. O namespace, os contratos e os objetos ZYCB foram preservados.

O antigo núcleo `br.com.zaya.bancario` e o serviço isolado `ZayaBoletoSP` foram retirados da aplicação ativa. Nenhuma instalação desses serviços havia sido realizada nesta conversa. Agora a entrada é `ZycbBillingServiceSP`, com a central HTML5 e as camadas application/domain/ports/banks/persistence/sankhya/security da referência.

Isso incorpora intenções transacionais, reserva antes do envio, tratamento de resultado incerto, conectores Itaú/Santander, reimpressão por BoletoHelper, mensageria, recebimentos, baixa nativa, permissões por empresa e cofre. A disponibilidade do código não equivale à homologação desses fluxos no ERP/banco.

## Preservação do trabalho anterior

- Fontes anteriores: `out/migracao/antes-adequacao.zip`, 58 arquivos conferidos; SHA-256 `BFFDFCF749C7E35319013D35851A3689015FC5C2013279325F12030577FBB42B`.
- Documentação completa anterior, incluindo README/changelog: `docs/historico/antes-adequacao`.
- A pasta `.git` foi preservada. Não existia identidade real do componente no checkout e o ZIP não a forneceu; nenhuma chave foi criada ou substituída.

## Ajustes locais sobre a referência

| Arquivo | Ajuste |
| --- | --- |
| `.gitignore` | Protege saídas, JDK portátil, backups e binários; preserva o Wrapper versionável. |
| `build.gradle` | `--release 8`, Python configurável, identidade por arquivo/ambiente e perfil de validação de código sem identidade. |
| `model/build.gradle` | Verifica o SDK local; permite seleção explícita do SDK Maven oficial e JAR NF-e licenciado complementar em compileOnly. |
| `gradle.properties` | Kotlin em processo para não depender de daemon permanente e diretórios externos de cache. |
| `scripts/Build-Addon.ps1` | Runner Windows com JDK 17, testes, verificação frontend e smoke opcional; restaura variáveis do processo. |

O modo sem identidade não gera descritores de distribuição. Ele recusa gerarAddon/deployAddon/publishAddon antes da execução das tarefas. Não simula identidade para produzir um pacote. O fluxo normal de empacotamento e a identidade da solução existente continuam obrigatórios.

O runner resolve o caminho de identidade antes de mudar o diretório. A revisão independente identificou esse problema e a correção foi aplicada. Também usa o alias curto do JDK no Windows para contornar a interpretação de caminho Unicode pelo KSP.

`-SemProxy` remove proxies somente durante o processo. `-IpcTcp` permite o fallback TCP do JDK para hosts que recusam AF_UNIX; não altera verificação TLS ou transporte dos bancos. São opções específicas de execução, não exigências gerais de uma instalação Sankhya.

## Ambiente e evidências desta validação

- JDK 17.0.21+9 obtido do Adoptium, com SHA-256 conferido contra o metadado oficial; fica em `out/toolchains`, fora do Git.
- Gradle Wrapper 8.2 e Add-on Studio 2.18.0 da referência.
- SDK oficial disponibilizado pelo plugin; o módulo NF-e necessário não estava incluído nessa distribuição. O complemento utilizado foi o JAR licenciado local `sannfe-4.35b99.jar`, SHA-256 `32CCD2662591BCFFB4A71BB5EF95A8406B44E5310B2D67B8336EF09F551C6403`, referenciado externamente, não copiado ao projeto nem declarado como dependência de runtime.
- Não foi localizado/obtido o SDK 4.36b88 exato solicitado pela referência. O uso do complemento 4.35 é declarado como referência de compilação/testes, sem atribuir a ele compatibilidade 4.36.
- 38 casos Java aprovados: 15 de orquestração, 12 de conectores, 2 de criptografia e 9 de certificados, sem falhas/erros.
- 88 classes do produto verificadas com major version 52, Java 8.
- Estrutura/XML/UTF-8/contrato Itaú, parâmetros frontend e sintaxe JavaScript aprovados.
- Smoke headless desktop/mobile aprovado com serviços simulados; imagens em `out/validacao/ui`.
- Bloqueio de empacotamento pelo perfil sem identidade testado por grafo dry-run.
- Revisão independente em leitura do delta de build/migração concluída, sem novos achados comprovados após a correção do caminho da identidade.

Relatórios JUnit: `model/buildGradle/test-results/test`. Evidências do runner e fidelidade ao ZIP: `out/migracao`. O complemento NF-e é compileOnly/teste, mas a inspeção de um pacote final continua pendente, pois não foi produzido `.exts` nesta etapa.

## Uso do runner

Com o SDK local da referência:

```powershell
.\scripts\Build-Addon.ps1 -JavaHome '<JDK17>' -SnkLib '<raiz SDK>' -PythonExe '<python>' -NodeExe '<node>'
```

Com o SDK oficial disponível, escolhendo explicitamente um complemento autorizado:

```powershell
.\scripts\Build-Addon.ps1 -JavaHome '<JDK17>' -SdkOficial -NfeJar '<JAR NF-e>' -PythonExe '<python>' -NodeExe '<node>'
```

Sem identidade, executa apenas validação. Para gerar o pacote, fornecer a identidade autorizada pelo mantenedor e acrescentar `-GerarPacote`. O runner não publica ou instala o produto. Não reutilizar uma identidade fictícia, não versionar arquivos confidenciais e não habilitar perfis por causa de um teste local aprovado.

## Pendências reais

Identidade autorizada para empacotamento; validação com SDK/runtime 4.36b88; instalação e migrações em Oracle/SQL Server reais; cofre e certificados no destino; contratos bancários e homologação financeira. Os perfis Itaú e Santander permanecem com `homologado=false`.

Os manuais recebidos descrevem o produto e as evidências do ambiente de origem. Este documento e a seção local de HOMOLOGACAO distinguem o que foi efetivamente executado neste checkout.
