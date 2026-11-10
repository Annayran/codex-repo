# Zaya API Boletos

Add-on próprio de cobrança bancária para Itaú e Santander. Núcleo independente de banco, parametrização por empresa/conta, fila transacional, reserva de inclusão, consulta de recebimentos e baixa nativa Sankhya. Produto sem dependência do módulo Sicoob/ZayaProd.

## Situação desta versão

Este checkout foi adequado ao ZIP de referência fornecido em 06/11/2026. A implementação Java da referência foi reaproveitada integralmente; o antigo serviço isolado de PDF foi retirado da aplicação ativa. A documentação anterior está em `docs/historico/antes-adequacao`.

**Validação deste checkout:** 38 testes Java aprovados, estrutura/XML/contrato Itaú e verificações frontend aprovados, smoke visual desktop/mobile com RPC simulado aprovado e bytecode Java 8 conferido. Foi usado JDK 17 com o SDK oficial do Add-on Studio e complemento NF-e local 4.35b99 apenas para compilação/testes. Isso não comprova compatibilidade com o SDK/runtime 4.36b88, que ainda não foi disponibilizado.

Não há identidade real do componente neste checkout nem pacote `.exts` gerado nesta adequação. Os perfis permanecem bloqueados. Fontes, migração, diferenças de build e pendências: [adequação local](docs/ADEQUACAO_LOCAL.md).

Versão 0.1.0 para desenvolvimento e homologação. **A ativação de produção está bloqueada por padrão.** Os perfis bancários têm `homologado=false` no manifesto da aplicação; não há contas, credenciais bancárias ou cofre Cloud homologados neste ambiente. O pacote não comprova homologação financeira ou bancária.

A identidade autorizada do produto não veio no ZIP. O contrato de carregamento é preservado: `.local/addon-identity.gradle`, ignorado pelo Git, ou variáveis de ambiente autorizadas. Também são aceitos `-PzycbIdentityFile` e `ZYCB_IDENTITY_FILE` para um arquivo autorizado. Não inventar uma AppKey nem trocar a identidade de uma solução cadastrada para gerar uma atualização.

Implementado: assistente HTML5, entidades/Dynaforms de consulta, serviços com controle por empresa, eventos, agendamentos, migrações Oracle/SQL Server, criptografia autenticada, clientes HTTP com mTLS, adaptadores bancários, reserva antes do envio, acompanhamento de operações e recebimentos, reemissão após cancelamento confirmado e entrega pela mensageria nativa.

As consultas Dynaform são técnicas e somente leitura. Devem ser concedidas apenas aos administradores técnicos na tela de acessos Sankhya; a operação cotidiana usa a central HTML5, com filtro e autorização de empresa no servidor. As tabelas de credenciais e os snapshots privados não são mapeados em Dynaform.

## Compilação

Utilizar JDK 17 para Gradle 8.2; fontes compilados para Java 8. Add-on Studio fixado em 2.18.0. Definir `SNK_LIB` com as bibliotecas Sankhya 4.36 e `api_outros` para o ambiente local. Também é possível informar `-PsnkLib`. Não há caminho de máquina fixo no projeto.

```sh
./gradlew :model:test gerarAddon
python3 scripts/check_project.py
node scripts/frontend.test.cjs
```

Saída: `build/libs/zaya-api-boletos.exts`. O exportador valida os módulos e os scripts Oracle/SQL Server antes de gerar o pacote e recusa artefato vazio. A tarefa não publica no portal e não instala em produção.

A plataforma mínima técnica declarada é 4.36, com compilação local contra SDK 4.36b88. A matriz de patches e o runtime Cloud exigem homologação real antes de distribuição operacional.

## Ativação

### Validação no Windows sem identidade

```powershell
.\scripts\Build-Addon.ps1 -JavaHome '<JDK17>' -SdkOficial -NfeJar '<JAR-NFe-licenciado>' -PythonExe '<python>' -NodeExe '<node>'
```

Sem identidade, o runner executa os testes no perfil `zycbValidacaoSemIdentidade`, que não gera descritores de distribuição e bloqueia geração de pacote, deploy e publicação. Para o SDK local da referência, substituir `-SdkOficial/-NfeJar` por `-SnkLib '<raiz-com-4_36-e-api_outros>'`. `-GerarPacote` exige identidade real e nunca usa esse perfil de validação. Python do staging é configurável por `ZYCB_PYTHON` ou `-PzycbPython`.

As opções `-SemProxy` e `-IpcTcp` são contornos opcionais do processo de build para hosts que precisem deles; o ambiente é restaurado ao final. `-Smoke`, com `-BrowserExe` e `-PlaywrightModule`, executa a interface com serviços simulados. Detalhes em [ADEQUACAO_LOCAL.md](docs/ADEQUACAO_LOCAL.md).

### Ativação após homologação

1. Instalar o pacote em homologação e validar migrações V1 e V2. Não executar scripts de remoção de demonstrações em bases existentes.
2. Prover o armazenamento seguro conforme [guia de instalação](docs/INSTALACAO.md). Sem cofre disponível não é possível gravar credenciais nem ativar a conta.
3. O usuário SUP concede os primeiros acessos por empresa; perfis disponíveis: ADMIN, OPERADOR e CONSULTA.
4. Selecionar empresa, banco e conta; informar o contrato e a próxima numeração livre validada com o banco.
5. Informar regras fiscais, financeiras, juros/multa, modelo de boleto da conta, SMTP e credenciais. Os percentuais zero não acrescentam instrução explícita: conferir os padrões do convênio na homologação.
6. Executar diagnóstico de autenticação e consulta, sem emissão.
7. Concluir a [matriz de homologação](docs/HOMOLOGACAO.md), homologar o perfil na release e registrar a aprovação da conta.
8. Habilitar `ZYCBHABIL=S` e ativar a conta. A primeira ativação captura o corte por NUFIN; reativação preserva esse corte.

Não ligar o parâmetro global para tentar superar os bloqueios de perfil ou cofre. A marcação do manifesto somente deve mudar em uma release com contrato e evidências validados.

## Operação

O evento de documento autorizado grava a intenção na mesma transação do ERP. O job chama o banco após o commit. Um resultado incerto não gera repetição automática do POST. A ação Consultar resultado somente consulta; Reprocessar aceita exclusivamente falhas sem envio anterior. Alteração de vencimento/valor do financeiro registrado cria instruções separadas. Mudanças de empresa, conta e pagador exigem cancelamento prévio.

A entrega inicial é enfileirada após confirmação do registro. Segunda via e reenvio reutilizam a cobrança, sem emitir outro boleto. O PDF usa o modelo nativo da conta e a mensagem usa as preferências gravadas na configuração; a mensageria nativa controla envio SMTP.

Pausar novas emissões preserva consultas pendentes e coleta de recebimentos. A coleta pode ser pausada separadamente. Avisos provisórios, pagamentos parciais, estornos, títulos já baixados e divergências exigem análise financeira. A baixa automática deixa o movimento bancário não conciliado; o crédito deve ser conciliado no fluxo financeiro nativo após conferência.

## Limites e dependências

- Os campos/respostas Santander usados pelo perfil precisam ser confrontados com o OpenAPI vigente da aplicação. O manual público histórico não substitui essa validação.
- Itaú: o anexo fornece endereço de produção; nenhum sandbox foi inventado. O diagnóstico rejeita ambiente de homologação sem perfil de endpoint documentado.
- É necessário demonstrar retorno de liquidação definitivo, representação dos percentuais e acompanhamento das instruções nos dois bancos. Uma instrução sem confirmação continua pendente/incerta.
- Numeração inicial não importa estoque CNAB. Garantir exclusividade da conta com relação a Boleto Rápido e outros motores antes de ativar.
- A recuperação por NUFIN pressupõe numeração crescente; ativar em janela sem inclusões financeiras em andamento.
- Cofre/JNDI, transações JTA, `BaixaHelper`, impressão sem registro adicional, SMTP e execução física Oracle/SQL Server não foram testados contra um servidor Sankhya real.
- Não inclui Sofisa, Pix/boleto híbrido, DDA, pagamentos a fornecedores, protesto, negativação, migração massiva de legado ou estorno financeiro automático.

Detalhes: [arquitetura](docs/ARQUITETURA.md), [contratos](docs/CONTRATOS_BANCARIOS.md), [instalação](docs/INSTALACAO.md) e [homologação](docs/HOMOLOGACAO.md).

Certificados: [configuração, renovação e homologação](docs/CERTIFICADOS.md). Novas contas usam o certificado da empresa no Sankhya; upload protegido exige seleção explícita.

## Material para o desenvolvedor

[Manual técnico de validação e manutenção](docs/GUIA_DESENVOLVEDOR.md) · [Versão Word](docs/Guia_Tecnico_Desenvolvedor_Zaya_API_Boletos.docx) · [Colaboração](CONTRIBUTING.md).

Repositório privado exclusivo: `ZayaIT/zaya-api-boletos`. Não concede acesso aos demais projetos do monorepositório.
