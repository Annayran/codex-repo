# Implantacao V1.5 — homologacao pendente

## Modelo e compatibilidade

Personalizacao HTML5/JSP e modulo Java legado, Java 8. Nao e Add-on Studio; nao usa anotacoes ou XSD de outro modelo. Metadados novos baseados no export AD_PEDREC 4.35b811 e na topologia mestre/detalhe comprovada pelo export CENTPARAM 4.36b126. A geracao local valida XML, nomes e referencias, mas NAO comprova aceitacao pelo importador Sankhya.

O pacote nao e exportado do servidor: e um candidato de implantacao gerado pelo projeto. O importador da PortoMag rejeitou a primeira versao sem `exportInfo`. O ZIP corrigido recebe somente esse bloco do export nativo AD_PEDREC da propria PortoMag 4.35b811; seus valores sensiveis ficam restritos ao artefato local em `out/`, ignorado pelo Git. O XML fonte continua sanitizado.

## Ordem

1. Confirmar ambiente, versao, permissoes e backup restauravel. Verificar inexistencia de ZPM_PEDREC e ZPM_PEDITE e aprovar o prefixo ZPM. Nenhum arquivo foi importado automaticamente.
2. Revisar `metadata/metadata.xml` e `docs/DICIONARIO.md`. Gerar o pacote informando um export nativo da mesma PortoMag 4.35b811 em `--export-info-from`. Importar `out/Metadados_ZPM_PEDIDOS_V1.1_HOMOLOGACAO.zip` somente na base isolada. Nao versionar nem compartilhar o ZIP sem sanitizacao, pois o `exportInfo` carrega metadados de conexao.
3. Validar PK automatica `ID` no mestre e PK composta `IDPED + SEQEXT` no detalhe, campos texto ate 2.000 caracteres, quantidade/preco com seis casas e valores/aliquotas com duas. O tipo F vem do export anterior, mas a escala fisica deve ser conferida no Oracle. Campo DHIMPORT usa tipo D; verificar preservacao de horario. Nao habilitar rotina com truncamento de dados.
4. No Construtor de Telas, conferir a relacao declarada no mestre `ZPM_PEDREC.ID -> ZPM_PEDITE.IDPED`, com remocao vinculada, e montar a aba detalhe ZPM_PEDITE. A estrutura segue o export nativo CENTPARAM; o cadastro visual da aba e seu ID nao foram inventados. Validar navegacao, inclusao e filtragem entre dois mestres diferentes e confirmar o efeito de exclusao somente em homologacao.
5. Executar `database/deploy.sql` para a unicidade CHAVEIMP e a FK. Nao habilitar a rotina sem a PK composta do detalhe, a FK e a unicidade do mestre ativas. O deploy repetido confere existencia; `database/validacao.sql` confere definicoes. DDL nao participa do rollback da acao.
6. Gerar JAR pelo Gradle com bibliotecas comprovadas do ambiente. O JAR local em `out/ZAYA-PORTOMAGPDF-V1.3-HOMOLOGACAO.jar` foi compilado com Java 8, a biblioteca de extensoes baixada do ambiente e Jape/modelcore 4.23b244 apenas como referencia; ele exige homologacao e nova compilacao com as bibliotecas 4.35b811 antes de producao. A V1.3 inclui compatibilidade com o parser XML antigo encontrado no servidor. Nao misturar conjuntos de SDK nem embarcar JARs proprietarios.
7. Na instancia ZPM_PEDREC, conferir a Rotina Java codigo 68, descricao `[ZAYA.IT] - Grava PDF`, classe `br.com.zaya.portomag.importador.sankhya.ReceberPedidoAction`. Alterar **Controla Acesso** de **Nao** para **Sim** antes de producao e manter **Controle de transacao manual desabilitado**.
8. O contrato da rotina e `XMLPEDIDO` (String com XML versao 1, maximo 250.000 caracteres). Confirmar o parametro de texto e ausencia de truncamento. O dashboard envia esse parametro pelo contrato interno `ActionButtonsSP.executeJava`, autorizado para a PortoMag 4.35b811; nao alterar livremente servico ou actionID.
9. Publicar `HTML5_PORTOMAGPDF_V1.4_ACAO68.zip` no componente, ponto de entrada `index.jsp`. A entrada usa as taglibs Sankhya/JSTL, `<snk:load />`, UTF-8 e EL habilitada. A distribuicao e plana; `pdf.js` e `pdf.worker.js` ficam na raiz. Validar `${BASE_FOLDER}`, chamada same-origin para `/mge/service.sbr`, MIME JavaScript, worker local, HTTPS e navegador suportado. Nenhuma CDN e usada.
10. Para o dashboard consolidado, usar componente nativo Tabela com entidade ZPM_PEDREC. A documentacao oficial suporta exibir suas acoes. Vincular chave ID e aplicar filtros/permissoes do usuario. Nao publicar consulta irrestrita com dados de outros usuarios/empresas.

## Limite desta entrega

Os botoes Importar PDF e Salvar pedido recebido funcionam no HTML5 V1.4. O salvamento exige confirmacao, envia somente `XMLPEDIDO` para a acao 68, bloqueia repeticao durante a requisicao e exibe a mensagem funcional devolvida. Timeout e tratado como resultado incerto: consultar por CHAVEIMP antes de repetir. O XML baixado continua sendo material interno de conferencia, NAO e metadado.

A rotina Java valida novamente campos, tipos, duplicidade de sequencias, soma, tamanho e paginas; fixa instancias/campos e autor do recebimento no servidor. Alertas de composicao resultam em DIVERGENTE. RECEBIDO tampouco significa aprovado para gerar documento. A constraint CHAVEIMP impede concorrencia entre importacoes do mesmo numero/CNPJs; uma revisao do mesmo pedido e bloqueada e exige regra futura.

Permissao da acao e da tela deve ser conferida com usuario autorizado e nao autorizado. Nao basta a visibilidade do botao. Nao conceder CRUD generico dos campos de controle para contornar essa rotina. Nenhum codigo de parceiro, produto, empresa, TOP ou natureza e inferido do PDF.

## Homologacao obrigatoria

- PDF referencia: 1 mestre, 2 detalhes, total 21.616,09; primeiro item sinalizado com divergencia de composicao.
- Importar primeiro em ambiente isolado uma versao completada a partir de export nativo da 4.35b811. Confirmar ordem das instancias, PK composta, relacao mestre -> detalhe e semantica de `remove="S"`.
- Fechar/reabrir mestre e conferir ambos os itens e suas paginas. Depois executar com outro pedido homologado e verificar isolamento do detalhe.
- Erro provocado no segundo item: nenhum mestre/item pode permanecer apos rollback.
- Dois usuarios recebendo o mesmo pedido simultaneamente: um unico mestre; erro do segundo nao deve deixar itens orfaos.
- Timeout apos envio: consultar por CHAVEIMP antes de repetir. Mensagem da acao nao substitui verificacao apos retorno/commit.
- Usuario sem acesso: acao bloqueada pelo servidor.
- Layout desconhecido, PDF vazio/escaneado, excesso de tamanho/paginas, XML malformado e campos fora do contrato: bloqueados.
- Confirmar que os campos monetarios mantem as casas e o numero externo de 10 digitos e texto.

## Etapa futura: documento comercial

Definir pedido de venda ou outro destino, material/servico juntos ou separados, CODEMP, CODPARC, produtos/unidades, TOP/DHTIPOPER/TIPMOV, natureza, negociacao e regra tributaria. Selecionar servico publico suportado na versao. Validar TGFCAB/TGFITE/TGFVAR, estoque, financeiro, fiscal, confirmacao, cancelamento e idempotencia antes de habilitar Gerar documento. Nenhuma insercao em tabela nativa existe nesta V1.0.
