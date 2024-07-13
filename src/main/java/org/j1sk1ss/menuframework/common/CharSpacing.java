// Special thanks to https://github.com/MitchGB/CustomInventoryUI/blob/main/src/main/java/com/buby/custominventoryui/CharRepo.java

package org.j1sk1ss.menuframework.common;

public enum CharSpacing {
    NEG1("\uF801"),
    NEG2("\uF802"),
    NEG4("\uF804"),
    NEG8("\uF808"),
    NEG16("\uF809"),
    NEG32("\uF80A"),
    NEG64("\uF80B"),
    NEG128("\uF80C"),
    NEG256("\uF80D"),
    NEG512("\uF80E"),
    NEG1024("\uF80F"),

    POS1("\uF821"),
    POS2("\uF822"),
    POS4("\uF824"),
    POS8("\uF828"),
    POS16("\uF829"),
    POS32("\uF82A"),
    POS64("\uF82B"),
    POS128("\uF82C"),
    POS256("\uF82D"),
    POS512("\uF82E"),
    POS1024("\uF82F");

    public final String literal;
    CharSpacing(String literal) {
        this.literal = literal;
    }

    @Override
    public String toString(){
        return this.literal;
    }

    private enum SpacingCharacters {
        NEG1(-1, CharSpacing.NEG1),
        NEG2(-2, CharSpacing.NEG2),
        NEG4(-4, CharSpacing.NEG4),
        NEG8(-8, CharSpacing.NEG8),
        NEG16(-16, CharSpacing.NEG16),
        NEG32(-32, CharSpacing.NEG32),
        NEG64(-64, CharSpacing.NEG64),
        NEG128(-128, CharSpacing.NEG128),
        NEG256(-256, CharSpacing.NEG256),
        NEG512(-512, CharSpacing.NEG512),
        NEG1024(-1024, CharSpacing.NEG1024),

        POS1(1, CharSpacing.POS1),
        POS2(2, CharSpacing.POS2),
        POS4(4, CharSpacing.POS4),
        POS8(8, CharSpacing.POS8),
        POS16(16, CharSpacing.POS16),
        POS32(32, CharSpacing.POS32),
        POS64(64, CharSpacing.POS64),
        POS128(128, CharSpacing.POS128),
        POS256(256, CharSpacing.POS256),
        POS512(512, CharSpacing.POS512),
        POS1024(1024, CharSpacing.POS1024);

        private final int weight;
        private final CharSpacing charRef;

        SpacingCharacters(int weight, CharSpacing charRef) {
            this.weight = weight;
            this.charRef = charRef;
        }
    }

    public static CharSpacing characterByWeight(int weight) {
        for (SpacingCharacters ch : SpacingCharacters.values()) if (ch.weight == weight) return ch.charRef;
        return null;
    }

    public static String getSpacing(int pixelAmount) {
        var binary = new StringBuilder(Integer.toBinaryString(Math.abs(pixelAmount))).reverse().toString();
        var sb     = new StringBuilder();
        var chArr  = binary.toCharArray();

        for (var index = 0; index < chArr.length; index++){
            var ch = chArr[index];
            if (ch == '0') continue;

            var weight = (int)Math.pow(2, index);
            weight = pixelAmount < 0 ? -weight : weight;
            var ref = characterByWeight(weight);

            if (ref != null) sb.append(ref.literal);
        }

        return sb.toString();
    }

    public static String getNeg(int pixelAmount) {
        return getSpacing(-Math.abs(pixelAmount));
    }

    public static String getPos(int pixelAmount) {
        return getSpacing(Math.abs(pixelAmount));
    }
}
