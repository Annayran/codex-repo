# TWFUCP — Usuários Candidatos de Processo

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UsuarioCandidatoProcesso | Usuários Candidatos de Processo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODEQUIPE | Cód. Equipe | I |  |  | N |
| CODGRUPOUSU | Cód. grupo | I |  |  | N |
| CODPRN | Cód. Processo | I |  |  | N |
| CODUSU | Cód. usuário | I |  |  | N |
| DINAMICO | Id Instância da Tarefa | S |  |  | N |
| EXPRESSAO | Expressão variável | S |  |  | N |
| IDELEMENTO | Id. Elemento | S |  |  | N |
| IDINSTPRN | Id Instância do Processo | I |  |  | N |
| IDINSTTAR | Id Instância da Tarefa | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| UsuarioCandidatoProcesso / [TWFUCP](TWFUCP.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPOUSU->CODGRUPO | I |
| UsuarioCandidatoProcesso / [TWFUCP](TWFUCP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
