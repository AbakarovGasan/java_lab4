import java.awt.geom.Rectangle2D;

/**
 * Этот класс является подклассом Fractal Generator. Он используется для вычисления фрактала Мандельборта.
 */
public class Mandelbrot extends FractalGenerator
{
    /**
     * максимальное число итерации
     */
    public static final int MAX_ITERATIONS = 2000;
    
    /**
     * Этот метод позволяет генератору фракталов указать, какая часть
      комплексной плоскости наиболее интересна для фрактала.
      Ему передается объект прямоугольника, и метод изменяет
      прямоугольник, чтобы показать правильный начальный диапазон для фрактала.
      Эта реализация устанавливает начальный диапазон в (-2 - 1,5i) - (1 + 1,5i)
      или x = -2, y = -1,5, width = height = 3.
     */
    public void getInitialRange(Rectangle2D.Double range)
    {
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }
    
    /**
     * Этот метод реализует итерационную функцию для фрактала Мандельброта.
     
     */
    public int numIterations(double x, double y)
    {
        /** начать итерации с 0. */
        int iteration = 0;
        /** инициализировать zreal и zimaginary. */
        double zreal = 0;
        double zimaginary = 0;
        
        /**
         * Вычислить Zn = Zn-1 ^ 2 + c, где значения представляют собой комплексные числа, представленные
          * по zreal и zimaginary, Z0 = 0, а c - конкретная точка в
          * фрактале, который мы показываем (заданный x и y). Это повторяется
          * до тех пор, пока Z ^ 2> 4 (абсолютное значение Z больше 2) или
          * количество итераций достигнуто максимума.
         */
        while (iteration < MAX_ITERATIONS &&
               zreal * zreal + zimaginary * zimaginary < 4)
        {
            double zrealUpdated = zreal * zreal - zimaginary * zimaginary + x;
            double zimaginaryUpdated = 2 * zreal * zimaginary + y;
            zreal = zrealUpdated;
            zimaginary = zimaginaryUpdated;
            iteration += 1;
        }
        
        /**
          * Если количество максимальных итераций достигнуто, возвращает -1
         */
        if (iteration == MAX_ITERATIONS)
        {
            return -1;
        }
        
        return iteration;
    }
    
    /**
     */
    public String toString() {
        return "Мальдерброт";
    }

}
