package jansible.util;

import jansible.model.database.InterfaceDbSort;

import java.util.List;

public class DbCommonUtils {

	public static <T extends InterfaceDbSort> void sortRoleRelation(List<T> sortList){
		for(int i = 0; i < sortList.size(); i++){
			T item = sortList.get(i);
			item.setSort(i + 1);
		}
	}

}
