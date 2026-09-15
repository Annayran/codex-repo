# <nome-do-projeto>

## Identificação

- Cliente: `<cliente>`
- Solução: `<solucao>`
- Pacote-base: `br.com.zaya.<cliente>.<solucao>`
- Java: `8`
- Código do artefato: `<SIGLA>`
- Versão do projeto: `1.0`
- Versão Sankhya validada: **Pendente de validação pela equipe**

Substitua os marcadores antes do primeiro desenvolvimento. Os segmentos de
pacote devem usar somente letras minúsculas e números, sem espaços, acentos ou
hífens.

## Objetivo

**Pendente de validação pela equipe**.

## Estrutura

Novos projetos usam MVC. Crie somente os pacotes utilizados:

```text
br.com.zaya.<cliente>.<solucao>/
├── controller/
├── model/
│   ├── dto/
│   ├── entity/
│   ├── repository/
│   └── service/
├── view/
├── sankhya/
└── shared/
```

Em rotinas sem interface, `view` pode ser omitido. Em Add-on Studio, use o
template oficial do componente e preserve os módulos `model`, `vc`,
`datadictionary` e `dbscripts`; este template é destinado a módulos Java
legados ou independentes.

Recursos carregados pelo classpath devem ficar em `src/main/resources`,
reproduzindo o caminho do pacote consumidor.

## Build

As dependências Sankhya são localizadas pela propriedade
`sankhyaDependenciesDir`, pela variável `SANKHYA_DEPENDENCIES_DIR` ou pelo
fallback corporativo `C:/dev/zaya/platform/modulos-sankhya/Dependencias`.

Quando necessário, configure a pasta de versão:

```powershell
.\gradlew.bat clean build -PsankhyaVersionDir=4_35
```

Não presuma que `4_35` seja a versão do cliente; valide antes de publicar.

O artefato é gerado como:

```text
ZAYA-<SIGLA>-V<MAJOR>.<MINOR>.jar
```

Configure `projectCode`, `projectTitle` e `projectVersion` em
`gradle.properties`. Ajustes incrementam o menor (`1.0` para `1.1`) e
funcionalidades novas incrementam o maior (`1.1` para `2.0`). Não publique
`SNAPSHOT`.

Abra o diretório no IntelliJ como projeto Gradle. Não versione `.idea`, `*.iml`,
`out` ou configurações locais da IDE.

## Banco de dados

Quando houver mudança, registre scripts separados de deploy, validação e
rollback em `database/`.

## Publicação e rollback

**Pendente de validação pela equipe**.
