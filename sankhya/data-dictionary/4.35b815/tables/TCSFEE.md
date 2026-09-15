# TCSFEE — Membro de Equipe de Etapa (EquipeEtapa)

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EquipeEtapa | Membro de Equipe de Etapa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTIPFUNCAO | Função | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| NUFAP | Número da FAP | I |  |  | N |
| NUMETAPA | Número da Etapa | I |  |  | N |
| PRINCIPAL | Principal | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EquipeEtapa / [TCSFEE](TCSFEE.md) | EtapaProjeto / [TCSFET](TCSFET.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |
| EquipeEtapa / [TCSFEE](TCSFEE.md) | TipoRecurso / [TCSTFU](TCSTFU.md) | CODTIPFUNCAO->CODTIPFUNCAO | I |
| EquipeEtapa / [TCSFEE](TCSFEE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| EtapaProjeto / [TCSFET](TCSFET.md) | EquipeEtapa / [TCSFEE](TCSFEE.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
