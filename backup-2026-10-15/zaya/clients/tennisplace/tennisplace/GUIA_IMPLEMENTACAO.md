# 📖 Guia de Implementação - CRM Bonus Integration

**Objetivo:** Integrar o fluxo completo do CRM Bonus com a plataforma Sankhya  
**Duração Estimada:** 3-5 dias para integração completa  
**Dificuldade:** Média (arquitetura pronta, faltam events/PDV)

---

## 📋 Índice

1. [Quickstart](#quickstart)
2. [Implementação Passo a Passo](#implementação-passo-a-passo)
3. [Integração com Sankhya](#integração-com-sankhya)
4. [Troubleshooting](#troubleshooting)
5. [Boas Práticas](#boas-práticas)

---

## Quickstart

### Pré-requisitos
- ✅ Java 8+
- ✅ Gradle 8.13+
- ✅ Sankhya configurado
- ✅ Acesso ao endpoint CRM Bonus

### 1 Minuto para Começar

```java
// 1. Importar classe de cliente
import br.com.crmbonus.clientes.service.impl.CrmBonusClientImpl;
import br.com.crmbonus.clientes.integration.client.CrmBonusClient;

// 2. Criar instância
CrmBonusClient client = new CrmBonusClientImpl();

// 3. Verificar usuário
UserVerifyResponse response = client.userVerify("64918487017");

// 4. Usar resposta
if (response.isResource_id()) {
    System.out.println("Usuário não cadastrado!");
} else {
    System.out.println("Usuário ID: " + response.getUser_id());
}
```

---

## Implementação Passo a Passo

### Fase 1: Inicialização (Passos 1-4)

#### Objetivo
Validar cliente, enviar PIN e efetuar autenticação

#### Passo 1: Validar Cliente (user-verify)

```java
// Em: EventCreateHash.java ou ação PDV
import br.com.crmbonus.clientes.service.impl.CrmUserVerifyService;

public void validarCliente(String cpf) {
    try {
        CrmUserVerifyService service = new CrmUserVerifyService();
        service.validarUsuarioCRM(cpf);
        System.out.println("Cliente validado com sucesso");
    } catch (Exception e) {
        System.out.println("Erro na validação: " + e.getMessage());
    }
}
```

#### Passo 2: Enviar PIN (pin-send)

```java
// Dados do cliente para envio de PIN
import br.com.crmbonus.clientes.dto.request.PinSendRequestDTO;
import br.com.crmbonus.clientes.service.impl.RequestHashServiceImpl;
import br.com.crmbonus.bonus.service.impl.BonusServiceImpl;

public void enviarPIN(DynamicVO clienteVO) throws Exception {
    // 1. Gerar hash único da venda
    BigDecimal nunota = clienteVO.asBigDecimal("NUNOTA");
    RequestHashService hashService = new RequestHashServiceImpl();
    String requestHash = hashService.generateHash(nunota);
    
    // 2. Preparar dados do cliente
    PinSendRequestDTO.CustomerDTO customer = new PinSendRequestDTO.CustomerDTO(
        55,                                    // country_code
        clienteVO.asString("NOME"),           // name
        clienteVO.asString("PHONE"),          // phone
        clienteVO.asString("EMAIL"),          // email
        clienteVO.asString("CGC_CPF")         // document
    );
    
    // 3. Preparar itens
    PinSendRequestDTO.ItemDTO[] items = new PinSendRequestDTO.ItemDTO[1];
    items[0] = new PinSendRequestDTO.ItemDTO(
        "SKU-001",
        100.0,  // value_of
        80.0,   // value_per (com desconto)
        1       // quantity
    );
    
    // 4. Montar requisição
    PinSendRequestDTO request = new PinSendRequestDTO(
        customer,
        100.0,  // gross_value
        items
    );
    
    // 5. Enviar ao CRM
    CrmBonusClient client = new CrmBonusClientImpl();
    PinSendResponseDTO response = client.pinSend(request, requestHash);
    
    // 6. Salvar user_id
    FluidUpdateVO fluid = JapeFactory.dao("CabecalhoNota").prepareToUpdate(clienteVO);
    fluid.set("AD_USERIDCRM", response.getUser_id());
    fluid.update();
}
```

#### Passo 3: Validar PIN (pin-validate)

```java
// Após usuário digitar PIN no PDV
import br.com.crmbonus.clientes.dto.request.PinValidateRequestDTO;

public void validarPIN(String userId, String pin, String requestHash) throws Exception {
    PinValidateRequestDTO request = new PinValidateRequestDTO(pin, userId);
    
    CrmBonusClient client = new CrmBonusClientImpl();
    String response = client.pinValidate(request, requestHash);
    
    System.out.println("PIN validado: " + response);
}
```

#### Passo 4: Reenviar PIN (pin-resend)

```java
// Se usuário solicitar novo PIN
import br.com.crmbonus.clientes.dto.request.PinResendRequestDTO;

public void resendirPIN(String userId, String requestHash) throws Exception {
    PinResendRequestDTO request = new PinResendRequestDTO(userId);
    
    CrmBonusClient client = new CrmBonusClientImpl();
    String response = client.pinResend(request, requestHash);
    
    System.out.println("PIN reenviado: " + response);
}
```

---

### Fase 2: Campanhas de Bônus (Passos 5-7)

#### Objetivo
Consultar, listar e aplicar campanhas de bônus disponíveis

#### Passo 5: Consultar Saldo (bonus-available)

```java
// Antes de mostrar campanhas no PDV
import br.com.crmbonus.bonus.service.impl.BonusServiceImpl;
import br.com.crmbonus.bonus.dto.request.BonusAvailableRequestDTO;

public void verificarBonusDisponivel(String userId, double valorCompra, String requestHash) 
        throws Exception {
    // Preparar itens
    BonusAvailableRequestDTO.ItemDTO[] items = new BonusAvailableRequestDTO.ItemDTO[1];
    items[0] = new BonusAvailableRequestDTO.ItemDTO(
        "SKU-001",
        100.0,   // value_of
        80.0,    // value_per (preço com desconto)
        1        // quantity
    );
    
    // Montar requisição
    BonusAvailableRequestDTO request = new BonusAvailableRequestDTO(
        userId,
        80.0,    // value (só o valor do desconto)
        items
    );
    
    // Chamar serviço
    BonusService bonusService = new BonusServiceImpl();
    BonusAvailableResponseDTO response = bonusService.checkAvailable(request, requestHash);
    
    System.out.println("Saldo disponível: " + response.getAvailable_value());
}
```

#### Passo 6: Listar Campanhas (bonus-campaigns)

```java
// Para popular modal de seleção no PDV
import br.com.crmbonus.bonus.service.impl.BonusServiceImpl;
import br.com.crmbonus.bonus.dto.request.BonusCampaignsRequestDTO;

public void listarCampanhas(String userId, double valorTotal, String requestHash) 
        throws Exception {
    // Montar requisição
    BonusCampaignsRequestDTO.TotalsDTO totals = 
        new BonusCampaignsRequestDTO.TotalsDTO(valorTotal);
    
    BonusCampaignsRequestDTO request = new BonusCampaignsRequestDTO(
        "IN_STORE",  // tipo de venda
        userId,
        totals
    );
    
    // Chamar serviço
    BonusService bonusService = new BonusServiceImpl();
    BonusCampaignsResponseDTO response = bonusService.getCampaigns(request, requestHash);
    
    // Processar campanhas
    for (BonusCampaignsResponseDTO.CampaignDTO campaign : response.getCampaigns()) {
        System.out.println("Campanha: " + campaign.getCampaign_id());
        System.out.println("Nome: " + campaign.getCampaign_name());
        System.out.println("Desconto: " + campaign.getDiscount_percentage() + "%");
    }
}
```

#### Passo 7: Aplicar Campanha (bonus-submit)

```java
// Após usuário selecionar campanha no modal
import br.com.crmbonus.bonus.service.impl.BonusServiceImpl;
import br.com.crmbonus.bonus.dto.request.BonusSubmitRequestDTO;

public void aplicarCampanha(String userId, String campaignId, 
                             BigDecimal nunota, String requestHash) 
        throws Exception {
    // Montar requisição
    BonusSubmitRequestDTO request = new BonusSubmitRequestDTO(
        userId,
        campaignId,
        nunota.toString()  // internal_order_id
    );
    
    // Chamar serviço
    BonusService bonusService = new BonusServiceImpl();
    BonusSubmitResponseDTO response = bonusService.submitCampaign(request, requestHash);
    
    // Salvar campaign_id na nota
    DynamicVO noteVO = JapeFactory.dao("CabecalhoNota").findByPK(nunota);
    FluidUpdateVO fluid = JapeFactory.dao("CabecalhoNota").prepareToUpdate(noteVO);
    fluid.set("AD_CAMPAIGNID", campaignId);
    fluid.update();
    
    System.out.println("Campanha aplicada. Desconto: " + response.getDiscount_applied());
}
```

---

### Fase 3: Finalização de Vendas (Passos 8-9)

#### Objetivo
Registrar venda finalizada e permitir cancelamento

#### Passo 8: Finalizar Venda (sales-total)

```java
// Após emissão da NF (evento afterInsert em CabecalhoNota)
import br.com.crmbonus.sales.service.impl.SalesServiceImpl;
import br.com.crmbonus.sales.dto.request.SalesTotalRequestDTO;

public void finalizarVenda(DynamicVO noteVO, String nfNumber, String requestHash) 
        throws Exception {
    // Preparar dados do cliente
    String cpf = noteVO.asString("CPFCNPJ");
    SalesTotalRequestDTO.CustomerDTO customer = 
        new SalesTotalRequestDTO.CustomerDTO(cpf);
    
    // Preparar totais
    SalesTotalRequestDTO.TotalsDTO totals = 
        new SalesTotalRequestDTO.TotalsDTO(
            noteVO.asDouble("VLRTOTAL")
        );
    
    // Montar requisição
    SalesTotalRequestDTO request = new SalesTotalRequestDTO(
        noteVO.asBigDecimal("NUNOTA").toString(),  // internal_order_id
        nfNumber,                                   // invoice (NF-XXXXX)
        customer,
        totals
    );
    
    // Chamar serviço
    SalesService salesService = new SalesServiceImpl();
    SalesTotalResponseDTO response = salesService.submitSalesTotal(request, requestHash);
    
    // Salvar sale_id
    FluidUpdateVO fluid = JapeFactory.dao("CabecalhoNota").prepareToUpdate(noteVO);
    fluid.set("AD_SALESIDCRM", response.getSale_id());
    fluid.update();
    
    System.out.println("Venda finalizada. Sale ID: " + response.getSale_id());
}
```

#### Passo 9: Cancelar Venda (sales-cancel)

```java
// Quando NF é cancelada (evento afterDelete em CabecalhoNota)
import br.com.crmbonus.sales.service.impl.SalesServiceImpl;
import br.com.crmbonus.sales.dto.request.SalesCancelRequestDTO;

public void cancelarVenda(String nunota, String requestHash) 
        throws Exception {
    // Montar requisição
    SalesCancelRequestDTO request = new SalesCancelRequestDTO(nunota);
    
    // Chamar serviço
    SalesService salesService = new SalesServiceImpl();
    SalesCancelResponseDTO response = salesService.cancelSales(request, requestHash);
    
    System.out.println("Venda cancelada: " + response.getMessage());
}
```

---

## Integração com Sankhya

### 1. Criar Campos Customizados

```sql
-- Campos a criar em CabecalhoNota
AD_REQHASH        VARCHAR(100)    -- Hash único da venda
AD_USERIDCRM      VARCHAR(100)    -- ID do usuário no CRM
AD_CAMPAIGNID     VARCHAR(100)    -- ID da campanha selecionada
AD_SALESIDCRM     VARCHAR(100)    -- ID da venda no CRM
```

### 2. Implementar Eventos

```java
// Em: clientes/events/EventCreateHash.java
public class EventCreateHash implements EventoProgramavelJava {
    @Override
    public void afterInsert(PersistenceEvent event) throws Exception {
        DynamicVO vo = (DynamicVO) event.getVo();
        
        // 1. Gerar hash
        RequestHashService hashService = new RequestHashServiceImpl();
        String hash = hashService.generateHash(vo.asBigDecimal("NUNOTA"));
        
        // 2. Salvar hash
        FluidUpdateVO fluid = JapeFactory.dao("CabecalhoNota")
            .prepareToUpdate(vo);
        fluid.set("AD_REQHASH", hash);
        fluid.update();
        
        // 3. Validar usuário
        String cpf = vo.asBigDecimal("CODPARC"); // obter CPF do parceiro
        CrmUserVerifyService service = new CrmUserVerifyService();
        service.validarUsuarioCRM(cpf);
    }
}
```

### 3. Registrar Eventos no Sankhya

No arquivo de configuração, registrar:
```xml
<event-handler>
    <class>br.com.crmbonus.clientes.events.EventCreateHash</class>
    <entity>CabecalhoNota</entity>
    <listener>afterInsert, afterDelete</listener>
</event-handler>
```

### 4. Criar Actions no PDV

```java
// Em: bonus/action/BonusCampaignsAction.java
@Override
public void doAction(ContextoAcao contexto) throws Exception {
    // 1. Obter nota selecionada
    DynamicVO nota = contexto.getSelectedVO();
    String userId = nota.asString("AD_USERIDCRM");
    String requestHash = nota.asString("AD_REQHASH");
    
    // 2. Listar campanhas
    BonusService service = new BonusServiceImpl();
    BonusAvailableRequestDTO request = // montar request
    BonusAvailableResponseDTO response = service.checkAvailable(request, requestHash);
    
    // 3. Exibir modal com campanhas
    // (implementar dialog Sankhya)
    
    // 4. Ao selecionar, chamar bonus-submit
    service.submitCampaign(submitRequest, requestHash);
}
```

---

## Troubleshooting

### Erro: "401 Unauthorized"

**Causa:** Token ou autenticação incorreta

**Solução:**
```java
// Verificar em CRMConfig.java
public static final String TOKEN = "9c7f3a1e5b8d4f2c6a0e9d7b3c1f8a2d4e6b9c0f1a3d5e7c8b2a4f6d9e1c3b7";
public static final String USERNAME = "token"; // sempre minúsculo
```

### Erro: "400 Bad Request"

**Causa:** Dados inválidos no request

**Solução:**
```java
// Verificar campos obrigatórios
BonusSubmitRequestDTO request = new BonusSubmitRequestDTO(
    userId,      // não pode ser null
    campaignId,  // não pode ser null
    orderId      // não pode ser null
);
```

### Erro: "Connection timeout"

**Causa:** Servidor CRM Bonus fora ou lento

**Solução:**
```java
// Logs indicarão o tempo de resposta
// Se > 15s, aumentar timeout em CrmHttpClient:
conn.setReadTimeout(30000); // 30 segundos
```

### Erro: "Request-Hash duplicada"

**Causa:** Hash reutilizada em múltiplas requisições

**Solução:**
```java
// Gerar novo hash para cada operação
RequestHashService hashService = new RequestHashServiceImpl();
String hash1 = hashService.generateHash(nunota); // Operação 1
String hash2 = hashService.generateHash(nunota); // Operação 2 (diferente!)
```

### Erro: "Invalid JSON"

**Causa:** DTO com campos incorretos

**Solução:**
```java
// Verificar nomenclatura de campos
// Devem match com API do CRM (snake_case)
private String user_id;      // ✅ Correto
private String userId;       // ❌ Errado

// Jackson cuida de mapeamento automático
```

---

## Boas Práticas

### 1. Hash Management

```java
// ✅ CORRETO: Hash único por venda
String hash = hashService.generateHash(nunota);
// Usar em TODOS os passos para a mesma venda

// ❌ ERRADO: Gerar hash diferente a cada operação
String hash1 = hashService.generateHash(nunota);
String hash2 = hashService.generateHash(nunota); // Diferente!
```

### 2. Error Handling

```java
// ✅ CORRETO: Capturar e logar erros
try {
    service.submitCampaign(request, hash);
} catch (Exception e) {
    System.out.println("Erro ao submeter: " + e.getMessage());
    throw new Exception(e);
}

// ❌ ERRADO: Ignorar erros
try {
    service.submitCampaign(request, hash);
} catch (Exception e) {
    // Ignorar silenciosamente
}
```

### 3. Armazenamento de Dados

```java
// ✅ CORRETO: Salvar IDs do CRM em campos customizados
FluidUpdateVO fluid = dao.prepareToUpdate(vo);
fluid.set("AD_USERIDCRM", response.getUser_id());
fluid.set("AD_SALESIDCRM", response.getSale_id());
fluid.update();

// ❌ ERRADO: Manter apenas em memória
String userId = response.getUser_id(); // Perderá se app reiniciar
```

### 4. Timeouts

```java
// ✅ CORRETO: Configurar timeouts adequados
conn.setConnectTimeout(15000);  // 15s para conectar
conn.setReadTimeout(15000);     // 15s para resposta

// ❌ ERRADO: Sem timeout (travará indefinidamente)
// conn.setReadTimeout(-1); // NUNCA!
```

### 5. Logging

```java
// ✅ CORRETO: Log estruturado
System.out.println("[CRM-BONUS][" + requestId + "] Iniciando user-verify");
System.out.println("[CRM-BONUS][" + requestId + "] CPF: " + cpf);
System.out.println("[CRM-BONUS][" + requestId + "] Response: " + responseBody);

// ❌ ERRADO: Sem contexto
System.out.println("User verified"); // Qual operação? Qual requisição?
```

---

## Checklist de Implementação

### ✅ Pré-Integração
- [ ] Testar conexão com CRM Bonus
- [ ] Validar token e autenticação
- [ ] Verificar endpoint (webhook-test)
- [ ] Testar manualmente em Postman

### ✅ Campos Sankhya
- [ ] Criar AD_REQHASH em CabecalhoNota
- [ ] Criar AD_USERIDCRM em CabecalhoNota
- [ ] Criar AD_CAMPAIGNID em CabecalhoNota
- [ ] Criar AD_SALESIDCRM em CabecalhoNota

### ✅ Eventos
- [ ] Implementar EventCreateHash.afterInsert
- [ ] Implementar EventCreateHash.afterDelete
- [ ] Registrar eventos no Sankhya
- [ ] Testar gatilho dos eventos

### ✅ Testes
- [ ] Testar passo 1: user-verify
- [ ] Testar passo 2: pin-send
- [ ] Testar passo 3: pin-validate
- [ ] Testar passo 5: bonus-available
- [ ] Testar passo 6: bonus-campaigns
- [ ] Testar passo 8: sales-total
- [ ] Testar passo 9: sales-cancel

### ✅ Documentação
- [ ] Documentar campos customizados
- [ ] Documentar fluxo de integração
- [ ] Documentar como reenviar PIN
- [ ] Documentar como cancelar venda

---

## 🚀 Próximas Etapas

1. **Dia 1-2:** Implementar Fase 1 (Inicialização)
2. **Dia 2-3:** Implementar Fase 2 (Bônus)
3. **Dia 3-4:** Implementar Fase 3 (Vendas)
4. **Dia 4-5:** Integração com Sankhya e testes

---

**Dúvidas?** Consulte:
- `RELATORIO_GERAL.md` - Visão geral do projeto
- `RELATORIO_BONUS_SALES.md` - Detalhes técnicos
- `RELATORIO_CORRECOES_CLIENTES.md` - Correções aplicadas
- Classes de exemplo: `BonusServiceImpl.java`, `SalesServiceImpl.java`

---

*Guia de Implementação v1.0*  
*27 de Fevereiro de 2026*

