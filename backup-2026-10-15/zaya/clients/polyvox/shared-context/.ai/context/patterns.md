# Padrões do Projeto

## Organização de Código

- Organização por feature em pacotes como `produto`, `posto`, `peca`, `pedidos`, `faturamento`, `linha`, `familia`, `listabasica`, `ordemservico` e `osintegracao`.
- Dentro de cada feature, quando aplicável, usar subpacotes:
  - `actions`: entrada via Sankhya button/scheduler.
  - `application`: caso de uso/fachada da feature.
  - `infrastructure/mapper`: parse e mapeamento.
- Pacotes compartilhados:
  - `shared/config` para configuração.
  - `shared/infrastructure/http` para cliente HTTP.
  - `shared/application/port` para contratos.
  - `shared/utils` para utilitários.
- Pacote `services` preserva implementação legada e não deve ser desmontado sem migração incremental.

## Nomenclatura

- Actions de botão usam sufixo `Bt`.
- Actions agendadas usam sufixo `Sc` ou `Scheduler`.
- Serviços de aplicação usam sufixo `Service`.
- Parsers/mappers usam prefixo `Parse`.
- Models de retorno da API frequentemente usam sufixo `Get`.
- Interfaces usam prefixo `I` em pacotes legados (`IOsIntegracaoDAO`, `IOsIntegracaoService`) e também há contrato em `shared/application/port`.

## Separação de Responsabilidades

- Actions devem ser finas: receber contexto Sankhya e delegar para camada `application`/serviços.
- Casos de uso devem orquestrar fluxo e preservar compatibilidade com legado por construtor/default backward-compatible.
- `services` e DAOs concentram acesso legado ao Sankhya e API Telecontrol.
- Parsers/mappers devem concentrar conversões JSON/VO/model.
- Regras novas devem preferir pacotes por feature em vez de adicionar mais lógica em pacotes legados genéricos.

## Acesso a Dados

- Acesso via `JapeFactory.dao`, `JapeWrapper`, `DynamicVO`, `FluidCreateVO`, `FluidUpdateVO`.
- Consultas mais complexas usam `NativeSql` ou SQL em JSP.
- Sempre fechar sessões/recursos quando usados diretamente, seguindo padrões existentes com `JdbcWrapper.closeSession`.
- Campos e tabelas `AD_*` devem ser tratados como contrato com o ambiente Sankhya.

## Logs

- Há `LogUtil` gravando registros em `AD_TCLLGT`.
- Vários fluxos usam `System.out.println` com prefixos operacionais, por exemplo `[OS-INTEGRACAO][DOC]` e `[OS-INTEGRACAO][PECAS]`.
- O build declara SLF4J/Logback, mas o uso efetivo parece parcial. Preferência futura: padronizar logs sem quebrar execução no Sankhya.

## Tratamento de Erros

- Padrão atual: capturar `Exception`, registrar via `LogUtil.saveLog(e)` quando possível, e relançar ou retornar resultado conforme o caso.
- Algumas actions usam `ctx.setMensagemRetorno` para feedback ao usuário no Sankhya.
- Fluxos de integração devem incluir contexto nos erros: OS, CNPJ, referência da peça, endpoint ou rotina.

## Boas Práticas Obrigatórias

- Não remover classes em `actions/*` sem atualizar e validar metadata Sankhya.
- Manter mudanças incrementais e validar compilação entre lotes.
- Preservar compatibilidade com construtores/defaults existentes.
- Não hardcodar segredos; token e endpoints devem vir de `AD_TCLCFG`/parâmetros.
- Marcar como `A confirmar` qualquer regra não rastreável no código.
- Antes de mexer em dashboard, confirmar qual cópia é a fonte oficial.

## Anti-padrões a Evitar

- Adicionar regra de negócio diretamente em JSP quando ela pertence ao Java/Sankhya.
- Duplicar lógica entre `services`, `application` e `parsers` sem necessidade.
- Remover bridges legadas por limpeza estética.
- Substituir chamadas Jape/Sankhya por JDBC genérico sem validar transações e permissões do ambiente.
- Criar refatorações amplas sem teste/compilação e sem migração Sankhya planejada.
