# Problemas Conhecidos

## Pendencias Tecnicas

- Publicar o JAR mais recente no modulo `[ZAYA.IT] - Momento Negociacao`.
- Reimportar o ZIP HTML5 mais recente.
- Confirmar se o ID `304` aponta para `br.com.zaya.like.Actions.CancelarDocumentoNegociacaoAction`.
- Homologar fluxo completo com `TGFITE.PENDENTE = 'S'` para Faturamento/Suprimento e `TPRIPROC.STATUSPROC = 'S'` para Producao.
- Homologar tentativa repetida de cancelamento para confirmar que nao duplica `TGFACT`.
- Confirmar no ambiente de homologacao se `TPRIPROC.AD_NUNEGOCIACAO` e `TPRIPROC.STATUSPROC` estao acessiveis pelo usuario do modulo.
- Confirmar se `TGFACT.OCORRENCIAS` deve continuar com texto padrao de pedido de compra ou variar por documento.
- Definir se `Notificar areas`, `Registrar pendencia` e `Solicitar aprovacao` terao backend real apos estabilizar a regra prioritaria.
- Em `modo=OPERACIONAL`, esses utilitarios ficam desabilitados quando nao houver Action ID configurado na engrenagem.
- Validar em homologacao se a consulta real de Mov. Interna por `TGFCAB.AD_NUNEGOCIACAO` retorna a nota esperada quando houver mais de uma nota vinculada; hoje o JSP usa a mais recente por `NUNOTA DESC`.
- Nao ha testes automatizados.

## Erros Ja Encontrados

- `clientconfirm` no `ActionButtonsSP.executeJava`: ocorreu ao usar `contexto.confirmarSimNao(...)`; nao usar confirmacao nativa nessa Action.
- `Ja existe uma transacao em andamento`: ocorreu ao abrir `JapeSession.open()`/`execWithTX(...)`; usar a transacao do Sankhya.
- Icones aparecendo como texto ou `?`: indica ZIP/JSP antigo ou tentativa de usar fonte externa; usar SVG inline.
- `class file version 55`: indica JAR Java 11; compilar para Java 8 (`major version: 52`).
- `illegal character: '\ufeff'`: BOM UTF-8 em arquivo Java; salvar Java sem BOM.
- `ORA-00942` em Producao: primeiro foi identificado por uso incorreto de `TPCIPROC`; a tabela correta informada e `TPRIPROC`. A versao atual usa `TPRIPROC` e ainda trata ausencia/permissao como retorno controlado.

## Pontos de Atencao

- `CODTIPOPER` nao deve limitar Pedido de Venda; filtro correto e `TGFCAB.TIPMOV = 'P'`.
- Item com saldo (`QTDENTREGUE <> QTDNEG`) pode ser cancelavel mesmo quando `TGFITE.PENDENTE = 'N'`; o cancelamento marca `PENDENTE = 'S'` e grava `TGFACT`.
- O retorno da Action Java e fonte final quando a tela estiver defasada.
- O workspace `C:\zy-tools\workspace\Creative Like` e espelho para homologacao; cuidado para nao subir artefato antigo.

## Riscos Operacionais

- Executar Action real em producao antes da homologacao.
- Subir JAR ou ZIP antigo.
- Suspender producao indevidamente se houver processo em `TPRIPROC` vinculado pela negociacao que nao deva ser tratado por esta rotina.
- Status macro do acompanhamento esta informativo na regra prioritaria atual; se voltar a ser trava, sera necessario revalidar textos por cliente.
