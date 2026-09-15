# TGFBOA — Cache do Servico Boa Vista

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CacheServicoBoaVista | Cache do Serviço Boa Vista | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CGC_CPF | CNPJ / CPF | S |  |  | N |
| CMC7 | CMC7 | S |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHCONSULTA | Dt e hora consulta | H |  |  | N |
| RESPOSTA | Resposta | C |  |  | N |
| TIPCONSULTA | Tipo de consulta | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CacheServicoBoaVista / [TGFBOA](TGFBOA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
