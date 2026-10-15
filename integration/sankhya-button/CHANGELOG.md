# Changelog

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
