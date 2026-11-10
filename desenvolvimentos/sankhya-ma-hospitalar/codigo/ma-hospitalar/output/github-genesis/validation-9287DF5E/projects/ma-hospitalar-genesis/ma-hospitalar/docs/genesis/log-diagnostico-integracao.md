# Log de diagnostico da integracao Genesis

## Objetivo

O fluxo registra no log da acao agendada e no log do servidor todas as etapas
necessarias para diagnosticar um movimento, desde a selecao na view ate a
persistencia do retorno da Genesis.

## Ordem esperada

1. Identificacao do candidato por chave, empresa, nota, sequencia, parceiro,
   produto, serie e tipo de movimento.
2. Resolucao de `AD_GENMODELO.IDGENESIS`, `AD_GENCLIENTE.IDGENESIS` e
   `AD_GENSECTOR.IDGENESIS`.
3. Pesquisa do item-material existente por modelo, cliente e serie.
4. Exibicao do payload final, codigo externo e hash.
5. Decisao de executar GET, POST ou PUT.
6. Request HTTP com metodo, endpoint, tamanho e corpo JSON.
7. Response HTTP com status, classificacao, ID Genesis, duracao e corpo.
8. Persistencia na Base Instalada e encerramento do candidato.

Se a resolucao do modelo falhar, o ultimo registro sera `Resolucao MODELO
iniciada` e nenhuma mensagem `HTTP request` aparecera. Isso comprova que nenhum
payload foi enviado para a Genesis naquele candidato.

## Protecao de dados

O header `token` nunca e exibido. Token, Authorization, CPF, CNPJ, e-mail,
telefone, CEP e endereco encontrados em JSON sao substituidos por
`[MASCARADO]`. Payloads e respostas sao divididos em partes para evitar perda
visual; a leitura de resposta HTTP permanece limitada a aproximadamente 64 KiB.

O modo detalhado aumenta o volume do log. Depois da homologacao, deve-se revisar
a retencao dos logs do servidor e limitar o tamanho do lote de diagnostico.


---
Copia de consulta para entrega; fonte canonica local: `ma-hospitalar/docs/genesis/log-diagnostico-integracao.md`. Sanitizacao de caminhos e enderecos aplicada; nao representa homologacao.
