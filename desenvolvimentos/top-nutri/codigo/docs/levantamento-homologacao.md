# Levantamento em homologação

Ambiente informado: Sankhya Om `4.36b109`, Manufatura `5.5.12`.

## Evidências confirmadas em 11/09/2026

- banco de dados Oracle;
- `TPRLMP` possui 30 colunas no ambiente consultado;
- `TPRLMP.AD_TIPOMP` é `VARCHAR2(10)` e aceita nulo;
- `TPRLMP.CODPRODPA` referencia `TGFPRO.CODPROD`;
- `TPRLMP.CODPRODMP` referencia `TGFPRO.CODPROD`;
- `TPRLMP.IDEXF` referencia `TPRATV.IDEXF`;
- a massa de referência originalmente continha componentes com categoria nula;
- regra atual confirmada em 28/09/2026: o TXT deve conter somente componentes
  classificados como `M` (Micro) ou `A` (Macro); categoria nula, `E`, `O` ou
  desconhecida bloqueia a importação;
- o PI é formado pelo prefixo `200` aplicado ao código canônico do PA e pertence
  ao processo produtivo `6`; o PA pertence ao processo `1`;
- etiqueta e sacaria são recuperadas da versão anterior do PA, preservadas na
  versão nova ou reinseridas como `1 UN` quando a cópia não as contiver.

## Evidências a coletar

- composição vigente do PA `000022`;
- conferência completa das categorias dos componentes: verificada;
- item da composição marcado com `TPRLMP.AD_TIPOMP = 'Micro'`;
- existência cadastral do PI derivado e vínculo com o processo `6`;
- processo e versão vigentes do PA e do PI;
- operação nativa utilizada pela tela para criar e ativar uma versão;
- configurações herdadas pela nova versão;
- precisão de quantidade configurada;
- comportamento diante de duas alterações concorrentes;
- conferir no 4.36b109 os códigos técnicos de `TIPOCONTROLEMP`: `L` para
  Literal, `I` para Automático e `H` para Herdar do PA;
- validar MP por lote (`TGFPRO.TIPCONTEST = 'L'`) com
  `TPRLMP.TIPOCONTROLEMP = 'I'` e `CONTROLEMP` vazio;
- validar os casos de controle por Lista e Série antes da liberação geral;
- mecanismo suportado para anexar ou enviar o TXT pelo Dynaform.
- tratamento funcional de quantidades iguais a zero ou negativas;
- tratamento funcional de PA ou componente repetido no mesmo arquivo.

## Regras de segurança

- não registrar credenciais, cookies, tokens ou identificadores de sessão;
- não reproduzir dados pessoais desnecessários nas evidências;
- não executar DML nas tabelas padrão;
- não alterar homologação durante a fase de descoberta sem autorização específica.
