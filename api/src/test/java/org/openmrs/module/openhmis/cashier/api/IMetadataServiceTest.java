/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.1 (the "License"); you may not use this file except in
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

package org.openmrs.module.openhmis.cashier.api;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.cashier.api.util.PagingInfo;

import java.util.Date;
import java.util.List;

public abstract class IMetadataServiceTest<S extends IMetadataService<E>, E extends BaseOpenmrsMetadata>
		extends IEntityServiceTest<S, E> {

	@Override
	protected void assertEntity(E expected, E actual) {
		super.assertEntity(expected, actual);

		Assert.assertEquals(expected.getChangedBy(), actual.getChangedBy());
		Assert.assertEquals(expected.getCreator(), actual.getCreator());
		Assert.assertEquals(expected.getDateChanged(), actual.getDateChanged());
		Assert.assertEquals(expected.getDateCreated(), actual.getDateCreated());
		Assert.assertEquals(expected.getDateRetired(), actual.getDateRetired());
		Assert.assertEquals(expected.getDescription(), actual.getDescription());
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.getRetired(), actual.getRetired());
		Assert.assertEquals(expected.getRetiredBy(), actual.getRetiredBy());
		Assert.assertEquals(expected.getRetireReason(), actual.getRetireReason());
	}

	/**
	 * @verifies retire the entity successfully
	 * @see IMetadataService#retire(E, String)
	 */
	@Test
	public void retire_shouldRetireTheEntitySuccessfully() throws Exception {
		String reason = "test retire";
		E entity = service.getById(0);

		service.retire(entity, reason);

		Context.flushSession();

		entity = service.getById(entity.getId());

		Assert.assertTrue(entity.getRetired());
		Assert.assertEquals(Context.getAuthenticatedUser(), entity.getRetiredBy());
		Assert.assertEquals(reason, entity.getRetireReason());
		Assert.assertTrue(entity.getDateRetired().before(new Date()));
	}

	/**
	 * @verifies throw NullPointerException when the entity is null
	 * @see IMetadataService#retire(E, String)
	 */
	@Test(expected = NullPointerException.class)
	public void retire_shouldThrowNullPointerExceptionWhenTheEntityIsNull() throws Exception {
		service.retire(null, "something");
	}

	/**
	 * @verifies throw IllegalArgumentException when no reason is given
	 * @see IMetadataService#retire(E, String)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void retire_shouldThrowIllegalArgumentExceptionWhenNoReasonIsGiven() throws Exception {
		E entity = service.getById(0);

		service.retire(entity, null);
	}

	/**
	 * @verifies throw NullPointerException if the entity is null
	 * @see IMetadataService#unretire(E)
	 */
	@Test(expected = NullPointerException.class)
	public void unretire_shouldThrowNullPointerExceptionIfTheEntityIsNull() throws Exception {
		service.unretire(null);
	}

	/**
	 * @verifies unretire the entity
	 * @see IMetadataService#unretire(E)
	 */
	@Test
	public void unretire_shouldUnretireTheEntity() throws Exception {
		String reason = "test retire";
		E entity = service.getById(0);
		service.retire(entity, reason);

		Context.flushSession();

		entity = service.getById(entity.getId());
		Date dateRetired = entity.getDateRetired();
		Assert.assertTrue(entity.getRetired());
		service.unretire(entity);

		Context.flushSession();

		entity = service.getById(entity.getId());
		Assert.assertFalse(entity.getRetired());
		Assert.assertNull(entity.getRetiredBy());
		Assert.assertNull(entity.getRetireReason());
		Assert.assertEquals(dateRetired, entity.getDateRetired());
	}

	/**
	 * @verifies return all retired entities when retired is set to true
	 * @see IMetadataService#getAll(boolean)
	 */
	@Test
	public void getAll_shouldReturnAllRetiredEntitiesWhenRetiredIsSetToTrue() throws Exception {
		String reason = "test retire";
		E entity = service.getById(0);
		service.retire(entity, reason);

		Context.flushSession();

		List<E> entities = service.getAll(true);
		Assert.assertNotNull(entities);
		Assert.assertEquals(1, entities.size());
		assertEntity(entity, entities.get(0));
	}

	/**
	 * @verifies return all unretired entities when retired is set to false
	 * @see IMetadataService#getAll(boolean)
	 */
	@Test
	public void getAll_shouldReturnAllUnretiredEntitiesWhenRetiredIsSetToFalse() throws Exception {
		String reason = "test retire";
		E entity = service.getById(0);
		service.retire(entity, reason);

		Context.flushSession();

		List<E> entities = service.getAll(false);
		Assert.assertNotNull(entities);
		Assert.assertEquals(getTestEntityCount() - 1, entities.size());
	}

	/**
	 * @verifies throw IllegalArgumentException if the name is null
	 * @see IMetadataService#findByName(String, boolean)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void findByName_shouldThrowIllegalArgumentExceptionIfTheNameIsNull() throws Exception {
		service.findByName(null, true);
	}

	/**
	 * @verifies throw IllegalArgumentException if the name is empty
	 * @see IMetadataService#findByName(String, boolean)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void findByName_shouldThrowIllegalArgumentExceptionIfTheNameIsEmpty() throws Exception {
		service.findByName("", true);
	}

	/**
	 * @verifies throw IllegalArgumentException if the name is longer than 255 characters
	 * @see IMetadataService#findByName(String, boolean)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void findByName_shouldThrowIllegalArgumentExceptionIfTheNameIsLongerThan255Characters() throws Exception {
		service.findByName(StringUtils.repeat("A", 256), true);
	}

	/**
	 * @verifies return an empty list if no entities are found
	 * @see IMetadataService#findByName(String, boolean)
	 */
	@Test
	public void findByName_shouldReturnAnEmptyListIfNoEntitiesAreFound() throws Exception {
		List<E> entities = service.findByName("NotAValidName", true);

		Assert.assertNotNull(entities);
		Assert.assertEquals(0, entities.size());
	}

	/**
	 * @verifies not return retired entities unless specified
	 * @see IMetadataService#findByName(String, boolean)
	 */
	@Test
	public void findByName_shouldNotReturnRetiredEntitiesUnlessSpecified() throws Exception {
		E entity = service.getById(0);
		service.retire(entity, "something");
		Context.flushSession();

		List<E> entities = service.findByName("t", false);
		Assert.assertNotNull(entities);
		Assert.assertEquals(getTestEntityCount() - 1, entities.size());

		entities = service.findByName("t", true);
		Assert.assertNotNull(entities);
		Assert.assertEquals(getTestEntityCount(), entities.size());
	}

	/**
	 * @verifies return entities that start with the specified name
	 * @see IMetadataService#findByName(String, boolean)
	 */
	@Test
	public void findByName_shouldReturnEntitiesThatStartWithTheSpecifiedName() throws Exception {
		E entity = service.getById(0);

		// Search using the first four characters in the name
		List<E> entities = service.findByName(entity.getName().substring(0, 4), false);
		Assert.assertTrue(entities.size() > 0);

		// Make sure the entity is in the results
		E found = null;
		for (E result : entities) {
			if (result.getId().equals(entity.getId())) {
				found = result;
				break;
			}
		}

		Assert.assertNotNull("Could not find entity in search results", found);
	}

	/**
	 * @verifies return all specified entity records if paging is null
	 * @see IMetadataService#getAll(boolean, org.openmrs.module.openhmis.cashier.api.util.PagingInfo)
	 */
	@Test
	public void getAll_shouldReturnAllSpecifiedEntityRecordsIfPagingIsNull() throws Exception {
		String reason = "test retire";
		E entity = service.getById(0);
		service.retire(entity, reason);

		Context.flushSession();

		List<E> entities = service.getAll(true, null);

		Assert.assertNotNull(entities);
		Assert.assertEquals(1, entities.size());
		assertEntity(entity, entities.get(0));
	}

	/**
	 * @verifies return all specified entity records if paging page or size is less than one
	 * @see IMetadataService#getAll(boolean, org.openmrs.module.openhmis.cashier.api.util.PagingInfo)
	 */
	@Test
	public void getAll_shouldReturnAllSpecifiedEntityRecordsIfPagingPageOrSizeIsLessThanOne() throws Exception {
		String reason = "test retire";
		E entity = service.getById(0);
		service.retire(entity, reason);

		Context.flushSession();

		PagingInfo paging = new PagingInfo(0, 1);
		List<E> entities = service.getAll(true, paging);

		Assert.assertNotNull(entities);
		Assert.assertEquals(1, entities.size());
		assertEntity(entity, entities.get(0));

		paging = new PagingInfo(1, 0);
		entities = service.getAll(true, paging);

		Assert.assertNotNull(entities);
		Assert.assertEquals(1, entities.size());
		assertEntity(entity, entities.get(0));
	}

	/**
	 * @verifies set the paging total records to the total number of entity records
	 * @see IMetadataService#getAll(boolean, org.openmrs.module.openhmis.cashier.api.util.PagingInfo)
	 */
	@Test
	public void getAll_shouldSetThePagingTotalRecordsToTheTotalNumberOfEntityRecords() throws Exception {
		PagingInfo paging = new PagingInfo(1, 1);
		List<E> entities = service.getAll(false, paging);

		Assert.assertNotNull(entities);
		Assert.assertEquals(1, entities.size());
		Assert.assertEquals(Long.valueOf(getTestEntityCount()), paging.getTotalRecordCount());
	}

	/**
	 * @verifies not get the total paging record count if it is more than zero
	 * @see IMetadataService#getAll(boolean, org.openmrs.module.openhmis.cashier.api.util.PagingInfo)
	 */
	@Test
	public void getAll_shouldNotGetTheTotalPagingRecordCountIfItIsMoreThanZero() throws Exception {
		PagingInfo paging = new PagingInfo(1, 1);
		List<E> entities = service.getAll(false, paging);

		Assert.assertNotNull(entities);
		Assert.assertEquals(1, entities.size());
	}

	/**
	 * @verifies return paged entity records if paging is specified
	 * @see IMetadataService#getAll(boolean, org.openmrs.module.openhmis.cashier.api.util.PagingInfo)
	 */
	@Test
	public void getAll_shouldReturnPagedEntityRecordsIfPagingIsSpecified() throws Exception {
		PagingInfo paging = new PagingInfo(1, 1);
		List<E> entities;

		for (int i = 0; i < getTestEntityCount(); i++) {
			paging.setPage(i + 1);
			entities = service.getAll(paging);

			Assert.assertNotNull(entities);
			Assert.assertEquals(1, entities.size());
			Assert.assertEquals(i, (int)entities.get(0).getId());
		}
	}
}

