package desktopclock;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class DesktopClock extends JFrame implements Runnable{
	private Thread thread;
	Canvas canvas = new Canvas();
	private BufferStrategy buffer;
	private Graphics2D g;
	static String title= "Analog Clock" ;
	static int size=350;

	public DesktopClock(String title,int size){
		DesktopClock.title = title;
		DesktopClock.size = size;
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(size+5, size+25) ;
		setResizable(false);
		add(canvas);
		canvas.setSize(size, size);
		pack();
	}
	
	public void drawHands(){	
		buffer = canvas.getBufferStrategy();
		if(buffer == null){
			canvas.createBufferStrategy(3);
			return;
		}
		g = (Graphics2D)buffer.getDrawGraphics();
		g.clearRect(0, 0, size, size);

		int center =size/2;
	    int Xaxis, Yaxis;
	    int radius;
	    double time = System.currentTimeMillis();
	    GradientPaint pat = new GradientPaint(0f, 0f, Color. white,30f, 30f, Color. LIGHT_GRAY, true) ;
		g. setPaint(pat) ;
		g. setRenderingHint(RenderingHints. KEY_ANTIALIASING,RenderingHints. VALUE_ANTIALIAS_ON) ;
	    Rectangle2D.Float rc = new Rectangle2D.Float(0, 0, size, size);
	    g.clearRect(0,0, size, size);
	    g.fill(rc);
	    g.setColor(Color. blue) ;
	    g.fillOval(10, 10, size-20, size-20);
	    g.setColor(Color. white) ;
	    g.fillOval(20, 20, size-40, size-40);
	    g.setColor(Color. black) ;
	    g.fillOval(center-10, center-10, 18, 18);
	    
//	    Draw  numbers

	    double teter;
	    for(int i=1; i<=12; i++){
	    	teter = (i/12.0)*Math.PI*2;
	    	radius = center-50;
	    	String a = Integer.toString(i);
	    	Xaxis = (int) ((center)+(Math.sin(teter)*radius));
	    	Yaxis = (int) ((center)-(Math.cos(teter)*radius));
	    	g.setFont(new Font("georgia",Font.BOLD, (size/25)));
	    	g.setColor(Color.black);
	    	g.drawString(a, Xaxis, Yaxis);
	    	}
	    
	    
//	 	Draw second hand
	    
    	radius = center-40;
    	time =System.currentTimeMillis()/(1000.0*60)*Math.PI*2;
    	Xaxis = (int) ((center)+(Math.sin(time)*radius));
    	Yaxis = (int) ((center)-(Math.cos(time)*radius));
    	g.setColor(Color.red);
    	g.setStroke(new BasicStroke(1));
    	g.drawLine(center, center, Xaxis, Yaxis);
    	
    	
//    	draw minute hand
        radius = center-70;
	    time =System.currentTimeMillis()/(60.0*60.0*1000.0)*Math.PI*2;
	    Xaxis = (int) ((center)+(Math.sin(time)*radius));
    	Yaxis = (int) ((center)-(Math.cos(time)*radius));
    	g.setColor(Color.blue);
    	g.setStroke(new BasicStroke(6));
    	g.drawLine(center, center, Xaxis, Yaxis);
    	
//    	draw hour hand
        radius = center-120;
	    time =System.currentTimeMillis()/(12.0*60.0*60.0*1000.0)*Math.PI*2;
	    Xaxis = (int) ((center)+(Math.sin(time)*radius));
    	Yaxis = (int) ((center)-(Math.cos(time+45)*radius));
    	g.setColor(Color.black);
    	g.setStroke(new BasicStroke(10));
    	g.drawLine(center, center, Xaxis, Yaxis);
    	
    	buffer.show();
    	g.dispose();
	}
	public synchronized void start(){
		thread =new Thread(this);
		thread.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			drawHands();
		}
	}
 
    /*------------------------------------*\
	Main
    \*------------------------------------*/
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
	DesktopClock eng = new DesktopClock("Analog Clock", 350);
	eng.start();
    }
    
}
