# TGFPLAC — Cabeçalho do Planejamento de Entrega

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CabecalhoPlanejamentoEntrega | Cabeçalho Planejamento de Entrega | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBAI | Bairro | I |  |  | S |
| CODCID | Cidade | I |  |  | S |
| CODCONTATO | Contato | I |  |  | N |
| CODEND | Endereço | I |  |  | S |
| CODPARCFAT | Parceiro | I |  |  | N |
| CODREG | Região | I |  |  | S |
| CODUSU | Cód. Usuário | I |  |  | N |
| DISTRIBUICAOCONTATO | Verifica se Houve Distribuição para o Contato | I |  |  | S |
| DTALTER | Data de Alteração | H |  |  | N |
| GRUPO | Grupo | I |  |  | N |
| NUMEND | Nro. Endereço | I |  |  | S |
| NUNOTA | N.Único Nota | I |  |  | N |
| NUPLAN | Nro. Planejamento | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoPlanejamentoEntrega / [TGFPLAC](TGFPLAC.md) | Bairro / [TSIBAI](TSIBAI.md) | CODBAI->CODBAI | I |
| CabecalhoPlanejamentoEntrega / [TGFPLAC](TGFPLAC.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| CabecalhoPlanejamentoEntrega / [TGFPLAC](TGFPLAC.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARCFAT->CODPARC | I |
| CabecalhoPlanejamentoEntrega / [TGFPLAC](TGFPLAC.md) | Endereco / [TSIEND](TSIEND.md) | CODEND->CODEND | I |
| CabecalhoPlanejamentoEntrega / [TGFPLAC](TGFPLAC.md) | ItemPlanejamentoEntrega / [TGFPLAI](TGFPLAI.md) | CODCONTATO->CODCONTATO; CODPARCFAT->CODPARCFAT; NUPLAN->NUPLAN | I |
| CabecalhoPlanejamentoEntrega / [TGFPLAC](TGFPLAC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCFAT->CODPARC | I |
| CabecalhoPlanejamentoEntrega / [TGFPLAC](TGFPLAC.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| PlanejamentoEntrega / [TGFPLAN](TGFPLAN.md) | CabecalhoPlanejamentoEntrega / [TGFPLAC](TGFPLAC.md) | NUPLAN->NUPLAN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
