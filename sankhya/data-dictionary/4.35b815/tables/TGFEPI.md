# TGFEPI — Equipamentos de Proteção Individual

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EquipamentoProtecaoIndividual | Funções que utilizam E.P.I | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCENCUSFINAL | Cód.Centro Resultado Final | I |  |  | N |
| CODCENCUSINICIAL | Cód.Centro Resultado Inicial | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODFUNCAO | Função | I |  |  | N |
| CODPROD | Cód. do E.P.I | I |  |  | N |
| DIASVALIDADE | Validade (Dias) | I |  |  | N |
| EXIGEDEVOLUCAO | Exige Devolução? | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EquipamentoProtecaoIndividual / [TGFEPI](TGFEPI.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUSINICIAL->CODCENCUS | I |
| EquipamentoProtecaoIndividual / [TGFEPI](TGFEPI.md) | CentroResultadoFinal / [TSICUS](TSICUS.md) | CODCENCUSFINAL->CODCENCUS | I |
| EquipamentoProtecaoIndividual / [TGFEPI](TGFEPI.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| EquipamentoProtecaoIndividual / [TGFEPI](TGFEPI.md) | EmpresaPessoal /  | CODEMP->CODEMP | I |
| EquipamentoProtecaoIndividual / [TGFEPI](TGFEPI.md) | Funcao /  | CODFUNCAO->CODFUNCAO | I |
| EquipamentoProtecaoIndividual / [TGFEPI](TGFEPI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | EquipamentoProtecaoIndividual / [TGFEPI](TGFEPI.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
