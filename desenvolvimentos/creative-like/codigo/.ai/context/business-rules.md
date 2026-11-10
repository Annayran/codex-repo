# Regras de Negocio

## Entidades Principais

Confirmadas no codigo:

- `AD_TGLCAB`: usada como base para buscar `NUNEGOCIACAO` e `STATUSPROPOSTA`.
- `AD_TCRNEGCAB`: usada como fonte de status do dashboard/negociacao.
- `AD_TCRNEGITE`: usada para itens principais da negociacao.
- `AD_TGLITEART`: usada para dados/status de arte.
- `TGFPRO`: usada para produto, referencia, descricao e tipo.
- `TGFCAB`: usada no fluxo operacional para localizar pedido/nota por `AD_NUNEGOCIACAO` e dados do documento.
- `TGFITE`: usada no fluxo operacional para avaliar `PENDENTE`, `QTDNEG`, `QTDENTREGUE`, saldo e item cancelavel.
- `TGFACT`: usada como acompanhamento/auditoria do item cancelado e trava de recancelamento por `AD_ITEMCANCELADO = 'S'`.

Confirmadas na documentacao:

- Proposta/negociacao.
- Ordem de producao.
- Requisicao/pedido de compra.
- Pedido de venda.
- Movimentacoes internas/estoque.
- Financeiro.
- Arte/personalizacao.
- Notificacoes e aprovacoes.

## Processos

### Consulta do Momento da Negociacao

Processo implementado no codigo Java:

1. Usuario seleciona uma linha no Sankhya.
2. A acao le `NUNEGOCIACAO`.
3. O DAO busca dados do cabecalho em `AD_TGLCAB` e `AD_TCRNEGCAB`.
4. O DAO busca itens principais em `AD_TCRNEGITE`, `TGFPRO` e `AD_TGLITEART`.
5. O servico monta HTML com cards de status e resumo dos itens.
6. A acao retorna o HTML para o usuario.

### Painel HTML5 / JSP

Processo implementado no JSP/Java:

1. Botao JavaScript abre `/mge/html5component.mge`.
2. O iframe recebe `entryPoint=modalMomentoNegociacao.jsp`, `nuGdg`, `nunegociacao`, IDs de Actions e `modo`.
3. O JSP monta o painel operacional a partir do `nunegociacao` recebido da linha selecionada.
4. O JSP consulta Pedido(s) de Venda reais pela `AD_VGLDOC` + `TGFCAB.TIPMOV = 'P'`.
5. O JSP consulta itens por `TGFITE.NUNOTA` do documento selecionado e valida saldo, entrega e cancelamento anterior.
6. Em `modo=OPERACIONAL`, o cancelamento chama `CancelarDocumentoNegociacaoAction` via `ActionButtonsSP.executeJava`.
7. A gravacao real e executada no Java.

### Regra de Suprimentos / Cancelamento

Regra prioritaria atual para entrega de 30/06:

- Permitir cancelar Faturamento, Suprimento e Producao.
- Faturamento e Pedido de Venda (`PV`) localizado por `AD_VGLDOC` + `TGFCAB.TIPMOV = 'P'`.
- Suprimento e Movimentacao Interna (`MI`) localizada por `TGFCAB.AD_NUNEGOCIACAO`.
- Producao e processo localizado por `TPRIPROC.AD_NUNEGOCIACAO`.
- Cancelamento por item e por documento se aplica a `PV`/`MI`, pois usam linhas em `TGFITE`.
- Cancelamento exclusivo de Producao usa botao proprio, pois o processo esta em `TPRIPROC`.
- Cancelamento completo executa o que estiver apto nas tres frentes.
- Nao permitir repeticao: itens usam trava `TGFACT.AD_ITEMCANCELADO = 'S'`; Producao usa `TPRIPROC.STATUSPROC = 'S'`.

Fluxo atual:

1. Partir de `AD_TGLCAB.NUNEGOCIACAO`.
2. Localizar Pedido(s) de Venda pela `AD_VGLDOC`, filtrando `TGFCAB.TIPMOV = 'P'`.
3. Localizar Nota Mov. Interna por `TGFCAB.AD_NUNEGOCIACAO`.
4. Consultar `TGFITE` por `NUNOTA` do documento selecionado.
5. Avaliar item a item:
   - se `TGFITE.QTDENTREGUE = TGFITE.QTDNEG`, bloquear cancelamento e informar que o item ja foi entregue;
   - se existe `TGFACT.AD_ITEMCANCELADO = 'S'` para `NUNOTA + SEQUENCIA`, mostrar como cancelado anteriormente e bloquear novo cancelamento;
   - se `TGFITE.QTDENTREGUE <> TGFITE.QTDNEG` e nao ha cancelamento anterior, permitir cancelamento mediante motivo.
6. Ao cancelar item permitido, abrir popup para o usuario informar o motivo.
7. A rotina real mantem/atualiza `TGFITE.PENDENTE = 'S'`.
8. O motivo informado compoe `TGFACT.OCORRENCIAS` no formato:
   `Pedido de compra possui itens cancelados! Motivo: <motivo>`.
9. A auditoria/trava de cancelamento fica em `TGFACT.AD_ITEMCANCELADO = 'S'`.

### Regra de Producao

Confirmada no ciclo atual:

- Processo de producao e localizado por `TPRIPROC.AD_NUNEGOCIACAO = NUNEGOCIACAO`.
- Suspensao do processo: `TPRIPROC.STATUSPROC = 'S'`.
- A suspensao ocorre por botao exclusivo de Producao ou pelo cancelamento completo.
- Se todos os processos ja estiverem com `STATUSPROC = 'S'`, a rotina bloqueia repeticao e informa que ja foi tratado.

### Matriz Visual no JSP

O modal HTML5/JSP deve deixar explicito:

- Suprimento controla Movimentacao Interna.
- Faturamento controla Pedido de Venda.
- Producao controla processo da negociacao em `TPRIPROC`.
- Cancelar tudo significa executar somente o que estiver permitido pela area/documento.
- O que estiver entregue, concluido ou finalizado deve seguir para notificacao/liberacao operacional.

### Acompanhamento da Negociacao

A tela deve preservar a leitura macro do dashboard existente, usando como referencia a consulta que relaciona:

- `AD_TGLCAB.NUNEGOCIACAO`
- `AD_TCRNEGCAB.NUNEGOCIACAO`
- `STATUS_SUPRIMENTO`
- `STATUS_LAYOUT`
- `STATUS_PRODUCAO_FINAL`
- `STATUS_FATURAMENTO`
- `STATUS_ENTREGA`

No JSP atual estes valores permanecem simulados, mas a origem real esperada e a query de acompanhamento da negociacao.

### Documentos Relacionados da Negociacao

Para aproximar o rastro do fluxo real, a tela deve considerar a `AD_VGLDOC` como view de documentos gerados/relacionados a negociacao.

Regra visual atual:

- partir do `NUNEGOCIACAO` selecionado;
- buscar documentos relacionados na `AD_VGLDOC`;
- considerar apenas documentos que sejam pedido de venda, ou seja, `TIPMOV = 'P'`;
- exibir o documento como "Pedido de Venda";
- usar o `NUNOTA` encontrado como Nro. Unico do documento relacionado.

No JSP atual a busca de Pedido de Venda ja deve refletir a linha selecionada e os dados reais encontrados por `AD_VGLDOC`/`TGFCAB`.

Campos gravados/simulados em `TGFACT` conforme modo:

- `NUNOTA`
- `SEQUENCIA`
- `DHOCOR`
- `HRACT`
- `CODUSU`
- `REFERENCIA = 'R'`
- `OCORRENCIAS`
- `DIGITADO = 'S'`
- `AD_ITEMCANCELADO = 'S'`

### Cancelamento de Proposta

Regras identificadas na documentacao Word, mas ainda nao implementadas como cancelamento real neste repositorio:

- Ao acionar cancelamento, validar automaticamente areas/processos relacionados.
- Se houver impedimento, apresentar checklist com area, acao necessaria e responsavel.
- Notificar areas responsaveis quando houver pendencias.
- Permitir aprovacao manual por gestor em casos excepcionais.
- Registrar tentativas, pendencias, aprovacoes e historico para auditoria.

## Validacoes

Confirmadas no codigo:

- Acao Java exige ao menos uma linha selecionada.
- Linha selecionada deve conter `NUNEGOCIACAO`.
- DAO exige que `NUNEGOCIACAO` exista em `AD_TGLCAB`.
- Itens retornados sao filtrados por `CLASSIFICACAO_PRODUTO = 'Principal'`.
- JSP/Java bloqueiam cancelamento quando `QTDENTREGUE = QTDNEG`.
- JSP/Java bloqueiam recancelamento quando ja existe `TGFACT.AD_ITEMCANCELADO = 'S'`.
- JSP/Java permitem cancelamento quando `QTDENTREGUE <> QTDNEG` e nao ha cancelamento anterior, mediante motivo.

Identificadas na documentacao, a confirmar em implementacao:

- Compras: se requisicao/pedido ainda nao foi enviado ao fornecedor, pode cancelar/excluir; se enviado ou em andamento, bloquear/listar pendencia.
- Vendas: se pedido for `1001`, fazer delete; se outro, nao fazer delete e seguir. Contexto exato de `1001` deve ser confirmado.
- Vendas: se houver nota/remessa emitida, impedir cancelamento automatico.
- Producao: se houver processo pendente no acompanhamento, suspender `TPRIPROC.STATUSPROC = 'S'`.
- Producao: se OP esta criada/aberta e nao iniciada, demais efeitos de estoque ainda precisam ser confirmados.
- Producao: se OP esta iniciada/concluida, regra fina de bloqueio/listagem de pendencia ainda precisa ser confirmada.
- Arte: se qualquer item estiver liberado para producao, exibir erro/bloquear.
- Movimentacoes internas: desfazer automaticamente apenas se possivel; se ja feitas ou nao reversiveis, listar pendencia ou nao executar acao.
- Financeiro: se houver baixa/pagamento vinculado, listar pendencia.

## Calculos

Nao ha calculos financeiros ou quantitativos complexos implementados.

Confirmado no codigo:

- Cards recebem cores por texto de status:
  - verde para status que contenham `COMPLETO`, `CONCL`, `FINALIZ`, `ENTREG`;
  - amarelo para `PEND`, `ANDAMENTO`, `PARCIAL`, `AGUARD`;
  - vermelho para `BLOQUE`, `CANCEL`, `ATRAS`, `ERRO`;
  - preto/cinza para demais casos.

## Restricoes

- A fase atual esta em homologacao com possibilidade de gravacao real via Action Java quando `modo=OPERACIONAL`.
- O JSP deve manter a capacidade de simular/validar visualmente antes de chamar o Java.
- Botao de cancelamento real deve ser restrito a perfis autorizados, conforme documentacao.
- Aprovacoes manuais excepcionais devem ser restritas a gestor/supervisor. A confirmar como isso sera configurado no Sankhya.

## Regras a Confirmar

- Tabelas definitivas para OPs, compras, vendas, movimentos internos, financeiro e notificacoes.
- Como identificar pedido de venda `1001` e qual entidade/campo representa esse codigo.
- Regras finais de permissoes do botao.
- Como registrar auditoria/historico de cancelamento.
- Se `DHOCOR` deve ser gravado com data zerada (`00:00`) ou timestamp completo no backend real.
- Regra definitiva para obtencao de `CODUSU` no backend real.
- Se `TGFACT.REFERENCIA` deve ser sempre `R` para este processo.
- Se o escopo atual realmente e cancelamento de proposta ou se ha demanda paralela de Recebimento Fiscal/Nota de Retorno de Industrializacao. Este segundo tema nao aparece nos arquivos do repositorio atual.
