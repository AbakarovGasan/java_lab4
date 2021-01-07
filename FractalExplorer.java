import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;

/**
  * Этот класс позволяет исследовать различные части фрактала с
  * созданием и отображением графического интерфейса Swing и обработкой событий, вызванных различными
  * взаимодействиями с пользователем.
  */
public class FractalExplorer
{
    /** Целочисленный размер дисплея - это ширина и высота дисплея в пикселях. **/
    private int displaySize;
    
    /**
      * Ссылка JImageDisplay для обновления отображения с помощью различных методов 
      * после того как фрактал вычислен.
     */
    private JImageDisplay display;
    
    
    private FractalGenerator fractal;
    
    private Rectangle2D.Double range;
    
    /**
      * Конструктор, который принимает размер дисплея, сохраняет его и
      * инициализирует объекты диапазона и фрактал-генератора.
    **/
    
    public FractalExplorer(int size) {
        this.displaySize = size;
        
        this.fractal = new Mandelbrot(); // объявить фрактал
        
        this.range = new Rectangle2D.Double();
        
        this.fractal.getInitialRange(range);
        
        this.display = new JImageDisplay(this.displaySize, this.displaySize);
    
    }
    /**
      * Этот метод инициализирует графический интерфейс Swing с помощью JFrame, содержащего
      * объект JImageDisplay и кнопка для сброса дисплея
    **/
    public void createAndShowGUI()
    {
        this.display.setLayout(new BorderLayout());
        JFrame myframe = new JFrame("фрактал мальдерброта");
        
        
        myframe.add(this.display, BorderLayout.CENTER);
        
        /** Создать кнопку сброса */
        JButton resetButton = new JButton("Сбросить");
        
        /** Обработка событии. */
        ResetHandler handler = new ResetHandler();
        resetButton.addActionListener(handler);
        
        /** Добавить кнопку. */
        myframe.add(resetButton, BorderLayout.SOUTH);
        
        /** Установить операцию закрытия фрейма по умолчанию на "exit". **/
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /**
         * Разместить содержимое фрейма так, чтобы оно было видно, и
         * запретить изменение размера окна.
         */
         /* добавить обработчик нажатия кнопки*/
        myframe.addMouseListener(new MouseHandler());
         
         
        myframe.pack();
        myframe.setVisible(true);
        myframe.setResizable(false);
    }
    
    /**
     * Bспомогательный метод для отображения фрактала. Этот метод зацикливается
     * на каждый пиксель на дисплее и вычисляет количество
     * итераций для соответствующих координат во фрактале
     * в oбласти отображения. Если количество итераций равно -1, установить цвет пикселя
     * в черный. В противном случае выбрать значение, основанное на количестве итераций.
     */
    private void drawFractal()
    {
        for (int x=0; x<displaySize; x++){
            for (int y=0; y<displaySize; y++){
                
                /**
                 * Найти соответствующие координаты xCoord и yCoord
                 * в области отображения фрактала.
                 */
                double xCoord = fractal.getCoord(range.x,
                range.x + range.width, displaySize, x);
                double yCoord = fractal.getCoord(range.y,
                range.y + range.height, displaySize, y);
                
                /**
                 * Вычислить количество итераций для координат в
                 * область отображения фрактала.
                 */
                int iteration = fractal.numIterations(xCoord, yCoord);
                
                /* Если количество итераций равно -1, установить черный пиксель. */
                if (iteration == -1){
                    display.drawPixel(x, y, 0);
                }
                
                else {
                    /**
                     * В противном случае выбрать значение оттенка на основе числа
                     * итераций.
                     */
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                
                    /** Обновить каждый пиксель. **/
                    display.drawPixel(x, y, rgbColor);
                }
                
            }
        }
        /*
        * Когда все пиксели будут нарисованы, перекрасить JImageDisplay в соответствии с
          * текущим содержимым его изображения. */
        display.repaint();
    }
    
    
        
    private class ResetHandler implements ActionListener
    {
        /**
         * Обработчик сбрасывает диапазон до начального диапазона, 
         * заданного параметром, а затем рисует фрактал.
         */
        public void actionPerformed(ActionEvent e)
        {
            fractal.getInitialRange(range);
            drawFractal();
        }
    }
    
    private class MouseHandler extends MouseAdapter
    {
        /**
         * Когда обработчик получает событие щелчка мыши, он отображает пиксель-
          * координаты щелчка в области фрактала, который
          * отображается, а затем вызывает функцию RecenterAndZoomRange () генератора
          * метод с координатами, по которым был выполнен щелчок, и шкалой 0,7.
         */
        @Override
        public void mouseClicked(MouseEvent e)
        {
            /** получить х координату после клика **/
            int x = e.getX();
            double xCoord = fractal.getCoord(range.x,
            range.x + range.width, displaySize, x);
            
            /** получить у координату после клика **/
            int y = e.getY();
            double yCoord = fractal.getCoord(range.y,
            range.y + range.height, displaySize, y);
            
            /**
             * вызывает метод recenterAndZoomRange() с
             * координатами где мы кликнули.
             */
             
             /**
              *  если кликнули правой кнопкой мыши, то увеличить маштаб, если другой,
              * то уменьшить маштаб
              */
            if (e.getButton()==1) fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            else fractal.recenterAndZoomRange(range, xCoord, yCoord, 2);
            /**
             * перерисовать фрактал.
             */
            drawFractal();
        }
    }
    
/** * Статический метод main () для запуска FractalExplorer.
 *  Инициализирует новый экземпляр * FractalExplorer с размером отображения 900,
 *  вызывает * createAndShowGUI () в объекте проводника, а затем вызывает * drawFractal ()
 в проводнике, чтобы увидеть начальное представление. */
    public static void main(String[] args)
    {
        FractalExplorer gui = new FractalExplorer(800);
        gui.createAndShowGUI();
        gui.drawFractal();
    }
}





