# Guia técnico do desenvolvedor Zaya API Boletos

> Este guia acompanha o desenvolvimento recebido. Para o estado e as evidências do checkout adaptado nesta conversa, consultar [ADEQUACAO_LOCAL.md](ADEQUACAO_LOCAL.md): a identidade não veio no ZIP, a suíte foi executada em perfil explícito sem identidade e a composição local de SDK não comprova o runtime 4.36b88.

Manual de validação, manutenção e evolução da integração bancária no Sankhya. Versão de referência 0.1.0, revisão de 6 de novembro de 2026. Público: desenvolvedor mantenedor, revisor técnico e responsável pela homologação financeira.

A implementação oferece uma base transacional para cobrança convencional e recebimentos via Itaú e Santander. A próxima responsabilidade do mantenedor é comprovar seu funcionamento no ambiente Sankhya e nos contratos bancários reais antes da liberação operacional. Compilação e testes locais aprovados não equivalem a homologação bancária, financeira ou Cloud. Os dois perfis permanecem bloqueados por `homologado=false`.

## 1 Como começar

Leia este manual com `README.md`, `docs/CONTRATOS_BANCARIOS.md`, `docs/CERTIFICADOS.md`, `docs/INSTALACAO.md` e `docs/HOMOLOGACAO.md`. Os caminhos citados são relativos à raiz do repositório. Use o código como referência final quando houver divergência com a documentação.

O repositório exclusivo é `ZayaIT/zaya-api-boletos`, privado. Clone-o em uma pasta própria; o projeto independe do monorepositório e do ZayaProd. O código está em `br.com.zaya.apiboletos`, com objetos próprios prefixados por `ZYCB`. Não transportar contas, empresas, TOPs ou credenciais da Imperial para o código.

O Sicoob foi referência de desenho para intenção transacional, reserva antes da inclusão, prevenção de repetição após timeout, recuperação, baixa nativa e separação da entrega. Autenticação, identificação, payloads e interpretação dos retornos são específicos dos novos bancos. Não existe dependência de classes Sicoob nem alteração dessa integração.

O escopo desta versão cobre registro, manutenção suportada pelo conector, consulta, cancelamento bancário, entrega e recebimentos. Sofisa, Pix e boleto híbrido, protesto, negativação, DDA, pagamentos a fornecedores e migração massiva de legado ficam fora da versão.

## 2 Ambiente e build reproduzível

O Gradle Wrapper é 8.2, executado com JDK 17. As fontes do produto geram bytecode Java 8. O Add-on Studio está fixado em 2.18.0. A compatibilidade mínima declarada é Sankhya 4.36, com compilação local contra bibliotecas 4.36b88; os patches efetivamente suportados ainda dependem de execução real.

Defina `SNK_LIB` para o diretório autorizado que contém as pastas `4_36` e `api_outros`, ou use `-PsnkLib`. Não versione bibliotecas proprietárias do ERP. `model/build.gradle` também aceita os JARs opcionais da pasta `lib` irmã do projeto; o responsável deve conferir se a distribuição de SDK usada exige algum deles. Uma máquina nova precisa recuperar as dependências Maven antes de usar `--offline`.

```sh
export JAVA_HOME="/caminho/do/jdk17"
export SNK_LIB="/caminho/do/sdk-sankhya"
./gradlew --no-daemon --console=plain :model:test gerarAddon
python3 scripts/check_project.py
node scripts/frontend.test.cjs
node --check vc/src/main/webapp/html5/zycb/zycb.js
```

Para executar somente os testes Java, use `./gradlew :model:test`. O relatório fica em `model/buildGradle/reports/tests/test/index.html`. O smoke test visual usa `node scripts/ui_smoke.cjs`, com Playwright disponível por `PLAYWRIGHT_MODULE` e navegador indicado por `CHROME_BIN`; os serviços são simulados, sem conexão ao ERP ou ao banco.

### Identidade do produto

A identidade original do add-on está preservada localmente em `.local/addon-identity.gradle`, com acesso restrito e fora do Git. O build carrega esse arquivo. Como alternativa, recebe `ZYCB_APP_KEY` e `ZYCB_PARCEIRO_NOME` do ambiente. `-PzycbIdentityFile` permite indicar outro arquivo autorizado. Não trocar a identidade para atualizar a solução já cadastrada no portal.

Uma nova máquina recebe a identidade pelo mantenedor, por canal seguro. Há um exemplo sem valores em `config/addon-identity.example.gradle`. AppKey, licença, client secrets, chave AES e certificados reais não devem aparecer em commits, documentos, capturas ou logs. O teste local não exige identidade real do produto; o empacotamento exige identidade autorizada. A licença cadastrada no portal não é recriada pelo código.

### Artefatos e exportação

O build gera `model/buildGradle/libs/zaya-api-boletos-model.jar`, `vc/buildGradle/libs/zaya-api-boletos-web.war` e `build/libs/zaya-api-boletos.exts`. O `.exts` é criptografado pelo exportador oficial. Sua geração não instala nem publica o add-on no portal.

`gerarAddon` depende do model, do web, dos testes e da validação do produto. A ordenação após `:clean` preserva o staging do exportador. `scripts/verify_staging.py` confere módulos, scripts de ambos os SGBDs e dicionário. O script corrige, de forma delimitada, a interpretação de acentuação do exportador 2.18; ao atualizar o plugin, reavaliar essa compatibilidade em vez de aplicar correções genéricas a todos os XMLs.

Fontes e XMLs de entrada usam UTF-8. O formato gerado pelo exportador possui convenções próprias. XML importável com raiz `metadata` precisa de `exportInfo` antes de `instances`, descrições e compatibilidade de encoding; não aplicar esse cabeçalho indiscriminadamente a descritores com outra raiz. `autoDDL=false` exige migrações explícitas.

## 3 Mapa do código e responsabilidades

| Local | Responsabilidade principal |
| --- | --- |
| `domain/Models.java` | Configuração, elegibilidade, título, cobrança, identidade, operação e recebimento |
| `application/BillingEngine.java` | Fila, reserva, processamento, instruções, recebimentos e reemissão |
| `application/ConfigurationService.java` | Revisão da configuração, diagnóstico, ativação e pausa |
| `application/RuntimeModule.java` | Composição dos adaptadores reais e chave global de execução |
| `application/InstructionValidator.java` | Campos permitidos e completude de cada instrução |
| `ports` | Contratos de registro, consulta, instrução, recebimento, documento, baixa e credenciais |
| `banks/itau` e `banks/santander` | Serialização e interpretação das APIs de cada banco |
| `banks/AuthenticatedClient.java` | OAuth, cache de token, correlação e restrição de URLs |
| `banks/ApacheTransport.java` | HTTPS com mTLS, verbos reais, timeouts e limite da resposta |
| `persistence` | SQL parametrizado, conexão, repositório, leases e persistência JSON |
| `sankhya` | Eventos JAPE, fronteiras transacionais, financeiro, PDF e mensageria |
| `security` | Autorização por empresa, cofre e resolução do certificado |
| `jobs` | Processamento, coleta e recuperação de elegibilidade |
| `services/BillingController.java` | Serviço autenticado consumido pela central HTML5 |
| `vc/src/main/webapp/html5/zycb` | Assistente, monitores e ações operacionais |
| `datadictionary` e `dbscripts` | Menus, consultas técnicas, entidades e migrações |

A composição em `RuntimeModule` usa `SankhyaDatabase`, `Repository`, `EncryptedCredentialStore`, um cliente HTTP autenticado compartilhado e dois conectores. `NativeFinancial` implementa as fronteiras financeiras e de documento. Para testes, essas dependências são substituídas por adaptadores controlados.

Os contratos de portas separados permitem evoluir capacidades de cada banco. `BankConnector` reúne essas capacidades para a orquestração atual. Um novo conector precisa ser registrado em `ConnectorRegistry` e na composição, receber validações de domínio e interface e ter seu perfil homologado; preencher apenas parâmetros não cria suporte a uma API desconhecida.

## 4 Fluxos e fronteiras transacionais

### Do documento ao registro

`NoteListener` e `FinancialListener` acionam `BillingEvents`. Quando o financeiro é elegível, `BillingEngine.enqueue` grava cobrança, numeração e operação na mesma transação do documento. `SankhyaDatabase.current` participa da conexão corrente e não confirma o faturamento. Se essa transação sofrer rollback, não deve restar intenção de registro.

A elegibilidade exige receita não provisória, não baixada, valor positivo, tipo de título permitido, empresa e conta correspondentes, endereço válido e ausência de identidade bancária preexistente. A regra fiscal é definida por TOP como `NFE`, `NFSE` ou `CONFIRMADA`. Financeiro manual e títulos anteriores ao corte exigem seleção explícita. A primeira ativação captura o NUFIN de corte; reativações não o avançam.

Depois do commit, `OperationsJob` chama `BillingEngine.process`. O worker adquire a operação e o lock da cobrança em transações curtas. Antes do POST de registro, grava `ZYCB_RESERVA` e `DHENVIO`; para outras mutações, grava `DHENVIO`. Só então chama o banco, fora dessas transações. O resultado é persistido em outra transação.

Registro concluído requer identidade confirmada, código de barras com 44 dígitos, linha digitável com 47 e banco correspondente. O financeiro recebe `NOSSONUM`, `LINHADIGITAVEL` e `CODIGOBARRA`. A entrega inicial é uma operação independente. Aceitação assíncrona não libera impressão e entrega como se o registro estivesse concluído.

### Estados da operação

| Estado | Significado e conduta |
| --- | --- |
| `PENDENTE` | Intenção disponível para aquisição; ainda não comprova envio |
| `PROCESSANDO` | Worker adquiriu a operação com token de lease |
| `AGUARDANDO` | Resultado pendente ou acompanhamento agendado |
| `INCERTO` | Pode existir efeito remoto; consultar antes de decidir qualquer repetição |
| `REJEITADO` | Recusa ou invalidação local/bancária identificada |
| `CONCLUIDO` | Resultado aceito como efetivado pelo fluxo específico |
| `EXCECAO` | Falha que exige análise; verificar `DHENVIO` antes de qualquer reprocessamento |

A cobrança tem estado próprio: `AGUARDANDO_REGISTRO`, `REGISTRADA`, `CANCELADA`, `PAGA` ou `DIVERGENTE`. Uma operação de entrega falha não significa falha do registro. Recebimentos divergentes são identificados em `ZYCB_REC`; não presumir que todo registro divergente altera automaticamente o estado da cobrança para `DIVERGENTE`.

`Repository.claim` respeita a sequência por cobrança e bloqueia sucessoras quando há operação anterior não resolvida. `finish` exige o token do worker proprietário. A recuperação de operações abandonadas ocorre após 15 minutos: sem `DHENVIO`, retorna a `PENDENTE`; com esse marcador, passa a `INCERTO`. Esse tempo não representa autorização para repetir POST.

O worker consulta automaticamente operações em `AGUARDANDO` já enviadas. Uma operação em `INCERTO` exige a ação `consultarResultado`, que a coloca em acompanhamento sem novo envio mutável. `reprocessar` é restrito a `EXCECAO` com `DHENVIO` nulo. Não alterar status, reserva ou marcador de envio diretamente no banco para forçar uma inclusão.

### Alterações e cancelamento

Vencimento e valor alterados no financeiro registrado geram instruções distintas; demais alterações passam pela ação e pelo validador. Cada instrução conserva seu resultado. Sem protocolo ou comprovação específica, uma consulta de existência do boleto não confirma uma alteração. O fluxo não oferece atomicidade remota entre várias instruções.

Cancelamento local antes da aquisição de um registro pendente pode rejeitar a intenção sem chamada bancária. Se o registro já pode ter sido enviado, exige confirmação do cancelamento no banco. Reemissão só é permitida após `CANCELADA`, sem operação anterior em aberto e sobre a geração mais recente. Gera outro nosso número e preserva o histórico. A reserva anterior é substituída na transação da nova geração.

A proteção global por NUFIN é interna ao produto. CNAB, Boleto Rápido e outro motor que não usem essa reserva precisam ser desabilitados ou segregados para a mesma conta e títulos.

### Recebimento e baixa

`ReceiptsJob` consulta cobranças conhecidas, respeitando o cursor e intervalo individual. O conector retorna pagamentos normalizados. `BillingEngine.receive` exige correspondência da cobrança e deduplica pela chave derivada do evento, data, valores e indicadores de situação. A chave não depende apenas de um ID externo isolado.

A baixa automática exige pagamento definitivo, positivo, não parcial e não estornado, principal compatível com o snapshot e cobrança ainda não paga nem cancelada. O principal é total menos juros e multa mais desconto e abatimento. Eventos provisórios, composição incompatível, estorno ou repetição com valores diferentes seguem para análise.

`NativeFinancial.settle` usa o usuário técnico, `BaixaHelper`, a TOP, a conta e o lançamento configurados. A baixa e o registro do evento aplicado participam da mesma transação. O movimento é criado como não conciliado. Conferência do crédito e conciliação permanecem no processo financeiro nativo. Não automatizar estorno financeiro a partir de um status bancário genérico.

### PDF e e-mail

`NativeFinancial.pdf` usa o modelo da conta com reimpressão, geração de número e registro bancário desabilitados. `deliver` grava anexo e fila de mensagem nativa. `ENTREGAR` concluída significa enfileiramento no Sankhya, não confirmação de recebimento do e-mail pelo cliente. Para falha posterior de SMTP, consultar a mensageria nativa. Segunda via não deve emitir outro boleto.

## 5 Persistência e agendamentos

| Tabela | Finalidade |
| --- | --- |
| `ZYCB_CFG` | Configuração, revisão, diagnóstico e controles de execução |
| `ZYCB_COB` | Cobrança por título e geração, identidade e snapshots |
| `ZYCB_OPER` | Fila, sequência, tentativas, protocolo, marcador de envio e lease |
| `ZYCB_RESERVA` | Reserva de inclusão por NUFIN |
| `ZYCB_NUM` | Próxima numeração durável por configuração |
| `ZYCB_CRED` | Credenciais cifradas e revisão do cofre |
| `ZYCB_REC` | Eventos de recebimento deduplicados e seu resultado |
| `ZYCB_CURSOR` | Agenda e último sucesso da consulta por cobrança |
| `ZYCB_SCAN` | Cursor de recuperação de financeiros elegíveis |
| `ZYCB_AUDIT` | Ações e ocorrências operacionais sem segredos |
| `ZYCB_ACESSO` | Perfil de usuário por empresa |

V1 cria as estruturas e restrições; V2 cria índices operacionais. Consultar `dbscripts/V1.xml` e `V2.xml` para tipos e chaves exatos. Campos JSON preservam contexto privado; status, locks e cursores relacionais são a fonte operacional de aquisição. Não assumir que `JSONDATA` isolado contém o último valor de todas as colunas de controle: `Repository.operation` recompõe o objeto com essas colunas.

A configuração usa revisão otimista e impede mudança da identidade de empresa, conta, banco, ambiente e contrato. Cobranças guardam snapshots de regras financeiras, destinatário e entrega. Alterar uma configuração não reescreve automaticamente cobranças existentes. Já a origem do certificado e as credenciais são obtidas da configuração atual ao carregar o cofre, permitindo rotação sem mudar a identidade da cobrança. Antes de alterar essa política, definir quais mudanças devem alcançar cobranças antigas e testar o histórico.

`OperationsJob` e `ReceiptsJob` executam a cada minuto. O envio usa lote por configuração e rotação entre contas; o recebimento respeita `collectMinutes`. `RecoveryJob` executa a cada cinco minutos e procura financeiros posteriores ao corte, percorrendo também os inaptos e reiniciando o ciclo. Os jobs verificam `ZYCBHABIL`; com `N`, não processam. Com `S`, ainda dependem dos controles por conta.

Salvar a configuração a deixa inativa e interrompe novas emissões, exigindo diagnóstico e ativação. A pausa operacional altera apenas `sendEnabled`, preservando acompanhamento permitido e coleta. A coleta tem controle independente e pode continuar durante uma reconfiguração. Verificar esses controles antes de tratar uma fila parada como defeito.

## 6 APIs bancárias e segurança

### Contratos por banco

Itaú usa o OpenAPI fornecido, versionado em `docs/contracts/itau-2.75.150.openapi.json`. O cliente distingue efetivação de simulação e preserva acompanhamento de respostas assíncronas. A URL de produção vem do contrato; o ambiente de homologação é recusado enquanto não houver perfil documentado. A instrução bancária de baixa invalida a cobrança; não é liquidação financeira.

Validar no Itaú a identidade composta das instruções, envelope e paginação da consulta, representação dos percentuais, pagamentos e confirmação de cada instrução. Os fixtures locais não são amostras autenticadas do cliente.

Santander usa o perfil de Cobrança com convênio e workspace. Os campos e estados implementados precisam ser confrontados com o OpenAPI vigente da aplicação e as permissões reais. Status ativo, baixa ou aviso provisório não são prova genérica de pagamento definitivo. Não copiar para ele a interpretação textual usada no Itaú.

Os endpoints e verbos ficam no código do conector. O operador não cadastra URLs. `AuthenticatedClient` restringe acompanhamento a HTTPS, ao mesmo host e ao contexto bancário permitido. `ApacheTransport` executa PATCH real, não segue redirects e não repete requisições automaticamente. Os timeouts são 15 segundos para conexão/aquisição e 30 para resposta; o corpo de resposta tem limite de 4 MiB. Não habilitar retries de POST para contornar instabilidade.

### Certificado e cofre

Novas configurações usam `SANKHYA`. `SankhyaCertificateProvider` resolve o CNPJ da empresa e chama `AssinadorNFe`, como no Sicoob. `TlsMaterial` valida material, vigência, uso permitido e correspondência da chave; o provedor nativo confere CNPJ e cadeia. A chave privada segue diretamente para um `X509ExtendedKeyManager` em memória, sem exportação para P12 ou arquivo.

`UPLOAD` exige seleção e gravação explícitas. O usuário envia P12/PFX e senha pelo serviço autenticado; o material fica no cofre cifrado. Configurações legadas sem o novo campo conservam UPLOAD. Não existe fallback entre origens. Na origem SANKHYA, material de upload não é utilizado e uma gravação nova de credenciais rejeita arquivos e senhas de certificado.

O certificado nativo é resolvido antes de cada chamada autenticada; o fingerprint participa do cache OAuth e do diagnóstico. Renovação demanda novo token e novo diagnóstico para ativação. A detecção depende do certificado entregue pelo cache do próprio Sankhya; o add-on não reinicializa esse cache. A mesma chamada mantém o material entre obtenção de token e operação.

O certificado fiscal só funciona no banco se estiver autorizado para aplicação, contrato, ambiente e conta. Se for exigido certificado específico, selecionar UPLOAD. A implementação não comprova revogação nem autorização bancária apenas com a leitura do certificado.

`EncryptedCredentialStore` usa AES-GCM, IV aleatório e AAD pelo ID da configuração. A chave AES vem de `java:comp/env/zycb/EncryptionKey`, como `javax.crypto.SecretKey`, e não da base ou da AppKey. O binding ainda precisa ser comprovado no Cloud. Rotação dessa chave exige recifrar os dados com a chave anterior disponível; não há migração automática implementada. Client ID e client secret continuam necessários nas duas origens.

### Serviços e permissões

A central chama `ZycbBillingServiceSP` pelo `service.sbr` da sessão Sankhya. Não há endpoint público de webhook na versão. `BillingController` usa `NotSupported`; o processamento controla suas próprias fronteiras transacionais. `Authorization` valida usuário e empresa do objeto carregado, com perfis ADMIN, OPERADOR e CONSULTA; SUP inicia a concessão de acesso.

Configuração, credenciais, diagnóstico e ativação são operações administrativas. Ações sobre cobranças exigem acesso à empresa correspondente. Consultas Dynaform são técnicas, somente leitura e devem ser restritas por permissões nativas; não substituem o isolamento por empresa da central HTML5. O serviço de monitor retorna até 200 registros por consulta, sem paginação operacional nesta versão.

## 7 Roteiro de validação do novo mantenedor

Primeiro reproduza o build e os testes em um clone limpo, sem bibliotecas ou arquivos gerados herdados de outra máquina. Confira que o pacote contém o model, web, migrações e metadados esperados, sem exemplos do template, certificados de teste ou identidade confidencial versionada.

A base local registrada contém 38 testes Java: 15 de orquestração, 12 de conectores, 2 de criptografia e 9 de certificados. H2 nos modos Oracle e MSSQLServer testa parte da portabilidade, sem validar transações e funções dos SGBDs físicos. O teste visual usa RPC simulado. Não apresentar esses resultados como aceite de integração com o banco.

Em seguida instale em homologação com jobs desligados. Comprove migrações em instalação nova e atualização; conexão corrente dos eventos; rollback conjunto de documento e intenção; permissões; cofre entre nós e reinícios; acesso a AssinadorNFe e comportamento de renovação. Valide ambos os SGBDs físicos e a versão Sankhya do destino.

Com o banco, valide autenticação e consulta sem emissão. Depois execute uma massa controlada para registro, resposta pendente, cada instrução, cancelamento e pagamento definitivo. Registre versão do contrato, ambiente, conta mascarada, correlação, esperado, observado e evidência saneada. Não incluir tokens, P12, senha, payload pessoal completo ou chave do cofre.

Teste timeout após efeito remoto, interrupção depois da reserva, concorrência e reinício. A aceitação é uma cobrança registrada, sem nova inclusão automática. Para instruções parcialmente concluídas, conferir cada operação. Para pagamentos repetidos, demonstrar uma única baixa; para parcial, excesso sem composição, provisório e estorno, demonstrar a divergência e a análise financeira.

Finalize com PDF sem novo registro, e-mail enfileirado, falha de SMTP, segunda via, corte de ativação, pausa de emissão e coleta independente. O aceite deve envolver o responsável financeiro, que confere TOP, composição, data de baixa e conciliação do crédito.

Somente após essas evidências, alterar o manifesto de homologação do banco em uma release revisada, aprovar a conta, habilitar `ZYCBHABIL=S` e ativar um piloto pequeno. Não desligar bloqueios para apenas demonstrar a tela.

## 8 Diagnóstico e correção de incidentes

| Sintoma | Onde verificar | Conduta inicial |
| --- | --- | --- |
| Não existe operação | Parâmetro global, conta, corte, elegibilidade fiscal e listeners | Identificar a regra; financeiro manual usa seleção explícita |
| Operação não avança | `ATIVO`, `ENVIO`, `PROXEXEC`, sequência e lock | Resolver a anterior; não apagar lease ou reserva por tentativa |
| Registro incerto | `DHENVIO`, protocolo e consulta no banco | Usar Consultar resultado; não gerar outro POST ou CNAB |
| Reprocessar recusado | Estado e `DHENVIO` | A ação só atende falha sem envio anterior |
| Ativação bloqueada | Cofre, diagnóstico, perfil e aprovação da conta | Corrigir a causa; não contornar o manifesto |
| Certificado indisponível | Origem salva, empresa/CNPJ, validade, chave e vínculo bancário | Verificar a origem selecionada sem trocar automaticamente |
| Pagamento não baixado | Status definitivo, composição, cobrança e baixa preexistente | Conferência financeira e evento DIVERGENTE |
| E-mail não chega | `ENTREGAR`, fila nativa, anexo e SMTP | Separar falha de PDF, enfileiramento e entrega efetiva |
| Mudança não alcançou título antigo | Snapshot da cobrança e configuração atual | Conferir a política; não reescrever histórico em massa |

Para suporte, recolha ID da configuração, NUFIN, ID da cobrança e operação, geração, estado, tentativas e horário. Use usuário autorizado e ambiente de homologação para reproduzir. Snapshots e `JSONDATA` podem conter dados do pagador: não os anexar integralmente a issue ou log. As mensagens genéricas atuais preservam segredos, mas limitam a causa técnica; evolução recomendada é telemetria com códigos, correlação e saneamento centralizado.

Consultas iniciais de suporte são somente leitura e usam identificador validado, vinculado à empresa autorizada. Não consultar ou exportar `ZYCB_CRED.CIFRADO` como diagnóstico. A intervenção manual em banco deve ter análise, backup, evidência bancária e revisão do mantenedor; não é o procedimento padrão de recuperação.

## 9 Como evoluir sem quebrar a esteira

Para corrigir um retorno bancário, capture um exemplo real saneado, confirme sua versão e acrescente um teste que reproduza o erro antes de mudar o parser. A mudança fica no conector; promoção a liquidação definitiva exige evidência financeira. Não transformar envelope desconhecido em sucesso por heurística comum aos dois bancos.

Para incluir um banco, implementar as portas suportadas e seus contratos, adicionar configuração e validação específicas, registrar o conector e definir capacidades claras. Se for preciso novo estado interno, revisar persistência, monitores, reprocessamento e testes de recuperação. Um banco que não confirme determinada instrução não pode herdar confirmação por mera existência da cobrança.

Para alterar a base, criar V3 ou superior com estratégia para Oracle e SQL Server e atualização preservando dados. Não reescrever V1/V2 já distribuídas. Testar instalação e upgrade, índices e concorrência. Novo campo em JSON precisa de política explícita para leitura de versões anteriores; `certificateSource` é o exemplo: ausência significa UPLOAD para o legado, enquanto a criação nova escolhe SANKHYA.

Para mudar autenticação, certificado ou armazenamento seguro, preservar isolamento por configuração e origem explícita. Renovação, expiração, ausência de chave e troca de cofre precisam de testes. Não desabilitar truststore, hostname verification ou verificação de certificado para fazer a conexão passar.

### Melhorias prioritárias identificadas

Antes de produção, fechar contrato Santander e particularidades Itaú; comprovar cofre Cloud, transações JAPE/JTA, baixa e impressão nativas; executar matriz física Oracle/SQL Server. Esses são bloqueios conhecidos, não ajustes cosméticos.

Na evolução operacional, priorizar paginação dos monitores e validação da paginação bancária, métricas de backlog/latência/falhas e logs saneados, política de retenção de eventos e dados pessoais, e tratamento de rate limit com reagendamento seguro. Consultas hoje são por cobrança; medir volume histórico e custo antes de adotar lotes ou consultas incrementais, mantendo sobreposição e deduplicação.

Revisar também política de snapshot para mudanças de usuário técnico e entrega, duração de leases para tarefas demoradas, limite de parcelas consultadas por documento e mecanismo de rotação da chave AES. As consultas de notas atualmente limitam a seleção a 1000 financeiros. A recuperação depende de NUFIN crescente e janela de ativação sem transações de inclusão em andamento; mudanças nessa hipótese exigem outra estratégia de corte e testes.

Webhook é capacidade futura complementar, com autenticação, deduplicação, replay e correlação próprios. Não substituir a consulta confiável antes de homologar o canal.

## 10 Colaboração no GitHub e entrega de correções

O repositório é privado e contém apenas este produto. O administrador concede acesso ao usuário ou time correto na área de colaboradores do repositório, com permissão de escrita para desenvolver. A configuração de acesso efetivo depende das políticas da organização e da aceitação do convite; ter o link não concede acesso. A identidade do desenvolvedor deve ser confirmada antes do convite. O acesso ao GitHub não concede acesso a bancos, portal Sankhya ou ambiente do cliente.

Trabalhe em branch de mudança, faça commits pequenos e abra pull request para a branch principal. Descreva o problema, o comportamento final, a evidência de teste e o risco financeiro. Revise alterações de estados, reserva, baixa, credenciais e permissões com outro mantenedor antes do merge. Proteção de branch e aprovação obrigatória são recomendações de governança, não configurações presumidas como já aplicadas.

Antes de cada entrega, executar as verificações relevantes, atualizar o changelog e este manual quando mudar o comportamento, gerar o pacote com a identidade autorizada e registrar versão e evidências. Artefatos compilados ficam fora do Git e devem ter distribuição controlada. O clone usa o Wrapper e não recebe `.local`, SDK, certificados reais, caches ou arquivos gerados.

Uma correção de software não desfaz efeitos bancários anteriores. Para contingência, pausar novas emissões; avaliar a coleta separadamente; usar `ZYCBHABIL=N` para interrupção geral, preservando tabelas e histórico. Não desinstalar o add-on ou liberar outro motor para uma cobrança incerta como mecanismo de rollback.

## Referências no projeto

`README.md` apresenta a entrada do produto. `docs/ARQUITETURA.md` registra a visão de componentes. `docs/CONTRATOS_BANCARIOS.md` descreve as referências e lacunas por banco. `docs/CERTIFICADOS.md` detalha origens, renovação e homologação. `docs/INSTALACAO.md` contém instalação e contingência. `docs/HOMOLOGACAO.md` reúne evidências locais e matriz pendente. `CONTRIBUTING.md` orienta a colaboração. O código e os testes citados neste manual são a base verificável da implementação.
