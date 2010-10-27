package src;

import javax.swing.*;
import java.awt.geom.*;
import java.awt.Graphics;
import java.util.*;
import java.util.Random;
import java.awt.Color;

public class ScatterPlot extends JFrame {
	
  public ScatterPlot(ArrayList<ArrayList<ArrayList<Double>>> dataPoints){
    super("ScatterPlot");
    setSize(500, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    final ArrayList<List> ALofL = new ArrayList<List>();
    

    // Below will be replaced with the imported coordinates.
    for (int i = 0; i < dataPoints.size(); i++) {
      ALofL.add(new ArrayList());
      for(int j = 0; j < dataPoints.get(i).size(); j++){
        ALofL.get(i).add(new Point2D.Double(dataPoints.get(i).get(j).get(0), dataPoints.get(i).get(j).get(1)));
        Collections.shuffle(ALofL.get(i));
      }
    }

    // Above will be replaced with the imported coordinates.
    
	  JPanel panel = new JPanel() {
	      public void paintComponent(Graphics g) {
	        // Plot points below.
	        int wth = getWidth();
	        int hgt = getHeight();
	        for(int i = 0; i < ALofL.size(); i++){
	        	for(int j = 0; j < ALofL.get(i).size(); j++){
	        		Point2D.Double pt = (Point2D.Double)ALofL.get(i).get(j);
	        		switch(i){
		        		case 0: g.setColor(Color.red);break;
		        		case 1: g.setColor(Color.blue);break;
		        		case 2: g.setColor(Color.green);break;
		        		case 3: g.setColor(Color.yellow);break;
		        		case 4: g.setColor(Color.orange);break;
		        		case 5: g.setColor(Color.pink);break;
		        		case 6: g.setColor(Color.white);break;
	        			default: g.setColor(Color.black);break;
	        				     
	        		}
	        		int x = (int)(pt.x*2 + wth/2) - 2;
	        		int y = (int)(-pt.y*2 + hgt/2) - 2;
	        		//x *= 2;
	        		//y *= 2;
	        		g.fillOval(x, y, 4, 4);
	        	}
	        }
	        // Plot points above.
	
	        // Draw the axes below.
	        int width = getWidth();
	        int height = getHeight();
	        setVisible(true);
	        g.setColor(Color.black);
	        g.drawLine(0, height/2, width, height/2); // x-axis
	        g.drawLine(width/2, 0, width/2, height); // y-axis
	        // Draw the axes above.
	        
	        //Label the axes below.
	        g.setColor(Color.black);
	        g.drawString("-20", width/2, height/2 +20);
	        g.drawString("20", width/2, height/2 - 20);
	        g.drawString("-40", width/2, height/2 + 40);
	        g.drawString("40", width/2, height/2 - 40);
	        // Label the axes above.
	
	        // Test geom below.
	        g.drawOval(width/2 - 3, height/2 - 3, 6, 6);
	        // Test geom above.
	      }
	    };
	
	    setContentPane(panel);
	
	    int width = getWidth();
	    int height = getHeight();
	    setBounds(20, 20, width, height);
	    // Four values:
	    // 1: x-coordinate of the top left of the window on the screen (starting
	    //    from top left of the screen).
	    // 2: y-coordinate of the top left of the window on the screen (starting
	    //    from top left of the screen).
	    // 3: width of the window.
	    // 4: height of the window.
	
	    setVisible(true);
	    try {
	      jbInit();
	    }
	    catch(Exception e) {
	      e.printStackTrace();
	    }
  }
  
  private void jbInit() throws Exception {
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
}