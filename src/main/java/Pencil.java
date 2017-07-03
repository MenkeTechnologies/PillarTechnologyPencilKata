import java.awt.print.Pageable;

/**
 * Created by jacobmenke on 7/2/17.
 */
class PointDurability {
    public static final Integer UPPERCASE_CHARCTER_DURABILITY_COST = 2;
    public static final Integer LOWERCASE_CHARCTER_DURABILITY_COST = 1;
}

class PencilDefaults {
    public static final Integer DEFAULT_PENCIL_LENGTH = 100;
    public static final Integer DEFAULT_PENCIL_INITIAL_POINT_DURABILITY = 40_000;
    public static final Integer DEFAULT_PENCIL_INITIAL_ERASER_DURABILITY = 40_000;
}

class SpecialCharacters {
    public static final Character SPACE = ' ';
}

public class Pencil {
    private Paper paper;
    private Integer pointDurability;
    private Integer initialPointDurability;
    private Integer length;
    private Integer eraserDurability;
    private Integer initialEraserDurability;

    public Integer getLength() {
        return length;
    }

    public Pencil(Integer length, Integer pointDurability, Integer eraserDurability) {
        this.length = length;
        this.initialPointDurability = pointDurability;
        this.pointDurability = initialPointDurability;
        this.initialEraserDurability = eraserDurability;
        this.eraserDurability = initialEraserDurability;
    }

    public Pencil(Integer length) {
        this(length, PencilDefaults.DEFAULT_PENCIL_INITIAL_POINT_DURABILITY, PencilDefaults.DEFAULT_PENCIL_INITIAL_ERASER_DURABILITY);
    }

    public Pencil(Integer length, Integer eraserDurability) {
        this(length, PencilDefaults.DEFAULT_PENCIL_INITIAL_POINT_DURABILITY, eraserDurability);
    }

    public Integer getPointDurability() {
        return pointDurability;
    }

    public Integer getInitialPointDurability() {
        return initialPointDurability;
    }

    public Integer getEraserDurability() {
        return eraserDurability;
    }

    public void setPaperToWriteTo(Paper paper) {
        this.paper = paper;
    }

    /**
     * writes characters to the paper as long as paper object has been set
     *
     * @param content the textual content to write to the paper
     */
    public void write(String content) {

        if (paper != null) {

            for (int i = 0; i < content.length(); i++) {
                Character currentCharacter = content.charAt(i);
                if (Character.isUpperCase(currentCharacter)) {
                    pointDurability -= PointDurability.UPPERCASE_CHARCTER_DURABILITY_COST;
                }
                //how should punctuation be counted?
                if (Character.isLowerCase(currentCharacter)) {
                    pointDurability -= PointDurability.LOWERCASE_CHARCTER_DURABILITY_COST;
                }

                if (pointDurability >= 0) {
                    paper.addCharacter(currentCharacter);
                } else {
                    paper.addCharacter(SpecialCharacters.SPACE);
                }
            }
        } else {
            System.err.println("No paper to write to.");
        }
    }

    /**
     * sharpens the pencil point restoring point durability and reduces the pencil length
     * if the pencil length is greater than zero
     */
    public void sharpen() {

        if (length > 0) {
            length--;
            pointDurability = initialPointDurability;
        }
    }

    /**
     * erases text from the paper object if it exists
     *
     * @param textToErase the text to erase from the paper object
     */
    public Integer erase(String textToErase) {
        if (paper != null) {

            Integer lengthToErase = textToErase.length();
            Integer startingIndexTextToErase = paper.getContents().lastIndexOf(textToErase);
            Integer endingIndexTextToErase = startingIndexTextToErase + lengthToErase-1;
            StringBuilder uneditedRegionsStringBuilder = new StringBuilder();
            //head end of text unaffected by erasure
            uneditedRegionsStringBuilder.append(paper.getContents().substring(0, startingIndexTextToErase));
            //text affected by erasure
            StringBuilder newStringToReplaceErasedString = new StringBuilder();

            //loop backwards from ending index of text to erase until starting index of text to erase

            for (int i = endingIndexTextToErase; i >= startingIndexTextToErase; i--) {
                char currentCharacter = paper.getContents().charAt(i);
                if (eraserDurability > 0) {
                    //if eraser is not expended then add space to replacement string

                    newStringToReplaceErasedString.append(SpecialCharacters.SPACE);
                    if (!Character.isWhitespace(currentCharacter)) {
                        eraserDurability--;
                    }
                } else {
                    //if erase is expended add back original character
                    newStringToReplaceErasedString.append(currentCharacter);
                }
            }
            //we appended to replacement string in reverse order so we must reverse this string to put
            //into right order
            uneditedRegionsStringBuilder.append(newStringToReplaceErasedString.reverse());
            //add end part of text not affected by erasure
            uneditedRegionsStringBuilder.append(paper.getContents().substring(endingIndexTextToErase+1));
            paper.eraseAndSetContents(uneditedRegionsStringBuilder.toString());

            return startingIndexTextToErase;
        } else {
            System.err.println("No paper to write to.");
        }

        return -1;
    }
}
