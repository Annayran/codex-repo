# Arquitetura inicial

## Classificação

Projeto Java/Sankhya tradicional, sem Add-on Studio. A interface operacional,
se necessária, será um Dynaform configurado manualmente. O código segue MVC e
mantém a regra funcional independente da apresentação e dos adaptadores Sankhya.

## Fluxo implementado

1. O controller recebe os bytes do arquivo.
2. O serviço calcula o SHA-256 sobre os bytes originais.
3. O parser tenta UTF-8 e mantém fallback ISO-8859-1, acumulando problemas
   estruturais sem alterar os valores do arquivo.
4. O validador verifica duplicidades e aceita diferença de massa de até 5%,
   inclusive, para cima ou para baixo.
5. Após validação cadastral, o planejador aceita somente Macro e Micro, deriva o
   PI pelo prefixo `200`, calcula sua quantidade no PA e normaliza sua composição
   proporcionalmente para 1 kg.
6. A view HTML5 envia os bytes do arquivo em Base64 para uma ação Java de
   pré-validação; o backend consulta os cadastros e processos em modo somente
   leitura, simula a composição futura do PA e do PI e devolve o resultado com
   o SHA-256, sem criar versão ou gravar dados.
7. A confirmação reenvia os mesmos bytes e o hash previamente apresentado. O
   backend recalcula ambos antes de permitir qualquer execução produtiva.
8. O adaptador Sankhya versiona o processo `1` para PAs e o processo `6` para os
   PIs, atualiza ambas as composições via Jape, valida as duas versões e publica
   primeiro Micro e depois PA.
9. Na composição copiada, materiais existentes são atualizados sem recriação.
   Para um material novo, o adaptador consulta `TGFPRO.TIPCONTEST` e `CODVOL`,
   define `TPRLMP.TIPOCONTROLEMP`/`CONTROLEMP` e deixa o listener nativo executar
   as validações, auditoria e integração de PI.

## Fronteiras

- `controller`: coordena a análise do arquivo e não acessa banco.
- `model/entity`: representa fórmulas, componentes e o plano de composição.
- `model/dto`: transporta resultado e inconsistências.
- `model/service`: contém parser, validações, hash e transformação funcional.
- `model/repository`: contém contratos do planejamento independente do ERP.
- `sankhya/service`: contém o adaptador Jape e a integração reflexiva com os
  helpers nativos de versionamento da Manufatura, além da consulta somente
  leitura usada na simulação prévia.

## Decisões comprovadas

- somente `TGFPRO.AD_CATEGORIA = 'M'` identifica micronutriente e somente `A`
  identifica componente direto do PA; demais categorias no TXT bloqueiam;
- somente `TPRLMP.AD_TIPOMP = 'Micro'` identifica o vínculo do PI previsto;
- o quinto campo do registro `0` é obrigatório, registrado e ignorado;
- as quantidades utilizam `BigDecimal`; a divergência de massa somente bloqueia
  quando ultrapassa 5% da quantidade do produto acabado;
- a execução produtiva deverá ser atômica.
- os macros do TXT e o PI integram a parte nutricional nova do PA; etiqueta e
  sacaria da versão anterior são preservadas ou reinseridas como `1 UN`;
- quantidades do TXT são preservadas com `BigDecimal`; somente a composição
  derivada do PI é normalizada para total exato de 1 kg com `DECIMAL128` e
  compensação no último componente;
- o PI usa o prefixo `200` sobre o código canônico do PA e pertence ao processo
  produtivo `6`; o PA pertence ao processo `1`;
- para `TGFPRO.TIPCONTEST = 'L'` (lote), a lista de materiais usa
  `TPRLMP.TIPOCONTROLEMP = 'I'` (Automático) e `CONTROLEMP` vazio;
- controle por Lista preserva o literal existente ou herda do PA quando ambos
  são controlados por Lista; sem informação suficiente, a importação bloqueia.

## Pendências

- Compatibilidade efetiva das assinaturas internas no 4.36b109: pendente de
  validação em homologação; `atualizarFormulasProducao` aceita os overloads de
  três parâmetros e de quatro parâmetros com `Boolean`/`boolean`. O runtime
  informa as assinaturas públicas encontradas quando nenhuma delas for compatível.
- Confirmar que a ação de produção permanece com controle manual desabilitado,
  usando a transação automática da plataforma.
- Transporte do upload pelo componente HTML5: implementado e pendente de teste
  integrado no ambiente 4.36b109.
- Modelo persistente de auditoria: pendente de validação pela equipe.
- Pasta local de dependências da versão 4.36: pendente de disponibilização.

Não existe fallback por DML direto. A persistência utiliza entidades Jape e o
versionamento/deploy nativos, com exceção propagada para rollback.

## Integração da view

O endpoint `ActionButtonsSP.executeJava` foi observado no componente HTML5
fornecido como referência. Não foi localizado como contrato público na
documentação oficial consultada. Seu uso fica isolado na JSP e deve ser validado
na versão do cliente. A autenticação permanece a cargo do navegador por
`credentials: same-origin`; o JavaScript não lê nem envia `JSESSIONID`.
