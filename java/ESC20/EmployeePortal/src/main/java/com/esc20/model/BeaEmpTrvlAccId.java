package com.esc20.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BeaEmpTrvlAccId implements Serializable {

	private static final long serialVersionUID = -8885555378468832744L;
	private String tripNbr;
	private long tripSeqNbr;
	private long distrSeqNbr;
	
	public BeaEmpTrvlAccId() {
	}

	public BeaEmpTrvlAccId(Long tripSeqNbr) {
		this.tripSeqNbr = tripSeqNbr;
    }

    @Column(name = "TRIP_NBR", nullable = false, length = 9, columnDefinition = "long default 0")
	public String getTripNbr() {
		return tripNbr;
	}

	public void setTripNbr(String tripNbr) {
		this.tripNbr = tripNbr;
	}

	@Column(name = "TRIP_SEQ_NBR", nullable = false, length = 2, columnDefinition = "long default 0")
	public long getTripSeqNbr() {
		return tripSeqNbr;
	}

	public void setTripSeqNbr(long tripSeqNbr) {
		this.tripSeqNbr = tripSeqNbr;
	}

	@Column(name = "DISTR_SEQ_NBR", nullable = false, length = 2, columnDefinition = "long default 0")
	public long getDistrSeqNbr() {
		return distrSeqNbr;
	}

	public void setDistrSeqNbr(long distrSeqNbr) {
		this.distrSeqNbr = distrSeqNbr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (distrSeqNbr ^ (distrSeqNbr >>> 32));
		result = prime * result + ((tripNbr == null) ? 0 : tripNbr.hashCode());
		result = prime * result + (int) (tripSeqNbr ^ (tripSeqNbr >>> 32));
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
		BeaEmpTrvlAccId other = (BeaEmpTrvlAccId) obj;
		if (distrSeqNbr != other.distrSeqNbr)
			return false;
		if (tripNbr == null) {
			if (other.tripNbr != null)
				return false;
		} else if (!tripNbr.equals(other.tripNbr))
			return false;
		if (tripSeqNbr != other.tripSeqNbr)
			return false;
		return true;
	}
}
