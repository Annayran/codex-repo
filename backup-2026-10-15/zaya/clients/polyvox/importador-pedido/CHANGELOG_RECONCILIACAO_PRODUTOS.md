# Changelog - Reconciliação de Produtos

## Data: 2026-04-13

### Problema Identificado

Itens com divergência de código vs referência estavam sendo rejeitados desnecessariamente:

```
PVX25320C	2	948	281	CPC51200009CI	281	911	1	COD_DIVERGENTE_DA_REFERENCIA
PVX25320C	2	948	495	CDI51000005CI	495	998	1	COD_DIVERGENTE_DA_REFERENCIA
PVX25320C	2	948	884	RSM66000014CI	884	964	1	COD_DIVERGENTE_DA_REFERENCIA
PVX25320C	2	948	2848	PSP22120012CI	2848	2849	1	COD_DIVERGENTE_DA_REFERENCIA
PVX25320C	2	948	3069	VSP22120003CI		3068	1	COD_INVALIDO_REFERENCIA_UNICA
```

Quando havia:
- **COD_DIVERGENTE_DA_REFERENCIA**: O código informado é válido, mas existe um produto com a mesma referência e código diferente
- **COD_INVALIDO_REFERENCIA_UNICA**: O código informado é inválido, mas a referência encontra um produto único

### Solução Implementada

Arquivo modificado: **NotaGeracaoService.java** (linhas 693-704)

**Antes:**
```java
if (produtosPorReferencia.size() == 1) {
    BigDecimal codPorReferencia = produtosPorReferencia.get(0).getCodProd();
    if (codPorReferencia.compareTo(codOrigem) != 0) {  // ❌ Só corrigia se diferente
        return ResultadoReconciliacaoProduto.corrigido(...);
    }
}
// ... retornava IGNORAR o item
```

**Depois:**
```java
// COD_DIVERGENTE_DA_REFERENCIA: referencia unica encontrada, considere a referencia
if (produtosPorReferencia.size() == 1) {
    BigDecimal codPorReferencia = produtosPorReferencia.get(0).getCodProd();
    // Sempre corrigir pelo produto encontrado na referencia (regra de negocio)
    return ResultadoReconciliacaoProduto.corrigido(
            codOrigem,
            codPorReferencia,
            referencia,
            "COD_DIVERGENTE_CORRIGIDO_POR_REFERENCIA",
            "ERRO_USUARIO_PLANILHA"
    );
}
```

### Comportamento Esperado

**Cenário 1: COD_DIVERGENTE_DA_REFERENCIA**
- ✅ Código informado: 281 (existe em TGFPRO)
- ✅ Referência informada: CPC51200009CI
- ✅ Produto por referência encontrado: CODPROD 911
- ✅ **Ação**: Usar produto 911 (corrigido pela referência)
- ✅ **Status**: COD_DIVERGENTE_CORRIGIDO_POR_REFERENCIA

**Cenário 2: COD_INVALIDO_REFERENCIA_UNICA**
- ✅ Código informado: 3069 (não existe em TGFPRO)
- ✅ Referência informada: VSP22120003CI
- ✅ Produto por referência encontrado: CODPROD 3068
- ✅ **Ação**: Usar produto 3068 (corrigido pela referência)
- ✅ **Status**: COD_INVALIDO_CORRIGIDO_POR_REFERENCIA (já tratado desde antes)

### Impacto

- ✅ Itens com referência válida e única serão **INCLUÍDOS** na nota (antes eram ignorados)
- ✅ Redução de itens perdidos por divergência de código
- ✅ Logs registram a correção automática para rastreabilidade
- ⚠️ **Validação**: Planilhas com referências ambíguas continuam sendo rejeitadas (comportamento correto)

### Logs Gerados

Exemplo para item corrigido:
```
GERACAO_ITEM_COD_CORRIGIDO
pvx: PVX25320C
empresa: 2
codparc: 948
cod_origem: 281
cod_resolvido: 911
referencia: CPC51200009CI
motivo_reconciliacao: COD_DIVERGENTE_CORRIGIDO_POR_REFERENCIA
causa_provavel: ERRO_USUARIO_PLANILHA
```

