# Homologação temporária — 09/10/2026

O usuário autorizou explicitamente Cloudflare Quick Tunnel e JAR com credencial individual extraível por uma única sessão de 60 minutos. A revisão automática inicialmente rejeitou a ação por falta dessa autorização específica; nenhuma tentativa de contornar a rejeição foi feita.

## Escopo

- Receptor normal (sem profile dev), loopback, usuário aleatório exclusivo, segredo aleatório de 256 bits, sem usuários demo, IA desligada e gateway desabilitado.
- Binding exclusivo com environment=fixture: resultados sempre simulados, inclusive quando o botão envia contexto de uma nota selecionada. Não é homologação de regra de estoque.
- Túnel HTTPS temporário pelo conector oficial cloudflared. O terceiro termina TLS; não enviar logs brutos nem dados reais sensíveis nesta sessão.
- JAR temporário inclui a identidade exclusiva e a credencial, sob ACL Windows restrita em config/private/session-*/. Não compartilhar publicamente. Não é armazenamento protegido para produção.
- Provedor verifica expiração antes de enviar. Um processo supervisor encerra receptor e túnel na expiração para revogar a credencial no servidor. A sessão depende da máquina ligada e da continuidade dos processos; reinício pode encerrar o teste antes do prazo.
- O JAR comum em build/libs é reconstruído sem recursos de sessão. Apenas o JAR privado ZAYA-NEXO-HOMOLOGACAO.jar está provisionado.

## Execução e encerramento

`scripts/Start-NexoTemporarySession.ps1 -Minutes 60` prepara receptor, túnel, verifica autenticação e envio sintético pelo HTTPS e compila o pacote temporário. Requer PowerShell 7, Java e o conector oficial em target/tools/cloudflared.exe. Não inicia serviço Windows, não abre janela visível e não cadastra nada no ERP.

`scripts/Stop-NexoTemporarySession.ps1 -SessionPath <caminho-do-session.json>` encerra antecipadamente os processos cujos PID e horário de criação correspondem à sessão. Nenhum segredo é exibido pelos scripts. Arquivos privados permanecem para inspeção; não reutilizar credenciais ou distribuir JAR expirado.

Falha de POST não causa reenvio automático: a entrega pode ter sido persistida antes do timeout. Só os GETs de prontidão são repetidos.

## Instalação manual no ERP para o teste

Substituir o módulo pelo JAR privado da sessão, manter a classe br.com.zaya.engineering.nexobutton.SendIncidentAction, a tela com NUNOTA e o parâmetro de relato MENSAGEM_ERRO. Não cadastrar parâmetros NEXO no Sistema. Selecionar uma nota de homologação e informar um relato sintético sem dados sensíveis. O retorno deve mostrar Homologação e o protocolo. Não houve cadastro automático ou alteração de banco.

## Código de referência fornecido

O usuário colou BtnGetLog e BtnRunQuery. BtnGetLog usa org.jboss.boot.log.file, lê o arquivo inteiro, reverte linhas e devolve até 1.000 via ServiceContext. O catch original pode expor caminho e conteúdo. O Nexo aproveita apenas a identificação do log pelo runtime; não incorporou código da biblioteca externa.

LogSignals lê no máximo 64 KiB do final e considera até 1.000 linhas, com fechamento de arquivo. Envia apenas contagens ERROR/WARN e até cinco tipos de exceções de namespaces padrão. Não envia linhas, mensagens, caminho ou corpos de requisição. Ausência/permissão negada vira evidência de indisponibilidade. Essas contagens não vinculam erro à nota selecionada e não demonstram causalidade. Encoding esperado UTF-8; rotação ou encoding diferente pode tornar sinais incompletos.

BtnRunQuery não foi incorporado: aceita SQL livre e usa execute(), permitindo também escrita. O Nexo não recebe SQL e não executa consultas arbitrárias. Itens/estoque exigem consultas fixas homologadas em etapa posterior.

## Limites

Compilação local usa SDK 4.35b99; runtime alvo 4.36b159 ainda precisa de teste. Prontidão pública e teste sintético não comprovam saída HTTPS do Sankhya ou compatibilidade do botão. Provisionamento permanente de identidade protegida continua pendente.


## Resultado desta sessão

Receptor iniciou em profile prod; envio sintético autenticado local e leitura do diagnóstico passaram (INCONCLUSIVA, simulated=true). Túnel estabeleceu conexão com Cloudflare, mas o hostname temporário não resolveu nesta máquina. Foi usado AllowUnverifiedHttps: o JAR privado está provisionado para teste de conectividade, com HTTPSValidated=false. Supervisor de expiração foi verificado ativo após correção de conversão cultural de timestamps; Test-NexoSessionStop.ps1 passou. Encerramento programado: 09/10/2026 16:27:14 America/Sao_Paulo. Artefato comum inspecionado sem recurso de credencial. Não houve instalação ou execução do botão no ERP.


## Correção V1.4

Erro observado no ERP ao interpretar a expiração 2026-10-09T19:27:14.3189759+00:00 com Instant.parse. Corrigido para OffsetDateTime.parse com ISO_OFFSET_DATE_TIME e conversão para Instant. Teste de regressão cobre o timestamp exato, offset -03:00 equivalente e Z. JAR privado V1.4 preserva endpoint, identidade, credencial e expiração originais. Nenhuma nova sessão ou extensão do prazo foi criada.
