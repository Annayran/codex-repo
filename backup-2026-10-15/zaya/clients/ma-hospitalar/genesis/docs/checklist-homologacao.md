# Checklist de Homologacao

## Dados e Parametrizacao

- [ ] Central de Parametros instalada no ambiente.
- [ ] TOPs elegiveis cadastradas.
- [ ] Parametros seguros da API configurados por ambiente.
- [ ] Campos de garantia e status criados no local decidido.
- [ ] VIEW criada e validada com dados reais.
- [ ] Tabela de controle criada.

## Cenarios Funcionais

- [ ] TOP parametrizada entra na fila e aparece no dashboard.
- [ ] TOP nao parametrizada e ignorada.
- [ ] Entrada de compra com serie gera payload correto.
- [ ] Saida/remessa com serie gera payload correto.
- [ ] Retorno com serie e integrado conforme regra.
- [ ] Ajuste de saldo e integrado quando TOP elegivel.
- [ ] Implantacao de saldo e integrada quando TOP elegivel.
- [ ] Produto em poder proprio gera `PODER = PROPRIO`.
- [ ] Produto em poder de terceiro gera `PODER = TERCEIRO`.
- [ ] Status manual O/A/I/V/D/R reflete no dashboard e payload.

## Integracao e Erros

- [ ] HTTP 201 marca movimento como enviado.
- [ ] HTTP 400 marca erro funcional com mensagem.
- [ ] HTTP 401 alerta responsaveis e mascara token.
- [ ] Timeout incrementa tentativa e mantem para retry.
- [ ] Reprocessamento nao duplica registro na Genesis.
- [ ] Logs nao exibem token ou credenciais.
