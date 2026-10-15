# Monitor agendado do log Sankhya

A rotina Java `br.com.zaya.engineering.nexobutton.monitoring.ScheduledLogMonitor` lê os novos registros de erro do log da instância e envia trechos filtrados ao Nexo. Não altera pedidos, estoque, banco ou personalizações. Classificação automática é provisória; ERROR no log não comprova falha de transação nem criticidade.

## Instalação e teste

1. Provisionar um JAR temporário com receptor HTTPS ativo. O JAR comum `integration/sankhya-button/build/libs/ZAYA-NEXO-V1.8.jar` não contém credencial e permanece sem envio até provisionado.
2. Importar como módulo Java separado, chamado Nexo Monitor Log. Não substituir o módulo de particionamento.
3. Em Configurações → Cadastros → Ações Agendadas, cadastrar uma ação Java, selecionar o módulo e informar a classe acima. Começar com intervalo de 60 segundos, sem transação automática. Conferir os campos disponíveis na versão instalada. Não há parâmetro MENSAGEM_ERRO.
4. Executar uma vez para estabelecer o ponto inicial no fim do arquivo. Erros anteriores não são reenviados.
5. Produzir um erro conhecido em homologação, aguardar dois ciclos e consultar `http://127.0.0.1:18085/issues.html`. Conferir ocorrência LIVE, mensagem e trecho filtrado. O erro original do ERP continua sendo tratado pela rotina original.

Referência de cadastro: [Ações Agendadas Sankhya](https://ajuda.sankhya.com.br/hc/pt-br/articles/360045110653-A%C3%A7%C3%B5es-Agendadas).

## Coleta e limites

O caminho vem de `org.jboss.boot.log.file`. O checkpoint e a pequena fila persistente ficam em `jboss.server.data.dir/nexo-log-monitor`, com acesso restrito. A hospedagem precisa permitir leitura do log, escrita nessa pasta e saída HTTPS. O arquivo de origem é somente lido.

Cada execução lê até 64 KiB; a fila comporta dez eventos e envia até cinco por ciclo. O trecho tem até 2.000 caracteres e 16 linhas. Credenciais, corpos de requisição, SQL e identificadores reconhecidos são omitidos ou mascarados por heurísticas, sem garantia de anonimização completa. O receptor aplica nova filtragem. Não há envio automático desses trechos à IA.

São reconhecidos ERROR/FATAL em formatos de cabeçalho suportados e algumas exceções/ORA sem nível. WARN não vira incidente automaticamente. O encerramento de um registro pode exigir um ciclo sem novas linhas, portanto a latência inicial esperada é de um a dois ciclos. Continuação tardia de stack pode ser separada. A correlação é textual, sem vínculo comprovado com uma execução ou nota.

O coletor considera UTF-8 e um arquivo por instância. Linhas excessivas são descartadas de forma limitada; arquivos arquivados não são percorridos. Rotação/truncamento detectados reiniciam a leitura do arquivo atual, preservando a fila, mas rotações rápidas entre ciclos podem perder cobertura. Não é um monitor de todos os nós nem de erros que nunca chegam ao log.

O envio mantém identidade estável e reenvia o mesmo evento após falha, com deduplicação no receptor. Fila bloqueada impede avançar na origem. Estado corrompido ou mudança de identidade exige revisão; não é apagado automaticamente. Eventos LIVE novos com mais de 24 horas podem ser rejeitados pelo receptor e permanecer pendentes. Os timeouts de rede são por operação, não um prazo global garantido.

## Receptor temporário e encerramento

`scripts/Start-NexoCollectorSession.ps1` cria uma entrada temporária restrita a envio de eventos, encaminhando para a central local existente. Cloudflare Quick Tunnel e credencial extraível no JAR exigem autorização de homologação limitada a 60 minutos. A sessão anterior expirada não autoriza uma nova publicação.

O túnel não publica a consulta de OS nem os comandos de triagem. A expiração encerra a entrada e revoga o envio, mantendo a central local e seus dados. Não é infraestrutura de produção. O PC precisa continuar ligado e acessível.

Para rollback, desativar a ação agendada e encerrar a sessão com `scripts/Stop-NexoCollectorSession.ps1`. Preservar checkpoint/fila para análise; apagar estado perde pendências e cria outro ponto inicial. O SDK usado no build é 4.35; a execução na instalação OM 4.36b159 ainda precisa ser homologada.
