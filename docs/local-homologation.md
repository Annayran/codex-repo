# Homologação com IA local

## Equipamento verificado em 15/10/2026

15,7 GB RAM; Intel i5-1135G7, quatro núcleos; Intel Iris Xe integrada; 59 GB livres antes dos downloads. Modelo inicial: qwen2.5:3b, aproximadamente 1,9 GB de download. A escolha é para avaliação local, não atestado de qualidade ou licença de uso em produção. Conferir os termos do modelo antes de distribuir uma solução comercial.

Ollama portátil 0.40.4 obtido da release oficial do GitHub; SHA256 conferido contra o digest publicado. Executáveis em target/tools/ollama, modelos em target/ollama-models. Nenhum serviço do Windows, mudança de PATH ou atualização do Java/Gradle foi feita.

Referências: [Windows](https://docs.ollama.com/windows), [compatibilidade da API](https://docs.ollama.com/api/openai-compatibility), [modelo](https://ollama.com/library/qwen2.5:3b).

## Uso

Na raiz do projeto, execute `& .\scripts\Start-NexoLocal.ps1`. Abra http://127.0.0.1:18083/nexo.html. Acesso sintético: demo-a / fixture-only-a. Esses valores são públicos de desenvolvimento; o perfil local só deve ouvir loopback, nunca ser exposto por túnel nem usado para receber dados reais.

A tela consulta diagnóstico, contexto e log pelo protocolo; o botão de teste gera um caso sintético de diferença de unidade. Não há consulta direta ao ERP nesse cenário. Relatórios antigos de outras identidades/ambientes não aparecem automaticamente. A tela não salva credenciais no armazenamento do navegador e usa textContent para apresentar conteúdo recebido.

O perfil local mantém a biblioteca LangChain4j existente e direciona suas chamadas para a API compatível do Ollama em 127.0.0.1:11434/v1. O valor de chave `ollama` é fictício, ignorado pelo servidor local. Sem chamadas de inferência a API paga. Timeout local: 180 segundos por chamada, max-retries=1 (mínimo aceito pela extensão). O modelo pode produzir uma resposta rejeitada; nesse caso o diagnóstico determinístico permanece e explanation informa a falha. Não confundir fallback com análise válida de IA.

O script retorna o caminho de session.json. Para encerrar, execute `& .\scripts\Stop-NexoLocal.ps1 -SessionPath '<caminho retornado>'`. Ele valida PID e horário de início; encerra somente os processos que iniciou. Um Ollama já existente não é encerrado.

## Limites e próxima etapa

A validação de estrutura e referências não garante correção semântica. É preciso revisar respostas reais e medir desempenho. O perfil local usa evidências sintéticas e credencial pública de teste. Receber novo incidente real pelo Sankhya exige identidade individual protegida, vínculo de ambiente e uma nova sessão HTTPS com prazo definido; a autorização anterior de 60 minutos já expirou. Não publicar o perfil local.

A instalação portátil e o download inicial requerem internet; a inferência usa o modelo baixado. Computador ligado, memória, energia e armazenamento continuam sendo recursos necessários. Não há promessa de disponibilidade para produção.


## Revisão sem bloquear o botão

POST /investigations/packages salva o relatório determinístico sem inferência, mesmo com IA habilitada. POST /investigations/{id}/review é autenticado e reanalisa o contexto salvo, gerando novo protocolo; a ferramenta reviewStoredEvidence registra o protocolo de origem. Nenhum acesso adicional ao ERP é realizado. A tela possui o botão “Analisar evidências com IA”.

Na sessão temporária individual, execute scripts/Get-NexoSessionAccess.ps1 -SessionPath <session.json> -CopyPassword para obter usuário e copiar a senha temporária sem imprimi-la. Cole-a na tela local da porta da sessão, use o protocolo recebido e acione a análise. Limpe a área de transferência depois. O botão sintético da tela é exclusivo do perfil local; não funciona na sessão com gateway desabilitado.

## Resultado observado

Em 15/10/2026, o teste básico respondeu “pronto” em 4,9 segundos. A primeira investigação levou 25,2 segundos e caiu no fallback por resposta inválida. Depois de ajustar as instruções, uma investigação sintética levou 22,4 segundos e foi aceita estruturalmente: protocolo 1ed74e6e-2658-4bcb-9672-85a6eeffe7c7, HIPOTESE, trace explainAdvisory=ACCEPTED. A resposta sugeriu verificar unidades/conversão e documentos, mas copiou formulações genéricas do exemplo. Portanto, funcionamento da inferência foi demonstrado; qualidade para diagnóstico real ainda não homologada.

A tela foi verificada por HTTP e o JavaScript por node --check. Não houve inspeção visual automatizada em navegador nem teste ponta a ponta executado pelo Sankhya nesta sessão; este último depende do operador instalar e acionar o novo JAR.

Teste final: pacote sintético recebido e revisão autenticada pelo receptor de identidade individual. Protocolo de origem a1e1f971-ee6c-4fa7-8002-4577777bce0e; revisão 13676d1d-6bf3-4ea3-920b-3fef99d58cc7; explainAdvisory=ACCEPTED; INCONCLUSIVA; 27,9 segundos. Acesso anônimo respondeu 401. Vinte e cinco testes passaram; pacote final gerado. A sessão HTTPS foi substituída durante a finalização usando apenas o tempo restante, sem estender os 60 minutos autorizados. Vencimento: 15/10/2026 12:05 Brasília. A conectividade pública não foi validada pela rede local.

## Análise operacional e solução orientada (15/10/2026)

GET /investigations/{id}/analysis, autenticado e limitado ao escopo do usuário, produz análise a partir do contexto salvo. Funciona também para casos cujo modelo falhou; não reclassifica o estado nem executa alterações. Para produto explicitamente inativo, apresenta código, observação, procedimento manual condicionado à autorização do responsável, validação e fontes oficiais. Os dois artigos oficiais consultados divergem no caminho/aba (Propriedades ou Geral); o sistema informa a variação e não declara o layout homologado na versão do cliente.

Fontes revisadas: https://ajuda.sankhya.com.br/hc/pt-br/articles/360043672613-Produto-n%C3%A3o-existe-ou-est%C3%A1-inativo-ou-n%C3%A3o-pode-ser-usado-aqui e https://ajuda.sankhya.com.br/hc/pt-br/articles/37884846550551-Como-cadastrar-Produtos. Orientam o cadastro padrão; não comprovam a implementação do gatilho customizado mencionado no incidente.

No caso fe0cd66c-52f8-4cea-b3d2-8a7bd5cd9b30, a explicação anterior registrou PROVIDER_FAILURE em 360571 ms e recuperação de zero documentos. A entrada do modelo agora agrupa achados por tipo, prioriza produto inativo, limita referências e resume lacunas repetidas. O relatório completo permanece armazenado. Isso reduz volume, mas não garante ausência de timeout ou veracidade da resposta. A orientação de solução é uma regra documentada independente do modelo; o prompt consultivo continua sem autorizar ações de escrita.

Validação da atualização: 28 testes passaram; Maven verify concluído; endpoint /analysis do caso real retornou produto 19391, nota 5031796, cinco passos e duas fontes; HTML respondeu 200 e JavaScript passou em node --check. Receptor reiniciado preservando identidade e vencimento da sessão.
Correção de interface: capacidades autenticadas em /investigations/capabilities; botão sintético oculto por padrão e habilitado somente no escopo de demonstração. Reproduzido 503 no POST sintético da sessão com gateway desabilitado; consultas de protocolos HTTP 200. 28 testes passaram e pacote gerado.
