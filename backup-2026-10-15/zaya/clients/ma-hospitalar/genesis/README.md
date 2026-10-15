# MA Hospitalar - Integracao Sankhya x Genesis

Projeto Gradle Java 8 para customizacao Sankhya que integra movimentos de estoque controlados por serie com a Base Instalada da Genesis.

## Objetivo

Enviar automaticamente para a Genesis somente movimentos de entrada e saida de estoque controlados por serie, conforme TOPs parametrizadas na Central de Parametros.

## Estrutura

- `.ai/context`: contexto vivo para IA e decisoes do projeto.
- `docs`: planejamento tecnico e checklist de homologacao.
- `sql`: scripts base para tabela de controle, VIEW e campos adicionais.
- `src/main/java`: codigo Java principal.
- `src/sankhya/java`: ponto reservado para fontes/recursos especificos Sankhya, conforme padrao Gradle local.
- `src/test/java`: testes unitarios.

## Build

O projeto usa Gradle com Java 8.

```powershell
gradle clean build
```

Dependencias locais Sankhya esperadas em:

```txt
C:/zy-tools/modulos-sankhya/Dependencias
```

## Componentes previstos

- VIEW `AD_VW_GENESIS_ESTOQUE`.
- Tabela de controle `AD_GENMOVEST`.
- Dashboard "Gestao Estoque por Serie" ou "Gestao Base Instalada".
- Acao agendada `IntegracaoGenesisScheduleAction`.
- Cliente REST `GenesisHttpClient`.
- DTO `EstoqueGenesisDTO`.
- Builder de payload `PayloadBuilder`.
- Repository `MovimentoEstoqueRepository`.

## Seguranca

Nao gravar URL real, token, senha ou credenciais em codigo, SQL, logs, dashboard ou documentacao versionada. Usar cofre/parametro seguro do ambiente Sankhya.

## Status

Estrutura inicial criada com base nos documentos de escopo e IntPDD. Ainda falta conectar a acao agendada ao SDK Sankhya, validar banco real e homologar contrato Genesis.

