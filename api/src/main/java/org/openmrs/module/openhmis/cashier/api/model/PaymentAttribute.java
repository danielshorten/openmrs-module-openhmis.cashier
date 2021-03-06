/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.openhmis.cashier.api.model;

import org.openmrs.BaseOpenmrsData;

/**
 * Model class that represents a payment mode attribute for a particular {@link Payment}.
 */
public class PaymentAttribute extends BaseOpenmrsData {
	private Integer paymentAttributeId;
	private Payment payment;
	private PaymentModeAttributeType paymentModeAttributeType;
	private String value;

	public Integer getId() {
		return paymentAttributeId;
	}
	public void setId(Integer id) {
		paymentAttributeId = id;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public PaymentModeAttributeType getPaymentModeAttributeType() {
		return paymentModeAttributeType;
	}

	public void setPaymentModeAttributeType(PaymentModeAttributeType paymentModeAttributeType) {
		this.paymentModeAttributeType = paymentModeAttributeType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
