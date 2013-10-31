package org.jbpm.ee.support.test;

import java.io.Serializable;

public class TestLoan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2112369029861697702L;

	private Integer loanAmount;
	
	private Boolean isEligible;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((isEligible == null) ? 0 : isEligible.hashCode());
		result = prime * result
				+ ((loanAmount == null) ? 0 : loanAmount.hashCode());
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
		TestLoan other = (TestLoan) obj;
		if (isEligible == null) {
			if (other.isEligible != null)
				return false;
		} else if (!isEligible.equals(other.isEligible))
			return false;
		if (loanAmount == null) {
			if (other.loanAmount != null)
				return false;
		} else if (!loanAmount.equals(other.loanAmount))
			return false;
		return true;
	}

	public Integer getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Integer loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Boolean getIsEligible() {
		return isEligible;
	}

	public void setIsEligible(Boolean isEligible) {
		this.isEligible = isEligible;
	}

	@Override
	public String toString() {
		return "TestLoan [loanAmount=" + loanAmount + ", isEligible="
				+ isEligible + "]";
	}
}
