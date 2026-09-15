# TGFAPURSTMEDENT — Cabeçalho Apuração ST Anterior Por Média

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CabecalhoApurSTAnt | Cabeçalho Apuração ST Anterior Por Média | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DHFIMAPUR | Data Fim da Apuração | H |  |  | N |
| DHSOLICT | Data de Apuração | H |  |  | N |
| DTAPUR | Data de Cópia/Contagem | H |  |  | N |
| NUAPUR | Código | I |  |  | N |
| OPCAPUR | Opção | I |  |  | N |
| OPCMARC | Opção de Marcação | I |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoApurSTAnt / [TGFAPURSTMEDENT](TGFAPURSTMEDENT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ItemApuracaoSTAnteriorPorMedia / [TGFIAPURSTMEDENT](TGFIAPURSTMEDENT.md) | CabecalhoApurSTAnt / [TGFAPURSTMEDENT](TGFAPURSTMEDENT.md) | NUAPUR->NUAPUR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
