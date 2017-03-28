package ir.bpadashi.requester.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;


public class Mapper {

	public <T> Object map(StringBuilder json, Class<T> typeClass) throws JSONException, Exception {

		Object jsonObj = new JSONTokener(json.toString()).nextValue();

		if (jsonObj instanceof JSONObject)
			return mapJsonToBeanObject(json, typeClass);
		else if (jsonObj instanceof JSONArray)
			return mapJsonToBeanArray(json, typeClass);
		throw new Exception();

	}

	public <T> Object mapJsonToBeanArray(StringBuilder json, Class<T> typeClass) throws Exception {


		try {
			return new Gson().fromJson(json.toString(), new ListOfJson<T>(typeClass));
		} catch (JsonParseException e) {
			e.printStackTrace();
			throw new Exception();
		}

	}

	public <T> Object mapJsonToBeanObject(StringBuilder json,Class<T> typeClass) throws Exception {

		try {
			return new Gson().fromJson(json.toString(), typeClass);
		} catch (JsonParseException e) {
			e.printStackTrace();
			throw new Exception();
		}
	}
}
