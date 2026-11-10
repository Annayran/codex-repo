# Revisao de produtos

A confirmacao tambem executa a [comparacao fiscal do XML](validacao-fiscal-xml.md).
As permissoes abaixo descrevem apenas o bloqueio de revisao; a comparacao fiscal
e as demais regras da central ainda podem impedir a confirmacao.

## Contrato funcional

- Os bloqueios de confirmacao (revisao e NCM/IPI) aplicam-se somente a TGFCAB
  com TIPMOV = C ou V. Outros valores, inclusive nulo/vazio, retornam antes
  das validacoes e consultas de produtos/XML.

- Inclusao persistida em TGFITE: consultar CabecalhoNota pela NUNOTA do item.
  Somente TIPMOV = C ou V permite consultar o produto e enviar o sinal.
  Outros movimentos, inclusive nulo/vazio, nao enviam sinal. Ausencia de NUNOTA
  ou cabecalho e erro de identificacao, sem envio de sinal.
- Para inclusao elegivel: consultar Produto pelo CODPROD e enviar
  br.zaya.aguardarNF para TGFPRO.AD_IDINSTPRN, quando preenchido.
- O processo ja deve existir e estar aguardando esse sinal na inclusao.
- Confirmacao: percorrer os produtos da nota; com AD_IDINSTPRN preenchido,
  somente AD_REVISADO = S permite confirmar. N, vazio ou nulo bloqueiam.
- Sem instancia preenchida, esta validacao nao bloqueia.
- Nota sem itens nao e bloqueada por esta validacao.
- O bloqueio exige CONFIRMANDO ativo e STATUSNOTA mudando de diferente de L
  para L. Salvar o cabecalho ou editar nota ja confirmada nao executa a validacao.
- AD_LIBPROC nao participa do bloqueio. A confirmacao nao envia sinais.
- Os dois campos sao do cadastro TGFPRO, compartilhados entre notas.
- Nao ha filtro adicional de TOP/AD_RNGSINALFLOW: a elegibilidade definida
  para esta entrega e o vinculo preenchido no produto.
- Nao inicia processos nem altera AD_REVISADO ou AD_IDINSTPRN.
- Edicao/exclusao de item nao sinaliza. Cada inclusao vinculada sinaliza,
  inclusive repeticao do produto ou instancia em outros itens/notas.
- Nao ha deduplicacao persistente nem garantia de entrega exatamente uma vez.
- Erros de consulta ou do Flow sao propagados; nao ha commit/rollback manual.

## Cadastro e implantacao

No mesmo JAR do projeto:
1. Cadastrar br.com.zaya.teleworld.flow.SinalInclusaoProduto como evento Java
   de ItemNota (TGFITE), executando afterInsert.
2. Cadastrar br.com.zaya.teleworld.flow.ValidaRevisaoProdutosConfirmacao como
   regra Java comercial no mecanismo que carrega a interface Regra, conforme
   evidenciado pelo erro de carregamento no ambiente 4.36b109.
   A classe implementa Regra e EventoProgramavelJava, preservando tambem a
   alternativa de evento Java de CabecalhoNota (TGFCAB). Usar somente um cadastro
   para o bloqueio, evitando executar a mesma validacao duas vezes.
   Ambas as entradas afterUpdate consultam os itens somente na transicao de
   confirmacao descrita acima. Atualizar o JAR no modulo Java ja cadastrado.
3. Desativar o cadastro antigo de EnviaSinalConfirmacaoNF de forma coordenada,
   para nao manter sinalizacao tambem na confirmacao.
4. Preservar o cadastro e o FQCN de SinalLiberacaoProcesso (minuta/liberacao).

Nenhum cadastro ou deploy foi executado pela alteracao do codigo.
Sem cadastro da regra Java ou do evento da TGFCAB, o bloqueio nao sera executado.

### Compatibilidade de carregamento

O JAR anterior implementava somente EventoProgramavelJava e falhava com
ClassCastException ao ser carregado como Regra. O adaptador agora implementa
ambas as interfaces e compartilha a mesma validacao. O nome completo foi mantido.
Nao e necessario alterar a regra funcional nem cadastrar um segundo bloqueio.

## Homologacao obrigatoria no servidor

- Inclusao em nota C/V vinculada envia sinal; pedido e qualquer outro TIPMOV
  nao consultam o produto nem enviam sinal, mesmo com AD_IDINSTPRN preenchido.

- Inclusao vinculada: somente a instancia do produto recebe o sinal.
- Inclusao sem instancia: nenhum sinal; edicao de item: nenhum sinal.
- Confirmacao com instancia e N/nulo/vazio: rejeitada com nota e produto.
- Confirmacao com instancia e S, ou sem instancia: permitida por esta regra.
- Nota com varios produtos: qualquer pendente bloqueia.
- Nota sem itens: este evento permite continuar (outras regras nativas podem impedir).
- Salvamento comum e edicao de nota ja em L: nao validar a revisao.
- Validar o disparo de afterUpdate/CONFIRMANDO em tela, API e lote.
- Os valores anteriores sao lidos por getModifingFields, sem getOldVO no evento.
- O bloqueio lanca excecao no afterUpdate: homologar rollback da confirmacao
  e dos efeitos transacionais. afterUpdate nao equivale a depois do commit.
- Simular falha do Flow e rollback da inclusao: atomicidade do sinal com a
  transacao ainda depende do comportamento do servidor.
- Repeticao do produto, instancia ja avancada e inclusao concorrente precisam
  de homologacao do Flow; nao foi introduzido controle persistente de reenvio.
- Revisao concorrente com confirmacao deve ser homologada; nao ha bloqueio
  explicito do cadastro de produto.
- Conferir Java 8, SDK/Flow e campos customizados na versao instalada.

## Logs e mensagens ao cliente

Logger Java padrao: `br.com.zaya.teleworld.flow`. A configuracao do servidor
determina o arquivo/destino e o nivel habilitado; nao foi criado arquivo proprio.

- INFO: inclusao, tentativa de sinal, retorno da chamada e validacao sem bloqueio.
- WARNING: pendencia funcional apresentada ao cliente.
- SEVERE: falha tecnica com referencia, tipo de excecao e pontos da pilha.
- FINE: entrada/saida e motivos para ignorar eventos ou sinais.

Habilitar FINE nessa categoria (e no handler correspondente) para diagnostico
detalhado. Cada execucao recebe uma referencia, compartilhada pelos logs internos;
em falha tecnica, a mensagem ao cliente informa essa referencia para o suporte.
Nao registrar VO completo, SQL, credenciais ou mensagens brutas das excecoes.
Mensagens funcionais sao preservadas; falhas tecnicas continuam propagando uma
excecao, com mensagem amigavel e sem causa bruta exposta ao cliente.

`chamada_concluida` significa apenas que sendSignal retornou sem erro; nao
comprova consumo do sinal, conclusao do fluxo ou commit da transacao.
Homologar destino dos logs e exibicao das mensagens na versao do servidor.

## Reversao da implantacao

Desativar os novos cadastros e restaurar o JAR/cadastro anterior se necessario.
Restaurar o envio antigo reintroduz o comportamento de sinal na confirmacao.
Reversao nao desfaz sinais ja consumidos. Nao houve mudanca de schema ou DML.
