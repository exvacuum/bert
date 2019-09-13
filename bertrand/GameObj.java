package bertrand;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameObj {
	
	static final int TYPE_NULL = 0;
	static final int TYPE_SOLID = 1;
	static final int TYPE_LIGHT = 2;
	static final int TYPE_PLAYER = 3;
	static final int TYPE_LENS = 4;
	
	int x,y,w,h,type;
	Image img;
	String filename;
	File file;
	byte[] fileData;
	String dataString;
	ArrayList<Integer> keyList = new ArrayList<Integer>();
	Bertrand bert;
	
	GameObj(Image img, String filename, File file,  Bertrand bert) throws IOException{
		this.img = img;
		this.filename = filename;
		this.file = file;
		this.bert = bert;
		
		fileData = Files.readAllBytes(file.toPath());
		dataString = new String(fileData);
		StringBuilder dataBuilder = new StringBuilder(dataString);
		
		if(dataBuilder.lastIndexOf("----------------")+17<dataBuilder.length() && dataBuilder.lastIndexOf("----------------")+16>17) {
			String usefuldata = dataBuilder.substring(dataBuilder.lastIndexOf("----------------")+16);
			int[] dataKeys = Arrays.stream(usefuldata.split("\\s")).mapToInt(Integer::parseInt).toArray();  
			keyList = IntStream.of(dataKeys).boxed().collect(Collectors.toCollection(ArrayList::new));
			for(int i=0;i<keyList.size();i++){
			    System.out.println(keyList.get(i));
			} 
		}
		
		if(keyList.size()>=1&&keyList.get(0)!=null) {
			x = keyList.get(0);
		}else{
			x = bert.rw/2; 
		}
		
		if(keyList.size()>=2&&keyList.get(1)!=null) {
			this.y = keyList.get(1);
		}else{
			y = bert.rh/2; ; 
		}
		
		if(keyList.size()>=3&&keyList.get(2)!=null) {
			this.w = keyList.get(2);
		}else{
			w = 128; 
		}
		
		if(keyList.size()>=4&&keyList.get(3)!=null) {
			this.h = keyList.get(3);
		}else{
			h = 128; 
		}
		
		if(keyList.size()>=5&&keyList.get(4)!=null) {
			type = keyList.get(4);
		}else{
			type = TYPE_NULL;
		}
		
		switch(type) {
		case TYPE_PLAYER:
			if(bert.player!=null)System.out.println("You lobotomized Bert!");
			bert.player = new Player(bert,this);
			break;
		case TYPE_LENS:
			if(bert.lens!=null)System.out.println("Four Eyes!");
			bert.lens = new Lens(bert,this);
			break;
		}
	}
	
	void render(Graphics2D g) {
		switch(type) {
		case TYPE_NULL:
			break;
		case TYPE_SOLID:
			break;
		case TYPE_LIGHT:
			g.setPaint(new RadialGradientPaint(new Rectangle2D.Float(x-w,y-h,w*3,h*3), new float[]{0.0f,1f}, new Color[]{new Color(255,255,255,100),new Color(255,255,255,0)}, CycleMethod.NO_CYCLE));
			g.fill(new Ellipse2D.Float(x-w,y-h, w*3, h*3));
			g.setPaint(null);
			break;
		}
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g.drawImage(img, x, y, w, h, null);
		g.setColor(Color.BLACK);
		
	}
	
	void update() {
		switch(type) {
		case TYPE_NULL:
			return;
		case TYPE_SOLID:
			return;
		case TYPE_LIGHT:
			return;
		}
	}
}
