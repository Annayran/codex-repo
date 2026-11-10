# Configuração manual do Dynaform e componente HTML5

## Artefatos

- JSP: `src/main/webapp/html5/importacaoFormulas.jsp`
- ZIP gerado: `build/distributions/TPN_html5Component.zip`
- lançador JavaScript: `src/main/resources/sankhya/abrirImportador.js`
- ação de pré-validação:
  `br.com.zaya.topnutri.importacaoformulas.sankhya.actions.PreviewFormulaImportAction`
- ação de confirmação:
  `br.com.zaya.topnutri.importacaoformulas.sankhya.actions.ExecuteFormulaImportAction`

## Parâmetros das ações

As duas ações recebem parâmetros texto configurados com estes nomes exatos:

- `TPN_ARQUIVO_NOME`
- `TPN_ARQUIVO_BASE64`

A ação de confirmação recebe também:

- `TPN_HASH_CONFIRMADO`

Os bytes do TXT são codificados em Base64 no navegador e decodificados apenas
no backend. O parser tenta UTF-8 estrito, aceita BOM e usa ISO-8859-1 como fallback
para arquivos legados. As quantidades seguem como texto
até a criação de `BigDecimal`; não há conversão para `Number`, `double` ou
arredondamento.

## Cadastro manual

1. Publique o JAR `ZAYA-TPN-V1.9.jar` no módulo Java de homologação.
2. Cadastre a ação de pré-validação com o FQCN informado acima.
3. Cadastre a ação de confirmação com o respectivo FQCN.
4. Mantenha o controle de acesso das ações habilitado.
5. Não habilite controle manual de transação sem validar o ponto de entrada. A
   ação produtiva final deverá executar o arquivo inteiro em uma transação.
6. Importe `TPN_html5Component.zip` como componente HTML5.
7. Substitua no lançador os três valores `PENDENTE_CONFIGURACAO` pelo `NU_GDG`
   e pelos códigos reais das duas ações.
8. Configure o JavaScript no Dynaform manual e teste primeiro em homologação.

## Compatibilidade e segurança

O modal e `ActionButtonsSP.executeJava` foram comprovados no componente de
referência enviado pelo cliente. Esse serviço não foi confirmado como API
pública na documentação oficial consultada e deverá ser testado no Sankhya Om
4.36b109. O código mantém essa dependência somente na JSP para permitir troca.

O navegador não lê cookies de autenticação e não envia identificador de sessão
na URL. A chamada é same-origin e deixa o gerenciamento da sessão para a
plataforma. As respostas são renderizadas com `textContent`.

## Estado da confirmação produtiva

Na V1.9, atualize o JAR e o ZIP HTML5 juntos para receber a simulação somente
leitura do PA e do PI/Batida. A pré-validação consulta os cadastros e a versão
anterior para apresentar a composição futura; não cria versão e não grava dados.
A diferença exata continua no contrato de resposta para rastreabilidade, mas não
é exibida ao usuário quando não bloqueia.
Atualize os dois artefatos juntos. Reaplique a proposta visual do lançador sem alterar os códigos
reais já cadastrados. Homologue com arquivo válido, divergente e vários PAs.
O resumo antecipa as validações cadastrais determinísticas, mas a confirmação
repete as verificações e a validação nativa imediatamente antes da publicação.

O frontend habilita a confirmação somente para arquivo válido.
`ExecuteFormulaImportAction` revalida o arquivo e compara o SHA-256, cria uma
nova versão pelo fluxo nativo, substitui a composição via Jape e publica somente
após a validação nativa. Não usar DML direto em `TPRPRC`, `TPREFX`, `TPRATV` ou
`TPRLMP`.

## Homologação e rollback

1. Confirme que o controle manual de transação da ação permanece desabilitado.
2. Execute primeiro um TXT com um único PA conhecido e registre o `IDPROC` e a
   versão anterior.
3. Confira materiais, quantidades sem arredondamento, `AD_TIPOMP`, `CODVOL`,
   `TIPOCONTROLEMP`, `CONTROLEMP`, atividade, PI criado pela rotina existente e
   processo padrão após o deploy.
4. Repita com múltiplos PAs, um PA novo e um erro proposital para provar rollback.
5. Se a validação funcional falhar após sucesso técnico, reative pela tela nativa
   a versão anterior registrada. Não exclua nem altere tabelas por SQL.
