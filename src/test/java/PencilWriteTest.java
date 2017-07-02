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










}
