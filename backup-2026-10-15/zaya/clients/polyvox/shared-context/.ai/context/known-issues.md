# Problemas Conhecidos

## Bugs

- A confirmar. Nenhum bug funcional foi confirmado nesta análise inicial.

## Débitos Técnicos

- `PolyvoxTelecontrol/docs/telecontrol-organizacao.md` registra próximos passos de refatoração ainda pendentes:
  - mover regras de parse/mapeamento para `osintegracao/application` e `osintegracao/domain`;
  - separar persistência de OS e peças em repositórios dedicados;
  - migrar chamadas diretas de `Api` para `ITelecontrolApiClient` no legado interno;
  - evoluir lock em memória para lock transacional em banco;
  - remover bridges antigas de `actions/*` somente após atualizar metadata Sankhya.
- Uso misto de `System.out.println`, `LogUtil` e dependências SLF4J/Logback sugere falta de padronização de logging.
- Existem classes/serviços legados e nova organização por feature convivendo; manter esse estado exige cuidado nas próximas refatorações.
- Há cópia de `dashboard_os_tlc` também em `Dashboards/dashboard_os_tlc`; fonte oficial precisa ser confirmada.

## Pontos de Atenção

- `TestOsIntegracao.java` contém muitos `System.out.println` e `printStackTrace`, mas parece ser ferramenta de diagnóstico/manual.
- Dashboard possui CSS inline extenso e arquivo CSS externo com sobreposição/variação; confirmar estratégia antes de editar.
- O dashboard menciona IDs padrão de ações Java (`83`, `134`, `136`); se mudarem no Sankhya, devem ser ajustados no ambiente/configuração do dashboard.

## Riscos Operacionais

- Remover ou renomear classes usadas pela metadata Sankhya pode quebrar botões/schedulers.
- Build depende de jars locais Sankhya e pode falhar fora do ambiente preparado.
- Geração/desfazer documentos altera campos financeiros e de OS (`OSPAGA`, `NUFINGER`, `NUNOTAGER`); testar em ambiente controlado.

## A Confirmar

- Issues externas, chamados ou logs de produção não foram consultados.
- Não foi executado build/teste nesta etapa; a análise foi estática.
