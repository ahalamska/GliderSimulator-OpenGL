package GilterSimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.renderEngine.FontRenderer;
import engine.renderEngine.VAOsLoader;
import engine.text.FontType;
import engine.text.GUIText;
import engine.text.TextMeshData;


public class TextManager {

	private static TextManager instance;
	private VAOsLoader loader;
	private Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();
	private FontRenderer renderer;

	public static TextManager getInstance() {
		if(instance == null) {
			instance = new TextManager();
		}
		return instance;
	}

	public void init(VAOsLoader theLoader){
		renderer = new FontRenderer();
		loader = theLoader;
	}
	
	public void render(){
		renderer.render(texts);
	}
	
	public void loadText(GUIText text){
		FontType font = text.getFont();
		TextMeshData data = font.loadText(text);
		int vao = loader.loadGUITextToVAO(data.getVertexPositions(), data.getTextureCoords());
		text.setMeshInfo(vao, data.getVertexCount());
		List<GUIText> textBatch = texts.get(font);
		if(textBatch == null){
			textBatch = new ArrayList<GUIText>();
			texts.put(font, textBatch);
		}
		textBatch.add(text);
	}
	
	public void removeText(GUIText text){
		List<GUIText> textBatch = texts.get(text.getFont());
		textBatch.remove(text);
		if(textBatch.isEmpty()){
			texts.remove(texts.get(text.getFont()));
		}
	}
	
	public void cleanUp(){
		renderer.cleanUp();
	}

}
