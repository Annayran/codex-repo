---
tipo_registro: banco_de_dados
empresa:
nome_banco: "{{title}}"
ambiente:
motor:
versao:
host:
porta:
banco:
schema:
login:
metodo_autenticacao: senha
ssl_tls: true
vpn:
bastion:
ferramenta_cliente:
cofre_segredos:
referencia_segredo:
responsavel: eu
nivel_acesso:
criticidade: alta
status: ativo
ultima_validacao:
proxima_revisao:
expira_em:
tags:
  - banco-de-dados
  - restrito
---

# {{title}}

> [!danger] Nunca inclua credenciais na conexão
> Não registre senha, token, chave SSH, connection string completa ou certificado privado. Mantenha o segredo no cofre e use somente `referencia_segredo` nesta nota.

## Finalidade


## Caminho de acesso

1. Conectar à VPN ou bastion relacionado, quando necessário.
2. Abrir a ferramenta cliente indicada.
3. Usar host, porta, banco e schema das propriedades.
4. Recuperar a credencial no cofre pela referência.

## Modelo de conexão sem segredo

```text
motor://<login>@<host>:<porta>/<banco>
```

## Permissões autorizadas


## Restrições operacionais

- [ ] Confirmar o ambiente antes de executar comandos.
- [ ] Evitar DDL/DML fora de procedimento autorizado.
- [ ] Registrar evidência da mudança quando aplicável.

## Teste de conectividade


## Histórico de validações

- {{date:YYYY-MM-DD}} — Registro criado; nenhum segredo incluído.

