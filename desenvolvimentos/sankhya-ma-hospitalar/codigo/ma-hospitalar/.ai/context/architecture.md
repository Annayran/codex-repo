# Arquitetura do Projeto

## Visão Geral

Projeto Gradle Java 8 para Sankhya. A integração Genesis mantém a Base Instalada
por série usando ação agendada, base legada importada, conciliação com cadastros
Sankhya, controle em `AD_GENMOVEST` e dashboard JSP analítico.

## Camadas

- `domain`/`dto`: domínio e contratos JSON.
- `repository`: Oracle/Sankhya.
- `service`: payload e orquestração.
- `client`: HTTP Genesis.
- `schedule`/`action`: agenda e reprocessamento.
- `webapp/genesis`: dashboard somente leitura.

## Fluxo Principal

`AD_VW_GENESIS_ESTOQUE` -> resolve produto por `TGFPRO.AD_IDEXTERNO` na
`AD_GENMODELO` -> resolve cliente/setor/item na Base Instalada ->
GET/POST/PUT de item-material -> atualiza `AD_GENITEMMAT` e `AD_GENMOVEST` ->
dashboard.

## Integrações

O fluxo agendado usa somente `/itens-materiais/`. Cliente, setor e modelo devem
estar previamente importados e conciliados na Base Instalada. Configuração na
Central `NUPAR=1`; token fora do projeto.
