/**
 * Created by jacobmenke on 7/2/17.
 */
class PointDurabilityCostConstants {
    public static final Integer UPPERCASE_CHARACTER_DURABILITY_COST = 2;
    public static final Integer LOWERCASE_CHARACTER_DURABILITY_COST = 1;
}

class PencilDefaults {
    public static final Integer DEFAULT_PENCIL_LENGTH = 100;
    public static final Integer DEFAULT_PENCIL_INITIAL_POINT_DURABILITY = 40_000;
    public static final Integer DEFAULT_PENCIL_INITIAL_ERASER_DURABILITY = 40_000;
}

class PencilSpecialCharacters {
    public static final Character SPACE = ' ';
    public static final Character OVERLAP_CHARACTER = '@';
}

public class Pencil {
    private Paper paper;
    private Integer pointDurability;
    private Integer initialPointDurability;
    private Integer length;
    private Integer eraserDurability;
    private Integer initialEraserDurability;


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


    public void setPaperToWriteTo(Paper paper) {
        this.paper = paper;
    }

    /**
     * writes characters to the paper as long as paper object has been set
     * @param content the textual content to write to the paper
     */
    public void write(String content) {

        if (paper != null) {

            for (int i = 0; i < content.length(); i++) {
                Character currentCharacter = content.charAt(i);

                if (Character.isUpperCase(currentCharacter)) {
                    pointDurability -= PointDurabilityCostConstants.UPPERCASE_CHARACTER_DURABILITY_COST;
                }

                if (Character.isLowerCase(currentCharacter)) {
                    pointDurability -= PointDurabilityCostConstants.LOWERCASE_CHARACTER_DURABILITY_COST;
                }
                //should punctuation decrease point durability?

                if (pointDurability >= 0) {
                    paper.addCharacter(currentCharacter);
                } else {
                    paper.addCharacter(PencilSpecialCharacters.SPACE);
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
     * @param textToErase the text to erase from the paper object
     */
    public Integer erase(String textToErase) {
        if (paper != null) {

            Integer lengthToErase = textToErase.length();
            Integer startingIndexTextToErase = paper.read().lastIndexOf(textToErase);
            Integer endingIndexTextToErase = startingIndexTextToErase + lengthToErase - 1;
            //last erased location defaults to starting index of text to erase
            //if eraser runs out during erase mark that location as last erased location
            Integer lastErasedLocation = startingIndexTextToErase;

            StringBuilder uneditedRegionsStringBuilder = new StringBuilder();
            //head end of text unaffected by erasure
            uneditedRegionsStringBuilder.append(paper.read().substring(0, startingIndexTextToErase));
            //text affected by erasure
            StringBuilder newStringToReplaceErasedString = new StringBuilder();
            String charactersThatCouldNotBeErasedDueToEraserMalfunction = "";

            //loop backwards from ending index of text to erase until starting index of text to erase
            for (int i = endingIndexTextToErase; i >= startingIndexTextToErase; i--) {
                char currentCharacter = paper.read().charAt(i);
                if (eraserDurability > 0) {
                    //if eraser is not expended then add space to replacement string
                    newStringToReplaceErasedString.append(PencilSpecialCharacters.SPACE);
                    if (!Character.isWhitespace(currentCharacter)) {
                        eraserDurability--;
                    }
                } else {
                    //if erase is expended mark this location as last erased location
                    //add remaining characters to new replacement String and exit for loop
                    lastErasedLocation = i;
                    charactersThatCouldNotBeErasedDueToEraserMalfunction = paper.read().substring(startingIndexTextToErase, i + 1);
                    break;
                }
            }
            //we appended to replacement string in reverse order so we must reverse this string
            uneditedRegionsStringBuilder.append(charactersThatCouldNotBeErasedDueToEraserMalfunction).
                    append(newStringToReplaceErasedString.reverse());
            //add end part of text not affected by erasure
            uneditedRegionsStringBuilder.append(paper.read().substring(endingIndexTextToErase + 1));
            paper.eraseAndSetContents(uneditedRegionsStringBuilder.toString());

            return lastErasedLocation;
        } else {
            System.err.println("No paper to write to.");
        }

        return -1;
    }

    /**
     * inserts a word at a given location (index) in the paper which the pencil is writing to
     * @param erasureLocation the last erased location for a potential edit to be made
     * @param wordToInsert the word to be inserted in the erased location
     */
    public void edit(Integer erasureLocation, String wordToInsert) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paper.read().substring(0, erasureLocation));

        for (int i = 0; i < wordToInsert.length(); i++) {
            Integer eraseLocationPlusOffset = erasureLocation + i;
            if (Character.isWhitespace(paper.read().charAt(eraseLocationPlusOffset))) {
                stringBuilder.append(wordToInsert.charAt(i));
            } else {
                stringBuilder.append(PencilSpecialCharacters.OVERLAP_CHARACTER);
            }
        }

        stringBuilder.append(paper.read().substring(erasureLocation + wordToInsert.length()));
        paper.eraseAndSetContents(stringBuilder.toString());
    }


    public Integer getLength() {
        return length;
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
}
