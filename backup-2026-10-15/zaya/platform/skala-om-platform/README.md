# Plataforma Sankhya

Este projeto engloba todo esforço de customização do ecossistema Sankhya Om utilizando a linguagem Java, incluindo `ações`, `ações agendadas`, `eventos`, `regras da central` e `extensões`.

## Padrões utilizados

O prefixo a ser utilizado para nomear projetos e objetos será: **SKL** (de SKAla).

### Nomenclaturas de projetos Java

A estrutura dos projetos Java seguirá o padrão adotado pela Sankhya para extensões, respeitando a estrutura MVC com um projeto **Model**, que contém os modelos e regras de negócio, e um projeto **VC** contendo a camada de apresentação.

```java
SKL-Core-Model
SKL-Core-VC

```

Para ações e demais implementações que se baseiem em módulos Java o projeto deverá usar a terminação **Action**.

```java
SKL-Core-Action

```

Iremos utilizar somente o módulo **Core** para nossos módulos, centralizando todas as implementações nestes módulos.

Poderemos, eventualmente, se houver real necessidade, abrir novos módulos para situações e implementações muito específicas.