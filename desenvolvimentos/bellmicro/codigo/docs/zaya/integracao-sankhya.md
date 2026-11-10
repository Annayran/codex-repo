# Integração Sankhya - simulação de rateio

## Correção vigente — 28/10/2026, V6.0

O Java não abre mais o modal. A entrada atual são SimularRateioAction,
RecalcularRateioAction e EfetivarRateioAction, na instância Financeiro/TGFFIN.
O JavaScript do dashboard abre o HTML e referencia os IDs cadastrados.
As instruções anteriores de AbrirModalRateioAction são históricas e foram
substituídas por [ações do Financeiro](acoes-financeiro.md).
O provider de backend continua necessário, agora sem contrato de abertura HTML.
Efetivar possui apenas entrada/contrato e é bloqueado por padrão, sem serviço
financeiro implementado. Build aprovado com 107 cenários Java, bridge e modal
locais; sem deploy ou teste no banco do cliente.

## Implementação local

Modelo físico: Criacao-Manual-Telas.html, confirmado pelo usuário. Java 8;
SDK local utilizado para verificar assinaturas: 4.35b99. A versão real da
Belmicro e o comportamento do importador/contêiner ainda exigem homologação.

Fluxo implementado:

1. AbrirModalRateioAction exige uma seleção e usuário autenticado consistente.
2. ConfiguracaoRateio fornece instâncias Jape, mapeamento da origem, ACL e
   abertura do componente HTML5 pelo padrão efetivamente instalado.
3. ComandosSankhyaRateio abre um contexto Jape por requisição, obtém o usuário
   de AuthenticationInfo e monta o serviço com os repositórios reais.
4. ComandosModalRateio recebe somente origem/entrada/ID/revisão/motivo/chave;
   não aceita usuário nem valores finais do navegador.
5. O modal permite composição manual/critério, simulação, recálculo e análise.
   A efetivação continua desabilitada; não existe criação financeira nesta fase.

## Persistência sem campos adicionais

SimulacaoRepositoryAdRat usa o gateway PersistenciaJape. AD_RATSIM permanece
um cabeçalho estável por origem. AD_RATPAR, AD_RATGRP e AD_RATINT apresentam a
projeção corrente; sua substituição não elimina o histórico abaixo.

Cada solicitação grava fotografias imutáveis em AD_RATEVT.DESCRICAO, usando
somente os campos já presentes no manual:

`RAT1|sha256(chaveSolicitacao)|indice|quantidade|fragmentoBase64`

- Envelope binário com comprimentos explícitos e versão, compactado em GZIP;
  não usa serialização de objetos Java nem carregamento dinâmico de classes.
- Fotografia completa: origem mapeada, entrada manual ou critério/versão,
  pesos/evidências de referência, parcelas, grupos, cabeçalhos materiais, hash,
  revisão, usuário, data, motivo e identidade da solicitação.
- Java7.14: fragmentos Base64 de até19 caracteres e envelope de até80,
  descrição completa de até99 em campo100, somente ASCII. Cliente manteve
  o limite100. O formato RAT1 e o SHA256 hexadecimal inteiro permanecem iguais;
  leitura de fragmentos antigos3500 preservada.
- Tipo do evento S para simulação inicial e R para recálculo; numeração nativa
  de SEQUENCIA e campos NUSIMULACAO/DHEVENTO/CODUSU mantidos.
- Hash de cálculo conferido na leitura; fragmentos ausentes/duplicados,
  versão desconhecida ou payload inválido geram erro, sem recomposição parcial.
- Limites simétricos de 8 MiB por fotografia descompactada, 100000 elementos
  por coleção e até 4000 fragmentos. Excesso falha e reverte a transação.
- Novas gravações: teto76000 caracteres na fotografia compactada/Base64,
  por causa do campo100. Excesso gera FOTOGRAFIA_EXCEDE_LIMITE antes dos eventos,
  sem truncar. Mais fragmentos implicam mais linhas e chamadas de persistência.
- Evidências devem ser referências rastreáveis, nunca detalhes de saúde,
  consumo ou credenciais. Os eventos não devem permitir edição operacional.

Este uso estruturado do evento é uma decisão da implementação local, não um
campo explicitamente descrito no HTML. Precisa ser conferido em homologação
quanto a encoding, capacidade, retenção e permissões. Não há alteração de schema.
Consultas por prefixo da descrição podem exigir avaliação de desempenho com
volume real; não foi criado índice sem validação do ambiente.

As chaves geradas continuam nativas. A criação inicial de AD_RATSIM, detalhes,
fotografia e CHAVESOLIC ocorre na mesma transação; não existe MAX+1 no código
de produção. A chave estável da simulação é o NUSIMULACAO gerado pelo Jape.
NUCOMPONENTE numérico é projetado quando aplicável; nomes de componentes e
compatibilidade são preservados integralmente na fotografia.

## Transação e concorrência

PersistenciaJape usa JapeSession.execEnsuringTX, sem commit/rollback JDBC
manual. Trava a linha da origem nativa por SELECT FOR UPDATE NOWAIT e depois
obtém LOCK TABLE AD_RATSIM IN EXCLUSIVE MODE NOWAIT. A trava de tabela
serializa a rotina, inclusive chaves reutilizadas entre origens/usuários.
É conservadora e pode limitar throughput; um conflito retorna CONCORRENCIA.
O futuro serviço de efetivação precisa respeitar a mesma ordem de locks.

Somente as tabelas adicionais recebem escrita. A origem é bloqueada/lida,
sem alteração financeira. Falhas propagam para rollback; nada é registrado
como efetivado. Jape gera as chaves e aplica os relacionamentos cadastrados.
Sessão e JdbcWrapper são fechados após a requisição. Repositórios JDBC não
fecham a conexão emprestada pelo contexto.

## Configuração de instalação ainda necessária

Não existe provider padrão que permita tudo ou presuma uma base/competência.
Implementar ConfiguracaoRateio com o ambiente homologado e registrá-lo no
arquivo `META-INF/services/br.com.zaya.belmicro.rateio.sankhya.ConfiguracaoRateio`.
O conteúdo desse arquivo é o FQCN real do provider, sem credenciais.

O provider deve:

- Instâncias já confirmadas pela captura do dicionário enviada em 27/10/2026:
  o default instanciasJape usa InstanciasRateio.confirmadas para os onze objetos
  AD_RAT*. Não é necessário fornecer novamente seus nomes.
- A implementação padrão OrigemRepositoryJdbc já resolve título/nota e marcador
  e valida empresas/CR. Implementar MapeamentoOrigem com base/competência e
  campos materiais aprovados por categoria. O retorno não substitui o cabeçalho
  nativo; se ausente, mantém base/competência pendentes, sem data/valor presumidos.
- Fornecer AutorizacaoRateio por operação e escopo de empresas, preservando
  autorização antes de retornos idempotentes. Não confiar em usuário do JSON.
- Abrir o componente HTML5 registrado pelo padrão suportado no cliente,
  sem inventar URL, ID de ação, endpoint ou identificador de componente.
- Registrar os comandos no transporte autenticado suportado, convertendo
  explicitamente a entrada em OrigemCanonica/EntradaSimulacao. Não expor
  exceções técnicas ou DTOs internos por serialização automática.

Os handlers retornam a projeção DtoModalRateio: valores decimais são strings,
IDs são identificadores, e evidências/campos materiais internos são omitidos.
Falhas funcionais devem chegar ao cliente como `{codigo: 'CODIGO_ESTAVEL'}`;
o transporte deve ter timeout finito e rejeitar promessas sem resposta.

## Ligação do modal

Arquivos em web/; o Gradle os inclui em META-INF/resources/rateio. Essa
inclusão não presume que o classloader do Sankhya os publique automaticamente.
Registrar o componente e seu acesso conforme o contêiner do cliente.

Após carregar os recursos no componente, inicializar:

```javascript
const modal = RateioModal.iniciar(document, rateioBridge, rateioOrigem);
```

rateioOrigem é `{tipo: 'NOTA' ou 'TITULO', id: 'identificador'}`. rateioBridge
implementa funções que retornam Promise:

- abrirContexto({origem}) -> ComandosSankhyaRateio.abrirContexto.
- simular({origem,entrada,solicitacaoId}) -> handler simular.
- recalcular({simulacaoId,revisaoEsperada,entrada,motivo,solicitacaoId}) -> handler recalcular.

Não existe bridge fictícia nos arquivos de produção. Sem bridge, o modal fica
bloqueado com uma mensagem de indisponibilidade. Após timeout, mantém dados e
chave da solicitação e bloqueia edição até recuperar o resultado. Não envia
usuário, base, competência arbitrária nem valor final para instrução financeira.

## Build e validação

Com SDK configurado, Gradle inclui sankhya/src na compilação e no JAR:

```powershell
.\gradlew.bat clean build --offline -PsankhyaDependenciesDir=<raiz-sdk> -PsankhyaVersionDir=<versao>
```

Sem SDK, a compilação do núcleo/testes permanece disponível, mas o JAR não
contém a ação e a ponte Jape. O manifesto identifica essa distinção.
O SDK é compileOnly; não distribuir os seus JARs proprietários.

Validação local executada:

- scripts/Test-Dominio.ps1: 77 cenários aprovados (21 domínio, 29 aplicação,
  14 JDBC e 13 persistência), incluindo 1000 composições geradas.
- scripts/Test-Sankhya.ps1: compilação --release 8 contra SDK local 4.35b99
  aprovada. -proc:none evita processadores de anotações do SDK alheios ao módulo.
- scripts/Test-Modal.cjs: fluxo no navegador instalado em headless, com bridge
  simulada: entrada/resultado, edição, timeout/reenvio com mesma identidade,
  bloqueio dos campos e renderização de texto seguro. Screenshot em build/ui/.
- Revisão independente: limite assimétrico do codec corrigido e coberto por
  teste; revisão das fronteiras e do modal sem novos achados.
- Gradle completo/JAR continuam sem validação devido à falha local de loopback
  já registrada. Nenhum banco real ou ambiente do cliente foi acessado.

Atualização em 27/10/2026: a falha de loopback foi contornada com o fallback TCP
previsto em PipeImpl.createListener do JDK local. O build completo offline com
SDK selecionado passou; script reproduzível em Build-Sankhya.ps1 -TcpOnlyJdk.
A variável de JVM é restaurada ao final; nenhuma mudança persistente de ambiente,
Java ou Gradle. Foram aprovados 90 cenários (21 domínio, 32 aplicação, 24 JDBC,
13 persistência) e o teste do modal, incluindo pendências na abertura.

Artefato: build/libs/ZAYA-BLM-V5.0.jar. Conteúdo conferido: 62 classes com major
52, sete SQL, três recursos do modal, manifesto V5.0 com integração Sankhya=true,
sem SDK ou classes de teste incluídos. SHA-256:
67687125748d300c97dabe18873044008c62ec714889731834076e062c34b60d.
Este pacote é de desenvolvimento; ainda requer provider do cliente e homologação.

V5.1: nomes das onze instâncias comprovados pela captura enviada pelo cliente
e vinculados à configuração padrão. Build completo/90 cenários aprovados;
63 classes Java8 no pacote, sem SDK empacotado. SHA-256 do JAR V5.1:
2404423b291e7ae247d192997ff461695553860c12b132234b474e65fca36257.

Para teste visual, usar Playwright já disponível (sem instalar dependências):
`node scripts/Test-Modal.cjs`. Se necessário, indicar navegador existente via
RATEIO_BROWSER_EXECUTABLE e resolução de módulos via NODE_PATH do ambiente.

## Homologação e rollback

Antes de liberar: validar instâncias, autonumeração das chaves compostas,
transação/rollback, caracteres/tamanho do evento, ACL, locks e volume de dados.
Testar seleção por nota e título direto, duplo clique/reenvio e leitura de
revisões antigas. Conferir que nenhuma etapa cria títulos financeiros.

Não houve deploy nesta etapa. Se um pacote for instalado em homologação,
retirar as ações/serviços/permissões e reverter o pacote ao anterior quando
necessário, preservando eventos e fotografias para consulta. Não excluir dados
de controle ou alterar a origem como rollback automático. Migração futura do
formato do evento deve manter leitor compatível com a versão 1.
