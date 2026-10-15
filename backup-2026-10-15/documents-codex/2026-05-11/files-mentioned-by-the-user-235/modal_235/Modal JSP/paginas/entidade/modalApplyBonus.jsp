<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

    <div id="status" class="field">Consultando disponibilidade de bônus...</div>

    <p id="explain">Se houver bônus disponível, você poderá aplicá-lo nesta venda antes do financeiro.</p>

    <div class="actions">
        <button class="btn btn-primary" id="confirmBtn" disabled>Aplicar Bônus</button>
        <button class="btn btn-secondary" id="cancelBtn">Cancelar</button>
    </div>

    <div id="result" style="margin-top:10px; color:#333"></div>
</div>

<script>
(function(){
    // Read query params
    const params = new URLSearchParams(window.location.search);
    const codParc = params.get('codparc');
    const nunota = params.get('nunota');

    function maskPhone(digits){
        if(!digits) return '';
        const d = digits.replace(/[^0-9]/g,'');
        if(d.length===11) return `(${d.substr(0,2)}) ${d.substr(2,5)}-${d.substr(7,4)}`;
        if(d.length===10) return `(${d.substr(0,2)}) ${d.substr(2,4)}-${d.substr(6,4)}`;
        return d;
    }

    const infoEl = document.getElementById('info');
    const statusEl = document.getElementById('status');
    const explainEl = document.getElementById('explain');
    const confirmBtn = document.getElementById('confirmBtn');
    const cancelBtn = document.getElementById('cancelBtn');
    const resultEl = document.getElementById('result');

    infoEl.innerHTML = `CODPARC: <b>${codParc || ''}</b> &nbsp;&nbsp; NUNOTA: <b>${nunota || ''}</b>`;

    // Close handler
    cancelBtn.addEventListener('click', function(){
        if(window.parent && window.parent.SanPopup){ try{ window.parent.SanPopup.close(); }catch(e){ window.close(); } }
        else window.close();
    });

    // Helper to call parent payload executor
    function callParentPayload(payload, cb){
        if(window.parent && typeof window.parent.executeApplyBonusPayload === 'function'){
            try{ window.parent.executeApplyBonusPayload(payload, cb); }catch(e){ cb(e); }
        } else {
            cb('Nenhuma função de execução configurada. Configure window.executeApplyBonusPayload ou window.APPLY_BONUS_ENDPOINT.');
        }
    }

    // Step 1: check bonus availability
    function checkBonus(){
        statusEl.innerText = 'Consultando disponibilidade de bônus...';
        resultEl.innerHTML = '';
        confirmBtn.disabled = true;

        const payload = { action: 'bonus-available', codparc: codParc, nunota: nunota };

        callParentPayload(payload, function(err, json){
            if(err){
                statusEl.innerHTML = '<b style="color:#c0392b">Erro ao consultar bônus:</b> ' + (err.message || err);
                explainEl.style.display = 'block';
                return;
            }

            if(!json || !json.success){
                statusEl.innerHTML = '<b>Sem resposta válida do servidor.</b>';
                return;
            }

            // Expect backend to return { success:true, bonus_has: boolean, data: { ... } }
            const has = !!json.bonus_has;
            if(has){
                statusEl.innerHTML = '<b style="color:#27AE60">Bônus disponível para esta venda.</b>';
                // show details if present
                if(json.data && json.data.available_value){
                    resultEl.innerHTML = `<p>Valor disponível: <b>${json.data.available_value}</b></p>`;
                } else if(json.value){
                    resultEl.innerHTML = `<p>Valor disponível: <b>${json.value}</b></p>`;
                }
                // enable apply
                confirmBtn.disabled = false;
                // store last response for apply
                window._bonusAvailableResponse = json;
            } else {
                statusEl.innerHTML = '<b style="color:#E67E22">Nenhum bônus disponível para esta venda.</b>';
                confirmBtn.disabled = true;
                if(json.message) resultEl.innerHTML = `<p>${json.message}</p>`;
            }
        });
    }

    // Step 2: apply bonus when user confirms
    confirmBtn.addEventListener('click', function(){
        confirmBtn.disabled = true;
        statusEl.innerText = 'Aplicando bônus...';
        resultEl.innerHTML = '';

        const payload = { action: 'apply-bonus', codparc: codParc, nunota: nunota };

        callParentPayload(payload, function(err, json){
            if(err){
                statusEl.innerHTML = '<b style="color:#c0392b">Erro ao aplicar bônus:</b> ' + (err.message || err);
                confirmBtn.disabled = false;
                return;
            }

            if(json && json.success){
                // show pin and phone when available
                const phone = json.phone ? maskPhone(json.phone) : (json.user_id || '');
                const pin = json.pin || (json.data && json.data.text_pin ? json.data.text_pin : '');
                let pinDigits = '';
                if(pin){ const m = (''+pin).match(/(\d{3,6})/); pinDigits = m ? m[0] : pin; }

                statusEl.innerHTML = '<b style="color:#27AE60">Bônus aplicado com sucesso.</b>';
                resultEl.innerHTML = `<div><b>PIN:</b> ${pinDigits} <br/><b>Numero:</b> ${phone}</div>`;

                // optionally close
                setTimeout(()=>{ if(window.parent && window.parent.SanPopup){ try{ window.parent.SanPopup.close(); }catch(e){ window.close(); } } },1800);

            } else {
                statusEl.innerHTML = '<b style="color:#c0392b">Falha ao aplicar bônus.</b>';
                resultEl.innerHTML = '<p>' + (json && json.message ? json.message : 'Resposta inesperada') + '</p>';
                confirmBtn.disabled = false;
            }
        });
    });

    // auto-run availability check
    checkBonus();

})();
</script>
</body>
</html>

