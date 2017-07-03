import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Created by jacobmenke on 6/27/17.
 */
public class PencilWriteTest {
   Pencil pencil;
   Paper paper;

    @Before
    public void setup(){
        pencil = new Pencil();
        paper = new Paper();
        pencil.setPaperToWriteTo(paper);
    }

    @Test
    public void whenPencilWritesToPaperThePaperReturnsContents(){
        String text = "Hello world.";
        pencil.write(text);
        assertEquals(text, paper.getContents());
    }

    @Test
    public void whenPencilAppendsToPaperThePaperReturnsContents(){
        String text = "She sells sea shells";
        String secondText = " down by the sea shore";
        paper.eraseAndSetContents(text);
        pencil.write(secondText);
        assertEquals(text + secondText , paper.getContents());
    }

    @Test
    public void whenPencilWritesSpacesAndNewlinesPointDurabilityRemainsSame(){
        Integer startingDurability = PointDurability.DEFAULT_STARTING_POINT_DURABILITY;
        pencil = new Pencil(startingDurability);
        pencil.setPaperToWriteTo(paper);
        String text = " \n \n ";
        pencil.write(text);
        assertEquals(startingDurability, pencil.getPointDurability());

    }

    @Test
    public void whenPencilWritesLowerCaseLettersPointDurabilityDecreasesBy1(){
        Integer startingDurability = pencil.getPointDurability();
        String text = "hello world";
        pencil.write(text);
        Integer lowerCaseCount = 0;

        for (int i = 0; i < text.length(); i++) {
            if (Character.isLowerCase(text.charAt(i))){
                lowerCaseCount++;
            }
        }

        Integer durabilityLoss = lowerCaseCount * PointDurability.LOWERCASE_CHARCTER_DURABILITY_COST;

        Integer expectedDurability = startingDurability - durabilityLoss;

        assertEquals(expectedDurability, pencil.getPointDurability());

    }


    @Test
    public void whenPencilWritesUpperCaseLettersPointDurabilityDecreasesBy2(){
        Integer startingDurability = pencil.getPointDurability();
        String text = "UPPERCASE CHARS";
        pencil.write(text);
        Integer upperCaseCount = 0;

        for (int i = 0; i < text.length(); i++) {
            if (Character.isUpperCase(text.charAt(i))){
                upperCaseCount++;
            }
        }

        Integer durabilityLoss = upperCaseCount * PointDurability.UPPERCASE_CHARCTER_DURABILITY_COST;

        Integer expectedDurability = startingDurability - durabilityLoss;

        assertEquals(expectedDurability, pencil.getPointDurability());

    }









}
