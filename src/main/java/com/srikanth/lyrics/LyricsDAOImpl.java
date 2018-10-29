package com.srikanth.lyrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

public class LyricsDAOImpl implements LyricsDAO{
	
	@Autowired
	private DDB ddb;

	public String getLyricText(String artist, String title) {
		Table table = ddb.getClient().getTable("Lyrics");
		GetItemSpec spec = new GetItemSpec().withPrimaryKey("artist", artist, "song", title);
		try {
			System.out.println("Attempting to read Lyrics table item");
			Item outcome = table.getItem(spec);
			System.out.println(outcome.toJSON());
			return outcome.get("text").toString();
		}
		catch(Exception e)
		{
			System.out.println("Unable to read item, exception caugh is: "+e.getMessage());
		}
		return null;
	}

	public String[] getTitlesbyArtist(String artist) {
		Table table = ddb.getClient().getTable("Lyrics");
		HashMap<String, String> nameMap = new HashMap<String, String>();
		nameMap.put(":artist", artist);
		QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("artist = :artist").withNameMap(nameMap);
		ItemCollection<QueryOutcome> items =table.query(querySpec);
		List<Item> itemList = new ArrayList<Item>();
		Iterator<Item> itr = items.iterator();
		while(itr.hasNext())
		{
			itemList.add(itr.next());
		}
		
		//String[] titles = itemList.stream().map(String::getAttribute("song")).collect(toArray());
		return null;
	}

	public String getArtist(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] searchLyrics(String[] words) {
		// TODO Auto-generated method stub
		return null;
	}

}
