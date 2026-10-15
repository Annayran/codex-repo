<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Importador PDF - AD_PEDREC</title>
    <style>
        body { font-family: Arial, Helvetica, sans-serif; margin: 0; padding: 12px; color: #1f2937; }
        .box { border: 1px solid #e5e7eb; border-radius: 8px; padding: 12px; }
        .title { font-size: 18px; font-weight: 700; margin-bottom: 10px; }
        .muted { color: #6b7280; font-size: 12px; }
        .dropzone {
            border: 2px dashed #9ca3af;
            border-radius: 8px;
            padding: 18px;
            text-align: center;
            margin: 10px 0;
            background: #f9fafb;
        }
        .dropzone.dragover { border-color: #2563eb; background: #eff6ff; }
        .actions { margin-top: 12px; display: flex; gap: 8px; }
        .btn { border: 0; border-radius: 6px; padding: 8px 12px; cursor: pointer; }
        .btn-primary { background: #2563eb; color: #fff; }
        .btn-secondary { background: #e5e7eb; color: #1f2937; }
        .btn[disabled] { opacity: 0.6; cursor: not-allowed; }
        .status { margin-top: 8px; font-size: 13px; }
        .ok { color: #047857; }
        .warn { color: #b45309; }
        .err { color: #b91c1c; }
        .preview { margin-top: 10px; max-height: 260px; overflow: auto; border: 1px solid #e5e7eb; border-radius: 6px; }
        table { width: 100%; border-collapse: collapse; font-size: 12px; }
        th, td { border-bottom: 1px solid #f3f4f6; padding: 6px 8px; text-align: left; }
        th { background: #f9fafb; position: sticky; top: 0; }
    </style>
</head>
<body>
<div class="box">
    <div class="title">Importador de PDF - AD_PEDREC</div>
    <div class="muted">Fase 1: leitura de texto do PDF e preenchimento automatico dos campos do registro atual.</div>

    <div id="dropzone" class="dropzone">
        <input id="pdfFile" type="file" accept="application/pdf" />
        <div class="muted" style="margin-top: 8px;">Voce tambem pode arrastar e soltar o arquivo .pdf aqui.</div>
    </div>

    <div class="actions">
        <button id="readBtn" class="btn btn-primary" disabled>Ler PDF</button>
        <button id="applyBtn" class="btn btn-primary" disabled>Preencher tela</button>
        <button id="closeBtn" class="btn btn-secondary">Fechar</button>
    </div>

    <div id="status" class="status">Aguardando arquivo PDF...</div>
    <div id="preview" class="preview" style="display:none"></div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/pdf.js/4.3.136/pdf.min.mjs" type="module"></script>
<script>
(function () {
    var fileInput = document.getElementById('pdfFile');
    var readBtn = document.getElementById('readBtn');
    var applyBtn = document.getElementById('applyBtn');
    var closeBtn = document.getElementById('closeBtn');
    var statusEl = document.getElementById('status');
    var previewEl = document.getElementById('preview');
    var dropzone = document.getElementById('dropzone');

    var selectedFile = null;
    var mapped = null;

    function resolveHostWindow() {
        var candidates = [window, window.parent, window.opener, window.top];

        for (var i = 0; i < candidates.length; i++) {
            var candidate = candidates[i];
            if (!candidate) continue;

            try {
                if (candidate.PdfPedrecMapper || candidate.applyPedrecImportFields) {
                    return candidate;
                }
            } catch (e) {
                // ignore cross-window access issues
            }
        }

        return window;
    }

    function setStatus(text, cssClass) {
        statusEl.className = 'status ' + (cssClass || '');
        statusEl.textContent = text;
    }

    function closeModal() {
        if (window.parent && window.parent.SanPopup) {
            try { window.parent.SanPopup.close(); return; } catch (e) { }
        }
        window.close();
    }

    function setSelected(file) {
        selectedFile = file || null;
        readBtn.disabled = !selectedFile;
        applyBtn.disabled = true;
        mapped = null;
        previewEl.style.display = 'none';
        previewEl.innerHTML = '';

        if (selectedFile) {
            setStatus('Arquivo selecionado: ' + selectedFile.name, 'ok');
        } else {
            setStatus('Aguardando arquivo PDF...', '');
        }
    }

    function sanitizeHtml(value) {
        return String(value)
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#39;');
    }

    function renderPreview(result) {
        var keys = Object.keys(result.values || {});
        if (!keys.length) {
            previewEl.style.display = 'none';
            return;
        }

        var rows = keys.map(function (key) {
            return '<tr><td>' + sanitizeHtml(key) + '</td><td>' + sanitizeHtml(result.values[key]) + '</td></tr>';
        }).join('');

        previewEl.innerHTML = [
            '<table>',
            '<thead><tr><th>Campo</th><th>Valor</th></tr></thead>',
            '<tbody>' + rows + '</tbody>',
            '</table>'
        ].join('');
        previewEl.style.display = 'block';
    }

    async function extractTextFromPdf(file) {
        var module = await import('https://cdnjs.cloudflare.com/ajax/libs/pdf.js/4.3.136/pdf.min.mjs');
        var pdfjsLib = module;
        pdfjsLib.GlobalWorkerOptions.workerSrc = 'https://cdnjs.cloudflare.com/ajax/libs/pdf.js/4.3.136/pdf.worker.min.mjs';

        var arrayBuffer = await file.arrayBuffer();
        var pdf = await pdfjsLib.getDocument({ data: arrayBuffer }).promise;
        var chunks = [];

        for (var pageNo = 1; pageNo <= pdf.numPages; pageNo++) {
            var page = await pdf.getPage(pageNo);
            var content = await page.getTextContent();
            var pageText = content.items.map(function (it) { return it.str; }).join('\n');
            chunks.push(pageText);
        }

        return chunks.join('\n');
    }

    async function readPdf() {
        if (!selectedFile) return;
        var hostWindow = resolveHostWindow();
        if (!hostWindow || !hostWindow.PdfPedrecMapper) {
            setStatus('Servico PdfPedrecMapper nao encontrado na tela principal.', 'err');
            return;
        }

        try {
            setStatus('Lendo e processando PDF...', '');
            var rawText = await extractTextFromPdf(selectedFile);
            mapped = hostWindow.PdfPedrecMapper.mapFromPdfText(rawText);
            renderPreview(mapped);

            var found = Object.keys(mapped.values || {}).length;
            var missing = (mapped.notFound || []).length;
            setStatus('Leitura concluida. Campos encontrados: ' + found + '. Nao encontrados: ' + missing + '.', missing ? 'warn' : 'ok');
            applyBtn.disabled = found === 0;
        } catch (error) {
            setStatus('Falha ao ler PDF: ' + (error && error.message ? error.message : error), 'err');
        }
    }

    function applyToForm() {
        if (!mapped || !mapped.values || !Object.keys(mapped.values).length) {
            setStatus('Nenhum dado para aplicar.', 'warn');
            return;
        }

        var hostWindow = resolveHostWindow();
        var fn = hostWindow && hostWindow.applyPedrecImportFields;
        if (typeof fn !== 'function') {
            setStatus('Funcao applyPedrecImportFields nao encontrada na tela principal.', 'err');
            return;
        }

        applyBtn.disabled = true;
        setStatus('Aplicando campos no registro atual...', '');
        fn(mapped.values, function (err, response) {
            if (err) {
                setStatus('Falha ao aplicar: ' + err, 'err');
                applyBtn.disabled = false;
                return;
            }

            var appliedCount = response && response.applied ? response.applied.length : 0;
            var skippedCount = response && response.skipped ? response.skipped.length : 0;
            setStatus('Importacao concluida. Aplicados: ' + appliedCount + '. Ignorados: ' + skippedCount + '.', skippedCount ? 'warn' : 'ok');
        });
    }

    fileInput.addEventListener('change', function (event) {
        var file = event.target.files && event.target.files[0];
        setSelected(file);
    });

    dropzone.addEventListener('dragover', function (event) {
        event.preventDefault();
        dropzone.classList.add('dragover');
    });

    dropzone.addEventListener('dragleave', function () {
        dropzone.classList.remove('dragover');
    });

    dropzone.addEventListener('drop', function (event) {
        event.preventDefault();
        dropzone.classList.remove('dragover');
        var file = event.dataTransfer && event.dataTransfer.files && event.dataTransfer.files[0];
        if (!file) return;
        fileInput.files = event.dataTransfer.files;
        setSelected(file);
    });

    readBtn.addEventListener('click', readPdf);
    applyBtn.addEventListener('click', applyToForm);
    closeBtn.addEventListener('click', closeModal);
})();
</script>
</body>
</html>

