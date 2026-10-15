(function (global) {
    function normalizeText(text) {
        return (text || "")
            .replace(/\r/g, "")
            .replace(/[ \t]+/g, " ")
            .replace(/\u00A0/g, " ")
            .trim();
    }

    function escapeRegex(text) {
        return String(text).replace(/[.*+?^${}()|[\]\\]/g, "\\$&");
    }

    function toIsoDate(raw) {
        if (!raw) return "";
        var clean = String(raw).trim();
        var match = clean.match(/(\d{2})[\/.\-](\d{2})[\/.\-](\d{4})/);
        if (!match) return clean;
        return match[3] + "-" + match[2] + "-" + match[1];
    }

    function toDecimal(raw) {
        if (!raw) return "";
        var clean = String(raw).replace(/[^0-9,.-]/g, "").trim();
        if (!clean) return "";

        if (clean.indexOf(",") >= 0) {
            clean = clean.replace(/\./g, "").replace(",", ".");
        }

        var parsed = Number(clean);
        return Number.isFinite(parsed) ? parsed : "";
    }

    function toInteger(raw) {
        if (!raw) return "";
        var digits = String(raw).replace(/\D/g, "");
        return digits || "";
    }

    var FIELD_MAP = [
        { field: "NUMNOTA", aliases: ["NRO. DOCUMENTO", "NRO DOCUMENTO", "NUMERO DOCUMENTO", "NUMNOTA"], type: "integer" },
        { field: "DTEMISSAO", aliases: ["DATA EMISSAO", "DTEMISSAO", "EMISSAO"], type: "date" },
        { field: "CODEMP", aliases: ["COD. EMPRESA", "COD EMPRESA", "CODEMP"], type: "integer" },
        { field: "CODPROD", aliases: ["CODIGO", "COD. PRODUTO", "CODPROD"], type: "integer" },
        { field: "OBS", aliases: ["OBSERVACAO", "OBS", "DESCRICAO"], type: "string" },
        { field: "CODSOL", aliases: ["SOLICITANTE", "CODSOL"], type: "integer" },
        { field: "CODRESP", aliases: ["RESPONSAVEL", "CODRESP"], type: "integer" },
        { field: "DTENTREGA", aliases: ["DATA DE ENTREGA", "DTENTREGA", "ENTREGA"], type: "date" },
        { field: "QTDE", aliases: ["QUANTIDADE", "QTDE"], type: "decimal" },
        { field: "CODVOL", aliases: ["UNIDADE", "CODVOL"], type: "string" },
        { field: "VLRUNIT", aliases: ["PRECO UNIT", "PRECO UNIT. (BRL)", "VLRUNIT"], type: "decimal" },
        { field: "VLRTOT", aliases: ["TOTAL DO ITEM", "VLRTOT", "VLR TOTAL"], type: "decimal" },
        { field: "CODCENCUS", aliases: ["CENTRO DE CUSTO", "CODCENCUS"], type: "integer" },
        { field: "CODNAT", aliases: ["COD. NATUREZA", "COD NATUREZA", "CODNAT"], type: "integer" },
        { field: "CODTIPOPER", aliases: ["COD.TIPO OPERACAO", "COD TIPO OPERACAO", "CODTIPOPER"], type: "integer" },
        { field: "CODBCO", aliases: ["COD. BANCO", "COD BANCO", "CODBCO"], type: "integer" },
        { field: "CODAGE", aliases: ["AGENCIA", "CODAGE"], type: "string" },
        { field: "CODCTABCOINT", aliases: ["CODIGO DA CONTA BANCARIA", "CODCTABCOINT"], type: "string" },
        { field: "CODTIPVENDA", aliases: ["TIPO DE NEGOCIACAO", "CODTIPVENDA"], type: "integer" }
    ];

    function findByAliases(lines, aliases) {
        for (var i = 0; i < aliases.length; i++) {
            var alias = aliases[i];
            var escaped = escapeRegex(alias);
            var lineRegex = new RegExp("^\\s*" + escaped + "\\s*[:\\-]?\\s*(.+)$", "i");

            for (var j = 0; j < lines.length; j++) {
                var line = lines[j];
                var m = line.match(lineRegex);
                if (m && m[1]) {
                    return m[1].trim();
                }

                // fallback: label and value split in adjacent lines
                if (new RegExp("^\\s*" + escaped + "\\s*$", "i").test(line) && lines[j + 1]) {
                    return String(lines[j + 1]).trim();
                }
            }

            // fallback in full text for label:value with no line break guarantees
            var textRegex = new RegExp(escaped + "\\s*[:\\-]\\s*([^\\n\\r]+)", "i");
            var full = lines.join("\n").match(textRegex);
            if (full && full[1]) {
                return full[1].trim();
            }
        }

        return "";
    }

    function castValue(type, value) {
        if (!value) return "";
        if (type === "date") return toIsoDate(value);
        if (type === "decimal") return toDecimal(value);
        if (type === "integer") return toInteger(value);
        return String(value).trim();
    }

    function mapFromPdfText(rawText) {
        var normalized = normalizeText(rawText);
        var lines = normalized.split(/\n+/).map(function (line) { return line.trim(); }).filter(Boolean);
        var values = {};
        var notFound = [];

        for (var i = 0; i < FIELD_MAP.length; i++) {
            var cfg = FIELD_MAP[i];
            var extracted = findByAliases(lines, cfg.aliases);
            var casted = castValue(cfg.type, extracted);

            if (casted === "" || casted === null || typeof casted === "undefined") {
                notFound.push(cfg.field);
                continue;
            }

            values[cfg.field] = casted;
        }

        return {
            values: values,
            notFound: notFound,
            lines: lines.length
        };
    }

    global.PdfPedrecMapper = {
        mapFromPdfText: mapFromPdfText,
        FIELD_MAP: FIELD_MAP
    };
})(window);

