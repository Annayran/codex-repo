# TWFRSLA — SLA Processo Negócio

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
| SLAProcessoNegocio | SLA Processo Negócio | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Active | S |  |  | N |
| CARGAHORARIA | Carga horária | I |  |  | N |
| CODPRN | Cód. Processo | I |  |  | N |
| CONDICOESESCOLHA | Condição de escolha | C |  |  | N |
| CONDICOESSUSPENSAO | Condição de suspensão | C |  |  | N |
| NOMEREGRA | Nome da regra | S |  |  | N |
| REGRA | Regra | I |  |  | N |
| TEMPOLIMITE | Tempo limite | I |  |  | N |
| TIPOTEMPO | Tipo de tempo | S |  |  | N |
| TIPOTEMPOAVISO | Tipo de tempo do aviso | S |  |  | N |
| VERSAO | Versão | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AlertaSLA / [TWFASLA](TWFASLA.md) | SLAProcessoNegocio / [TWFRSLA](TWFRSLA.md) | CODPRN->CODPRN; REGRA->REGRA; VERSAO->VERSAO | I |
| SLAProcessoNegocio / [TWFRSLA](TWFRSLA.md) | ProcessoNegocio / [TWFPRN](TWFPRN.md) | CODPRN->CODPRN; VERSAO->VERSAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
