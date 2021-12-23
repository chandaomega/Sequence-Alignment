package sequenceAlignment;
import java.util.HashMap;
import java.util.Map;

public abstract class SequenceAlignment {
    static final int DELTA;
    static final Map<Character, Integer> BASE_TO_INDEX_MAP;
    static final int[][] ALPHA;

    static {
        DELTA = 30;

        BASE_TO_INDEX_MAP = new HashMap<>();
        BASE_TO_INDEX_MAP.put('A', 0);
        BASE_TO_INDEX_MAP.put('C', 1);
        BASE_TO_INDEX_MAP.put('G', 2);
        BASE_TO_INDEX_MAP.put('T', 3);

        ALPHA = new int[][]{
                {0, 110, 48, 94},
                {110, 0, 118, 48},
                {48, 118, 0, 110},
                {94, 48, 110, 0}};
    }

    protected String x;

    protected String y;

    protected StringBuilder aligned_x;

    protected StringBuilder aligned_y;

    protected long alignmentScore;

    SequenceAlignment(String x, String y) {
        this.x = x;
        this.y = y;
        aligned_x = new StringBuilder();
        aligned_y = new StringBuilder();
    }

    public abstract void runAlgorithm();

    public long getAlignmentScore() {
        return alignmentScore;
    }

    public String getAlignment() {
        return format(aligned_x).append("\n").append(format(aligned_y)).toString();
    }

    private StringBuilder format(StringBuilder alignment) {
        if (alignment.length() <= 50) return alignment;

        StringBuilder op = new StringBuilder();
        return op.append(alignment.substring(0, 50)).append(" ")
                .append(alignment.substring(alignment.length() - 50));
    }

    int getMismatchCost(char base1, char base2) {
        return ALPHA[BASE_TO_INDEX_MAP.get(base1)][BASE_TO_INDEX_MAP.get(base2)];
    }
}
