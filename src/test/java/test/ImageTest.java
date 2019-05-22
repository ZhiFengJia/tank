package test;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class ImageTest {

    @Test
    public void test() throws IOException {
//        BufferedImage image = ImageIO.read(new File("images/bulletD.gif"));
//        assertNotNull(image);

        BufferedImage image2 = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
        //this.getClass()
        assertNotNull(image2);
    }

}
