# Relatorio Consolidado - Organizacao Workspace

Data: 2026-06-11

## Dashboards organizados

### Polyvox

Destino:

`C:\zy-tools\workspace\Polyvox\Dashboards`

Itens:

- `dashboard_clientes`
- `dashboard_os_tlc`
- `dashboard_reclforn`

### Portomag

Destino:

`C:\zy-tools\workspace\Portomag\Dashboards`

Itens:

- `portomag_kpi_html5`
- `portomag_leadtime_html5`
- `portomag_leadtime_html5_v1`
- `portomag_cotacao_html5`
- `portomag_cotacao_html5_v1`
- `portomag_kpi_auditoria`
- `portomag_xml_auditoria`
- `portomag_xml_final`

## Projetos Java/Sankhya em conformidade

- `C:\zy-tools\workspace\Skala\sankhya-om-plataforma-main`
  - Gradle Java 8 multi-modulo.
  - Build validado anteriormente com `clean distAll check`.

- `C:\zy-tools\workspace\Polyvox\PolyvoxTelecontrol`
  - Build: `BUILD SUCCESSFUL`
  - Jar: `TelecontrolPolyvox.jar`
  - Estrutura corrigida apos remover pastas aninhadas `src\src`, `docs\docs`, `gradle\gradle`.

- `C:\zy-tools\workspace\Polyvox\ImportadorPedido`
  - Convertido para Gradle Java 8.
  - Build: `BUILD SUCCESSFUL`
  - Jar: `ImportadorPVX.jar`

- `C:\zy-tools\workspace\Polyvox\Koncili`
  - Gradle Kotlin validado.
  - Wrapper alinhado para Gradle `9.0.0`.
  - Build: `BUILD SUCCESSFUL`
  - Jar: `Koncili.jar`

- `C:\zy-tools\workspace\Lidon\CriacaoNotaEst`
  - Wrapper alinhado para Gradle `9.0.0`.
  - Build: `BUILD SUCCESSFUL`
  - Jar: `CRIACAONOTAEST-1.0-SNAPSHOT.JAR`

- `C:\zy-tools\workspace\Lidon\BarramentoRegrasLidon`
  - Convertido para Gradle Java 8.
  - Corrigidos imports ausentes em `ReservaRepository.java`.
  - Build: `BUILD SUCCESSFUL`
  - Jar: `BarramentosRegrasLidon.jar`

- `C:\zy-tools\workspace\TennisPlace`
  - Dependencias Sankhya apontadas para `C:\zy-tools\modulos-sankhya\Dependencias`.
  - Wrapper alinhado para Gradle `9.0.0`.
  - Build: `BUILD SUCCESSFUL`
  - Jar: `CRMBonusIntegration.jar`

- `C:\zy-tools\workspace\Portomag\CRMLeituraPDF`
  - Wrapper alinhado para Gradle `9.0.0`.
  - Build: `BUILD SUCCESSFUL`
  - Jar: `CRMSankhyaPdf.jar`

- `C:\zy-tools\workspace\Teleword`
  - Ja estava em Gradle Java 8.
  - Jar existente: `FLOWPARC-1.0-SNAPSHOT.JAR`

- `C:\zy-tools\workspace\AttTelecontrol`
  - Ja estava em Gradle Java 8.
  - Jar existente: `AtualizacaoTelecontrol.jar`

## Duplicidades classificadas

Indice criado em:

`C:\zy-tools\workspace\_legado_duplicados\README_DUPLICIDADES.md`

Classificados como legado/consulta:

- `C:\zy-tools\workspace\CRM Bonus\TennisPlace\TennisPlace`
- `C:\zy-tools\workspace\Lidon\lidon-lacamentoos-main 2\CriacaoNotaEst`

## Pacotes Skala pendentes

Destino:

`C:\zy-tools\workspace\Skala\_pacotes_codex_para_consolidar`

Status:

- Copiados e preservados.
- Nao mesclados automaticamente no Skala principal.
- Precisam de revisao funcional pacote por pacote.

## Observacoes gerais

- Os projetos Gradle estao padronizados em Java 8.
- A maioria usa dependencias locais em `C:\zy-tools\modulos-sankhya\Dependencias`.
- Restam avisos esperados de Java 8 em JDK moderno e alguns `unchecked` em codigo legado.
- Testes automatizados sao raros; varios projetos retornam `NO-SOURCE` para testes.
