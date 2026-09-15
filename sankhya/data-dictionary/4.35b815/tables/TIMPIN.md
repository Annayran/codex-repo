# TIMPIN — TIMPIN

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimProcessoIntegracao | Processo de integração | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCRICAO | Descrição | S |  |  | N |
| DHULTIMAEXEC | Data e hora | H |  |  | N |
| FILEULTIMAEXEC | Arquivo gerado | S |  |  | N |
| NUCOL | Coletor a utilizar | I |  |  | N |
| NUFID | Filtro a utilizar | I |  |  | N |
| NUODA | Fonte a utilizar | I |  |  | N |
| NUORD | Ordenador a utilizar | I |  |  | N |
| NUPIN | Nro. Único | I |  |  | N |
| NUPOR | Portal a utilizar | I |  |  | N |
| NURND | Gerador a utilizar | I |  |  | N |
| NUTRA | Tradutor a utilizar | I |  |  | N |
| URLULTIMAEXEC | Link de acesso | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimProcessoIntegracao / [TIMPIN](TIMPIN.md) | TimColetorDados / [TIMCOL](TIMCOL.md) | NUCOL->NUCOL | I |
| TimProcessoIntegracao / [TIMPIN](TIMPIN.md) | TimFiltroDados / [TIMFID](TIMFID.md) | NUFID->NUFID | I |
| TimProcessoIntegracao / [TIMPIN](TIMPIN.md) | TimFonteDados / [TIMODA](TIMODA.md) | NUODA->NUODA | I |
| TimProcessoIntegracao / [TIMPIN](TIMPIN.md) | TimGeradorDados / [TIMRND](TIMRND.md) | NURND->NURND | I |
| TimProcessoIntegracao / [TIMPIN](TIMPIN.md) | TimOrdemDados / [TIMORD](TIMORD.md) | NUORD->NUORD | I |
| TimProcessoIntegracao / [TIMPIN](TIMPIN.md) | TimPortalImoveis / [TIMPOR](TIMPOR.md) | NUPOR->PORCODIGO | I |
| TimProcessoIntegracao / [TIMPIN](TIMPIN.md) | TimTradutorDados / [TIMTRA](TIMTRA.md) | NUTRA->NUTRA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
