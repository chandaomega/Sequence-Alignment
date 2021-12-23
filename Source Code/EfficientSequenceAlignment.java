package sequenceAlignment;

public class EfficientSequenceAlignment extends SequenceAlignment  {
    public EfficientSequenceAlignment(String x, String y) {
        super(x, y);
    }

    public void runAlgorithm() {
        constructAlignment(0, x.length(), 0, y.length());
    }

    private void constructAlignment(int x_start, int x_end, int y_start, int y_end) {
        int x_length = x_end - x_start;
        int y_length = y_end - y_start;

        if (x_length <= 2 || y_length <= 2) {
            BasicSequenceAlignment bsa  = new BasicSequenceAlignment(
                    x.substring(x_start, x_end),
                    y.substring(y_start, y_end));
            bsa.runAlgorithm();
            this.alignmentScore += bsa.alignmentScore;
            this.aligned_x.append(bsa.aligned_x);
            this.aligned_y.append(bsa.aligned_y);
            return;
        }

        int y_optimalMid = findOptimalMid(x.substring(x_start, x_end), y.substring(y_start, y_end)) +  y_start;
        int x_mid = (x_length % 2 == 0 ? x_length/2 : x_length/2 + 1) + x_start;

        constructAlignment(x_start, x_mid, y_start, y_optimalMid);
        constructAlignment(x_mid, x_end, y_optimalMid, y_end);
    }

    private int findOptimalMid(String x, String y) {
        int x_length = x.length();
        int y_length = y.length();

        int x_mid = x_length % 2 == 0 ? x_length/2 : x_length/2 + 1;
        long[] scoresForX = computeAlignmentScores(x.substring(0, x_mid), y);
        long[] scoresForXr = computeAlignmentScores(
                new StringBuilder(x.substring(x_mid)).reverse().toString(),
                new StringBuilder(y).reverse().toString());
        x = null;
        y = null;

        int optimalMid = 0;
        long minScore = Long.MAX_VALUE;
        for (int i = 0; i <= y_length; i++) {
            long score = scoresForX[i] + scoresForXr[y_length - i];
            if (minScore > score) {
                minScore = score;
                optimalMid = i;
            }
        }
        return optimalMid;
    }

    private long[] computeAlignmentScores(String x, String y) {
        int x_length = x.length();
        int y_length = y.length();

        long[] prev = new long[y_length + 1];
        for (int i = 0; i <= y_length; i++) {
            prev[i] = i * DELTA;
        }

        for (int i = 1; i <= x_length; i++) {
            long[] current = new long[y_length + 1];
            current[0] = i * DELTA;
            for (int j = 1; j <= y_length; j++) {
                int mismatchCost = getMismatchCost(x.charAt(i - 1), y.charAt(j - 1));
                current[j] = Math.min(mismatchCost + prev[j - 1],
                        Math.min(DELTA + prev[j], DELTA + current[j - 1]));
            }
            prev = current;
        }
        return prev;
    }

}
