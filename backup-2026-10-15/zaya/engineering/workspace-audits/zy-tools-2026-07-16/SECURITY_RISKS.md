# Riscos de segurança

| Caminho | Tipo provável | Risco | Ação recomendada |
|---|---|---|---|
| CRM Bonus/TennisPlace/TennisPlace/src/main/java/br/com/crmbonus/common/integration/EmpresaCredentialsProvider.java | provedor de credenciais | alto | revisão manual e migração para armazenamento seguro |
| CRM Bonus/TennisPlace/TennisPlace/src/main/java/br/com/crmbonus/common/config/CrmHttpConfig.java | configuração de API | alto | validar segredo embutido e externalizar |
| TennisPlace/src/main/java/br/com/crmbonus/*/config/CRMConfig.java | configuração de API | alto | revisar sem registrar valores e rotacionar se necessário |
| Polyvox/PolyvoxTelecontrol/src/main/java/br/zaya/telecontrol/shared/config/ConfigTelecontrol.java | configuração de integração | alto | externalizar credenciais e restringir acesso |
| Polyvox/PolyvoxTelecontrol.codex-backup-20260611-1605/src/main/java/br/zaya/telecontrol/shared/config/ConfigTelecontrol.java | cópia de configuração de integração | crítico | revisar cópia, rotacionar se confirmado e retirar de distribuição após aprovação |
| Polyvox/Koncili/src/main/java/br/com/zaya/integration/KonciliWebhookClient.java | token/webhook | alto | usar secret manager/variável protegida |
| MA Hospitalar/src/main/java/br/com/zaya/mahospitalar/genesis/config/ParametrosGenesis.java | parâmetros de API | alto | revisar armazenamento e rotacionar se necessário |
| Skala/sankhya-om-plataforma-main/SKL-Core-Action/src/tech/skala/action/integration | credenciais de integrações | alto | inventariar secrets por integração e externalizar |
| CRM Bonus/TennisPlace/TennisPlace/src/main/Modal JSP | token/senha em frontend ou exemplo | crítico | confirmar imediatamente; remover exposição de frontend após aprovação |
| TennisPlace/src/main/Modal JSP | token/senha em frontend ou exemplo | crítico | confirmar imediatamente; remover exposição de frontend após aprovação |

Nenhum valor de senha, token ou chave foi reproduzido neste relatório. As ocorrências são heurísticas e exigem confirmação manual segura.