package ar.uba.fi.mileem.utils;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.mileem.models.PublicationResult;

public class SearchCache {
	
	private static SearchCache INSTANCE = null;
	List<PublicationResult> results = null;

	private SearchCache(){
		results = new ArrayList<PublicationResult>();
	};
	
	static public SearchCache getInstance(){
		if(INSTANCE == null){
			INSTANCE =  new SearchCache();
		}
		return	INSTANCE;
	}
	
	public List<PublicationResult> getResults(){
		return results;
	}
	
	public List<PublicationResult> addResults(List<PublicationResult> moreResults ){
		results.addAll(moreResults);
		return  results;
	}

	public void clearResults(){
		results.clear();
	}
}
