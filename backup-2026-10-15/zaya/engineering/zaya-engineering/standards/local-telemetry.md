# Telemetria local de eficiência

## Recomendada

O script scripts/Write-ZayaTelemetry.ps1 registra métricas técnicas somente
quando chamado explicitamente. A saída fica em .zaya/telemetry/, ignorada pelo
Git.

Campos permitidos:

- tipo da tarefa;
- quantidade de arquivos pesquisados e abertos;
- documentos da biblioteca carregados;
- consultas ao dicionário;
- status de build e validação.

É proibido registrar código, SQL integral, dados de cliente, segredos, URLs
internas ou prompts completos.
