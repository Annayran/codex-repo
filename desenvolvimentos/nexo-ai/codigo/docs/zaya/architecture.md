# Arquitetura e limites — Nexo AI

Serviço externo independente do Java 8/WildFly do ERP. Java 21 foi selecionado por requisito do serviço Quarkus; nenhum projeto legado foi migrado. O profile automático não detecta `maven.compiler.release` neste POM: a versão declarada e compilada é 21.

Botão de ação → coleta permitida no ERP → POST HTTPS de pacote versionado → autenticação central → escopo derivado de manifesto administrado → validação das evidências → conhecimento isolado → verificações Java → explicação opcional → diagnóstico e contexto normalizado persistidos juntos. O botão recebe um protocolo curto; suporte autenticado recupera o relatório e contexto.

As ferramentas são chamadas pelo executor Java. O modelo não recebe conector, acesso ao filesystem, SQL, identidade configurável ou operações de escrita. A escolha da ordem nunca omite uma etapa obrigatória. Relato bruto não é transmitido ao provedor; interpretação livre fica pendente da política de tratamento de dados. Documentos são dados, não instruções.

O receptor de pacotes não chama `SankhyaGateway`. O contrato antigo foi preservado para demonstrações em dev/test; o endpoint antigo fica desabilitado em produção. Nenhum relacionamento ou política funcional real foi validado. O coletor Java 8 do botão começa com nota e relato; leitura completa precisa validar campos, consultas, limites e versões na instalação alvo. O cliente não pode declarar causa raiz ou aprovar disponibilidade: o servidor verifica aprovação por cliente/ambiente/referência/versão ERP/versão do coletor.

Persistência local atende reprodução deste primeiro marco em uma instância. O diagnóstico original pode conter dados sensíveis do relato: ACL, criptografia de armazenamento e retenção devem ser aprovadas antes de dados reais. Memória conversacional não existe. Manifestos revisados são carregados no startup e filtrados por tenant, ambiente e processo; nenhum conteúdo do cliente é promovido à biblioteca corporativa.

CONFIRMADA exige origem e cadeia causal revisadas; não é produzida pelo receptor atual de pacotes nem pelas fixtures. HIPOTESE: divergência nos dados coletados sem origem demonstrada. INCONCLUSIVA: não há divergência demonstrável com dados disponíveis. Essa distinção não é porcentagem de confiança. Coleta posterior ao erro não comprova estado no instante da confirmação.

Não há deploy, alterações em banco, ações remotas, correções de estoque ou publicação de chamados.
