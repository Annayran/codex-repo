# Construtor de Telas

Não há tabela, campo, instância, ligação ou tela nova para criar no Construtor
de Telas nesta entrega.

As instâncias existentes continuam utilizando:

- `AD_TGFSEQENT` — Sequência de Entrega;
- `AD_VGFRESEST` — Reserva de Estoque;
- `AD_VGFPROVEST` — Reserva de Estoque Provisionado;
- `AD_VGFFRPROD` — Fila por Produto.

As três views são substituídas por SQL preservando os nomes e as colunas. Por
isso, não deve ser feita nova importação de campos sem que uma divergência seja
primeiro comprovada na validação.

O objeto novo `PRA_PKG_FILARES` é um package Oracle interno. Package não aparece
como tabela/instância e deve ser criado pelo arquivo SQL numerado do deploy.

