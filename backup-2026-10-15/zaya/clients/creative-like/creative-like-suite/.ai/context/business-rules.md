# Regras de Negocio

## Entidades Principais

Confirmadas no codigo:

- `AD_TGLCAB`: usada como base para buscar `NUNEGOCIACAO` e `STATUSPROPOSTA`.
- `AD_TCRNEGCAB`: usada como fonte de status do dashboard/negociacao.
- `AD_TCRNEGITE`: usada para itens principais da negociacao.
- `AD_TGLITEART`: usada para dados/status de arte.
- `TGFPRO`: usada para produto, referencia, descricao e tipo.
- `TGFCAB`: usada na simulacao de suprimentos para localizar pedido/nota por `AD_NUNEGOCIACAO`.
- `TGFITE`: usada na simulacao de suprimentos para avaliar `PENDENTE`, `QTDNEG` e `QTDENTREGUE`.
- `TGFACT`: usada na simulacao de suprimentos como destino do acompanhamento de item cancelado.

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

### Simulacao HTML5

Processo implementado no JSP:

1. Botao JavaScript abre `/mge/html5component.mge`.
2. O iframe recebe `entryPoint=modalMomentoNegociacao.jsp`, `nuGdg`, `nunegociacao`, `codAcaoJavaMomento` e `modo=SIMULACAO`.
3. O JSP monta uma simulacao de suprimentos a partir do `nunegociacao` recebido da linha selecionada.
4. O JSP tambem mantem a visao macro do dashboard de acompanhamento da negociacao, baseada na query `AD_TGLCAB` + `AD_TCRNEGCAB`.
5. Botoes simulam validacao de itens, cancelamento possivel e preenchimento de `TGFACT`.
6. Nenhuma gravacao e executada.

### Simulacao de Suprimentos

Fluxo solicitado e simulado no JSP:

1. Partir de `AD_TGLCAB.NUNEGOCIACAO`.
2. Localizar a negociacao em `TGFCAB.AD_NUNEGOCIACAO`.
3. Obter `TGFCAB.NUNOTA`.
4. Consultar `TGFITE` por `NUNOTA`.
5. Avaliar item a item:
   - se `TGFITE.QTDENTREGUE = TGFITE.QTDNEG`, bloquear cancelamento e informar que o item ja foi entregue;
   - se `TGFITE.QTDENTREGUE <> TGFITE.QTDNEG`, permitir simulacao de `TGFITE.PENDENTE = 'N'` e gerar acompanhamento em `TGFACT`.
6. Ao cancelar item permitido, abrir popup para o usuario informar o motivo.
7. O motivo informado compoe `TGFACT.OCORRENCIAS` no formato simulado:
   `Pedido de compra possui itens cancelados! Motivo: <motivo>`.

Observacao: a simulacao atual nao possui seletor manual de cenario. Os dados mockados sao derivados do `NUNEGOCIACAO` recebido, para aproximar o teste do comportamento real de uma linha selecionada no Sankhya.

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

No JSP atual estes valores ainda sao simulados em JavaScript.

Campos simulados em `TGFACT`:

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
- Simulacao HTML5 bloqueia cancelamento de item de suprimentos quando `QTDENTREGUE = QTDNEG`.
- Simulacao HTML5 permite cancelamento simulado quando `QTDENTREGUE <> QTDNEG`, mediante motivo.

Identificadas na documentacao, a confirmar em implementacao:

- Compras: se requisicao/pedido ainda nao foi enviado ao fornecedor, pode cancelar/excluir; se enviado ou em andamento, bloquear/listar pendencia.
- Vendas: se pedido for `1001`, fazer delete; se outro, nao fazer delete e seguir. Contexto exato de `1001` deve ser confirmado.
- Vendas: se houver nota/remessa emitida, impedir cancelamento automatico.
- Producao: se OP esta criada/aberta e nao iniciada, permitir cancelamento automatico e voltar estoque local de origem.
- Producao: se OP esta iniciada/concluida, impedir cancelamento automatico e listar pendencia.
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

- Fase atual deve operar sem gravacao para simulacao visual.
- Cancelamento real ainda deve ser confirmado antes de implementar.
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
