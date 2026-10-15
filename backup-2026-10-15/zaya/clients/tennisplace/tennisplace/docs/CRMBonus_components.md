Resumo técnico: componentes `218_html5Component` e `219_html5Component`

Objetivo
- Esses dois componentes entregam interfaces HTML5 (iframes/modal) integradas ao Sankhya W para execução de fluxos CRM-Bonus no PDV (TGFCAB).
- Botões no PDV permitem "Consultar Bonus CRM" e "Aplicar Bonus CRM". O frontend cliente garante que a venda (NUNOTA) possua um identificador de requisição (`AD_REQHASH`), abre modais e orquestra chamadas ao HUB CRM por meio de actions Java server-side.

Arquivos principais (por componente)
- `CrmBonusController.js`
  - Renders dois botões no PDV.
  - Garante que `AD_REQHASH` exista na tabela `CabecalhoNota` (leitura via CRUDServiceProvider.loadRecords e escrita via CabecalhoNota.salvarCabecalho).
  - Abre o modal HTML5 (`modalCrmBonus.jsp` ou `modalCrmCampanhas.jsp`) incluindo `requesthash` na URL.
  - Gera hash seguro se necessário.

- `modalCrmBonus.jsp`
  - Modal multi-step (Identificação → PIN → Bônus).
  - Lida com: `user-verify`, `pin-send`, `pin-validate`, `pin-resend`, `bonus-available`, `bonus-submit`.
  - Consulta e persiste `AD_USER_ID` em `Parceiro` (TGFPAR).
  - Para `bonus-available` a Action grava em `AD_TGIBAV` — o front lê essa tabela filtrando por `BAV_REQUEST_HASH` para exibir resultados.
  - Utiliza `ActionButtonsSP.executeJava` (via `ActionButtonsSP.executeJava` service) como proxy server-side para chamar o HUB, evitando CORS e expondo credenciais do hub apenas no servidor.
  - Possui fallback para execução direta via `HttpRequest.doPost` ou `MGEServiceExecutor.call` caso necessário.
  - Persiste campanhas em `AD_CRMBONUSCAMPAIGNS` via `jx.executar` (se disponível), para armazenamento local.

- `modalCrmCampanhas.jsp`
  - Modal para selecionar campanhas e finalizar a venda (aplicar campanha).
  - Lê `AD_REQHASH` se não recebido via URL, busca campanhas com `bonus-campaigns` e finaliza via `bonus-submit` (Action IDs separados para campanhas e submit).
  - Similar a `modalCrmBonus.jsp` em estrutura, porém focado em listagem de campanhas e submissão final.

Fluxo chave
1. Botão no PDV chama `garantirRequestHash(nunota)` → lê AD_REQHASH ou gera e salva um novo.
2. Abre modal com `requesthash` na URL.
3. Modal executa `user-verify` (Action Java) passando `REQUEST_HASH` e `PAYLOAD`. A Action Java monta o corpo e chama o HUB.
4. Dependendo do retorno: pode enviar PIN (pin-send), validar PIN (pin-validate) ou seguir direto para `bonus-available`.
5. `bonus-available` normalmente grava resultado em `AD_TGIBAV` (server-side). O front lê AD_TGIBAV filtrando por `BAV_REQUEST_HASH` para exibir o `BAV_MESSAGE` e permitir `bonus-submit`.
6. `bonus-submit` aplica o bônus no HUB; a Action pode gravar em tabela e retornar mensagens de confirmação.

Técnicas e pontos importantes
- O cliente evita CORS chamando uma Action Java no Sankhya que age como proxy para o HUB.
- `REQUEST_HASH` (AD_REQHASH) funciona como correlação entre a venda no Sankhya e as chamadas ao HUB. É enviado na URL do modal e na carga enviada à Action Java (`REQUEST_HASH` field). Recentemente também foi reforçado para ser enviado no header HTTP do client Java (modificação no projeto).
- O código JS contém utilitários para lidar com respostas do Sankhya W (estrutura f0,f1... com metadata), correção de encoding (latin1→utf8), parsing de HTML retornado no `statusMessage`, e heurísticas para extrair `user_id` do HTML.
- Persistência local: `AD_CRMBONUSCAMPAIGNS` é populada via SQL direto (`jx.executar`) para manter um histórico de campanhas retornadas por uma consulta (`campaigns`), associado ao `REQUEST_HASH` e `NUNOTA`.

Diferenças entre `218` e `219`
- Conteúdo: ambos os componentes incluem os mesmos arquivos `CrmBonusController.js`, `modalCrmBonus.jsp`, `modalCrmCampanhas.jsp` e possuem implementação essencialmente idêntica.
- Observação: o `CrmBonusController.js` em ambos define `NUGDG = 218` — no componente `219_html5Component` o valor também é `218`, o que pode ser intencional ou um detalhe a ajustar caso o nuGdg deva corresponder à pasta. Verifique o nuGdg configurado no ambiente se for necessário isolar comportamento por component ID.

Segurança e segredos
- No código há uma constante `HUB_TOKEN` e `HUB_ENDPOINT` diretamente no JSP. Recomendação: não manter tokens sensíveis no projeto ou repositório; mover para configuração server-side e não expor no client JS. No documento criado aqui, o token será redigido.

Proposta: "colocar em sua base de dado todo o conhecimento"
- Como não possuo acesso a sistemas externos fora deste repositório, implementei uma base de conhecimento local dentro do repositório:
  - `docs/CRMBonus_components.md` (este arquivo): análise detalhada e instruções.
  - `docs/crm_bonus_index.json` (arquivo JSON com metadados estruturados) — contém informações extraídas (arquivos, endpoints, Action IDs, campos usados) com token redigido.

Como prosseguir/ônus operacional
- Se desejar, posso:
  - Remover o `HUB_TOKEN` dos JSPs e substituir por chamada a uma Action server-side que injete o token (mais seguro).
  - Unificar `NUGDG` entre componentes (ajustar 219 para `219` se necessário).
  - Criar testes unitários (mocks para ActionButtonsSP) que validem que o front invoca as Actions com `REQUEST_HASH` presente.

Arquivos criados
- `docs/CRMBonus_components.md` — análise textual e recomendações.
- `docs/crm_bonus_index.json` — metadados estruturados (redigido).

Feito: criei os arquivos e o conteúdo resumido. Se quiser que eu também substitua os tokens por placeholders no código fonte ou que eu mova a persistência de campanhas para uma Action server-side (removendo SQL direto do client), diga qual opção prefere e eu implemento.

