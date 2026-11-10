# Matriz de homologação

## Adequação do checkout atual — 06/11/2026

Foram executados aqui 38 casos Java, com zero falhas/erros, as verificações de estrutura/XML/contrato Itaú, os testes de parâmetros e sintaxe JavaScript e o smoke visual desktop/mobile com RPC simulado. Os 88 arquivos de classe do produto conferidos têm major version 52 (Java 8). Houve revisão independente em leitura do delta de build e migração; o problema identificado de caminho relativo da identidade foi corrigido.

Ambiente desta validação: JDK 17.0.21+9, Gradle Wrapper 8.2, Add-on Studio 2.18.0, SDK oficial fornecido pelo plugin e complemento licenciado NF-e 4.35b99 em compileOnly. O SDK/runtime 4.36b88 da referência não foi disponibilizado nem obtido nesta etapa; esta suíte não comprova compatibilidade com ele.

O perfil de validação não usa identidade fictícia. O bloqueio de `gerarAddon` nesse perfil foi testado antes da execução das tarefas. Não foi gerado pacote instalável nesta adequação. As evidências de JAR/WAR/exts registradas abaixo pertencem à referência recebida, não a um pacote produzido neste checkout. Homologação de ERP/banco/Cloud continua pendente. Ver [ADEQUACAO_LOCAL.md](ADEQUACAO_LOCAL.md).

## Evidência local

A suíte exercita domínio, persistência, orquestração, segurança e parsing. H2 em modos Oracle e MSSQLServer comprova parte da portabilidade, sem substituir os dois SGBDs reais. Os testes não acessam bancos nem usam credenciais reais.

- Inclusão única e numeração durável.
- Rollback de intenção e contador.
- Concorrência entre workers.
- Timeout e recuperação por consulta sem repetir POST.
- Resposta assíncrona sem conclusão antecipada.
- Worker interrompido após reserva.
- Deduplicação de pagamento e registro de estorno como divergência.
- Pagamento parcial/provisório/cancelado sem baixa indevida.
- Elegibilidade fiscal e corte de ativação.
- Reemissão após cancelamento com novo número.
- Credencial cifrada vinculada à configuração e cofre ausente bloqueado.
- URL de acompanhamento externa rejeitada antes de enviar token.
- Validação de parâmetros, UTF-8 e ausência de demonstrativos.

## Pendências obrigatórias de ambiente

| Verificação | Situação nesta entrega |
| --- | --- |
| Oracle físico e SQL Server físico | Não executada |
| Instalação/atualização no Sankhya Cloud | Não executada |
| Cofre gerenciado e binding da chave | Não comprovado |
| Registro, consulta, instruções e cancelamento Itaú | Aguardam credenciais/conta e fixtures reais |
| Contrato e mapeamentos finais Santander | Aguardam OpenAPI e validação do banco |
| BaixaHelper, TOP, encargos e movimentos | Compilados contra SDK; execução real pendente |
| Impressão sem novo registro bancário | Deve ser demonstrada com modelo/conta do cliente |
| SMTP e entrega da fila nativa | Execução real pendente |
| Exclusividade versus CNAB/Boleto Rápido | Deve ser validada por conta |
| Chave/certificado entre nós e reinícios | Execução Cloud pendente |

## Aceite de conta

Emitir boletos distintos para registro, manutenção, cancelamento e pagamento. Conferir nosso número, linha digitável, código de barras e valor. Repetir eventos e interromper o worker depois da chamada bancária. Confirmar que a nova execução consulta o resultado, sem registrar novamente. Forçar rollback de documento e validar que não há operação remota.

Testar juros, multa, descontos, abatimentos, recebimento parcial, a maior, repetido e estornado. Demonstrar que o sistema registra divergência quando não há liquidação segura. Confirmar uma única baixa e que o movimento permanece não conciliado até a conferência do crédito.

Simular falha de PDF/e-mail e reenviar sem novo registro; cancelar e reemitir usando números diferentes. Reiniciar aplicação e verificar reservas, cursores e credenciais. Só liberar o manifesto e a ativação depois do aceite técnico e financeiro, registrando versão, banco, conta e ambiente.

## Verificação da entrega local

Build de 06/11/2026: 38 testes Java aprovados; verificações de XML/estrutura e parâmetros frontend aprovadas; smoke test headless com serviços simulados aprovado em desktop e mobile. Model JAR e WAR tiveram integridade ZIP e conteúdo verificados. O `.exts` usa a criptografia do exportador oficial; sua instalação continua pendente de ambiente Sankhya real.

O grafo local foi atualizado por extração AST, sem LLM. O parser apontou interpretação parcial dos Gradle e não gerou nós para o OpenAPI JSON; o build, os testes e o contrato versionado são as fontes de verificação desses arquivos.

Certificados: 9 testes específicos aprovados para origem explícita, ausência de fallback, isolamento de material transitório, renovação de token e diagnóstico, compatibilidade de configurações anteriores, validade, EKU, CNPJ e chave. Tela validada por navegador com serviços simulados, incluindo credenciais Sankhya sem arquivo. Acesso real ao Console NF-e/Cloud e autorização dos certificados no Itaú e Santander continuam pendentes. Veja CERTIFICADOS.md.
