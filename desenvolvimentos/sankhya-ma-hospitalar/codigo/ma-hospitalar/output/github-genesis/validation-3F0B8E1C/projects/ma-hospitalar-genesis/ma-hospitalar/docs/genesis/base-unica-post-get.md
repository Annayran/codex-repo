# Base unica Genesis: POST e GET

Decisao de 09/10/2026: manter exclusivamente IDBASE=1, inclusive dados importados
da planilha e resultados da API. Nao criar carga 2. Implementacao local candidata;
nao instalada ou homologada no servidor.

## Responsabilidades e sequencia

IntegracaoGenesisScheduleAction mantem o FQCN registrado. O runner prepara um
movimento por rodada e CadastroGenesisPreparationService resolve no maximo uma
dependencia por rodada, nesta ordem:

1. Cliente.
2. Setor, dependente do cliente confirmado.
3. Material.
4. Fabricante.
5. Modelo, dependente de material e fabricante confirmados.
6. Item-material, dependente de cliente, setor e modelo confirmados.

Os resultados de dependencias compartilhadas sao reutilizados apos persistencia.
Nao ha botao manual novo nem job adicional obrigatorio para preparar cadastros.
Um cadastro por rodada nao significa uma unica chamada: sem ID podem ocorrer
GET por codigo externo e POST; com ID ocorre GET por ID.

## Endpoints e base instalada

| Entidade | Recurso /api/v1 | Tabela da carga 1 | Vinculo Sankhya |
| --- | --- | --- | --- |
| Cliente | clientes/ e clientes/{id} | AD_GENCLIENTE | CODPARC |
| Setor | setores/ e setores/{id} | AD_GENSECTOR | CODPARC e IDCLIENTEGEN |
| Material | materiais/ e materiais/{id} | AD_GENMATERIAL | CODGRUPOPROD |
| Fabricante | empresas/ e empresas/{id} | AD_GENEMPRESA | MARCASANKHYA / codigo externo |
| Modelo | modelos/ e modelos/{id} | AD_GENMODELO | CODPROD |
| Equipamento | itens-materiais/ e itens-materiais/{id} | AD_GENITEMMAT | CODPROD e NSERIE |

Fonte do contrato: Swagger fornecido pelo usuario; as seis colecoes suportam
GET com codigoExterno, POST e GET por ID. O novo caminho nao executa PUT.
Nao pesquisa cliente por documento no endpoint alternativo que apresentou 500.
Os metodos legados com PUT continuam no JAR por compatibilidade, mas nao sao
chamados pelo novo runner. O catalogo /estoques possui fluxo independente e
mantem suas regras anteriores de preco, PUT e inativacao; nao foi convertido
para este fluxo de base instalada.

## Regras de identidade

Com ID local: GET por ID, conferir ID e identidade; 404 nunca autoriza recriacao.
Sem ID local: primeiro recuperar protocolo duravel, quando houver. Na ausencia,
consultar codigo externo estavel. Somente ausencia confirmada autoriza reserva
e POST. Erro 429/500 na consulta nao e ausencia.

Cliente/fabricante validam documento quando disponivel; fabricante sem CNPJ
valida codigo externo na consulta, mas POST exige CNPJ de 14 digitos.
Setor valida cliente. Material/modelo validam codigo externo. Item valida
serie e modelo. Vinculos duplicados/ambiguos bloqueiam a atualizacao.
Cliente importado sem CODPARC pode ser recuperado pelo documento local, sem
apropriar cadastro ja vinculado a outro parceiro. Material/fabricante ja
referenciados pelo modelo sao reutilizados quando o vinculo direto estiver
ausente. O GET confirmado complementa o vinculo Sankhya apenas se estiver
vazio ou corresponder ao mesmo cadastro.
Setor continua sendo o cadastro associado ao cliente, nao uma descoberta
automatica de UTI/centro cirurgico.

Nao e possivel detectar um registro remoto sem ID conhecido e com codigo externo
diferente. Nesses casos a conciliacao da planilha deve ser corrigida antes do
envio. Nao ha garantia de unicidade remota por nome ou marca.

## Persistencia e retomada

GenesisOperationRepository usa AD_GENMOVEST com chave API|ENTIDADE|CHAVE_LOCAL,
NUNOTA nulo e TIPO_MOVIMENTO CADASTRO_ENTIDADE. Nao representa uma nota fiscal.
Esses controles aparecem no diagnostico, mas nao contam como equipamentos
enviados no painel geral.

- ENVIO_INCERTO: reserva persistida antes de chamar POST.
- API_CONFIRMADO: ID retornado persistido antes da gravacao da base instalada.
- API_SINCRONIZADO: base instalada gravada.

GenesisTransaction usa execWithAutonomousTX do SDK existente, sem commit JDBC
manual. Cada reserva, confirmacao e persistencia possui sua transacao gerenciada.
Uma queda antes de confirmar o ID deixa a reserva incerta e bloqueia novo POST.
Uma queda depois de confirmar o ID permite recuperar por GET, sem novo POST.
Uma reserva incerta permite somente GET de reconciliacao pelo codigo externo.
Se encontrado, o ID e recuperado; 404 continua bloqueando POST. A origem de
criacao nao e presumida nessa recuperacao, portanto DELETE exige revisao.
Nao se promete exactly-once: uma reserva sem resposta pode representar POST
nao iniciado ou concluido remotamente. Exige reconciliacao, nao repeticao cega.

POST confirmado insere/atualiza o registro local com ID e codigo externo.
GET sobre registro local existente atualiza apenas campos presentes no retorno;
campo ausente preserva o valor anterior, null explicito limpa o campo.
Vinculos operacionais Sankhya nao sao copiados de atributos arbitrarios da API.
Nao se grava a resposta mascarada de log: persistencia usa o objeto original,
sem imprimir documentos/credenciais.

Campos remotos sao limitados ao modelo existente da base: cliente/endereco,
setor/cliente, material/tipo/risco, empresa/nome/papeis, modelo/dependencias/ANVISA,
item/serie/dependencias/valores/datas/marca/situacao. A base nao replica atributos
da API que nao possuem coluna local. Datas seguem a unidade epoch configurada.

## Cancelamento e reconsulta

A reserva de POST do equipamento tambem marca o movimento como incerto. Se a
nota for cancelada durante o POST, o retorno e gravado sem revincular NUNOTA e
a EXCFILA e preenchida/complementada. DELETE continua condicionado a origem
CRIADO, identidade, movimento e cancelamento confirmados. Item preexistente
consultado por GET nao ganha autorizacao de exclusao.

Novo job opcional para atualizacao periodica:

- Nome: [ZAYA.IT] - Reconciliar Base Instalada Genesis
- Descricao: Reconsulta um registro Genesis por ID e atualiza a carga 1, sem mutacao remota.
- Classe: br.com.zaya.mahospitalar.genesis.schedule.BaseGenesisRefreshScheduleAction
- Instancia de referencia: AD_GENBASE (confirmar nome da instancia no Construtor).
- Sugestao de agenda: a cada 15 minutos; transacao automatica desligada.

O job seleciona um registro conciliado ha mais de 24 horas, em qualquer uma
das seis tabelas, e faz somente GET por ID. Respeita cooldown e backoff, registra
tentativa e alterna registros antigos. A janela real depende do volume/agenda:
nao e espelho em tempo real. 404/erro preserva o registro local, sem POST/DELETE.
Sem cadastrar este job, a atualizacao acontece nos GETs do processamento normal.

## Homologacao, deploy e rollback

Nao ha CREATE TABLE, DDL, alteracao de runtime ou dependencia. Campos existentes
devem comportar os novos estados/chaves; validar com validar-base-unica-post-get.sql.
Nenhum DML em tabela padrao Sankhya e executado pelo novo fluxo.

Antes do deploy: exportar JAR e ZIP JSP vigentes, preservar controles e pausar
acoes Genesis. Substituir o modulo Java pelo JAR consolidado e o componente BI
160 pelo ZIP JSP separado. Manter eventos e job de inativacao existentes.
Habilitar primeiro com poucos movimentos em homologacao e conferir o commit.

Cenarios: seis cadastros novos/existentes; dependencia ausente; codigo externo
existente sem vinculo; 404/429/500; POST sem ID/timeout; falha ao persistir retorno;
reinicio; dois executores concorrentes; cancelamento durante POST; setor/modelo
ambiguos; reconsulta sem PUT; campos ausentes/null e preservacao de CODPARC/CODPROD.

Rollback: pausar jobs e restaurar JAR/JSP anteriores. Preservar controles API,
protocolo e EXCFILA. Nao apagar reservas nem transformar ENVIO_INCERTO em PENDENTE
para forcar POST. Autonomias ja confirmadas nao sao desfeitas pelo rollback do
chamador; isso e intencional e exige conciliacao, nao exclusao indiscriminada.


---
Copia de consulta para entrega; fonte canonica local: `ma-hospitalar/docs/genesis/base-unica-post-get.md`. Sanitizacao de caminhos e enderecos aplicada; nao representa homologacao.
