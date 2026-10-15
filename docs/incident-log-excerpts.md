# Trechos de log vinculados ao incidente — V1.6

## Coleta no Sankhya

IncidentLogCollector lê somente o arquivo indicado pelo runtime em org.jboss.boot.log.file. Não recebe caminho, SQL, regex ou comando do usuário/receptor. Consulta até o último 1 MiB, considera até 5.000 linhas e procura termos extraídos do relato (ORA, gatilho e produto), além do número da nota selecionada.

Prioriza as ocorrências mais específicas e recentes e seleciona até três janelas (duas linhas anteriores e seis posteriores). Quando há produto/gatilho específico no relato, não retorna apenas um ORA genérico sem algum identificador específico. O conteúdo enviado tem no máximo 12.000 caracteres, linhas limitadas e flag de truncamento. Encoding esperado UTF-8; rotação, acesso negado e arquivo diferente podem impedir a localização.

O coletor mantém porções diagnósticas (ORA, gatilho, produto e stack frames). Linhas adjacentes não diagnósticas são omitidas; mensagens de Caused by são reduzidas ao tipo da exceção. Linhas com indicadores de segredo, corpo JSON/requisição ou SQL são omitidas. Emails, identificadores numéricos longos/CPF/CNPJ, URLs e caminhos reconhecidos são substituídos. O receptor aplica filtragem adicional.

**A filtragem é heurística: não garante anonimização completa de texto livre.** Acesso restrito e retenção continuam necessários. O conteúdo filtrado trafega pelo endpoint da sessão, inclusive pelo terceiro Cloudflare na homologação temporária autorizada. Não há envio do arquivo completo ou uso desse conteúdo pelo modelo de IA.

## Status e correlação

- FOUND: existe correspondência textual na janela consultada e um trecho filtrado acompanha o pacote.
- NOT_FOUND: nenhum identificador correspondente foi localizado na janela; não demonstra que o erro não ocorreu.
- UNAVAILABLE: propriedade ausente, arquivo inacessível ou leitura falhou; detalhes internos não são enviados.

correlation permanece TEXT_MATCH_UNVERIFIED. Sem identificação de requisição/horário/transação não é possível atribuir a ocorrência à nota ou provar causa. Nenhum status do log altera automaticamente o estado do diagnóstico.

## Arquivo no receptor

EvidencePackage adiciona logExcerpt opcional. Relatórios antigos e coletores anteriores permanecem compatíveis. O JSON da investigação guarda os metadados e o conteúdo filtrado. Quando status=FOUND, o receptor grava também `<protocolo>.log` junto a `<protocolo>.json`, em data/<tenant>/<environment> (dentro da pasta privada na sessão temporária).

O arquivo é publicado por `GET /investigations/{id}/log`, com autenticação e mesmo isolamento por cliente/ambiente do diagnóstico. Responde text/plain UTF-8 como attachment, com nosniff. Sem trecho retorna 404; o JSON mantém o status. O endpoint não recebe caminhos e o nome é derivado do UUID da investigação.

Persistência usa temporários e renomeações atômicas por arquivo; falha normal limpa anexos não confirmados. Não há transação atômica entre os dois arquivos: interrupção abrupta pode deixar .log órfão, que não é servido sem o JSON. Conteúdo de log não é enviado para IA nem usado como prova de causa raiz.

## Como testar

Instalar o JAR privado V1.6 da sessão atual, manter SendIncidentAction e MENSAGEM_ERRO e reenviar o incidente. O retorno informa trecho encontrado, não localizado ou indisponível. Protocolos anteriores não ganham trecho retroativamente: a coleta precisa ocorrer no ERP.

Sessão atual preserva endpoint/credencial e expiração de 09/10/2026 17:27:06 (Brasília). IA continua desligada. Rollback: reinstalar V1.5 da mesma sessão enquanto válida; o receptor aceita pacotes sem logExcerpt, sem alteração de banco.

## Validação

Build Gradle passou com testes de matching, stack context, redaction, ausência de correspondência e limite de 1 MiB, além dos testes anteriores. Maven package passou com 18 testes, incluindo persistência/download, filtragem adicional, isolamento entre clientes, autenticação, ausência de anexo e rejeição de correlação falsa. O receptor atualizado foi reiniciado na mesma sessão. Teste sintético local confirmou arquivo .log criado, download autenticado e remoção dos dados sensíveis sintéticos. O trecho real ainda depende do reenvio pelo botão atualizado.
