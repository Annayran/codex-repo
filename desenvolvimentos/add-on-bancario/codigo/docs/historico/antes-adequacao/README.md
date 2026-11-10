# Add-on Bancário Zaya

Projeto Add-on Studio para Sankhya, com núcleo compartilhado e Santander como primeiro banco. Stack: Java 8, Gradle e MVC. Template oficial incorporado em `model` e `vc`.

## Estado atual

- Geração nativa de PDF pelo `BoletoHelper`, por um NUFIN.
- Serviço EJB `ZayaBoletoSP.gerarPdf`, autenticado, com permissão explícita de emissão e transação Required.
- Controller, entidades e contratos comuns separados dos adaptadores Sankhya.
- Catálogo preliminar de endpoints Santander; autenticação HTTP e registro bancário direto ainda não implementados.
- Build oficial de model/vc passou com JDK 8u492 e Gradle 8.2; seis grupos de verificações executados sem emissão real.
- Pacote instalável ainda não gerado: a tentativa de `gerarAddon` parou porque `ADDON_APP_KEY` não está configurada. Nenhum deploy ou teste no ERP foi executado.

## Estrutura

| Local | Responsabilidade |
| --- | --- |
| `model/src/main/java` | Serviço de entrada Sankhya/EJB. |
| `core/src/main/java` | Controller, entidades e contratos do model. |
| `core/src/sankhya/java` | Adaptador BoletoHelper. |
| `vc` | Infraestrutura web oficial e provider gerado; ainda sem tela de emissão. |
| `scripts` | Build e verificações locais. |

O módulo `model` inclui os fontes existentes de `core`, sem copiá-los ou alterar seus FQCNs. Interfaces EJB, descritores e provider são gerados pelo plugin oficial. Não há tabelas, scripts de banco, jobs ou listeners de demonstração no pacote bancário.

## Build

```powershell
.\scripts\Build-Addon.ps1 -JavaHome '<caminho-do-JDK8>'
```

O script executa `:model:build :vc:build`. `-Offline` reutiliza o cache e `-SemProxy` remove os proxies apenas no processo, restaurando-os ao final; usar essa opção somente quando necessária na rede. Não configura permanentemente o Windows.

O Wrapper da raiz mantém Gradle 8.2. A faixa de plugin `1+` veio do template e resolveu 1.14.5 neste build. JDK 21 não deve ser usado para executar esse Wrapper. O build isolado anterior de `core` permanece disponível; ele não substitui o empacotamento oficial.

Saídas intermediárias conferidas:

- `model/buildGradle/libs/zaya-bancario-model.jar`
- `vc/buildGradle/libs/vc.war`

Não instalar esses arquivos isoladamente como se fossem o pacote final: ainda há substituições de configuração realizadas pelo empacotamento oficial.

## Configuração e homologação

- `ADDON_APP_KEY`: chave real do componente, via ambiente. Não salvar no código ou enviar no chat.
- `WILDFLY_HOME`: ambiente de desenvolvimento para as etapas que dependam de servidor.
- `BANCARIO_EMISSAO_USUARIOS`: códigos dos operadores autorizados, separados por vírgulas. Sem configuração, a emissão é negada.

Com a chave disponível, `Build-Addon.ps1 -JavaHome '<caminho-do-JDK8>' -GerarPacote` tenta o empacotamento oficial. O script não executa deploy ou publicação.

Antes do uso real, confirmar versão do ERP e identidade do componente, homologar permissões por empresa/título, geração de numeração, PDF e efeitos do Boleto Rápido. O BoletoHelper está depreciado na biblioteca local 4.35b99; seu uso foi solicitado e está isolado no adaptador. Essa biblioteca é referência de compilação local, não confirmação da versão do cliente.

O PDF é um documento de cobrança, não o JSON de registro Santander nem uma confirmação de pagamento. A geração nativa pode alterar numeração e acionar fluxos configurados no ERP. Não combinar registro nativo e direto sem validar os efeitos.

## Documentação

- [Serviço de emissão, contrato e evidências de build](docs/servico-emissao.md).
- [Incorporação e validação do template](docs/template-import.md).
- [Arquitetura e pesquisa de reaproveitamento Sankhya](docs/architecture.md).
- [Contratos públicos Santander e limitações](docs/santander-api.md).
- [Histórico de desenvolvimento](CHANGELOG.md).
