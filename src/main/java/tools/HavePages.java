package tools;

import java.util.ArrayList;
import java.util.List;

public class HavePages {
	public static List<Integer> getMyPages(int page,int total){
		int pageAmount = total / 5;
		if (total % 5 != 0) {
			pageAmount++;
		}
		List<Integer> pages = new ArrayList<Integer>();
		pages.add(page-1);
		pages.add(page);
		pages.add(page+1);
		pages.add(pageAmount);
		int tmp = page-2;
		for(int i=0;i<=2;i++){
			if(tmp<=0){
				tmp++;
			}
		}
		if(tmp+4>pageAmount){
			for(int i=tmp+4-pageAmount;i>0;i--){
				if(tmp-1>=1){
					tmp--;
				}
			}
		}
		
		for(int i=0;i<5;i++){
			if(tmp<=pageAmount){
				pages.add(tmp);
				tmp++;
			}
		}
		//System.out.println(pages.toString());
		return pages;
	}
}
