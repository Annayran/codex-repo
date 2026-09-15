# TCSFEQ — Membro de Equipe de Projeto (EquipeProjeto)

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EquipeProjeto | EquipeProjeto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTIPFUNCAO | Função | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| NUFAP | Número da FAP | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EquipeProjeto / [TCSFEQ](TCSFEQ.md) | TipoRecurso / [TCSTFU](TCSTFU.md) | CODTIPFUNCAO->CODTIPFUNCAO | I |
| EquipeProjeto / [TCSFEQ](TCSFEQ.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | EquipeProjeto / [TCSFEQ](TCSFEQ.md) | NUFAP->NUFAP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
