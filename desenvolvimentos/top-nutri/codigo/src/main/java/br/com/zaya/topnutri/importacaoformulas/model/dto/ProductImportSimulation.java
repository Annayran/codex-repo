package br.com.zaya.topnutri.importacaoformulas.model.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ProductImportSimulation {
    private final int lineNumber;
    private final String finishedProductCode;
    private final String finishedProductDescription;
    private final BigDecimal outputQuantity;
    private final String microProductCode;
    private final String microProductDescription;
    private final BigDecimal microQuantity;
    private final List<SimulatedMaterial> finishedProductMaterials;
    private final List<SimulatedMaterial> microProductMaterials;

    public ProductImportSimulation(
            int lineNumber,
            String finishedProductCode,
            String finishedProductDescription,
            BigDecimal outputQuantity,
            String microProductCode,
            String microProductDescription,
            BigDecimal microQuantity,
            List<SimulatedMaterial> finishedProductMaterials,
            List<SimulatedMaterial> microProductMaterials) {
        this.lineNumber = lineNumber;
        this.finishedProductCode = Objects.requireNonNull(
                finishedProductCode, "finishedProductCode");
        this.finishedProductDescription = Objects.requireNonNull(
                finishedProductDescription, "finishedProductDescription");
        this.outputQuantity = Objects.requireNonNull(outputQuantity, "outputQuantity");
        this.microProductCode = microProductCode;
        this.microProductDescription = microProductDescription;
        this.microQuantity = microQuantity;
        this.finishedProductMaterials = Collections.unmodifiableList(
                new ArrayList<SimulatedMaterial>(finishedProductMaterials));
        this.microProductMaterials = Collections.unmodifiableList(
                new ArrayList<SimulatedMaterial>(microProductMaterials));
    }

    public int getLineNumber() { return lineNumber; }
    public String getFinishedProductCode() { return finishedProductCode; }
    public String getFinishedProductDescription() { return finishedProductDescription; }
    public BigDecimal getOutputQuantity() { return outputQuantity; }
    public String getMicroProductCode() { return microProductCode; }
    public String getMicroProductDescription() { return microProductDescription; }
    public BigDecimal getMicroQuantity() { return microQuantity; }
    public List<SimulatedMaterial> getFinishedProductMaterials() {
        return finishedProductMaterials;
    }
    public List<SimulatedMaterial> getMicroProductMaterials() {
        return microProductMaterials;
    }
}
