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











}
