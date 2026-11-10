# Decisoes Tecnicas

## Decisoes Ativas

### Contexto IA no repositorio

**Decisao:** Manter contexto tecnico em `.ai/context`, com apoio de `AGENTS.md`, `.ai/sankhya`, `.ai/prompts` e `.ai/scripts`.

**Motivo:** Facilitar retomada futura sem depender do historico da conversa.

**Impacto:** Antes de continuar, ler `AGENTS.md`, `.ai/context` e `.ai/sankhya`.

### JSP/HTML5 como painel operacional

**Decisao:** Usar o JSP/HTML5 como painel de validacao, selecao de documento, exibicao de regras e chamada da Action Java.

**Motivo:** O usuario precisa entender documentos, itens, saldo, bloqueios, areas e motivo antes da gravacao real.

**Impacto:** O JSP antecipa a UX e a validacao visual; a regra transacional oficial fica no Java.

### Pedido de Venda por AD_VGLDOC sem limitar TOP

**Decisao:** Buscar Pedido(s) de Venda por `AD_VGLDOC` + `TGFCAB.TIPMOV = 'P'`, sem limitar `CODTIPOPER`.

**Motivo:** A negociacao pode gerar pedidos com TOPs diferentes.

**Impacto:** `CODTIPOPER` pode ser exibido, mas nao deve filtrar a busca.

### Documento selecionavel

**Decisao:** Tratar Pedido de Venda como lista e permitir selecionar o `NUNOTA` ativo.

**Motivo:** Uma negociacao pode ter mais de um Pedido de Venda; a Nota Mov. Interna tende a ser unica.

**Impacto:** A tabela de itens sempre opera sobre o documento selecionado. A tela nao deve abrir os itens automaticamente; primeiro exibe documentos, depois carrega itens apos clique na linha.

### Uma Action para lote e item

**Decisao:** Usar `CancelarDocumentoNegociacaoAction` para lote e item individual.

**Motivo:** O mesmo contrato atende os dois casos; `P_SEQUENCIA` define item individual.

**Impacto:** O botao individual e o lote usam a Action configurada na engrenagem.

### Action chamada pelo JSP sem confirmacao/transacao propria

**Decisao:** Nao usar `contexto.confirmarSimNao(...)`, `JapeSession.open()` ou `execWithTX(...)` na Action chamada pelo `ActionButtonsSP.executeJava`.

**Motivo:** Ja causou erros `clientconfirm` e `Ja existe uma transacao em andamento`.

**Impacto:** Confirmacao fica no modal; a Action usa o contexto transacional do Sankhya.

### Cancelamento mantem TGFITE.PENDENTE = 'S'

**Decisao:** Manter/atualizar `TGFITE.PENDENTE = 'S'` no cancelamento.

**Motivo:** A leitura operacional desejada e manter o item pendente/tratado, nao encerrado como `N`.

**Impacto:** A trava de recancelamento nao depende de `PENDENTE`; depende de `TGFACT.AD_ITEMCANCELADO = 'S'`.

### Saldo define item apto

**Decisao:** Considerar item apto quando `QTDENTREGUE <> QTDNEG` e nao existir cancelamento anterior em `TGFACT`, mesmo se `TGFITE.PENDENTE` estiver `N`.

**Motivo:** O processo operacional deve marcar `PENDENTE = 'S'` como resultado do cancelamento, nao exigir esse valor como pre-condicao.

**Impacto:** JSP e Java usam a mesma regra: saldo restante libera popup/Action; item totalmente entregue ou ja tratado bloqueia.

### Idempotencia por TGFACT

**Decisao:** Considerar item ja cancelado quando existir `TGFACT.AD_ITEMCANCELADO = 'S'` para `NUNOTA + SEQUENCIA`, e revalidar isso no Java antes de cada gravacao.

**Motivo:** Evitar duplicidade por clique duplo, reabertura de tela, concorrencia ou tentativa de repetir processo ja tratado.

**Impacto:** A Action nao cria novo acompanhamento se o item ja foi tratado. O retorno separa cancelados agora, ja tratados, ignorados por trava e avisos.

### Cancelamento direto por frente

**Decisao:** Na regra prioritaria atual, permitir cancelar Faturamento, Suprimento e Producao diretamente pela Action operacional.

**Motivo:** A entrega de 30/06 precisa focar no fluxo funcional e testavel das tres frentes principais.

**Impacto:** `PV`/Faturamento e `MI`/Suprimento continuam com travas por item entregue e `TGFACT.AD_ITEMCANCELADO = 'S'`; Producao usa trava por `TPRIPROC.STATUSPROC = 'S'`. O status macro do acompanhamento fica informativo.

### Suspensao de producao

**Decisao:** Producao e uma operacao propria: pode ser cancelada por botao dedicado ou dentro do cancelamento completo.

**Motivo:** A producao esta em tabela/processo diferente dos itens de Faturamento/Suprimento.

**Impacto:** Atualiza `TPRIPROC.STATUSPROC = 'S'` somente para processos ainda nao suspensos. Cancelar `PV`/`MI` nao suspende Producao automaticamente.

### Producao em TPRIPROC

**Decisao:** Usar `TPRIPROC.AD_NUNEGOCIACAO` para localizar producao e `TPRIPROC.STATUSPROC = 'S'` para marcar suspensao.

**Motivo:** Errata funcional confirmou que a tabela correta de producao e `TPRIPROC`, nao `TPCIPROC`.

**Impacto:** JSP e Java consultam/atualizam `TPRIPROC`; se o objeto nao estiver acessivel, o card de Producao fica vermelho e a Action retorna mensagem controlada.

### Icones independentes de fonte externa

**Decisao:** Usar SVG inline no padrao Material, nao fonte Material Icons.

**Motivo:** O iframe HTML5 do Sankhya pode nao carregar fonte externa e exibir texto cru.

**Impacto:** O JSP nao deve mostrar `?`, `settings` ou `check_circle` como texto.

### IDs de Actions por engrenagem

**Decisao:** Manter engrenagem no modal para configurar IDs de Actions por ambiente.

**Motivo:** IDs mudam entre ambientes.

**Impacto:** IDs ficam no `localStorage`; botoes sem ID configurado atuam como log/simulacao.

### UX progressiva por documento

**Decisao:** Mostrar acompanhamentos e itens somente quando fizerem sentido operacional.

**Motivo:** Reduzir ruido visual e deixar claro que a operacao ocorre sobre o documento clicado.

**Impacto:** `TGFACT` aparece somente quando houver registro real/simulado para o documento selecionado; cards de area so sao clicaveis quando exigem notificacao/liberacao.

### Modo operacional sem sucesso local

**Decisao:** Em `modo=OPERACIONAL`, apos sucesso da Action Java, o JSP nao altera os itens localmente como simulacao; ele recarrega a tela para reconsultar `TGFITE` e `TGFACT`.

**Motivo:** A Action Java e a fonte oficial da gravacao. A tela nao deve mostrar cancelado se a base ainda nao refletiu isso.

**Impacto:** O usuario ve o retorno da Action no log e, em seguida, a tela revalida os dados reais.

### Sem mock em operacional

**Decisao:** Em `modo=OPERACIONAL`, o JSP nao deve criar documentos mockados quando nao houver documento real/parametro confiavel.

**Motivo:** Nesta fase de homologacao, a tela precisa representar a negociacao real selecionada.

**Impacto:** Se a busca real nao localizar documento, a tela orienta selecionar/validar origem em vez de exibir documento inventado.
