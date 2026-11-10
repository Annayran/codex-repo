<!-- generated-by: zaya-sankhya-development -->
<!-- zaya-profile:fingerprint=DDA19284223148B6F89F6246B85DBD480C07D733931ADD774EC03247A3F1C153 -->
# Perfil tecnico do projeto

> Arquivo gerado automaticamente. Fatos funcionais e compatibilidade Sankhya
> ainda exigem confirmacao no ambiente e na documentacao oficial.

## Identificacao

- Projeto: SKala
- Classificacao: Java/Sankhya legado ou modular
- Build: Gradle
- Java detectado: 8
- Gradle Wrapper detectado: 9.3.0
- Profile gerado em: 2026-10-16T15:05:04-03:00
- Git HEAD de origem: 2267522d1f51d7001be97783037f86f24f64dcf1
- Build hash: 8E6BA32A3983932AA7CDD91970FD86D17420874FF6E3BFEAFE140F3C24A802B4
- Source tree hash: B70E561431B2806738DD8C72E9AB2C11FBE968041B08F51C61238CDF43102FD0

## Build

- `build.gradle`

## Estrutura

- Arquivos Java: 1098
- Testes: 3
- Arquivos em pacotes MVC: 73
- Arquivos em pacotes de arquitetura em camadas: 0
- XMLs em datadictionary: 0
- Arquivos em database/dbscripts: 6
- Relatorios: 0

## Codigo

- Package roots: br.com.zaya.engineering.nexobutton.monitoring, br.com.zaya.skala.particionamento, br.com.zaya.skala.particionamento.config, br.com.zaya.skala.particionamento.model, br.com.zaya.skala.particionamento.service, br.com.zaya.skala.particionamento.util, br.zaya.actions, br.zaya.events, br.zaya.extrema.controller, br.zaya.extrema.dao, br.zaya.extrema.events, br.zaya.extrema.model, br.zaya.extrema.service, br.zaya.extrema.util, br.zaya.helpers, br.zaya.nfejson.action, br.zaya.nfejson.dao, br.zaya.nfejson.service, br.zaya.nfejson.util, br.zaya.rules
- Entry points: 30
- Actions: 20
- Schedules: 12
- Listeners: 20
- Services: 20
- Repositories: 20
- Uso de Jape: True
- Uso de NativeSql: True
- Principais tabelas: AD_CABENTFINSEXP, AD_CABENTRDEVEXP, AD_CABENTRDEVNAC, AD_CABINDENIZACAO, AD_CABREMTRANSF, AD_CABSAIFINSEXP, AD_CAMADAS, AD_CODCIDMAE, AD_CODOCO, AD_CODPARCORIG, AD_CODPROD, AD_CODTOPENTDEV, AD_CODUSUCAB, AD_CODUSULIB, AD_DESCFINA, AD_DHAGENDAMENTO, AD_DTBASEPEDIDO, AD_DTCOMERCIAL, AD_DTCONFIRM, AD_DTCORTEVT18, AD_DTENTEFETIVA, AD_DTFINFECH, AD_DTINIFECH, AD_DTNOTAINDENIZA, AD_ENTLANCEXT, AD_EXIGCLIENTE, AD_GERAENTAUT, AD_ID_TANGERINO, AD_ITGLOG, AD_ITGLOGITE
- Dependencias criticas: compileOnly fileTree(dir: sankhya435Path, include: ['*.jar']); testImplementation fileTree(dir: sankhya435Path, include: ['*.jar'])

## Alertas

- Versao SNAPSHOT detectada.
- Convencao corporativa de nome do JAR nao detectada.

## Conhecimento aplicavel

- Leia o AGENTS.md mais proximo antes de qualquer alteracao.
- Use os padroes MVC, Gradle e Java da biblioteca Zaya.
- Para Add-on Studio ou metadados, use o template oficial e valide o XSD.
- Para transacoes e documentos, aplique os checklists Sankhya correspondentes.