# Publicação controlada no ambiente TESTE

Este diretório divide a homologação da Fila de Reserva em dois níveis
independentes. Não executar em produção.

Para instalar os dois níveis na mesma janela, utilizar
`deploy_completo/README.md`, preservando os pontos de parada entre eles.

1. Executar e aprovar o [Nível 1](nivel1_automacao/README.md).
2. Manter o Nível 1 em observação.
3. Somente depois executar o [Nível 2](nivel2_estoque_oe/README.md).

Os scripts de negócio continuam em `database/candidate/fase2/deploy`. Estes
guias informam exatamente quais arquivos utilizar e quais não utilizar.

Nenhum nível cria tabela, campo adicional, instância ou tela no Sankhya.
