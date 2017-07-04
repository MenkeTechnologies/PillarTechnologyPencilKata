import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jacobmenke on 6/27/17.
 */
public class PencilDurabilityTest {
    private Pencil pencil;
    private Paper paper;

    @Before
    public void setup() {
        pencil = new Pencil(PencilDefaults.DEFAULT_PENCIL_LENGTH);
        paper = new Paper();
        pencil.setPaperToWriteTo(paper);
    }

    @Test
    public void whenPencilWritesToPaperThePaperReturnsContents() {
        String text = "Hello world.";
        pencil.write(text);
        assertEquals(text, paper.read());
    }

    @Test
    public void whenPencilAppendsToPaperThePaperReturnsContents() {
        String text = "She sells sea shells";
        String secondText = " down by the sea shore";
        paper.eraseAndSetContents(text);
        pencil.write(secondText);
        assertEquals(text + secondText, paper.read());
    }

    @Test
    public void whenPencilWritesSpacesAndNewlinesPointDurabilityRemainsSame() {
        pencil = new Pencil(PencilDefaults.DEFAULT_PENCIL_LENGTH,
                PencilDefaults.DEFAULT_PENCIL_INITIAL_POINT_DURABILITY);
        pencil.setPaperToWriteTo(paper);
        String text = " \n \n ";
        pencil.write(text);
        assertEquals(PencilDefaults.DEFAULT_PENCIL_INITIAL_POINT_DURABILITY,
                pencil.getPointDurability());
    }

    @Test
    public void whenPencilWritesLowerCaseLettersPointDurabilityDecreasesByOne() {
        Integer startingDurability = pencil.getPointDurability();
        String text = "hello world";
        pencil.write(text);
        Integer lowerCaseCount = 0;

        for (int i = 0; i < text.length(); i++) {
            if (Character.isLowerCase(text.charAt(i))) {
                lowerCaseCount++;
            }
        }

        Integer durabilityLoss = lowerCaseCount * PointDurabilityCostConstants.LOWERCASE_CHARACTER_DURABILITY_COST;
        Integer expectedDurability = startingDurability - durabilityLoss;
        assertEquals(expectedDurability, pencil.getPointDurability());
    }

    @Test
    public void whenPencilWritesUpperCaseLettersPointDurabilityDecreasesByTwo() {
        Integer startingDurability = pencil.getPointDurability();
        String text = "UPPERCASE CHARS";
        pencil.write(text);
        Integer upperCaseCount = 0;

        for (int i = 0; i < text.length(); i++) {
            if (Character.isUpperCase(text.charAt(i))) {
                upperCaseCount++;
            }
        }

        Integer durabilityLoss = upperCaseCount * PointDurabilityCostConstants.UPPERCASE_CHARACTER_DURABILITY_COST;
        Integer expectedDurability = startingDurability - durabilityLoss;
        assertEquals(expectedDurability, pencil.getPointDurability());
    }

    @Test
    public void whenPencilPointDurabilityDoesNotReachZeroCharactersWrittenNormally() {
        Integer examplePointDurability = 4;
        pencil = new Pencil(PencilDefaults.DEFAULT_PENCIL_LENGTH, examplePointDurability,
                PencilDefaults.DEFAULT_PENCIL_INITIAL_ERASER_DURABILITY);
        String text = "text";
        Paper cleanSheet = new Paper();
        pencil.setPaperToWriteTo(cleanSheet);
        pencil.write(text);
        assertEquals("text", cleanSheet.read());
    }

    @Test
    public void whenPencilPointDurabilityReachesZeroCharactersWrittenAsSpaces() {
        Integer examplePointDurability = 4;
        pencil = new Pencil(PencilDefaults.DEFAULT_PENCIL_LENGTH, examplePointDurability,
                PencilDefaults.DEFAULT_PENCIL_INITIAL_ERASER_DURABILITY);
        String tooMuchText = "Text";
        Paper anotherCleanSheet = new Paper();
        pencil.setPaperToWriteTo(anotherCleanSheet);
        pencil.write(tooMuchText);
        assertEquals("Tex ", anotherCleanSheet.read());
    }

    @Test
    public void sharpeningPencilRestoresPointDurabilityToInitialPointDurability() {
        pencil = new Pencil(PencilDefaults.DEFAULT_PENCIL_LENGTH,
                PencilDefaults.DEFAULT_PENCIL_INITIAL_POINT_DURABILITY);
        pencil.setPaperToWriteTo(paper);
        pencil.write("test text");
        pencil.sharpen();

        assertEquals(PencilDefaults.DEFAULT_PENCIL_INITIAL_POINT_DURABILITY, pencil.getPointDurability());
    }

    @Test
    public void pencilLengthReducedByOneWhenSharpened() {
        pencil = new Pencil(PencilDefaults.DEFAULT_PENCIL_LENGTH);
        pencil.setPaperToWriteTo(paper);
        pencil.sharpen();
        Integer pencilLengthAfterSharpening = PencilDefaults.DEFAULT_PENCIL_LENGTH - 1;
        assertEquals(pencilLengthAfterSharpening, pencil.getLength());
    }

    @Test
    public void whenPencilLengthIsZeroSharpeningDoesNotRestorePointDurability() {
        pencil = new Pencil(1, PencilDefaults.DEFAULT_PENCIL_INITIAL_POINT_DURABILITY);
        pencil.setPaperToWriteTo(paper);
        pencil.sharpen();
        Integer zeroLength = 0;
        assertEquals(zeroLength, pencil.getLength());
        pencil.write("example TEXT");
        pencil.sharpen();
        assertNotEquals(PencilDefaults.DEFAULT_PENCIL_INITIAL_POINT_DURABILITY, pencil.getPointDurability());
    }

    @Test
    public void whenPencilErasesTextFromPaperLastOccurenceOfErasedTextIsReplacedWithSpaces() {
        Paper thirdCleanSheet = new Paper();
        pencil = new Pencil(PencilDefaults.DEFAULT_PENCIL_LENGTH);
        pencil.setPaperToWriteTo(thirdCleanSheet);
        pencil.write("How much wood would a woodchuck chuck if a woodchuck could chuck wood?");
        String textToErase = "chuck";
        pencil.erase(textToErase);
        String textWithSingleEraseOfChuck = "How much wood would a woodchuck chuck if a woodchuck could       wood?";
        assertEquals(textWithSingleEraseOfChuck, thirdCleanSheet.read());
        String textWithDoubleErasureOfChuck = "How much wood would a woodchuck chuck if a wood      could       wood?";
        pencil.erase(textToErase);
        assertEquals(textWithDoubleErasureOfChuck, thirdCleanSheet.read());
    }

    @Test
    public void pencilErasingOfCharactersStopsWhenPencilHasEraserDurabilityOfZero() {
        pencil = new Pencil(PencilDefaults.DEFAULT_PENCIL_LENGTH,3);
        Paper newPaper = new Paper();
        pencil.setPaperToWriteTo(newPaper);
        pencil.write("Buffalo Bill");
        pencil.erase("Bill");
        String textRemaining = "Buffalo B   ";
        assertEquals(textRemaining, newPaper.read());
    }

    @Test
    public void whenPencilWritesOverErasedTextItFitsIntoTheGivenWhiteSpaceAvailable() {
        String initialText = "An apple a day keeps the doctor away";
        pencil = new Pencil(PencilDefaults.DEFAULT_PENCIL_LENGTH);
        Paper penultimateSheet = new Paper();
        pencil.setPaperToWriteTo(penultimateSheet);
        pencil.write(initialText);
        Integer erasureLocation = pencil.erase("apple");
        pencil.edit(erasureLocation, "onion");
        String expectedText = "An onion a day keeps the doctor away";
        assertEquals(expectedText, penultimateSheet.read());
    }

    @Test
    public void whenWritingOverCharactersAnAtSignIsAdded() {
        String initialText = "An apple a day keeps the doctor away";
        Paper lastSheet = new Paper();
        pencil.setPaperToWriteTo(lastSheet);
        pencil.write(initialText);
        Integer erasureLocation = pencil.erase("apple");
        pencil.edit(erasureLocation, "artichoke");
        String finalText = "An artich@k@ay keeps the doctor away";
        assertEquals(finalText, lastSheet.read());
    }
}