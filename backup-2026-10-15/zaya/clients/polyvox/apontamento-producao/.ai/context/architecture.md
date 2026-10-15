# Arquitetura do Projeto

## Visão Geral

Projeto Java para automação de apontamento de produção em ambiente Sankhya. A aplicação principal identificada é uma ação agendada (`ScheduledAction`) que consulta apontamentos pendentes em uma view/tabela customizada, cria e confirma apontamentos de produção usando APIs Sankhya e atualiza dados de série relacionados.

O código-fonte principal parece ter sido reconstruído a partir de `.class` pelo IntelliJ/Fernflower, conforme comentário no topo da classe principal. Há também um `org.example.Main` gerado pelo template do IntelliJ, sem relação confirmada com o fluxo de negócio.

## Camadas

- Agendamento: implementação de `org.cuckoo.core.ScheduledAction` em `ApontamentoProducaoSchedule`.
- Contexto/autenticação Sankhya: criação de `AuthenticationInfo`, `ServiceContext` e propriedades de sessão Jape antes do processamento.
- Acesso a dados: uso de `JdbcWrapper`, `NativeSql`, Jape DAO (`JapeFactory.dao`) e SQL embarcado em arquivo `.sql`.
- Regras de produção: criação, alteração de quantidade, confirmação do apontamento e atualização de séries.
- Empacotamento: projeto Gradle Java que gera `ApontamentoProducao.jar`.

## Módulos Principais

- `src/main/java/br/com/zaya/apontamentoProducao/scheduleActions/ApontamentoProducaoSchedule.java`: classe principal de execução agendada.
- `src/main/java/br/com/zaya/apontamentoProducao/scheduleActions/queApo.sql`: consulta base dos apontamentos pendentes.
- `src/main/java/org/example/Main.java`: classe de exemplo gerada por IDE; uso em produção a confirmar.
- `old/`: artefatos antigos, incluindo `apontamento7.rar`, `META-INF/MANIFEST.MF` e cópia antiga do SQL.

## Fluxo Principal

1. O agendador chama `ApontamentoProducaoSchedule.onTime`.
2. A rotina cria autenticação como usuário `SUP` e inicializa `ServiceContext`.
3. O SQL `queApo.sql` é carregado via `NativeSql.loadSql`.
4. A consulta busca registros em `AD_VGFAPO` com `STATUS = 'P'`.
5. Para cada registro, a rotina configura propriedades de sessão Jape relacionadas a apontamento e série.
6. O código cria apontamento de atividade por `ApontamentoHelper.criarApontamentosAtividade`.
7. A quantidade apontada é alterada em `ApontamentoPA`.
8. O apontamento é validado/confirmado via APIs de produção Sankhya.
9. O campo customizado `AD_SEQVOL` é atualizado no apontamento PA.
10. A série é atualizada em `SerieProduto` e `TPRSERPA` conforme dados consultados.

## Integrações

- Sankhya/Jape: `EntityFacade`, `JapeSession`, `JapeFactory`, `JapeWrapper`, `DynamicVO`, `FluidCreateVO`, `FluidUpdateVO`.
- Sankhya Produção: `ApontamentoHelper`, `OperacaoProducaoSPBean`.
- Cuckoo scheduler: `ScheduledAction` e `ScheduledActionContext`.
- Banco de dados Sankhya: tabelas/views identificadas incluem `AD_VGFAPO`, `TPRAPO`, `TPRAPF`, `TPRIATV`, `TGFCAB`, `TGFCON2`, `TGFIVC`, `TGFPRO`, `SerieProduto` e `TPRSERPA`.
- XML/JDOM: montagem de parâmetros para confirmação de apontamento.

## Diagrama Textual

```txt
Agendador Cuckoo
  -> ApontamentoProducaoSchedule.onTime
    -> Autenticação/ServiceContext Sankhya
    -> queApo.sql: AD_VGFAPO STATUS='P'
    -> ApontamentoHelper cria apontamento
    -> Jape DAO atualiza ApontamentoPA
    -> OperacaoProducaoSPBean confirma apontamento
    -> NativeSql/Jape atualiza séries e observações
```

## Pontos de Atenção

- O `settings.gradle.kts` inclui o módulo `plugin`, mas a pasta `plugin` não existe no clone atual.
- O Gradle wrapper está incompleto: há `gradlew`/`gradlew.bat`, mas não há `gradle/wrapper/gradle-wrapper.jar`.
- O projeto contém `build.gradle` e `build.gradle.kts`; o arquivo Groovy `build.gradle` é o que foi ajustado e parece ser a configuração prática atual.
- Há SQL montado por concatenação em alguns pontos, incluindo mensagens de exceção; risco de erro de aspas e manutenção.
- O código usa `System.out.println` e `printStackTrace` em vez de logging estruturado.