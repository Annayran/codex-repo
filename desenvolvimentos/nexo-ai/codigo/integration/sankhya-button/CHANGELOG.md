# Changelog

## V1.16 — 2026-10-16

- Agrupa frames stderr consecutivos com prefixo WildFly da mesma thread/nível.
- Descarta frames órfãos e prioriza mensagem, Caused by, Suppressed e ORA/gatilhos dentro do limite.
- Replay filtrado do ZIP real produziu dois incidentes com família, quantidade e referência de gatilho.

## V1.15 — 2026-10-16

- Ingresso oferece UUID de confirmação também em X-Nexo-Issue-Id, após validar o recibo do backend.
- Java 8 aceita o cabeçalho somente com marca do receptor e UUID válido; JSON permanece como alternativa.
- Central permite relato manual autenticado, explicitamente separado de captura ao vivo.

## V1.14 — 2026-10-16

- Recibos sem issueId distinguem marcador do receptor e formatos conhecidos, sem divulgar valores ou conteúdo.
- Ingresso temporário identifica respostas e registra somente chegada autenticada/status do backend.

## V1.13 — 2026-10-16

- Log real confirmou erros de família/quantidade em ERROR; avisos WARN aparecem próximos e motivaram cobertura adicional, sem atribuir a eles a falha de envio.
- Modo Skala aceita WARN apenas quando existe sinal relevante e classe de exceção; avisos comuns continuam excluídos.
- Corrigido reconhecimento da classe de exceção para não confundir método processError com tipo de falha.

## V1.12 — 2026-10-16

- Diagnóstico identifica a etapa da falha: configuração, serialização, conexão, envio, resposta HTTP ou recibo.
- Limites de payload/recibo e ausência de issueId têm códigos explícitos, sem expor dados recebidos.

## V1.11 — 2026-10-16

- Retorno `falhaEnvio`: DNS, TLS, TIMEOUT, CONEXAO, HTTP_<status> ou TRANSPORTE_OU_RESPOSTA.
- Mensagens de exceção, endpoints e credenciais não aparecem no diagnóstico; fila e checkpoint preservados.

## V1.10 — 2026-10-16

- Modo Skala no agendamento: sinais de família/quantidade/particionamento ou stack da personalização.
- Leitura limitada a 2 MiB por ciclo nesse modo; atraso em bytes e contador acumulado de registros filtrados.
- Categoria PRODUCT_FAMILY_MISSING e análise de valores reportados e cadeia de triggers.
- Fila previamente persistida é preservada. O foco não comprova correlação com a execução Skala.

## V1.9 — 2026-10-16

- Categoria explícita para estoque insuficiente no monitor Java 8.
- Central com design system local, dossiê estruturado e revisão opcional por IA local.
- Diagnósticos distinguem sinal reportado, hipótese, evidência ausente e orientação condicional.

## V1.8 — 2026-10-15

- Monitor nativo de ação agendada com leitura incremental de ERROR/FATAL, checkpoint e fila persistente.
- Envio HTTPS autenticado, deduplicação, trechos filtrados e tratamento limitado de rotação.
- Receptor temporário restrito a eventos preserva a central local de OS.
- JAR comum sem credenciais; instalação no ERP e sessão pública exigem homologação.

## V1.6 — 2026-10-09

- Coleta limitada de trechos correspondentes ao relato no log, com contexto de stack e filtragem.
- Status encontrado/não encontrado/indisponível; correspondência textual sem causalidade presumida.
- Pacote adiciona logExcerpt opcional, persistido e servido pelo receptor como anexo autenticado.
- Mesma sessão e expiração, sem mudança de banco ou ativação de IA.

## V1.5 — 2026-10-09

- Coleta SELECT fixa de itens/produto e linhas de estoque, com NUNOTA vinculada, limites e timeout JDBC.
- Produto ativo, saldo físico e reservado separados; nenhuma disponibilidade calculada.
- Build provisionado mantém intermediários e JAR na pasta privada da sessão.
- Mesmo endpoint/credencial/expiração; execução SQL no cliente ainda exige homologação.

## V1.4 — 2026-10-09

- Corrige leitura de expiração com offset explícito e sete casas decimais no runtime Java 8, usando ISO_OFFSET_DATE_TIME.
- Regressão cobre o timestamp exato reportado, Z e offset -03:00; prazo e credencial da sessão preservados.

## V1.3 — 2026-10-09

- Provedor de homologação com credencial individual e expiração, incluído apenas em build provisionado.
- Coleta limitada de sinais do log por org.jboss.boot.log.file, sem linhas brutas, mensagens ou caminhos.
- Scripts de receptor/túnel temporário e encerramento por sessão, sem parâmetros NEXO no ERP.
- Testes executáveis de coleta limitada e rejeição de credencial ausente/expirada.

## V1.2 — 2026-10-09

- Remove leitura de parâmetros Sankhya e variáveis de ambiente da ação.
- Usa exclusivamente o contrato de provedor de implantação; ausência informa instalação pendente.
- Provedor concreto, receptor HTTPS e coleta de logs continuam pendentes; não é uma instalação pronta para envio.

## V1.1 — 2026-10-06

- Leitura dos parâmetros administrativos não secretos pela API getParametroSistema do SDK.
- Credencial permanece em provedor protegido ou backend, com mensagem explícita quando ausente.
- Artefato ZAYA-NEXO-V1.1.jar; SDK e Java 8 preservados.

## V1.0 — 2026-10-06

- Botão Rotina Java envia nota e mensagem de erro ao receptor central por HTTPS.
- Configuração protegida extensível por ServiceLoader; transporte limitado, sem redirects.
- Build Gradle corporativo, bytecode Java 8, dependências Sankhya compileOnly.
- Artefato: ZAYA-NEXO-V1.0.jar. Compilado com SDK local 4.35b99; alvo informado OM 4.36b159/Core 5.21.4 ainda exige homologação.
- Coleta de estoque/configurações não incluída no coletor mínimo.

## 1.7 — 15/10/2026

Emissor opcional Java 8 para falhas de personalizações, com classificação local sem mensagem/stack brutos, transporte HTTPS via configuração protegida, identidade temporária expirada rejeitada e checks de captura. Artefato normal permanece sem credenciais. Captura preparada nas duas ações da Skala; implantação e transporte real pendentes de homologação.
