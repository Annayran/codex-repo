# Checkpoint Técnico

## Data

2026-06-18

## Estado Atual

Foi identificada uma solução Java/JSP focada em integração Sankhya <> Telecontrol. O projeto principal `PolyvoxTelecontrol` usa Java 8, Gradle, dependências locais Sankhya e empacota `TelecontrolPolyvox.jar`. A arquitetura está em migração incremental para pacotes por feature, com camada `application`, adapters, interfaces e preservação do legado em `services` e `actions`.

O dashboard `dashboard_os_tlc` consulta OS finalizadas em `AD_OSTLC`, identifica presença de peças em `AD_OSPECA`, consolida valores por parceiro/CNPJ e permite acionar rotinas Java cadastradas no Sankhya.

## Arquivos/Pastas Relevantes

- `PolyvoxTelecontrol/build.gradle`
- `PolyvoxTelecontrol/gradle/wrapper/gradle-wrapper.properties`
- `PolyvoxTelecontrol/docs/telecontrol-organizacao.md`
- `PolyvoxTelecontrol/src/main/java/br/zaya/telecontrol/actions`
- `PolyvoxTelecontrol/src/main/java/br/zaya/telecontrol/osintegracao`
- `PolyvoxTelecontrol/src/main/java/br/zaya/telecontrol/services`
- `PolyvoxTelecontrol/src/main/java/br/zaya/telecontrol/shared`
- `PolyvoxTelecontrol/src/main/java/br/zaya/telecontrol/models`
- `PolyvoxTelecontrol/src/main/java/br/zaya/telecontrol/parsers`
- `dashboard_os_tlc/dashboard_os_tlc.jsp`
- `dashboard_os_tlc/css/dashboardOsTlc.css`
- `Dashboards/dashboard_os_tlc`

## O que já está claro

- O projeto usa Java 8 e Gradle.
- O build gera `TelecontrolPolyvox.jar`.
- O runtime alvo é o ambiente Sankhya.
- Integração Telecontrol usa HTTP com token/ambiente carregados de `AD_TCLCFG`.
- OS integradas são persistidas em `AD_OSTLC` e peças em `AD_OSPECA`.
- OS finalizadas podem gerar nota de compra quando têm peça ou financeiro direto quando não têm peça.
- Há uma estratégia explícita de refatoração incremental documentada.

## O que precisa ser confirmado

- Fonte oficial entre `dashboard_os_tlc` e `Dashboards/dashboard_os_tlc`.
- Processo real de deploy e cadastro das rotinas no Sankhya.
- Versões exatas de Sankhya, banco e Java em produção.
- Parâmetros obrigatórios para criação de nota/financeiro.
- Se os demais projetos do workspace (`ImportadorPedido`, `Koncili`, `Dashboards`) devem ser incorporados ao contexto futuro.

## Próxima melhor ação sugerida

Confirmar com o responsável técnico/funcional a fonte oficial do dashboard e o fluxo de deploy no Sankhya. Em seguida, rodar `./gradlew test` ou `./gradlew clean build` no `PolyvoxTelecontrol` em ambiente com dependências locais disponíveis para transformar este checkpoint estático em checkpoint validado por build.
