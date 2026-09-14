package com.dsavisualizer.app.ui.visualization.runtime;

public final class MatrixAlgorithmEngine extends BaseStepEngine {

    public MatrixAlgorithmEngine(VisualizationRuntimeHost host) {
        super(host);
    }

    @Override
    public Step next() {
        if (host == null) {
            return Step.idle(true, false);
        }
        advance(host);
        host.captureCurrentSnapshot();
        return Step.moved(false, host.isPlaybackComplete());
    }

    public static void advance(VisualizationRuntimeHost host) {
        int[][] matrixValues = host.getMatrixValues();
        if (matrixValues.length == 0) {
            host.initializeCustomSortPreview();
            matrixValues = host.getMatrixValues();
        }
        if (matrixValues.length == 0) {
            host.finish("Matrix is empty");
            return;
        }

        int matrixK = host.getMatrixK();
        int matrixRow = host.getMatrixRow();
        int matrixCol = host.getMatrixCol();

        if (matrixK == -1) {
            matrixK = 0;
            matrixRow = 0;
            matrixCol = 0;
        }

        if (matrixK >= matrixValues.length) {
            host.finish(("warshall_algorithm".equals(host.getTechniqueId()) ? "Warshall" : "Floyd") + " completed");
            return;
        }

        if ("warshall_algorithm".equals(host.getTechniqueId())) {
            int candidate = (matrixValues[matrixRow][matrixK] == 1 && matrixValues[matrixK][matrixCol] == 1) ? 1 : 0;
            matrixValues[matrixRow][matrixCol] = Math.max(matrixValues[matrixRow][matrixCol], candidate);
            host.setHighlightedLines(3, 1);
            host.updateStatusText("Checking whether path " + matrixRow + " -> " + matrixK + " -> " + matrixCol + " creates reachability.");
        } else {
            int via = safeAdd(matrixValues[matrixRow][matrixK], matrixValues[matrixK][matrixCol]);
            if (via < matrixValues[matrixRow][matrixCol]) {
                matrixValues[matrixRow][matrixCol] = via;
            }
            host.setHighlightedLines(3, 1);
            host.updateStatusText("Checking whether going through " + matrixK + " improves the cost from " + matrixRow + " to " + matrixCol + ".");
        }

        host.setMatrixState(matrixValues, matrixK, matrixRow, matrixCol, host.resolveMatrixLabel());

        matrixCol++;
        if (matrixCol >= matrixValues.length) {
            matrixCol = 0;
            matrixRow++;
        }
        if (matrixRow >= matrixValues.length) {
            matrixRow = 0;
            matrixK++;
        }

        host.setMatrixValues(matrixValues);
        host.setMatrixK(matrixK);
        host.setMatrixRow(matrixRow);
        host.setMatrixCol(matrixCol);
    }

    private static int safeAdd(int left, int right) {
        if (left >= 9999 || right >= 9999) {
            return 9999;
        }
        return left + right;
    }
}
