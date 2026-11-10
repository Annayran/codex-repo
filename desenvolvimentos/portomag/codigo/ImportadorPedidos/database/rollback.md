# Rollback V1.0

1. Desabilitar o novo dashboard e a acao ReceberPedidoAction; revogar os acessos de homologacao.
2. Preservar/exportar os registros ZPM pelo procedimento de backup aprovado. Nao apagar recebimentos para permitir reimportacao.
3. Restaurar o cadastro anterior de modulos/botoes, se tiver sido alterado durante a homologacao.
4. Para desinstalacao completa, somente em base isolada, com backup restauravel e aprovacao: remover primeiro a ligacao/tela detalhe ZPM_PEDITE e depois o mestre ZPM_PEDREC pelo Construtor de Telas. Conferir dependencias antes de remover objetos fisicos.
5. As constraints ZPM_PEDITE_FK1 e ZPM_PEDREC_UK1 pertencem exclusivamente a esta instalacao. A chave primaria composta de ZPM_PEDITE (`IDPED`, `SEQEXT`) garante a unicidade dos itens. Nao remover essas garantias enquanto a rotina estiver habilitada.

Nenhum objeto AD_PEDREC, AD_TGLCAB, LancamentoOS ou tabela nativa foi modificado. Esta versao nao gera documentos comerciais e portanto nao exige cancelamento de TGFCAB/TGFITE. Se uma etapa futura gerar documentos, utilizar o fluxo oficial de cancelamento e tratar estoque, financeiro e fiscal.
