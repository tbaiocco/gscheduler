package com.nuovonet.gscheduler.util;

import java.util.Collection;

public class AppUtil {

	/**
	 * Checks if is collection empty.
	 *
	 * @param collection the collection
	 * @return true, if is collection empty
	 */
	private static boolean isCollectionEmpty(Collection<?> collection) {
		if (collection == null || collection.isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if is object empty.
	 *
	 * @param object the object
	 * @return true, if is object empty
	 */
	public static boolean isObjectEmpty(Object object) {
		if(object == null) return true;
		else if(object instanceof String) {
			if (((String)object).trim().length() == 0) {
				return true;
			}
		} else if(object instanceof Collection) {
			return isCollectionEmpty((Collection<?>)object);
		}
		return false;
	}
	
}
