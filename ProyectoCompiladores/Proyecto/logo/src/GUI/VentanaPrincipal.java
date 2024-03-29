package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import Configuracion.Configuracion;
import Configuracion.Linea;
import Interprete.Parser;

public class VentanaPrincipal extends JFrame {

    JTextArea areaDeCodigo;
    JScrollPane scrollCodigo;
    PanelDeDibujo panelDeDibujo;
    JButton ejecutar;
    Parser parser;
    boolean modoDebug;
    
    public VentanaPrincipal(){
        
        super("Proyecto Final");
        
        modoDebug = false;
        
        parser = new Parser();
        parser.insertarInstrucciones();
        
        areaDeCodigo = new JTextArea(20,10);
        areaDeCodigo.setFont(new Font("Console", Font.PLAIN, 20));
        areaDeCodigo.setLineWrap(true);
        areaDeCodigo.setWrapStyleWord(true);
        areaDeCodigo.setTabSize(4);
        scrollCodigo = new JScrollPane (areaDeCodigo);
        scrollCodigo.setBounds(10,10,400,550);
        add(scrollCodigo);
        
        panelDeDibujo = new PanelDeDibujo();
        panelDeDibujo.setBounds(scrollCodigo.getX()+scrollCodigo.getWidth()+10,10,Propiedades.PANEL_DE_DIBUJO_ANCHO,Propiedades.PANEL_DE_DIBUJO_LARGO);
        panelDeDibujo.setBackground(new Color(0, 153, 204));
        add(panelDeDibujo);
        
        ejecutar = new JButton("Ejecutar");
        ejecutar.setBounds(120,570,170,40);
        ejecutar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                parser.limpiar();
                if(parser.compilar(areaDeCodigo.getText()))
                    panelDeDibujo.setConfiguracion(parser.ejecutar());
                else{
                    parser = new Parser();
                    parser.insertarInstrucciones();
                    panelDeDibujo.setConfiguracion(parser.getConfiguracion());
                }
                panelDeDibujo.repaint();
            }
        });
        add(ejecutar);
        
        setLayout(null);
        setBounds(50,50,10+scrollCodigo.getWidth()+panelDeDibujo.getWidth()+30,649);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        
    }

}
