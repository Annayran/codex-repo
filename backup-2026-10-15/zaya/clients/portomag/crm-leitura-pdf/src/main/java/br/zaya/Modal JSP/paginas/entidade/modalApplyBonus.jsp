<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,java.math.BigDecimal,java.sql.ResultSet,java.util.Base64" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="br.com.sankhya.jape.dao.JdbcWrapper" %>
<%@ page import="br.com.sankhya.jape.sql.NativeSql" %>
<%@ page import="br.com.sankhya.jape.EntityFacade" %>
<%@ page import="br.com.sankhya.modelcore.util.EntityFacadeFactory" %>
<%@ page import="br.com.sankhya.jape.wrapper.JapeFactory" %>
<%@ page import="br.com.sankhya.jape.vo.DynamicVO" %>
<%@ page import="br.com.crmbonus.clientes.config.CRMConfig" %>

<%
    // Read request params
    String codParcParam = request.getParameter("codparc");
    String nunotaParam = request.getParameter("nunota");

    Map<String,Object> serverData = new HashMap<>();
    serverData.put("codparc", codParcParam != null ? codParcParam : "");
    serverData.put("nunota", nunotaParam != null ? nunotaParam : "");

    List<Map<String,Object>> items = new ArrayList<>();
    String phone = null;
    String userId = null;
    BigDecimal grossValue = null;
    String requestHash = null;

    if (codParcParam != null && nunotaParam != null) {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;
        try {
            EntityFacade facade = EntityFacadeFactory.getDWFFacade();
            jdbc = facade.getJdbcWrapper();
            jdbc.openSession();

            // try to load partner user id and phone
            DynamicVO parcVO = JapeFactory.dao("Parceiro").findByPK(new BigDecimal(codParcParam));
            if (parcVO != null) {
                userId = parcVO.asString("AD_USER_ID");
                phone = parcVO.asString("TELEFONE");
            }

            sql = new NativeSql(jdbc);
            sql.appendSql("SELECT CAB.VLRNOTA, CAB.AD_REQUEST_HASH, ITE.CODPROD AS SKU, ITE.QTDNEG AS QUANTITY, ITE.VLRUNIT AS VALUEPER, ITE.VLRTOT AS VALUEOF FROM TGFCAB CAB JOIN TGFITE ITE ON ITE.NUNOTA = CAB.NUNOTA WHERE CAB.CODPARC = :CODPARC AND CAB.NUNOTA = :NUNOTA");
            sql.setNamedParameter("CODPARC", new BigDecimal(codParcParam));
            sql.setNamedParameter("NUNOTA", new BigDecimal(nunotaParam));

            try (ResultSet rs = sql.executeQuery()) {
                while (rs.next()) {
                    if (grossValue == null) grossValue = rs.getBigDecimal("VLRNOTA");
                    if (requestHash == null) requestHash = rs.getString("AD_REQUEST_HASH");

                    Map<String,Object> it = new HashMap<>();
                    it.put("sku", rs.getString("SKU"));
                    it.put("quantity", rs.getBigDecimal("QUANTITY"));
                    it.put("value_per", rs.getBigDecimal("VALUEPER"));
                    it.put("value_of", rs.getBigDecimal("VALUEOF"));
                    items.add(it);
                }
            }

        } catch (Throwable t) {
            // ignore, we'll present an error on client
            t.printStackTrace();
        } finally {
            if (sql != null) NativeSql.releaseResources(sql);
            if (jdbc != null) JdbcWrapper.closeSession(jdbc);
        }
    }

    serverData.put("user_id", userId);
    serverData.put("phone", phone);
    serverData.put("gross_value", grossValue != null ? grossValue : BigDecimal.ZERO);
    serverData.put("items", items);
    serverData.put("request_hash", requestHash);

    ObjectMapper mapper = new ObjectMapper();
    String serverDataJson = mapper.writeValueAsString(serverData);
    String baseUrl = CRMConfig.BASE_URL;
    String username = CRMConfig.USERNAME;
    String token = CRMConfig.TOKEN;
    String auth = Base64.getEncoder().encodeToString((username + ":" + token).getBytes(StandardCharsets.UTF_8));
%>

<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Aplicar Bônus — CRM Bonus</title>
    <style>
        body{font-family: Arial, Helvetica, sans-serif; margin:0; padding:12px;}
        .box{padding:12px}
        .title{font-size:18px; margin-bottom:8px}
        .field{margin:6px 0}
        .actions{margin-top:12px}
        .btn{padding:8px 12px; border-radius:4px; cursor:pointer; border:0}
        .btn-primary{background:#2ECC71;color:#fff}
        .btn-secondary{background:#ecf0f1;color:#2c3e50}
    </style>
</head>
<body>
<div class="box">
    <div class="title">Aplicar Bônus antes do Financeiro</div>
    <div id="info" class="field"></div>

    <div id="status" class="field">Preparando consulta...</div>

    <p id="explain">Se houver bônus disponível, você poderá aplicá-lo nesta venda antes do financeiro.</p>

    <div class="actions">
        <button class="btn btn-primary" id="confirmBtn" disabled>Aplicar Bônus</button>
        <button class="btn btn-secondary" id="cancelBtn">Cancelar</button>
    </div>

    <div id="result" style="margin-top:10px; color:#333"></div>
</div>

<script>
(function(){
    // serverData injected from JSP
    const serverData = <%= serverDataJson %>;
    const CRM_BASE_URL = '<%= baseUrl %>';
    const CRM_AUTH = 'Basic <%= auth %>';

    function maskPhone(digits){ if(!digits) return ''; const d = (''+digits).replace(/[^0-9]/g,''); if(d.length===11) return `(${d.substr(0,2)}) ${d.substr(2,5)}-${d.substr(7,4)}`; if(d.length===10) return `(${d.substr(0,2)}) ${d.substr(2,4)}-${d.substr(6,4)}`; return d; }

    const infoEl = document.getElementById('info');
    const statusEl = document.getElementById('status');
    const explainEl = document.getElementById('explain');
    const confirmBtn = document.getElementById('confirmBtn');
    const cancelBtn = document.getElementById('cancelBtn');
    const resultEl = document.getElementById('result');

    infoEl.innerHTML = `CODPARC: <b>${serverData.codparc || ''}</b> &nbsp;&nbsp; NUNOTA: <b>${serverData.nunota || ''}</b>`;

    cancelBtn.addEventListener('click', function(){ if(window.parent && window.parent.SanPopup){ try{ window.parent.SanPopup.close(); }catch(e){ window.close(); } } else window.close(); });

    // Helper to call CRM directly from client (JSP injected auth) — WARNING: exposing token in client.
    function callCrm(entidade, subEntidade, body) {
        const headers = {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'entidade': entidade,
            'sub-entidade': subEntidade,
            'Authorization': CRM_AUTH
        };
        // include Request-Hash if present
        if(serverData.request_hash) headers['Request-Hash'] = serverData.request_hash;

        return fetch(CRM_BASE_URL, { method: 'POST', headers, body: JSON.stringify(body) })
            .then(async res => {
                const text = await res.text();
                try { return JSON.parse(text); } catch(e){ return text; }
            });
    }

    // Step 1: bonus-available
    function checkBonus(){
        statusEl.innerText = 'Consultando disponibilidade de bônus...';
        resultEl.innerHTML = '';
        confirmBtn.disabled = true;

        if(!serverData.user_id){
            statusEl.innerHTML = '<b style="color:#E67E22">Parceiro sem user_id no Sankhya. Utilize Enviar PIN.</b>';
            return;
        }

        const payload = {
            user_id: serverData.user_id,
            value: serverData.gross_value,
            items: serverData.items
        };

        callCrm('bonus','bonus-available', payload)
            .then(function(json){
                if(!json){ statusEl.innerHTML = '<b style="color:#c0392b">Resposta inválida do CRM.</b>'; return; }

                // try both shapes
                const bonusHas = json.bonus_has || (json.data && json.data.bonus_has) || (json.data && json.data.available);
                if(bonusHas){
                    statusEl.innerHTML = '<b style="color:#27AE60">Bônus disponível para esta venda.</b>';
                    if(json.data && json.data.available_value) resultEl.innerHTML = `<p>Valor disponível: <b>${json.data.available_value}</b></p>`;
                    else if(json.value) resultEl.innerHTML = `<p>Valor disponível: <b>${json.value}</b></p>`;
                    confirmBtn.disabled = false;
                    window._bonusResponse = json;
                } else {
                    statusEl.innerHTML = '<b style="color:#E67E22">Nenhum bônus disponível para esta venda.</b>';
                    if(json.message) resultEl.innerHTML = `<p>${json.message}</p>`;
                }
            }).catch(function(err){ statusEl.innerHTML = '<b style="color:#c0392b">Erro ao consultar CRM:</b> ' + err; });
    }

    // Step 2: apply-bonus
    confirmBtn.addEventListener('click', function(){
        confirmBtn.disabled = true; statusEl.innerText = 'Aplicando bônus...'; resultEl.innerHTML = '';

        const payload = {
            user_id: serverData.user_id,
            value: serverData.gross_value,
            items: serverData.items
        };

        callCrm('bonus','bonus-submit', payload)
            .then(function(json){
                if(!json){ statusEl.innerHTML = '<b style="color:#c0392b">Resposta inválida do CRM.</b>'; confirmBtn.disabled=false; return; }
                if(json.success === false){ statusEl.innerHTML = '<b style="color:#c0392b">Falha:</b> ' + (json.message || ''); confirmBtn.disabled=false; return; }

                // extract pin
                let pin = json.pin || (json.data && json.data.text_pin) || '';
                let pinDigits = '';
                if(pin){ const m = (''+pin).match(/(\d{3,6})/); pinDigits = m ? m[0] : pin; }
                const phone = serverData.phone ? maskPhone(serverData.phone) : (json.user_id || '');

                statusEl.innerHTML = '<b style="color:#27AE60">Bônus aplicado com sucesso.</b>';
                resultEl.innerHTML = `<div><b>PIN:</b> ${pinDigits} <br/><b>Numero:</b> ${phone}</div>`;

                setTimeout(()=>{ if(window.parent && window.parent.SanPopup){ try{ window.parent.SanPopup.close(); }catch(e){ window.close(); } } },1800);

            }).catch(function(err){ statusEl.innerHTML = '<b style="color:#c0392b">Erro ao aplicar:</b> ' + err; confirmBtn.disabled=false; });
    });

    checkBonus();

})();
</script>
</body>
</html>

