package dev.exvaccum.bert.world.interfaces.pc;

import dev.exvaccum.bert.Bert;
import dev.exvaccum.bert.control.Utilities;
import dev.exvaccum.bert.control.WDraggable;
import dev.exvaccum.bert.world.interfaces.PCInterface;
import dev.exvaccum.bert.world.interfaces.pc.files.MetaFile;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class Notepad extends MetaWindow implements MouseListener, MouseWheelListener {

    //File to view
    BufferedReader br;
    BufferedWriter bw;
    ArrayList<String> lines = new ArrayList<>();
    int viewableCountV, startOfViewV, viewableCountH, startOfViewH, modifyingLine, modifyingIndex, longestLine;
    static Font noteFont = new Font("Lucida Console", Font.PLAIN, 11);
    FontMetrics noteMetrics;

    WDraggable scrollbarV, scrollbarH;

    boolean modified;

    /**
     * Notepad viewer, shows a desired image from file browser
     * @param x x-coordinate of window
     * @param y y-coordinate of window
     * @param width width of window
     * @param height  height of window
     * @param owner PC interface to which this window belongs
     * @param fname filename for image loading
     */
    public Notepad(int x, int y, int width, int height, PCInterface owner, String fname) {
        super(x, y, width, height, owner);
        title = "Notepad - "+fname+" (Read-Only)";
        bgColor = new Color(220,220,220);
        type = WindowType.NOTEPAD;
        resizable = true;

        //Load file
        try {
            br = new BufferedReader(new FileReader(Utilities.getResourceAsFile(MetaFile.pathMap.get(fname))));
            bw = new BufferedWriter(new FileWriter(Utilities.getResourceAsFile(MetaFile.pathMap.get(fname))));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            getLongestLine();
        }catch (IOException e){
            e.printStackTrace();
        }

        bounds.height += titleBar.height;
        startOfViewV = 0;

        scrollbarV = new WDraggable(bounds.x+bounds.width-20,bounds.y+titleBar.height,20, bounds.height-titleBar.height-20, this){

            {
                setBgC(new Color(0,0,0,0));
                setBordered(false);
                setText("");
            }

            @Override
            public void performAction(){
                startOfViewV =Math.min((int)((Bert.mBert.input.mouseLocation.y-ownerWindow.owner.getY()-ownerWindow.owner.getInsets().top-ownerWindow.bounds.y-titleBar.height)/(double)(bounds.height-40)*lines.size()),lines.size()- viewableCountV);
            }
        };

        scrollbarH = new WDraggable(bounds.x,bounds.y+bounds.height-20,bounds.width-20, 20, this){

            {
                setBgC(new Color(0,0,0,0));
                setBordered(false);
                setText("");
            }

            @Override
            public void performAction(){
                startOfViewH =Math.min((int)((Bert.mBert.input.mouseLocation.x-ownerWindow.owner.getX()-ownerWindow.owner.getInsets().left-ownerWindow.bounds.x)/(double)(bounds.width-20)*longestLine),longestLine- viewableCountH);
            }
        };

        windowDraggables.add(scrollbarV);
        windowDraggables.add(scrollbarH);

        owner.addMouseListener(this);
        owner.addMouseWheelListener(this);

        modifyingIndex = modifyingLine = 0;
    }

    @Override
    public void update(){
        super.update();
        if(noteMetrics!=null){
            viewableCountH = ((bounds.width-20)/noteMetrics.getWidths()[0]);
            viewableCountV = ((bounds.height-40-noteMetrics.getHeight())/noteMetrics.getHeight());
        }
        scrollbarV.setBounds(bounds.x+bounds.width-20,bounds.y+titleBar.height,20, bounds.height-titleBar.height-20);
        scrollbarH.setBounds(bounds.x,bounds.y+bounds.height-20,bounds.width-20, 20);
        startOfViewV = Math.max(Math.min(lines.size()- viewableCountV, startOfViewV),0);
        startOfViewH = Math.max(Math.min(longestLine- viewableCountH, startOfViewH),0);
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        g2.setColor(Color.BLACK);
        g2.setFont(noteFont);
        noteMetrics = g2.getFontMetrics();
        for(int i = startOfViewV; i < Math.min(startOfViewV + viewableCountV,lines.size()); i++){
            if(lines.get(i).length()>startOfViewH) g2.drawString((lines.get(i).length()>viewableCountH||startOfViewH!=0)?lines.get(i).substring(startOfViewH,Math.min(startOfViewH+viewableCountH,lines.get(i).length())):lines.get(i),bounds.x+10, bounds.y+titleBar.height+20+(noteMetrics.getHeight()*(i- startOfViewV)));
        }
        g2.setColor(System.currentTimeMillis()%1000<500?Color.BLACK:new Color(0,0,0,0));
        if(focused&&modifyingIndex-startOfViewH>=0&&modifyingIndex-startOfViewH<=viewableCountH&&modifyingLine-startOfViewV>=0&&modifyingLine-startOfViewV<=viewableCountV)g2.fillRect((bounds.x+10+(noteMetrics.getWidths()[0]*(modifyingIndex-startOfViewH))),(bounds.y+titleBar.height+20+(noteMetrics.getHeight()*(modifyingLine- startOfViewV -1))),2,noteMetrics.getHeight());
        g2.setColor(Color.DARK_GRAY);
        if(viewableCountV <lines.size()) {
            g2.fillRect(bounds.x + bounds.width - 20, (int) (bounds.y + titleBar.height + (((double) viewableCountV / lines.size() * (bounds.height - 40)) / viewableCountV * startOfViewV)), 20, Math.max((int) ((double) viewableCountV / lines.size() * (bounds.height - 40)),2));
            scrollbarV.draw(g2);
        }
        g2.setColor(Color.DARK_GRAY);
        if(viewableCountH < longestLine) {
            g2.fillRect((int) (bounds.x + (((double) viewableCountH / longestLine * (bounds.width - 20)) / viewableCountH * startOfViewH)), bounds.y + bounds.height - 20, Math.max((int) ((double) viewableCountH / longestLine * (bounds.width - 20)),2), 20);
            scrollbarH.draw(g2);
        }
    }

    public void nav(int xdir, int ydir){
        modifyingLine = Math.max(0,Math.min(modifyingLine+ydir,lines.size()));
        modifyingIndex = Math.max(0,Math.min(modifyingIndex+xdir,lines.get(modifyingLine).length()));
    }

    public void newLine(KeyEvent e){
        lines.add(modifyingLine+1,"");
        if(!e.isShiftDown()) {
            String split = lines.get(modifyingLine).substring(modifyingIndex);
            lines.set(modifyingLine, lines.get(modifyingLine).substring(0, modifyingIndex));
            lines.set(modifyingLine + 1, split);
        }
        modifyingLine++;
        modifyingIndex=0;
    }

    public void typeChar(KeyEvent e){
        if((e.getKeyChar()!=KeyEvent.CHAR_UNDEFINED)){
            lines.set(modifyingLine,lines.get(modifyingLine).substring(0,modifyingIndex)+e.getKeyChar()+lines.get(modifyingLine).substring(modifyingIndex));
            modifyingIndex++;
        }
    }

    public void shortCut(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_S){
            owner.windows.add(new PopUp(bounds.x+40,bounds.y+40,120,60,owner,"File is read-only."));
        }
    }

    public void deleteChar(){
        if(modifyingIndex>0){
            lines.set(modifyingLine,(lines.get(modifyingLine).substring(0,modifyingIndex-1)+lines.get(modifyingLine).substring(modifyingIndex)));
        }
        if(modifyingLine>0)modifyingIndex--;
        else modifyingIndex = Math.max(0,modifyingIndex-1);
        if(modifyingIndex<0){
            int oldLine = modifyingLine;
            modifyingLine= Math.max(modifyingLine-1,0);
            if(oldLine!=modifyingLine) {
                modifyingIndex = lines.get(modifyingLine).length();
                StringBuilder lineConcat = new StringBuilder(lines.get(modifyingLine));
                lineConcat.append(lines.get(oldLine));
                lines.set(modifyingLine,lineConcat.toString());
                lines.remove(oldLine);
                getLongestLine();
            }
        }
    }

    public void getLongestLine(){
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if(line.length()>longestLine)longestLine=line.length();
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if(focused&&new Rectangle(owner.getX()+bounds.x,owner.getY()+owner.getInsets().top+bounds.y+titleBar.height,bounds.width-20,bounds.height-titleBar.height-20).contains(Bert.mBert.input.mouseLocation)){
            modifyingLine = Math.min((int)((Bert.mBert.input.mouseLocation.y-owner.getY()-bounds.getY()-titleBar.height-40)/noteMetrics.getHeight())+startOfViewV,lines.size()-1);
            modifyingIndex = Math.min((int)((Bert.mBert.input.mouseLocation.x-owner.getX()-bounds.getX()-10)/noteMetrics.getWidths()[0]+startOfViewH),lines.get(modifyingLine).length());
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        if(focused)
        if(!mouseWheelEvent.isShiftDown()) {
            startOfViewV = Math.max(0, Math.min(startOfViewV + mouseWheelEvent.getUnitsToScroll(), lines.size() - viewableCountV));
            return;
        }else{
            startOfViewH = Math.max(0, Math.min(startOfViewH + mouseWheelEvent.getUnitsToScroll(), longestLine - viewableCountH));
            return;
        }
    }
}
