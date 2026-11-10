package br.com.zaya.engineering.nexo.tickets;

import java.util.List;

/** Future contract only; no external publication or automatic correction. */
public record TicketDraft(String investigationId, String title, String description, List<String> evidenceIds) {}
