package cn.boxiao.bxn.meeting.base.model;

/*
 * Copyright 2008-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.Serializable;

/**
 * Basic Java Bean implementation of {@code Pageable}.
 *
 * @author Oliver Gierke
 */
public class PageRequest implements Pageable, Serializable {

	private static final long serialVersionUID = 8280485938848398236L;

	private final int page;
	private final int size;
	private final Sort sort;

	/**
	 * Creates a new {@link PageRequest}. Pages are zero indexed, thus providing
	 * 0 for {@code page} will return the first page.
	 *
	 * @param size
	 * @param page
	 */
	public PageRequest(int page, int size) {

		this(page, size, null);
	}

	/**
	 * Creates a new {@link PageRequest} with sort parameters applied.
	 *
	 * @param page
	 * @param size
	 * @param direction
	 * @param properties
	 */
	public PageRequest(int page, int size, Sort.Direction direction, String... properties) {

		this(page, size, new Sort(direction, properties));
	}

	/**
	 * Creates a new {@link PageRequest} with sort parameters applied.
	 *
	 * @param page
	 * @param size
	 * @param sort
	 */
	public PageRequest(int page, int size, Sort sort) {

		if (0 > page) {
			throw new IllegalArgumentException("Page index must not be less than zero!");
		}

		if (0 >= size) {
			throw new IllegalArgumentException("Page size must not be less than or equal to zero!");
		}

		this.page = page;
		this.size = size;
		this.sort = sort;
	}

	public int getPageSize() {

		return size;
	}

	public int getPageNumber() {

		return page;
	}

	public int getOffset() {

		return page * size;
	}

	public Sort getSort() {

		return sort;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + page;
		result = prime * result + size;
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
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
		PageRequest other = (PageRequest) obj;
		if (page != other.page)
			return false;
		if (size != other.size)
			return false;
		if (sort == null) {
			if (other.sort != null)
				return false;
		} else if (!sort.equals(other.sort))
			return false;
		return true;
	}

}
