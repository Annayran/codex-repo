# TCSERR — Erros e Soluções

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| BaseConhecimento | Base de Conhecimento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ASSUNTO | Assunto | S |  |  | N |
| CODERRO | Código | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODUSUATUAL | Cód. Usuário Atual | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DTATUAL | Data Atual | H |  |  | N |
| DTCAD | Data do Cadastro | H |  |  | N |
| SOLUCAO | Solução | S |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| BaseConhecimento / [TCSERR](TCSERR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
