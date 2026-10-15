<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="snk" uri="/WEB-INF/tld/sankhyaUtil.tld" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Dashboard de Pagamentos - OSTLC</title>
    <link rel="stylesheet" type="text/css" href="${BASE_FOLDER}/css/dashboardOsTlc.css">
    <link rel="stylesheet" type="text/css" href="css/dashboardOsTlc.css">
    <style data-inline-design-system="true">
:root {
    --color-zone-green: #22c55e;
    --color-zone-blue: #3b82f6;
    --color-zone-red: #ef4444;
    --color-zone-dark: #0f172a;
    --bg-page: #f8fafc;
    --bg-card: #ffffff;
    --bg-soft: #f8fafc;
    --bg-elevated: #ffffff;
    --table-head: rgba(248, 250, 252, 0.96);
    --row-hover: rgba(248, 250, 252, 0.72);
    --canvas-hole: #f8fafc;
    --border: #e2e8f0;
    --border-soft: #f1f5f9;
    --text-main: #0f172a;
    --text-secondary: #64748b;
    --text-muted: #94a3b8;
    --shadow: 0 12px 30px rgba(15, 23, 42, 0.06);
    --radius-card: 10px;
    --radius-control: 6px;
    --radius-soft: 8px;
    --radius-pill: 999px;
}

body[data-theme="dark"] {
    --bg-page: #0f172a;
    --bg-card: #1e293b;
    --bg-soft: #111827;
    --bg-elevated: #0f172a;
    --table-head: rgba(15, 23, 42, 0.96);
    --row-hover: rgba(255, 255, 255, 0.04);
    --canvas-hole: #111827;
    --border: #334155;
    --border-soft: #334155;
    --text-main: #f8fafc;
    --text-secondary: #cbd5e1;
    --text-muted: #94a3b8;
    --shadow: 0 14px 34px rgba(0, 0, 0, 0.22);
}

* {
    box-sizing: border-box;
}

html,
body {
    min-height: 100%;
}

body {
    margin: 0;
    color: var(--text-main);
    background: var(--bg-page);
    font-family: Inter, "Segoe UI", Arial, sans-serif;
    color-scheme: light;
}

body[data-theme="dark"] {
    color-scheme: dark;
}

h1,
h2,
h3,
p {
    margin: 0;
}

button,
input {
    font-family: inherit;
}

.dashboard-shell {
    min-height: 100vh;
    padding: 32px;
    display: flex;
    flex-direction: column;
    gap: 24px;
    overflow: hidden;
}

.topbar {
    min-height: 72px;
    padding: 14px 18px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 16px;
    border: 1px solid var(--border);
    border-radius: var(--radius-card);
    background: var(--bg-card);
    box-shadow: var(--shadow);
}

.topbar-left,
.topbar-right {
    display: flex;
    align-items: center;
    gap: 14px;
    min-width: 0;
}

.brand-mark {
    width: 132px;
    height: 42px;
    padding: 7px 10px;
    border-radius: var(--radius-control);
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
    background: var(--bg-elevated);
    border: 1px solid var(--border-soft);
    box-shadow: 0 8px 20px rgba(15, 23, 42, 0.05);
}

body[data-theme="dark"] .brand-mark {
    background: #ffffff;
    border-color: rgba(255, 255, 255, 0.88);
    box-shadow: 0 10px 24px rgba(0, 0, 0, 0.22);
}

.brand-logo {
    max-width: 100%;
    max-height: 28px;
    object-fit: contain;
    display: block;
}

.brand-fallback {
    display: none;
    color: var(--text-main);
    font-size: 13px;
    font-weight: 800;
    letter-spacing: 0.02em;
}

.topbar-divider {
    width: 1px;
    height: 36px;
    background: var(--border);
}

.live-dot {
    width: 10px;
    height: 10px;
    border-radius: var(--radius-pill);
    background: var(--color-zone-green);
    box-shadow: 0 0 0 6px rgba(34, 197, 94, 0.12);
}

.topbar-title {
    color: var(--text-main);
    font-size: 20px;
    font-weight: 800;
    line-height: 1.1;
    letter-spacing: -0.04em;
    text-transform: uppercase;
}

.topbar-sub {
    margin-top: 5px;
    color: var(--text-secondary);
    font-size: 11px;
    font-weight: 600;
    letter-spacing: 0.12em;
    text-transform: uppercase;
}

.topbar-sub strong {
    color: var(--text-main);
}

.clock {
    min-width: 78px;
    color: var(--text-main);
    font-size: 13px;
    font-weight: 700;
    text-align: center;
    font-variant-numeric: tabular-nums;
}

.ghost-button {
    min-height: 40px;
    padding: 10px 16px;
    border: 1px solid var(--border);
    border-radius: var(--radius-control);
    background: var(--bg-soft);
    color: var(--text-secondary);
    cursor: pointer;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
    box-shadow: 0 4px 14px rgba(15, 23, 42, 0.04);
    font-size: 11px;
    font-weight: 700;
    letter-spacing: 0.12em;
    text-transform: uppercase;
    transition: all 0.2s ease;
    white-space: nowrap;
}

.ghost-button:hover {
    color: var(--color-zone-green);
    border-color: rgba(34, 197, 94, 0.35);
}

.ghost-button:active {
    transform: scale(0.98);
}

.ghost-button:disabled {
    opacity: 0.55;
    cursor: not-allowed;
}

.theme-switch {
    width: 48px;
    min-width: 48px;
    height: 28px;
    min-height: 28px;
    padding: 3px;
    border: 1px solid var(--border);
    border-radius: var(--radius-pill);
    background: var(--bg-soft);
    color: var(--text-secondary);
    cursor: pointer;
    display: inline-flex;
    align-items: center;
    justify-content: flex-start;
    box-shadow: 0 4px 14px rgba(15, 23, 42, 0.04);
    transition: all 0.2s ease;
}

.theme-switch:hover {
    border-color: rgba(34, 197, 94, 0.35);
}

.theme-switch-thumb {
    width: 22px;
    height: 22px;
    border-radius: var(--radius-pill);
    background: var(--color-zone-green);
    color: #ffffff;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    font-size: 13px;
    font-family: Arial, "Segoe UI Symbol", sans-serif;
    line-height: 1;
    transform: translateX(0);
    transition: transform 0.2s ease, background 0.2s ease;
}

body[data-theme="dark"] .theme-switch {
    justify-content: flex-start;
    background: #020617;
}

body[data-theme="dark"] .theme-switch-thumb {
    transform: translateX(20px);
    background: #1e293b;
}

.summary-grid {
    display: grid;
    grid-template-columns: repeat(6, minmax(0, 1fr));
    gap: 18px;
}

.action-strip {
    display: grid;
    grid-template-columns: repeat(4, minmax(0, 1fr));
    gap: 18px;
}

.action-card {
    min-height: 96px;
    padding: 18px;
    border: 1px solid var(--border-soft);
    border-radius: var(--radius-card);
    background: var(--bg-card);
    box-shadow: var(--shadow);
    color: var(--text-main);
    cursor: pointer;
    text-align: left;
    transition: all 0.2s ease;
}

.action-card:hover {
    border-color: rgba(34, 197, 94, 0.30);
    box-shadow: 0 18px 36px rgba(34, 197, 94, 0.10);
    transform: translateY(-1px);
}

.action-card span {
    display: block;
    color: var(--text-muted);
    font-size: 10px;
    font-weight: 700;
    letter-spacing: 0.16em;
    text-transform: uppercase;
}

.action-card strong {
    display: block;
    margin-top: 9px;
    color: var(--text-main);
    font-size: 15px;
    font-weight: 800;
    letter-spacing: -0.03em;
}

.action-card small {
    display: block;
    margin-top: 8px;
    color: var(--text-secondary);
    font-size: 11px;
    font-weight: 500;
    line-height: 1.45;
}

.action-card-soft {
    background: var(--bg-soft);
}

.action-card-danger:hover {
    border-color: rgba(239, 68, 68, 0.28);
    box-shadow: 0 18px 36px rgba(239, 68, 68, 0.10);
}

.action-card.needs-config {
    border-style: dashed;
    opacity: 0.72;
}

.metric-card,
.panel-card,
.chart-card,
.info-panel {
    border: 1px solid var(--border-soft);
    border-radius: var(--radius-card);
    background: var(--bg-card);
    box-shadow: var(--shadow);
}

.metric-card {
    min-height: 134px;
    padding: 22px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    overflow: hidden;
}

.metric-card-strong {
    border-color: rgba(34, 197, 94, 0.28);
    box-shadow: 0 18px 36px rgba(34, 197, 94, 0.10);
}

.metric-card-warning {
    border-color: rgba(249, 115, 22, 0.24);
}

.metric-card-danger {
    border-color: rgba(239, 68, 68, 0.24);
}

.metric-label,
.chart-card-title {
    display: block;
    color: var(--text-muted);
    font-size: 10px;
    font-weight: 700;
    letter-spacing: 0.16em;
    text-transform: uppercase;
}

.metric-value {
    display: block;
    margin-top: 10px;
    color: var(--text-main);
    font-size: clamp(26px, 3vw, 40px);
    font-weight: 800;
    line-height: 1;
    letter-spacing: -0.04em;
    overflow-wrap: anywhere;
}

.metric-foot,
.panel-head p,
.info-panel p,
.search-info {
    color: var(--text-secondary);
    font-size: 11px;
    font-weight: 500;
    line-height: 1.55;
}

.metric-foot {
    margin-top: 12px;
}

.dashboard-layout {
    display: grid;
    grid-template-columns: 336px minmax(0, 1fr);
    gap: 24px;
    min-height: 0;
    flex: 1;
}

.charts-column {
    display: grid;
    gap: 18px;
    align-content: start;
}

.dashboard-content {
    min-width: 0;
}

.chart-card,
.info-panel {
    padding: 20px;
}

.chart-card-title {
    margin-bottom: 14px;
}

canvas {
    display: block;
    max-width: 100%;
}

.info-panel {
    display: grid;
    gap: 12px;
}

.info-panel strong {
    color: var(--text-main);
}

.panel-card {
    min-height: calc(100vh - 248px);
    padding: 24px;
    display: flex;
    flex-direction: column;
    overflow: hidden;
}

.panel-head {
    margin-bottom: 18px;
    display: flex;
    align-items: flex-start;
    justify-content: space-between;
    gap: 16px;
    flex-shrink: 0;
}

.panel-head h2 {
    color: var(--text-main);
    font-size: 20px;
    font-weight: 800;
    letter-spacing: -0.04em;
    text-transform: uppercase;
}

.panel-head p {
    margin-top: 6px;
}

.tabs-toolbar {
    margin-bottom: 8px;
    display: grid;
    grid-template-columns: minmax(360px, 0.9fr) minmax(560px, 1.1fr);
    align-items: center;
    justify-content: space-between;
    gap: 14px;
    flex-shrink: 0;
}

.filters-bar {
    min-width: 0;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    gap: 8px;
}

.date-filter {
    height: 42px;
    padding: 0 10px;
    border: 1px solid var(--border-soft);
    border-radius: var(--radius-soft);
    background: var(--bg-soft);
    display: flex;
    align-items: center;
    gap: 8px;
    box-shadow: 0 4px 14px rgba(15, 23, 42, 0.03);
}

.date-filter label {
    color: var(--text-muted);
    font-size: 10px;
    font-weight: 700;
    letter-spacing: 0.12em;
    text-transform: uppercase;
}

.date-filter input {
    width: 122px;
    border: 0;
    outline: none;
    background: transparent;
    color: var(--text-main);
    font-size: 11px;
    font-weight: 600;
}

.tabs-nav {
    min-width: 0;
    display: grid;
    grid-template-columns: repeat(3, minmax(0, max-content));
    gap: 10px;
    align-items: center;
}

.tab-button {
    min-height: 40px;
    padding: 9px 12px;
    border: 1px solid var(--border);
    border-radius: var(--radius-control);
    background: var(--bg-soft);
    color: var(--text-secondary);
    cursor: pointer;
    display: inline-flex;
    align-items: center;
    gap: 8px;
    font-size: 11px;
    font-weight: 700;
    letter-spacing: 0.1em;
    text-transform: uppercase;
    transition: all 0.2s ease;
    white-space: nowrap;
}

.tab-button.is-active {
    border-color: rgba(34, 197, 94, 0.35);
    background: rgba(34, 197, 94, 0.10);
    color: var(--color-zone-green);
}

.tab-count {
    min-width: 24px;
    padding: 2px 7px;
    border-radius: var(--radius-pill);
    background: rgba(15, 23, 42, 0.08);
    color: var(--text-secondary);
    font-size: 10px;
    text-align: center;
}

body[data-theme="dark"] .tab-count {
    background: rgba(255, 255, 255, 0.08);
}

body[data-theme="dark"] .action-card-soft {
    background: rgba(15, 23, 42, 0.78);
}

.table-search {
    min-width: 300px;
    flex: 1 1 300px;
    display: flex;
    align-items: center;
    gap: 8px;
}

.table-search input {
    width: 100%;
    height: 42px;
    padding: 0 14px;
    border: 1px solid var(--border-soft);
    border-radius: var(--radius-soft);
    outline: none;
    background: var(--bg-soft);
    color: var(--text-main);
    font-size: 13px;
    font-weight: 500;
    box-shadow: 0 4px 14px rgba(15, 23, 42, 0.03);
    transition: all 0.2s ease;
}

.table-search input::placeholder {
    color: var(--text-muted);
}

.table-search input:focus,
.date-filter:focus-within {
    border-color: var(--color-zone-green);
    box-shadow: 0 0 0 4px rgba(34, 197, 94, 0.10);
}

.search-info {
    min-height: 18px;
    margin-bottom: 10px;
}

#tablesArea {
    min-height: 0;
    flex: 1;
    overflow: auto;
}

.tab-panel {
    display: none;
}

.tab-panel.is-active {
    display: block;
}

.table-wrap {
    border: 1px solid var(--border-soft);
    border-radius: var(--radius-card);
    overflow: auto;
    background: var(--bg-card);
}

.monitor-table {
    width: 100%;
    min-width: 1100px;
    border-collapse: collapse;
    background: var(--bg-card);
}

.monitor-table th,
.monitor-table td {
    padding: 13px 14px;
    border-bottom: 1px solid var(--border-soft);
    color: var(--text-secondary);
    text-align: left;
    vertical-align: middle;
}

.monitor-table th {
    position: sticky;
    top: 0;
    z-index: 1;
    background: var(--table-head);
    color: var(--text-muted);
    font-size: 10px;
    font-weight: 700;
    letter-spacing: 0.12em;
    text-transform: uppercase;
}

.monitor-table td {
    max-width: 300px;
    font-size: 11px;
    font-weight: 500;
}

.monitor-table tbody tr {
    transition: background 0.2s ease;
}

.monitor-table tbody tr:hover {
    background: var(--row-hover);
}

.monitor-table th:last-child,
.monitor-table td:last-child {
    min-width: 168px;
    text-align: center;
}

.monitor-table td strong,
.monitor-table td small {
    display: block;
}

.monitor-table td strong {
    color: var(--text-main);
    font-size: 12px;
    font-weight: 700;
}

.monitor-table td small {
    margin-top: 4px;
    color: var(--text-muted);
    font-size: 10px;
    font-weight: 600;
    letter-spacing: 0.05em;
    text-transform: uppercase;
}

.status-badge {
    width: max-content;
    max-width: 100%;
    min-height: 26px;
    padding: 5px 9px;
    border-radius: var(--radius-pill);
    display: inline-flex;
    align-items: center;
    justify-content: center;
    white-space: nowrap;
    font-size: 10px;
    font-weight: 700;
    letter-spacing: 0.06em;
    text-transform: uppercase;
}

.mini-action {
    min-height: 30px;
    min-width: 74px;
    padding: 7px 12px;
    border: 1px solid rgba(34, 197, 94, 0.28);
    border-radius: var(--radius-pill);
    background: rgba(34, 197, 94, 0.10);
    color: var(--color-zone-green);
    cursor: pointer;
    font-size: 10px;
    font-weight: 800;
    letter-spacing: 0.08em;
    text-transform: uppercase;
    white-space: nowrap;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    line-height: 1;
}

.mini-action:hover {
    border-color: rgba(34, 197, 94, 0.48);
    background: rgba(34, 197, 94, 0.16);
}

.action-cell {
    min-width: 150px;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
    flex-wrap: nowrap;
}

.select-cell {
    width: 38px;
    text-align: center;
}

.os-select {
    width: 16px;
    height: 16px;
    cursor: pointer;
    accent-color: var(--color-zone-blue);
}

.mini-action-danger {
    border-color: rgba(239, 68, 68, 0.24);
    background: rgba(239, 68, 68, 0.10);
    color: var(--color-zone-red);
}

.mini-action-danger:hover {
    border-color: rgba(239, 68, 68, 0.42);
    background: rgba(239, 68, 68, 0.15);
}

.status-success {
    background: rgba(34, 197, 94, 0.10);
    color: var(--color-zone-green);
}

.status-warning {
    background: rgba(249, 115, 22, 0.10);
    color: #f97316;
}

.status-danger {
    background: rgba(239, 68, 68, 0.10);
    color: var(--color-zone-red);
}

.status-muted {
    background: rgba(100, 116, 139, 0.10);
    color: var(--text-secondary);
}

.empty-state {
    padding: 34px;
    color: var(--text-secondary);
    text-align: center;
    font-size: 12px;
    font-weight: 500;
}

.modal-backdrop {
    position: fixed;
    inset: 0;
    z-index: 3000;
    display: none;
    place-items: center;
    padding: 20px;
    background: rgba(15, 23, 42, 0.80);
    backdrop-filter: blur(8px);
}

.modal-backdrop.is-visible {
    display: grid;
}

.modal-card {
    width: min(620px, 96vw);
    max-height: 90vh;
    border: 1px solid var(--border);
    border-radius: var(--radius-card);
    background: var(--bg-card);
    box-shadow: 0 24px 60px rgba(15, 23, 42, 0.22);
    display: flex;
    flex-direction: column;
    overflow: hidden;
}

.modal-card-large {
    width: min(860px, 96vw);
}

.modal-head,
.modal-actions {
    padding: 16px 18px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 14px;
    background: var(--bg-soft);
}

.modal-head {
    border-bottom: 1px solid var(--border-soft);
}

.modal-actions {
    justify-content: flex-end;
    border-top: 1px solid var(--border-soft);
}

.modal-head h3 {
    margin: 0;
    color: var(--text-main);
    font-size: 18px;
    font-weight: 800;
    letter-spacing: -0.03em;
    text-transform: uppercase;
}

.modal-head p {
    margin: 4px 0 0;
    color: var(--text-secondary);
    font-size: 11px;
    font-weight: 600;
    letter-spacing: 0.10em;
    text-transform: uppercase;
}

.modal-body {
    padding: 18px;
    overflow: auto;
}

.icon-button {
    width: 36px;
    height: 36px;
    border: 1px solid var(--border);
    border-radius: var(--radius-control);
    background: var(--bg-elevated);
    color: var(--text-secondary);
    cursor: pointer;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    font-size: 20px;
}

.primary-action {
    border-color: rgba(34, 197, 94, 0.45);
    background: var(--color-zone-green);
    color: #ffffff;
}

.progress-track {
    height: 10px;
    overflow: hidden;
    border-radius: var(--radius-pill);
    background: var(--border);
}

.progress-bar {
    width: 0;
    height: 100%;
    border-radius: var(--radius-pill);
    background: var(--color-zone-blue);
    transition: width 0.22s ease;
}

.progress-bar.is-success {
    background: var(--color-zone-green);
}

.progress-bar.is-error {
    background: var(--color-zone-red);
}

.result-box,
.hint-box {
    margin-top: 14px;
    padding: 14px;
    border: 1px solid var(--border-soft);
    border-radius: var(--radius-soft);
    background: var(--bg-soft);
    color: var(--text-secondary);
    font-size: 12px;
    font-weight: 600;
    line-height: 1.5;
}

.hint-box.is-error {
    border-color: rgba(239, 68, 68, 0.24);
    background: rgba(239, 68, 68, 0.08);
    color: var(--color-zone-red);
}

.hint-box.is-success {
    border-color: rgba(34, 197, 94, 0.24);
    background: rgba(34, 197, 94, 0.08);
    color: var(--color-zone-green);
}

.config-grid {
    display: grid;
    grid-template-columns: repeat(3, minmax(0, 1fr));
    gap: 12px;
}

.config-grid-two {
    grid-template-columns: repeat(2, minmax(0, 1fr));
}

.config-field {
    padding: 12px;
    border: 1px solid var(--border-soft);
    border-radius: var(--radius-soft);
    background: var(--bg-soft);
}

.config-field span {
    display: block;
    margin-bottom: 8px;
    color: var(--text-muted);
    font-size: 10px;
    font-weight: 800;
    letter-spacing: 0.12em;
    text-transform: uppercase;
}

.config-field input {
    width: 100%;
    height: 38px;
    padding: 0 10px;
    border: 1px solid var(--border);
    border-radius: var(--radius-control);
    outline: none;
    background: var(--bg-elevated);
    color: var(--text-main);
    font-size: 12px;
    font-weight: 700;
}

::-webkit-scrollbar {
    width: 6px;
    height: 6px;
}

::-webkit-scrollbar-track {
    background: transparent;
}

::-webkit-scrollbar-thumb {
    border-radius: var(--radius-control);
    background: var(--border);
}

::selection {
    background: rgba(34, 197, 94, 0.25);
    color: inherit;
}

@media (max-width: 1180px) {
    .summary-grid,
    .action-strip {
        grid-template-columns: repeat(2, minmax(0, 1fr));
    }

    .dashboard-layout {
        grid-template-columns: minmax(0, 1fr);
    }

    .tabs-toolbar {
        grid-template-columns: minmax(0, 1fr);
    }

    .charts-column {
        grid-template-columns: repeat(2, minmax(0, 1fr));
    }

    .info-panel {
        grid-column: 1 / -1;
    }

    .panel-card {
        min-height: auto;
    }
}

@media (max-width: 820px) {
    .dashboard-shell {
        padding: 16px;
    }

    .topbar,
    .topbar-left,
    .topbar-right,
    .filters-bar {
        align-items: stretch;
        flex-direction: column;
    }

    .tabs-toolbar {
        grid-template-columns: minmax(0, 1fr);
    }

    .topbar-divider,
    .live-dot {
        display: none;
    }

    .summary-grid,
    .action-strip,
    .charts-column {
        grid-template-columns: minmax(0, 1fr);
    }

    .config-grid {
        grid-template-columns: minmax(0, 1fr);
    }

    .table-search {
        width: 100%;
        min-width: 0;
        flex: 1 1 auto;
    }

    .date-filter input {
        width: 100%;
    }

    .tabs-nav {
        width: 100%;
        display: flex;
        flex-wrap: nowrap;
        overflow-x: auto;
        padding-bottom: 4px;
    }

    .tab-button {
        white-space: nowrap;
    }
}

    </style>
    <snk:load/>
</head>
<body>
    <snk:query var="resumoParceiros">
        <%
            String queryResumo =
                "WITH BASE AS ( " +
                "    SELECT " +
                "        OST.NROOS, " +
                "        OST.DATAABERTURA, " +
                "        OST.CNPJ AS CNPJ_OSTLC, " +
                "        OST.STATUSOS, " +
                "        NVL(OST.OSPAGA, 'N') AS OSPAGA, " +
                "        OST.NUFINGER, " +
                "        OST.NUNOTAGER, " +
                "        PAR.CODPARC, " +
                "        PAR.NOMEPARC, " +
                "        PAR.CGC_CPF, " +
                "        NVL(PAR.AD_TAXAMANUTENCAO, 0) AS AD_VLRFIXSER, " +
                "        CASE WHEN EXISTS (SELECT 1 FROM AD_OSPECA PEC INNER JOIN AD_TCLPCA TCL ON UPPER(TRIM(TCL.REFERENCIA)) = UPPER(TRIM(PEC.REFERENCIA)) WHERE PEC.NROOS = OST.NROOS) THEN 'S' ELSE 'N' END AS TEM_PECA " +
                "    FROM AD_OSTLC OST " +
                "    INNER JOIN TGFPAR PAR ON REGEXP_REPLACE(PAR.CGC_CPF, '[^0-9]', '') = REGEXP_REPLACE(OST.CNPJ, '[^0-9]', '') " +
                "    WHERE OST.CNPJ IS NOT NULL " +
                "      AND OST.STATUSOS = 'Finalizada' " +
                ") " +
                "SELECT " +
                "    CODPARC, " +
                "    NOMEPARC, " +
                "    CGC_CPF AS CNPJ_PARCEIRO, " +
                "    CNPJ_OSTLC, " +
                "    AD_VLRFIXSER, " +
                "    COUNT(*) AS QTD_OS, " +
                "    SUM(CASE WHEN OSPAGA = 'S' THEN 1 ELSE 0 END) AS QTD_GERADA, " +
                "    SUM(CASE WHEN OSPAGA = 'S' THEN 0 ELSE 1 END) AS QTD_PENDENTE, " +
                "    SUM(CASE WHEN TEM_PECA = 'S' THEN 1 ELSE 0 END) AS QTD_COM_PECA, " +
                "    SUM(CASE WHEN TEM_PECA = 'N' THEN 1 ELSE 0 END) AS QTD_SEM_PECA, " +
                "    AD_VLRFIXSER * SUM(CASE WHEN OSPAGA = 'S' THEN 1 ELSE 0 END) AS VLR_GERADO, " +
                "    AD_VLRFIXSER * SUM(CASE WHEN OSPAGA = 'S' THEN 0 ELSE 1 END) AS VLR_PENDENTE, " +
                "    AD_VLRFIXSER * SUM(CASE WHEN TEM_PECA = 'S' THEN 1 ELSE 0 END) AS VLR_COM_PECA, " +
                "    AD_VLRFIXSER * SUM(CASE WHEN TEM_PECA = 'N' THEN 1 ELSE 0 END) AS VLR_SEM_PECA, " +
                "    AD_VLRFIXSER * COUNT(*) AS VLR_TOTAL " +
                "FROM BASE " +
                "GROUP BY CODPARC, NOMEPARC, CGC_CPF, CNPJ_OSTLC, AD_VLRFIXSER " +
                "ORDER BY VLR_TOTAL DESC, NOMEPARC";
            out.println(queryResumo);
        %>
    </snk:query>

    <snk:query var="osDetalhes">
        <%
            String queryDetalhe =
                "SELECT " +
                "    OST.NROOS, " +
                "    OST.DATAABERTURA, " +
                "    OST.CNPJ AS CNPJ_OSTLC, " +
                "    OST.STATUSOS, " +
                "    NVL(OST.OSPAGA, 'N') AS OSPAGA, " +
                "    OST.NUFINGER, " +
                "    OST.NUNOTAGER, " +
                "    PAR.CODPARC, " +
                "    PAR.NOMEPARC, " +
                "    PAR.CGC_CPF AS CNPJ_PARCEIRO, " +
                "    NVL(PAR.AD_TAXAMANUTENCAO, 0) AS AD_VLRFIXSER, " +
                "    CASE WHEN EXISTS (SELECT 1 FROM AD_OSPECA PEC INNER JOIN AD_TCLPCA TCL ON UPPER(TRIM(TCL.REFERENCIA)) = UPPER(TRIM(PEC.REFERENCIA)) WHERE PEC.NROOS = OST.NROOS) THEN 'S' ELSE 'N' END AS TEM_PECA, " +
                "    CASE " +
                "        WHEN EXISTS (SELECT 1 FROM AD_OSPECA PEC INNER JOIN AD_TCLPCA TCL ON UPPER(TRIM(TCL.REFERENCIA)) = UPPER(TRIM(PEC.REFERENCIA)) WHERE PEC.NROOS = OST.NROOS) THEN 'Nota de Compra' " +
                "        ELSE 'Financeiro Direto' " +
                "    END AS FORMA_PAGAMENTO, " +
                "    NVL(PAR.AD_TAXAMANUTENCAO, 0) AS VLR_A_PAGAR " +
                "FROM AD_OSTLC OST " +
                "INNER JOIN TGFPAR PAR ON REGEXP_REPLACE(PAR.CGC_CPF, '[^0-9]', '') = REGEXP_REPLACE(OST.CNPJ, '[^0-9]', '') " +
                "WHERE OST.CNPJ IS NOT NULL " +
                "  AND OST.STATUSOS = 'Finalizada' " +
                "ORDER BY PAR.NOMEPARC, OST.NROOS";
            out.println(queryDetalhe);
        %>
    </snk:query>

    <main class="dashboard-shell">
        <header class="topbar">
            <div class="topbar-left">
                <div class="brand-mark">
                    <img class="brand-logo" src="https://acdn-us.mitiendanube.com/stores/005/839/688/themes/common/logo-1297653022-1743705838-bbbe0334fa873803b1231225499070071743705839-480-0.webp" alt="Polyvox" onerror="this.style.display='none'; this.nextElementSibling.style.display='block';">
                    <span class="brand-fallback">Polyvox</span>
                </div>
                <div class="topbar-divider"></div>
                <span class="live-dot"></span>
                <div>
                    <div class="topbar-title">Dashboard de Pagamentos por Posto</div>
                    <div class="topbar-sub">Atualizado em <span id="lastRefresh">--:--:--</span></div>
                </div>
            </div>
            <div class="topbar-right">
                <span class="clock" id="clockDisplay">--:--:--</span>
                <button class="theme-switch" type="button" id="themeToggle" onclick="alternarTema()" title="Alternar tema" aria-label="Alternar tema"><span class="theme-switch-thumb" id="themeIcon">&#9728;</span></button>
                <button class="ghost-button" type="button" onclick="atualizarPagina(this)">Atualizar agora</button>
            </div>
        </header>

        <section class="summary-grid">
            <article class="metric-card metric-card-strong">
                <span class="metric-label">Total a pagar</span>
                <strong class="metric-value" id="metricTotal">R$ 0,00</strong>
                <small class="metric-foot">Taxa Administrativa x OS finalizadas</small>
            </article>
            <article class="metric-card">
                <span class="metric-label">Com pecas</span>
                <strong class="metric-value" id="metricComPeca">R$ 0,00</strong>
                <small class="metric-foot">Gerar Nota de Compra</small>
            </article>
            <article class="metric-card">
                <span class="metric-label">Sem peca</span>
                <strong class="metric-value" id="metricSemPeca">R$ 0,00</strong>
                <small class="metric-foot">Financeiro Direto</small>
            </article>
            <article class="metric-card metric-card-strong">
                <span class="metric-label">OS paga</span>
                <strong class="metric-value" id="metricGerado">R$ 0,00</strong>
                <small class="metric-foot"><span id="metricQtdGerada">0</span> OS paga</small>
            </article>
            <article class="metric-card metric-card-warning">
                <span class="metric-label">OS nao paga</span>
                <strong class="metric-value" id="metricPendente">R$ 0,00</strong>
                <small class="metric-foot"><span id="metricQtdPendente">0</span> OS nao paga</small>
            </article>
            <article class="metric-card">
                <span class="metric-label">OS finalizadas</span>
                <strong class="metric-value" id="metricQtdOs">0</strong>
                <small class="metric-foot"><span id="metricParceiros">0</span> parceiros com CNPJ vinculado</small>
            </article>
        </section>

        <section class="action-strip">
            <button class="action-card action-card-danger" type="button" data-action-key="desfazerDocumentos" onclick="executarDesfazerFiltrado()">
                <span>Operacao</span>
                <strong>Desfazer geracao</strong>
                <small>OS nao paga e limpa Nro financeiro/Nota geradas.</small>
            </button>
            <button class="action-card" type="button" data-action-key="geraPagamento" onclick="executarPagamentoSelecionadas()">
                <span>Selecionar</span>
                <strong>Gerar selecionadas</strong>
                <small>Paga apenas as OS marcadas na tabela detalhada.</small>
            </button>
            <button class="action-card" type="button" data-action-key="syncPeriodo" onclick="executarSyncPeriodo()">
                <span>Periodo</span>
                <strong>OS Sync p/ Data</strong>
                <small>Usa os filtros De/Ate informados no dashboard.</small>
            </button>
            <button class="action-card" type="button" data-action-key="geraPagamento" onclick="executarPagamentoFiltrado()">
                <span>Pagamento</span>
                <strong>Gerar Pagamento</strong>
                <small>Executa para os CNPJs filtrados/visiveis.</small>
            </button>
            <button class="action-card action-card-soft" type="button" onclick="abrirConfiguracaoAcoes()">
                <span>Configuracao</span>
                <strong>Configurar acoes</strong>
                <small>Ajuste IDs se mudarem no ambiente.</small>
            </button>
        </section>

        <section class="dashboard-layout">
            <aside class="charts-column">
                <div class="chart-card">
                    <div class="chart-card-title">Distribuicao do montante</div>
                    <canvas id="paymentChart" width="280" height="190"></canvas>
                </div>
                <div class="info-panel">
                    <span class="metric-label">Regra aplicada</span>
                    <p><strong>Com pecas</strong> vira Nota de Compra. <strong>Sem peca</strong> vira Financeiro Direto.</p>
                    <p>O valor de cada OS Finalizada considera a <strong>Taxa Administrativa</strong> do parceiro encontrado pelo CNPJ.</p>
                </div>
            </aside>

            <section class="dashboard-content">
                <div class="panel-card">
                    <div class="panel-head">
                        <div>
                            <h2>Montante por parceiro</h2>
                            <p>Resumo consolidado por CNPJ/posto, considerando somente OS com STATUSOS Finalizada.</p>
                        </div>
                    </div>

                    <div class="tabs-toolbar">
                        <div class="tabs-nav" id="statusTabs"></div>
                        <div class="filters-bar">
                            <div class="date-filter">
                                <label for="dateStart">De</label>
                                <input type="date" id="dateStart">
                            </div>
                            <div class="date-filter">
                                <label for="dateEnd">Ate</label>
                                <input type="date" id="dateEnd">
                            </div>
                            <div class="table-search">
                                <input type="text" id="tabSearchInput" placeholder="Buscar parceiro, CNPJ, NROOS..." autocomplete="off">
                                <button class="ghost-button" type="button" id="tabSearchClear">Limpar</button>
                            </div>
                        </div>
                    </div>
                    <div class="search-info" id="tabSearchInfo"></div>
                    <div id="tablesArea">
                        <div class="empty-state">Carregando dados...</div>
                    </div>
                </div>
            </section>
        </section>

        <table id="resumoDataSource" style="display:none">
            <tbody>
                <c:forEach items="${resumoParceiros.rows}" var="row">
                    <tr class="js-resumo-row"
                        data-codparc="<c:out value="${row.CODPARC}" />"
                        data-nomeparc="<c:out value="${row.NOMEPARC}" />"
                        data-cnpjparceiro="<c:out value="${row.CNPJ_PARCEIRO}" />"
                        data-cnpjostlc="<c:out value="${row.CNPJ_OSTLC}" />"
                        data-vlrfixser="<c:out value="${row.AD_VLRFIXSER}" />"
                        data-qtdos="<c:out value="${row.QTD_OS}" />"
                        data-qtdgerada="<c:out value="${row.QTD_GERADA}" />"
                        data-qtdpendente="<c:out value="${row.QTD_PENDENTE}" />"
                        data-qtdcompeca="<c:out value="${row.QTD_COM_PECA}" />"
                        data-qtdsempeca="<c:out value="${row.QTD_SEM_PECA}" />"
                        data-vlrgerado="<c:out value="${row.VLR_GERADO}" />"
                        data-vlrpendente="<c:out value="${row.VLR_PENDENTE}" />"
                        data-vlrcompeca="<c:out value="${row.VLR_COM_PECA}" />"
                        data-vlrsempeca="<c:out value="${row.VLR_SEM_PECA}" />"
                        data-vlrtotal="<c:out value="${row.VLR_TOTAL}" />"
                    ></tr>
                </c:forEach>
            </tbody>
        </table>

        <table id="detalheDataSource" style="display:none">
            <tbody>
                <c:forEach items="${osDetalhes.rows}" var="row">
                    <tr class="js-detalhe-row"
                        data-nroos="<c:out value="${row.NROOS}" />"
                        data-dataabertura="<c:out value="${row.DATAABERTURA}" />"
                        data-cnpjostlc="<c:out value="${row.CNPJ_OSTLC}" />"
                        data-statusos="<c:out value="${row.STATUSOS}" />"
                        data-ospaga="<c:out value="${row.OSPAGA}" />"
                        data-nufinger="<c:out value="${row.NUFINGER}" />"
                        data-nunotager="<c:out value="${row.NUNOTAGER}" />"
                        data-codparc="<c:out value="${row.CODPARC}" />"
                        data-nomeparc="<c:out value="${row.NOMEPARC}" />"
                        data-cnpjparceiro="<c:out value="${row.CNPJ_PARCEIRO}" />"
                        data-vlrfixser="<c:out value="${row.AD_VLRFIXSER}" />"
                        data-tempeca="<c:out value="${row.TEM_PECA}" />"
                        data-formapagamento="<c:out value="${row.FORMA_PAGAMENTO}" />"
                        data-vlrapagar="<c:out value="${row.VLR_A_PAGAR}" />"
                    ></tr>
                </c:forEach>
            </tbody>
        </table>

    </main>

    <div class="modal-backdrop" id="actionModal" onclick="fecharModalAcao()">
        <section class="modal-card" onclick="event.stopPropagation()">
            <header class="modal-head">
                <div>
                    <h3 id="actionTitle">Execucao</h3>
                    <p id="actionSub">Aguardando confirmacao</p>
                </div>
                <button class="icon-button" type="button" onclick="fecharModalAcao()">x</button>
            </header>
            <div class="modal-body">
                <div class="progress-track"><div class="progress-bar" id="actionProgress"></div></div>
                <div class="result-box" id="actionResult">Pronto para executar.</div>
            </div>
            <footer class="modal-actions">
                <button class="ghost-button" type="button" onclick="fecharModalAcao()">Fechar</button>
                <button class="ghost-button primary-action" type="button" id="reloadAfterAction" onclick="atualizarPagina(this)" style="display:none;">Atualizar dashboard</button>
            </footer>
        </section>
    </div>

    <div class="modal-backdrop" id="syncDateModal" onclick="fecharModalSyncPeriodo()">
        <section class="modal-card" onclick="event.stopPropagation()">
            <header class="modal-head">
                <div>
                    <h3>OS Sync p/ Data</h3>
                    <p>Informe o periodo para sincronizar OS no Telecontrol.</p>
                </div>
                <button class="icon-button" type="button" onclick="fecharModalSyncPeriodo()">x</button>
            </header>
            <div class="modal-body">
                <div class="config-grid config-grid-two">
                    <label class="config-field">
                        <span>Data inicio</span>
                        <input type="date" id="syncDateStart">
                    </label>
                    <label class="config-field">
                        <span>Data fim</span>
                        <input type="date" id="syncDateEnd">
                    </label>
                </div>
                <div class="hint-box">Estas datas serao enviadas para os parametros P_DATA_INICIO e P_DATA_FIM da action Java.</div>
            </div>
            <footer class="modal-actions">
                <button class="ghost-button" type="button" onclick="fecharModalSyncPeriodo()">Cancelar</button>
                <button class="ghost-button primary-action" type="button" onclick="confirmarSyncPeriodo()">Executar sync</button>
            </footer>
        </section>
    </div>

    <div class="modal-backdrop" id="configModal" onclick="fecharConfiguracaoAcoes()">
        <section class="modal-card modal-card-large" onclick="event.stopPropagation()">
            <header class="modal-head">
                <div>
                    <h3>Configuracao das acoes</h3>
                    <p>IDs internos das rotinas Java cadastradas na AD_OSTLC.</p>
                </div>
                <button class="icon-button" type="button" onclick="fecharConfiguracaoAcoes()">x</button>
            </header>
            <div class="modal-body">
                <div class="config-grid" id="configGrid"></div>
                <div class="hint-box">Os valores padrao sao 83, 134 e 136 conforme as acoes da tela AD_OSTLC. Se algum ID mudar no ambiente, ajuste aqui sem alterar o pacote.</div>
            </div>
            <footer class="modal-actions">
                <button class="ghost-button" type="button" onclick="fecharConfiguracaoAcoes()">Cancelar</button>
                <button class="ghost-button primary-action" type="button" onclick="salvarConfiguracaoAcoes()">Salvar</button>
            </footer>
        </section>
    </div>
<script type="text/javascript">
        var _tabItems = { resumo: [], comPeca: [], semPeca: [] };
        var ACTIONS = {
            desfazerDocumentos: { id: '', label: '[ZAYA.IT] - Desfaz Pagamento OS' },
            syncPeriodo: { id: '134', label: '[ZAYA.IT] - OS Sync p/ Data' },
            geraPagamento: { id: '136', label: '[ZAYA.IT] - Gera Pagamento' }
        };

        function normalizarTexto(v) {
            return v == null ? '' : String(v).replace(/^\s+|\s+$/g, '');
        }
        function normalizarBusca(v) {
            var s = normalizarTexto(v).toUpperCase();
            if (s && s.normalize) s = s.normalize('NFD').replace(/[\u0300-\u036f]/g, '');
            return s.replace(/[^A-Z0-9 ]/g, '');
        }
        function somenteDigitos(v) {
            return normalizarTexto(v).replace(/\D/g, '');
        }
        function esc(s) {
            return String(s || '').replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
        }
        function getCookie(name) {
            var value = '; ' + document.cookie;
            var parts = value.split('; ' + name + '=');
            if (parts.length === 2) return parts.pop().split(';').shift();
            return '';
        }
        function storageKey() {
            return 'zaya.telecontrol.dashboard.actions';
        }
        function themeStorageKey() {
            return 'zaya.telecontrol.dashboard.theme';
        }
        function cssVar(nome, fallback) {
            var valor = getComputedStyle(document.body).getPropertyValue(nome);
            return normalizarTexto(valor) || fallback;
        }
        function aplicarTema(theme) {
            var modo = theme === 'dark' ? 'dark' : 'light';
            document.body.setAttribute('data-theme', modo);
            localStorage.setItem(themeStorageKey(), modo);
            var btn = document.getElementById('themeToggle');
            var icon = document.getElementById('themeIcon');
            if (btn) {
                btn.title = modo === 'dark' ? 'Alternar para modo claro' : 'Alternar para modo escuro';
                btn.setAttribute('aria-label', btn.title);
            }
            if (icon) icon.innerHTML = modo === 'dark' ? '&#9790;' : '&#9728;';
            if (window.__ultimoGraficoPagamento) {
                desenharGraficoPagamento(window.__ultimoGraficoPagamento.comPeca, window.__ultimoGraficoPagamento.semPeca);
            }
        }
        function carregarTema() {
            aplicarTema(localStorage.getItem(themeStorageKey()) || 'light');
        }
        function alternarTema() {
            var atual = document.body.getAttribute('data-theme') === 'dark' ? 'dark' : 'light';
            aplicarTema(atual === 'dark' ? 'light' : 'dark');
        }
        function carregarActionIds() {
            try {
                var saved = JSON.parse(localStorage.getItem(storageKey()) || '{}');
                for (var k in saved) {
                    if (ACTIONS[k] && normalizarTexto(saved[k])) ACTIONS[k].id = normalizarTexto(saved[k]);
                }
            } catch (e) {}
            atualizarEstadoBotoesAcao();
        }
        function atualizarEstadoBotoesAcao() {
            var btns = document.querySelectorAll('[data-action-key]');
            for (var i = 0; i < btns.length; i++) {
                var key = btns[i].getAttribute('data-action-key');
                if (ACTIONS[key] && !ACTIONS[key].id) btns[i].classList.add('needs-config');
                else btns[i].classList.remove('needs-config');
            }
        }
        function abrirConfiguracaoAcoes() {
            var html = '';
            for (var k in ACTIONS) {
                if (!ACTIONS.hasOwnProperty(k)) continue;
                html += '<label class="config-field"><span>' + esc(ACTIONS[k].label) + '</span>' +
                    '<input type="text" data-config-key="' + esc(k) + '" value="' + esc(ACTIONS[k].id) + '" placeholder="ID da acao"></label>';
            }
            document.getElementById('configGrid').innerHTML = html;
            document.getElementById('configModal').classList.add('is-visible');
        }
        function fecharConfiguracaoAcoes() {
            document.getElementById('configModal').classList.remove('is-visible');
        }
        function salvarConfiguracaoAcoes() {
            var inputs = document.querySelectorAll('[data-config-key]');
            var saved = {};
            for (var i = 0; i < inputs.length; i++) {
                var key = inputs[i].getAttribute('data-config-key');
                ACTIONS[key].id = normalizarTexto(inputs[i].value);
                saved[key] = ACTIONS[key].id;
            }
            localStorage.setItem(storageKey(), JSON.stringify(saved));
            fecharConfiguracaoAcoes();
            atualizarEstadoBotoesAcao();
        }
        function toNumber(v) {
            var s = normalizarTexto(v);
            if (s.indexOf(',') >= 0 && s.indexOf('.') >= 0) s = s.replace(/\./g, '').replace(',', '.');
            else if (s.indexOf(',') >= 0) s = s.replace(',', '.');
            var n = Number(s);
            return isNaN(n) ? 0 : n;
        }
        function formatCurrency(v) {
            return Number(v || 0).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
        }
        function pad(n) {
            return n < 10 ? '0' + n : String(n);
        }
        function parseDateValue(v) {
            var s = normalizarTexto(v);
            if (!s) return '';
            var m = s.match(/^(\d{2})\/(\d{2})\/(\d{4})/);
            if (m) return m[3] + '-' + m[2] + '-' + m[1];
            m = s.match(/^(\d{4})-(\d{2})-(\d{2})/);
            if (m) return m[1] + '-' + m[2] + '-' + m[3];
            return '';
        }
        function atualizarRelogio() {
            var d = new Date();
            var value = pad(d.getHours()) + ':' + pad(d.getMinutes()) + ':' + pad(d.getSeconds());
            var clock = document.getElementById('clockDisplay');
            if (clock) clock.textContent = value;
        }
        function atualizarPagina(btn) {
            if (btn) {
                btn.textContent = 'Carregando...';
                btn.disabled = true;
            }
            window.location.reload();
        }
        function abrirModalAcao(titulo, subtitulo) {
            document.getElementById('actionTitle').textContent = titulo;
            document.getElementById('actionSub').textContent = subtitulo || '';
            document.getElementById('actionResult').textContent = 'Executando...';
            document.getElementById('reloadAfterAction').style.display = 'none';
            document.getElementById('actionProgress').className = 'progress-bar';
            document.getElementById('actionProgress').style.width = '35%';
            document.getElementById('actionModal').classList.add('is-visible');
        }
        function fecharModalAcao() {
            document.getElementById('actionModal').classList.remove('is-visible');
        }
        function formatDateParam(iso) {
            var s = normalizarTexto(iso);
            var m = s.match(/^(\d{4})-(\d{2})-(\d{2})$/);
            return m ? (m[3] + '/' + m[2] + '/' + m[1]) : s;
        }
        function abrirModalSyncPeriodo() {
            var dateStart = document.getElementById('dateStart');
            var dateEnd = document.getElementById('dateEnd');
            var syncStart = document.getElementById('syncDateStart');
            var syncEnd = document.getElementById('syncDateEnd');
            if (syncStart && dateStart) syncStart.value = dateStart.value || '';
            if (syncEnd && dateEnd) syncEnd.value = dateEnd.value || '';
            document.getElementById('syncDateModal').classList.add('is-visible');
        }
        function fecharModalSyncPeriodo() {
            document.getElementById('syncDateModal').classList.remove('is-visible');
        }
        function confirmarSyncPeriodo() {
            var syncStart = document.getElementById('syncDateStart');
            var syncEnd = document.getElementById('syncDateEnd');
            var ini = syncStart ? syncStart.value : '';
            var fim = syncEnd ? syncEnd.value : '';
            if (!ini || !fim) {
                alert('Informe Data inicio e Data fim para executar o OS Sync p/ Data.');
                return;
            }
            fecharModalSyncPeriodo();
            executarAction('syncPeriodo', [], 'ALL', { ini: ini, fim: fim });
        }
        function montarParametros(actionKey, parametrosManuais) {
            if (actionKey === 'geraPagamento' || actionKey === 'desfazerDocumentos') {
                var items = parametrosManuais && parametrosManuais.items ? parametrosManuais.items : [];
                var nros = [];
                var cnpjs = [];
                for (var i = 0; i < items.length; i++) {
                    var nro = normalizarTexto(items[i].nroos);
                    var cnpj = somenteDigitos(items[i].cnpj || items[i].cnpjostlc || items[i].cnpjparceiro);
                    if (nro) nros.push(nro);
                    if (cnpj) cnpjs.push(cnpj);
                }
                return {
                    param: [
                        { paramName: 'P_NROOS_LIST', name: 'P_NROOS_LIST', type: 'S', $: nros.join(',') },
                        { paramName: 'P_CNPJ_LIST', name: 'P_CNPJ_LIST', type: 'S', $: cnpjs.join(',') }
                    ]
                };
            }
            if (actionKey !== 'syncPeriodo') return null;
            var dateStart = document.getElementById('dateStart');
            var dateEnd = document.getElementById('dateEnd');
            var ini = parametrosManuais && parametrosManuais.ini ? parametrosManuais.ini : (dateStart ? dateStart.value : '');
            var fim = parametrosManuais && parametrosManuais.fim ? parametrosManuais.fim : (dateEnd ? dateEnd.value : '');
            if (!ini || !fim) {
                throw new Error('Informe Data inicio e Data fim para executar o OS Sync p/ Data.');
            }
            return {
                param: [
                    { paramName: 'P_DATA_INICIO', name: 'P_DATA_INICIO', type: 'D', $: formatDateParam(ini) },
                    { paramName: 'P_DATA_FIM', name: 'P_DATA_FIM', type: 'D', $: formatDateParam(fim) }
                ]
            };
        }
        function montarCamposAction(item, actionKey) {
            if (actionKey === 'geraPagamento' || actionKey === 'desfazerDocumentos') {
                return [
                    { fieldName: 'NROOS', type: 'N', $: normalizarTexto(item.nroos) },
                    { fieldName: 'CNPJ', type: 'S', $: somenteDigitos(item.cnpj || item.cnpjostlc || item.cnpjparceiro) }
                ];
            }
            return [];
        }
        function executarAcaoGlobal(actionKey) {
            executarAction(actionKey, [], 'ALL');
        }
        function osPagamentoFiltradasAtuais(cnpjFiltro, somenteGeradas) {
            var detalhes = detalhesFiltradosAtuais();
            var mapa = {};
            var lista = [];
            for (var i = 0; i < detalhes.length; i++) {
                var cnpj = somenteDigitos(detalhes[i].cnpjostlc || detalhes[i].cnpjparceiro);
                var nroos = normalizarTexto(detalhes[i].nroos);
                if (!cnpj || !nroos) continue;
                if (cnpjFiltro && cnpj !== cnpjFiltro) continue;
                var gerada = String(detalhes[i].ospaga).toUpperCase() === 'S';
                if (somenteGeradas && !gerada) continue;
                if (!somenteGeradas && gerada) continue;
                if (mapa[nroos]) continue;
                mapa[nroos] = true;
                lista.push({ nroos: nroos, cnpj: cnpj });
            }
            return lista;
        }
        function executarPagamentoFiltrado() {
            var os = osPagamentoFiltradasAtuais();
            if (!os.length) {
                alert('Nenhuma OS pendente localizada nos filtros atuais para gerar pagamento.');
                return;
            }
            executarAction('geraPagamento', os, 'ALL');
        }
        function executarPagamentoParceiro(btn) {
            var cnpj = btn ? somenteDigitos(btn.getAttribute('data-cnpj')) : '';
            if (!cnpj) {
                alert('CNPJ do parceiro nao localizado.');
                return;
            }
            var os = osPagamentoFiltradasAtuais(cnpj, false);
            if (!os.length) {
                alert('Nenhuma OS pendente localizada para este CNPJ.');
                return;
            }
            executarAction('geraPagamento', os, 'ALL');
        }
        function executarPagamentoLinha(btn) {
            var nroos = btn ? normalizarTexto(btn.getAttribute('data-nroos')) : '';
            var cnpj = btn ? somenteDigitos(btn.getAttribute('data-cnpj')) : '';
            if (!nroos) {
                alert('NROOS da linha nao localizado.');
                return;
            }
            executarAction('geraPagamento', [{ nroos: nroos, cnpj: cnpj }], 'ALL');
        }
        function osPagamentoSelecionadas() {
            var checks = document.querySelectorAll('[data-os-select]:checked');
            var lista = [];
            var mapa = {};
            for (var i = 0; i < checks.length; i++) {
                var nroos = normalizarTexto(checks[i].getAttribute('data-nroos'));
                var cnpj = somenteDigitos(checks[i].getAttribute('data-cnpj'));
                if (!nroos || mapa[nroos]) continue;
                mapa[nroos] = true;
                lista.push({ nroos: nroos, cnpj: cnpj });
            }
            return lista;
        }
        function executarPagamentoSelecionadas() {
            var os = osPagamentoSelecionadas();
            if (!os.length) {
                alert('Marque as OS pendentes que deseja pagar na tabela detalhada.');
                return;
            }
            executarAction('geraPagamento', os, 'ALL');
        }
        function alternarSelecaoOsDetalhe(master) {
            var checks = document.querySelectorAll('[data-os-select]');
            for (var i = 0; i < checks.length; i++) {
                if (!checks[i].disabled && checks[i].offsetParent !== null) {
                    checks[i].checked = !!master.checked;
                }
            }
        }
        function executarDesfazerParceiro(btn) {
            var cnpj = btn ? somenteDigitos(btn.getAttribute('data-cnpj')) : '';
            if (!cnpj) {
                alert('CNPJ do parceiro nao localizado.');
                return;
            }
            var os = osPagamentoFiltradasAtuais(cnpj, true);
            if (!os.length) {
                alert('Nenhuma OS gerada localizada para este CNPJ.');
                return;
            }
            if (!confirm('Desfazer a geracao de ' + os.length + ' OS deste parceiro?')) return;
            executarAction('desfazerDocumentos', os, 'ALL');
        }
        function executarDesfazerFiltrado() {
            var os = osPagamentoFiltradasAtuais('', true);
            if (!os.length) {
                alert('Nenhuma OS gerada localizada nos filtros atuais para desfazer.');
                return;
            }
            if (!confirm('Desfazer a geracao de ' + os.length + ' OS filtrada(s)? OSPAGA volta para N e NUFINGER/NUNOTAGER serao limpos.')) return;
            executarAction('desfazerDocumentos', os, 'ALL');
        }
        function executarDesfazerLinha(btn) {
            var nroos = btn ? normalizarTexto(btn.getAttribute('data-nroos')) : '';
            var cnpj = btn ? somenteDigitos(btn.getAttribute('data-cnpj')) : '';
            if (!nroos) {
                alert('NROOS da linha nao localizado.');
                return;
            }
            if (!confirm('Desfazer a geracao da OS ' + nroos + '?')) return;
            executarAction('desfazerDocumentos', [{ nroos: nroos, cnpj: cnpj }], 'ALL');
        }
        function executarSyncPeriodo() {
            abrirModalSyncPeriodo();
        }
        function executarAction(actionKey, items, refreshType, parametrosManuais) {
            var action = ACTIONS[actionKey];
            if (!action || !action.id) {
                abrirConfiguracaoAcoes();
                alert('Configure o ID da acao "' + (action ? action.label : actionKey) + '" antes de executar.');
                return;
            }
            var params = null;
            try {
                params = montarParametros(actionKey, Object.assign({ items: items || [] }, parametrosManuais || {}));
            } catch (e) {
                alert(e.message || String(e));
                return;
            }
            abrirModalAcao(action.label, (items && items.length ? items.length + ' OS selecionada(s)' : 'Execucao global'));
            var sid = getCookie('JSESSIONID').replace('.master', '');
            var url = '/mge/service.sbr?serviceName=ActionButtonsSP.executeJava&outputType=json&mgeSession=' + sid;
            var javaCall = { actionID: String(action.id), refreshType: refreshType || 'SEL' };
            if (params) javaCall.params = params;
            if (items && items.length) {
                var rows = [];
                for (var i = 0; i < items.length; i++) rows.push({ field: montarCamposAction(items[i], actionKey) });
                javaCall.rows = { row: rows };
            }
            var body = { serviceName: 'ActionButtonsSP.executeJava', requestBody: { javaCall: javaCall } };
            fetch(url, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(body) })
                .then(function(r) { return r.json(); })
                .then(function(resp) {
                    var ok = String(resp && resp.status) === '1' || String(resp && resp.status) === '2';
                    var msg = (resp && (resp.statusMessage || resp.message)) || JSON.stringify(resp);
                    document.getElementById('actionProgress').className = 'progress-bar ' + (ok ? 'is-success' : 'is-error');
                    document.getElementById('actionProgress').style.width = '100%';
                    document.getElementById('actionResult').innerHTML = esc(msg || 'Acao executada.');
                    document.getElementById('reloadAfterAction').style.display = 'inline-flex';
                })
                .catch(function(err) {
                    document.getElementById('actionProgress').className = 'progress-bar is-error';
                    document.getElementById('actionProgress').style.width = '100%';
                    document.getElementById('actionResult').textContent = 'Erro ao executar: ' + (err && err.message ? err.message : String(err));
                });
        }
        function montarIndiceBusca(item) {
            var partes = [];
            for (var k in item) {
                if (item.hasOwnProperty(k)) partes.push(normalizarTexto(item[k]));
            }
            return normalizarBusca(partes.join(' '));
        }

        function coletarResumo() {
            var rows = document.querySelectorAll('#resumoDataSource .js-resumo-row');
            var items = [];
            for (var i = 0; i < rows.length; i++) {
                var r = rows[i];
                items.push({
                    codparc: r.getAttribute('data-codparc') || '',
                    nomeparc: r.getAttribute('data-nomeparc') || '',
                    cnpjparceiro: r.getAttribute('data-cnpjparceiro') || '',
                    cnpjostlc: r.getAttribute('data-cnpjostlc') || '',
                    vlrfixser: toNumber(r.getAttribute('data-vlrfixser')),
                    qtdos: toNumber(r.getAttribute('data-qtdos')),
                    qtdgerada: toNumber(r.getAttribute('data-qtdgerada')),
                    qtdpendente: toNumber(r.getAttribute('data-qtdpendente')),
                    qtdcompeca: toNumber(r.getAttribute('data-qtdcompeca')),
                    qtdsempeca: toNumber(r.getAttribute('data-qtdsempeca')),
                    vlrgerado: toNumber(r.getAttribute('data-vlrgerado')),
                    vlrpendente: toNumber(r.getAttribute('data-vlrpendente')),
                    vlrcompeca: toNumber(r.getAttribute('data-vlrcompeca')),
                    vlrsempeca: toNumber(r.getAttribute('data-vlrsempeca')),
                    vlrtotal: toNumber(r.getAttribute('data-vlrtotal'))
                });
            }
            return items;
        }

        function coletarDetalhes() {
            var rows = document.querySelectorAll('#detalheDataSource .js-detalhe-row');
            var items = [];
            for (var i = 0; i < rows.length; i++) {
                var r = rows[i];
                items.push({
                    nroos: r.getAttribute('data-nroos') || '',
                    dataabertura: r.getAttribute('data-dataabertura') || '',
                    dataref: parseDateValue(r.getAttribute('data-dataabertura')),
                    codparc: r.getAttribute('data-codparc') || '',
                    nomeparc: r.getAttribute('data-nomeparc') || '',
                    cnpjostlc: r.getAttribute('data-cnpjostlc') || '',
                    statusos: r.getAttribute('data-statusos') || '',
                    ospaga: r.getAttribute('data-ospaga') || 'N',
                    nufinger: r.getAttribute('data-nufinger') || '',
                    nunotager: r.getAttribute('data-nunotager') || '',
                    cnpjparceiro: r.getAttribute('data-cnpjparceiro') || '',
                    vlrfixser: toNumber(r.getAttribute('data-vlrfixser')),
                    tempeca: r.getAttribute('data-tempeca') || '',
                    formapagamento: r.getAttribute('data-formapagamento') || '',
                    vlrapagar: toNumber(r.getAttribute('data-vlrapagar'))
                });
            }
            return items;
        }

        function dentroDoPeriodo(item) {
            var dateStart = document.getElementById('dateStart');
            var dateEnd = document.getElementById('dateEnd');
            var start = dateStart ? dateStart.value : '';
            var end = dateEnd ? dateEnd.value : '';
            var data = item.dataref || '';
            if (start && data && data < start) return false;
            if (end && data && data > end) return false;
            return true;
        }

        function filtrarDetalhesPorPeriodo(detalhes) {
            var filtrados = [];
            for (var i = 0; i < detalhes.length; i++) {
                if (dentroDoPeriodo(detalhes[i])) filtrados.push(detalhes[i]);
            }
            return filtrados;
        }

        function filtrarDetalhesPorBusca(detalhes) {
            var input = document.getElementById('tabSearchInput');
            var termo = normalizarBusca(input ? input.value : '');
            if (!termo) return detalhes;
            var filtrados = [];
            for (var i = 0; i < detalhes.length; i++) {
                if (montarIndiceBusca(detalhes[i]).indexOf(termo) >= 0) filtrados.push(detalhes[i]);
            }
            return filtrados;
        }

        function detalhesFiltradosAtuais() {
            return filtrarDetalhesPorBusca(filtrarDetalhesPorPeriodo(window.__detalhesBase || []));
        }

        function consolidarResumoPorDetalhes(detalhes) {
            var grupos = {};
            for (var i = 0; i < detalhes.length; i++) {
                var item = detalhes[i];
                var chave = (item.codparc || 'SEM_PARCEIRO') + '|' + (item.cnpjostlc || item.cnpjparceiro || '');
                if (!grupos[chave]) {
                    grupos[chave] = {
                        codparc: item.codparc,
                        nomeparc: item.nomeparc,
                        cnpjparceiro: item.cnpjparceiro,
                        cnpjostlc: item.cnpjostlc,
                        vlrfixser: item.vlrfixser,
                        qtdos: 0,
                        qtdgerada: 0,
                        qtdpendente: 0,
                        qtdcompeca: 0,
                        qtdsempeca: 0,
                        vlrgerado: 0,
                        vlrpendente: 0,
                        vlrcompeca: 0,
                        vlrsempeca: 0,
                        vlrtotal: 0,
                        primeiraabertura: item.dataabertura,
                        ultimaabertura: item.dataabertura
                    };
                }
                var g = grupos[chave];
                var hasPeca = String(item.tempeca).toUpperCase() === 'S';
                var gerada = String(item.ospaga).toUpperCase() === 'S';
                g.qtdos += 1;
                g.vlrtotal += item.vlrapagar;
                if (gerada) {
                    g.qtdgerada += 1;
                    g.vlrgerado += item.vlrapagar;
                } else {
                    g.qtdpendente += 1;
                    g.vlrpendente += item.vlrapagar;
                }
                if (hasPeca) {
                    g.qtdcompeca += 1;
                    g.vlrcompeca += item.vlrapagar;
                } else {
                    g.qtdsempeca += 1;
                    g.vlrsempeca += item.vlrapagar;
                }
                if (!g.primeiraabertura || (item.dataref && item.dataref < parseDateValue(g.primeiraabertura))) g.primeiraabertura = item.dataabertura;
                if (!g.ultimaabertura || (item.dataref && item.dataref > parseDateValue(g.ultimaabertura))) g.ultimaabertura = item.dataabertura;
            }
            var lista = [];
            for (var k in grupos) if (grupos.hasOwnProperty(k)) lista.push(grupos[k]);
            lista.sort(function(a, b) {
                if (b.vlrtotal !== a.vlrtotal) return b.vlrtotal - a.vlrtotal;
                return normalizarTexto(a.nomeparc).localeCompare(normalizarTexto(b.nomeparc));
            });
            return lista;
        }

        function atualizarMetricas(resumo) {
            var total = 0, comPeca = 0, semPeca = 0, qtdOs = 0, gerado = 0, pendente = 0, qtdGerada = 0, qtdPendente = 0;
            for (var i = 0; i < resumo.length; i++) {
                total += resumo[i].vlrtotal;
                comPeca += resumo[i].vlrcompeca;
                semPeca += resumo[i].vlrsempeca;
                qtdOs += resumo[i].qtdos;
                gerado += resumo[i].vlrgerado;
                pendente += resumo[i].vlrpendente;
                qtdGerada += resumo[i].qtdgerada;
                qtdPendente += resumo[i].qtdpendente;
            }
            document.getElementById('metricTotal').textContent = formatCurrency(total);
            document.getElementById('metricComPeca').textContent = formatCurrency(comPeca);
            document.getElementById('metricSemPeca').textContent = formatCurrency(semPeca);
            document.getElementById('metricGerado').textContent = formatCurrency(gerado);
            document.getElementById('metricPendente').textContent = formatCurrency(pendente);
            document.getElementById('metricQtdGerada').textContent = qtdGerada.toLocaleString('pt-BR');
            document.getElementById('metricQtdPendente').textContent = qtdPendente.toLocaleString('pt-BR');
            document.getElementById('metricQtdOs').textContent = qtdOs.toLocaleString('pt-BR');
            document.getElementById('metricParceiros').textContent = resumo.length.toLocaleString('pt-BR');
            desenharGraficoPagamento(comPeca, semPeca);
        }

        function desenharGraficoPagamento(comPeca, semPeca) {
            window.__ultimoGraficoPagamento = { comPeca: comPeca, semPeca: semPeca };
            var canvas = document.getElementById('paymentChart');
            if (!canvas || !canvas.getContext) return;
            var ctx = canvas.getContext('2d');
            var W = canvas.width, H = canvas.height;
            ctx.clearRect(0, 0, W, H);
            var total = comPeca + semPeca;
            var cx = 82, cy = 86, r = 58, start = -Math.PI / 2;
            var colors = ['#22c55e', '#f97316'];
            var values = [comPeca, semPeca];
            for (var i = 0; i < values.length; i++) {
                var slice = total ? (values[i] / total) * Math.PI * 2 : 0;
                ctx.beginPath();
                ctx.moveTo(cx, cy);
                ctx.arc(cx, cy, r, start, start + slice);
                ctx.closePath();
                ctx.fillStyle = colors[i];
                ctx.fill();
                start += slice;
            }
            ctx.beginPath();
            ctx.arc(cx, cy, 34, 0, Math.PI * 2);
            ctx.fillStyle = cssVar('--canvas-hole', '#f8fafc');
            ctx.fill();
            ctx.fillStyle = cssVar('--text-main', '#0f172a');
            ctx.font = '800 13px Inter, Arial';
            ctx.textAlign = 'center';
            ctx.fillText(total ? '100%' : '0%', cx, cy + 5);

            var labels = [
                { label: 'Nota de Compra', value: comPeca, color: colors[0] },
                { label: 'Financeiro Direto', value: semPeca, color: colors[1] }
            ];
            ctx.textAlign = 'left';
            for (var j = 0; j < labels.length; j++) {
                var y = 64 + (j * 42);
                ctx.fillStyle = labels[j].color;
                ctx.fillRect(165, y - 10, 12, 12);
                ctx.fillStyle = cssVar('--text-secondary', '#64748b');
                ctx.font = '700 11px Inter, Arial';
                ctx.fillText(labels[j].label, 186, y);
                ctx.fillStyle = cssVar('--text-main', '#0f172a');
                ctx.font = '800 13px Inter, Arial';
                ctx.fillText(formatCurrency(labels[j].value), 186, y + 18);
            }
        }

        function montarTabelaResumo(items) {
            var html = '<div class="table-wrap"><table class="monitor-table"><thead><tr>';
            html += '<th>Parceiro</th><th>CNPJ</th><th>Periodo abertura</th><th>Taxa Administrativa</th><th>Qtd OS</th><th>OS paga</th><th>OS nao paga</th><th>Com pecas</th><th>Sem peca</th><th>Total</th><th>Acao</th>';
            html += '</tr></thead><tbody>';
            for (var i = 0; i < items.length; i++) {
                var item = items[i];
                html += '<tr data-search="' + esc(montarIndiceBusca(item)) + '">';
                html += '<td><strong>' + esc(item.nomeparc || '-') + '</strong><small>CODPARC ' + esc(item.codparc || '-') + '</small></td>';
                html += '<td>' + esc(item.cnpjparceiro || item.cnpjostlc || '-') + '</td>';
                html += '<td>' + esc(item.primeiraabertura || '-') + '<small>ate ' + esc(item.ultimaabertura || '-') + '</small></td>';
                html += '<td>' + esc(formatCurrency(item.vlrfixser)) + '</td>';
                html += '<td>' + esc(item.qtdos.toLocaleString('pt-BR')) + '</td>';
                html += '<td><span class="status-badge status-success">OS paga</span><strong>' + esc(formatCurrency(item.vlrgerado)) + '</strong><small>' + esc(item.qtdgerada) + ' OS</small></td>';
                html += '<td><span class="status-badge status-muted">OS nao paga</span><strong>' + esc(formatCurrency(item.vlrpendente)) + '</strong><small>' + esc(item.qtdpendente) + ' OS</small></td>';
                html += '<td><span class="status-badge status-success">Nota de Compra</span><strong>' + esc(formatCurrency(item.vlrcompeca)) + '</strong><small>' + esc(item.qtdcompeca) + ' OS</small></td>';
                html += '<td><span class="status-badge status-warning">Financeiro Direto</span><strong>' + esc(formatCurrency(item.vlrsempeca)) + '</strong><small>' + esc(item.qtdsempeca) + ' OS</small></td>';
                html += '<td><strong>' + esc(formatCurrency(item.vlrtotal)) + '</strong></td>';
                html += '<td><div class="action-cell"><button class="mini-action" type="button" data-cnpj="' + esc(item.cnpjostlc || item.cnpjparceiro || '') + '" onclick="executarPagamentoParceiro(this)">Gerar</button><button class="mini-action mini-action-danger" type="button" data-cnpj="' + esc(item.cnpjostlc || item.cnpjparceiro || '') + '" onclick="executarDesfazerParceiro(this)">Desfazer</button></div></td>';
                html += '</tr>';
            }
            html += '</tbody></table></div>';
            return html;
        }

        function montarTabelaDetalhe(items) {
            var html = '<div class="table-wrap"><table class="monitor-table"><thead><tr>';
            html += '<th class="select-cell"><input class="os-select" type="checkbox" onclick="alternarSelecaoOsDetalhe(this)" title="Selecionar OS visiveis"></th><th>NROOS</th><th>Data abertura</th><th>Status OS</th><th>Status geracao</th><th>Documento</th><th>Parceiro</th><th>CNPJ OSTLC</th><th>Peca?</th><th>Pagamento</th><th>Valor</th><th>Acao</th>';
            html += '</tr></thead><tbody>';
            for (var i = 0; i < items.length; i++) {
                var item = items[i];
                var hasPeca = String(item.tempeca).toUpperCase() === 'S';
                var gerada = String(item.ospaga).toUpperCase() === 'S';
                var docGerado = item.nunotager || item.nufinger || '-';
                html += '<tr data-search="' + esc(montarIndiceBusca(item)) + '">';
                html += '<td class="select-cell">' + (gerada ? '' : '<input class="os-select" type="checkbox" data-os-select="S" data-nroos="' + esc(item.nroos || '') + '" data-cnpj="' + esc(item.cnpjostlc || item.cnpjparceiro || '') + '" title="Selecionar OS ' + esc(item.nroos || '') + '">') + '</td>';
                html += '<td><strong>' + esc(item.nroos || '-') + '</strong></td>';
                html += '<td>' + esc(item.dataabertura || '-') + '</td>';
                html += '<td><span class="status-badge status-success">' + esc(item.statusos || 'Finalizada') + '</span></td>';
                html += '<td><span class="status-badge ' + (gerada ? 'status-success' : 'status-muted') + '">' + (gerada ? 'OS paga' : 'OS nao paga') +'</span></td>';
                html += '<td>' + esc(docGerado) + '</td>';
                html += '<td><strong>' + esc(item.nomeparc || '-') + '</strong><small>CODPARC ' + esc(item.codparc || '-') + '</small></td>';
                html += '<td>' + esc(item.cnpjostlc || '-') + '</td>';
                html += '<td><span class="status-badge ' + (hasPeca ? 'status-success' : 'status-warning') + '">' + (hasPeca ? 'Sim' : 'Nao') + '</span></td>';
                var pagamentoErro = String(item.formapagamento || '').toLowerCase().indexOf('erro:') === 0;
                html += '<td><span class="status-badge ' + (pagamentoErro ? 'status-danger' : (hasPeca ? 'status-success' : 'status-warning')) + '">' + esc(item.formapagamento || '-') + '</span></td>';
                html += '<td><strong>' + esc(formatCurrency(item.vlrapagar)) + '</strong></td>';
                html += '<td><div class="action-cell">' + (gerada ? '<button class="mini-action mini-action-danger" type="button" data-nroos="' + esc(item.nroos || '') + '" data-cnpj="' + esc(item.cnpjostlc || item.cnpjparceiro || '') + '" onclick="executarDesfazerLinha(this)">Desfazer</button>' : '<button class="mini-action" type="button" data-nroos="' + esc(item.nroos || '') + '" data-cnpj="' + esc(item.cnpjostlc || item.cnpjparceiro || '') + '" onclick="executarPagamentoLinha(this)">Gerar</button>') + '</div></td>';
                html += '</tr>';
            }
            html += '</tbody></table></div>';
            return html;
        }

        function ativarAba(cat) {
            var btns = document.querySelectorAll('.tab-button');
            var panels = document.querySelectorAll('.tab-panel');
            for (var i = 0; i < btns.length; i++) btns[i].classList.toggle('is-active', btns[i].getAttribute('data-tab') === cat);
            for (var p = 0; p < panels.length; p++) panels[p].classList.toggle('is-active', panels[p].getAttribute('data-tab') === cat);
            aplicarBuscaAbaAtiva();
        }

        function atualizarInfoBusca(visiveis, total) {
            var info = document.getElementById('tabSearchInfo');
            if (info) info.textContent = total ? (visiveis + ' de ' + total + ' registros exibidos') : '';
        }

        function aplicarBuscaAbaAtiva() {
            var input = document.getElementById('tabSearchInput');
            var panel = document.querySelector('.tab-panel.is-active');
            if (!panel) {
                atualizarInfoBusca(0, 0);
                return;
            }
            var termoDigitado = input ? input.value : '';
            var termo = normalizarBusca(termoDigitado);
            var rows = panel.querySelectorAll('tbody tr');
            var total = rows.length;
            var visiveis = 0;
            for (var i = 0; i < rows.length; i++) {
                var ok = !termo || normalizarBusca(rows[i].getAttribute('data-search')).indexOf(termo) >= 0;
                rows[i].style.display = ok ? '' : 'none';
                if (ok) visiveis++;
            }
            var empty = panel.querySelector('.tab-search-empty');
            if (!empty) {
                empty = document.createElement('div');
                empty.className = 'empty-state tab-search-empty';
                panel.appendChild(empty);
            }
            empty.style.display = total > 0 && visiveis === 0 && termo ? 'block' : 'none';
            empty.textContent = 'Nenhum registro encontrado para "' + termoDigitado + '".';
            atualizarInfoBusca(visiveis, total);
        }

        function iniciarBusca() {
            var input = document.getElementById('tabSearchInput');
            var clear = document.getElementById('tabSearchClear');
            var dateStart = document.getElementById('dateStart');
            var dateEnd = document.getElementById('dateEnd');
            if (input) input.addEventListener('input', atualizarDashboardPorPeriodo);
            if (dateStart) dateStart.addEventListener('change', atualizarDashboardPorPeriodo);
            if (dateEnd) dateEnd.addEventListener('change', atualizarDashboardPorPeriodo);
            if (clear) clear.addEventListener('click', function() {
                if (input) {
                    input.value = '';
                    input.focus();
                }
                if (dateStart) dateStart.value = '';
                if (dateEnd) dateEnd.value = '';
                atualizarDashboardPorPeriodo();
            });
        }

        function atualizarDashboardPorPeriodo() {
            var detalhesFiltrados = detalhesFiltradosAtuais();
            var resumoFiltrado = consolidarResumoPorDetalhes(detalhesFiltrados);
            atualizarMetricas(resumoFiltrado);
            construirAbas(resumoFiltrado, detalhesFiltrados);
        }

        function construirAbas(resumo, detalhes) {
            var abaAtualEl = document.querySelector('.tab-panel.is-active');
            var abaAtual = abaAtualEl ? abaAtualEl.getAttribute('data-tab') : 'resumo';
            _tabItems.resumo = resumo;
            _tabItems.comPeca = [];
            _tabItems.semPeca = [];
            _tabItems.geradas = [];
            _tabItems.pendentes = [];
            for (var i = 0; i < detalhes.length; i++) {
                if (String(detalhes[i].tempeca).toUpperCase() === 'S') _tabItems.comPeca.push(detalhes[i]);
                else _tabItems.semPeca.push(detalhes[i]);
                if (String(detalhes[i].ospaga).toUpperCase() === 'S') _tabItems.geradas.push(detalhes[i]);
                else _tabItems.pendentes.push(detalhes[i]);
            }

            var metas = [
                { cat: 'resumo', label: 'Resumo por parceiro', count: resumo.length },
                { cat: 'geradas', label: 'OS paga', count: _tabItems.geradas.length },
                { cat: 'pendentes', label: 'OS nao paga', count: _tabItems.pendentes.length },
                { cat: 'comPeca', label: 'Com pecas', count: _tabItems.comPeca.length },
                { cat: 'semPeca', label: 'Sem peca', count: _tabItems.semPeca.length }
            ];
            var tabs = '';
            var content = '';
            for (var m = 0; m < metas.length; m++) {
                var active = metas[m].cat === abaAtual ? ' is-active' : '';
                if (!active && !abaAtualEl && m === 0) active = ' is-active';
                tabs += '<button class="tab-button' + active + '" data-tab="' + metas[m].cat + '" type="button" onclick="ativarAba(\'' + metas[m].cat + '\')">';
                tabs += '<span>' + esc(metas[m].label) + '</span><span class="tab-count">' + esc(metas[m].count) + '</span></button>';
                content += '<div class="tab-panel' + active + '" data-tab="' + metas[m].cat + '">';
                if (metas[m].cat === 'resumo') content += resumo.length ? montarTabelaResumo(resumo) : '<div class="empty-state">Nenhum parceiro encontrado pelo CNPJ da AD_OSTLC.</div>';
                if (metas[m].cat === 'geradas') content += _tabItems.geradas.length ? montarTabelaDetalhe(_tabItems.geradas) : '<div class="empty-state">Nenhuma OS gerada localizada.</div>';
                if (metas[m].cat === 'pendentes') content += _tabItems.pendentes.length ? montarTabelaDetalhe(_tabItems.pendentes) : '<div class="empty-state">Nenhuma OS pendente localizada.</div>';
                if (metas[m].cat === 'comPeca') content += _tabItems.comPeca.length ? montarTabelaDetalhe(_tabItems.comPeca) : '<div class="empty-state">Nenhuma OS com peca localizada.</div>';
                if (metas[m].cat === 'semPeca') content += _tabItems.semPeca.length ? montarTabelaDetalhe(_tabItems.semPeca) : '<div class="empty-state">Nenhuma OS sem peca localizada.</div>';
                content += '</div>';
            }
            document.getElementById('statusTabs').innerHTML = tabs;
            document.getElementById('tablesArea').innerHTML = content;
            aplicarBuscaAbaAtiva();
        }

        var detalhes = coletarDetalhes();
        window.__detalhesBase = detalhes;
        var resumo = consolidarResumoPorDetalhes(detalhes);
        carregarTema();
        carregarActionIds();
        atualizarMetricas(resumo);
        construirAbas(resumo, detalhes);
        iniciarBusca();
        atualizarRelogio();
        setInterval(atualizarRelogio, 1000);
        (function() {
            var el = document.getElementById('lastRefresh');
            if (el) {
                var d = new Date();
                el.textContent = pad(d.getHours()) + ':' + pad(d.getMinutes()) + ':' + pad(d.getSeconds());
            }
        })();
    </script>
</body>
</html>






