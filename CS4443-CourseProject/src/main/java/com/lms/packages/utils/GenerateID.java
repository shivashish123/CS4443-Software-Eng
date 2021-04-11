package com.lms.packages.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lms.packages.repository.BookRepository;

public class GenerateID {	
	
	private Map<String,Integer> genres;
	private String[] genreList;
	private Map<String, HashMap<String, Integer>> subGenres;
	private Map<String, String[]> subGenreList;
	
	
	public GenerateID() {	
		
		System.out.println("Generateid");
		
		genres = new HashMap<String,Integer>();
		subGenres = new HashMap<String,HashMap<String,Integer>>();
		subGenreList = new HashMap<String,String[]>();
		
		//String genreList[] = {"Fiction","Mystery","Fantasy","Horror","Romance","Thriller","Science","Art"};
		String genreList[] = {"Fiction","Mystery"};
		subGenreList.put("Fiction",new String[]{"Fiction1","Fiction2"});
		subGenreList.put("Mystery",new String[]{"Mystery1","Mystery2"});
		
		
		genres = fillMap(genreList);
		for(String genre: genreList) {
			subGenres.put(genre,fillMap(subGenreList.get(genre)));
		}	
		System.out.println(subGenres.get("Fiction").get("Fiction1"));
		System.out.println(subGenres.get("Fiction").get("Fiction2"));
		System.out.println(subGenres.get("Mystery").get("Mystery1"));
		System.out.println(subGenres.get("Mystery").get("Mystery2"));

	}

	private HashMap<String, Integer> fillMap(String[] genreList2) {
		HashMap<String,Integer> genres2 = new HashMap<String,Integer>();
		int counter = 1;		
		for(String genre: genreList2) {
			genres2.put(genre,counter);
			counter++;
		}	
		return genres2;		
	}
	
	public String generateBookId(String genre,String subgenre , BookRepository bookRepository){
		String id = "ID";
		id+=genres.get(genre);
		id+=subGenres.get(genre).get(subgenre);
		
		int similarBookCount = bookRepository.countByGenreSubgenre(genre,subgenre);
		similarBookCount++;
		id+= String.valueOf(similarBookCount);
		
		return id;
		
		
	}
}
