# Chamado 8175 — Runbook de publicação em produção

## Objetivo

Publicar a regra de corte automático para pedidos de exportação, preservando o comportamento atual das TOPs que não são de exportação e permitindo reversão controlada.

Este runbook consolida os scripts SQL preparados, as validações realizadas, o incidente encontrado durante o teste ponta a ponta e as pendências necessárias antes da produção.

## Situação atual

O pacote SQL local contém:

1. uma nova view para cálculo do saldo específico da exportação;
2. uma alteração da `STP_CORTEAUTO_PLA` para utilizar esse saldo quando a versão da TOP estiver marcada como exportação;
3. scripts somente leitura de validação;
4. scripts de rollback da view e da procedure.

Durante o teste ponta a ponta, o corte foi processado até a etapa de faturamento/geração da solicitação de estoque, quando ocorreu erro na validação padrão de estoque do Sankhya. O caso foi analisado com André Siqueira e escalado para Duílio, que informou possuir uma solução e que fará a implementação.

**O pacote não deve ser publicado em produção enquanto a solução de Duílio não estiver entregue, identificada e homologada junto com estes scripts.**

## Artefatos SQL preparados

| Ordem | Arquivo | Finalidade |
|---:|---|---|
| 1 | `scripts/8175_01_deploy_skl_qtdestplapla_exp_v2.sql` | Cria ou substitui a view `SKL_QTDESTPLAPLA_EXP_V2`. |
| 2 | `scripts/8175_02_validacao_skl_qtdestplapla_exp_v2.sql` | Valida compilação, colunas, duplicidades, saldo e conversões para CX. Não realiza DML. |
| 3 | `scripts/8175_04_deploy_stp_corteauto_pla_exportacao.sql` | Cria ou substitui a `STP_CORTEAUTO_PLA` com a regra de exportação. |
| 4 | `scripts/8175_05_validacao_stp_corteauto_pla_exportacao.sql` | Valida view, procedure, TOPs, saldo, cálculo do corte e regressão do fluxo não exportação. Não realiza DML. |
| Rollback A | `scripts/8175_06_rollback_stp_corteauto_pla_exportacao.sql` | Restaura a versão anterior conhecida da `STP_CORTEAUTO_PLA`. |
| Rollback B | `scripts/8175_03_rollback_skl_qtdestplapla_exp_v2.sql` | Remove a view criada pelo chamado. Deve ser executado somente depois da restauração da procedure. |

Documento da investigação:

- `docs/os-8175-validacao-corte-automatico-exportacao.md`

### Manifesto dos scripts analisados

| Arquivo | SHA-256 |
|---|---|
| `8175_01_deploy_skl_qtdestplapla_exp_v2.sql` | `8D6368127B6AB3D5FCC6953F96EEF9B1D56D6BCA69B9AFEB17AF87B7FCBD7A86` |
| `8175_02_validacao_skl_qtdestplapla_exp_v2.sql` | `4F0AE0B68C98B06CCB32B91F3E6F9102384E651E1D6576F8DF9B1BAECA1709B9` |
| `8175_03_rollback_skl_qtdestplapla_exp_v2.sql` | `5F6764B5F2173F2C4AD81FD41F2CB8BD885031AD2E776C6AFAFC35BF50C9D5BE` |
| `8175_04_deploy_stp_corteauto_pla_exportacao.sql` | `2E916B7C69F6DFEF6FBAD2348D49B3BA64685FAB982E408AD9B3C10578650DB4` |
| `8175_05_validacao_stp_corteauto_pla_exportacao.sql` | `146900F5300F136E15149FC6A1F076E35293CDBC6CFB35572D73A2193D07AEBB` |
| `8175_06_rollback_stp_corteauto_pla_exportacao.sql` | `FC567174967EB8DB9046317798FBD3DD752776A65653AB5FBFB74225BB738798` |

## Dependências funcionais e técnicas

### Objetos e cadastros usados

- `TGFCAB`
- `TGFITE`
- `TGFTOP`
- `TGFEST`
- `TGFVOA`
- `AD_TPLTTOPCART`
- `AD_QTDESTPLAPLA`
- funções auxiliares `ACT_INT_FIELD` e demais dependências já utilizadas pela procedure original

### Configuração da TOP

A versão exata da TOP usada pela nota é obtida por:

```sql
TOP.CODTIPOPER = CAB.CODTIPOPER
AND TOP.DHALTER = CAB.DHTIPOPER
```

Somente versões de TOP com `TGFTOP.AD_EXPORTACAO = 'S'` utilizam a nova regra. TOPs com valor `N` ou nulo mantêm `AD_QTDESTPLAPLA.QTDCORTE`.

A marcação deve ser conferida para cada versão efetivamente usada no fluxo. Qualquer alteração de cadastro deve ser feita pelo mecanismo aprovado pela equipe, com registro do valor anterior e autorização funcional.

### Conversão de volume

Cada produto pendente usado pelo fluxo precisa ter exatamente uma conversão válida em `TGFVOA` para `CODVOL='CX'`, com `QUANTIDADE > 0`.

Produtos sem conversão, com conversão duplicada ou quantidade zero deixam o saldo em caixas sem cálculo válido e devem ser saneados antes da produção.

### Regra de saldo da exportação

A view implementa a seguinte composição:

```text
estoque físico da empresa 110
- reservado das empresas 110 e 2
- WMS bloqueado das empresas 110 e 2
```

Filtros aplicados ao saldo:

```sql
EST.CODEMP IN (110, 2)
AND EST.CODPARC = 0
AND EST.TIPO = 'P'
```

## Bloqueadores antes da produção

### 1. Correção de Duílio

É obrigatório obter e registrar:

- arquivo ou pacote exato da correção;
- versão anterior e nova versão;
- objeto, classe ou serviço alterado;
- instrução de instalação;
- instrução de rollback;
- evidência de build e homologação;
- confirmação de compatibilidade com a versão Sankhya do ambiente.

O repositório atual não contém um artefato identificado como a solução de Duílio para o erro de faturamento.

### 2. Valor negativo em `WMSBLOQUEADO`

No teste do produto `3108`, empresa `110`, local `107000`, controle `M0042601`, foram observados:

```text
ESTOQUE       = -24
RESERVADO     = 0
WMSBLOQUEADO  = -600
```

Com a fórmula atual:

```text
-24 - 0 - (-600) = 576
```

Assim, o bloqueio WMS negativo aumenta o saldo calculado para `576`. Esse valor coincidiu com a quantidade enviada no item que falhou na validação padrão de estoque.

Antes da produção, a equipe funcional/técnica deve confirmar uma das opções:

1. o valor negativo é válido e a fórmula atual está correta;
2. o dado está inconsistente e deve ser corrigido por processo suportado;
3. a regra da view precisa ser alterada e novamente homologada.

Não aplicar `ABS`, `GREATEST` ou correção direta em `TGFEST` sem definição funcional, autorização e plano de rollback.

### 3. Teste ponta a ponta após a correção

O fluxo completo precisa ser reexecutado em homologação:

1. pedido de exportação;
2. cálculo do saldo;
3. execução do corte automático;
4. confirmação;
5. faturamento;
6. geração da solicitação de estoque;
7. validação dos itens, locais, quantidades, volumes e vínculos;
8. cancelamento e reprocessamento seguro, quando aplicável.

O teste deve terminar sem `EstoqueException`, sem documento parcial e sem divergência de estoque.

## Preparação da janela de produção

### Aprovações necessárias

- aprovação funcional da regra de saldo e corte;
- aprovação técnica da solução de Duílio;
- aceite da homologação ponta a ponta;
- autorização para substituir a procedure existente;
- autorização para criar a nova view;
- responsável definido para executar e validar;
- janela e plano de comunicação definidos.

### Backup obrigatório antes do deploy

Capturar o DDL efetivo do ambiente imediatamente antes da alteração. O ZIP analisado é um retrato anterior e não substitui o backup da janela.

```sql
SELECT DBMS_METADATA.GET_DDL('PROCEDURE', 'STP_CORTEAUTO_PLA')
FROM DUAL;
```

Confirmar se a view já existe:

```sql
SELECT OBJECT_NAME, OBJECT_TYPE, STATUS
FROM USER_OBJECTS
WHERE OBJECT_NAME = 'SKL_QTDESTPLAPLA_EXP_V2';
```

Se existir, capturar seu DDL antes de executar `CREATE OR REPLACE`:

```sql
SELECT DBMS_METADATA.GET_DDL('VIEW', 'SKL_QTDESTPLAPLA_EXP_V2')
FROM DUAL;
```

Também registrar:

- versões das TOPs e valores de `AD_EXPORTACAO`;
- conversões `TGFVOA` dos produtos do teste;
- saldo detalhado da `TGFEST`;
- versão do pacote Java antes da correção de Duílio;
- objetos inválidos existentes antes da implantação.

## Ordem de implantação

### Etapa 1 — Instalar a view

Executar:

```text
scripts/8175_01_deploy_skl_qtdestplapla_exp_v2.sql
```

Não continuar se houver erro de compilação.

### Etapa 2 — Validar a view

Executar:

```text
scripts/8175_02_validacao_skl_qtdestplapla_exp_v2.sql
```

Critérios mínimos:

- objeto com `STATUS='VALID'`;
- nenhuma linha em `USER_ERRORS`;
- nenhuma duplicidade por nota, sequência, produto, empresa e local;
- nenhuma conversão inválida para os produtos que entrarão no fluxo;
- saldo conferido manualmente com casos positivos, zero e negativos;
- tratamento de `WMSBLOQUEADO` negativo previamente aprovado.

### Etapa 3 — Instalar a procedure

Executar:

```text
scripts/8175_04_deploy_stp_corteauto_pla_exportacao.sql
```

A procedure depende da view. Nunca inverter as etapas 1 e 3.

### Etapa 4 — Validar a procedure

Executar:

```text
scripts/8175_05_validacao_stp_corteauto_pla_exportacao.sql
```

Critérios mínimos:

- procedure e view com `STATUS='VALID'`;
- nenhuma linha em `USER_ERRORS`;
- TOPs de exportação marcadas na versão correta;
- nenhum item de exportação pendente com `EXP.ESTDISPVOLPAD IS NULL`;
- cálculo em UN e exibição em CX conferidos;
- consulta de regressão para TOP não exportação sem divergências.

O exemplo documentado no script espera:

```text
QTDNEG = 2400 UN
ESTDISPVOLPAD_EXP = 2232 UN
QTDCORTE = 168 UN
Fator CX = 6
Exibição = 28 CX
```

Se a nota de exemplo `5085355` não existir em produção, substituir somente na consulta de validação por uma nota controlada e registrar a evidência.

### Etapa 5 — Instalar a correção de Duílio

Executar conforme o procedimento entregue por Duílio. A ordem relativa entre o pacote Java e os scripts SQL deve ser confirmada por ele antes da janela.

Não improvisar substituição de JAR, classe ou serviço sem pacote versionado, backup e instrução de rollback.

### Etapa 6 — Smoke test controlado

Executar com uma nota controlada:

1. conferir TOP e sua versão;
2. conferir item em UN e conversão para CX;
3. registrar saldo anterior;
4. executar o corte;
5. conferir `QTDCONFERIDA` calculada;
6. confirmar e faturar;
7. validar a solicitação de estoque gerada;
8. conferir saldo, reserva, WMS, financeiro, fiscal e vínculos;
9. confirmar ausência de registros ou documentos órfãos;
10. testar repetição segura ou impedir duplicidade.

## Critérios de aceite para liberar o uso

- view e procedure válidas;
- solução de Duílio instalada e versionada;
- nenhuma exceção de estoque durante o faturamento do cenário controlado;
- corte correto em UN e apresentação correta em CX;
- local `107000` aplicado no processo validado;
- fluxo não exportação sem regressão;
- confirmação, faturamento e solicitação concluídos;
- estoque, reserva e WMS consistentes antes e depois;
- rollback técnico disponível e testado em homologação;
- evidências anexadas à OS.

## Rollback

### Ordem obrigatória

1. interromper novas execuções do processo;
2. reverter a solução de Duílio para a versão anterior;
3. restaurar a `STP_CORTEAUTO_PLA` usando preferencialmente o DDL capturado na própria janela;
4. validar a procedure restaurada;
5. somente depois, remover ou restaurar a view `SKL_QTDESTPLAPLA_EXP_V2`;
6. restaurar configurações de TOP alteradas, se houver;
7. validar objetos inválidos e executar teste do fluxo anterior.

O arquivo `8175_06_rollback_stp_corteauto_pla_exportacao.sql` representa a versão anterior conhecida, mas não é byte a byte igual ao DDL exportado analisado e não contém a palavra-chave `EDITIONABLE`. Por isso, o DDL capturado imediatamente antes do deploy deve ser a fonte principal do rollback.

Se a view não existia antes do chamado, executar após a restauração da procedure:

```text
scripts/8175_03_rollback_skl_qtdestplapla_exp_v2.sql
```

Se a view já existia, restaurar o DDL capturado em vez de executar `DROP VIEW`.

## Evidências a anexar à OS

- DDL anterior e posterior da view e da procedure;
- resultado de `USER_OBJECTS` e `USER_ERRORS`;
- resultados completos dos scripts 02 e 05;
- TOP, versão e `AD_EXPORTACAO` do cenário;
- conversão `TGFVOA` dos produtos;
- saldo detalhado da `TGFEST` antes e depois;
- versão e hash do pacote entregue por Duílio;
- log do smoke test e do teste ponta a ponta;
- documentos gerados e seus vínculos;
- decisão formal sobre `WMSBLOQUEADO` negativo;
- aceite funcional e técnico.

## Status de prontidão

**Não pronto para produção na situação atual.**

Pendências impeditivas:

1. correção de Duílio ainda não localizada no workspace;
2. teste ponta a ponta ainda não concluído após a correção;
3. regra para `WMSBLOQUEADO` negativo ainda não confirmada;
4. scripts e documentação do chamado estão não rastreados no Git;
5. não houve compilação dos objetos no banco de homologação nesta análise local;
6. rollback deve ser baseado no DDL capturado na janela.
