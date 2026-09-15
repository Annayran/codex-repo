# Erros comuns em projetos Sankhya

- Tratar o ERP como CRUD comum.
- Inserir documentos diretamente sem avaliar fluxo funcional.
- Ignorar `TGFVAR` em relações origem/destino.
- Assumir que TOPs diferentes têm o mesmo comportamento.
- Fazer commit/rollback manual sem conhecer a transação.
- Engolir exceções ou deixar recursos abertos.
- Alterar aliases SQL sem verificar consumidores.
- Implementar regra crítica somente no JavaScript.
- Versionar credenciais, logs ou artefatos compilados.
- Publicar sem validação e rollback.

Novos itens devem citar evidência genérica ou permanecer **Pendente de validação pela equipe**.