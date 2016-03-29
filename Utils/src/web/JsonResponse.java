package web;

import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import security.Encryptor;

public class JsonResponse {
	
	public HttpServletResponse resp;
	private HashMap<String, Object> ret;
	private JSONObject data;
	private String key = null;
	boolean rendered;
	
	public JsonResponse(HttpServletResponse resp) {
		ret = new HashMap<String, Object>();
		ret.put("status", 1);
		
		data = new JSONObject();
		this.resp = resp;
		rendered = false;
	}

	public void encryptResponseWithKey(String key){
		this.key = key;
	}
	
	public void addData(String key, Object value) throws Exception{
		data.put(key, value);
	}
	
	public void addInt(String key, int value) throws Exception{
		data.put(key, value);
	}

	public void addCollection(String key, Collection<Object> col) throws Exception{
		data.put(key, col);
	}
	
	public void addBoolean(String key, boolean value) throws Exception{
		data.put(key, value);
	}
	
	public void succ() throws Exception{
		render();
	}
	
	public void error() throws Exception{
		error("");
	}
	
	public void errorWithCode(int code) throws Exception{
		ret.put("status", 0);
		ret.put("errCode", code);
		render();
	}
	
	public void error(String message) throws Exception{
		ret.put("status", 0);
		ret.put("message", message);
		render();
	}
	
	private String encryptResponse(String json){
		return Encryptor.Base64Encode(Encryptor.DESEncrypt(json, key));
	}
	
	public boolean isRendered(){
		return rendered;
	}
	
	public void render() throws Exception{
		if(rendered){
			throw new Exception("Double rendered.");
		}
		rendered = true;
		if(data.length() > 0){
			if(StringUtils.isNotEmpty(key)){
				ret.put("data", encryptResponse(data.toString()));
			}
			else{
				ret.put("data", data);
			}
		}
		JSONObject json = new JSONObject(ret);
		resp.setContentType("text/json; charset=UTF-8");
		resp.getOutputStream().write(json.toString().getBytes("UTF-8"));
	}

}
