package sequenceAlignment;

public class BasicSequenceAlignment extends SequenceAlignment {
    private long[][] optimizerMatrix;

    public BasicSequenceAlignment(String x, String y) {
        super(x, y);
        this.optimizerMatrix = new long[x.length()+ 1][y.length()+ 1];
    }

    public void runAlgorithm() {
        computeAlignmentScore();
        constructAlignment();
    }

    private void computeAlignmentScore() {
        int x_length = x.length();
        int y_length = y.length();

        for (int i = 0; i <= x_length; i++) {
            optimizerMatrix[i][0] = i * DELTA;
        }
        for (int i = 0; i <= y_length; i++) {
            optimizerMatrix[0][i] = i * DELTA;
        }

        for (int i = 1; i <= x_length; i++) {
            for (int j = 1; j <= y_length; j++) {
                int mismatchCost = getMismatchCost(x.charAt(i - 1), y.charAt(j - 1));
                optimizerMatrix[i][j] = Math.min(mismatchCost + optimizerMatrix[i - 1][j - 1],
                        Math.min(DELTA + optimizerMatrix[i - 1][j], DELTA + optimizerMatrix[i][j - 1]));
            }
        }

        this.alignmentScore = optimizerMatrix[optimizerMatrix.length - 1][optimizerMatrix[0].length - 1];
    }

    private void constructAlignment() {
        int i = x.length();
        int j = y.length();

        while (i != 0 && j != 0) {
            int mismatchCost = getMismatchCost(x.charAt(i - 1), y.charAt(j - 1));
            if (optimizerMatrix[i][j] == optimizerMatrix[i - 1][j - 1] + mismatchCost) {
                aligned_x.append(x.charAt(--i));
                aligned_y.append(y.charAt(--j));
            }  else if (optimizerMatrix[i][j] == optimizerMatrix[i - 1][j] + DELTA) {
                aligned_x.append(x.charAt(--i));
                aligned_y.append("_");
            } else if (optimizerMatrix[i][j] == optimizerMatrix[i][j - 1] + DELTA) {
                aligned_x.append("_");
                aligned_y.append(y.charAt(--j));
            }
        }

        while (i > 0) {
            aligned_x.append(x.charAt(--i));
            aligned_y.append("_");
        }

        while (j > 0) {
            aligned_x.append("_");
            aligned_y.append(y.charAt(--j));
        }

        aligned_x.reverse();
        aligned_y.reverse();
    }
    
}
