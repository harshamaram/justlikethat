package com.test;

import java.io.Serializable;

public class PieChartSectionDataBean implements Serializable,
        Comparable<PieChartSectionDataBean> {

    private static final long serialVersionUID = 1L;

    private String industryCategory;
    private Double parAmount;
    private Double percentage;
    private Double adjustedPercentage;
    private int noOfAssets;
    private boolean changed;

    public String getIndustryCategory() {
        return industryCategory;
    }

    public int getNoOfAssets() {
        return noOfAssets;
    }

    public void setNoOfAssets(int noOfAssets) {
        this.noOfAssets = noOfAssets;
    }

    public void setIndustryCategory(String industryCategory) {
        this.industryCategory = industryCategory;
    }

    public Double getParAmount() {
        return parAmount;
    }

    public void setParAmount(Double parAmount) {
        this.parAmount = parAmount;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public int compareTo(PieChartSectionDataBean dataBean) {
        return dataBean.getParAmount().compareTo(this.getParAmount());
    }

    public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	@Override
    public int hashCode() {

        return super.hashCode();
    }

    public Double getAdjustedPercentage() {
		return adjustedPercentage;
	}

	public void setAdjustedPercentage(Double adjustedPercentage) {
		this.adjustedPercentage = adjustedPercentage;
	}

	public boolean equals(Object arg0) {
        return super.equals(arg0);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[").append(industryCategory)
                .append(", ").append(percentage).append(", ")
                .append(adjustedPercentage).append("]");
        return sb.toString();
    }

}
