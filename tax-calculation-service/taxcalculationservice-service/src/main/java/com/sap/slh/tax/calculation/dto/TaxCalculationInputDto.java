package com.sap.slh.tax.calculation.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxCalculationInputDto implements Serializable {

	private static final long serialVersionUID = -7787862080659179385L;

	private String directionIndicator;
	private String productTaxability;
	private String sFCountry;
	private String sTCountry;
	private String taxReportingGroupIndicator;
	private String sFBusinessPartnerTaxGroup;
	private String sTBusinessPartnerTaxGroup;
	private String businessPartnerTaxability;
	private String cFCountry;
	private String taxExemptionReasonCode;
	private String exemptionRegionStatus;
	private String servicePointTaxableIndicator;
	private String sLCountry;
	private String soldOverElectronicMedium;
	private String sTCountryCustomerTaxRegistrationIndicator;
	private String sTCountrySupplierTaxRegistrationIndicator;
	private String sFCommunity;
	private String sTCommunity;
	private Long validFrom;
	private Long validTo;

	public String getDirectionIndicator() {
		return directionIndicator;
	}

	public void setDirectionIndicator(String directionIndicator) {
		this.directionIndicator = directionIndicator;
	}

	public String getProductTaxability() {
		return productTaxability;
	}

	public void setProductTaxability(String productTaxability) {
		this.productTaxability = productTaxability;
	}

	public String getsFCountry() {
		return sFCountry;
	}

	public void setsFCountry(String sFCountry) {
		this.sFCountry = sFCountry;
	}

	public String getsTCountry() {
		return sTCountry;
	}

	public void setsTCountry(String sTCountry) {
		this.sTCountry = sTCountry;
	}

	public String getTaxReportingGroupIndicator() {
		return taxReportingGroupIndicator;
	}

	public void setTaxReportingGroupIndicator(String taxReportingGroupIndicator) {
		this.taxReportingGroupIndicator = taxReportingGroupIndicator;
	}

	public String getsFBusinessPartnerTaxGroup() {
		return sFBusinessPartnerTaxGroup;
	}

	public void setsFBusinessPartnerTaxGroup(String sFBusinessPartnerTaxGroup) {
		this.sFBusinessPartnerTaxGroup = sFBusinessPartnerTaxGroup;
	}

	public String getsTBusinessPartnerTaxGroup() {
		return sTBusinessPartnerTaxGroup;
	}

	public void setsTBusinessPartnerTaxGroup(String sTBusinessPartnerTaxGroup) {
		this.sTBusinessPartnerTaxGroup = sTBusinessPartnerTaxGroup;
	}

	public String getBusinessPartnerTaxability() {
		return businessPartnerTaxability;
	}

	public void setBusinessPartnerTaxability(String businessPartnerTaxability) {
		this.businessPartnerTaxability = businessPartnerTaxability;
	}

	public String getcFCountry() {
		return cFCountry;
	}

	public void setcFCountry(String cFCountry) {
		this.cFCountry = cFCountry;
	}

	public String getTaxExemptionReasonCode() {
		return taxExemptionReasonCode;
	}

	public void setTaxExemptionReasonCode(String taxExemptionReasonCode) {
		this.taxExemptionReasonCode = taxExemptionReasonCode;
	}

	public String getExemptionRegionStatus() {
		return exemptionRegionStatus;
	}

	public void setExemptionRegionStatus(String exemptionRegionStatus) {
		this.exemptionRegionStatus = exemptionRegionStatus;
	}

	public String getServicePointTaxableIndicator() {
		return servicePointTaxableIndicator;
	}

	public void setServicePointTaxableIndicator(String servicePointTaxableIndicator) {
		this.servicePointTaxableIndicator = servicePointTaxableIndicator;
	}

	public String getsLCountry() {
		return sLCountry;
	}

	public void setsLCountry(String sLCountry) {
		this.sLCountry = sLCountry;
	}

	public String getSoldOverElectronicMedium() {
		return soldOverElectronicMedium;
	}

	public void setSoldOverElectronicMedium(String soldOverElectronicMedium) {
		this.soldOverElectronicMedium = soldOverElectronicMedium;
	}

	public String getsTCountryCustomerTaxRegistrationIndicator() {
		return sTCountryCustomerTaxRegistrationIndicator;
	}

	public void setsTCountryCustomerTaxRegistrationIndicator(String sTCountryCustomerTaxRegistrationIndicator) {
		this.sTCountryCustomerTaxRegistrationIndicator = sTCountryCustomerTaxRegistrationIndicator;
	}

	public String getsTCountrySupplierTaxRegistrationIndicator() {
		return sTCountrySupplierTaxRegistrationIndicator;
	}

	public void setsTCountrySupplierTaxRegistrationIndicator(String sTCountrySupplierTaxRegistrationIndicator) {
		this.sTCountrySupplierTaxRegistrationIndicator = sTCountrySupplierTaxRegistrationIndicator;
	}

	public String getsFCommunity() {
		return sFCommunity;
	}

	public void setsFCommunity(String sFCommunity) {
		this.sFCommunity = sFCommunity;
	}

	public String getsTCommunity() {
		return sTCommunity;
	}

	public void setsTCommunity(String sTCommunity) {
		this.sTCommunity = sTCommunity;
	}

	public Long getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Long validFrom) {
		this.validFrom = validFrom;
	}

	public Long getValidTo() {
		return validTo;
	}

	public void setValidTo(Long validTo) {
		this.validTo = validTo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((businessPartnerTaxability == null) ? 0 : businessPartnerTaxability.hashCode());
		result = prime * result + ((cFCountry == null) ? 0 : cFCountry.hashCode());
		result = prime * result + ((directionIndicator == null) ? 0 : directionIndicator.hashCode());
		result = prime * result + ((exemptionRegionStatus == null) ? 0 : exemptionRegionStatus.hashCode());
		result = prime * result + ((productTaxability == null) ? 0 : productTaxability.hashCode());
		result = prime * result + ((sFBusinessPartnerTaxGroup == null) ? 0 : sFBusinessPartnerTaxGroup.hashCode());
		result = prime * result + ((sFCommunity == null) ? 0 : sFCommunity.hashCode());
		result = prime * result + ((sFCountry == null) ? 0 : sFCountry.hashCode());
		result = prime * result + ((sLCountry == null) ? 0 : sLCountry.hashCode());
		result = prime * result + ((sTBusinessPartnerTaxGroup == null) ? 0 : sTBusinessPartnerTaxGroup.hashCode());
		result = prime * result + ((sTCommunity == null) ? 0 : sTCommunity.hashCode());
		result = prime * result + ((sTCountry == null) ? 0 : sTCountry.hashCode());
		result = prime * result + ((sTCountryCustomerTaxRegistrationIndicator == null) ? 0
				: sTCountryCustomerTaxRegistrationIndicator.hashCode());
		result = prime * result + ((sTCountrySupplierTaxRegistrationIndicator == null) ? 0
				: sTCountrySupplierTaxRegistrationIndicator.hashCode());
		result = prime * result
				+ ((servicePointTaxableIndicator == null) ? 0 : servicePointTaxableIndicator.hashCode());
		result = prime * result + ((soldOverElectronicMedium == null) ? 0 : soldOverElectronicMedium.hashCode());
		result = prime * result + ((taxExemptionReasonCode == null) ? 0 : taxExemptionReasonCode.hashCode());
		result = prime * result + ((taxReportingGroupIndicator == null) ? 0 : taxReportingGroupIndicator.hashCode());
		result = prime * result + ((validFrom == null) ? 0 : validFrom.hashCode());
		result = prime * result + ((validTo == null) ? 0 : validTo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaxCalculationInputDto other = (TaxCalculationInputDto) obj;
		if (businessPartnerTaxability == null) {
			if (other.businessPartnerTaxability != null)
				return false;
		} else if (!businessPartnerTaxability.equals(other.businessPartnerTaxability))
			return false;
		if (cFCountry == null) {
			if (other.cFCountry != null)
				return false;
		} else if (!cFCountry.equals(other.cFCountry))
			return false;
		if (directionIndicator == null) {
			if (other.directionIndicator != null)
				return false;
		} else if (!directionIndicator.equals(other.directionIndicator))
			return false;
		if (exemptionRegionStatus == null) {
			if (other.exemptionRegionStatus != null)
				return false;
		} else if (!exemptionRegionStatus.equals(other.exemptionRegionStatus))
			return false;
		if (productTaxability == null) {
			if (other.productTaxability != null)
				return false;
		} else if (!productTaxability.equals(other.productTaxability))
			return false;
		if (sFBusinessPartnerTaxGroup == null) {
			if (other.sFBusinessPartnerTaxGroup != null)
				return false;
		} else if (!sFBusinessPartnerTaxGroup.equals(other.sFBusinessPartnerTaxGroup))
			return false;
		if (sFCommunity == null) {
			if (other.sFCommunity != null)
				return false;
		} else if (!sFCommunity.equals(other.sFCommunity))
			return false;
		if (sFCountry == null) {
			if (other.sFCountry != null)
				return false;
		} else if (!sFCountry.equals(other.sFCountry))
			return false;
		if (sLCountry == null) {
			if (other.sLCountry != null)
				return false;
		} else if (!sLCountry.equals(other.sLCountry))
			return false;
		if (sTBusinessPartnerTaxGroup == null) {
			if (other.sTBusinessPartnerTaxGroup != null)
				return false;
		} else if (!sTBusinessPartnerTaxGroup.equals(other.sTBusinessPartnerTaxGroup))
			return false;
		if (sTCommunity == null) {
			if (other.sTCommunity != null)
				return false;
		} else if (!sTCommunity.equals(other.sTCommunity))
			return false;
		if (sTCountry == null) {
			if (other.sTCountry != null)
				return false;
		} else if (!sTCountry.equals(other.sTCountry))
			return false;
		if (sTCountryCustomerTaxRegistrationIndicator == null) {
			if (other.sTCountryCustomerTaxRegistrationIndicator != null)
				return false;
		} else if (!sTCountryCustomerTaxRegistrationIndicator.equals(other.sTCountryCustomerTaxRegistrationIndicator))
			return false;
		if (sTCountrySupplierTaxRegistrationIndicator == null) {
			if (other.sTCountrySupplierTaxRegistrationIndicator != null)
				return false;
		} else if (!sTCountrySupplierTaxRegistrationIndicator.equals(other.sTCountrySupplierTaxRegistrationIndicator))
			return false;
		if (servicePointTaxableIndicator == null) {
			if (other.servicePointTaxableIndicator != null)
				return false;
		} else if (!servicePointTaxableIndicator.equals(other.servicePointTaxableIndicator))
			return false;
		if (soldOverElectronicMedium == null) {
			if (other.soldOverElectronicMedium != null)
				return false;
		} else if (!soldOverElectronicMedium.equals(other.soldOverElectronicMedium))
			return false;
		if (taxExemptionReasonCode == null) {
			if (other.taxExemptionReasonCode != null)
				return false;
		} else if (!taxExemptionReasonCode.equals(other.taxExemptionReasonCode))
			return false;
		if (taxReportingGroupIndicator == null) {
			if (other.taxReportingGroupIndicator != null)
				return false;
		} else if (!taxReportingGroupIndicator.equals(other.taxReportingGroupIndicator))
			return false;
		if (validFrom == null) {
			if (other.validFrom != null)
				return false;
		} else if (!validFrom.equals(other.validFrom))
			return false;
		if (validTo == null) {
			if (other.validTo != null)
				return false;
		} else if (!validTo.equals(other.validTo))
			return false;
		return true;
	}

}
