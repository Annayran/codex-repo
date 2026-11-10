# Backlog — investigação de confirmação e estoque

| # | Título | Dependências | Critério de aceite | Situação |
|---|---|---|---|---|
| 1 | Contratos de entrada, diagnóstico e evidências | — | Sintoma, divergência e origem separados; referências e estados explícitos | Implementado |
| 2 | Serviço central e persistência | 1 | POST de pacote e GET de diagnóstico/contexto autenticados, isolados e reproduzíveis | Implementado; retenção e uso em múltiplas instâncias pendentes |
| 3 | Coletor Sankhya no botão e consultas validadas | 1 | Campos, chaves e semântica aprovados em 4.36b159/Core 5.21.4; parâmetros e limites | Adaptador Rotina Java e transporte Java 8; coleta mínima de nota/relato; SDK e consultas completas pendentes |
| 4 | Recepção de nota, itens, estoque e configurações | 3 | Pacote versionado, fontes e referências por item; lacunas explícitas | Receptor implementado e testado com dados sintéticos; TOP/histórico/configurações reais pendentes |
| 5 | Verificações determinísticas | 4 | Local/unidade/controle e disponibilidade segundo política aprovada; sem fórmula inventada | Implementado sobre DTOs simulados |
| 6 | Recuperação da biblioteca | 1 | Importação com fonte/versão/processo/cliente; resultados isolados e citados | Importador seletivo e manifesto persistente implementados; conteúdo funcional de cliente pendente |
| 7 | Orquestração LangChain4j | 4,5,6 | Fluxo limitado e IA sem alterar permissões/estado; ferramentas autorizadas e conhecimento delimitado | Ordem das ferramentas e explicação opcionais; interpretação de relato bruto e avaliação com modelo pendentes |
| 8 | Rastreabilidade e isolamento | 2,4,6,7 | Autenticação define escopo; evidências/contexto persistidos; testes cruzados negativos | Manifesto administrado de identidades/políticas implementado; identidade/segredos/TLS reais pendentes |
| 9 | Avaliação com incidentes conhecidos | 3–8 | Casos reais anonimizados e revisados, origem correta e limitações detectadas | Casos sintéticos implementados; homologação pendente |
| 10 | Rascunho de chamado | 1,9 | Rascunho referencia investigação; revisão humana antes de publicação | DTO futuro; geração/publicação pendentes |

Não há issues externas, deploy, correções no ERP ou DML. O próximo marco é compilar o botão contra o SDK alvo e homologar configuração protegida, saída HTTPS e coleta de um incidente conhecido na hospedagem do cliente. Nosso servidor recebe evidências; não precisa acessar o ERP pela rede. Idempotência de reenvio, criptografia/retention, seleção da infraestrutura central e instalação na hospedagem continuam pendentes.
