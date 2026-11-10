# Catalogo de estoques Genesis - implementacao local

Processo: br.com.zaya.mahospitalar.genesis.schedule.EstoqueGenesisScheduleAction.
Cadastrar como acao agendada, inicialmente desativada. Intervalo de 15 ou 30
minutos. O job usa JapeSession e a mesma exclusao mutua/cooldown dos outros jobs.
Nao cadastrar como evento de nota. Nao reutilizar GENMOVEST ou EXCFILA.

Botao opcional na instancia AD_GENESTCTRL (nao necessario nem solicitado para implantacao):
br.com.zaya.mahospitalar.genesis.action.AcaoReprocessarEstoqueGenesis.
Reprocessa ERRO/REVISAO/REPROCESSAR apos correcao, preserva ID/hash/status remoto
e nao limpa 429 durante cooldown. Nao usar AcaoReprocessarGenesis nessa tabela.

Dashboard: secao separada de catalogo, 25 linhas por pagina no servidor. Nao e
uma subaba da lista de equipamentos nesta etapa. Banner de cooldown principal
ainda mostra movimentos/EXCFILA; o erro 429 do catalogo aparece em sua lista.

## Cadastro manual no Construtor

Tabela e instancia: AD_GENESTCTRL. Sem CREATE TABLE. Criar antes de ativar.
PK manual CODPROD: numero inteiro, sem numeracao automatica; pode relacionar
com TGFPRO conforme padrao do Construtor. Nao relacionar com TGFCAB/TGFCAN.

| Campo | Tipo / tamanho | Uso |
| --- | --- | --- |
| CODPROD | Inteiro, PK | Produto local |
| CODIGOEXTERNO | Texto 100 | Identidade estavel; inicialmente CODPROD |
| IDGENESIS | Inteiro | ID retornado/reconciliado; nao alterar manualmente |
| HASHCONFIRMADO | Texto 64 | Payload confirmado |
| STATUSREMOTO | Texto 20 | Ativo/Inativo confirmado |
| STATUS | Texto 20 | PENDENTE/ENVIADO/REPROCESSAR/ERRO/REVISAO |
| OPERACAO | Texto 30 | CRIADO/ATUALIZADO/SEM_ALTERACAO/INATIVADO |
| HTTP_STATUS | Inteiro | Resultado da ultima tentativa |
| TENTATIVAS | Inteiro, padrao 0 | Falhas consecutivas |
| MENSAGEM | Texto 4000, nao CLOB | Ultimo erro sanitizado/cooldown |
| PROXIMATENTATIVA | Data/hora | Espera para retry |
| DH_CONFIRMACAO | Data/hora | Ultimo resultado confirmado |
| DH_ATUALIZACAO | Data/hora | Ultima observacao/tentativa |

## Fontes e regras

- TGFPRO: ativos e tipo efetivo M2/M3/SV; HSP prioritario, AD somente vazio.
- Produtos ja vinculados seguem sendo observados mesmo inativos ou fora do tipo.
- TGFIPI: CODIPI, enviando PERCENTUAL; sem aliquota cadastrada para CODIPI informado bloqueia envio.
- Tabela 101: ComercialUtils.getNumeroUnicoTabela resolve vigencia;
  ComercialUtils.obtemPreco2 consulta venda no contexto dessa tabela.
- Custo medio: PrecoCustoHelper.getCusto com TipoCusto.CUSTO_MEDIO, seguindo
  natureza de custo do Protheus. Contexto unico TGFCUS e parametros nativos;
  nenhum contexto ou multiplos contextos gera REVISAO, sem escolha arbitraria.
- Fabricante conciliado por marca na AD_GENEMPRESA, IDBASE=1, correspondencia
  unica. Categoria 1 e fornecedor igual fabricante seguem referencia Protheus;
  confirmar esses IDs na homologacao antes de habilitar o job.
- codigo/codigoExterno inicialmente CODPROD; preservar codigo externo ja gravado.
- qtd=1 e controlarEstoque=false: catalogo para orcamento, nao saldo fisico.
- Nao mapeado nesta etapa: registro ANVISA, unidade alternativa, local/controle
  especifico de preco, desconto comercial ou outros tributos.

## Ciclo

GET por codigo externo -> POST somente ausencia confirmada e sem ID local.
Existente -> compara DTO -> PUT quando diferente. ID local com chave nao
localizada -> GET por ID e validacao de codigo externo, nunca recriacao cega.
Inativo/fora do tipo previamente enviado -> GET por ID, valida identidade e
DELETE. Reativacao envia status Ativo por PUT; contrato requer homologacao.
HTTP 201 confirma POST; HTTP 200 confirma PUT/DELETE; respostas inconclusivas
nao concluem. ID, hash e status remoto sao preservados quando tentativa falha.

Lote 25, retry limitado com espera exponencial; 429/auth interrompem o lote.
Hash confirmado evita GET/PUT repetitivo enquanto dados e status forem iguais.
Nao e monitor de alteracoes manuais no Genesis: reconciliacao remota periodica
completa fica fora desta etapa. Exclusao local do produto e timeout seguido de
inativacao antes de recuperar ID exigem analise manual; nao autorizar DELETE sem
identidade recuperada. ERRO/REVISAO voltam automaticamente para reavaliacao em
cada vencimento de PROXIMATENTATIVA, sem limite de tentativas. A correcao dos
dados pode continuar necessaria, mas nao exige botao para retomar.

## Deploy, validacao e rollback

1. Guardar JAR/JSP anteriores e exportar metadados/configuracao antes de mudar.
2. Criar campos no Construtor e validar tipos/nomes e permissao dos jobs.
3. Publicar JAR e ZIP do JSP; manter job de catalogo desativado inicialmente.
4. Validar preco real da tabela 101, custo, IPI, fabricante/fornecedor/categoria.
5. Homologar POST, PUT, inativacao/reativacao, 429, timeout, ID divergente e rollback local.
6. Comparar equipamento/EXCFILA antes e depois para regressao.
7. Ativar so apos aceite; rollback desativa novo job e restaura JAR/JSP anteriores.
   Nao excluir registros remotos nem controles como rollback automatico.

Codigo local nao confirma instalacao ou homologacao. Transacao ainda engloba
HTTP e lote, como executores existentes; checkpoints independentes continuam
pendentes. Cooldown persistido em controles exige que nao sejam apagados.
# Regra atual de valores (08/10/2026)

Por definicao do usuario, o catalogo envia preco de venda da tabela 101 e IPI,
sem custo. vlrCusto permanece nulo e e omitido do JSON; nao consultar TGFCUS nem
substituir ausencia por zero. O fabricante continua exigindo ID Genesis valido,
resolvido a partir da marca; a conciliacao automatica dessa dependencia permanece
pendente no fluxo atual. Homologar POST/PUT sem custo antes de liberar.



---
Copia de consulta para entrega; fonte canonica local: `ma-hospitalar/docs/genesis/catalogo-estoques.md`. Sanitizacao de caminhos e enderecos aplicada; nao representa homologacao.
