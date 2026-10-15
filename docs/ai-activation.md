# Ativação da IA — homologação

Situação em 13/10/2026: integração implementada, chamadas reais não executadas; OPENAI_API_KEY ausente no processo verificado. Não reutilizar o túnel nem as credenciais de botão da sessão expirada. Este guia não publica serviço.

O receptor lê OPENAI_API_KEY, NEXO_AI_ENABLED (padrão false) e NEXO_MODEL (padrão existente gpt-4o-mini). A configuração da chave via ambiente do processo servidor segue a [documentação oficial](https://developers.openai.com/api/reference/overview). Não colocar chave no JAR do botão, arquivos versionados, linha de comando ou chat.

Para uma chamada real, o operador deve disponibilizar a chave no ambiente protegido do receptor e definir NEXO_AI_ENABLED=true antes de iniciar o Java. NEXO_MODEL pode manter o padrão existente; a disponibilidade na conta ainda precisa ser validada. Não há necessidade de parâmetros adicionais no Sankhya para a IA: a integração ocorre no receptor.

O receptor precisa também da identidade autenticada e do vínculo tenant/ambiente já previstos pela instalação. Os scripts de sessão temporária forçam IA desligada; não basta definir a variável e executar esses scripts. Sua sessão antiga expirou. Uma nova publicação de homologação exige nova sessão com prazo definido, e infraestrutura permanente continua pendente.

No PowerShell aberto na raiz do projeto, execute `& .\scripts\Set-NexoAiEnvironment.ps1`. A chave é solicitada com entrada oculta e colocada apenas no ambiente do processo atual, sem persistir em arquivo. Inicie o receptor pelo mesmo terminal para herdar esse ambiente. Não execute o comando dentro de outro `powershell -File`, pois o ambiente não retornará ao terminal pai. Para limpar, execute `& .\scripts\Set-NexoAiEnvironment.ps1 -Disable` e reinicie o receptor. O processo Java precisa receber o segredo em memória; entrada oculta não substitui um gerenciador de segredos para produção.

## Aceite da primeira chamada

1. Iniciar receptor com identidade válida e armazenamento protegido, mantendo o acesso local para o primeiro teste.
2. Enviar pacote sintético pelo endpoint autenticado; consultar o protocolo e verificar explanation como string JSON válida, estado preservado e referências existentes. Não considerar a mensagem de fallback como sucesso do provedor.
3. Revisar o texto usando os critérios de first-case-analysis.md. Validação estrutural não atesta veracidade ou pertinência; exemplos sintéticos de teste não demonstram qualidade do modelo.
4. Somente depois, avaliar um novo caso real autorizado. Erro original e log não são enviados; atualmente a entrada contém achados determinísticos, lacunas, IDs e conteúdo limitado da biblioteca revisada. O modelo não tem acesso direto ao ERP nem executa suas sugestões.

São feitas até duas operações do modelo por investigação: planejamento restrito e explicação. Timeout e retries permanecem nos valores existentes. Há custo de uso do provedor; nenhum teste local com modelo substituto faz chamada externa.

Rollback: definir NEXO_AI_ENABLED=false e reiniciar o receptor. Relatórios existentes permanecem legíveis. A IA não reprocessa automaticamente os relatórios antigos.

Atualização 15/10/2026: existe alternativa com Ollama local, documentada em local-homologation.md, sem chave de provedor pago. O recebimento de pacotes não chama mais IA; a análise é solicitada separadamente por POST /investigations/{id}/review, com novo protocolo. Os scripts temporários mantêm IA desligada por padrão, mas agora aceitam -LocalAi, que aponta exclusivamente para Ollama em loopback.
