import javax.swing.*;
import java.awt.image.*;
import java.awt.*;

/**
* Этот класс позволяет нам отображать наши фракталы.
 * Он является производным от javax.swing.JComponent.
 */
class JImageDisplay extends JComponent
{
    /**
     * Экземпляр буферизованного изображения.
     */ 
    private BufferedImage image;
    
    /**
     * Метод получения отображаемого изображения из другого класса
     */
    public BufferedImage getImage() {
        return this.image;
    }
    
    /**
      * Конструктор принимает целые значения ширины и высоты и инициализирует
      * его объект BufferedImage должен быть новым изображением с такой шириной и высотой
      * типа изображения TYPE_INT_RGB.
      */
    public JImageDisplay(int width, int height) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        /** 
         * Вызвать метод setPreferredSize () родительского класса
         * с заданной шириной и высотой.
         */
        super.setPreferredSize(new Dimension(width, height));
        
    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, this.image.getWidth(), this.image.getHeight(), null);
    }
    /**
     * Устанавливает все пиксели в данных изображения черными.
     */
    public void clearImage()
    {
        this.image.setRGB(0, 0, this.image.getWidth(), this.image.getHeight(),
         new int[this.image.getWidth() * this.image.getHeight()], 0, 1);
    }
    /**
     * Устанавливает пиксель определенного цвета.
     */
    public void drawPixel(int x, int y, int rgbColor)
    {
        this.image.setRGB(x, y, rgbColor);
    }
}
