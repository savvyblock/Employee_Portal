package com.esc20.util;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CodeIterator implements Iterator<String> {
	@SuppressWarnings("unused")
	private List<String> codes;
	private Map<String,BigDecimal> values;
	private Iterator<String> iter;
	private String next;

	public CodeIterator(List<String> codes, Map<String, BigDecimal> values) {
		this.codes = codes;
		this.values = values;
		iter = codes.iterator();
		next();
	}

	@Override
	public boolean hasNext() {
		return next != null;
	}

	@Override
	public String next() {
		String result = next;
		BigDecimal temp = null;

		while((temp == null || temp.doubleValue() == 0) && iter.hasNext()) {
			next = iter.next();
			temp = values.get(next);

			if(!iter.hasNext()) {
				next = null;
			}
		}

		return result;
	}

	@Override
	public void remove() {
		iter.remove();
	}
}