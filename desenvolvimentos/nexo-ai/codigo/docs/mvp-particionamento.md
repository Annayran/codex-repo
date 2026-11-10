# MVP de investigação do particionamento — 16/10/2026

## Alternativa local: relato manual

A central oferece "Analisar erro manualmente": operador autenticado cola até 2.000 caracteres, cria OS e abre a análise local/IA. POST `/monitoring/manual` recebe UUID, instante do relato e mensagem; classifica no servidor, filtra o texto e mantém escopo e deduplicação. O componente é `skala.relato.manual`, versão não verificada e origem HISTORICAL: nunca soma captura ao vivo. O instante representa envio manual, não o instante comprovado do erro. A interface identifica a fonte e não chama o relato de trecho extraído do log.

Isso permite analisar mensagens fornecidas pelo usuário sem depender do transporte Java. Não constitui importador de ZIP nem resolve o recibo do coletor. Os registros do ingresso confirmaram POSTs autenticados com backend HTTP 200; uma reprodução de evento histórico retornou issueId/duplicate/severity/workOrderId pela entrada local. A causa do recibo sem issueId observado na JVM ERP permanece aberta.

## Ajuste V1.10

O piloto passou a filtrar ruído no agendamento e informar atraso em bytes. A central reconhece PRODUCT_FAMILY_MISSING e orienta a conferir a associação realmente exigida pela personalização, sem presumir um campo padrão. DELIVERED_QTY destaca valores entregue/negociada presentes no texto e as referências a TRG_UPT_TGFITE e TRG_INC_TGFVAR; não conclui como a divergência foi produzida.

Os dois formatos enviados pelo usuário foram incorporados aos testes. Isso não registra os relatos manualmente como ocorrências ao vivo nem prova que aparecem no log da instalação. A validação ponta a ponta continua dependendo de nova captura no ERP.

## Entrega

Central local em `/issues.html`, com design system próprio, ícones SVG locais, layout responsivo, busca na página, indicadores, dossiê do incidente, trecho de log, próximos passos e triagem da OS. Credenciais ficam somente nos campos da sessão, sem localStorage. Valores recebidos são renderizados como texto, nunca HTML.

`GET /monitoring/issues/{id}/analysis` entrega orientação imediata por playbooks. `POST /monitoring/issues/{id}/analysis/review` solicita complemento ao modelo configurado, com validação estrutural de referências, estado e verificações de leitura. Ambos preservam o isolamento de escopo da OS. A revisão não modifica o incidente, severidade, ERP ou causa determinística.

As orientações cobrem quantidade entregue, produto inativo, ausência de particionamento ativo, combinação global e estoque insuficiente. São procedimentos condicionais, não regras homologadas da Skala. Sem categoria reconhecida, a análise é inconclusiva e indica a coleta necessária.

IA local: iniciar com `scripts/Start-NexoDesk.ps1 -EnableLocalAi`. Se a central já estiver rodando, encerrá-la com `scripts/Stop-NexoDesk.ps1` antes de mudar a configuração. Usa o Ollama/modelo qwen2.5:3b já disponível. Não baixa modelos ou atualiza dependências. Gateway real permanece desativado e identidades de demonstração não são habilitadas.

O modelo recebe somente síntese curada do sinal e roteiro de consulta; não recebe trecho bruto, nomes de componentes, IDs de nota ou dados do cliente. Portanto não acrescenta contexto transacional que não foi coletado. As respostas são complementares, não prova de causalidade. Indisponibilidade, desativação ou resposta inválida aparecem explicitamente na tela. A revisão fica na memória da página e no arquivo exportado, não é persistida automaticamente na OS. Registrar conclusões revisadas no campo de proposta e a evidência do teste na validação.

## Roteiro do teste real

1. Abrir a central local, autenticar e habilitar atualização a cada dez segundos.
2. Instalar o JAR temporário V1.9 como módulo separado e configurar a classe `br.com.zaya.engineering.nexobutton.monitoring.ScheduledLogMonitor` em ação agendada Java, inicialmente a cada 60 segundos e sem transação automática.
3. Rodar uma vez para estabelecer o checkpoint. O coletor não importa erros antigos.
4. Reproduzir o particionamento com erro em homologação. Registrar nota, operação e horário separadamente para confrontar a captura. O erro precisa chegar ao arquivo de log em formato reconhecido; mensagens apenas de tela podem não ser capturadas.
5. Aguardar dois ciclos e conferir a informação da ação agendada: leitura, envio e pendências. Abrir a nova ocorrência ao vivo na central.
6. Conferir trecho, categoria, hipótese, evidências ausentes, orientação e revisão da IA. A causa não deve ser apresentada como confirmada com base no texto isolado.
7. Executar somente uma intervenção autorizada, repetir o mesmo cenário e registrar proposta, rollback e resultado da validação na OS.

O monitor de log não realiza consultas ad hoc de banco nem coleta automaticamente itens/estoque para cada ocorrência. O botão de investigação existente pode coletar contexto da nota separadamente; vinculação automática entre esses dois fluxos ainda não foi implementada. Não confundir coincidência textual com correlação comprovada.

O SDK de compilação continua 4.35 com alvo Java 8; instalação OM 4.36b159 requer homologação. Limites e cobertura estão em [monitor agendado](scheduled-log-monitor.md).

## Validação executada

- Maven verify: 37 testes, sem falhas; inclui isolamento dos novos endpoints e fallback com IA desativada.
- Teste do modelo com resposta válida e rejeição de resposta que tenta confirmar causa; dados privados fora do prompt.
- Gradle build do Java 8 V1.9: verificações de coleta, fila, rotação, redaction e transporte passaram.
- JavaScript: sintaxe e referências a IDs do HTML conferidas.
- Revisão real com Ollama do caso histórico DELIVERED_QTY: IA CONCLUIDA, estado HIPOTESE, sem criar novo evento na lista.
- Reimportação histórica: zero novos eventos, oito entregas duplicadas reconhecidas.
- Revisão visual por automação não concluída: ferramenta de controle de janela interrompeu por não conseguir confirmar a URL. Conferência visual manual permanece necessária.

Nova sessão de 60 minutos autorizada pelo usuário em 16/10. Receptor de entrada e túnel iniciados; HTTPS público não validado neste PC. O primeiro envio do ERP deve provar conectividade. Nenhuma correção foi implantada no ERP. Não houve commit ou push das alterações deste MVP.
